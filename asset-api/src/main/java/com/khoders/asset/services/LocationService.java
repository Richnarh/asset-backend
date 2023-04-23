package com.khoders.asset.services;

import com.khoders.entities.Location;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private AppService appService;
    public Location save(Location location) {
        return appService.save(location);
    }
    public List<Location> locations() {
        return appService.findAll(Location.class);
    }

    public Location findById(String locationId) {
        return appService.findById(Location.class, locationId);
    }

    public boolean delete(String locationId) throws Exception {
        return appService.deleteById(Location.class, locationId);
    }
}
