package br.com.lareira.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PessoaFisicaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;    
    private String nome;    
    private String sobrenome;   
    private LocalDate dataNascimento;
    private String profissao;
    private String telCelular;    
    private String email;
    private String problemaSaude;

    // public PessoaFisicaDTO(PessoaFisica obj) {
    //     this.id = obj.getId();
    //     this.nome = obj.getNome();
    //     this.sobrenome = obj.getSobrenome();
    //     this.dataNascimento = obj.getDataNascimento();
    //     this.profissao = obj.getProfissao();
    //     this.telCelular = obj.getTelCelular();
    //     this.email = obj.getEmail();
    //     this.problemaSaude = obj.getProblemaSaude();
    // }
}