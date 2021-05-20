package com.application.integration_task.service;

import com.application.integration_task.dao.BeneficiaryRepository;
import com.application.integration_task.entity.Beneficiary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;

    public BeneficiaryServiceImpl(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public List<Beneficiary> findAll() {
        return beneficiaryRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Beneficiary findById(int id) {
        return beneficiaryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        beneficiaryRepository.deleteById(id);
    }

    @Override
    public void save(Beneficiary beneficiary) {
        beneficiaryRepository.save(beneficiary);
    }

    @Override
    public List<Beneficiary> findAllByName(String name) {
        return beneficiaryRepository.findBeneficiariesByNameOrderByNameAsc(name);
    }

    @Override
    public Beneficiary findByUniqueCode(String uniqueCode) {
        return beneficiaryRepository.findBeneficiaryByUniqueCode(uniqueCode);

    }
}
