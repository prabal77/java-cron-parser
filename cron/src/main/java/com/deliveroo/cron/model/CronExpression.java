package com.deliveroo.cron.model;

import java.util.Objects;
import java.util.StringJoiner;

import com.deliveroo.cron.util.ParserHelper;

/**
 * Immutable model class representing a valid Cron expression.
 * Fields list
 * <ul>
 *     <li>Minutes</li>
 *     <li>Hours</li>
 *     <li>Day of month</li>
 *     <li>Month</li>
 *     <li>Day of week</li>
 *     <li>Command to Execute</li>
 * </ul>
 *
 * @author nandip
 * @since 19-Jun-2022
 **/
public class CronExpression implements Cloneable {
    private final CronField minuteField;
    private final CronField hoursField;
    private final CronField dayOfMonthField;
    private final CronField monthField;
    private final CronField dayOfWeekField;
    private final String commandToExecute;

    private CronExpression(CronField minuteField, CronField hoursField, CronField dayOfMonthField,
                           CronField monthField, CronField dayOfWeekField, String commandToExecute) {
        this.minuteField = minuteField;
        this.hoursField = hoursField;
        this.dayOfMonthField = dayOfMonthField;
        this.monthField = monthField;
        this.dayOfWeekField = dayOfWeekField;
        this.commandToExecute = commandToExecute;
    }

    /**
     * Get instance of {@link CronExpressionBuilder}
     *
     * @return CronExpressionBuilder instance
     */
    public static CronExpressionBuilder builder() {
        return new CronExpressionBuilder();
    }

    /**
     * Get minute CronField
     *
     * @return minute field
     */
    public CronField getMinuteField() {
        return minuteField;
    }

    /**
     * Get hours CronField
     *
     * @return hours field
     */
    public CronField getHoursField() {
        return hoursField;
    }

    /**
     * Get day of minute CronField
     *
     * @return day of minute field
     */
    public CronField getDayOfMonthField() {
        return dayOfMonthField;
    }

    /**
     * Get month CronField
     *
     * @return month field
     */
    public CronField getMonthField() {
        return monthField;
    }

    /**
     * Get day of week CronField
     *
     * @return minute field
     */
    public CronField getDayOfWeekField() {
        return dayOfWeekField;
    }

    /**
     * Get Command to execute CronField
     *
     * @return Command to execute field
     */
    public String getCommandToExecute() {
        return commandToExecute;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CronExpression.class.getSimpleName() + "[", "]")
                .add("minuteField=" + minuteField)
                .add("hoursField=" + hoursField)
                .add("dayOfMonthField=" + dayOfMonthField)
                .add("monthField=" + monthField)
                .add("dayOfWeekField=" + dayOfWeekField)
                .add("commandToExecute='" + commandToExecute + "'")
                .toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning of the object is not supported");
    }

    /**
     * Builder class to create {@link CronExpression} instance
     */
    public static class CronExpressionBuilder {
        private String minuteField;
        private String hoursField;
        private String dayOfMonthField;
        private String monthField;
        private String dayOfWeekField;
        private String commandToExecute;

        /**
         * Set Minute {@link CronField}
         *
         * @param minuteField minute field String input
         * @return builder instance
         */
        public CronExpressionBuilder setMinuteField(String minuteField) {
            this.minuteField = minuteField;
            return this;
        }

        /**
         * Set Hours {@link CronField}
         *
         * @param hoursField hours field String input
         * @return builder instance
         */
        public CronExpressionBuilder setHoursField(String hoursField) {
            this.hoursField = hoursField;
            return this;
        }

        /**
         * Set Day of Month {@link CronField}
         *
         * @param dayOfMonthField day of month field String input
         * @return builder instance
         */
        public CronExpressionBuilder setDayOfMonthField(String dayOfMonthField) {
            this.dayOfMonthField = dayOfMonthField;
            return this;
        }

        /**
         * Set Month {@link CronField}
         *
         * @param monthField month field String input
         * @return builder instance
         */
        public CronExpressionBuilder setMonthField(String monthField) {
            this.monthField = monthField;
            return this;
        }

        /**
         * Set Day of Week {@link CronField}
         *
         * @param dayOfWeekField day of week field String input
         * @return builder instance
         */
        public CronExpressionBuilder setDayOfWeekField(String dayOfWeekField) {
            this.dayOfWeekField = dayOfWeekField;
            return this;
        }

        /**
         * Set Command to execute {@link CronField}
         *
         * @param commandToExecute command to execute string
         * @return builder instance
         */
        public CronExpressionBuilder setCommandToExecute(String commandToExecute) {
            this.commandToExecute = commandToExecute;
            return this;
        }

        /**
         * Builds and returns a new instance of {@link CronExpression}
         *
         * @return CronExpression
         * @throws IllegalArgumentException when input is invalid
         */
        public CronExpression build() {
            if (Objects.isNull(this.commandToExecute) || this.commandToExecute.isEmpty()) {
                throw new IllegalArgumentException("Command field cannot be null or empty");
            }
            return new CronExpression(
                    buildCronField(CronFieldType.MINUTE, this.minuteField),
                    buildCronField(CronFieldType.HOUR, this.hoursField),
                    buildCronField(CronFieldType.DAY_OF_MONTH, this.dayOfMonthField),
                    buildCronField(CronFieldType.MONTH, this.monthField),
                    buildCronField(CronFieldType.DAY_OF_WEEK, this.dayOfWeekField),
                    this.commandToExecute
            );
        }

        /**
         * Parse the Input String type and returns a {@link CronField} instance
         *
         * @param fieldType   Cron field type
         * @param inputString input string to parse
         * @return CronField
         * @throws IllegalArgumentException if input is invalid
         */
        private CronField buildCronField(CronFieldType fieldType, String inputString) {
            if (Objects.isNull(inputString) || inputString.isBlank()) {
                throw new IllegalArgumentException(fieldType.name() + " cron field is mandatory");
            }
            return new CronField(fieldType, ParserHelper.parseCronField(fieldType, inputString));
        }
    }
}
