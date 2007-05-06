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
public abstract class HibernateTestCase extends TestCase {
    protected static SessionFactory sessionFactory;

    protected void setUp() throws Exception {
        super.setUp();
        exportSchema();
    }

    protected abstract void exportSchema() throws HibernateException;

    public static void exportSchema(String[] files) throws HibernateException {
        Configuration cfg = new Configuration();
        for (int i = 0; i < files.length; i++) {
            cfg.addResource(files[i], TestCase.class.getClassLoader());
        }
        new SchemaExport(cfg).create(true, true);
        sessionFactory = cfg.buildSessionFactory(/*new TestInterceptor()*/);
    }
}
