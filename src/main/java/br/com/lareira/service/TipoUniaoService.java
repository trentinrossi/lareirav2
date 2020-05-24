package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.lareira.model.TipoUniao;
import br.com.lareira.model.dto.TipoUniaoDTO;
import br.com.lareira.repository.TipoUniaoRepository;
import br.com.lareira.service.exceptions.DataIntegrityException;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class TipoUniaoService {

    @Autowired
    private TipoUniaoRepository repository;

    /**
     * Método auxiliar para converter um objeto DTO para um objeto de instanciação
     * 
     * @param objDto
     * @return objeto convertido
     */
    public TipoUniao fromDTO(TipoUniaoDTO objDto) {
        return new TipoUniao(objDto.getId(), objDto.getNome(), objDto.getDescricao());
    }

    /**
     * Lista todos os tipos de união com recurso de paginação
     * 
     * @param page
     * @param linesPerPage
     * @param orderBy
     * @param direction
     * @return
     */
    public Page<TipoUniao> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    /**
     * Lista somente um tipo de união conforme o ID que foi passado como parâmetro
     * 
     * @param id
     * @return
     */
    public TipoUniao find(Long id) {
        Optional<TipoUniao> obj = repository.findById(id);

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + TipoUniao.class.getName()));
    }

    /**
     * Insere um novo Tipo de União
     * 
     * @param obj
     * @return
     */
    public TipoUniao insert(TipoUniao obj) {
        obj.setId(null);
        return repository.save(obj);
    }

    /**
     * Atualiza um Tipo de União já existente
     * 
     * @param obj
     * @return
     */
    public TipoUniao update(TipoUniao obj) {
        find(obj.getId());
        return repository.save(obj);
    }

    /**
     * Deleta um tipo de união
     * 
     * @param id
     */
    public void delete(Long id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Tipo de União que possui casais vinculados.");
        }
    }

}