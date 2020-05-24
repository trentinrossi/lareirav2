package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.lareira.model.TipoUniao;
import br.com.lareira.repository.TipoUniaoRepository;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class TipoUniaoService {

    @Autowired
    private TipoUniaoRepository repository;

    public Page<TipoUniao> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public TipoUniao buscar(Long id) {
        Optional<TipoUniao> obj = repository.findById(id);

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + TipoUniao.class.getName()));
    }
}