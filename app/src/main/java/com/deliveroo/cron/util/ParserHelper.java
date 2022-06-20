package com.deliveroo.cron.util;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deliveroo.cron.model.CronFieldType;

/**
 * Exposes logic to parse Cron field expression string and return list of valid values
 *
 * @author nandip
 * @since 19-Jun-2022
 **/
public class ParserHelper {
    private static final Logger log = LoggerFactory.getLogger(ParserHelper.class);
    private static final String GLOBAL_INVALID_CHARACTER_REGEX = "[^a-zA-Z0-9\\*\\/\\-\\?\\#]+";

    /**
     * Parse the input string of type {@link com.deliveroo.cron.model.CronField} type and return list of valid values
     *
     * @param cronFieldType cron field type
     * @param input         input string
     * @return immutable list of valid values
     */
    public static List<Integer> parseCronField(CronFieldType cronFieldType, String input) {
        // Validate against whitelisted characters
        if (containsInValidCharacters(input)) {
            throw new IllegalArgumentException("Input " + input + " for cron field type " + cronFieldType.name() + " contains illegal characters");
        }
        // Trim string for any extra spaces
        String[] inputFragments = extractAllFragments(input.trim());
        // Use tree set to maintain natural order of output and remove duplicates
        TreeSet<Integer> output = new TreeSet<>();
        for (String fragment : inputFragments) {
            switch (cronFieldType) {
                case MINUTE:
                case HOUR:
                case DAY_OF_MONTH: {
                    output.addAll(parseNumericOnlyFields(cronFieldType, cronFieldType.getMin(), cronFieldType.getMax(), fragment));
                    break;
                }
                case MONTH:
                case DAY_OF_WEEK: {
                    output.addAll(parseAlphanumericFields(cronFieldType, cronFieldType.getMin(), cronFieldType.getMax(), fragment));
                    break;
                }
            }
        }
        return output.stream().collect(Collectors.toUnmodifiableList());
    }

    /**
     * Check if the input fragment contains any invalid character.
     * White listed characters: [a-zA-Z], numeric values [0-9], special characters ["*", "/", "-", "?", "#"]
     * Any other character/special character is considered invalid
     *
     * @param inputFragment
     * @return true if input contains invalid character
     */
    private static boolean containsInValidCharacters(String inputFragment) {
        return inputFragment.matches(GLOBAL_INVALID_CHARACTER_REGEX);
    }

    /**
     * Extract all input fragments from the input expression. Each input fragment denotes valid cron field separated by ","
     *
     * @param input input cron field string
     * @return array of input fragments
     */
    private static String[] extractAllFragments(String input) {
        if (input.indexOf(",") != -1) {
            return input.split(",");
        } else {
            return new String[]{input};
        }
    }

    /**
     * Parse the input fragment and returns all possible allowed values within the min and max range, as a set of integers.
     * Applicable only for numeric only fields i.e. minute, hour and day of month.
     * Performs following operations
     * <br/>
     * <b>Note: Uses multiple regex, instead of single. As this makes the code stable and less error prone due to indexing problems</b>
     * <br/>
     * <ul>
     *     <li>If input is '*' then returns all values in range (min and max) both including</li>
     *     <li>If input is single numeric value, return the value (if within the range)</li>
     *     <li>If input is range, returns all valid values within the range (both inclusive)</li>
     *     <li>If input is step function, returns values starting from min to max, incremented by step value</li>
     * </ul>
     *
     * @param cronFieldType - Type of cron field function is parsing
     * @param minValue      - Min value defined for the field
     * @param maxValue      - Max value defined for the field
     * @param inputFragment - user input string fragment
     * @return set of all possible valid values
     */
    private static Set<Integer> parseNumericOnlyFields(CronFieldType cronFieldType, int minValue, int maxValue, String inputFragment) {
        // Test All value pattern first
        Matcher matcher = RegexExpressions.ALL_VALUES_PATTERN.matcher(inputFragment);
        // If matches all value pattern
        if (matcher.matches()) {
            return FragmentHandler.handleAllValuePatternMatch(minValue, maxValue);
        }
        // if matches only integer pattern
        matcher = RegexExpressions.ONLY_NUMERIC_PATTERN.matcher(inputFragment);
        // If matches all value pattern
        if (matcher.matches()) {
            return Set.of(FragmentHandler.handleSingleNumericMatch(matcher.group(), minValue, maxValue));
        }
        // if matches only numeric range pattern
        matcher = RegexExpressions.ONLY_NUMERIC_RANGE_PATTERN.matcher(inputFragment);
        // If matches all value pattern
        if (matcher.matches()) {
            return FragmentHandler.handleNumericRangePatternMatch(matcher.group(1), matcher.group(2), minValue, maxValue);
        }
        // if matches only numeric step pattern
        matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher(inputFragment);
        if (matcher.matches()) {
            String start;
            String end;
            // parse the range portion of the step function
            if (Objects.equals("*", matcher.group(1))) {
                // If step start-end range is "*" (ALL values)
                start = String.valueOf(minValue);
                end = String.valueOf(maxValue);
            } else if (Objects.isNull(matcher.group(2))) {
                // Step range is single value
                start = matcher.group(1);
                end = String.valueOf(maxValue);
            } else {
                // Step range is numeric range
                start = matcher.group(2);
                end = matcher.group(3);
            }
            return FragmentHandler.handleStepPatternMatch(start, end, matcher.group(4), minValue, maxValue);
        }
        throw new IllegalArgumentException("Invalid input for field type " + cronFieldType.name() + " input " + inputFragment);
    }

    /**
     * Parse the input fragment and returns all possible allowed values within the min and max range, as a set of integers.
     * Applicable only for alphanumeric field only i.e. month and day of week
     * Performs following operations
     * <br/>
     * <b>Note: Uses multiple regex, instead of single. As this makes the code stable and less error prone due to indexing problems</b>
     * <br/>
     * <ul>
     *     <li>If input is '*' then returns all values in range (min and max) both including</li>
     *     <li>If input is single numeric value, return the value (if within the range)</li>
     *     <li>If input is range, returns all valid values within the range (both inclusive)</li>
     *     <li>If input is step function, returns values starting from min to max, incremented by step value</li>
     * </ul>
     *
     * @param cronFieldType - Type of cron field function is parsing
     * @param minValue      - Min value defined for the field
     * @param maxValue      - Max value defined for the field
     * @param inputFragment - user input string fragment
     * @return set of all possible valid values
     */
    private static Set<Integer> parseAlphanumericFields(CronFieldType cronFieldType, int minValue, int maxValue, String inputFragment) {
        // Test All value pattern first
        Matcher matcher = RegexExpressions.ALL_VALUES_PATTERN.matcher(inputFragment);
        if (matcher.matches()) {
            return FragmentHandler.handleAllValuePatternMatch(minValue, maxValue);
        }
        // if matches only integer pattern
        matcher = RegexExpressions.ONLY_NUMERIC_PATTERN.matcher(inputFragment);
        if (matcher.matches()) {
            return Set.of(FragmentHandler.handleSingleNumericMatch(matcher.group(), minValue, maxValue));
        }
        // if matches only single word pattern
        matcher = RegexExpressions.ONLY_ALPHABET_PATTERN.matcher(inputFragment);
        if (matcher.matches()) {
            return Set.of(FragmentHandler.handleOnlyAlphabetPatternMatch(cronFieldType, matcher.group(), minValue, maxValue));
        }
        // if matches only numeric range pattern
        matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher(inputFragment);
        if (matcher.matches()) {
            String start = matcher.group(1);
            String end = matcher.group(2);
            // if start and end are integer, return, else look up the integer value of the String passed
            String startInteger = RegexExpressions.NUMERIC_ONLY_SUB_ITEM.matcher(start).matches() ? start : NameToIntegerMap.getIntegerValue(cronFieldType, start.toLowerCase()).toString();
            String endInteger = RegexExpressions.NUMERIC_ONLY_SUB_ITEM.matcher(end).matches() ? end : NameToIntegerMap.getIntegerValue(cronFieldType, end.toLowerCase()).toString();
            return FragmentHandler.handleNumericRangePatternMatch(startInteger, endInteger, minValue, maxValue);
        }

        // if matches alphanumeric step pattern
        matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher(inputFragment);
        // If matches all value pattern
        if (matcher.matches()) {
            String start;
            String end;
            String step = matcher.group(4);
            // parse the range portion of the step function
            if (Objects.equals("*", matcher.group(1))) {
                // If step start-end range is "*" (ALL values)
                start = String.valueOf(minValue);
                end = String.valueOf(maxValue);
            } else if (Objects.isNull(matcher.group(2))) {
                // Step range is single value (handles numeric and word both)
                start = RegexExpressions.NUMERIC_ONLY_SUB_ITEM.matcher(matcher.group(1)).matches() ? matcher.group(1) : NameToIntegerMap.getIntegerValue(cronFieldType, matcher.group(1).toLowerCase()).toString();
                end = String.valueOf(maxValue);
            } else {
                // Step range is numeric range
                String startValue = matcher.group(2);
                String endValue = matcher.group(3);
                String stepValue = matcher.group(4);
                // if start and end are integer, return else look up the integer value of the String passed
                start = RegexExpressions.NUMERIC_ONLY_SUB_ITEM.matcher(startValue).matches() ? startValue : NameToIntegerMap.getIntegerValue(cronFieldType, startValue.toLowerCase()).toString();
                end = RegexExpressions.NUMERIC_ONLY_SUB_ITEM.matcher(endValue).matches() ? endValue : NameToIntegerMap.getIntegerValue(cronFieldType, endValue.toLowerCase()).toString();
                step = RegexExpressions.NUMERIC_ONLY_SUB_ITEM.matcher(stepValue).matches() ? stepValue : NameToIntegerMap.getIntegerValue(cronFieldType, stepValue.toLowerCase()).toString();
            }
            return FragmentHandler.handleStepPatternMatch(start, end, step, minValue, maxValue);
        }
        throw new IllegalArgumentException("Invalid input for field type " + cronFieldType.name() + " input " + inputFragment);
    }


}
