package examples.showcase.quartz;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

public class JobDetailFactory {

	public static JobDetail getJobDetail(Class<? extends Job> jobClass) {
		JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
		JobDetail jobDetail = jobBuilder.build();
		return jobDetail;
	}
}
