package com.khoders.asset.services;

import com.khoders.asset.dto.maintenance.InstructionSetDto;
import com.khoders.asset.entities.maintenance.InstructionSet;
import com.khoders.asset.entities.maintenance.InstructionStep;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.maintenance.InstructionSetMapper;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.utilities.Msg;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class InstructionSetService {
    private CrudBuilder builder;
    private InstructionSetMapper mapper;

    public InstructionSetDto save(InstructionSetDto dto) throws Exception {
        if (dto.getId() != null){
            InstructionSet instruction = builder.simpleFind(InstructionSet.class, dto.getId());
            if (instruction == null){
                throw new DataNotFoundException("InstructionSet with ID: "+ dto.getId() +" Not Found");
            }
        }
        InstructionSet instructionSet = mapper.toEntity(dto);
        if (builder.save(instructionSet) != null){
            for (InstructionStep instructionStep: instructionSet.getInstructionStepList()){
                instructionStep.setInstructionSet(instructionSet);
                builder.save(instructionStep);
            }
        }
        return mapper.toDto(instructionSet);
    }

    public List<InstructionSetDto> instructionSetList()throws Exception{
        Session session = builder.session();

        List<InstructionStep> instructionStepList;
        List<InstructionSetDto> instructionList = new LinkedList<>();
        List<InstructionSet> instructionSetList = builder.findAll(InstructionSet.class);
        if (instructionSetList.isEmpty()){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
            try {
                for (InstructionSet instructionSet:instructionSetList){
                    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                    CriteriaQuery<InstructionStep> criteriaQuery = criteriaBuilder.createQuery(InstructionStep.class);
                    Root<InstructionStep> root = criteriaQuery.from(InstructionStep.class);
                    criteriaQuery.where(criteriaBuilder.equal(root.get(InstructionStep._instructionSet), instructionSet));
                    Query<InstructionStep> query = session.createQuery(criteriaQuery);
                    instructionStepList = query.getResultList();
                    instructionSet.setInstructionStepList(instructionStepList);
                    instructionSetList = new LinkedList<>();
                    instructionSetList.add(instructionSet);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            for (InstructionSet instructionSet:instructionSetList){
                instructionList.add(mapper.toDto(instructionSet));
            }
            return instructionList;
    }

    public InstructionSetDto findById(String instructionSetId) throws Exception {
        Session session = builder.session();
        List<InstructionStep> instructionStepList;

        InstructionSet instructionSet = builder.simpleFind(InstructionSet.class, instructionSetId);
        if (instructionSet == null){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
            try {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<InstructionStep> criteriaQuery = criteriaBuilder.createQuery(InstructionStep.class);
                Root<InstructionStep> root = criteriaQuery.from(InstructionStep.class);
                criteriaQuery.where(criteriaBuilder.equal(root.get(InstructionStep._instructionSet), instructionSet));
                Query<InstructionStep> query = session.createQuery(criteriaQuery);
                instructionStepList = query.getResultList();
                instructionSet.setInstructionStepList(instructionStepList);
                return mapper.toDto(instructionSet);
            }catch (Exception e){
                e.printStackTrace();
            }
        return null;
    }
    public boolean delete(String instructionSetId) throws Exception {
        return builder.deleteById(instructionSetId, InstructionSet.class);
    }
}
