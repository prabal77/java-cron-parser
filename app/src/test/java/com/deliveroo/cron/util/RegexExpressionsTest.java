package com.deliveroo.cron.util;

import java.util.regex.Matcher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author nandip
 * @since 19-Jun-2022
 **/
@DisplayName("Test all regex patterns")
public class RegexExpressionsTest {

    @DisplayName("Test all value pattern")
    @Test
    void testPatternAllValues() {
        Matcher matcher = RegexExpressions.ALL_VALUES_PATTERN.matcher("*");
        Assertions.assertTrue(matcher.matches());
        Assertions.assertEquals(0, matcher.groupCount());
        Assertions.assertEquals("*", matcher.group());
        matcher = RegexExpressions.ALL_VALUES_PATTERN.matcher("?");
        Assertions.assertFalse(matcher.matches());
    }



    @DisplayName("Test step pattern with alphanumeric range")
    @Test
    void testPatternStepWithAlphabetNumericRange() {
        Matcher matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("11-abc/xyz");
        Assertions.assertTrue(matcher.matches());
        Assertions.assertEquals(4, matcher.groupCount());
        Assertions.assertEquals("11-abc", matcher.group(1));
        Assertions.assertEquals("11", matcher.group(2));
        Assertions.assertEquals("abc", matcher.group(3));
        Assertions.assertEquals("xyz", matcher.group(4));
        matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("aaa-12/4");
        Assertions.assertTrue(matcher.matches());
        Assertions.assertEquals(4, matcher.groupCount());
        Assertions.assertEquals("aaa-12", matcher.group(1));
        Assertions.assertEquals("aaa", matcher.group(2));
        Assertions.assertEquals("12", matcher.group(3));
        Assertions.assertEquals("4", matcher.group(4));
        matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("12-12/qqqq");
        Assertions.assertTrue(matcher.matches());
        matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("-a/e");
        Assertions.assertFalse(matcher.matches());
        matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("-a/e");
        Assertions.assertFalse(matcher.matches());
        matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("-/qqqq");
        Assertions.assertFalse(matcher.matches());
        matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("a-/wer");
        Assertions.assertFalse(matcher.matches());
        matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("a!-&/34w");
        Assertions.assertFalse(matcher.matches());
    }

    @DisplayName("Test alphanumeric patterns")
    @Nested
    class TestAlphanumericPatterns {

        @DisplayName("Test only alphabet pattern")
        @Test
        void testPatternOnlyAlphabets() {
            Matcher matcher = RegexExpressions.ONLY_ALPHABET_PATTERN.matcher("jan");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(0, matcher.groupCount());
            Assertions.assertEquals("jan", matcher.group());
            matcher = RegexExpressions.ONLY_ALPHABET_PATTERN.matcher("Feb");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals("Feb", matcher.group());
            matcher = RegexExpressions.ONLY_ALPHABET_PATTERN.matcher("Fe%");
            Assertions.assertFalse(matcher.matches());
        }

        @DisplayName("Test alphabet pattern range")
        @Test
        void testPatternAlphaNumericRange() {
            Matcher matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("jan-Feb");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(2, matcher.groupCount());
            Assertions.assertEquals("jan", matcher.group(1));
            Assertions.assertEquals("Feb", matcher.group(2));
            matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("sun-mon");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals("sun", matcher.group(1));
            Assertions.assertEquals("mon", matcher.group(2));
            matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("12-13");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals("12", matcher.group(1));
            Assertions.assertEquals("13", matcher.group(2));
            matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("sun-monday");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals("sun", matcher.group(1));
            Assertions.assertEquals("monday", matcher.group(2));
            matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("sunday");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("s)nday");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("sun-ar8");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("-AB");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("jan -feb");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_RANGE_PATTERN.matcher("jan-feb ");
            Assertions.assertFalse(matcher.matches());
        }

        @DisplayName("Test step pattern with alphabets only")
        @Test
        void testPatternStepWithOnlyAlphabets() {
            Matcher matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("jan/gOP");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(4, matcher.groupCount());
            Assertions.assertEquals("jan", matcher.group(1));
            Assertions.assertNull(matcher.group(2));
            Assertions.assertNull(matcher.group(3));
            Assertions.assertEquals("gOP", matcher.group(4));
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("jan/");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("/Feb");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("/");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("Jan1/gOP");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("gOP/A$");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("gOP /gOP");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("abc/efg ");
            Assertions.assertFalse(matcher.matches());
        }

        @DisplayName("Test alphanumeric step pattern with integer only range")
        @Test
        void testPatternStepWithIntegerOnlyRange() {
            Matcher matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("12-2/23");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(4, matcher.groupCount());
            Assertions.assertEquals("12-2", matcher.group(1));
            Assertions.assertEquals("12", matcher.group(2));
            Assertions.assertEquals("2", matcher.group(3));
            Assertions.assertEquals("23", matcher.group(4));
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("01-13/12");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(4, matcher.groupCount());
            Assertions.assertEquals("01-13", matcher.group(1));
            Assertions.assertEquals("01", matcher.group(2));
            Assertions.assertEquals("13", matcher.group(3));
            Assertions.assertEquals("12", matcher.group(4));
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("12-123/12");
            Assertions.assertTrue(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("-12/12");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("-/23");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("12-/34");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("12-&/34");
            Assertions.assertFalse(matcher.matches());
        }

        @DisplayName("Test step pattern with alphabet only range")
        @Test
        void testPatternStepWithAlphabetOnlyRange() {
            Matcher matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("eee-efg/xyz");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(4, matcher.groupCount());
            Assertions.assertEquals("eee-efg", matcher.group(1));
            Assertions.assertEquals("eee", matcher.group(2));
            Assertions.assertEquals("efg", matcher.group(3));
            Assertions.assertEquals("xyz", matcher.group(4));
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("jan-Feb/12");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(4, matcher.groupCount());
            Assertions.assertEquals("jan-Feb", matcher.group(1));
            Assertions.assertEquals("jan", matcher.group(2));
            Assertions.assertEquals("Feb", matcher.group(3));
            Assertions.assertEquals("12", matcher.group(4));
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("jan-33/12");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(4, matcher.groupCount());
            Assertions.assertEquals("jan-33", matcher.group(1));
            Assertions.assertEquals("jan", matcher.group(2));
            Assertions.assertEquals("33", matcher.group(3));
            Assertions.assertEquals("12", matcher.group(4));
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("-a/e");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("-/qqqq");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("a-/wer");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ALPHANUMERIC_STEP_PATTERN.matcher("a!-&/34w");
            Assertions.assertFalse(matcher.matches());
        }
    }

    @DisplayName("Test numeric only patterns")
    @Nested
    class TestNumericOnlyPatterns {
        @DisplayName("Test only integer pattern")
        @Test
        void testPatternOnlyIntegers() {
            Matcher matcher = RegexExpressions.ONLY_NUMERIC_PATTERN.matcher("12");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(0, matcher.groupCount());
            Assertions.assertEquals("12", matcher.group());
            matcher = RegexExpressions.ONLY_NUMERIC_PATTERN.matcher("1");
            Assertions.assertTrue(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_PATTERN.matcher("123");
            Assertions.assertTrue(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_PATTERN.matcher("12A1");
            Assertions.assertFalse(matcher.matches());
        }

        @DisplayName("Test integer pattern range")
        @Test
        void testPatternIntegerRange() {
            Matcher matcher = RegexExpressions.ONLY_NUMERIC_RANGE_PATTERN.matcher("1-2");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(2, matcher.groupCount());
            Assertions.assertEquals("1", matcher.group(1));
            Assertions.assertEquals("2", matcher.group(2));
            matcher = RegexExpressions.ONLY_NUMERIC_RANGE_PATTERN.matcher("11-33");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals("11", matcher.group(1));
            Assertions.assertEquals("33", matcher.group(2));
            matcher = RegexExpressions.ONLY_NUMERIC_RANGE_PATTERN.matcher("12-123");
            Assertions.assertTrue(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_RANGE_PATTERN.matcher("12");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_RANGE_PATTERN.matcher("-");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_RANGE_PATTERN.matcher("12-");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_RANGE_PATTERN.matcher("12-A");
            Assertions.assertFalse(matcher.matches());
        }

        @DisplayName("Test step pattern with integers only")
        @Test
        void testPatternStepWithOnlyInteger() {
            Matcher matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("*/2");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(4, matcher.groupCount());
            Assertions.assertEquals("*", matcher.group(1));
            Assertions.assertNull(matcher.group(2));
            Assertions.assertNull(matcher.group(3));
            Assertions.assertEquals("2", matcher.group(4));
            matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("12/2");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(4, matcher.groupCount());
            Assertions.assertEquals("12", matcher.group(1));
            Assertions.assertNull(matcher.group(2));
            Assertions.assertNull(matcher.group(3));
            Assertions.assertEquals("2", matcher.group(4));
            matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("12-30/2");
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals(4, matcher.groupCount());
            Assertions.assertEquals("12-30", matcher.group(1));
            Assertions.assertEquals("12", matcher.group(2));
            Assertions.assertEquals("30", matcher.group(3));
            Assertions.assertEquals("2", matcher.group(4));
            matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("12/");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("/2");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("/");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("12A/2");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("12/A$");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("12 /2");
            Assertions.assertFalse(matcher.matches());
            matcher = RegexExpressions.ONLY_NUMERIC_STEP_PATTERN.matcher("12/2 ");
            Assertions.assertFalse(matcher.matches());
        }

    }

}
