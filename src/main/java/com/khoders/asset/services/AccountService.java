package com.khoders.asset.services;

import com.khoders.asset.dto.accounting.AccountDto;
import com.khoders.asset.dto.accounting.BillDto;
import com.khoders.asset.dto.accounting.BillPaymentDto;
import com.khoders.asset.entities.accounting.Account;
import com.khoders.asset.entities.accounting.Bill;
import com.khoders.asset.entities.accounting.BillItem;
import com.khoders.asset.entities.accounting.BillPayment;
import com.khoders.asset.mapper.accounting.BillExtractMapper;
import com.khoders.asset.mapper.accounting.AccountMapper;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class AccountService {
    @Autowired private CrudBuilder builder;
    @Autowired private BillExtractMapper extractMapper;
    @Autowired private AccountMapper mapper;

    public BillDto saveBill(BillDto dto){
        if (dto.getId() != null){
            Bill bill = builder.simpleFind(Bill.class, dto.getId());
            if (bill == null){
                throw new DataNotFoundException("Bill with ID: "+ dto.getId() +" Not Found");
            }
        }
        Bill bill = extractMapper.toEntity(dto);
        if (builder.save(bill) != null){
            for(BillItem billItem: bill.getBillItemList()){
                billItem.setBill(bill);
                builder.save(billItem);
            }
        }
        return extractMapper.toDto(bill);
    }

    public List<BillDto> billList(){
        Session session = builder.session();

        List<BillItem> billItemList;
        List<BillDto> dtoList = new LinkedList<>();

        List<Bill> billList = builder.findAll(Bill.class);
        if (billList != null && !billList.isEmpty()){
           try {
               for (Bill bill:billList){
                   CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                   CriteriaQuery<BillItem> criteriaQuery = criteriaBuilder.createQuery(BillItem.class);
                   Root<BillItem> root = criteriaQuery.from(BillItem.class);
                   criteriaQuery.where(criteriaBuilder.equal(root.get(BillItem._bill), bill));
                   Query<BillItem> query = session.createQuery(criteriaQuery);
                   billItemList = query.getResultList();
                   bill.setBillItemList(billItemList);
                   billList = new LinkedList<>();
                   billList.add(bill);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
            for (Bill bill:billList){
                dtoList.add(extractMapper.toDto(bill));
            }
            return dtoList;
        }
        return Collections.emptyList();
    }
    public BillDto findById(String billId){
        Session session = builder.session();
        List<BillItem> billItemList;

        Bill bill = builder.simpleFind(Bill.class, billId);

        if (bill != null){
            try {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<BillItem> criteriaQuery = criteriaBuilder.createQuery(BillItem.class);
                Root<BillItem> root = criteriaQuery.from(BillItem.class);
                criteriaQuery.where(criteriaBuilder.equal(root.get(BillItem._bill), bill));
                Query<BillItem> query = session.createQuery(criteriaQuery);
                billItemList = query.getResultList();
                bill.setBillItemList(billItemList);
                return extractMapper.toDto(bill);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public AccountDto saveAccount(AccountDto dto){
        if (dto.getId() != null){
            Account account = builder.simpleFind(Account.class, dto.getId());
            if (account == null){
                throw new DataNotFoundException("Account with ID: "+ dto.getId() +" Not Found");
            }
        }
        Account account= mapper.toEntity(dto);
        if (builder.save(account) != null){
            return mapper.tDto(account);
        }
        return  null;
    }

    public BillPaymentDto saveBillPayment(BillPaymentDto dto){
        if (dto.getId() != null){
            BillPayment billPayment = builder.simpleFind(BillPayment.class, dto.getId());
            if (billPayment == null){
                throw new DataNotFoundException("BillPayment with ID: "+ dto.getId() +" Not Found");
            }
        }
        BillPayment billPayment = extractMapper.toEntity(dto);
        if (builder.save(billPayment) != null){
           return extractMapper.toDto(billPayment);
        }
        return null;
    }

    public List<BillPaymentDto> findByBill(String billId){
        Session session = builder.session();
        List<BillPayment> billPaymentList;
        List<BillPaymentDto> dtoList = new LinkedList<>();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BillPayment> criteriaQuery = criteriaBuilder.createQuery(BillPayment.class);
            Root<BillPayment> root = criteriaQuery.from(BillPayment.class);
            criteriaQuery.where(criteriaBuilder.equal(root.get(BillPayment._bill), billId));
            Query<BillPayment> query = session.createQuery(criteriaQuery);
            billPaymentList = query.getResultList();

            for (BillPayment payment: billPaymentList){
                dtoList.add(extractMapper.toDto(payment));
            }
            return dtoList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public boolean deletePayment(String billPaymentId){
        return builder.deleteById(billPaymentId, BillPayment.class);
    }
}
