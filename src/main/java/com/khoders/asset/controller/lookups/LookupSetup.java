package com.khoders.asset.controller.lookups;

import com.khoders.asset.dto.LookupItem;
import com.khoders.asset.utils.CrudBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

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

    // TODO: 05/07/22, creating generics to generate lookup out of entity
    public  <T> T convert(Class<T> t){
        List<T> params = builder.findAll(t);
        for (int i=0; i <= params.size()-1; i++){
            LookupItem item = new LookupItem();
            item.setItemName(params.get(i).toString());
        }
        return null;
    }
}
