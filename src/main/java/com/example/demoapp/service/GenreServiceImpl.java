package com.example.demoapp.service;

import com.example.demoapp.model.dto.request.GenreRequestDto;
import com.example.demoapp.model.dto.response.GenreResponseDto;
import com.example.demoapp.model.entity.Genre;
import com.example.demoapp.repo.GenreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl {

    private final GenreRepo genreRepo;

    public List<GenreResponseDto> getAllGenres() {
        return genreRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public List<GenreResponseDto> getGenre(String name) {
        Optional<Genre> foundGenre = genreRepo.findByNameIs(name);
        return foundGenre.stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public GenreResponseDto createGenre(GenreRequestDto genreCreationRequestDto) {
        Genre newGenre = new Genre();
        newGenre.setName(genreCreationRequestDto.getName());
        return mapToDto(genreRepo.save(newGenre));
    }

    public GenreResponseDto updateGenre(GenreRequestDto genreRequestDto, Long id) {
        Genre genre = genreRepo.findById(id).orElseThrow(() -> new RuntimeException("Genre not found"));
        genre.setName(genreRequestDto.getName());
        return mapToDto(genreRepo.save(genre));
    }

    private GenreResponseDto mapToDto(Genre genreEntity) {
        var response = new GenreResponseDto();
        response.setId(genreEntity.getId());
        response.setName(genreEntity.getName());
        return response;
    }
}
