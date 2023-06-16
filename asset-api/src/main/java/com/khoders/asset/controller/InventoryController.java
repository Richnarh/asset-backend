package com.khoders.asset.controller;

import com.khoders.asset.dto.InventoryDto;
import com.khoders.asset.services.InventoryService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Inventory - Endpoint")
@RequestMapping(ApiEndpoint.INVENTORY_ENDPOINT)
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDto> create(@RequestBody InventoryDto dto) throws Exception {
        InventoryDto inventory = inventoryService.saveInventory(dto);
        return ApiResponse.created(Msg.CREATED, inventory);
    }

    @PutMapping
    public ResponseEntity<InventoryDto> update(@RequestBody InventoryDto dto) throws Exception {
        InventoryDto inventory = inventoryService.saveInventory(dto);
        return ApiResponse.ok(Msg.CREATED, inventory);
    }

    @GetMapping("/list")
    public ResponseEntity<List<InventoryDto>> list() {
        List<InventoryDto> dtoList = inventoryService.inventoryList();
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryDto> findSingle(@PathVariable(value = "inventoryId") String inventoryId) {
        InventoryDto inventory = inventoryService.findById(inventoryId);
        return ApiResponse.ok(Msg.RECORD_FOUND, inventory);
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "inventoryId") String inventoryId) {
        try {
            if (inventoryService.delete(inventoryId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Inventory", false);
    }
}
