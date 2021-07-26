package com.online_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.online_shop.models.Artigo_detalhes;

public interface ArtigoDetalhesRepository extends JpaRepository<Artigo_detalhes, Long> {

	@Query(value = "select * from Artigo_detalhes u where u.artigo_id=?", nativeQuery = true)
	List<Artigo_detalhes> findByArtigoId(@Param("search") Long id);

	@Query(value = "select * from Artigo_detalhes AD where AD.estado=?", nativeQuery = true)
	List<Artigo_detalhes> findAllStatusArtigo_detalhes(@Param("estado") Long id);

	@Query(value = "SELECT * FROM artigo_detalhes AD join artigo A ON AD.artigo_id=A.id join "
			+ "categoria C ON A.categoria_id=C.id WHERE AD.estado=1 AND  C.id=?", nativeQuery = true)
	List<Artigo_detalhes> findAllByCategoryId(@Param("category_id") Long cat_id);

}
