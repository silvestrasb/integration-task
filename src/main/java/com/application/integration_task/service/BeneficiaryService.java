package com.application.integration_task.service;

import com.application.integration_task.entity.Beneficiary;

import java.util.List;

public interface BeneficiaryService {
    List<Beneficiary> findAll();

    Beneficiary findById(int id);

    void deleteById(int id);

    void save(Beneficiary beneficiary);

    List<Beneficiary> findAllByName(String name);

}
