package br.com.lareira.service;

import java.net.URI;
import java.util.Optional;

import java.awt.image.BufferedImage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.lareira.model.Casal;
import br.com.lareira.model.Endereco;
import br.com.lareira.model.Filho;
import br.com.lareira.model.Lareira;
import br.com.lareira.model.PessoaFisica;
import br.com.lareira.model.TipoUniao;
import br.com.lareira.model.dto.CasalDTO;
import br.com.lareira.repository.CasalRepository;
import br.com.lareira.service.exceptions.BadRequestIdException;
import br.com.lareira.service.exceptions.DataIntegrityException;
import br.com.lareira.service.exceptions.ObjectNotFoundException;

@Service
public class CasalService {

    @Autowired
    private CasalRepository repository;

    @Autowired
    private LareiraService lareiraService;

    @Autowired
    private TipoUniaoService tipoUniaoService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;
    
    @Value("${img.profile.size}")
    private Integer size;

    /**
     * Método auxiliar para converter um objeto DTO para um objeto de instanciação
     * 
     * @param casalDto
     * @return objeto convertido
     */
    public Casal fromDTO(CasalDTO casalDto) {
        Casal casal = new Casal(casalDto.getId(), casalDto.getNumeroFicha(), casalDto.getFoneFixo(),
                casalDto.getDataUniao(), casalDto.getMemorando(), null, null, null, null, null);

        Casal casalPadrinho = new Casal();
        if (casalDto.getCasalPadrinhoId() != null) {
            casalPadrinho = find(casalDto.getCasalPadrinhoId());
            casal.setCasalPadrinho(casalPadrinho);
        }

        Lareira lareira = lareiraService.find(casalDto.getLareiraId());

        TipoUniao tipoUniao = tipoUniaoService.find(casalDto.getTipoUniaoId());

        PessoaFisica marido = new PessoaFisica(casalDto.getMarido().getId(), casalDto.getMarido().getNome(),
                casalDto.getMarido().getSobrenome(), casalDto.getMarido().getDataNascimento(),
                casalDto.getMarido().getProblemaSaude(), casalDto.getMarido().getTelCelular(),
                casalDto.getMarido().getEmail(), casalDto.getMarido().getProblemaSaude());

        PessoaFisica esposa = new PessoaFisica(casalDto.getEsposa().getId(), casalDto.getEsposa().getNome(),
                casalDto.getEsposa().getSobrenome(), casalDto.getEsposa().getDataNascimento(),
                casalDto.getEsposa().getProblemaSaude(), casalDto.getEsposa().getTelCelular(),
                casalDto.getEsposa().getEmail(), casalDto.getEsposa().getProblemaSaude());

        Endereco endereco = new Endereco(casalDto.getEndereco().getId(), casalDto.getEndereco().getCep(),
                casalDto.getEndereco().getRua(), casalDto.getEndereco().getNumero(), casalDto.getEndereco().getBairro(),
                casalDto.getEndereco().getCidade(), casalDto.getEndereco().getEstado());

        casal.setLareira(lareira);
        casal.setTipoUniao(tipoUniao);
        casal.setMarido(marido);
        casal.setEsposa(esposa);
        casal.setEndereco(endereco);

        // Lista de Filhos
        if (casalDto.getFilhos() != null) {
            casalDto.getFilhos().forEach(f -> {
                Filho filho = new Filho(f.getId(), f.getNome(), f.getSexo(), f.getDataNascimento());
                casal.addFilho(filho);
            });
        }

        return casal;
    }

    /**
     * Lista todos os Casais com recurso de paginação
     * 
     * @param page
     * @param linesPerPage
     * @param orderBy
     * @param direction
     * @return
     */
    public Page<Casal> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    /**
     * Procura um casal pelo código passado como parâmetro
     * 
     * @param id
     * @return Casal caso seja encontrado senão um erro de objeto não encontrado
     */
    public Casal find(Long id) {
        Optional<Casal> obj = repository.findById(id);

        // Isso vai fazer com que a classe ResourceExceptionHandler seja executada,
        // retornando o erro 404 para o cliente
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Casal.class.getName()));
    }

    /**
     * Insere um novo Casal
     * 
     * @param obj
     * @return
     */
    // @Transactional
    public Casal insert(Casal obj) {
        if (obj.getId() != null) {
            throw new BadRequestIdException("Para inserir um novo registro não deve ser informado o ID.");
        }
        obj.setId(null);
        obj = repository.save(obj);
        // repoPessoaFisica.save(obj.getMarido());
        // repoPessoaFisica.save(obj.getEsposa());
        // repoEndereco.save(obj.getEndereco());

        // Não precisa salvar os filhos aqui novamente pois já estão serializados dentro
        // do objeto Casal
        // repoFilho.saveAll(obj.getFilhos());

        return obj;
    }

    /**
     * Atualiza um Casal já existente
     * 
     * @param obj
     * @return
     */
    public Casal update(Casal objNovo) {
        if (objNovo.getId() == null) {
            throw new BadRequestIdException("Obrigatório informar um ID para alterar o registro.");
        }
        Casal objGravado = find(objNovo.getId());

        // objGravado.getFilhos().clear();
        // if (objNovo.getFilhos() != null) {
        // objGravado.getFilhos().addAll(objNovo.getFilhos());
        // objGravado.getFilhos().forEach(c -> c.setCasal(objGravado));
        // }

        BeanUtils.copyProperties(objNovo, objGravado);

        return repository.save(objGravado);
    }

    /**
     * Deleta um Casal
     * 
     * @param id
     */
    public void delete(Long id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Casal que possui filhos vinculados.");
        }
    }

    /**
     * Realiza o upload da foto do casal, usa o padrão de nome conforme o prefixo + id .jpg
     * @param file
     * @return
     */
    public URI uploadFotoCasal(MultipartFile file, Long id) {
        if (id == null || id == 0) {
            throw new BadRequestIdException("Obrigatório informar o id do casal para realizar o upload da imagem.");
        }
        
        // Caso a URL da imagem armazenada na AWS fosse gravada na tabela de casais
        // Casal objGravado = find(id);
        // URI urlImagem = s3Service.uploadFile(file);
        // objGravado.setImageUrl(urlImagem.toString());
        // repository.save(objGravado);
        BufferedImage jpgImage = imageService.getJpgImageFromFile(file);
        jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + id + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}