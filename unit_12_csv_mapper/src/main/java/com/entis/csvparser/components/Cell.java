package com.entis.csvparser.components;

import java.util.Objects;

public final class Cell {

    private final String value;

    public Cell(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

    public int getInt() {
        return Integer.parseInt(value);
    }

    public long getLong() {
        return Long.parseLong(value);
    }

    public double getDouble() {
        return Double.parseDouble(value);
    }

    public boolean getBoolean() {
        return Boolean.parseBoolean(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return Objects.equals(value, cell.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "CSVCell{" +
                "value='" + value + '\'' +
                '}';
    }
}
