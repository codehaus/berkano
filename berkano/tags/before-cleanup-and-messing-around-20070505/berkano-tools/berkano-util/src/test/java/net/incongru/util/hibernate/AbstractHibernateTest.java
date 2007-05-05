package net.incongru.util.hibernate;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.derby.impl.jdbc.EmbedSQLException;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.DerbyDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public abstract class AbstractHibernateTest extends TestCase {
    private static final boolean SHOW_SQL = false;
    private static final String DERBY_REL_PATH = "target/derby/";
    private static final String DB_URL = "jdbc:derby:directory:Test";
    protected SessionFactory sessionFactory;

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
    }

    protected void tearDown() throws Exception {
        super.tearDown();

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

    protected abstract String[] getHbmFiles() throws HibernateException;

}
