/*package com.kir138.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.Main;
import com.kir138.connect.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class IntegrationTestBase {
    protected final OkHttpClient client = new OkHttpClient();
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @BeforeAll
    public static void setUpBeforeClass() {
        Runnable runnable = () -> {
            try {
                Main.main(new String[]{});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        new Thread(runnable).start();

        Thread.sleep(3000);
    }

    @AfterEach
    public void afterEach() {
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            entityManager.createQuery("delete from Book").executeUpdate();
            entityManager.createQuery("delete from Reader").executeUpdate();
            entityManager.getTransaction().commit();
        }
    }
}*/
