package net.incongru.jodatime.hibernate;

import net.incongru.jodatime.hibernate.testmodel.SomethingThatHappens;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.joda.time.Period;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class PeriodTypeTest extends AbstractHibernateTestCase {
    protected void setupConfiguration(Configuration cfg) {
        cfg.addResource("net/incongru/jodatime/hibernate/testmodel/SomethingThatHappens.hbm.xml");
    }

    private Period[] periods = new Period[]{
            Period.days(2), Period.seconds(30), Period.months(3),
            new Period(30), new Period(4, 35, 40, 141),
            new Period(28, 10, 2, 2, 4, 35, 40, 141), new Period(28, 10, 0, 16, 4, 35, 40, 141),
            // new Period(new DateTime()),
            // new Period(new YearMonthDay()),
            // new Period(new YearMonthDay(2005, 12, 25)),
            // new Period(new YearMonthDay(2005, 0, 25)), new Period(new YearMonthDay(2005, 13, 25)),
            // new Period(new DateTime(CopticChronology.getInstance())            )
    };

    public void testSimpleStore() throws SQLException, IOException {
        Session session = sessionFactory.openSession();

        for (int i = 0; i < periods.length; i++) {
            SomethingThatHappens thing = new SomethingThatHappens();
            thing.setId(i);
            thing.setName("test_" + i);
            thing.setThePeriod(periods[i]);
            session.save(thing);
        }

        session.flush();
        session.connection().commit();
        session.close();

        for (int i = 0; i < periods.length; i++) {
            session = sessionFactory.openSession();
            SomethingThatHappens happeningThing = (SomethingThatHappens) session.get(SomethingThatHappens.class, new Long(i));

            assertNotNull(happeningThing);
            assertEquals(i, happeningThing.getId());
            assertEquals("test_" + i, happeningThing.getName());
            assertEquals(periods[i], happeningThing.getThePeriod());

            session.close();
        }

        printSqlQueryResults("SELECT * FROM happening");
    }

}
