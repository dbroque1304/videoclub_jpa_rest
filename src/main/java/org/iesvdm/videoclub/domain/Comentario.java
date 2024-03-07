package org.iesvdm.videoclub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Comentario {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;
    @ManyToOne
    @JoinColumn(name = "tutorial_id_fk", nullable = false, foreignKey = @ForeignKey(name = "id_tutorial"))
    @JsonIgnore
    @ToString.Exclude
    Tutorial tutorial;
}
