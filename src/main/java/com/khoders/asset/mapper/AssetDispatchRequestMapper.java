package com.khoders.asset.mapper;

import com.khoders.asset.dto.AssetDispatchRequestDto;
import com.khoders.asset.entities.AssetDispatchRequest;
import com.khoders.asset.entities.Department;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.entities.InventoryItem;
import com.khoders.asset.entities.constants.ApprovalStatus;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetDispatchRequestMapper {
    private static final Logger log = LoggerFactory.getLogger(AssetDispatchRequestMapper.class);
    @Autowired
    private CrudBuilder builder;

    public AssetDispatchRequest toEntity(AssetDispatchRequestDto dto) {
        AssetDispatchRequest dispatchRequest = new AssetDispatchRequest();
        if (dto.getId() != null) {
            dispatchRequest.setId(dto.getId());
        }
        dispatchRequest.setRefNo(dispatchRequest.getRefNo());
        dispatchRequest.setQuantity(dto.getQuantity());
        try {
            dispatchRequest.setApprovalStatus(ApprovalStatus.valueOf(dto.getApprovalStatus()));
        } catch (Exception ignored) {
        }
        if (dto.getDepartmentId() == null) {
            throw new DataNotFoundException("Please Specify Valid DepartmentId");
        }
        if (dto.getReceivedById() == null) {
            throw new DataNotFoundException("Please Specify Valid ReceivedById (employeeId)");
        }
        if (dto.getInventoryItemId() == null) {
            throw new DataNotFoundException("Please Specify Valid InventoryItemId");
        }
        Department department = builder.simpleFind(Department.class, dto.getDepartmentId());
        if (department != null) {
            dispatchRequest.setDepartment(department);
        }
        Employee employee = builder.simpleFind(Employee.class, dto.getReceivedById());
        if (employee != null) {
            dispatchRequest.setReceivedBy(employee);
        }
        InventoryItem inventoryItem = builder.simpleFind(InventoryItem.class, dto.getInventoryItemId());
        if (inventoryItem != null) {
            dispatchRequest.setInventoryItem(inventoryItem);
        }
        return dispatchRequest;
    }

    public AssetDispatchRequestDto toDto(AssetDispatchRequest dispatchRequest) {
        AssetDispatchRequestDto dto = new AssetDispatchRequestDto();
        if (dispatchRequest.getId() == null) {
            return null;
        }
        dto.setId(dispatchRequest.getId());
        dto.setQuantity(dto.getQuantity());
        try {
            dto.setApprovalStatus(dispatchRequest.getApprovalStatus().getLabel());
        } catch (Exception ignored) {
        }
        if (dispatchRequest.getReceivedBy() != null) {
            String fullName = dispatchRequest.getReceivedBy().getFirstName() + " " + dispatchRequest.getReceivedBy().getSurname() + " " + dispatchRequest.getReceivedBy().getOtherName();
            dto.setReceivedBy(fullName);
            dto.setReceivedById(dispatchRequest.getReceivedBy().getId());
        }
        if (dispatchRequest.getDepartment() != null) {
            dto.setDepartmentId(dispatchRequest.getDepartment().getId());
            dto.setDepartmentName(dispatchRequest.getDepartment().getDepartmentName());
        }
        if (dispatchRequest.getInventoryItem() != null) {
            dto.setInventoryItemId(dispatchRequest.getInventoryItem().getId());
            dto.setInventoryItem(dispatchRequest.getInventoryItem().getProductName());
        }
        return dto;
    }
}
