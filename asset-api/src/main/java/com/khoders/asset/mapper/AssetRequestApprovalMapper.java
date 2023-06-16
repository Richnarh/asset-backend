package com.khoders.asset.mapper;

import com.khoders.asset.dto.AssetRequestApprovalDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.AssetRequestApproval;
import com.khoders.entities.Employee;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetRequestApprovalMapper {
    @Autowired
    private AppService appService;

    public AssetRequestApproval toEntity(AssetRequestApprovalDto dto) throws Exception {
        AssetRequestApproval requestApproval = new AssetRequestApproval();
        if (dto.getId() != null) {
            requestApproval.setId(dto.getId());
        }
        requestApproval.setApprovalDate(dto.getApprovalDate());
        if (dto.getApprovedById() == null || dto.getApprovedById().equals("")) {
            throw new DataNotFoundException("Specify Valid ApprovedById - (employeeId)");
        }
        Employee employee = appService.findById(Employee.class, dto.getApprovedById());
        if (employee != null) {
            requestApproval.setApprovedBy(employee);
        }
        requestApproval.setDescription(dto.getDescription());
        requestApproval.setRefNo(requestApproval.getRefNo());
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
