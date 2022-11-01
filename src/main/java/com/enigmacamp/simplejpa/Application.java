package com.enigmacamp.simplejpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 1. Insert data
//        insertStudent(entityManager);

        // 2. Query
//        getAllStudent(entityManager);

        //3. Find By Id
//        getStudentById(entityManager, "e15d988c-8e0a-4f98-85a6-a5f07daf50ee");

        //4. Find By major
//        getAllStudentByMajor(entityManager, "Dev");

        //4. Find In major
//        getAllStudentInMajor(entityManager, "DevOps", "Java");

        //5. Update student major
        updateStudentMajor(entityManager, "7cb7a03c-7a3f-48df-8696-e8a11bde6cb6", "ReactJS");
    }

    private static void insertStudent(EntityManager entityManager) {
        Student student01 = new Student();
        student01.setFirstName("Tika");
        student01.setLastName("Yesi");
        student01.setGender("F");
        student01.setMajor("Java");
        entityManager.getTransaction().begin();
        entityManager.persist(student01);
        entityManager.getTransaction().commit();
        entityManager.getEntityManagerFactory().close();
        entityManager.close();

    }

    private static void getAllStudent(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        List<Student> result = entityManager.createQuery("select s from Student s", Student.class).getResultList();
        for (Student s : result) {
            System.out.println(s);
        }
        entityManager.getEntityManagerFactory().close();
        entityManager.close();
    }

    private static void getAllStudentByMajor(EntityManager entityManager, String major) {
        entityManager.getTransaction().begin();
        TypedQuery<Student> query = entityManager.createQuery("select s from Student s where s.major like ?1", Student.class);
        query.setParameter(1, "%" + major + "%");
        for (Student s : query.getResultList()) {
            System.out.println(s);
        }
        entityManager.getEntityManagerFactory().close();
        entityManager.close();
    }

    private static void getAllStudentInMajor(EntityManager entityManager, String... majors) {
        entityManager.getTransaction().begin();
        TypedQuery<Student> query = entityManager.createQuery("select s from Student s where s.major in (:majors)", Student.class);
        query.setParameter("majors", Arrays.asList(majors));
        for (Student s : query.getResultList()) {
            System.out.println(s);
        }
        entityManager.getEntityManagerFactory().close();
        entityManager.close();
    }

    private static void getStudentById(EntityManager entityManager, String id) {
        entityManager.getTransaction().begin();
        Student result = entityManager.find(Student.class, id);
        System.out.println(result);
        entityManager.getEntityManagerFactory().close();
        entityManager.close();
    }

    private static void updateStudentMajor(EntityManager entityManager, String id, String newMajor) {
        entityManager.getTransaction().begin();
        Student result = entityManager.find(Student.class, id);
        result.setMajor(newMajor);
        entityManager.getTransaction().commit();
        entityManager.getEntityManagerFactory().close();
        entityManager.close();
    }
}
