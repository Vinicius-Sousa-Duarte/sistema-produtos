package com.produtos.controller;

import com.produtos.service.ProdutoService;
import java.util.ArrayList;
import java.util.Collections; // Import para Collections.unmodifiableList
import java.util.List;
import java.util.Optional;

public class ProdutoController {
    private List<ProdutoService> produtos;
    private int proximoId;

    public ProdutoController() {
        this.produtos = new ArrayList<>();
        this.proximoId = 1; // Começa com ID 1
        // Adicionar alguns produtos de exemplo para facilitar o teste
        criarProduto("Mouse Gamer Logitech", 150.00, 10);
        criarProduto("Teclado Mecânico HyperX", 300.00, 5);
        criarProduto("Monitor Ultrawide LG", 1200.00, 3);
        criarProduto("Webcam Full HD Razer", 250.00, 7);
        criarProduto("Headset Gamer Astro A50", 800.00, 2);
    }

    /**
     * Cria um novo produto e o adiciona à lista.
     * @param nome Nome do produto.
     * @param preco Preço unitário do produto.
     * @param quantidade Quantidade inicial do produto.
     * @return O ProdutoService recém-criado, ou null se a criação falhar por validação.
     */
    public ProdutoService criarProduto(String nome, double preco, int quantidade) {
        if (nome == null || nome.trim().isEmpty()) {
            return null; // ou lançar uma exceção específica
        }
        if (preco < 0 || quantidade < 0) {
            return null; // ou lançar uma exceção
        }
        ProdutoService novoProduto = new ProdutoService(proximoId++, nome.trim(), preco, quantidade);
        produtos.add(novoProduto);
        return novoProduto;
    }

    /**
     * Retorna uma lista imutável de todos os produtos.
     * @return Uma lista de ProdutoService.
     */
    public List<ProdutoService> listarTodosProdutos() {
        return Collections.unmodifiableList(produtos); // Retorna uma lista imutável
    }

    /**
     * Busca um produto pelo seu ID.
     * @param id O ID do produto a ser buscado.
     * @return Um Optional contendo o ProdutoService se encontrado, ou um Optional vazio.
     */
    public Optional<ProdutoService> buscarProdutoPorId(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    /**
     * Atualiza o nome e o preço de um produto existente.
     * A quantidade não é atualizada diretamente por aqui, mas sim pelos métodos específicos.
     * @param id ID do produto a ser atualizado.
     * @param novoNome Novo nome do produto.
     * @param novoPreco Novo preço do produto.
     * @return true se o produto foi atualizado, false caso contrário.
     */
    public boolean atualizarProduto(int id, String novoNome, double novoPreco) {
        if (novoNome == null || novoNome.trim().isEmpty() || novoPreco < 0) {
            return false; // Validação básica
        }
        Optional<ProdutoService> produtoOptional = buscarProdutoPorId(id);
        if (produtoOptional.isPresent()) {
            ProdutoService produto = produtoOptional.get();
            produto.setNome(novoNome.trim());
            produto.setPreco(novoPreco);
            return true;
        }
        return false;
    }

    /**
     * Deleta um produto da lista.
     * @param id ID do produto a ser deletado.
     * @return true se o produto foi deletado, false caso contrário.
     */
    public boolean deletarProduto(int id) {
        return produtos.removeIf(p -> p.getId() == id);
    }

    /**
     * Adiciona uma quantidade ao estoque de um produto.
     * @param id ID do produto.
     * @param quantidadeAdicionar Quantidade a ser adicionada. Deve ser positiva.
     * @return true se a quantidade foi adicionada, false caso contrário.
     */
    public boolean adicionarQuantidade(int id, int quantidadeAdicionar) {
        if (quantidadeAdicionar <= 0) {
            return false;
        }
        Optional<ProdutoService> produtoOptional = buscarProdutoPorId(id);
        if (produtoOptional.isPresent()) {
            ProdutoService produto = produtoOptional.get();
            produto.setQuantidade(produto.getQuantidade() + quantidadeAdicionar);
            return true;
        }
        return false;
    }

    /**
     * Remove uma quantidade do estoque de um produto.
     * @param id ID do produto.
     * @param quantidadeRemover Quantidade a ser removida. Deve ser positiva e não maior que a quantidade atual.
     * @return true se a quantidade foi removida, false caso contrário.
     */
    public boolean removerQuantidade(int id, int quantidadeRemover) {
        if (quantidadeRemover <= 0) {
            return false;
        }
        Optional<ProdutoService> produtoOptional = buscarProdutoPorId(id);
        if (produtoOptional.isPresent()) {
            ProdutoService produto = produtoOptional.get();
            if (quantidadeRemover <= produto.getQuantidade()) { // Verifica se há estoque suficiente
                produto.setQuantidade(produto.getQuantidade() - quantidadeRemover);
                return true;
            }
        }
        return false;
    }
}