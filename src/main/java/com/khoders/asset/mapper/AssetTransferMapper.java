package com.khoders.asset.mapper;

import com.khoders.asset.dto.AssetTransferDto;
import com.khoders.asset.entities.AssetTransfer;
import com.khoders.asset.entities.Location;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class AssetTransferMapper {
    @Autowired
    private CrudBuilder builder;

    public AssetTransfer toEntity(AssetTransferDto dto) {
        AssetTransfer assetTransfer = new AssetTransfer();
        if (dto.getId() != null) {
            assetTransfer.setId(dto.getId());
        }
        assetTransfer.setRefNo(assetTransfer.getRefNo());
        assetTransfer.setTransferDate(DateUtil.parseLocalDate(dto.getTransferDate(), Pattern._yyyyMMdd));
        assetTransfer.setDescription(dto.getDescription());
        if (dto.getValueDate() == null) {
            assetTransfer.setValueDate(LocalDate.now());
            assetTransfer.setLastModifiedDate(LocalDateTime.now());
        }
        if (dto.getTransferFromId() == null) {
            throw new DataNotFoundException("Please Specify Valid TransferFromId");
        }
        if (dto.getTransferToId() == null) {
            throw new DataNotFoundException("Please Specify Valid TransferToId");
        }
        Location fromLoc = builder.findOne(dto.getTransferFromId(), Location.class);
        Location toLoc = builder.findOne(dto.getTransferToId(), Location.class);
        if (fromLoc != null) {
            assetTransfer.setTransferFrom(fromLoc);
        }
        if (toLoc != null) {
            assetTransfer.setTransferTo(toLoc);
        }
        return assetTransfer;
    }

    public AssetTransferDto toDto(AssetTransfer assetTransfer) {
        AssetTransferDto dto = new AssetTransferDto();
        if (assetTransfer == null) {
            return null;
        }
        dto.setId(assetTransfer.getId().toString());
        dto.setTransferDate(DateUtil.parseLocalDateString(assetTransfer.getTransferDate(), Pattern.ddMMyyyy));
        dto.setDescription(assetTransfer.getDescription());
        dto.setValueDate(DateUtil.parseLocalDateString(assetTransfer.getValueDate(), Pattern.ddMMyyyy));
        if (assetTransfer.getTransferFrom() != null) {
            dto.setTransferFromId(assetTransfer.getTransferFrom().getId());
            dto.setTransferFrom(assetTransfer.getTransferFrom().getLocationName());
        }
        if (assetTransfer.getTransferFrom() != null) {
            dto.setTransferToId(assetTransfer.getTransferTo().getId());
            dto.setTransferTo(assetTransfer.getTransferTo().getLocationName());
        }
        return dto;
    }
}
