package com.khoders.asset.mapper;

import com.khoders.asset.dto.AssetDispatchRequestDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.AssetDispatchRequest;
import com.khoders.entities.Department;
import com.khoders.entities.Employee;
import com.khoders.entities.InventoryItem;
import com.khoders.entities.constants.ApprovalStatus;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetDispatchRequestMapper {
    @Autowired
    private AppService appService;

    public AssetDispatchRequest toEntity(AssetDispatchRequestDto dto) throws Exception {
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
        Department department = appService.findById(Department.class, dto.getDepartmentId());
        if (department != null) {
            dispatchRequest.setDepartment(department);
        }
        Employee employee = appService.findById(Employee.class, dto.getReceivedById());
        if (employee != null) {
            dispatchRequest.setReceivedBy(employee);
        }
        InventoryItem inventoryItem = appService.findById(InventoryItem.class, dto.getInventoryItemId());
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
