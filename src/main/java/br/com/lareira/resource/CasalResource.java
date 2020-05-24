package br.com.lareira.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lareira.model.Casal;
import br.com.lareira.service.CasalService;

@RestController
@RequestMapping(value = "/casais")
public class CasalResource {

    @Autowired
    private CasalService service;

    @GetMapping
    public ResponseEntity<Page<Casal>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        
        Page<Casal> list = service.findAll(page, linesPerPage, orderBy, direction);
        
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Casal obj = service.buscar(id);
        return ResponseEntity.ok().body(obj);
    }
}