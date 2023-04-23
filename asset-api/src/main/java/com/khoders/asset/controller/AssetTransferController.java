package com.khoders.asset.controller;

import com.khoders.asset.dto.AssetTransferDto;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.exceptions.InternalErrException;
import com.khoders.asset.mapper.AssetTransferMapper;
import com.khoders.asset.services.AssetTransferService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.entities.AssetTransfer;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@Tag(name = "Asset Transfer - Endpoint")
@RequestMapping(ApiEndpoint.ASSET_TRANSFER_ENDPOINT)
public class AssetTransferController {
    @Autowired
    private AssetTransferService transferService;
    @Autowired
    private AssetTransferMapper mapper;

    @PostMapping
    public ResponseEntity<AssetTransfer> createAssetTransfer(@RequestBody AssetTransferDto dto) throws Exception {
        try {
            AssetTransfer entity = mapper.toEntity(dto);
            AssetTransfer assetTransfer = transferService.saveTransfer(entity);
            if (assetTransfer == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.ok("Asset Transfer Created Successfully", mapper.toDto(assetTransfer));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<AssetTransfer> updateTransfer(@RequestBody AssetTransferDto dto) throws Exception {
        try {
            AssetTransfer transfer = transferService.findById(dto.getId());
            if (transfer == null) {
                return ApiResponse.notFound("No Transfer Found", null);
            }
            AssetTransfer assetTransfer = transferService.saveTransfer(transfer);
            if (assetTransfer == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            AssetTransferDto itemDto = mapper.toDto(assetTransfer);
            return ApiResponse.ok("Asset Transfer Updated", itemDto.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }

    @GetMapping("/{transferId}")
    public ResponseEntity<AssetTransfer> findTransfer(@PathVariable(value = "transferId") String transferId) throws Exception {
        try {
            AssetTransfer transfer = transferService.findById(transferId);
            if (transfer == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new AssetTransferDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(transfer));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<AssetTransfer>> getAssetTransfers() {
        List<AssetTransfer> assetTransferList = transferService.transferList();
        List<AssetTransferDto> dtoList = new LinkedList<>();
        assetTransferList.forEach(assetTransfer -> {
            dtoList.add(mapper.toDto(assetTransfer));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @DeleteMapping("/delete/{transferId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "transferId") String transferId) throws Exception {
        try {
            if (transferService.delete(transferId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
        return ApiResponse.error("Could Not Delete Transfer", false);
    }
}
