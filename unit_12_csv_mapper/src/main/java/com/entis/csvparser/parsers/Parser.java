package com.entis.csvparser.parsers;

import com.entis.csvparser.components.Table;
import com.entis.csvparser.exception.CSVParsingException;

public interface Parser<T> {

    Table parse(T source) throws CSVParsingException;
}
