package br.com.lareira.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_marido")
    private PessoaFisica marido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_esposa")
    private PessoaFisica esposa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @OneToMany(mappedBy = "casal", cascade = CascadeType.ALL)
    private List<Filho> filhos = new ArrayList<>();

    @ManyToOne
    @JsonIgnoreProperties("casalPadrinho")
    private Casal casalPadrinho;

    public Casal() {
    }

    public Casal(Long id, Integer numeroFicha, String foneFixo, LocalDate dataUniao, String memorando, byte[] foto,
            Lareira lareira, TipoUniao tipoUniao, PessoaFisica marido, PessoaFisica esposa, Endereco endereco) {
        this.id = id;
        this.numeroFicha = numeroFicha;
        this.foneFixo = foneFixo;
        this.dataUniao = dataUniao;
        this.memorando = memorando;
        this.foto = foto;
        this.lareira = lareira;
        this.tipoUniao = tipoUniao;
        this.marido = marido;
        this.esposa = esposa;
        this.endereco = endereco;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroFicha() {
        return this.numeroFicha;
    }

    public void setNumeroFicha(Integer numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public String getFoneFixo() {
        return this.foneFixo;
    }

    public void setFoneFixo(String foneFixo) {
        this.foneFixo = foneFixo;
    }

    public LocalDate getDataUniao() {
        return this.dataUniao;
    }

    public void setDataUniao(LocalDate dataUniao) {
        this.dataUniao = dataUniao;
    }

    public String getMemorando() {
        return this.memorando;
    }

    public void setMemorando(String memorando) {
        this.memorando = memorando;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Lareira getLareira() {
        return this.lareira;
    }

    public void setLareira(Lareira lareira) {
        this.lareira = lareira;
    }

    public TipoUniao getTipoUniao() {
        return this.tipoUniao;
    }

    public void setTipoUniao(TipoUniao tipoUniao) {
        this.tipoUniao = tipoUniao;
    }

    public PessoaFisica getMarido() {
        return this.marido;
    }

    public void setMarido(PessoaFisica marido) {
        this.marido = marido;
    }

    public PessoaFisica getEsposa() {
        return this.esposa;
    }

    public void setEsposa(PessoaFisica esposa) {
        this.esposa = esposa;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Filho> getFilhos() {
        return this.filhos;
    }

    public void setFilhos(List<Filho> filhos) {
        this.filhos = filhos;
    }

    public Casal id(Long id) {
        this.id = id;
        return this;
    }

    public Casal numeroFicha(Integer numeroFicha) {
        this.numeroFicha = numeroFicha;
        return this;
    }

    public Casal foneFixo(String foneFixo) {
        this.foneFixo = foneFixo;
        return this;
    }

    public Casal dataUniao(LocalDate dataUniao) {
        this.dataUniao = dataUniao;
        return this;
    }

    public Casal memorando(String memorando) {
        this.memorando = memorando;
        return this;
    }

    public Casal foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public Casal lareira(Lareira lareira) {
        this.lareira = lareira;
        return this;
    }

    public Casal tipoUniao(TipoUniao tipoUniao) {
        this.tipoUniao = tipoUniao;
        return this;
    }

    public Casal marido(PessoaFisica marido) {
        this.marido = marido;
        return this;
    }

    public Casal esposa(PessoaFisica esposa) {
        this.esposa = esposa;
        return this;
    }

    public Casal endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public Casal filhos(List<Filho> filhos) {
        this.filhos = filhos;
        return this;
    }

    public void setCasalPadrinho(Casal casalPadrinho) {
        this.casalPadrinho = casalPadrinho;
    }

    public Casal getCasalPadrinho() {
        return this.casalPadrinho;
    }

    public Casal casalPadrinho(Casal casalPadrinho) {
        this.casalPadrinho = casalPadrinho;
        return this;
    }

    public Casal addFilho(Filho filho) {
        this.filhos.add(filho);
        filho.setCasal(this);
        return this;
    }

    public Casal removeFilho(Filho filho) {
        this.filhos.remove(filho);
        filho.setCasal(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Casal)) {
            return false;
        }
        Casal casal = (Casal) o;
        return Objects.equals(id, casal.id) && Objects.equals(numeroFicha, casal.numeroFicha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", numeroFicha='" + getNumeroFicha() + "'" + ", foneFixo='"
                + getFoneFixo() + "'" + ", dataUniao='" + getDataUniao() + "'" + ", memorando='" + getMemorando() + "'"
                + ", foto='" + getFoto() + "'" + ", lareira='" + getLareira().getNome() + "'" + ", tipoUniao='"
                + getTipoUniao().getDescricao() + "'" + ", marido='" + getMarido() + "'" + ", esposa='" + getEsposa()
                + "'" + ", endereco='" + getEndereco() + "'" + getFilhos() + "}";
    }
}