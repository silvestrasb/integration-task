package com.application.integration_task.dao;

import com.application.integration_task.entity.Beneficiary;

import java.util.List;

// Alternative DAO interface
public interface BeneficiaryDAO {

    public List<Beneficiary> findAll();

    public Beneficiary findById(int id);

    public void save(Beneficiary beneficiary);

    public void deleteById(int id);

}
