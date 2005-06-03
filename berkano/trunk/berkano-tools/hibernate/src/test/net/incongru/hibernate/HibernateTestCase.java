package net.incongru.hibernate;

import junit.framework.TestCase;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.tool.hbm2ddl.SchemaExport;

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
