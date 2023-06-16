package com.khoders.asset.services;

import com.khoders.asset.dto.InventoryDto;
import com.khoders.asset.dto.Sql;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.InventoryExtractMapper;
import com.khoders.entities.Inventory;
import com.khoders.entities.InventoryItem;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private AppService appService;
    @Autowired
    private InventoryExtractMapper extractMapper;
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public InventoryDto saveInventory(InventoryDto dto) throws Exception {
        if (dto.getId() != null) {
            Inventory inventory = appService.findById(Inventory.class, dto.getId());
            if (inventory == null) {
                throw new DataNotFoundException("Inventory with ID: " + dto.getId() + " Not Found");
            }
        }
        Inventory inventory = extractMapper.toEntity(dto);
        if (appService.save(inventory) != null) {
            for (InventoryItem inventoryItem : inventory.getInventoryItemList()) {
                inventoryItem.setInventory(inventory);
                appService.save(inventoryItem);
            }
        }
        return extractMapper.toDto(inventory);
    }

    public List<InventoryDto> inventoryList() {
        List<InventoryItem> inventoryItemList;
        List<InventoryDto> dtoList = new LinkedList<>();

        List<Inventory> inventoryList = appService.findAll(Inventory.class);
        if (inventoryList != null && !inventoryList.isEmpty()) {

            try {
                for (Inventory inventory : inventoryList) {
                    SqlParameterSource param = new MapSqlParameterSource(InventoryItem._inventoryId, inventory.getId());
                    inventoryItemList = jdbc.query(Sql.INVENTORY_ITEM_INV_ID, param, BeanPropertyRowMapper.newInstance(InventoryItem.class));
                    inventory.setInventoryItemList(inventoryItemList);
                    inventoryList = new LinkedList<>();
                    inventoryList.add(inventory);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Inventory inventory : inventoryList) {
                dtoList.add(extractMapper.toDto(inventory));
            }
            return dtoList;
        }
        return Collections.emptyList();
    }
    public InventoryDto findById(String inventoryId) {
        List<InventoryItem> inventoryItemList = new LinkedList<>();
        Inventory inventory = appService.findById(Inventory.class, inventoryId);
        if (inventory != null) {
            SqlParameterSource param = new MapSqlParameterSource(InventoryItem._inventoryId, inventory.getId());
            inventoryItemList = jdbc.query(Sql.INVENTORY_ITEM_INV_ID, param, BeanPropertyRowMapper.newInstance(InventoryItem.class));
            inventory.setInventoryItemList(inventoryItemList);
            return extractMapper.toDto(inventory);
        }
        return null;
    }

    public boolean delete(String inventoryId) throws Exception {
        return appService.deleteById(Inventory.class, inventoryId);
    }
}
