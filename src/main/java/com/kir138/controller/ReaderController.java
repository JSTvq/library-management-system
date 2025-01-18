package com.kir138.controller;

import com.kir138.model.dto.ReaderDto;
import com.kir138.model.dto.ReaderRegistrationRq;
import com.kir138.service.ReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

//curl GET http://localhost:8080/api/v1/readers/1
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/readers")
@RestController
public class ReaderController {
    private final ReaderService readerService;
    MyExceptionHandler myExceptionHandler;

    @GetMapping("/{id}")
    public ReaderDto getReaderById(@PathVariable Long id) {
        log.info("GET /api/v1/readers/{} - получение книги по id", id);
       ReaderDto SS = readerService.getReaderById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReaderDto> saveOrUpdateReader(@RequestBody ReaderRegistrationRq readerRegistrationRq) {
        try {
            log.info("POST /api/v1/readers - начало обработки запроса");
            ReaderDto readerDto = readerService.saveOrUpdateReader(readerRegistrationRq);
            log.info("POST /api/v1/readers - успешно добавлен/обновлен " +
                    "читатель с id {}", readerDto.getId());
            return ResponseEntity.ok(readerDto);
        } catch (RuntimeException e) {

        }
    }

    @GetMapping
    public List<ReaderDto> getAllReaders() {
        log.info("GET /api/v1/readers - получение всех читателей");
        return readerService.getAllReaders();
    }

    @DeleteMapping("/{id}")
    public void deleteReader(@PathVariable Long id) {
        log.info("DELETE /api/v1/readers/{} - удаление читателя по id", id);
        readerService.deleteReader(id);
    }
}


