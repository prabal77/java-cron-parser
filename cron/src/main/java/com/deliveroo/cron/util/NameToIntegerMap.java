package com.deliveroo.cron.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.deliveroo.cron.model.CronFieldType;

/**
 * Maps word to integer. Used as a look up table to resolve month and week day string to integer equivalent
 *
 * @author nandip
 * @since 19-Jun-2022
 **/
public class NameToIntegerMap {
    private static final Map<String, Integer> monthNameMap = new HashMap<>();
    private static final Map<String, Integer> dayOfWeekNameMap = new HashMap<>();

    static {
        // jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, or dec
        monthNameMap.put("jan", 1);
        monthNameMap.put("feb", 2);
        monthNameMap.put("mar", 3);
        monthNameMap.put("apr", 4);
        monthNameMap.put("may", 5);
        monthNameMap.put("jun", 6);
        monthNameMap.put("jul", 7);
        monthNameMap.put("aug", 8);
        monthNameMap.put("sep", 9);
        monthNameMap.put("oct", 10);
        monthNameMap.put("nov", 11);
        monthNameMap.put("dec", 12);

        // mon, tue, wed, thu, fri, sat, sun
        dayOfWeekNameMap.put("mon", 1);
        dayOfWeekNameMap.put("tue", 2);
        dayOfWeekNameMap.put("wed", 3);
        dayOfWeekNameMap.put("thu", 4);
        dayOfWeekNameMap.put("fri", 5);
        dayOfWeekNameMap.put("sat", 6);
        dayOfWeekNameMap.put("sun", 7);
    }

    /**
     * Returns integer equivalent of the input word. Returns Integer.MAX_VALUE if fieldStringValue is not present in cache
     *
     * @param fieldType Field Type
     * @param fieldStringValue String value to resolve
     * @return integer equivalent of the fieldStringValue
     */
    public static Integer getIntegerValue(CronFieldType fieldType, String fieldStringValue) {
        if (Objects.equals(CronFieldType.MONTH, fieldType)) {
            return monthNameMap.getOrDefault(fieldStringValue, Integer.MAX_VALUE);
        } else if (Objects.equals(CronFieldType.DAY_OF_WEEK, fieldType)) {
            return dayOfWeekNameMap.getOrDefault(fieldStringValue, Integer.MAX_VALUE);
        }
        return Integer.MAX_VALUE;
    }
}
