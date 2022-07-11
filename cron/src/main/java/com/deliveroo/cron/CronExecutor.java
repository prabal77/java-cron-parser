package com.deliveroo.cron;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deliveroo.cron.model.CronExpression;
import com.deliveroo.cron.model.CronField;

/**
 * Provides CRON job execution functionality. Parses the input String and prints {@link CronExpression}
 *
 * @author nandip
 * @since 19-Jun-2022
 **/
public class CronExecutor {
    private static final Logger log = LoggerFactory.getLogger(CronExecutor.class);

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("No arguments passed");
        }
        try {
            CronExpression cronExpression = CronExpressionParser.parse(args[0]);
            print(cronExpression);
        } catch (IllegalArgumentException exception) {
            log.error("Error parsing cron expression: ", exception);
            throw exception;
        }
    }

    public static LocalDateTime getNextDate(CronExpression cronExpression, LocalDateTime currentDateTime) {
        int currentMin = currentDateTime.getMinute();
        // find next highest minute
        NextValue nextValueMin = getNextValue(cronExpression.getMinuteField(), currentDateTime.getMinute(), 0);
        NextValue nextValueHour = getNextValue(cronExpression.getHoursField(), currentDateTime.getHour(), nextValueMin.carryForward);
        NextValue nextValueDayOfMonth = getNextValue(cronExpression.getDayOfMonthField(), currentDateTime.getDayOfMonth(), nextValueMin.carryForward);
        NextValue nextValueMonth = getNextValue(cronExpression.getMonthField(), currentDateTime.getMonth().getValue(), nextValueMin.carryForward);
        NextValue nextValueDayOfWeek = getNextValue(cronExpression.getDayOfWeekField(), currentDateTime.getDayOfWeek().getValue(), nextValueMin.carryForward);


        return null;
    }

    public static NextValue getNextValue(CronField cronField, int currentValue, int previousCarry) {
        int nextMin = -1;
        for (Integer i = 0; i < cronField.getInputValues().size(); i++) {
            if (cronField.getInputValues().get(i) >= currentValue+previousCarry) {
                nextMin = cronField.getInputValues().get(i);
                break;
            }
        }
        NextValue out = (nextMin == -1) ? new NextValue(cronField.getInputValues().get(0), 1) : new NextValue(nextMin, 0);
        System.out.println(out);
        return out;
    }


    public static boolean isValidDate(LocalDateTime dateTime) {
        return false;
    }

    /**
     * Print the cronExpression in the required format
     *
     * @param cronExpression
     */
    private static void print(CronExpression cronExpression) {
        System.out.println(String.format("%-14s%s", "minute", cronExpression.getMinuteField().getInputValues()));
        System.out.println(String.format("%-14s%s", "hour", cronExpression.getHoursField().getInputValues()));
        System.out.println(String.format("%-14s%s", "day of month", cronExpression.getDayOfMonthField().getInputValues()));
        System.out.println(String.format("%-14s%s", "month", cronExpression.getMonthField().getInputValues()));
        System.out.println(String.format("%-14s%s", "day of week", cronExpression.getDayOfWeekField().getInputValues()));
        System.out.println(String.format("%-14s%s", "command", cronExpression.getCommandToExecute()));
    }

    public static class NextValue {
        public int nextValue;
        public int carryForward;

        public NextValue(int nextValue, int carryForward) {
            this.nextValue = nextValue;
            this.carryForward = carryForward;
        }

        public String toString() {
            return "NextValue [" + this.nextValue + " , " + this.carryForward + " ]";
        }

    }
}
