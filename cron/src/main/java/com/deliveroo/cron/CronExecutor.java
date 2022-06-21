package com.deliveroo.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deliveroo.cron.model.CronExpression;

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
}
