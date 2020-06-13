package br.com.lareira.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CasalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer numeroFicha;

    private String foneFixo;

    @Past
    private LocalDate dataUniao;

    private String memorando;    

    @NotNull
    @Positive
    private Long lareiraId;

    private Long tipoUniaoId;

    private Long casalPadrinhoId;

    private PessoaFisicaDTO marido;

    private PessoaFisicaDTO esposa;

    private EnderecoDTO endereco;

    private Set<FilhoDTO> filhos = new HashSet<>();
}