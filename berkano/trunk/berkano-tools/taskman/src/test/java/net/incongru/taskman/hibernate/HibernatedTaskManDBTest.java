package net.incongru.taskman.hibernate;

import junit.framework.TestCase;
import net.incongru.taskman.AbstractTaskManTestCase;
import net.incongru.taskman.TaskAction;
import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.def.TaskDef;
import net.incongru.taskman.def.TaskDefImpl;
import net.incongru.taskman.testmodel.FirstTaskAction;
import net.incongru.taskman.testmodel.FourthTaskAction;
import net.incongru.taskman.testmodel.SecondTaskAction;
import net.incongru.taskman.testmodel.ThirdTaskAction;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.DerbyDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class HibernatedTaskManDBTest extends AbstractTaskManTestCase {
    private static final String DERBY_REL_PATH = "target/derby/";
    private static final String DB_URL = "jdbc:derby:db-test";

    private SessionFactory sessionFactory;
    private Session session;

    protected void setUp() throws Exception {
        super.setUp();

        // clean and create the derby db:
        final String derbyPath = trashDerbyFiles();
        final Properties p = System.getProperties();
        p.put("derby.system.home", derbyPath);
        DriverManager.registerDriver(new EmbeddedDriver());
        DriverManager.getConnection(DB_URL + ";create=true");

        // setup hibernate:
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.dialect", DerbyDialect.class.getName());
        cfg.setProperty("hibernate.connection.driver_class", EmbeddedDriver.class.getName());
        cfg.setProperty("hibernate.connection.url", DB_URL);
        cfg.setProperty("hibernate.show_sql", "true");
        for (String file : getHbmFiles()) {
            cfg.addResource(file, TestCase.class.getClassLoader());
        }

        // create and verify the db schema:
        new SchemaExport(cfg).create(true, true);
        final Connection conn = DriverManager.getConnection(DB_URL);
        final ResultSet tables = conn.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        List<String> tableNames = new ArrayList<String>();
        while (tables.next()) {
            tableNames.add(tables.getString("TABLE_NAME").toLowerCase());
        }
        conn.close();
        assertTrue("tableNames.contains(\"taskdef\")", tableNames.contains("taskdef"));
        assertTrue("tableNames.contains(\"taskdef_actions\")", tableNames.contains("taskdef_actions"));
        // this is derby-specific :
        assertTrue("tableNames.contains(\"hibernate_unique_key\")", tableNames.contains("hibernate_unique_key"));
        assertEquals(3, tableNames.size());

        // setup hibernate sessionFactory and session:
        sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
    }

    protected void tearDown() throws Exception {
        super.tearDown();

        if (session.isOpen()) {
            session.close();
        }

        printSqlQueryResults("SELECT * FROM taskdef");
        printSqlQueryResults("SELECT * FROM taskdef_actions");

        //DriverManager.getConnection(DB_URL + ";shutdown=true");
        trashDerbyFiles();
    }

    private String trashDerbyFiles() throws IOException {
        File derbyDir = new File(DERBY_REL_PATH);
        org.apache.commons.io.FileUtils.deleteDirectory(derbyDir);
        return derbyDir.getAbsolutePath();
    }

    private String[] getHbmFiles() {
        return new String[]{"net/incongru/taskman/hibernate/TaskDef.hbm.xml", "net/incongru/taskman/hibernate/TaskInstance.hbm.xml"};
    }

    public void testTaskDefDeploymentSetsIdFirstVersionIdAndDeployedDateTime() throws SQLException, IOException {
        final HibernatedTaskMan taskMan = new HibernatedTaskMan(session, null);

        final TaskDef taskDef = getDummyTaskDef();
        assertNull(taskDef.getId());
        assertNull(taskDef.getVersionId());
        assertNull(taskDef.getDeploymentDateTime());

        final DateTime before = new DateTime();
        final TaskDef deployedTaskDef = taskMan.deployTaskDef(taskDef);
        final DateTime after = new DateTime();
        session.flush();
        session.close();

        assertNotNull(deployedTaskDef.getId());
        assertEquals(Long.valueOf(1), deployedTaskDef.getVersionId());
        assertEquals("some name", deployedTaskDef.getName());
        assertEquals("some desc", deployedTaskDef.getDescription());
        assertEquals(Period.weeks(2), deployedTaskDef.getDuePeriod());
        assertEquals(Period.days(3), deployedTaskDef.getDueDateTimeout());
        assertEquals(Period.minutes(10), deployedTaskDef.getReminderPeriod());
        final Map<TaskEvent, Class<? extends TaskAction>> eventActionMap = ((TaskDefImpl) deployedTaskDef).getEventActionMap();
        assertEquals(2, eventActionMap.size());
        assertEquals(FirstTaskAction.class, eventActionMap.get(TaskEvent.started));
        assertEquals(SecondTaskAction.class, eventActionMap.get(TaskEvent.cancelled));

        assertTrue(before.isBefore(deployedTaskDef.getDeploymentDateTime()));
        assertTrue(after.isAfter(deployedTaskDef.getDeploymentDateTime()));


        printSqlQueryResults("SELECT * FROM taskdef");
        printSqlQueryResults("SELECT * FROM taskdef_actions");
    }

    public void testTaskDefDeploymentIncreasesVersionNumberWithSameTaskDefName() throws SQLException, IOException {
        final HibernatedTaskMan taskMan = new HibernatedTaskMan(session, null);

        final TaskDef firstTaskDef = taskMan.deployTaskDef(getDummyTaskDef());
        final TaskDef secondTaskDef = taskMan.deployTaskDef(getDummyTaskDef());
        final TaskDef taskDef3 = getDummyTaskDef();
        ((TaskDefImpl) taskDef3).setDescription("new desc"); // don't do this as a user :o
        ((TaskDefImpl) taskDef3).getEventActionMap().put(TaskEvent.started, ThirdTaskAction.class); // don't do this as a user :o
        ((TaskDefImpl) taskDef3).getEventActionMap().put(TaskEvent.stopped, FourthTaskAction.class); // don't do this as a user :o
        final TaskDef thirdTaskDef = taskMan.deployTaskDef(taskDef3);
        session.flush();
        session.close();

        assertTrue(firstTaskDef.getVersionId().longValue() < secondTaskDef.getVersionId().longValue());
        assertTrue(secondTaskDef.getVersionId().longValue() < thirdTaskDef.getVersionId().longValue());
        assertEquals(Long.valueOf(1), firstTaskDef.getVersionId());
        assertEquals(Long.valueOf(2), secondTaskDef.getVersionId());
        assertEquals(Long.valueOf(3), thirdTaskDef.getVersionId());
    }

    // TODO :
//    public void testFindRemaingTasksWithActualData() {
//        Mock actionMan = mock(TaskActionManager.class);
//        actionMan.expects(once()).method("getTaskAction").with(isA(TaskInstance.class), eq(TaskEvent.instanciated)).will(returnValue(null));
//
//        final HibernatedTaskMan taskMan = new HibernatedTaskMan(session, (TaskActionManager) actionMan.proxy());
//
//        final TaskDef deployedTaskDef = taskMan.deployTaskDef(getDummyTaskDef());
//        taskMan.newTaskInstance(deployedTaskDef.getId(), null, null, null);
//
//    }

    private void printSqlQueryResults(final String sql) throws SQLException, IOException {
        final Session session = sessionFactory.openSession();
        final Connection conn = session.connection();
        final Statement stmt = conn.createStatement();
        final ResultSet rs = stmt.executeQuery(sql);
        final ResultSetMetaData metaData = rs.getMetaData();
        final Formatter out = new Formatter();
        for (int c = 1; c <= metaData.getColumnCount(); c++) {
            out.format(" %1$-10s |", metaData.getColumnName(c));
        }
        out.out().append("\n");
        while (rs.next()) {
            for (int c = 1; c <= metaData.getColumnCount(); c++) {
                out.format(" %1$-10s |", rs.getObject(c));
            }
            out.out().append("\n");
        }
        System.out.println(out);
        rs.close();
        stmt.close();
        conn.commit();
        conn.close();
        session.close();
    }
}
