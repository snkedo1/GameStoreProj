package com.example.demoapp.controller;

import com.example.demoapp.model.dto.request.PublisherDTO;
import com.example.demoapp.model.dto.response.PublisherResponseDto;
import com.example.demoapp.service.PublisherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherServiceImpl publisherService;

    @GetMapping("/all")
    public List<PublisherResponseDto> getAllPublishers(){
        return publisherService.getPublishers();
    }
    @GetMapping("/{id}")
    public PublisherResponseDto getPublisher(@PathVariable  Long id){
        return publisherService.getPublisher(id);
    }
    @PostMapping("/create")
    public PublisherResponseDto createPublisher(@RequestBody PublisherDTO publisher){
        return publisherService.createPublisher(publisher);
    }
    @PutMapping("/update/{id}")
    public PublisherResponseDto updatePublisher(@RequestBody PublisherDTO publisher,@PathVariable Long id){
        return publisherService.updatePublisher(publisher,id);
    }
    @DeleteMapping("/delete/{id}")
    public void deletePublisher(@PathVariable Long id){
        publisherService.deltePublisher(id);
    }
}

