package br.com.lareira.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lareira.model.TipoUniao;
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
        TipoUniao obj = service.buscar(id);
        return ResponseEntity.ok().body(obj);
    }
}