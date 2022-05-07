package com.khoders.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan(basePackages = {"com.khoders.asset.mapper"}, basePackageClasses = {CrudBuilder.class})
//@EntityScan(basePackages = {"com.khoders.asset.entities"})
@SpringBootApplication
public class AssetApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class, args);
    }
}
