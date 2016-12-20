package examples.showcase.quartz;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class HelloJobTriggerFactory {

    public static Trigger getTrigger() {
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withRepeatCount(5)
                .withIntervalInSeconds(2);
        triggerBuilder.withSchedule(simpleScheduleBuilder).startNow();
        Trigger trigger = triggerBuilder.build();
        return trigger;
    }
}