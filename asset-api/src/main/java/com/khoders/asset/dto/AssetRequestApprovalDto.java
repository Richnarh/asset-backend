package com.khoders.asset.dto;

import java.time.LocalDate;

public class AssetRequestApprovalDto extends BaseDto {
    private String approvedByName;
    private String approvedById;
    private LocalDate approvalDate;
    private String assetRequestDispatch;
    private String assetRequestDispatchId;
    private String description;

    public String getApprovedByName() {
        return approvedByName;
    }

    public void setApprovedByName(String approvedByName) {
        this.approvedByName = approvedByName;
    }

    public String getApprovedById() {
        return approvedById;
    }

    public void setApprovedById(String approvedById) {
        this.approvedById = approvedById;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getAssetRequestDispatch() {
        return assetRequestDispatch;
    }

    public void setAssetRequestDispatch(String assetRequestDispatch) {
        this.assetRequestDispatch = assetRequestDispatch;
    }

    public String getAssetRequestDispatchId() {
        return assetRequestDispatchId;
    }

    public void setAssetRequestDispatchId(String assetRequestDispatchId) {
        this.assetRequestDispatchId = assetRequestDispatchId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
