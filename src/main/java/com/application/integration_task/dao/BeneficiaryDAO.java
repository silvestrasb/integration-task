package com.application.integration_task.dao;

import com.application.integration_task.entity.Beneficiary;

import java.util.List;

// Alternative DAO interface
public interface BeneficiaryDAO {

    List<Beneficiary> findAll();

    Beneficiary findById(int id);

    void save(Beneficiary beneficiary);

    void deleteById(int id);

}
