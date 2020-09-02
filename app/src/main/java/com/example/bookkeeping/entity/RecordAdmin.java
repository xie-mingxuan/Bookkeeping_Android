package com.example.bookkeeping.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RecordAdmin {
    private int optionType;
    private String username;
    private BigDecimal decimal;
    private String time;

    public int getOptionType() {
        return optionType;
    }

    public void setOptionType(int optionType) {
        this.optionType = optionType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
