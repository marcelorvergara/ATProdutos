/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.produtos.service;

import edu.infnet.produtos.model.Produto;
import edu.infnet.produtos.repository.ProdutoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public void save(Produto produto) {
        produtoRepository.save(produto);
    }

    @Override
    public List<Produto> findAll() {
        return (List<Produto>) produtoRepository.findAll();
    }

    @Override
    public Produto findNomeByCodigoProd(Long id) {
        return produtoRepository.findNomeByCodigoProd(id);
    }

    @Override
    public Produto findByCodigoProd(long id) {
        return produtoRepository.findByCodigoProd(id);
    }

    @Override
    public Produto findById(String codigoProd) {
        return produtoRepository.findByCodigoProd(Long.parseLong(codigoProd));
    }

    @Override
    public void deleteByCodigoProd(String id) {
        produtoRepository.deleteById(Long.parseLong(id));
    }

}
