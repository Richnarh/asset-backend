package com.khoders.asset.controller.lookups;

import com.khoders.asset.dto.LookupItem;
import com.khoders.asset.entities.*;
import com.khoders.asset.entities.maintenance.RequestType;
import com.khoders.asset.entities.settings.InvoiceType;
import com.khoders.asset.utils.CrudBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;
@Component
@Transactional
public class LookupSetup {
    @Autowired
    private CrudBuilder builder;

    private static final Logger log = getLogger(LookupSetup.class);

    public static <E extends Enum<E>> List<LookupItem> PrepareEnum(E[] eEnum){
        List<LookupItem> dtoList = new LinkedList<>();
        for (int i=0; i <= Arrays.asList(eEnum).size() - 1; i++){
            log.info("Index: {}  --- Enum: {} ",i,eEnum[i]);
            LookupItem item = new LookupItem();
            item.setId(eEnum[i].name());
            item.setItemName(eEnum[i].toString());
            dtoList.add(item);
        }
        return dtoList;
    }
    public List<LookupItem> categories(){
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(Category.class).forEach(category ->{
            LookupItem item = new LookupItem();
                item.setId(category.getId());
                item.setItemName(category.getCategoryName());
                itemList.add(item);
            });
        return itemList;
    }
    public List<LookupItem> department(){
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(Department.class).forEach(data ->{
            LookupItem item = new LookupItem();
                item.setId(data.getId());
                item.setItemName(data.getDepartmentName());
                itemList.add(item);
            });
        return itemList;
    }
    public List<LookupItem> location(){
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(Location.class).forEach(data ->{
            LookupItem item = new LookupItem();
                item.setId(data.getId());
                item.setItemName(data.getLocationName());
                itemList.add(item);
            });
        return itemList;
    }
    public List<LookupItem> employees(){
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(Employee.class).forEach(data ->{
            LookupItem item = new LookupItem();
                item.setId(data.getId());
                item.setItemName(data.getFirstName() +" "+data.getSurname());
                itemList.add(item);
            });
        return itemList;
    }
    public List<LookupItem> businessClient(){
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(BusinessClient.class).forEach(data ->{
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getFirstname() +" "+data.getLastname());
            itemList.add(item);
        });
        return itemList;
    }
    public List<LookupItem> invoiceType(){
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(InvoiceType.class).forEach(data ->{
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getTypeName());
            itemList.add(item);
        });
        return itemList;
    }
    public List<LookupItem> requestType(){
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(RequestType.class).forEach(data ->{
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getRequestName());
            itemList.add(item);
        });
        return itemList;
    }
    public List<LookupItem> companies(){
        List<LookupItem> itemList = new LinkedList<>();
        builder.findAll(Company.class).forEach(data ->{
            LookupItem item = new LookupItem();
            item.setId(data.getId());
            item.setItemName(data.getCompanyName());
            itemList.add(item);
        });
        return itemList;
    }
}
