package br.com.lareira.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lareira.model.Casal;
import br.com.lareira.service.CasalService;

@RestController
@RequestMapping(value = "/casais")
public class CasalResource {

    @Autowired
    private CasalService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Casal obj = service.buscar(id);
        return ResponseEntity.ok().body(obj);
    }
}