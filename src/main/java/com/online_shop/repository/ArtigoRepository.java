package com.online_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.online_shop.models.Artigo;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> {
	
	
	@Query(value="select * from artigo a where a.descricao like %:search%", nativeQuery=true)
	List<Artigo> findBySearch(@Param("search") String search);
}
