package com.entis.triangle;

import java.math.BigDecimal;

public class TriangleArea {

    public static BigDecimal getArea(Point t1, Point t2, Point t3) {
        BigDecimal formulaPart1It1 = new BigDecimal(t1.getX() - t3.getX());
        BigDecimal formulaPart1It2 = new BigDecimal(t2.getX() - t3.getX());
        BigDecimal formulaPart1It3 = new BigDecimal(t1.getY() - t3.getY());
        BigDecimal formulaPart1It4 = new BigDecimal(t2.getY() - t3.getY());
        BigDecimal mul1 = formulaPart1It1.multiply(formulaPart1It4);
        BigDecimal mul2 = formulaPart1It2.multiply(formulaPart1It3);
        BigDecimal result = mul2.subtract(mul1).divide(new BigDecimal(2));
        if (result.compareTo(new BigDecimal(0)) < 0) {
            result = result.multiply(new BigDecimal(-1));
        }
        return result;
    }
}
