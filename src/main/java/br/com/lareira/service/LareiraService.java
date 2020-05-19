package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lareira.model.Lareira;
import br.com.lareira.repository.LareiraRepository;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class LareiraService {

    @Autowired
    private LareiraRepository repository;

    public Lareira buscar(Long id) {
        Optional<Lareira> obj = repository.findById(id);        

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Lareira.class.getName()));
    }
}