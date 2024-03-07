package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.exception.CategoriaNotFoundException;
import org.iesvdm.videoclub.exception.PeliculaNotFoundException;
import org.iesvdm.videoclub.repository.CategoriaRepository;
import org.iesvdm.videoclub.repository.PeliculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> all() {
        this.categoriaRepository.findAll().forEach(this::conteoPeliculas);
        return this.categoriaRepository.findAll();
    }

    public Categoria save(Categoria categoria) {
        this.conteoPeliculas(categoria);
        return this.categoriaRepository.save(categoria);
    }

    public Categoria one(Long id) {
        this.conteoPeliculas(this.categoriaRepository.findById(id).get());
        return this.categoriaRepository.findById(id)
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    public Categoria replace(Long id, Categoria categoria) {
        this.conteoPeliculas(categoria);
        return this.categoriaRepository.findById(id).map( p -> (id.equals(categoria.getId())  ?
                                                            this.categoriaRepository.save(categoria) : null))
                .orElseThrow(() -> new CategoriaNotFoundException(id));

    }

    public void delete(Long id) {
        this.conteoPeliculas(this.categoriaRepository.findById(id).get());
        this.categoriaRepository.findById(id).map(p -> {this.categoriaRepository.delete(p);
                                                        return p;})
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    public Categoria conteoPeliculas(Categoria categoria){
        categoria.setConteoPeliculas(categoria.getPeliculas().size());
        return categoria;
    }

}
