package br.com.lareira.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

// import br.com.lareira.model.TipoUniao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TipoUniaoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "{validation.nome.NotEmpty}")
    private String nome;
    private String descricao;

    // public TipoUniaoDTO(TipoUniao obj) {
    //     id = obj.getId();
    //     nome = obj.getNome();
    //     descricao = obj.getDescricao();
    // }
}