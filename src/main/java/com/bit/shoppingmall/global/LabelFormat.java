package com.bit.shoppingmall.global;

public enum LabelFormat {
    PREFIX("/WEB-INF/views/"),
    SUFFIX(".jsp");

    private final String label;

    public String label() {
        return label;
    }

    LabelFormat(String label) {
        this.label = label;
    }
}
