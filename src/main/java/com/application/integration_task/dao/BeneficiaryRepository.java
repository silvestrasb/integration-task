package com.application.integration_task.dao;

import com.application.integration_task.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Main DAO
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {

    List<Beneficiary> findAllByOrderByNameAsc();

    List<Beneficiary> findBeneficiariesByNameOrderByNameAsc(String name);

    Beneficiary findBeneficiaryByUniqueCode(String name);
}
