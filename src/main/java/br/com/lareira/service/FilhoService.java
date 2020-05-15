package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lareira.model.Filho;
import br.com.lareira.repository.FilhoRepository;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class FilhoService {

    @Autowired
    private FilhoRepository repository;

    public Filho buscar(Long id) {
        Optional<Filho> obj = repository.findById(id);

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Filho.class.getName()));
    }
}