package com.khoders.asset.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "asset_request_approval")
public class AssetRequestApproval extends Ref{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(255)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    @ManyToOne
    private Employee approvedBy;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @JoinColumn(name = "asset_request_dispatch", referencedColumnName = "id")
    @ManyToOne
    private AssetDispatchRequest assetRequestDispatch;

    @Column(name = "description")
    @Lob
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public AssetDispatchRequest getAssetRequestDispatch() {
        return assetRequestDispatch;
    }

    public void setAssetRequestDispatch(AssetDispatchRequest assetRequestDispatch) {
        this.assetRequestDispatch = assetRequestDispatch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
