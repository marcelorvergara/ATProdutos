/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.produtos.repository;

import edu.infnet.produtos.model.Produto;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

}
