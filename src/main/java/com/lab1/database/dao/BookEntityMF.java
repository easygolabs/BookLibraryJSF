package com.lab1.database.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class BookEntityMF {

    private EntityManagerFactory emFactory;

    EntityManager getEntityManager(){
        emFactory = Persistence.createEntityManagerFactory("BookPersistenceUnit");
        return emFactory.createEntityManager();
    }

    void close(){
        emFactory.close();
    }
}