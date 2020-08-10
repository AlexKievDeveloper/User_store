package com.glushkov.dao.jdbc.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class QueryGenerator {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String findAll(Class<?> clazz) {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");

        String tableName = getTableName(clazz);

        StringJoiner stringJoiner = new StringJoiner(", ");

        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);

            if (columnAnnotation != null) {
                String columnName = columnAnnotation.name().isEmpty() ? declaredField.getName() : columnAnnotation.name();
                stringJoiner.add(columnName);
            }
        }

        stringBuilder.append(stringJoiner).append(" FROM ").append(tableName).append(";");

        return stringBuilder.toString();
    }

    public String save(Object value) {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");

        String tableName = getTableName(value.getClass());

        Map<String, Object> columnNameToValueMap = getFieldsValues(value);

        StringBuilder columnsBuilder = new StringBuilder();
        columnsBuilder.append(" (");

        StringBuilder valuesBuilder = new StringBuilder();
        valuesBuilder.append("VALUES (");

        for (Map.Entry<String, Object> columnNameWithValue : columnNameToValueMap.entrySet()) {

            String columnName = columnNameWithValue.getKey();
            columnsBuilder.append(columnName).append(", ");

            Object fieldValue = columnNameWithValue.getValue();
            valuesBuilder.append(fieldValue).append(", ");
        }

        String columns = columnsBuilder.substring(0, columnsBuilder.length() - ", ".length());
        String values = valuesBuilder.substring(0, valuesBuilder.length() - ", ".length());

        stringBuilder.append(tableName).append(columns).append(") ").append(values).append(")").append(";");

        return stringBuilder.toString();
    }

    public String update(Object value) {
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");

        String tableName = getTableName(value.getClass());

        Map<String, Object> columnNameToValueMap = getFieldsValues(value);

        Map<String, Object> primaryKeyMap = getPrimaryKey(value);

        StringJoiner stringJoiner = new StringJoiner(", ");

        String primaryKeyName = null;

        Object primaryKeyValue = null;

        for (Map.Entry<String, Object> columnNameWithValue : columnNameToValueMap.entrySet()) {
            String columnName = columnNameWithValue.getKey();
            Object fieldValue = columnNameWithValue.getValue();
            stringJoiner.add(columnName + " = " + fieldValue);
        }

        for (Map.Entry<String, Object> primaryKeyEntry : primaryKeyMap.entrySet()) {
            primaryKeyName = primaryKeyEntry.getKey();
            primaryKeyValue = primaryKeyEntry.getValue();
        }

        stringBuilder.append(tableName).append(" SET ").append(stringJoiner).append(" WHERE ");
        stringBuilder.append(primaryKeyName).append(" = ").append("'").append(primaryKeyValue).append("'").append(";");

        return stringBuilder.toString();
    }

    public String findByID(Class<?> clazz, Object id) {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");

        String tableName = getTableName(clazz);

        StringJoiner stringJoiner = new StringJoiner(", ");
        String primaryKeyName = "";

        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);
            Id idAnnotation = declaredField.getAnnotation(Id.class);

            if (columnAnnotation != null) {
                String columnName = columnAnnotation.name().isEmpty() ? declaredField.getName() : columnAnnotation.name();
                stringJoiner.add(columnName);
            }
            if (idAnnotation != null) {
                primaryKeyName = columnAnnotation.name().isEmpty() ? declaredField.getName() : columnAnnotation.name();
            }
        }

        stringBuilder.append(stringJoiner).append(" FROM ").append(tableName).append(" WHERE ");
        stringBuilder.append(primaryKeyName).append(" = ").append("'").append(id).append("'").append(";");

        return stringBuilder.toString();
    }

    public String findByName(Class<?> clazz, String name) {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");

        String tableName = getTableName(clazz);

        StringJoiner stringJoiner = new StringJoiner(", ");

        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);

            if (columnAnnotation != null) {
                String columnName = columnAnnotation.name().isEmpty() ? declaredField.getName() : columnAnnotation.name();
                stringJoiner.add(columnName);
            }
        }

        stringBuilder.append(stringJoiner).append(" FROM ").append(tableName).append(" WHERE ").append("firstname");
        stringBuilder.append(" ILIKE ").append("'").append(name).append("%'").append(";");

        return stringBuilder.toString();
    }

    public String delete(Class<?> clazz, Object id) {

        StringBuilder stringBuilder = new StringBuilder("DELETE FROM ");

        String tableName = getTableName(clazz);

        String primaryKeyName = "";

        for (Field declaredField : clazz.getDeclaredFields()) {

            Column columnAnnotation = declaredField.getAnnotation(Column.class);
            Id idAnnotation = declaredField.getAnnotation(Id.class);

            if (idAnnotation != null) {
                primaryKeyName = columnAnnotation.name().isEmpty() ? declaredField.getName() : columnAnnotation.name();
            }
        }

        stringBuilder.append(tableName).append(" WHERE ").append(primaryKeyName).append(" = ");
        stringBuilder.append("'").append(id).append("'").append(";");

        return stringBuilder.toString();
    }

    static String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation == null) {
            throw new IllegalArgumentException("@Table is missing");
        }

        return annotation.name().isEmpty() ? clazz.getName() : annotation.name();
    }

    static Map<String, Object> getFieldsValues(Object value) {
        try {
            Map<String, Object> columnNameToValueMap = new HashMap<>();

            for (Field field : value.getClass().getDeclaredFields()) {

                StringBuilder valueBuilder = new StringBuilder();

                Column columnAnnotation = field.getAnnotation(Column.class);

                Id idAnnotation = field.getAnnotation(Id.class);

                if (columnAnnotation != null && idAnnotation == null) {
                    field.setAccessible(true);

                    String columnName = columnAnnotation.name().isEmpty() ? field.getName() : columnAnnotation.name();

                    Object fieldValue = field.get(value);

                    if (fieldValue == null) {
                        throw new RuntimeException("Field value is: null. " + "Field:" + field);
                    }

                    if (fieldValue instanceof CharSequence || fieldValue instanceof LocalDate) {
                        valueBuilder.append("'").append(fieldValue).append("'");
                    } else {
                        valueBuilder.append(fieldValue);
                    }
                    columnNameToValueMap.put(columnName, valueBuilder.toString());
                }
            }
            return columnNameToValueMap;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Error while getting values from object fields.", e);
        }
    }

    Map<String, Object> getPrimaryKey(Object value) {
        try {
            for (Field declaredField : value.getClass().getDeclaredFields()) {
                Column columnAnnotation = declaredField.getAnnotation(Column.class);
                Id idAnnotation = declaredField.getAnnotation(Id.class);

                declaredField.setAccessible(true);

                if (idAnnotation != null && columnAnnotation != null) {
                    String primaryKeyColumnName = columnAnnotation.name().isEmpty() ? declaredField.getName() : columnAnnotation.name();
                    Object primaryKeyValue;

                    primaryKeyValue = declaredField.get(value);

                    Map<String, Object> primaryKeyMap = new HashMap<>();
                    primaryKeyMap.put(primaryKeyColumnName, primaryKeyValue);
                    return primaryKeyMap;
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("Error while getting primary key field value", e);
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("There is no primary key specified");
    }
}


