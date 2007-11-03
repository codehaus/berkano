package net.incongru.taskman.impl;

import net.incongru.taskman.TaskSweeper;
import net.incongru.taskman.TaskSweeperRunner;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * TODO : shutdown
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class SingleThreadedTaskSweeperRunner implements TaskSweeperRunner {
    private final TaskSweeper sweeper; // ? do we need to even keep a reference to this? probably if we need to reschedule...
    private final Period period;

    public SingleThreadedTaskSweeperRunner(TaskSweeper theSweeper, Period period) {
        this.sweeper = theSweeper;
        this.period = period;

        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                sweeper.sweepAndRemind();
            }
        }, 0, getDurationInMillis(period), TimeUnit.MILLISECONDS);
    }

    public Period getPeriod() {
        return period;
    }

    private long getDurationInMillis(Period period) {
        return period.toDurationFrom(new DateTime()).getMillis();
    }

    public static void main(String[] args) {
        final TaskSweeper ts = new TaskSweeper() {
            private int count = 0;

            public void sweepAndRemind() {
                System.out.print(new DateTime());
                System.out.println("  ++count : " + ++count);
                if (count % 8 == 0) {
                    throw new RuntimeException("I hate 8's");
                }
            }
        };
        final Period p = Period.seconds(2);
        System.out.println("p = " + p);
        System.out.println("p.getMillis() = " + p.getMillis());
        new SingleThreadedTaskSweeperRunner(ts, p);
    }
}
