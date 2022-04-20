package com.khoders.asset.controller;

import com.khoders.asset.dto.InventoryDto;
import com.khoders.asset.dto.LocationDto;
import com.khoders.asset.entities.Inventory;
import com.khoders.asset.entities.Location;
import com.khoders.asset.mapper.InventoryMapper;
import com.khoders.asset.mapper.LocationMapper;
import com.khoders.asset.services.InventoryService;
import com.khoders.asset.services.LocationService;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private InventoryMapper mapper;

    @PostMapping
    public ResponseEntity<Inventory> create(@RequestBody InventoryDto dto) {
        try {
            Inventory entity = mapper.toEntity(dto);
            Inventory inventory = inventoryService.save(entity);
            if (inventory == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(inventory));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Inventory>> list() {
        List<Inventory> inventoryList = inventoryService.inventories();
        List<InventoryDto> dtoList = new LinkedList<>();
        inventoryList.forEach(inventory -> {
            dtoList.add(mapper.toDto(inventory));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<Location> findSingle(@PathVariable(value = "inventoryId") String inventoryId) {
        try {
            Inventory inventory = inventoryService.findById(inventoryId);
            if (inventory == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new InventoryDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(inventory));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<Inventory> update(@RequestBody InventoryDto dto) {
        try {
            Inventory loc = inventoryService.findById(dto.getId());
            if (loc == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Inventory entity = mapper.toEntity(dto);
            Inventory inventory = inventoryService.save(entity);
            if (inventory == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.ok(Msg.UPDATED, mapper.toDto(inventory));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
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
