package com.entis.service;

import java.util.Properties;

public interface PropertyMapper {
    <T> T map(Class<T> entityClass, Properties properties);
}
