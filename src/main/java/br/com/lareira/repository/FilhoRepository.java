package br.com.lareira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lareira.model.Filho;

@Repository
public interface FilhoRepository extends JpaRepository<Filho, Long> {
    
}