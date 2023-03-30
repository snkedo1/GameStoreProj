package com.example.demoapp.service;

import com.example.demoapp.model.dto.request.GameRequestDto;
import com.example.demoapp.model.dto.response.GameResponseDto;
import com.example.demoapp.model.entity.Game;
import com.example.demoapp.model.entity.Genre;
import com.example.demoapp.model.entity.Publisher;
import com.example.demoapp.repo.GameRepo;
import com.example.demoapp.repo.GenreRepo;
import com.example.demoapp.repo.PublisherRepo;
import com.example.demoapp.service.contract.GameService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GenreRepo genreRepo;
    private final GameRepo gameRepo;
    private final PublisherRepo publisherRepo;

    public List<GameResponseDto> getAllGames() {
        return gameRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public GameResponseDto buyGame(Long id){
        Game game= gameRepo.findById(id).orElseThrow(()->new RuntimeException("Invalid game"));
        int updatedStock=game.getStock()-1;
        game.setStock(updatedStock);
        Game saveGame=gameRepo.save(game);
        return mapToDto(saveGame);
    }

    public GameResponseDto mapToDto(Game game) {
        var dto = new GameResponseDto();
        dto.setId(game.getId());
        dto.setName(game.getName());
        dto.setPrice(game.getPrice());
        dto.setStock(game.getStock());
        dto.setPublisher(game.getPublisher().getName());
        dto.setGenre(game.getGenre().getName());
        return dto;
    }

    public GameResponseDto createGame(GameRequestDto requestDto) {
        Game game = new Game();
        game.setName(requestDto.getName());
        game.setPrice(requestDto.getPrice());
        game.setStock((requestDto.getStock()));
        String publisherName = requestDto.getPublisher();
        Publisher publisher = publisherRepo.findByNameIs(publisherName)
                .orElseThrow(() -> new RuntimeException("Published not found with name: " + publisherName));
        game.setPublisher(publisher);
        String genreName=requestDto.getGenre();
        Genre genre=genreRepo.findByNameIs(genreName)
                .orElseThrow(()->new RuntimeException("Genere not found"));
        game.setGenre(genre);
        Game savedGame = gameRepo.save(game);
        return mapToDto(savedGame);
    }

    public GameResponseDto getByName(String name) {
        Optional<Game> foundGame = gameRepo.findByNameIs(name);
        return foundGame
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Could not find game"));
    }
    public GameResponseDto updateGame(GameRequestDto request,Long id){
        Game game= gameRepo.findById(id).orElseThrow(()->new RuntimeException("Invalid game"));
        game.setName(request.getName());
        game.setPrice(request.getPrice());
        game.setStock((request.getStock()));
        if(game.getPublisher().getName().equals(request.getPublisher())&&game.getGenre().getName().equals(request.getGenre())){
            Game savedGame = gameRepo.save(game);
            return mapToDto(savedGame);
        }else{
            String publisherName = request.getPublisher();
            Publisher publisher = publisherRepo.findByNameIs(publisherName)
                    .orElseThrow(() -> new RuntimeException("Published not found with name: " + publisherName));
            game.setPublisher(publisher);
            String genreName=request.getGenre();
            Genre genre=genreRepo.findByNameIs(genreName)
                    .orElseThrow(()->new RuntimeException("Genre not existent"));
            game.setGenre(genre);
            Game savedGame = gameRepo.save(game);
            return mapToDto(savedGame);
        }

    }

    @Transactional
    public void deleteGame(String name) {
        gameRepo.deleteByNameIs(name);
    }
}

