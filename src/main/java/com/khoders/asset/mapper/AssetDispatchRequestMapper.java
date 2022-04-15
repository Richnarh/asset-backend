package com.khoders.asset.mapper;

import com.khoders.asset.dto.AssetDispatchRequestDto;
import com.khoders.asset.entities.AssetDispatchRequest;
import com.khoders.asset.entities.Department;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.entities.InventoryItem;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.asset.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetDispatchRequestMapper {
    private static final Logger log = LoggerFactory.getLogger(AssetDispatchRequestMapper.class);
    @Autowired
    private CrudBuilder builder;

    public AssetDispatchRequest toEntity(AssetDispatchRequestDto dto) throws Exception{
        AssetDispatchRequest dispatchRequest = new AssetDispatchRequest();
        if(dto.getId() != null){
            dispatchRequest.setId(SpringUtils.stringToUUID(dto.getId()));
        }
        dispatchRequest.setRefNo(dispatchRequest.getRefNo());
        dispatchRequest.setQuantity(dto.getQuantity());
        dispatchRequest.setApprovalStatus(dto.getApprovalStatus());
        if(dto.getDepartmentId() == null){
            throw new Exception("Please Specify Valid DepartmentId");
        }
       if(dto.getReceivedById() == null){
            throw new Exception("Please Specify Valid ReceivedById (employeeId)");
        }
        if(dto.getInventoryItemId() == null){
            throw new Exception("Please Specify Valid InventoryItemId");
        }
        Department department = builder.findOne(dto.getDepartmentId(), Department.class);
        if (department != null) {
            dispatchRequest.setDepartment(department);
        }
        Employee employee = builder.findOne(dto.getReceivedById(), Employee.class);
        if (employee != null) {
            dispatchRequest.setReceivedBy(employee);
        }
        InventoryItem inventoryItem = builder.findOne(dto.getInventoryItemId(), InventoryItem.class);
        if (inventoryItem != null) {
            dispatchRequest.setInventoryItem(inventoryItem);
        }
        return dispatchRequest;
    }

    public AssetDispatchRequestDto toDto(AssetDispatchRequest dispatchRequest){
        AssetDispatchRequestDto dto = new AssetDispatchRequestDto();
        if(dispatchRequest.getId() == null){
            return null;
        }
        dto.setId(SpringUtils.UUIDtoString(dispatchRequest.getId()));
        dto.setQuantity(dto.getQuantity());
        dto.setApprovalStatus(dispatchRequest.getApprovalStatus());
        if(dispatchRequest.getReceivedBy() != null){
            String fullName = dispatchRequest.getReceivedBy().getFirstName() + " "+dispatchRequest.getReceivedBy().getSurname() +" "+dispatchRequest.getReceivedBy().getOtherName();
            dto.setReceivedBy(fullName);
            dto.setReceivedById(SpringUtils.UUIDtoString(dispatchRequest.getReceivedBy().getId()));
        }
        if(dispatchRequest.getDepartment() != null){
            dto.setDepartmentId(SpringUtils.UUIDtoString(dispatchRequest.getDepartment().getId()));
            dto.setDepartmentName(dispatchRequest.getDepartment().getDepartmentName());
        }
        if(dispatchRequest.getInventoryItem() != null){
            dto.setInventoryItemId(SpringUtils.UUIDtoString(dispatchRequest.getInventoryItem().getId()));
            dto.setInventoryItem(dispatchRequest.getInventoryItem().getProductName());
        }

        return dto;
    }
}
