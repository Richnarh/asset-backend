package com.khoders.asset.mapper.maintenance;

import com.khoders.asset.dto.maintenance.OccurrenceDto;
import com.khoders.asset.entities.Asset;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.entities.Location;
import com.khoders.asset.entities.maintenance.InstructionSet;
import com.khoders.asset.entities.maintenance.MaintenanceTask;
import com.khoders.asset.entities.maintenance.Occurrence;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OccurrenceMapper {
    @Autowired private CrudBuilder builder;

    public Occurrence toEntity(OccurrenceDto dto){
        Occurrence occurrence = new Occurrence();
        if (dto.getId() != null){
            occurrence.setId(dto.getId());
        }
        occurrence.setOutsourced(dto.isOutsourced());
        if (dto.getAssetId() == null){
            throw new DataNotFoundException("Please Specify Valid AssetId");
        }
         if (dto.getEmployeeId() == null){
            throw new DataNotFoundException("Please Specify Valid EmployeeId");
        }
         if (dto.getEquipmentId() == null){
            throw new DataNotFoundException("Please Specify Valid Equipment");
        }
         if (dto.getInstructionSetId() == null){
            throw new DataNotFoundException("Please Specify Valid InstructionSet");
        }
         if (dto.getLocationId() == null){
            throw new DataNotFoundException("Please Specify Valid LocationId");
        }
         if (dto.getMaintenanceTaskId() == null){
            throw new DataNotFoundException("Please Specify Valid MaintenanceTask");
        }
        Asset asset = builder.simpleFind(Asset.class, dto.getAssetId());
         if (asset != null){
             occurrence.setAsset(asset);
         }
         Asset equipment = builder.simpleFind(Asset.class, dto.getEquipmentId());
         if (equipment != null){
             occurrence.setEquipment(equipment);
         }
         Employee employee = builder.simpleFind(Employee.class, dto.getEmployeeId());
         if (employee != null){
             occurrence.setEmployee(employee);
         }
         InstructionSet instructionSet = builder.simpleFind(InstructionSet.class, dto.getInstructionSetId());
         if (instructionSet != null){
             occurrence.setInstructionSet(instructionSet);
         }
         Location location = builder.simpleFind(Location.class, dto.getLocationId());
         if (location != null){
             occurrence.setLocation(location);
         }
         MaintenanceTask maintenanceTask = builder.simpleFind(MaintenanceTask.class, dto.getMaintenanceTaskId());
         if (maintenanceTask != null){
             occurrence.setMaintenanceTask(maintenanceTask);
         }
         return occurrence;
    }

    public OccurrenceDto toDto(Occurrence occurrence){
        OccurrenceDto dto = new OccurrenceDto();
        if (occurrence.getId() == null) return null;
        dto.setId(occurrence.getId());
        dto.setOutsourced(occurrence.isOutsourced());
        if (occurrence.getAsset() != null){
            dto.setAssetId(occurrence.getAsset().getId());
            dto.setAssetName(occurrence.getAsset().getAssetCode());
        }
        if (occurrence.getEquipment() != null){
            dto.setEquipmentId(occurrence.getEquipment().getId());
            dto.setEquipment(occurrence.getEquipment().getAssetCode());
        }
        if (occurrence.getEmployee() != null){
            dto.setEmployeeId(occurrence.getEmployee().getId());
            dto.setEmployeeName(occurrence.getEmployee().getEmailAddress());
        }
        if (occurrence.getLocation() != null){
            dto.setLocationId(occurrence.getLocation().getId());
            dto.setLocationName(occurrence.getLocation().getLocationName());
        }
        if (occurrence.getInstructionSet() != null){
            dto.setInstructionSetId(occurrence.getInstructionSet().getId());
            dto.setInstructionSet(occurrence.getInstructionSet().getInstructionName());
        }
        if (occurrence.getMaintenanceTask() != null){
            dto.setMaintenanceTaskId(occurrence.getMaintenanceTask().getId());
            dto.setMaintenanceTask(occurrence.getMaintenanceTask().getTaskName());
        }
        return dto;
    }
}
