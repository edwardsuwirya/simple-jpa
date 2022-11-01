package com.enigmacamp.simplejpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        JpaUtil.getEntityManagerFactory();
        // 1. Insert data
        insertStudent();

        // 2. Query
        getAllStudent();

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

    private static void insertStudent() {
        EntityManager entityManager = JpaUtil.getEntityManger();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            Student student01 = new Student();
            student01.setFirstName("Tika");
            student01.setLastName("Cleo");
            student01.setGender(Gender.FEMALE);
            student01.setMajor("Angular");
            Date studentDob = new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-07");
            student01.setBirthDate(studentDob);
            tx.begin();
            entityManager.persist(student01);
            tx.commit();
        } catch (ParseException e) {
            System.out.println("Failed because " + e.getMessage());
            tx.rollback();
        } catch (Exception e) {
            System.out.println("Failed because " + e.getMessage());
            tx.rollback();
        } finally {
            entityManager.close();
        }
    }

    private static void getAllStudent() {
        EntityManager entityManager = JpaUtil.getEntityManger();
        try {
            List<Student> result = entityManager.createQuery("select s from Student s", Student.class).getResultList();
            for (Student s : result) {
                System.out.println(s);
            }
        } catch (Exception e) {
            System.out.println("Failed because " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private static void getAllStudentByMajor(String major) {
        EntityManager entityManager = JpaUtil.getEntityManger();
        try {
            TypedQuery<Student> query = entityManager.createQuery("select s from Student s where s.major like ?1", Student.class);
            query.setParameter(1, "%" + major + "%");
            for (Student s : query.getResultList()) {
                System.out.println(s);
            }
        } catch (Exception e) {
            System.out.println("Failed because " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private static void getAllStudentInMajor(String... majors) {
        EntityManager entityManager = JpaUtil.getEntityManger();
        try {
            TypedQuery<Student> query = entityManager.createQuery("select s from Student s where s.major in (:majors)", Student.class);
            query.setParameter("majors", Arrays.asList(majors));
            for (Student s : query.getResultList()) {
                System.out.println(s);
            }

        } catch (Exception e) {
            System.out.println("Failed because " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private static void getStudentById(String id) {
        EntityManager entityManager = JpaUtil.getEntityManger();
        try {
            entityManager.getTransaction().begin();
            Student result = entityManager.find(Student.class, id);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Failed because " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private static void updateStudentMajor(String id, String newMajor) {
        EntityManager entityManager = JpaUtil.getEntityManger();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            Student result = entityManager.find(Student.class, id);
            result.setMajor(newMajor);
            tx.commit();
            entityManager.close();
        } catch (Exception e) {
            System.out.println("Failed because " + e.getMessage());
            tx.rollback();
        } finally {
            entityManager.close();
        }
    }
}
