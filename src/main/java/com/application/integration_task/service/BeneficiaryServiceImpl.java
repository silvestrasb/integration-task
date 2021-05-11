package com.application.integration_task.service;

import com.application.integration_task.dao.BeneficiaryRepository;
import com.application.integration_task.entity.Beneficiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Override
    @Transactional
    public List<Beneficiary> findAll() {
        return beneficiaryRepository.findAllByOrderByNameAsc();
    }

    @Override
    @Transactional
    public Beneficiary findById(int id) {
        return beneficiaryRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        beneficiaryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Beneficiary beneficiary) {
        beneficiaryRepository.save(beneficiary);
    }

    @Override
    @Transactional
    public List<Beneficiary> findAllByName(String name) {
        return beneficiaryRepository.findBeneficiariesByNameOrderByNameAsc(name);
    }

    @Override
    @Transactional
    public Beneficiary findByUniqueCode(String uniqueCode) {
        return beneficiaryRepository.findBeneficiaryByUniqueCode(uniqueCode);
    }
}
