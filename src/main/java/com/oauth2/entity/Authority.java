package com.oauth2.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 权限信息
 * @author fuhx
 */
@Entity
@Data
public class Authority {

    @Id
    @NotNull
    @Size(min = 0, max = 50)
    private String name;

}
