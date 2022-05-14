package com.khoders.asset.services;

import com.khoders.asset.dto.accounting.ExpenseDto;
import com.khoders.asset.entities.accounting.Expense;
import com.khoders.asset.entities.accounting.ExpenseItem;
import com.khoders.asset.mapper.accounting.ExpenseMapper;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.utilities.Msg;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class ExpenseService {
    @Autowired private CrudBuilder builder;
    @Autowired private ExpenseMapper expenseMapper;

    public ExpenseDto save(ExpenseDto dto){
        if (dto.getId() != null){
            Expense expense = builder.simpleFind(Expense.class, dto.getId());
            if (expense == null){
                throw new DataNotFoundException("Expense with ID: "+ dto.getId() +" Not Found");
            }
        }
        Expense expense = expenseMapper.toEntity(dto);
        if (builder.save(expense) != null){
            for(ExpenseItem expenseItem: expense.getExpenseItemList()){
                expenseItem.setExpense(expense);
                builder.save(expenseItem);
            }
        }
        return expenseMapper.toDto(expense);
    }
    public List<ExpenseDto> expenseList(){
        Session session = builder.session();

        List<ExpenseItem> expenseItemList;
        List<ExpenseDto> dtoList = new LinkedList<>();

        List<Expense> expenseList = builder.findAll(Expense.class);
        if (expenseList.isEmpty()){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
            try {
                for (Expense expense:expenseList){
                    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                    CriteriaQuery<ExpenseItem> criteriaQuery = criteriaBuilder.createQuery(ExpenseItem.class);
                    Root<ExpenseItem> root = criteriaQuery.from(ExpenseItem.class);
                    criteriaQuery.where(criteriaBuilder.equal(root.get(ExpenseItem._expense), expense));
                    Query<ExpenseItem> query = session.createQuery(criteriaQuery);
                    expenseItemList = query.getResultList();
                    expense.setExpenseItemList(expenseItemList);
                    expenseList = new LinkedList<>();
                    expenseList.add(expense);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            for (Expense expense : expenseList){
                dtoList.add(expenseMapper.toDto(expense));
            }
            return dtoList;
    }
    public ExpenseDto findById(String expenseId){
        Session session = builder.session();
        List<ExpenseItem> expenseItemList = new LinkedList<>();

        Expense expense = builder.simpleFind(Expense.class, expenseId);
        if (expense == null){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
            try {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<ExpenseItem> criteriaQuery = criteriaBuilder.createQuery(ExpenseItem.class);
                Root<ExpenseItem> root = criteriaQuery.from(ExpenseItem.class);
                criteriaQuery.where(criteriaBuilder.equal(root.get(ExpenseItem._expense), expense));
                Query<ExpenseItem> query = session.createQuery(criteriaQuery);
                expenseItemList = query.getResultList();
                expense.setExpenseItemList(expenseItemList);
                return expenseMapper.toDto(expense);
            }catch (Exception e){
                e.printStackTrace();
            }
        return null;
    }
    public boolean delete(String expenseId) {
        return builder.deleteById(expenseId, Expense.class);
    }
}
