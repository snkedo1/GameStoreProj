package com.example.demoapp.controller;

import com.example.demoapp.model.dto.request.GenreRequestDto;
import com.example.demoapp.model.dto.response.GenreResponseDto;
import com.example.demoapp.service.GenreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/genres")
public class GenreController {
    private final GenreServiceImpl genreService;

    @GetMapping("/all")
    public List<GenreResponseDto> getGenres(){
        return genreService.getAllGenres();
    }
    @GetMapping("/{name}")
    public List<GenreResponseDto> getGenreName(@PathVariable String name){
        return genreService.getGenre(name);
    }
    @PostMapping("/create")
    public GenreResponseDto createGenre(@RequestBody GenreRequestDto addGenre){
        return genreService.createGenre(addGenre);
    }
    @PutMapping("update/{id}")
    public  GenreResponseDto updateGenre(@RequestBody GenreRequestDto updateGenre,@PathVariable Long id){
        return genreService.updateGenre(updateGenre,id);
    }
}
