package com.example.bookkeeping.entity;

import java.math.BigDecimal;

public class RecordUser {
    private BigDecimal decimal;
    private String time;

    public BigDecimal getDecimal() {
        return decimal;
    }

    public void setDecimal(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
