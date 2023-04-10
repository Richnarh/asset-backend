package com.khoders.asset.services;

import com.khoders.asset.dto.accounting.InvoiceDto;
import com.khoders.asset.entities.accounting.Invoice;
import com.khoders.asset.entities.accounting.InvoiceItem;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.accounting.InvoiceExtractMapper;
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
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class InvoiceService {
    @Autowired private CrudBuilder builder;
    @Autowired private InvoiceExtractMapper extractMapper;

    public InvoiceDto saveInvoice(InvoiceDto dto)throws Exception{
        if (dto.getId() != null){
            Invoice invoice = builder.simpleFind(Invoice.class, dto.getId());
            if (invoice == null){
                throw new DataNotFoundException("Invoice with ID: "+ dto.getId() +" Not Found");
            }
        }
        Invoice invoice = extractMapper.toEntity(dto);
        if (builder.save(invoice) != null){
            for(InvoiceItem invoiceItem: invoice.getInvoiceItemList()){
                invoiceItem.setInvoice(invoice);
                builder.save(invoiceItem);
            }
        }
        return extractMapper.toDto(invoice);
    }

    public List<InvoiceDto> invoiceList() throws Exception {
        Session session = builder.session();

        List<InvoiceItem> invoiceItemList;
        List<InvoiceDto> dtoList = new LinkedList<>();

        List<Invoice> invoiceList = builder.findAll(Invoice.class);

        if (invoiceList.isEmpty()){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
            try {
                for (Invoice invoice:invoiceList){
                    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                    CriteriaQuery<InvoiceItem> criteriaQuery = criteriaBuilder.createQuery(InvoiceItem.class);
                    Root<InvoiceItem> root = criteriaQuery.from(InvoiceItem.class);
                    criteriaQuery.where(criteriaBuilder.equal(root.get(InvoiceItem._invoice), invoice));
                    Query<InvoiceItem> query = session.createQuery(criteriaQuery);
                    invoiceItemList = query.getResultList();
                    invoice.setInvoiceItemList(invoiceItemList);
                    invoiceList = new LinkedList<>();
                    invoiceList.add(invoice);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            for (Invoice invoice:invoiceList){
                dtoList.add(extractMapper.toDto(invoice));
            }
            return dtoList;
    }

    public InvoiceDto findById(String invoiceId)throws Exception{
        Session session = builder.session();
        List<InvoiceItem> invoiceItemList;

        Invoice invoice = builder.simpleFind(Invoice.class, invoiceId);

        if (invoice == null){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
            try {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<InvoiceItem> criteriaQuery = criteriaBuilder.createQuery(InvoiceItem.class);
                Root<InvoiceItem> root = criteriaQuery.from(InvoiceItem.class);
                criteriaQuery.where(criteriaBuilder.equal(root.get(InvoiceItem._invoice), invoice));
                Query<InvoiceItem> query = session.createQuery(criteriaQuery);
                invoiceItemList = query.getResultList();
                invoice.setInvoiceItemList(invoiceItemList);
                return extractMapper.toDto(invoice);
            }catch (Exception e){
                e.printStackTrace();
            }
        return null;
    }
    public boolean delete(String invoiceId) throws Exception {
        return builder.deleteById(invoiceId, Invoice.class);
    }
}
