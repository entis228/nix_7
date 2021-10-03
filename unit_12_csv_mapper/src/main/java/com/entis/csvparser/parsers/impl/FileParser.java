package com.entis.csvparser.parsers.impl;

import com.entis.csvparser.components.Row;
import com.entis.csvparser.components.Table;
import com.entis.csvparser.exception.CSVParsingException;
import com.entis.csvparser.parsers.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public final class FileParser implements Parser<Path> {

    @Override
    public Table parse(Path source) throws CSVParsingException {
        try (BufferedReader reader = Files.newBufferedReader(source)) {
            String header = reader.readLine();
            if (header == null) {
                throw new CSVParsingException("Can't parse CSV, file is empty");
            }
            Table table = new Table(parseLine(header));
            String line;
            while ((line = reader.readLine()) != null) {
                table.addRow(parseLine(line));
            }
            return table;
        } catch (IOException e) {
            throw new CSVParsingException(e);
        }
    }

    private Row parseLine(String line) {
        return new Row(Arrays.asList(line.split(",")));
    }
}
