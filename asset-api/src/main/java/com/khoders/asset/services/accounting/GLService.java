package com.khoders.asset.services.accounting;

import com.khoders.asset.dto.accounting.GLDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.accounting.GLMapper;
import com.khoders.entities.accounting.GeneralLedger;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GLService {
    @Autowired
    private AppService appService;
    private GLMapper glMapper;

    public GLDto save(GLDto dto) throws Exception {
        if (dto.getId() != null) {
            GeneralLedger gl = appService.findById(GeneralLedger.class, dto.getId());
            if (gl == null) {
                throw new DataNotFoundException("GeneralLedger with ID: " + dto.getId() + " Not Found");
            }
        }
        GeneralLedger ledger = glMapper.toEntity(dto);
        if (appService.save(ledger) != null) {
            return glMapper.toDto(ledger);
        }
        return null;
    }

    public List<GLDto> glList() {
        List<GLDto> dtoList = new LinkedList<>();
        List<GeneralLedger> glList = appService.findAll(GeneralLedger.class);
        for (GeneralLedger gl : glList) {
            dtoList.add(glMapper.toDto(gl));
        }
        return dtoList;
    }

    public GLDto findById(String glId) {
        return glMapper.toDto(appService.findById(GeneralLedger.class, glId));
    }

    public boolean delete(String glId) throws Exception {
        return appService.deleteById(GeneralLedger.class, glId);
    }
}
