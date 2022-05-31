package com.khoders.asset.dto.authpayload;

import com.khoders.asset.dto.BaseDto;
import com.khoders.asset.dto.CompanyDto;

import java.util.List;
import java.util.Set;

public class JwtResponse extends BaseDto {
    private String accessToken;
    private String refreshToken;
    private String expiryDate;
    private String authType = "Bearer";
    private Set<RoleDto> roleList;
    private List<CompanyDto> companyList;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<CompanyDto> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<CompanyDto> companyList) {
        this.companyList = companyList;
    }
}
