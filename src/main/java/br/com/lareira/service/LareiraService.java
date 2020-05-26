package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.lareira.model.Lareira;
import br.com.lareira.model.dto.LareiraDTO;
import br.com.lareira.repository.LareiraRepository;
import br.com.lareira.service.exceptions.DataIntegrityException;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class LareiraService {

    @Autowired
    private LareiraRepository repository;

    /**
     * Método auxiliar para converter um objeto DTO para um objeto de instanciação
     * 
     * @param objDto
     * @return objeto convertido
     */
    public Lareira fromDTO(LareiraDTO objDto) {
        return new Lareira(objDto.getId(), objDto.getNome(), objDto.getEndereco(), objDto.getBairro(), objDto.getCep(),
                objDto.getCidade(), objDto.getEstado(), objDto.getTelefone());
    }

    /**
     * Lista todos as Lareiras com recurso de paginação
     * 
     * @param page
     * @param linesPerPage
     * @param orderBy
     * @param direction
     * @return
     */
    public Page<Lareira> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Lareira find(Long id) {
        Optional<Lareira> obj = repository.findById(id);

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Lareira.class.getName()));
    }

    /**
     * Insere uma nova Lareira
     * 
     * @param obj
     * @return
     */
    public Lareira insert(Lareira obj) {
        obj.setId(null);
        return repository.save(obj);
    }

    /**
     * Atualiza uma Lareira já existente
     * 
     * @param obj
     * @return
     */
    public Lareira update(Lareira obj) {
        find(obj.getId());
        return repository.save(obj);
    }

    /**
     * Deleta uma Lareira
     * 
     * @param id
     */
    public void delete(Long id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma Lareira que possui casais vinculados.");
        }
    }

}