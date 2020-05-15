package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lareira.model.TipoUniao;
import br.com.lareira.repository.TipoUniaoRepository;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class TipoUniaoService {

    @Autowired
    private TipoUniaoRepository repository;

    public TipoUniao buscar(Long id) {
        Optional<TipoUniao> obj = repository.findById(id);

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + TipoUniao.class.getName()));
    }
}