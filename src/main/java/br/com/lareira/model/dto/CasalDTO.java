package br.com.lareira.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CasalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer numeroFicha;
    private String foneFixo;
    private LocalDate dataUniao;
    private String memorando;
    private byte[] foto;

    @NotNull
    private Long lareiraId;
    private Long tipoUniaoId;

    private PessoaFisicaDTO marido;
    private PessoaFisicaDTO esposa;
    private EnderecoDTO endereco;

    private Set<FilhoDTO> filhos = new HashSet<>();
}