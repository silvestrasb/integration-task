package com.application.integration_task.dao;

import com.application.integration_task.entity.Beneficiary;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

// Alternative DAO
@Repository
public class BeneficiaryDAOHibernateImpl implements BeneficiaryDAO {

    // define field for entity manager
    private final EntityManager entityManager;

    public BeneficiaryDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Beneficiary> findAll() {

        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // create a query
        Query<Beneficiary> theQuery =
                currentSession.createQuery("FROM Beneficiary ", Beneficiary.class);

        // return the results
        return theQuery.getResultList();
    }

    @Override
    public Beneficiary findById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // return the beneficiary
        return currentSession.get(Beneficiary.class, id);
    }

    @Override
    public void save(Beneficiary beneficiary) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // save the beneficiary
        currentSession.saveOrUpdate(beneficiary);
    }

    @Override
    public void deleteById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // delete the object with primary key
        Query theQuery =
                currentSession.createQuery("DELETE FROM Beneficiary WHERE id=:beneficiaryID");
        theQuery.setParameter("beneficiaryID", id);

        theQuery.executeUpdate();

        // below code might work i'm not completely sure
//        currentSession.delete(id);
    }

}
