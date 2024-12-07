package com.kir138.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BookRepositoryImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private BookRepositoryImpl bookRepository;

    //выполняется перед всеми методами один раз
    @BeforeAll
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("library-test");
    }

    //выполняется после всех методов один раз
    @AfterAll
    public static void tearDownAfterClass() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    //выполняется перед каждым методом
    @BeforeEach
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        bookRepository = new BookRepositoryImpl();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Book").executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void testSave() {

    }


}
