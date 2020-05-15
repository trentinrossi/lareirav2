package br.com.lareira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lareira.model.TipoUniao;

@Repository
public interface TipoUniaoRepository extends JpaRepository<TipoUniao, Long> {
    
}