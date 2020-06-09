package br.com.lareira.resource;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import br.com.lareira.model.Usuario;
import br.com.lareira.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);    

    @Autowired
    private UsuarioService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<Usuario>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Page<Usuario> list = service.findAll(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok().body(list);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Usuario obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }
    
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Usuario obj) {
        log.debug("REST request to save Usuario : {}", obj);                
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @Valid @RequestBody Usuario obj) {
        obj.setId(id);
        Usuario objSalvo = service.update(obj);

        return ResponseEntity.ok(objSalvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Usuario> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}