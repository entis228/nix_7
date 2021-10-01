package com.entis.service.impl;

import com.entis.anottations.PropertyKey;
import com.entis.service.PropertyMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

public class PropertiesMapperImpl implements PropertyMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesMapperImpl.class);
    private static final String dateFormat="dd-MM-yyyy";

    private void logError(Exception e){
        LOGGER.info("Error: " + e.getMessage());
    }

    public <T> T map(Class<T> entityClass, Properties props) {
        try {
            T obj = Objects.requireNonNull(entityClass.getConstructor()).newInstance();
            for (Field field : entityClass.getDeclaredFields()) {

                if (field.isAnnotationPresent(PropertyKey.class)&&field.trySetAccessible()) {
                    PropertyKey propertyKey = field.getAnnotation(PropertyKey.class);
                    String getValueFromProperty = props.getProperty(propertyKey.value());

                    if (field.getType().equals(Integer.TYPE)) {
                        field.set(obj, Integer.parseInt(getValueFromProperty));
                    } else if (field.getType().equals(Long.TYPE)) {
                        field.set(obj, Long.parseLong(getValueFromProperty));
                    } else if (field.getType().equals(Float.TYPE)) {
                        field.set(obj, Float.parseFloat(getValueFromProperty));
                    } else if (field.getType().equals(Double.TYPE)) {
                        field.set(obj, Double.parseDouble(getValueFromProperty));
                    } else if (field.getType().equals(Boolean.TYPE)) {
                        field.set(obj, Boolean.parseBoolean(getValueFromProperty));
                    } else if (field.getType() == Date.class) {
                        try {
                            field.set(obj, new SimpleDateFormat(dateFormat).parse(getValueFromProperty));
                        } catch (ParseException e) {
                            logError(e);
                        }
                    } else if(field.getType()==String.class) {
                        field.set(obj, getValueFromProperty);
                    }else throw new RuntimeException("Can't parse type "+field.getType());
                }
            }
            return obj;
        } catch (IllegalAccessException | NumberFormatException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            logError(e);
            throw new RuntimeException(e);
        }
    }
}