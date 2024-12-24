package com.kir138.servlet;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.eclipse.jetty.http.MetaData;

import java.io.IOException;

public class ReaderControllerTest {

    public void saveReaderShouldWork() throws IOException {

        OkHttpClient client = new OkHttpClient();

        String json = "{\"name\":\"Имя131\",\"email\":\"kirkir5@yandex.ru\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("/api/v1/readers")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

        }
    }
}
