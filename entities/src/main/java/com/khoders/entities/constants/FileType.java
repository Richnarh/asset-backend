package com.khoders.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum FileType implements MsgResolver {
    XLS("XLS", ".xls"),
    PDF("PDF", ".pdf"),
    DOC("DOC", ".doc"),
    DOCX("DOCX", ".docx"),
    PNG("PNG", ".png"),
    JPEG("JPEG", ".jpeg"),
    JPG("JPG", ".jpg");

    private final String code;
    private final String label;

    FileType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
