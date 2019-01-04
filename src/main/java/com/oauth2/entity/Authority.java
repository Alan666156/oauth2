package com.oauth2.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Authority extends AbstractEntity{

    private String name;

}
