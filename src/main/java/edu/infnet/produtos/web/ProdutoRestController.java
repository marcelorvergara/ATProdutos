/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.produtos.web;

import edu.infnet.produtos.model.Produto;
import edu.infnet.produtos.s3.AwsS3Service;
import edu.infnet.produtos.service.ProdutoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@RestController
public class ProdutoRestController {

    //S3
    @Autowired
    private AwsS3Service awsS3Service;

    //bucket S3
    @Value("${aws.bucketName}")
    private String bucket_name;

    //Interface com o database
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtoList")
    public List<Produto> getProdutos(Model model) {

        //vai no banco pegar a lista de produtos
        return produtoService.findAll();

    }

    //retornar o nome do produto pelo id
    @GetMapping("/nomeProd/{id}")
    public String getNomeProd(@PathVariable String id) {

        Produto produto = produtoService.findNomeByCodigoProd(Long.parseLong(id));

        return produto.getNome();
    }

    //Api para uso do Http DELETE
    @DeleteMapping("/api/delete/{codigoProd}")
    public Map<String, Boolean> deletarCotacao(@PathVariable("codigoProd") String codigoProd) {

        //declaração para retorno REST
        Map<String, Boolean> response = new HashMap<>();

        //pegar a imagem do produto a partir do id --> deletar no S3
        Produto produto = produtoService.findByCodigoProd(Long.parseLong(codigoProd));

        //teste para previnir erro HTTP 500
        if (produto == null) {
            response.put("Produto inexistente", Boolean.TRUE);
            return response;
        }

        //Deletando a imagem antiga. "imagem" é a url do arquvivo guardada no db.
        String fileName = produto.getImagem().split(".com/")[1];

        awsS3Service.delete(bucket_name, fileName);

        //deletando o produto da base de dados
        produtoService.deleteByCodigoProd(codigoProd);

        response.put("Produto deletado com sucesso!", Boolean.TRUE);

        return response;
    }

    //Api para uso do Http PUT
    @PutMapping("/api/update/{codigoProd}")
    public Map<String, Boolean> replaceNews(@RequestBody Produto newProd, @PathVariable String codigoProd) {

        //declaração para retorno REST
        Map<String, Boolean> response = new HashMap<>();

        Produto prod = produtoService.findByCodigoProd(Long.parseLong(codigoProd));

        //teste para previnir erro HTTP 500
        if (prod == null) {
            response.put("Produto inexistente", Boolean.TRUE);
            return response;
        }

        prod.setNome(newProd.getNome());
        prod.setDescricao(newProd.getDescricao());
        prod.setImagem(newProd.getImagem());

        produtoService.save(prod);

        response.put("Produto alterado com sucesso!", Boolean.TRUE);

        return response;

    }
}
