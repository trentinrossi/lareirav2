package br.com.lareira.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import br.com.lareira.model.Lareira;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LareiraDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "{validation.nome.NotEmpty}")
    private String nome;
    private String endereco;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    private String telefone;

    public LareiraDTO(Lareira obj) {
        id = obj.getId();
        nome = obj.getNome();
        endereco = obj.getEndereco();
        bairro = obj.getBairro();
        cep = obj.getCep();
        cidade = obj.getCidade();
        estado = obj.getEstado();
        telefone = obj.getTelefone();
    }
}