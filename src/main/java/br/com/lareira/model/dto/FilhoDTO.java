package br.com.lareira.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilhoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String sexo;
    private LocalDate dataNascimento;
}