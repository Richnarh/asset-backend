package com.khoders.asset.controller.lookups;

import com.khoders.asset.dto.LookupItem;
import com.khoders.entities.*;
import com.khoders.entities.maintenance.RequestType;
import com.khoders.entities.settings.InvoiceType;
import com.khoders.springapi.AppService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class LookupSetup {
    @Autowired
    private AppService appService;

    private static final Logger log = getLogger(LookupSetup.class);

    public static <E extends Enum<E>> List<LookupItem> PrepareEnum(E[] eEnum) {
        List<LookupItem> dtoList = new LinkedList<>();
        for (int i = 0; i <= Arrays.asList(eEnum).size() - 1; i++) {
            log.info("Index: {}  --- Enum: {} ", i, eEnum[i]);
            LookupItem item = new LookupItem();
            item.setId(eEnum[i].name());
            item.setItemName(eEnum[i].toString());
            dtoList.add(item);
        }
        return dtoList;
    }

    public List<LookupItem> categories() {
        List<LookupItem> itemList = new LinkedList<>();
        appService.findAll(Category.class).forEach(category -> {
            LookupItem item = new LookupItem();
            item.setId(category.getId());
            item.setItemName(category.getCategoryName());
            itemList.add(item);
        });
        return itemList;
    }

    public List<LookupItem> department() {
        List<LookupItem> itemList = new LinkedList<>();
        appService.findAll(Department.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getDepartmentName());
            itemList.add(item);
        });
        return itemList;
    }

    public List<LookupItem> location() {
        List<LookupItem> itemList = new LinkedList<>();
        appService.findAll(Location.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getLocationName());
            itemList.add(item);
        });
        return itemList;
    }

    public List<LookupItem> employees() {
        List<LookupItem> itemList = new LinkedList<>();
        appService.findAll(Employee.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getFirstName() + " " + data.getSurname());
            itemList.add(item);
        });
        return itemList;
    }

    public List<LookupItem> businessClient() {
        List<LookupItem> itemList = new LinkedList<>();
        appService.findAll(BusinessClient.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getFirstname() + " " + data.getLastname());
            itemList.add(item);
        });
        return itemList;
    }

    public List<LookupItem> invoiceType() {
        List<LookupItem> itemList = new LinkedList<>();
        appService.findAll(InvoiceType.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getTypeName());
            itemList.add(item);
        });
        return itemList;
    }

    public List<LookupItem> requestType() {
        List<LookupItem> itemList = new LinkedList<>();
        appService.findAll(RequestType.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getRequestName());
            itemList.add(item);
        });
        return itemList;
    }

    public List<LookupItem> companies() {
        List<LookupItem> itemList = new LinkedList<>();
        appService.findAll(Company.class).forEach(data -> {
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getCompanyName());
            itemList.add(item);
        });
        return itemList;
    }
}
