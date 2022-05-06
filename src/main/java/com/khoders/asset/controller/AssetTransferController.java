package com.khoders.asset.controller;

import com.khoders.asset.dto.AssetTransferDto;
import com.khoders.asset.entities.AssetTransfer;
import com.khoders.asset.mapper.AssetTransferMapper;
import com.khoders.asset.services.AssetTransferService;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/transfer")
public class AssetTransferController {
    @Autowired
    private AssetTransferService transferService;
    @Autowired
    private AssetTransferMapper mapper;

    @PostMapping
    public ResponseEntity<AssetTransfer> createAssetTransfer(@RequestBody AssetTransferDto dto) {
        try {
            AssetTransfer entity = mapper.toEntity(dto);
            AssetTransfer assetTransfer = transferService.saveTransfer(entity);
            if (assetTransfer == null) {
                return ApiResponse.error("Unknown Error", null);
            }
            return ApiResponse.ok("Asset Transfer Created Successfully", mapper.toDto(assetTransfer));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @PutMapping
    public ResponseEntity<AssetTransfer> updateTransfer(@RequestBody AssetTransferDto dto) {
        try {
            AssetTransfer transfer = transferService.findById(dto.getId());
            if (transfer == null) {
                return ApiResponse.notFound("No Transfer Found", null);
            }
            AssetTransfer assetTransfer = transferService.saveTransfer(transfer);
            if (assetTransfer == null) {
                return ApiResponse.error("Unknown Error", null);
            }
            return ApiResponse.ok("Asset Transfer Updated", true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/{transferId}")
    public ResponseEntity<AssetTransfer> findTransfer(@PathVariable(value = "transferId") String transferId) {
        try {
            AssetTransfer transfer = transferService.findById(transferId);
            if (transfer == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new AssetTransferDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(transfer));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
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
            return ApiResponse.ok("No Record Found", dtoList);
        }
        return ApiResponse.ok("Records Found", dtoList);
    }

    @DeleteMapping("/delete/{transferId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "transferId") String transferId) {
        try {
            if (transferService.delete(transferId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Transfer", false);
    }
}
