package com.application.integration_task.dao;

import com.application.integration_task.entity.Beneficiary;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

// Alternative DAO
@Repository
public class BeneficiaryDAOHibernateImpl implements BeneficiaryDAO {

    // define field for entity manager
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Beneficiary> findAll() {

        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // create a query
        Query<Beneficiary> theQuery =
                currentSession.createQuery("FROM Beneficiary ", Beneficiary.class);

        // execute the query and get the result list
        List<Beneficiary> beneficiaries = theQuery.getResultList();

        // return the results
        return beneficiaries;
    }

    @Override
    public Beneficiary findById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // get the beneficiary
        Beneficiary beneficiary =
                currentSession.get(Beneficiary.class, id);

        // return the beneficiary
        return beneficiary;
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
