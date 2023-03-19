package com.example.demoapp.service;

import com.example.demoapp.model.dto.request.PublisherDTO;
import com.example.demoapp.model.dto.response.PublisherResponseDto;
import com.example.demoapp.model.entity.Publisher;
import com.example.demoapp.repo.PublisherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl {
    private final PublisherRepo publisherRepo;

    public PublisherResponseDto mapToDto(Publisher publisher) {
        var pubi = new PublisherResponseDto();
        pubi.setId(publisher.getId());
        pubi.setName(publisher.getName());
        return pubi;
    }


    public List<PublisherResponseDto> getPublishers() {
        return publisherRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PublisherResponseDto getPublisher(Long id) {
        Optional<Publisher> publisherFind = publisherRepo.findById(id);
        return publisherFind
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
    }

    public PublisherResponseDto createPublisher(PublisherDTO publisherr) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherr.getName());
        return mapToDto(publisherRepo.save(publisher));
    }

    public PublisherResponseDto updatePublisher(PublisherDTO publisherr, Long id) {
        Publisher publisher = publisherRepo.findById(id).orElseThrow(() -> new RuntimeException("Incorect publisher"));
        publisher.setName(publisherr.getName());
        return mapToDto(publisherRepo.save(publisher));
    }

    public void deltePublisher(Long id) {
        publisherRepo.deleteById(id);
    }
}

