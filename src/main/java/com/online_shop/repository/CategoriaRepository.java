package com.online_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_shop.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
