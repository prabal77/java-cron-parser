package com.deliveroo.cron.util;

import java.util.regex.Pattern;

/**
 * Holds instances of all Regex Pattern classes
 *
 * @author nandip
 * @since 19-Jun-2022
 **/
public class RegexExpressions {
    // Matches only character '*' - single value
    private static final String ALL_VALUES_REGEX = "\\*";
    // Placeholder to match entire string
    private static final String MATCH_WHOLE_STRING_FORMAT = "^%s$";

    // Only numeric field patterns
    // Matches only numerics [0-9] - one or more
    private static final String ONLY_NUMERIC_REGEX = "\\d+";
    // Matches only range all numeric [0-9]-[0-9]: Group 1 (first number) Group 2 (second number)
    private static final String ONLY_NUMERIC_RANGE_REGEX = String.format("(%s)\\-(%s)", ONLY_NUMERIC_REGEX, ONLY_NUMERIC_REGEX);
    // Matches "*" or "single number" or "number range" and returns as a group
    private static final String ONLY_NUMERIC_STEP_RANGE_REGEX = String.format("(%s|%s|%s)", ALL_VALUES_REGEX, ONLY_NUMERIC_REGEX,
            ONLY_NUMERIC_RANGE_REGEX);
    // Matches step value and return as a group - Only numeric
    private static final String ONLY_NUMERIC_STEP_VALUE_REGEX = String.format("(%s)", ONLY_NUMERIC_REGEX);
    // Matches step pattern (range/step) - Only numeric
    private static final String ONLY_NUMERIC_STEP_PATTERN_REGEX = String.format("%s\\/%s", ONLY_NUMERIC_STEP_RANGE_REGEX, ONLY_NUMERIC_STEP_VALUE_REGEX);

    // Alphanumeric patterns
    // Matches only alphabets [a-zA-Z] - one or more
    private static final String ONLY_ALPHABETS_REGEX = "[a-zA-Z]+";
    // Matches only range all alphanumeric [a-zA-Z|0-9]-[a-zA-Z|0-9]: Group 1 (first word or number) Group 2 (second word or number)
    private static final String ALPHANUMERIC_RANGE_REGEX = String.format("(%s|%s)\\-(%s|%s)", ONLY_NUMERIC_REGEX, ONLY_ALPHABETS_REGEX,
            ONLY_NUMERIC_REGEX, ONLY_ALPHABETS_REGEX);
    // Matches "*" or "single number" or "single word" or "number range" or "alphanumeric range" and returns as a group
    private static final String ALPHANUMERIC_STEP_RANGE_REGEX = String.format("(%s|%s|%s|%s)", ALL_VALUES_REGEX, ONLY_NUMERIC_REGEX,
            ONLY_ALPHABETS_REGEX, ALPHANUMERIC_RANGE_REGEX);
    // Matches step value and return as a group - Alphanumeric
    private static final String ALPHANUMERIC_STEP_VALUE_REGEX = String.format("(%s|%s)", ONLY_NUMERIC_REGEX, ONLY_ALPHABETS_REGEX);
    // Matches step pattern (range/step) - Alphanumeric
    private static final String ALPHANUMERIC_STEP_PATTERN_REGEX = String.format("%s\\/%s", ALPHANUMERIC_STEP_RANGE_REGEX, ALPHANUMERIC_STEP_VALUE_REGEX);


    // All complied pattern classes
    public static final Pattern ALL_VALUES_PATTERN = Pattern.compile(String.format(MATCH_WHOLE_STRING_FORMAT, ALL_VALUES_REGEX));
    public static final Pattern ONLY_NUMERIC_PATTERN = Pattern.compile(String.format(MATCH_WHOLE_STRING_FORMAT, ONLY_NUMERIC_REGEX));
    public static final Pattern ONLY_NUMERIC_RANGE_PATTERN = Pattern.compile(String.format(MATCH_WHOLE_STRING_FORMAT, ONLY_NUMERIC_RANGE_REGEX));
    public static final Pattern ONLY_NUMERIC_STEP_PATTERN = Pattern.compile(String.format(MATCH_WHOLE_STRING_FORMAT, ONLY_NUMERIC_STEP_PATTERN_REGEX));

    // Alphanumeric field patterns
    public static final Pattern ONLY_ALPHABET_PATTERN = Pattern.compile(String.format(MATCH_WHOLE_STRING_FORMAT, ONLY_ALPHABETS_REGEX));
    public static final Pattern ALPHANUMERIC_RANGE_PATTERN = Pattern.compile(String.format(MATCH_WHOLE_STRING_FORMAT, ALPHANUMERIC_RANGE_REGEX));
    public static final Pattern ALPHANUMERIC_STEP_PATTERN = Pattern.compile(String.format(MATCH_WHOLE_STRING_FORMAT, ALPHANUMERIC_STEP_PATTERN_REGEX));

    // Matches any number (sub element)
    public static final Pattern NUMERIC_ONLY_SUB_ITEM = Pattern.compile(ONLY_NUMERIC_REGEX);
}
