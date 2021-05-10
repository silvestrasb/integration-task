package com.application.integration_task.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// Lombok annotations to minimize boiler-plate code
@Getter
@Setter
@NoArgsConstructor

// Database annotations
@Entity
@Table(name = "beneficiary")
public class Beneficiary {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uniq_code", unique = true)
    private String uniqueCode;

    @Column(name = "name")
    private String name;

    public Beneficiary(String uniqueCode, String name) {
        this.uniqueCode = uniqueCode;
        this.name = name;
    }
}
