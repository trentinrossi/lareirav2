package br.com.lareira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lareira.model.Lareira;

@Repository
public interface LareiraRepository extends JpaRepository<Lareira, Long> {
    
}