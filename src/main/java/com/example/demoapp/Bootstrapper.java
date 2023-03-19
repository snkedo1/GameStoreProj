package com.example.demoapp;

import com.example.demoapp.model.entity.*;
import com.example.demoapp.repo.*;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final GameRepo gameRepo;
    private final PublisherRepo publisherRepo;
    private final GenreRepo gameGenreRepom;
    private final UserRepo userRepo;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Publisher ubisoft = createUbisoftPublisher();
        Publisher activision=activisionPublisher();
        Game acGame = createACGame();
        User dorin=createUser();
        Genre genre=crateGenre();




        gameGenreRepom.save(genre);
        publisherRepo.save(activision);
        userRepo.save(dorin);
        publisherRepo.save(ubisoft);
        acGame.setPublisher(ubisoft);
        acGame.setGenre(genre);
        gameRepo.save(acGame);
    }

    private Publisher createUbisoftPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Ubisoft");
        return publisher;
    }
    private Publisher activisionPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Activision");
        return publisher;
    }

    private Game createACGame() {
        Game ac = new Game();
        ac.setName("AC");
        ac.setStock(1);
        ac.decreaseStock(1);
        ac.setPrice(5.0);
        return ac;
    }

    private User createUser(){
        User doru=new User();
        doru.setName("Dorin");
        doru.setPassword("Loko");
        return doru;
    }
    private Genre crateGenre(){
        Genre genre=new Genre();
        genre.setName("FPS");
        return genre;
    }


}
