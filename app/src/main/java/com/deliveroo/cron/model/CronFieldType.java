package com.deliveroo.cron.model;

/**
 * Enum representing all Cron field types and their ranges
 *
 * @author nandip
 * @since 19-Jun-2022
 **/
public enum CronFieldType {
    MINUTE(0, 59),
    HOUR(0, 23),
    DAY_OF_MONTH(1, 31),
    MONTH(1, 12),
    DAY_OF_WEEK(1, 7);

    private int min;
    private int max;

    private CronFieldType(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Get min value of the Cron field range
     *
     * @return min value
     */
    public int getMin() {
        return min;
    }

    /**
     * Get max value of the Cron field range
     *
     * @return max value
     */
    public int getMax() {
        return max;
    }
}
