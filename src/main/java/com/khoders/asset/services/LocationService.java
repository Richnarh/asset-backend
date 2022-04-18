package com.khoders.asset.services;

import com.khoders.asset.entities.Location;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class LocationService {
    @Autowired
    private CrudBuilder builder;

    public Location save(Location location) {
        return builder.save(location);
    }

    public List<Location> locations() {
        return builder.findAll(Location.class);
    }

    public Location findById(String locationId) {
        return builder.simpleFind(Location.class, locationId);
    }

    public boolean delete(String locationId) {
        return builder.deleteById(locationId, Location.class);
    }
}
