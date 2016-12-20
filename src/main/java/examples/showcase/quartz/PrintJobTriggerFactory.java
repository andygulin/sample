package examples.showcase.quartz;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class PrintJobTriggerFactory {

    public static Trigger getTrigger() {
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(5, 1);
        triggerBuilder.withSchedule(simpleScheduleBuilder);
        Trigger trigger = triggerBuilder.build();
        return trigger;
    }
}