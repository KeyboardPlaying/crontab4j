package org.keyboardplaying.cron.scheduler;

import java.util.Objects;

/**
 * Object utility to represent a job that may be supplied to the scheduler.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class CronJob {

    private Runnable job;
    private String cron;

    /**
     * Creates a new job.
     *
     * @param job
     *            the job to be run
     * @param cron
     *            the CRON expression to trigger this job
     * @throws NullPointerException
     *            if {@code job} or {@code cron} are {@code null}
     */
    public CronJob(Runnable job, String cron) {
        Objects.requireNonNull(job);
        Objects.requireNonNull(cron);
        this.job = job;
        this.cron = cron;
    }

    /**
     * Returns the job to be run.
     *
     * @return the job to be run
     */
    public Runnable getJob() {
        return job;
    }

    /**
     * Returns the CRON expression to trigger this job.
     *
     * @return the CRON expression
     */
    public String getCron() {
        return cron;
    }
}