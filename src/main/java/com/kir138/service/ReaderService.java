package com.kir138.service;

import com.kir138.exception.ErrorCode;
import com.kir138.exception.ServiceException;
import com.kir138.model.dto.ReaderRegistrationRq;
import com.kir138.model.entity.Reader;
import com.kir138.model.dto.ReaderDto;
import com.kir138.mapper.ReaderMapper;
import com.kir138.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    @Transactional
    public ReaderDto saveOrUpdateReader(ReaderRegistrationRq request) {
        return readerRepository.findById(request.getId())
                .map(getReader -> {
            getReader.setEmail(request.getEmail());
            getReader.setName(request.getName());
            return readerMapper.toReader(readerRepository.save(getReader));
        })
                .orElseGet(() -> {
                    Reader reader = readerMapper.toReader(request);
                    return readerMapper.toReader(readerRepository.save(reader));
                });
    }

    @Transactional(readOnly = true)
    public List<ReaderDto> getAllReaders() {
        return readerRepository.findAll()
                .stream()
                .map(readerMapper::toReader)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReaderDto getReaderById(Long id) {
        return readerRepository.findById(id)
                .map(readerMapper::toReader)
                .orElseThrow(() -> new ServiceException(ErrorCode.READER_NOT_FOUND_BY_ID, id));
    }

    @Transactional
    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }
}
