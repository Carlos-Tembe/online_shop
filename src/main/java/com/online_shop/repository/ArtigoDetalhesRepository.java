package com.online_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.online_shop.models.Artigo_detalhes;

public interface ArtigoDetalhesRepository extends JpaRepository<Artigo_detalhes, Long> {
	
	@Query(value="select * from Artigo_detalhes u where u.artigo_id=?", nativeQuery=true)
	List<Artigo_detalhes> findByArtigoId(@Param("search") Long id);



}
