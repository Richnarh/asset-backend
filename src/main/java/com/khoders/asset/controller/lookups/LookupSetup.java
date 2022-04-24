package com.khoders.asset.controller.lookups;

import com.khoders.asset.dto.LookupItem;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class LookupSetup {
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
}
