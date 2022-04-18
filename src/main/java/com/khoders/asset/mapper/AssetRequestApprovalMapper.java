package com.khoders.asset.mapper;

import com.khoders.asset.dto.AssetRequestApprovalDto;
import com.khoders.asset.entities.AssetRequestApproval;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.utils.CrudBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AssetRequestApprovalMapper {
    private static final Logger log = LoggerFactory.getLogger(AssetRequestApprovalMapper.class);
    @Autowired
    private CrudBuilder builder;

    public AssetRequestApproval toEntity(AssetRequestApprovalDto dto) {
        AssetRequestApproval requestApproval = new AssetRequestApproval();
        if (dto.getId() != null) {
            requestApproval.setId(dto.getId());
        }
        requestApproval.setApprovalDate(dto.getApprovalDate());
        Employee employee = builder.findOne(dto.getApprovedById(), Employee.class);
        if (employee != null) {
            requestApproval.setApprovedBy(employee);
        }
        requestApproval.setDescription(dto.getDescription());
        requestApproval.setRefNo(requestApproval.getRefNo());
        requestApproval.setLastModifiedDate(LocalDateTime.now());
        return requestApproval;
    }

    public AssetRequestApprovalDto toEntity(AssetRequestApproval requestApproval) {
        AssetRequestApprovalDto dto = new AssetRequestApprovalDto();
        if (requestApproval.getId() == null) {
            return null;
        }
        dto.setId(requestApproval.getId());
        dto.setApprovalDate(requestApproval.getApprovalDate());
        dto.setDescription(requestApproval.getDescription());
        if (requestApproval.getApprovedBy() != null) {
            dto.setApprovedById(requestApproval.getApprovedBy().getId());
            dto.setApprovedByName(requestApproval.getApprovedBy().getEmailAddress());
        }
        return dto;
    }
}
