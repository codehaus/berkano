package net.incongru.jodatime.hibernate;

import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.IOException;
import java.util.Formatter;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public abstract class AbstractHibernateTestCase extends TestCase {
    protected SessionFactory sessionFactory;
    private Configuration cfg;

    protected void setUp() throws Exception {
        super.setUp();
        cfg = new Configuration();

        setupConfiguration(cfg);

        cfg.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
        cfg.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:hbmtest" + getClass().getName());
        cfg.setProperty("hibernate.dialect", HSQLDialect.class.getName());

        cfg.setProperty("hibernate.show_sql", "true");
        sessionFactory = cfg.buildSessionFactory();

        SchemaExport export = new SchemaExport(cfg);
        export.create(true, true);
    }

    protected void tearDown() throws Exception {
        final String[] dropSQLs = cfg.generateDropSchemaScript(new HSQLDialect());
        final Connection connection = sessionFactory.openSession().connection();
        Statement stmt;
        try {
            stmt = connection.createStatement();
            for (String sql : dropSQLs) {
                System.out.println("sql[i] = " + sql);
                stmt.executeUpdate(sql);
            }
        } finally {
            connection.close();
        }

        this.sessionFactory.close();
        this.sessionFactory = null;
    }

    protected abstract void setupConfiguration(Configuration cfg);

    protected void printSqlQueryResults(final String sql) throws SQLException, IOException {
        final Session session = sessionFactory.openSession();
        final Connection conn = session.connection();
        final Statement stmt = conn.createStatement();
        final ResultSet rs = stmt.executeQuery(sql);
        final ResultSetMetaData metaData = rs.getMetaData();
        final Formatter out = new Formatter();
        for (int c = 1; c <= metaData.getColumnCount(); c++) {
            out.format(" %1$-15s |", metaData.getColumnName(c));
        }
        out.out().append("\n");
        while (rs.next()) {
            for (int c = 1; c <= metaData.getColumnCount(); c++) {
                out.format(" %1$-15s |", rs.getObject(c));
            }
            out.out().append("\n");
        }
        System.out.println(out);
        rs.close();
        stmt.close();
        conn.commit();
        //conn.close();
        session.close();
    }
}

