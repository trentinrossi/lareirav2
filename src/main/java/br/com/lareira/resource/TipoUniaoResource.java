package br.com.lareira.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lareira.model.TipoUniao;
import br.com.lareira.service.TipoUniaoService;

@RestController
@RequestMapping(value = "/tiposUniao")
public class TipoUniaoResource {

    @Autowired
    private TipoUniaoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        TipoUniao obj = service.buscar(id);
        return ResponseEntity.ok().body(obj);
    }
}