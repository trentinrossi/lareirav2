package br.com.lareira.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lareira.model.TipoUniao;
import br.com.lareira.model.dto.TipoUniaoDTO;
import br.com.lareira.service.TipoUniaoService;

@RestController
@RequestMapping(value = "/tipos-uniao")
public class TipoUniaoResource {

    @Autowired
    private TipoUniaoService service;

    @GetMapping
    public ResponseEntity<Page<TipoUniao>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        
        Page<TipoUniao> list = service.findAll(page, linesPerPage, orderBy, direction);
        
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        TipoUniao obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody TipoUniaoDTO objDto) {        
        TipoUniao obj = service.fromDTO(objDto);
        obj = service.insert(obj);        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();                
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<TipoUniao> update(@PathVariable Long id,@Valid @RequestBody TipoUniaoDTO objDto) {         
        TipoUniao obj = service.fromDTO(objDto);
        obj.setId(id);
        TipoUniao objSalvo = service.update(obj);
        
        return ResponseEntity.ok(objSalvo);
    }

    @DeleteMapping(value = "/{id}")    
    public ResponseEntity<TipoUniao> delete(@PathVariable Long id) {        
        service.delete(id); 

        return ResponseEntity.noContent().build();
    }

}