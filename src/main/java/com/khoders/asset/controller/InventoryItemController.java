package com.khoders.asset.controller;

import com.khoders.asset.dto.InventoryItemDto;
import com.khoders.asset.entities.InventoryItem;
import com.khoders.asset.mapper.InventoryItemMapper;
import com.khoders.asset.services.InventoryItemService;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/inventory-item")
public class InventoryItemController {
    @Autowired
    private InventoryItemService inventoryItemService;
    @Autowired
    private InventoryItemMapper mapper;

    @PostMapping
    public ResponseEntity<InventoryItem> create(@RequestBody InventoryItemDto dto) {
        try {
            InventoryItem entity = mapper.toEntity(dto);
            InventoryItem inventoryItem = inventoryItemService.save(entity);
            if (inventoryItem == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(inventoryItem));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<InventoryItem>> list() {
        List<InventoryItem> inventoryItemList = inventoryItemService.inventoryItemList();
        List<InventoryItemDto> dtoList = new LinkedList<>();
        inventoryItemList.forEach(item -> {
            dtoList.add(mapper.toDto(item));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{inventoryItemId}")
    public ResponseEntity<InventoryItem> findSingle(@PathVariable(value = "inventoryItemId") String inventoryItemId) {
        try {
            InventoryItem inventoryItem = inventoryItemService.findById(inventoryItemId);
            if (inventoryItem == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new InventoryItemDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(inventoryItem));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<InventoryItem> update(@RequestBody InventoryItemDto dto) {
        try {
            InventoryItem loc = inventoryItemService.findById(dto.getId());
            if (loc == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            InventoryItem entity = mapper.toEntity(dto);
            InventoryItem inventoryItem = inventoryItemService.save(entity);
            if (inventoryItem == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.ok(Msg.UPDATED, mapper.toDto(inventoryItem));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @DeleteMapping("/{inventoryItemId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "inventoryItemId") String inventoryItemId) {
        try {
            if (inventoryItemService.delete(inventoryItemId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete InventoryItem", false);
    }
}
