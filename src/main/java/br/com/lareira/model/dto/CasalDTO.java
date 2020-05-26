package br.com.lareira.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.lareira.model.Filho;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    // private List<Filho> filhos = new ArrayList<>();
}