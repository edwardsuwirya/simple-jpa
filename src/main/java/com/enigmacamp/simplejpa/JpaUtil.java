package com.enigmacamp.simplejpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final String PERSISTENCE_UNIT_NAME = "default";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public static EntityManager getEntityManger() {
        if (factory != null) {
            return factory.createEntityManager();
        }
        throw new RuntimeException("Factory is null");
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
