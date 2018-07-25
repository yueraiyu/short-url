package com.yeay.shorturl.vo;

public class ChartsBaseDataVo {
    private String value;

    private String name;

    public ChartsBaseDataVo() {
    }

    public ChartsBaseDataVo(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
