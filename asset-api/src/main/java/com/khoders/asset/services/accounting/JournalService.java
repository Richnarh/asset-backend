package com.khoders.asset.services.accounting;

import com.khoders.asset.dto.accounting.JournalDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.accounting.JournalMapper;
import com.khoders.entities.accounting.Journal;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class JournalService {
    @Autowired
    private AppService appService;
    private JournalMapper journalMapper;

    public JournalDto save(JournalDto dto) throws Exception {
        if (dto.getId() != null) {
            Journal journal = appService.findById(Journal.class, dto.getId());
            if (journal == null) {
                throw new DataNotFoundException("Journal with ID: " + dto.getId() + " Not Found");
            }
        }
        Journal journal = journalMapper.toEntity(dto);
        if (appService.save(journal) != null) {
            return journalMapper.toDto(journal);
        }
        return null;
    }

    public List<JournalDto> journalList() {
        List<JournalDto> dtoList = new LinkedList<>();
        List<Journal> journalList = appService.findAll(Journal.class);
        for (Journal journal : journalList) {
            dtoList.add(journalMapper.toDto(journal));
        }
        return dtoList;
    }

    public JournalDto findById(String journalId) {
        return journalMapper.toDto(appService.findById(Journal.class, journalId));
    }

    public boolean delete(String journalId) throws Exception {
        return appService.deleteById(Journal.class, journalId);
    }
}
