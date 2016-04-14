package examples.showcase.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class JobTest {
	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		scheduler.scheduleJob(PrintJobFactory.getJobDetail(), PrintJobFactory.getTrigger());
		scheduler.scheduleJob(HelloJobFactory.getJobDetail(), HelloJobFactory.getTrigger());
		scheduler.start();
	}
}
