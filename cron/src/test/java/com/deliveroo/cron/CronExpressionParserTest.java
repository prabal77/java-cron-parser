package com.deliveroo.cron;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.deliveroo.cron.model.CronExpression;

/**
 * @author nandip
 * @since 20-Jun-2022
 **/
public class CronExpressionParserTest {

    @Test
    void testInvalidInput() {
        Assertions.assertEquals("Input cannot be null or empty string",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse(null)).getMessage());
        Assertions.assertEquals("Input cannot be null or empty string",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse(" ")).getMessage());
        Assertions.assertEquals("Incorrect number of arguments. Pattern is [* * * * * $command]. Input = a b",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse("a b")).getMessage());
        Assertions.assertEquals("Input $ for cron field type MINUTE contains illegal characters",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse("$ a b c d e f g h")).getMessage());
        Assertions.assertEquals("Input $ for cron field type DAY_OF_MONTH contains illegal characters",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse("12 * $ c d e f g h")).getMessage());
        Assertions.assertEquals("Value not in range. input = 2147483647 min = 1 max = 12",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse("12 * 12-3 c & e f g h")).getMessage());
    }

    @Test
    void testMinuteFieldValidations() {
        Assertions.assertEquals("Value not in range. input = 100 min = 0 max = 59",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse("100 * * * * /usr/find")).getMessage());
        Assertions.assertEquals("Invalid input for field type MINUTE input -1",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse("-1 * * * * /usr/find")).getMessage());
        Assertions.assertEquals("Invalid input for field type MINUTE input A",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse("A * * * * /usr/find")).getMessage());
        Assertions.assertEquals("Invalid input for field type MINUTE input 12-",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse("12- 13 * * * * /usr/find")).getMessage());
    }

    @Test
    void testMinuteField() {
        List<Integer> expectedOutput = List.of(12);
        CronExpression expression = CronExpressionParser.parse("12 * * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMinuteField().getInputValues());

        expectedOutput = List.of(12, 15, 17);
        expression = CronExpressionParser.parse("12,15,17 * * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMinuteField().getInputValues());

        expectedOutput = List.of(12, 15, 16, 17, 18, 19);
        expression = CronExpressionParser.parse("12,15-19 * * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMinuteField().getInputValues());

        expectedOutput = List.of(12, 15, 16, 17, 18, 19, 45, 55);
        expression = CronExpressionParser.parse("12,15-19,45/10 * * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMinuteField().getInputValues());

        expectedOutput = List.of(0, 10, 20, 30, 40, 50);
        expression = CronExpressionParser.parse("*/10 * * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMinuteField().getInputValues());
    }

    @Test
    void testHourField() {
        List<Integer> expectedOutput = List.of(12);
        CronExpression expression = CronExpressionParser.parse("1 12 * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getHoursField().getInputValues());

        expectedOutput = List.of(1, 3, 5);
        expression = CronExpressionParser.parse("1 1,3,5 * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getHoursField().getInputValues());

        expectedOutput = List.of(3, 5, 6, 7, 8, 9);
        expression = CronExpressionParser.parse("1 3,5-9 * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getHoursField().getInputValues());

        expectedOutput = List.of(3, 4, 5, 6, 7, 8, 9, 10, 11);
        expression = CronExpressionParser.parse("1 3-11,5-9 * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getHoursField().getInputValues());

        expectedOutput = List.of(2, 3, 4, 5, 6, 10, 14, 18, 22);
        expression = CronExpressionParser.parse("1 2,3-5,6/4 * * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getHoursField().getInputValues());

        expectedOutput = List.of(0, 5, 10, 15, 20);
        expression = CronExpressionParser.parse("1 */5 * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getHoursField().getInputValues());
    }

    @Test
    void testDayOfMonthField() {
        List<Integer> expectedOutput = List.of(12);
        CronExpression expression = CronExpressionParser.parse("1 1 12 * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfMonthField().getInputValues());

        expectedOutput = List.of(1, 3, 5);
        expression = CronExpressionParser.parse("1 1 1,3,5 * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfMonthField().getInputValues());

        expectedOutput = List.of(3, 5, 6, 7, 8, 9);
        expression = CronExpressionParser.parse("1 * 3,5-9 * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfMonthField().getInputValues());

        expectedOutput = List.of(3, 4, 5, 6, 7, 8, 9, 10, 11);
        expression = CronExpressionParser.parse("1 * 3-11,5-9 * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfMonthField().getInputValues());

        expectedOutput = List.of(2, 3, 4, 5, 6, 16, 26);
        expression = CronExpressionParser.parse("1 * 2,3-5,6/10 * * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfMonthField().getInputValues());

        expectedOutput = List.of(1, 11, 21, 31);
        expression = CronExpressionParser.parse("1 * */10 * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfMonthField().getInputValues());
    }

    @Test
    void testMonthField() {
        List<Integer> expectedOutput = List.of(12);
        CronExpression expression = CronExpressionParser.parse("* * * 12 * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());

        expectedOutput = List.of(1, 3, 5);
        expression = CronExpressionParser.parse("* * * 1,3,5 * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());

        expectedOutput = List.of(3, 5, 6, 7, 8, 9);
        expression = CronExpressionParser.parse("* * * 3,5-9 * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());

        expectedOutput = List.of(3, 4, 5, 6, 7, 8, 9, 10, 11);
        expression = CronExpressionParser.parse("* * * 3-11,5-9 * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());

        expectedOutput = List.of(2, 3, 4, 5, 6);
        expression = CronExpressionParser.parse("* * * 2,3-5,6/10 * * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());

        expectedOutput = List.of(1, 6, 11);
        expression = CronExpressionParser.parse("* * * */5 * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());

        expectedOutput = List.of(1, 2, 3);
        expression = CronExpressionParser.parse("* * * jan-mar * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());

        expectedOutput = List.of(1, 3, 5, 7, 9);
        expression = CronExpressionParser.parse("* * * jan-oct/2 * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());

        expectedOutput = List.of(1, 3, 5, 7, 9);
        expression = CronExpressionParser.parse("* * * jan-oct/feb * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());

        expectedOutput = List.of(1);
        expression = CronExpressionParser.parse("* * * jan-oct/dec * /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getMonthField().getInputValues());
    }

    @Test
    void testDayOfWeekField() {
        List<Integer> expectedOutput = List.of(7);
        CronExpression expression = CronExpressionParser.parse("* * * * 7 /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());

        Assertions.assertEquals("Value not in range. input = 0 min = 1 max = 7",
                Assertions.assertThrows(IllegalArgumentException.class, () -> CronExpressionParser.parse("* * * * 0 /usr/find")).getMessage());

        expectedOutput = List.of(1, 3, 5);
        expression = CronExpressionParser.parse("* * * * 1,3,5 /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());

        expectedOutput = List.of(3, 5, 6, 7);
        expression = CronExpressionParser.parse("* * * * 3,5-7 /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());

        expectedOutput = List.of(3, 4, 5, 6, 7);
        expression = CronExpressionParser.parse("* * * * 3-7,4-6 /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());

        expectedOutput = List.of(1, 2, 3, 4, 5, 7);
        expression = CronExpressionParser.parse("* * * * 2,3-5,*/2 /usr/find");

        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());

        expectedOutput = List.of(1, 4, 7);
        expression = CronExpressionParser.parse("* * * * */3 /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());

        expectedOutput = List.of(1, 2, 3, 4, 5);
        expression = CronExpressionParser.parse("* * * * mon-fri /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());

        expectedOutput = List.of(1, 3, 5, 7);
        expression = CronExpressionParser.parse("* * * * mon-sun/2 /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());

        expectedOutput = List.of(2, 4);
        expression = CronExpressionParser.parse("* * * * tue-fri/tue /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());

        expectedOutput = List.of(1);
        expression = CronExpressionParser.parse("* * * * mon-sun/sun /usr/find");
        Assertions.assertIterableEquals(expectedOutput, expression.getDayOfWeekField().getInputValues());
    }
}
