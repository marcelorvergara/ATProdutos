/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.produtos.service;

import edu.infnet.produtos.model.Produto;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@Service
public interface ProdutoService {

    public void save(Produto produto);

    public List<Produto> findAll();

    public Produto findNomeByCodigoProd(Long id);

    public Produto findByCodigoProd(long id);

    public Produto findById(String codigoProd);

    public void deleteByCodigoProd(String id);

}
