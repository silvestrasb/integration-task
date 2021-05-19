package com.application.integration_task.dao;

import com.application.integration_task.entity.Beneficiary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

// Alternative DAO
@Repository
public class BeneficiaryDAOJpaImpl implements BeneficiaryDAO {

    private final EntityManager entityManager;

    public BeneficiaryDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Beneficiary> findAll() {
        // create a query
        TypedQuery<Beneficiary> query =
                entityManager.createQuery("FROM Beneficiary ", Beneficiary.class);

        // return the results
        return query.getResultList();
    }

    @Override
    public Beneficiary findById(int theId) {

        return entityManager.find(Beneficiary.class, theId);

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
