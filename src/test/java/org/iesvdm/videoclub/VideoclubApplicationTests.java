package org.iesvdm.videoclub;

import jakarta.transaction.Transactional;
import org.iesvdm.videoclub.domain.Comentario;
import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@SpringBootTest
class VideoclubApplicationTests {

    @Autowired
    TutorialRepository tutorialRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    void pruebaOneToManyTutorial(){
        var tutorialList =  tutorialRepository.findAll();
        tutorialList.forEach(System.out::println);
    }

    @Test
    void pruebaGrabarOneToMany(){
        Tutorial tutorial = Tutorial.builder()
                .titulo("Título de tutorial")
                .publicado(true)
                .descripcion("La descrición del Tutorial")
                .fechaPublicacion(new Date())
                .comentarios(new HashSet<>())
                .build();
        Comentario comentario = Comentario.builder()
                .text("Comentario1")
                .build();
        Comentario comentario2 = Comentario.builder()
                .id(2)
                .text("Comentario2")
                .build();
        tutorial.addComentario(comentario).addComentario(comentario2);
        tutorialRepository.save(tutorial);
    }
    @Test
    @Transactional
    void pruebaEliminarOneToMany(){
        var optionaltutorial = this.tutorialRepository.findById(1L);
        optionaltutorial.ifPresent(tutorial -> {
                tutorial
                        .getComentarios()
                        .forEach(System.out::println);

                var optionalComentario = tutorial.getComentarios().stream().findFirst();

                tutorial.removeComentario(optionalComentario.get());
                this.tutorialRepository.save(tutorial);
        });
        this.tutorialRepository.delete(optionaltutorial.get());
        this.tutorialRepository.flush();
    }
}
