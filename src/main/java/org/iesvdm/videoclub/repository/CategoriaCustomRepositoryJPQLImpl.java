package org.iesvdm.videoclub.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.iesvdm.videoclub.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaCustomRepositoryJPQLImpl implements CategoriaCustomRepository {

    @Autowired
    private EntityManager em;

    @Override
    public Page<Categoria> queryCustomCategoria(Optional<String> buscarOptional, Optional<String> ordenarOptional, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("SELECT C from Categoria C");
        if (buscarOptional.isPresent()) {
            queryBuilder.append(" ").append("WHERE C.nombre like :nombre");
        }
        if (ordenarOptional.isPresent()) {
            if (buscarOptional.isPresent() && "asc".equalsIgnoreCase(buscarOptional.get())) {
                queryBuilder.append(" ").append("ORDER BY C.nombre ASC");
            } else if (buscarOptional.isPresent() && "desc".equalsIgnoreCase(buscarOptional.get())) {
                queryBuilder.append(" ").append("ORDER BY C.nombre DESC");
            }
        }
        Query query = em.createQuery(queryBuilder.toString());
        if (buscarOptional.isPresent()) {
            query.setParameter("nombre", "%" + buscarOptional.get() + "%");
        }

        // Aplicar paginación
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Categoria> categorias = query.getResultList();
        long total = contarTotalCategorias(buscarOptional);

        return new PageImpl<>(categorias, pageable, total);
    }
    private long contarTotalCategorias(Optional<String> buscarOptional) {
        StringBuilder countQueryBuilder = new StringBuilder("SELECT COUNT(C) from Categoria C");
        if (buscarOptional.isPresent()) {
            countQueryBuilder.append(" WHERE C.nombre LIKE :nombre");
        }

        Query countQuery = em.createQuery(countQueryBuilder.toString());
        if (buscarOptional.isPresent()) {
            countQuery.setParameter("nombre", "%" + buscarOptional.get() + "%");
        }

        return (long) countQuery.getSingleResult();
    }
}

