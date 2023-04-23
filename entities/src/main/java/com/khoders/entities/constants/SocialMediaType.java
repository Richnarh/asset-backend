package com.khoders.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum SocialMediaType implements MsgResolver {
    FACEBOOK("FACEBOOK", "Facebook"),
    TWITTER("TWITTER", "Twitter"),
    LINKED_IN("LINKED_IN", "Linked In"),
    INSTAGRAM("INSTAGRAM", "Instagram");

    private final String code;
    private final String label;

    SocialMediaType(String code, String label) {
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
