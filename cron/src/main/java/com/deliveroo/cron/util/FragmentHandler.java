package com.deliveroo.cron.util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deliveroo.cron.model.CronFieldType;

/**
 * Exposes handlers to generate valid field values for cron expressions
 *
 * @author nandip
 * @since 19-Jun-2022
 **/
public class FragmentHandler {
    private static final Logger log = LoggerFactory.getLogger(FragmentHandler.class);

    /**
     * Returns all values between min value and max value (incremented by unity)
     *
     * @param minValue min value
     * @param maxValue max value
     * @return Set of all values between min and max (both inclusive)
     */
    public static Set<Integer> handleAllValuePatternMatch(int minValue, int maxValue) {
        return IntStream.range(minValue, maxValue + 1).mapToObj(Integer::valueOf).collect(Collectors.toSet());
    }

    /**
     * Returns the Integer represented by the inputValue string, if valid.
     *
     * @param inputValue Single input value as provided
     * @param minValue   min value
     * @param maxValue   max value
     * @return Set of all values between min and max (both inclusive)
     * @throws IllegalArgumentException if input is not valid or out of range
     */
    public static Integer handleSingleNumericMatch(String inputValue, int minValue, int maxValue) {
        try {
            Integer value = Integer.valueOf(inputValue);
            if (minValue <= value && value <= maxValue) {
                return value;
            }
            throw new IllegalArgumentException("Value not in range. input = " + inputValue + " min = " + minValue + " max = " + maxValue);
        } catch (NumberFormatException exception) {
            log.error("Error converting input string to Integer. " + inputValue, exception);
            throw new IllegalArgumentException("Incorrect integer value provided. " + inputValue);
        }
    }

    /**
     * Returns all valid integers between the start and end value.
     *
     * @param startValue start value as provided
     * @param endValue   end value as provided
     * @param minValue   min value
     * @param maxValue   max value
     * @return Set of all values between min and max (both inclusive)
     * @throws IllegalArgumentException if input is not valid or out of range
     */
    public static Set<Integer> handleNumericRangePatternMatch(String startValue, String endValue, int minValue, int maxValue) {
        Integer start = handleSingleNumericMatch(startValue, minValue, maxValue);
        Integer end = handleSingleNumericMatch(endValue, minValue, maxValue);
        if (start <= end) {
            return IntStream.range(start, end + 1).mapToObj(Integer::valueOf).collect(Collectors.toSet());
        } else {
            Set<Integer> outputSet = new HashSet<>();
            outputSet.addAll(IntStream.range(start, maxValue+1).boxed().collect(Collectors.toSet()));
            outputSet.addAll(IntStream.range(minValue, end+1).boxed().collect(Collectors.toSet()));
            return outputSet;
        }
    }

    /**
     * Return list of valid values for the step function
     *
     * @param startValue start value of the range defined
     * @param endValue   end value of the range defined
     * @param stepValue  step value to increment (if out of range, use Max+1 as default)
     * @param minValue   min value
     * @param maxValue   max value
     * @return Set of all values between min and max (both inclusive) incremented by step value
     * @throws IllegalArgumentException if input is not valid or out of range
     */
    public static Set<Integer> handleStepPatternMatch(String startValue, String endValue, String stepValue, int minValue, int maxValue) {
        Integer start = handleSingleNumericMatch(startValue, minValue, maxValue);
        Integer end = handleSingleNumericMatch(endValue, minValue, maxValue);
        // default step value
        Integer step = Integer.valueOf(maxValue) + 1;
        try {
            step = handleSingleNumericMatch(stepValue, minValue, maxValue);
        } catch (IllegalArgumentException e) {
            log.warn("Step value more than range. Using max as step value.");
        }
        // Populate all the valid values
        Set<Integer> output = new HashSet<>();
        for (int i = start; i <= end; i += step) {
            output.add(i);
        }
        return output;
    }

    /**
     * Returns single integer value, as defined by the input value (String).
     *
     * @param fieldType  Cron expression field type
     * @param inputValue Input word as provided
     * @param minValue   min value
     * @param maxValue   max value
     * @return Single value representing the input word
     * @throws IllegalArgumentException if input is not valid or out of range
     * @see NameToIntegerMap
     */
    public static Integer handleOnlyAlphabetPatternMatch(CronFieldType fieldType, String inputValue, int minValue, int maxValue) {
        Integer fieldIntValue = NameToIntegerMap.getIntegerValue(fieldType, inputValue.toLowerCase());
        return handleSingleNumericMatch(String.valueOf(fieldIntValue), minValue, maxValue);
    }

}
