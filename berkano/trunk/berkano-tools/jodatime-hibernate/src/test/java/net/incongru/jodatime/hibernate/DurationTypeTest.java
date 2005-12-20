package net.incongru.jodatime.hibernate;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;

import java.sql.SQLException;
import java.io.IOException;

import net.incongru.jodatime.hibernate.testmodel.SomethingThatLasts;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class DurationTypeTest extends AbstractHibernateTestCase {
    protected void setupConfiguration(Configuration cfg) {
        cfg.addResource("net/incongru/jodatime/hibernate/testmodel/SomethingThatLasts.hbm.xml");
    }

    private Duration[] durations = new Duration[]{
            Duration.ZERO, new Duration(30), Period.seconds(30).toDurationTo(new DateTime()), Period.months(3).toDurationFrom(new DateTime())
    };

    public void testSimpleStore() throws SQLException, IOException {
        Session session = sessionFactory.openSession();

        for (int i = 0; i < durations.length; i++) {
            SomethingThatLasts thing = new SomethingThatLasts();
            thing.setId(i);
            thing.setName("test_" + i);
            thing.setTheDuration(durations[i]);
            session.save(thing);
        }

        session.flush();
        session.connection().commit();
        session.close();

        for (int i = 0; i < durations.length; i++) {
            session = sessionFactory.openSession();
            SomethingThatLasts lastingThing = (SomethingThatLasts) session.get(SomethingThatLasts.class, new Long(i));

            assertNotNull(lastingThing);
            assertEquals(i, lastingThing.getId());
            assertEquals("test_" + i, lastingThing.getName());
            assertEquals(durations[i], lastingThing.getTheDuration());

            session.close();
        }

        printSqlQueryResults("SELECT * FROM lasting");
    }

}
