package com.khoders.asset.mapper;

import com.khoders.asset.dto.AssetTransferDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.AssetTransfer;
import com.khoders.entities.Location;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetTransferMapper {
    @Autowired
    private AppService appService;

    public AssetTransfer toEntity(AssetTransferDto dto) throws Exception {
        AssetTransfer assetTransfer = new AssetTransfer();
        if (dto.getId() != null) {
            assetTransfer.setId(dto.getId());
        }
        assetTransfer.setRefNo(assetTransfer.getRefNo());
        assetTransfer.setTransferDate(DateUtil.parseLocalDate(dto.getTransferDate(), Pattern._yyyyMMdd));
        assetTransfer.setDescription(dto.getDescription());

        if (dto.getTransferFromId() == null) {
            throw new DataNotFoundException("Please Specify Valid TransferFromId");
        }
        if (dto.getTransferToId() == null) {
            throw new DataNotFoundException("Please Specify Valid TransferToId");
        }
        Location fromLoc = appService.findById(Location.class, dto.getTransferFromId());
        Location toLoc = appService.findById(Location.class, dto.getTransferToId());
        if (fromLoc != null) {
            assetTransfer.setTransferFrom(fromLoc);
        }
        if (toLoc != null) {
            assetTransfer.setTransferTo(toLoc);
        }
        if (assetTransfer.getTransferFrom().equals(assetTransfer.getTransferTo())) {
            throw new DataNotFoundException("Transfer is not allowed within same location.");
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
