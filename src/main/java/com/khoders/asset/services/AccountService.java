package com.khoders.asset.services;

import com.khoders.asset.dto.accounting.AccountDto;
import com.khoders.asset.dto.accounting.BillDto;
import com.khoders.asset.dto.accounting.PaymentDto;
import com.khoders.asset.entities.accounting.Account;
import com.khoders.asset.entities.accounting.Bill;
import com.khoders.asset.entities.accounting.BillItem;
import com.khoders.asset.entities.accounting.Payment;
import com.khoders.asset.entities.constants.PaymentType;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.accounting.BillExtractMapper;
import com.khoders.asset.mapper.accounting.AccountMapper;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.utilities.Msg;
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

    public BillDto saveBill(BillDto dto)throws Exception{
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
                   List<BillItem> billItemList = query.getResultList();
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

    public AccountDto saveAccount(AccountDto dto)throws Exception{
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
    public AccountDto findAccount(String accountId)throws Exception{
        Account account = builder.simpleFind(Account.class, accountId);
        if (account == null){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        return mapper.tDto(account);
    }
    public List<AccountDto> accountList()throws Exception{
        List<Account> accounts = builder.findAll(Account.class);
        if (accounts.isEmpty()){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        List<AccountDto> dtoList = new LinkedList<>();
        for (Account account:accounts){
            dtoList.add(mapper.tDto(account));
        }
        return dtoList;
    }

    public PaymentDto savePayment(PaymentDto dto)throws Exception{
        if (dto.getId() != null){
            Payment payment = builder.simpleFind(Payment.class, dto.getId());
            if (payment == null){
                throw new DataNotFoundException("Payment with ID: "+ dto.getId() +" Not Found");
            }
        }
        Payment payment = extractMapper.toEntity(dto);
        if (builder.save(payment) != null){
           return extractMapper.toDto(payment);
        }
        return null;
    }

    // Payment
    public List<PaymentDto> find(String billOrInvoice, String type){
        Session session = builder.session();
        List<Payment> paymentList;
        List<PaymentDto> dtoList = new LinkedList<>();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Payment> criteriaQuery = criteriaBuilder.createQuery(Payment.class);
            Root<Payment> root = criteriaQuery.from(Payment.class);
            if ((PaymentType.BILL_PAYMENT == PaymentType.valueOf(type))){
                criteriaQuery.where(criteriaBuilder.equal(root.get(Payment._bill), billOrInvoice));
            }else{
                criteriaQuery.where(criteriaBuilder.equal(root.get(Payment._invoice), billOrInvoice));
            }
            Query<Payment> query = session.createQuery(criteriaQuery);
            paymentList = query.getResultList();

            for (Payment payment: paymentList){
                dtoList.add(extractMapper.toDto(payment));
            }
            return dtoList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public boolean delete(String accountId) throws Exception {
        return builder.deleteById(accountId, Account.class);
    }
    public boolean deleteBill(String billId) throws Exception {
        return builder.deleteById(billId, Bill.class);
    }
    public boolean deletePayment(String paymentId) throws Exception {
        return builder.deleteById(paymentId, Payment.class);
    }
}
