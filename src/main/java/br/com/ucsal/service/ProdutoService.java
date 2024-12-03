package br.com.ucsal.service;

import java.util.List;

import br.com.ucsal.model.Produto;
import br.com.ucsal.persistencia.ProdutoRepository;


/*O construtor agora recebe ProdutoRepository<?, ?> ao invés de <Produto, Integer> e realiza o casting
do objeto recebido como argumento para o tipo esperado pelo campo produtoRepository*/
public class ProdutoService {

    private ProdutoRepository<Produto, Integer> produtoRepository;

    @SuppressWarnings("unchecked")
	public ProdutoService(ProdutoRepository<?, ?> produtoRepository) {
        this.produtoRepository = (ProdutoRepository<Produto, Integer>) produtoRepository;
    }
    
    public void adicionarProduto(String nome, double preco) {
        Produto produto = new Produto(null, nome, preco);
        produtoRepository.adicionar(produto);
    }

    public void removerProduto(Integer id) {
        produtoRepository.remover(id);
    }

    public Produto obterProdutoPorId(Integer id) {
        return produtoRepository.obterPorID(id);
    }

    public void atualizarProduto(Produto p) {
        produtoRepository.atualizar(p);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.listar();
    }
}
