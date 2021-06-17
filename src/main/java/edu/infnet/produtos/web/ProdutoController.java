/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.produtos.web;

import edu.infnet.produtos.model.Produto;
import edu.infnet.produtos.s3.AwsS3Service;
import edu.infnet.produtos.service.ProdutoService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@Controller
public class ProdutoController {

    private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

    //S3
    @Autowired
    private AwsS3Service awsS3Service;

    //Interface com o database
    @Autowired
    private ProdutoService produtoService;

    @Value("${aws.bucketName}")
    private String bucket_name;

    @GetMapping({"/produto", "/produto.html"})
    public String produto(Model model) {

        Produto prodForm = new Produto();

        model.addAttribute("prodForm", prodForm);

        return "produto";
    }

    //enviar foto
    @PostMapping(value = "/cadProd", params = "action=upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile,
            @RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            Model model) throws IOException {

        log.info("nome arquivo: " + multipartFile.getOriginalFilename());

        //já subir o arquivo para o s3 e usar a url do bucket para guardar na base de dados
        File file = File.createTempFile("tmp", "tmp");
        multipartFile.transferTo(file);

        log.info("Foto file: " + multipartFile.getOriginalFilename());

        awsS3Service.upload(file, multipartFile.getOriginalFilename(), bucket_name);

        StringBuilder fileUrl = new StringBuilder();

        fileUrl.append("https://dr4s3bucket.s3.sa-east-1.amazonaws.com/").append(multipartFile.getOriginalFilename());

        Produto produto = new Produto(nome, descricao, fileUrl.toString());

        produtoService.save(produto);

        return "redirect:/produto";
    }
}
