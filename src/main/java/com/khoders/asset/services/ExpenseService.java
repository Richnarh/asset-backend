package com.khoders.asset.services;

import com.khoders.asset.config.JndiConfig;
import com.khoders.asset.dto.Sql;
import com.khoders.asset.dto.accounting.ExpenseDto;
import com.khoders.asset.entities.accounting.Expense;
import com.khoders.asset.entities.accounting.ExpenseItem;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.accounting.ExpenseMapper;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.AppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired private AppService appService;
    @Autowired private ExpenseMapper expenseMapper;
    @Autowired private NamedParameterJdbcTemplate jdbc;
    
    @Autowired
    public ExpenseService(JndiConfig jndiConfig) {
    	this.jdbc = new NamedParameterJdbcTemplate(jndiConfig.dataSource());
    }

    public ExpenseDto save(ExpenseDto dto)throws Exception{
        if (dto.getId() != null){
            Expense expense = appService.get(Expense.class, dto.getId());
            if (expense == null){
                throw new DataNotFoundException("Expense with ID: "+ dto.getId() +" Not Found");
            }
        }
        Expense expense = expenseMapper.toEntity(dto);
        if (appService.save(expense) != null){
            for(ExpenseItem expenseItem: expense.getExpenseItemList()){
                expenseItem.setExpense(expense);
                appService.save(expenseItem);
            }
        }
        return expenseMapper.toDto(expense);
    }
    public List<ExpenseDto> expenseList() throws Exception {
        List<ExpenseItem> expenseItemList;
        List<ExpenseDto> dtoList = new LinkedList<>();

        List<Expense> expenseList = appService.findAll(Expense.class);
        if (expenseList.isEmpty()){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        
        for (Expense expense : expenseList){
            SqlParameterSource param = new MapSqlParameterSource(ExpenseItem._expenseId, expense.getId());
            expenseItemList = jdbc.query(Sql.EXPENSE_ITEM_BY_EXPENSEID, param, BeanPropertyRowMapper.newInstance(ExpenseItem.class));
            expense.setExpenseItemList(expenseItemList);
            expenseList = new LinkedList<>();
            expenseList.add(expense);
        }
        for (Expense expense : expenseList){
            dtoList.add(expenseMapper.toDto(expense));
        }
        return dtoList;
    }
    public ExpenseDto findById(String expenseId) throws Exception {
        List<ExpenseItem> expenseItemList = new LinkedList<>();

        Expense expense = appService.findById(Expense.class, expenseId);
        if (expense == null){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        SqlParameterSource param = new MapSqlParameterSource(ExpenseItem._expenseId, expenseId);
        
        expenseItemList = jdbc.query(Sql.EXPENSE_ITEM_BY_EXPENSEID, param, BeanPropertyRowMapper.newInstance(ExpenseItem.class));
        expense.setExpenseItemList(expenseItemList);
        return expenseMapper.toDto(expense);

    }
    public boolean delete(String expenseId) throws Exception {
        appService.deleteById(Expense.class,expenseId);
        return true;
    }
    
    public final class ExpenseItemRows implements RowMapper<ExpenseItem>{

		@Override
		public ExpenseItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpenseItem expense = new ExpenseItem();
			return null;
		}
    	
    }
}
