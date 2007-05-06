package net.incongru.berkano.user.hibernate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import junit.framework.TestCase;
import net.incongru.berkano.security.password.matching.MD5MessageDigestPasswordMatchingStrategy;
import net.incongru.berkano.user.GroupDAO;
import net.incongru.berkano.user.GroupImpl;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;

import org.apache.commons.io.FileUtils;
import org.apache.derby.impl.jdbc.EmbedSQLException;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.DerbyDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernatedUserDAODBTest extends TestCase {
    private static final boolean SHOW_SQL = true;
    private static final String DERBY_REL_PATH = "target/derby/";
    private static final String DB_URL = "jdbc:derby:directory:Test";
    protected SessionFactory sessionFactory;
    private Session session;
    private GroupDAO groupDAO;
    private UserDAO userDAO;

    /**
     * BERKANO-31 : can't add users to group created in the same session
     */
    public void testAdditionOfNewlyCreatedUserInANewlyCreatedGroup() {
        // Create a new user
        User u = userDAO.newUser("user", "password", "email@email.com", "my User");
        // Create a new group
        GroupImpl group1 = (GroupImpl) groupDAO.newGroup("group-1");

        // Add user to group
        userDAO.addToGroup(u.getUserId(), group1.getGroupId());
    }

    protected void setUp() throws Exception {
        super.setUp();

        // clean and create the derby db:
        final String derbyPath = trashDerbyFiles();
        final Properties p = System.getProperties();
        p.put("derby.system.home", derbyPath);
        new EmbeddedDriver();
        final Connection c = DriverManager.getConnection(DB_URL + ";create=true");
        c.close();

        // setup hibernate:
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.dialect", DerbyDialect.class.getName());
        cfg.setProperty("hibernate.connection.driver_class", EmbeddedDriver.class.getName());
        cfg.setProperty("hibernate.connection.url", DB_URL);
        cfg.setProperty("hibernate.show_sql", Boolean.toString(SHOW_SQL));
        cfg.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        cfg.setProperty("hibernate.cache.use_second_level_cache", "false");
        cfg.setProperty("hibernate.cache.use_query_cache", "false");

        for (String file : getHbmFiles()) {
            cfg.addResource(file, TestCase.class.getClassLoader());
        }

        // create the db schema:
        new SchemaExport(cfg).create(SHOW_SQL, true);

        // setup hibernate sessionFactory
        sessionFactory = cfg.buildSessionFactory();

        // Initiate session
        session = sessionFactory.openSession();
        
        userDAO = new HibernatedUserDAO(session, new MD5MessageDigestPasswordMatchingStrategy());
        groupDAO = new HibernatedGroupDAO(session);
        
    }

    protected void tearDown() throws Exception {
        super.tearDown();

        session.close();
        sessionFactory.close();

        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (EmbedSQLException e) {
            assertEquals("Derby system shutdown.", e.getMessage());
        }

        trashDerbyFiles();
    }

    private String trashDerbyFiles() throws IOException {
        File derbyDir = new File(DERBY_REL_PATH);
        FileUtils.deleteDirectory(derbyDir);
        return derbyDir.getAbsolutePath();
    }

    protected String[] getHbmFiles() throws HibernateException {
        return new String[] { "net/incongru/berkano/user/Group.hbm" , "net/incongru/berkano/user/User.hbm", "net/incongru/berkano/security/Role.hbm" } ;
    }

}
