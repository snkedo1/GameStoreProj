package com.example.demoapp;

import com.example.demoapp.model.entity.*;
import com.example.demoapp.model.enums.ERole;
import com.example.demoapp.repo.*;
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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Publisher ubisoft = createUbisoftPublisher();
        Publisher activision=activisionPublisher();
        Game acGame = createACGame();
        Genre genre=crateGenre();
        saveRoles();

        gameGenreRepom.save(genre);
        publisherRepo.save(activision);
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
        ac.setStock(12);
        ac.decreaseStock(1);
        ac.setPrice(5.0);
        return ac;
    }

    private Genre crateGenre(){
        Genre genre=new Genre();
        genre.setName("FPS");
        return genre;
    }

    private void saveRoles(){
        for(ERole value : ERole.values()){
            roleRepository.save(
                    Role.builder().name(value).build()
            );
        }
    }

}
