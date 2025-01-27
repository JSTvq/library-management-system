package com.kir138.controller;

import com.kir138.testTransaction.PendingBookProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/v1/books/updateProcessor")
public class PendingBookProcessorController {
    private final PendingBookProcessor pendingBookProcessor;

    @PostMapping("/up")
    public void updateBookProcessor() {
        pendingBookProcessor.processPendingBooks();
    }
}
