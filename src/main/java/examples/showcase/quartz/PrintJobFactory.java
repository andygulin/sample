package examples.showcase.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class PrintJobFactory {

	public static JobDetail getJobDetail() {
		JobBuilder jobBuilder = JobBuilder.newJob(PrintJob.class);
		JobDetail jobDetail = jobBuilder.build();
		return jobDetail;
	}

	public static Trigger getTrigger() {
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(5, 1);
		triggerBuilder.withSchedule(simpleScheduleBuilder);
		Trigger trigger = triggerBuilder.build();
		return trigger;
	}
}