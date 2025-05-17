package com.produtos.service;

public class ProdutoService {
    private int id;
    private String nome;
    private double preco;
    private int quantidade;

    public ProdutoService(int id, String nome, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getTotalProduto() {
        return preco * quantidade;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Preço: R$ %.2f | Quantidade: %d | Total: R$ %.2f",
                id, nome, preco, quantidade, getTotalProduto());
    }
}
