package com.khoders.asset.services;

import com.khoders.asset.dto.InventoryDto;
import com.khoders.asset.entities.Inventory;
import com.khoders.asset.entities.InventoryItem;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.InventoryExtractMapper;
import com.khoders.asset.utils.CrudBuilder;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class InventoryService {
    @Autowired private CrudBuilder builder;
    @Autowired private InventoryExtractMapper extractMapper;

    public InventoryDto saveInventory(InventoryDto dto)throws Exception{
        if (dto.getId() != null){
            Inventory inventory = builder.simpleFind(Inventory.class, dto.getId());
            if (inventory == null){
                throw new DataNotFoundException("Inventory with ID: "+ dto.getId() +" Not Found");
            }
        }
        Inventory inventory = extractMapper.toEntity(dto);
        if (builder.save(inventory) != null){
            for(InventoryItem inventoryItem: inventory.getInventoryItemList()){
                inventoryItem.setInventory(inventory);
                builder.save(inventoryItem);
            }
        }
        return extractMapper.toDto(inventory);
    }
    public List<InventoryDto> inventoryList(){
        Session session = builder.session();

        List<InventoryItem> inventoryItemList;
        List<InventoryDto> dtoList = new LinkedList<>();

        List<Inventory> inventoryList = builder.findAll(Inventory.class);
        if (inventoryList != null && !inventoryList.isEmpty()){
            try {
                for (Inventory inventory:inventoryList){
                    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                    CriteriaQuery<InventoryItem> criteriaQuery = criteriaBuilder.createQuery(InventoryItem.class);
                    Root<InventoryItem> root = criteriaQuery.from(InventoryItem.class);
                    criteriaQuery.where(criteriaBuilder.equal(root.get(InventoryItem._inventory), inventory));
                    Query<InventoryItem> query = session.createQuery(criteriaQuery);
                    inventoryItemList = query.getResultList();
                    inventory.setInventoryItemList(inventoryItemList);
                    inventoryList = new LinkedList<>();
                    inventoryList.add(inventory);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            for (Inventory inventory : inventoryList){
                dtoList.add(extractMapper.toDto(inventory));
            }
            return dtoList;
        }
        return Collections.emptyList();
    }
    public InventoryDto findById(String inventoryId){
        Session session = builder.session();
        List<InventoryItem> inventoryItemList = new LinkedList<>();

        Inventory inventory = builder.simpleFind(Inventory.class, inventoryId);

        if (inventory != null){
            try {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<InventoryItem> criteriaQuery = criteriaBuilder.createQuery(InventoryItem.class);
                Root<InventoryItem> root = criteriaQuery.from(InventoryItem.class);
                criteriaQuery.where(criteriaBuilder.equal(root.get(InventoryItem._inventory), inventory));
                Query<InventoryItem> query = session.createQuery(criteriaQuery);
                inventoryItemList = query.getResultList();
                inventory.setInventoryItemList(inventoryItemList);
                return extractMapper.toDto(inventory);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public boolean delete(String inventoryId) throws Exception {
        return builder.deleteById(inventoryId, Inventory.class);
    }
}
