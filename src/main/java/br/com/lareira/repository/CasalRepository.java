package br.com.lareira.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lareira.model.Casal;

@Repository
public interface CasalRepository extends JpaRepository<Casal, Long> {

    public Page<Casal> findByMaridoNomeContainingOrMaridoSobrenomeContainingOrEsposaNomeContainingOrEsposaSobrenomeContaining(
            String maridoNome, String maridoSobrenome, String esposaNome, String esposaSobrenome, Pageable page);
}