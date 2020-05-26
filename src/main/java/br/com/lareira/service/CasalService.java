package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.lareira.model.Casal;
import br.com.lareira.model.Endereco;
import br.com.lareira.model.Lareira;
import br.com.lareira.model.PessoaFisica;
import br.com.lareira.model.TipoUniao;
import br.com.lareira.model.dto.CasalDTO;
import br.com.lareira.repository.CasalRepository;
import br.com.lareira.repository.EnderecoRepository;
import br.com.lareira.repository.PessoaFisicaRepository;
import br.com.lareira.service.exceptions.DataIntegrityException;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class CasalService {

    @Autowired
    private CasalRepository repository;

    @Autowired
    private PessoaFisicaRepository repoPessoaFisica;

    @Autowired
    private EnderecoRepository repoEndereco;

    /**
     * Método auxiliar para converter um objeto DTO para um objeto de instanciação
     * 
     * @param casalDto
     * @return objeto convertido
     */
    public Casal fromDTO(CasalDTO casalDto) {
        Casal casal = new Casal(casalDto.getId(), casalDto.getNumeroFicha(), casalDto.getFoneFixo(),
                casalDto.getDataUniao(), casalDto.getMemorando(), casalDto.getFoto(), null, null, null, null, null);

        Lareira lareira = new Lareira(casalDto.getLareiraId(), null, null, null, null, null, null, null);

        TipoUniao tipoUniao = new TipoUniao(casalDto.getTipoUniaoId(), null, null);

        PessoaFisica marido = new PessoaFisica(casalDto.getMarido().getId(), casalDto.getMarido().getNome(),
                casalDto.getMarido().getSobrenome(), casalDto.getMarido().getDataNascimento(),
                casalDto.getMarido().getProblemaSaude(), casalDto.getMarido().getTelCelular(),
                casalDto.getMarido().getEmail(), casalDto.getMarido().getProblemaSaude());
        PessoaFisica esposa = new PessoaFisica(casalDto.getEsposa().getId(), casalDto.getEsposa().getNome(),
                casalDto.getEsposa().getSobrenome(), casalDto.getEsposa().getDataNascimento(),
                casalDto.getEsposa().getProblemaSaude(), casalDto.getEsposa().getTelCelular(),
                casalDto.getEsposa().getEmail(), casalDto.getEsposa().getProblemaSaude());

        Endereco endereco = new Endereco(casalDto.getEndereco().getId(), casalDto.getEndereco().getCep(),
                casalDto.getEndereco().getRua(), casalDto.getEndereco().getNumero(), casalDto.getEndereco().getBairro(),
                casalDto.getEndereco().getCidade(), casalDto.getEndereco().getEstado());

        casal.setLareira(lareira);
        casal.setTipoUniao(tipoUniao);
        casal.setMarido(marido);
        casal.setEsposa(esposa);
        casal.setEndereco(endereco);

        return casal;
    }

    /**
     * Lista todos os Casais com recurso de paginação
     * 
     * @param page
     * @param linesPerPage
     * @param orderBy
     * @param direction
     * @return
     */
    public Page<Casal> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    /**
     * Procura um casal pelo código passado como parâmetro
     * 
     * @param id
     * @return Casal caso seja encontrado senão um erro de objeto não encontrado
     */
    public Casal find(Long id) {
        Optional<Casal> obj = repository.findById(id);

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Casal.class.getName()));
    }

    /**
     * Insere um novo Casal
     * 
     * @param obj
     * @return
     */
    public Casal insert(Casal obj) {
        obj.setId(null);
        obj = repository.save(obj);
        repoPessoaFisica.save(obj.getMarido());
        repoPessoaFisica.save(obj.getEsposa());
        repoEndereco.save(obj.getEndereco());
        return obj;
    }

    /**
     * Atualiza um Casal já existente
     * 
     * @param obj
     * @return
     */
    public Casal update(Casal obj) {
        Casal newObj = find(obj.getId());
        BeanUtils.copyProperties(obj, newObj);
        return repository.save(newObj);
    }

    /**
     * Deleta um Casal
     * 
     * @param id
     */
    public void delete(Long id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Casal que possui filhos vinculados.");
        }
    }
}