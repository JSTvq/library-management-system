package com.kir138.service;

import com.kir138.model.dto.ReaderRegistrationRq;
import com.kir138.model.entity.Reader;
import com.kir138.model.dto.ReaderDto;
import com.kir138.mapper.ReaderMapper;
import com.kir138.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    public ReaderDto saveOrUpdateReader(ReaderRegistrationRq request) {
        Reader reader = readerMapper.toReader(request);
        return readerMapper.toReader(readerRepository.save(reader));
    }

    public List<ReaderDto> getAllReaders() {
        return readerRepository.findAll()
                .stream()
                .map(readerMapper::toReader)
                .toList();
    }

    public ReaderDto getReaderById(Long id) {
        return readerRepository.findById(id)
                .map(readerMapper::toReader)
                .orElseThrow(() -> new IllegalArgumentException("Читатель с id = " + id + " не найден"));
    }

    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }
}
