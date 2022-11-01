package com.enigmacamp.simplejpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        // 1. Insert data
        insertStudent(emf.createEntityManager());

        // 2. Query
        getAllStudent(emf.createEntityManager());

        //3. Find By Id
//        getStudentById(emf.createEntityManager(), "e15d988c-8e0a-4f98-85a6-a5f07daf50ee");

        //4. Find By major
//        getAllStudentByMajor(emf.createEntityManager(), "Dev");

        //4. Find In major
//        getAllStudentInMajor(emf.createEntityManager(), "DevOps", "Java");

        //5. Update student major
//        updateStudentMajor(emf.createEntityManager(), "7cb7a03c-7a3f-48df-8696-e8a11bde6cb6", "ReactJS");
        JpaUtil.shutdown();
    }

    private static void insertStudent(EntityManager entityManager) {
        try {
            Student student01 = new Student();
            student01.setFirstName("Sulton");
            student01.setLastName("Gonzales");
            student01.setGender(Gender.MALE);
            student01.setMajor("React");
            Date studentDob = new SimpleDateFormat("yyyy-MM-dd").parse("2005-04-07");
            student01.setBirthDate(studentDob);
            entityManager.getTransaction().begin();
            entityManager.persist(student01);
            entityManager.getTransaction().commit();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    private static void getAllStudent(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        List<Student> result = entityManager.createQuery("select s from Student s", Student.class).getResultList();
        for (Student s : result) {
            System.out.println(s);
        }
        entityManager.close();
    }

    private static void getAllStudentByMajor(EntityManager entityManager, String major) {
        entityManager.getTransaction().begin();
        TypedQuery<Student> query = entityManager.createQuery("select s from Student s where s.major like ?1", Student.class);
        query.setParameter(1, "%" + major + "%");
        for (Student s : query.getResultList()) {
            System.out.println(s);
        }
        entityManager.close();
    }

    private static void getAllStudentInMajor(EntityManager entityManager, String... majors) {
        entityManager.getTransaction().begin();
        TypedQuery<Student> query = entityManager.createQuery("select s from Student s where s.major in (:majors)", Student.class);
        query.setParameter("majors", Arrays.asList(majors));
        for (Student s : query.getResultList()) {
            System.out.println(s);
        }
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
        entityManager.close();
    }
}
