package org.iesvdm.videoclub.domain;

import jakarta.persistence.Id;

public class Socio {
    @Id
    private int dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Long tarjeta;


}
