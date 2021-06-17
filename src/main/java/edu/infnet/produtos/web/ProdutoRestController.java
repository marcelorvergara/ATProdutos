/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.produtos.web;

import edu.infnet.produtos.model.Produto;
import edu.infnet.produtos.service.ProdutoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@RestController
public class ProdutoRestController {

    //Interface com o database
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtoList")
    public List<Produto> getProdutos(Model model) {

        //vai no banco pegar a lista de produtos
        return produtoService.findAll();

    }

}
