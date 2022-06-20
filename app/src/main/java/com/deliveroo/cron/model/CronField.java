package com.deliveroo.cron.model;

import java.util.List;
import java.util.StringJoiner;

/**
 * Class representing individual field of Cron expression. e.g. Minute Field, Day or week field
 *
 * @author nandip
 * @since 19-Jun-2022
 **/
public class CronField {
    private final CronFieldType fieldType;
    private final List<Integer> inputValues;

    public CronField(CronFieldType fieldType, List<Integer> inputValues) {
        this.fieldType = fieldType;
        this.inputValues = inputValues;
    }

    public List<Integer> getInputValues() {
        return inputValues;
    }

    public CronFieldType getFieldType() {
        return fieldType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CronField.class.getSimpleName() + "[", "]")
                .add("inputValues=" + inputValues)
                .add("fieldType=" + fieldType)
                .toString();
    }
}
