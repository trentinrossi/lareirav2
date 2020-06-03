package br.com.lareira.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.lareira.model.Casal;
import br.com.lareira.model.Filho;
import br.com.lareira.model.dto.FilhoOnlyDTO;
import br.com.lareira.repository.FilhoRepository;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class FilhoService {

    @Autowired
    private FilhoRepository repository;

    @Autowired
    private CasalService casalService;

    public Filho fromDTO(FilhoOnlyDTO filhoDto) {
        Filho filho = new Filho(filhoDto.getId(), filhoDto.getNome(), filhoDto.getSexo(), filhoDto.getDataNascimento());

        Casal c = casalService.find(filhoDto.getIdCasal());
        filho.setCasal(c);

        return filho;
    }

    public FilhoOnlyDTO toDTO(Filho f) {
        FilhoOnlyDTO filhoResponse = new FilhoOnlyDTO(f.getId(), f.getNome(), f.getSexo(), f.getDataNascimento(),
                f.getCasal().getId());

        return filhoResponse;
    }

    public Filho find(Long id) {
        Optional<Filho> obj = repository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Filho.class.getName()));
    }

    public Page<Filho> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Filho insert(Filho obj) {
        obj.setId(null);
        obj = repository.save(obj);
        return obj;
    }

    public Filho update(Filho objNovo) {
        Filho objGravado = find(objNovo.getId());

        BeanUtils.copyProperties(objNovo, objGravado);

        return repository.save(objGravado);
    }

    public void delete(Long id) {
        find(id);
        repository.deleteById(id);
    }
}