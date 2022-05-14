package com.khoders.asset.dto.maintenance;

import com.khoders.asset.dto.BaseDto;

public class RequestTypeDto extends BaseDto {
    private String requestName;
    private String description;

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
