package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lareira.model.Casal;
import br.com.lareira.repository.CasalRepository;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class CasalService {

    @Autowired
    private CasalRepository repository;

    public Casal buscar(Long id) {
        Optional<Casal> obj = repository.findById(id);

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Casal.class.getName()));
    }
}