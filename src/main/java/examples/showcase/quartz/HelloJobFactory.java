package examples.showcase.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class HelloJobFactory {

	public static JobDetail getJobDetail() {
		JobBuilder jobBuilder = JobBuilder.newJob(HelloJob.class);
		JobDetail jobDetail = jobBuilder.build();
		return jobDetail;
	}

	public static Trigger getTrigger() {
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withRepeatCount(5)
				.withIntervalInSeconds(2);
		triggerBuilder.withSchedule(simpleScheduleBuilder).startNow();
		Trigger trigger = triggerBuilder.build();
		return trigger;
	}
}