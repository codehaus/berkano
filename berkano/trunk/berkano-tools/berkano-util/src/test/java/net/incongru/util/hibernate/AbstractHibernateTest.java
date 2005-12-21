package net.incongru.util.hibernate;

import junit.framework.TestCase;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.3 $
 */
public abstract class AbstractHibernateTest extends TestCase {
    protected static SessionFactory sessionFactory;

    protected void setUp() throws Exception {
        super.setUp();
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.dialect", org.hibernate.dialect.HSQLDialect.class.getName());
        cfg.setProperty("hibernate.connection.driver_class", org.hsqldb.jdbcDriver.class.getName());
        cfg.setProperty("hibernate.connection.url", "jdbc:hsqldb:db-test");
        cfg.setProperty("hibernate.show_sql", "false");
        for (String file : getHbmFiles()) {
            cfg.addResource(file, TestCase.class.getClassLoader());
        }
        new SchemaExport(cfg).create(true, true);
        sessionFactory = cfg.buildSessionFactory(/*new TestInterceptor()*/);
    }

    protected abstract String[] getHbmFiles() throws HibernateException;
}
