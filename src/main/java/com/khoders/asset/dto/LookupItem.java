package com.khoders.asset.dto;

import com.khoders.resource.utilities.MsgResolver;

public class LookupItem {
    private String id;
    private String itemName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
