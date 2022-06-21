package com.deliveroo.cron;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deliveroo.cron.model.CronExpression;

/**
 * Exposes functionalities to build a {@link com.deliveroo.cron.model.CronExpression} instance from input string
 *
 * @author nandip
 * @since 19-Jun-2022
 **/
public class CronExpressionParser {
    private static final Logger log = LoggerFactory.getLogger(CronExpressionParser.class);

    /**
     * Parses the input string and returns instance of {@link CronExpression} object. Throws {@link IllegalArgumentException} when input is not valid.
     * <p>
     *     Splits input string along the whitespace character. And parses individual fields
     * </p>
     *
     * @param inputArgument Command line input string
     * @return instance of {@link CronExpression}
     * @throws IllegalArgumentException when input is invalid
     */
    public static CronExpression parse(String inputArgument) {
        if (Objects.isNull(inputArgument) || inputArgument.isBlank()) {
            throw new IllegalArgumentException("Input cannot be null or empty string");
        }
        String[] input = inputArgument.trim().split("\\s");
        if (input.length < 6) {
            throw new IllegalArgumentException("Incorrect number of arguments. Pattern is [* * * * * $command]. Input = " + inputArgument);
        }
        return CronExpression.builder()
                .setMinuteField(input[0])
                .setHoursField(input[1])
                .setDayOfMonthField(input[2])
                .setMonthField(input[3])
                .setDayOfWeekField(input[4])
                .setCommandToExecute(input[5])
                .build();
    }
}
