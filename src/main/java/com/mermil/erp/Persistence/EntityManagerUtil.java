package com.mermil.erp.Persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MermilERPPersistenceUnit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
}
