package com.deliveroo.cron.util;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.deliveroo.cron.model.CronFieldType;

/**
 * @author nandip
 * @since 19-Jun-2022
 **/
public class ParserHelperTest {

    @DisplayName("Test numeric patterns")
    @Nested
    class TestNumericFragments {

        @Test
        void testSingleInput() {
            List<Integer> expectedOutput = List.of(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55);
            List<Integer> out = ParserHelper.parseCronField(CronFieldType.MINUTE, "*/5");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(0);
            out = ParserHelper.parseCronField(CronFieldType.MINUTE, "*/100");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(5);
            out = ParserHelper.parseCronField(CronFieldType.MINUTE, "5/100");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(5);
            out = ParserHelper.parseCronField(CronFieldType.MONTH, "may/100");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(5);
            out = ParserHelper.parseCronField(CronFieldType.MONTH, "MAY/100");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59);
            out = ParserHelper.parseCronField(CronFieldType.MINUTE, "5/1");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(2, 5, 8);
            out = ParserHelper.parseCronField(CronFieldType.MONTH, "feb-oct/3");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(2);
            out = ParserHelper.parseCronField(CronFieldType.MONTH, "2-3/5");
            Assertions.assertIterableEquals(expectedOutput, out);
        }

        @DisplayName("Test step value all numeric")
        @Test
        void testStepValue() {
            List<Integer> expectedOutput = List.of(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55);
            List<Integer> out = ParserHelper.parseCronField(CronFieldType.MINUTE, "*/5");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(0);
            out = ParserHelper.parseCronField(CronFieldType.MINUTE, "*/100");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(5);
            out = ParserHelper.parseCronField(CronFieldType.MINUTE, "5/100");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59);
            out = ParserHelper.parseCronField(CronFieldType.MINUTE, "5/1");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(5, 10, 15, 20);
            out = ParserHelper.parseCronField(CronFieldType.MINUTE, "5-20/5");
            Assertions.assertIterableEquals(expectedOutput, out);

            expectedOutput = List.of(2);
            out = ParserHelper.parseCronField(CronFieldType.MINUTE, "2-3/5");
            Assertions.assertIterableEquals(expectedOutput, out);
        }
    }
}
