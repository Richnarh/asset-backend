package com.khoders.asset.entities.accounts;

import com.khoders.asset.entities.Ref;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bills")
public class Bill extends Ref {
}
