package com.khoders.asset.services;

import com.khoders.asset.entities.AssetRequestApproval;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class AssetRequestApprovalService {
    @Autowired
    private CrudBuilder builder;

    public AssetRequestApproval save(AssetRequestApproval requestApproval) {
        return builder.save(requestApproval);
    }

    public List<AssetRequestApproval> requestApprovals() {
        return builder.findAll(AssetRequestApproval.class);
    }

    public AssetRequestApproval findById(String requestApprovalId) {
        return builder.findOne(requestApprovalId, AssetRequestApproval.class);
    }

    public boolean delete(String requestApprovalId) {
        return builder.deleteById(requestApprovalId, AssetRequestApproval.class);
    }
}
