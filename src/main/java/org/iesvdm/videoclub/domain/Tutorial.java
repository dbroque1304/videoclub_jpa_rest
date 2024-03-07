package org.iesvdm.videoclub.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tutorial"
, schema = "videoclub_jpa"
, indexes = {@Index(name = "index_titulo", columnList = "titulo", unique = false)})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tutorial {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "titulo", length = 50)
    private String titulo;

    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Column(name = "publi")
    private Boolean publicado;

    @Column(nullable = false)
    private Date fechaPublicacion;

    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL)
    private Set<Comentario> comentarios;

    public Tutorial addComentario(Comentario comentario){
        this.comentarios.add(comentario);
        comentario.setTutorial(this);
        return this;
    }
    public Tutorial removeComentario(Comentario comentario){
        this.comentarios.remove(comentario);
        comentario.setTutorial(null);
        return this;
    }

}
