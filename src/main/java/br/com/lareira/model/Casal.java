package br.com.lareira.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class Casal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCasal;
    private String maridoNome;
    private String maridoSobrenome;
    private LocalDate maridoDataNascimento;
    private String maridoProfissao;
    private String maridoTelCelular;
    private String maridoEmail;
    private String maridoProblemaSaude;
    private String esposaNome;
    private String esposaSobrenome;
    private LocalDate esposaDataNascimento;
    private String esposaProfissao;
    private String esposaTelCelular;
    private String esposaEmail;
    private String esposaProblemaSaude;
    private String casalFoneFixo;
    private String casalEnderecoCep;
    private String casalEnderecoRua;
    private String casalEnderecoNumero;
    private String casalEnderecoBairro;
    private String casalEnderecoCidade;
    private String casalEnderecoEstado;
    private LocalDate casalDataUniao;
    private Integer casalNumeroFicha;
    private String casalMemorando;
    @Lob
    private byte[] casalFoto;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_lareira")
    private Lareira lareira;

    @JsonBackReference
    @OneToMany(mappedBy = "casal") // Nome do atributo do outro lado
    private List<Filho> filhos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_casalPadrinho")
    private Casal casalPadrinho;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_tipoUniao")
    private TipoUniao tipoUniao;
}