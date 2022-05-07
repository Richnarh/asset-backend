package com.khoders.asset.entities.settings;

import com.khoders.asset.entities.Ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "logo")
public class Logo extends Ref {
    @Column(name = "logo")
    private byte[] logo;

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
