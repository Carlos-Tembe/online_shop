package com.online_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online_shop.models.Artigo_movimento;

@Repository
public interface Artigo_movimentoRepository extends JpaRepository<Artigo_movimento, Long> {

}
