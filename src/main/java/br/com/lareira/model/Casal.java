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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class Casal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numeroFicha;
    private String foneFixo;
    private LocalDate dataUniao;
    private String memorando;
    @Lob
    private byte[] foto;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_lareira")
    private Lareira lareira;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_tipoUniao")
    private TipoUniao tipoUniao;

    @OneToOne
    @JoinColumn(name = "id_marido")
    private PessoaFisica marido;

    @OneToOne
    @JoinColumn(name = "id_esposa")
    private PessoaFisica esposa;

    @OneToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @JsonBackReference
    @OneToMany(mappedBy = "casal")
    private List<Filho> filhos = new ArrayList<>();
}