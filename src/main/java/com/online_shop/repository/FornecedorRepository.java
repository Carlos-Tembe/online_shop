package com.online_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.online_shop.models.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	@Query(value="select * from fornecedor f where f.nome like %:search% or f.nuit like %:search% f.email like %:search%", nativeQuery=true)
	List<Fornecedor> findBySearch(@Param("search") String search);
}
