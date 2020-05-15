package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lareira.model.PessoaFisica;
import br.com.lareira.repository.PessoaFisicaRepository;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class PessoaFisicaService {

    @Autowired
    private PessoaFisicaRepository repository;

    public PessoaFisica buscar(Long id) {
        Optional<PessoaFisica> obj = repository.findById(id);

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + PessoaFisica.class.getName()));
    }
}