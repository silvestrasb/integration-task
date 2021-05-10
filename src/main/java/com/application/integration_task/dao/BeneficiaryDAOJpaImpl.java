package com.application.integration_task.dao;

import com.application.integration_task.entity.Beneficiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

// Alternative DAO
@Repository
public class BeneficiaryDAOJpaImpl implements BeneficiaryDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Beneficiary> findAll() {
        // create a query
        TypedQuery<Beneficiary> query =
                entityManager.createQuery("FROM Beneficiary ", Beneficiary.class);

        // execute the query and get the result list
        List<Beneficiary> beneficiaries = query.getResultList();

        // return the results
        return beneficiaries;
    }

    @Override
    public Beneficiary findById(int theId) {

        // get beneficiary
        Beneficiary beneficiary = entityManager.find(Beneficiary.class, theId);

        // return the result
        return beneficiary;
    }

    @Override
    public void save(Beneficiary beneficiary) {
        // save or update the beneficiary
        Beneficiary dbBeneficiary = entityManager.merge(beneficiary);

        // update with id from db ... so we can get generated id for save/insert
        beneficiary.setId(dbBeneficiary.getId());
    }

    @Override
    public void deleteById(int id) {
        // create a query
        Query query =
                entityManager.createQuery("DELETE FROM Beneficiary WHERE id=:beneficiaryId");

        query.setParameter("beneficiaryId", id);

        query.executeUpdate();

    }
}
