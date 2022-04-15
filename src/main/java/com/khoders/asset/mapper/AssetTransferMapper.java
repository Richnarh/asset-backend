package com.khoders.asset.mapper;

import com.khoders.asset.dto.AssetTransferDto;
import com.khoders.asset.entities.AssetTransfer;
import com.khoders.asset.utils.SpringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class AssetTransferMapper
{
    public AssetTransfer toEntity(AssetTransferDto dto)
    {
        AssetTransfer assetTransfer = new AssetTransfer();
        if(dto.getId() != null){
            assetTransfer.setId(SpringUtils.stringToUUID(dto.getId()));
        }
        assetTransfer.setRefNo(assetTransfer.getRefNo());
        assetTransfer.setTransferTo(dto.getTransferTo());
        assetTransfer.setTransferFrom(dto.getTransferFrom());
        assetTransfer.setTransferDate(dto.getTransferDate());
        assetTransfer.setDescription(dto.getDescription());
        if(dto.getValueDate() == null){
            assetTransfer.setValueDate(LocalDate.now());
            assetTransfer.setLastModifiedDate(LocalDateTime.now());
        }

        return assetTransfer;
    }

    public AssetTransferDto toDto(AssetTransfer assetTransfer)
    {
        AssetTransferDto dto = new AssetTransferDto();
        if (assetTransfer == null) {
            return null;
        }
        dto.setId(assetTransfer.getId().toString());
        dto.setRefNo(assetTransfer.getRefNo());
        dto.setTransferDate(assetTransfer.getTransferDate());
        dto.setTransferFrom(assetTransfer.getTransferFrom());
        dto.setTransferTo(assetTransfer.getTransferTo());
        dto.setDescription(assetTransfer.getDescription());
        dto.setValueDate(assetTransfer.getValueDate());
        return dto;
    }
}
