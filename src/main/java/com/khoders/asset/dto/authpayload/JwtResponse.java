package com.khoders.asset.dto.authpayload;

import com.khoders.asset.dto.BaseDto;

import java.util.Set;

public class JwtResponse extends BaseDto {
    private String accessToken;
    private String refreshToken;
    private String type = "Bearer";
    private Set<RoleDto> roleList;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<RoleDto> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<RoleDto> roleList) {
        this.roleList = roleList;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
