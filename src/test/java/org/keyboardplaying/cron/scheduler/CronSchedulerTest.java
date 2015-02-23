package org.keyboardplaying.cron.scheduler;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.keyboardplaying.cron.exception.UnsupportedCronException;
import org.keyboardplaying.cron.expression.CronExpression;
import org.keyboardplaying.cron.expression.CronExpression.DayConstraint;
import org.keyboardplaying.cron.expression.CronExpression.Field;
import org.keyboardplaying.cron.expression.rule.AnyValueRule;
import org.keyboardplaying.cron.expression.rule.CronRule;
import org.keyboardplaying.cron.parser.CronSyntacticParser;

/**
 * Tests {@link CronScheduler}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
// XXX Javadoc
public class CronSchedulerTest {

    private Runnable job = new Runnable() {
        @Override
        public void run() {
            latch.countDown();
        }
    };

    private CronScheduler schd;
    private CountDownLatch latch;

    {
        schd = new CronScheduler();
        // Use a mock CRON parser to trigger a CRON everyy second.
        schd.setParser(new CronSyntacticParser() {

            @Override
            public boolean isValid(String cron) {
                return true;
            }

            @Override
            public CronExpression parse(String cron) {
                final CronRule any = new AnyValueRule();
                return CronExpression.Builder.create().set(DayConstraint.NONE)
                        .set(Field.SECOND, any)
                        .set(Field.MINUTE, any)
                        .set(Field.HOUR, any)
                        .set(Field.DAY_OF_MONTH, any)
                        .set(Field.MONTH, any)
                        .set(Field.DAY_OF_WEEK, any)
                        .set(Field.YEAR, any).build();
            }
        });
    }

    @Test(timeout = 3500)
    public void testExecution() throws InterruptedException, UnsupportedCronException {
        latch = new CountDownLatch(2);
        schd.startJob(new CronJob(job, "* * * * * *"));
        latch.await(3000, TimeUnit.MILLISECONDS);
        assertEquals(0, latch.getCount());
    }
}
