package br.com.lareira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lareira.model.Casal;

@Repository
public interface CasalRepository extends JpaRepository<Casal, Long> {
    
}