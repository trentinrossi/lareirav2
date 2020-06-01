package br.com.lareira.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Filho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sexo;
    private LocalDate dataNascimento;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "id_casal")
    private Casal casal;

    public Filho() {
    }

    public Filho(Long id, String nome, String sexo, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @JsonIgnore
    public Casal getCasal() {
        return this.casal;
    }

    public void setCasal(Casal casal) {
        this.casal = casal;
    }

    public Filho id(Long id) {
        this.id = id;
        return this;
    }

    public Filho nome(String nome) {
        this.nome = nome;
        return this;
    }

    public Filho sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public Filho dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public Filho casal(Casal casal) {
        this.casal = casal;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Filho)) {
            return false;
        }
        Filho filho = (Filho) o;
        return Objects.equals(id, filho.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", nome='" + getNome() + "'" + ", sexo='" + getSexo() + "'"
                + ", dataNascimento='" + getDataNascimento() + "'" + "}";
    }

}