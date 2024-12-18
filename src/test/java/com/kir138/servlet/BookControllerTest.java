package com.kir138.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.BookDto;
import com.kir138.model.dto.BookRegistrationRq;
import com.kir138.service.BookService;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTest {

    private BookService bookService;
    private ObjectMapper objectMapper;
    private BookController bookController;

    @BeforeAll
    public void setUp() {
        bookService = mock(BookService.class);
        objectMapper = new ObjectMapper();
        bookController = new BookController(bookService, objectMapper);
    }

    @Test
    public void doPost_ShouldAddBook() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String jsonRequest = "{\"title\":\"Книга1\",\"author\":\"Автор1\",\"year\":2020}";
        InputStream inputStream = new ByteArrayInputStream(jsonRequest.getBytes(StandardCharsets.UTF_8));
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(inputStream));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        BookDto bookDto = BookDto.builder()
                .title("Книга1")
                .author("Автор1")
                .build();

        when(bookService.saveOrUpdateBook(any(BookRegistrationRq.class))).thenReturn(bookDto);

        bookController.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        String jsonResponse = stringWriter.toString();
        assertThat(jsonResponse).isEqualTo(objectMapper.writeValueAsString(bookDto));
    }

    private static class DelegatingServletInputStream extends ServletInputStream {
        private final InputStream sourceStream;

        public DelegatingServletInputStream(InputStream sourceStream) {
            this.sourceStream = sourceStream;
        }

        @Override
        public int read() throws IOException {
            return sourceStream.read();
        }

        @Override
        public boolean isFinished() {
            try {
                return sourceStream.available() == 0;
            } catch (IOException e) {
                return true;
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}
