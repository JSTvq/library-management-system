/*
package com.kir138.controller;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.dto.ReaderDto;
import com.kir138.model.entity.Reader;
import jakarta.persistence.EntityManager;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ReaderControllerTest extends IntegrationTestBase {

    @SneakyThrows
    @Test
    public void saveReaderShouldWork() throws IOException {
        String json = "{\"name\": \"Имя131\", \"email\": \"kirkir5@yandex.ru\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/v1/readers")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String string = response.body().string();
            ReaderDto readerDto = objectMapper.readValue(string, ReaderDto.class);

            Assertions.assertThat(readerDto)
                                .usingRecursiveComparison()
                                .ignoringFields("id")
                                .isEqualTo(ReaderDto.builder()
                                .name("Имя131")
                                .email("kirkir5@yandex.ru")
                                .build());

            try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
                Reader reader = entityManager.find(Reader.class, readerDto.getId());
                Assertions.assertThat(reader).isNotNull();
                Assertions.assertThat(reader.getName()).isEqualTo("Имя131");
                Assertions.assertThat(reader.getEmail()).isEqualTo("kirkir5@yandex.ru");
            }
        }
    }

    @SneakyThrows
    @Test
    public void getReaderShouldWork() throws IOException {
        String json = "{\"name\": \"Имя131\", \"email\": \"kirkir5@yandex.ru\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request postRequest = new Request.Builder()
                .url("http://localhost:8080/api/v1/readers")
                .post(body)
                .build();

        Long readerId;
        try (Response postResponse = client.newCall(postRequest).execute()) {

            String string = null;
            if (postResponse.body() != null) {
                string = postResponse.body().string();
            }
            ReaderDto readerDto = objectMapper.readValue(string, ReaderDto.class);
            readerId = readerDto.getId();
        }

        Request getRequest = new Request.Builder()
                .url("http://localhost:8080/api/v1/readers?id=" + readerId)
                .get()
                .build();

        try (Response getResponse = client.newCall(getRequest).execute()) {
            assert getResponse.body() != null;
            String string = getResponse.body().string();
            ReaderDto readerDto = objectMapper.readValue(string, ReaderDto.class);

            Assertions.assertThat(readerDto)
                    .usingRecursiveComparison()
                    .isEqualTo(ReaderDto.builder()
                            .id(readerId)
                            .name("Имя131")
                            .email("kirkir5@yandex.ru")
                            .build());
        }
    }

    @SneakyThrows
    @Test
    public void deleteReaderShouldWork() throws IOException {

        String json = "{\"name\": \"Имя131\", \"email\": \"kirkir5@yandex.ru\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request postRequest = new Request.Builder()
                .url("http://localhost:8080/api/v1/readers")
                .post(body)
                .build();

        Long readerId;
        try (Response postResponse = client.newCall(postRequest).execute()) {
            assert postResponse.body() != null;
            String postResponseBody = postResponse.body().string();
            ReaderDto savedReader = objectMapper.readValue(postResponseBody, ReaderDto.class);
            readerId = savedReader.getId();
        }


        Request deleteRequest = new Request.Builder()
                .url("http://localhost:8080/api/v1/readers?id=" + readerId)
                .delete()
                .build();

        try (Response deleteResponse = client.newCall(deleteRequest).execute()) {
            Assertions.assertThat(deleteResponse.code()).isEqualTo(200);
        }


        Request getRequest = new Request.Builder()
                .url("http://localhost:8080/api/v1/readers?id=" + readerId)
                .get()
                .build();

        try (Response getResponse = client.newCall(getRequest).execute()) {
            Assertions.assertThat(getResponse.code()).isEqualTo(404);
        }
    }
}
*/
