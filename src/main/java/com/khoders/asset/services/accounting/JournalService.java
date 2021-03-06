package com.khoders.asset.services.accounting;

import com.khoders.asset.dto.accounting.GLDto;
import com.khoders.asset.dto.accounting.JournalDto;
import com.khoders.asset.entities.accounting.GeneralLedger;
import com.khoders.asset.entities.accounting.Journal;
import com.khoders.asset.mapper.accounting.JournalMapper;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class JournalService {
    @Autowired
    private CrudBuilder builder;
    private JournalMapper journalMapper;

    public JournalDto save(JournalDto dto){
        if (dto.getId() != null){
            Journal journal = builder.simpleFind(Journal.class, dto.getId());
            if (journal == null){
                throw new DataNotFoundException("Journal with ID: "+ dto.getId() +" Not Found");
            }
        }
        Journal  journal = journalMapper.toEntity(dto);
        if (builder.save(journal) != null){
            return journalMapper.toDto(journal);
        }
        return null;
    }
    public List<JournalDto> journalList(){
        List<JournalDto> dtoList = new LinkedList<>();
        List<Journal> journalList = builder.findAll(Journal.class);
        for (Journal journal:journalList){
            dtoList.add(journalMapper.toDto(journal));
        }
        return dtoList;
    }
    public JournalDto findById(String journalId){
        return journalMapper.toDto(builder.simpleFind(Journal.class, journalId));
    }
    public boolean delete(String journalId){
        return builder.deleteById(journalId, Journal.class);
    }
}
