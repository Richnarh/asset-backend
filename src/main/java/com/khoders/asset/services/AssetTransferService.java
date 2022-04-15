package com.khoders.asset.services;

import com.khoders.asset.entities.AssetTransfer;
import com.khoders.asset.utils.CrudBuilder;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@Repository
public class AssetTransferService {
    private static final Logger log = LoggerFactory.getLogger(AssetTransferService.class);
    @Autowired
    private CrudBuilder builder;

    public AssetTransfer saveTransfer(AssetTransfer assetTransfer) {
        return builder.save(assetTransfer);
    }
    public List<AssetTransfer> transferList() {return builder.findAll(AssetTransfer.class);}
    public AssetTransfer findById(String id) {
        return builder.findOne(id, AssetTransfer.class);
    }
    public boolean delete(String transferId) {return builder.deleteById(transferId, AssetTransfer.class);}
}
