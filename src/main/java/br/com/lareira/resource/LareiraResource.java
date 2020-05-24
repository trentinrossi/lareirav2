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

import br.com.lareira.model.Lareira;
import br.com.lareira.model.dto.LareiraDTO;
import br.com.lareira.service.LareiraService;

@RestController
@RequestMapping(value = "/lareiras")
public class LareiraResource {

    @Autowired
    private LareiraService service;

    @GetMapping
    public ResponseEntity<Page<Lareira>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        
        Page<Lareira> list = service.findAll(page, linesPerPage, orderBy, direction);
        
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Lareira obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody LareiraDTO objDto) {        
        Lareira obj = service.fromDTO(objDto);
        obj = service.insert(obj);        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();                
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Lareira> update(@PathVariable Long id,@Valid @RequestBody LareiraDTO objDto) {         
        Lareira obj = service.fromDTO(objDto);
        obj.setId(id);
        Lareira objSalvo = service.update(obj);
        
        return ResponseEntity.ok(objSalvo);
    }

    @DeleteMapping(value = "/{id}")    
    public ResponseEntity<Lareira> delete(@PathVariable Long id) {        
        service.delete(id); 

        return ResponseEntity.noContent().build();
    }
}