package com.application.integration_task.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

// Lombok annotations to minimize boiler-plate code
@Getter
@Setter
@NoArgsConstructor

// Swagger annotations
@ApiModel(description = "Details about the beneficiary.")

// Database annotations
@Entity
@Table(name = "beneficiary")
public class Beneficiary {

    @ApiModelProperty(notes = "The primary key of the beneficiary.")
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(notes = "The unique code of the beneficiary (Max length 36).")
    @Column(name = "uniq_code", unique = true)
    private String uniqueCode;

    @ApiModelProperty(notes = "The beneficiary's name (Max length 40).")
    @Column(name = "name")
    private String name;

    public Beneficiary(String name) {
        this.name = name;
        this.uniqueCode = UUID.randomUUID().toString();
    }

    public Beneficiary(String uniqueCode, String name) {
        this.uniqueCode = uniqueCode;
        this.name = name;
    }
}
