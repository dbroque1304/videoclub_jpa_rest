package org.iesvdm.videoclub.repository;

import org.iesvdm.videoclub.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoriaCustomRepository {

     abstract Page<Categoria> queryCustomCategoria(Optional<String> buscarOptional, Optional<String> ordenarOptional, Pageable pageable);
}
