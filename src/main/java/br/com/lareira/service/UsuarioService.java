package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lareira.model.Usuario;
import br.com.lareira.repository.UsuarioRepository;
import br.com.lareira.service.exceptions.BadRequestIdException;
import br.com.lareira.service.exceptions.DataIntegrityException;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;  
    
    @Autowired
	private BCryptPasswordEncoder pe;

    /**
     * Lista todos os Usuarios com recurso de paginação
     * 
     * @param page
     * @param linesPerPage
     * @param orderBy
     * @param direction
     * @return
     */
    public Page<Usuario> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Usuario find(Long id) {
        Optional<Usuario> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
    }

    /**
     * Insere um novo Usuario
     * 
     * @param obj
     * @return
     */
    public Usuario insert(Usuario obj) {
        if (obj.getId() != null) {
            throw new BadRequestIdException("Para inserir um novo usuário não deve ser informado o ID.");
        }
        obj.setId(null);

        // Faz a encriptação da senha
        obj.setSenha(pe.encode(obj.getSenha()));

        return repository.save(obj);
    }

    /**
     * Atualiza uma Usuario já existente
     * 
     * @param obj
     * @return
     */
    public Usuario update(Usuario obj) {
        if (obj.getId() == null) {
            throw new BadRequestIdException("Obrigatório informar um ID para alterar o usuário.");
        }
        find(obj.getId());
        return repository.save(obj);
    }

    /**
     * Deleta uma Usuario
     * 
     * @param id
     */
    public void delete(Long id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Usuario, apenas desativa-lo.");
        }
    }

}