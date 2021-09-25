package com.entis.csvparser.mappers;

import com.entis.csvparser.components.Table;
import com.entis.csvparser.exception.CSVMappingException;

import java.util.List;

public interface Mapper {

    <T> List<T> map(Table table, Class<T> type) throws CSVMappingException;

}
