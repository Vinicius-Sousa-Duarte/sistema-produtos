package com.produtos.controller;

import com.produtos.service.ProdutoService;

import java.util.ArrayList;
import java.util.Scanner;

public class ProdutoController {
    private ArrayList<ProdutoService> produtos = new ArrayList<>();
    private int proximoId = 1;
    private Scanner scanner = new Scanner(System.in);

    public void criarProduto() {
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o preço do produto: ");
        double preco = scanner.nextDouble();

        System.out.print("Digite a quantidade do produto: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        ProdutoService produto = new ProdutoService(proximoId++, nome, preco, quantidade);
        produtos.add(produto);

        System.out.println("Produto criado com sucesso!");
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Lista de produtos vazia.");
            return;
        }

        System.out.println("\n--- LISTA DE PRODUTOS ---");
        for (ProdutoService produto : produtos) {
            System.out.println(produto);
        }
    }

    public void atualizarProduto() {
        System.out.print("Informe o ID do produto que deseja atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (ProdutoService produto : produtos) {
            if (produto.getId() == id) {
                System.out.println("O que deseja alterar?");
                System.out.println("1 - Nome");
                System.out.println("2 - Preço");
                System.out.println("3 - Ambos");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Novo nome: ");
                        String novoNome = scanner.nextLine();
                        produto.setNome(novoNome);
                        break;
                    case 2:
                        System.out.print("Novo preço: ");
                        double novoPreco = scanner.nextDouble();
                        produto.setPreco(novoPreco);
                        break;
                    case 3:
                        System.out.print("Novo nome: ");
                        novoNome = scanner.nextLine();
                        System.out.print("Novo preço: ");
                        novoPreco = scanner.nextDouble();
                        produto.setNome(novoNome);
                        produto.setPreco(novoPreco);
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        return;
                }

                System.out.println("Produto atualizado com sucesso!");
                return;
            }
        }

        System.out.println("Produto com ID " + id + " não encontrado.");
    }

    public void deletarProduto() {
        System.out.print("Informe o ID do produto a remover: ");
        int id = scanner.nextInt();

        for (ProdutoService produto : produtos) {
            if (produto.getId() == id) {
                produtos.remove(produto);
                System.out.println("Produto removido com sucesso!");
                return;
            }
        }

        System.out.println("Produto com ID " + id + " não encontrado.");
    }

    public void adicionarQuantidade() {
        System.out.print("Informe o ID do produto: ");
        int id = scanner.nextInt();

        for (ProdutoService produto : produtos) {
            if (produto.getId() == id) {
                System.out.print("Quantidade a adicionar: ");
                int qtd = scanner.nextInt();
                produto.setQuantidade(produto.getQuantidade() + qtd);
                System.out.println("Quantidade atualizada com sucesso!");
                return;
            }
        }

        System.out.println("Produto com ID " + id + " não encontrado.");
    }

    public void removerQuantidade() {
        System.out.print("Informe o ID do produto: ");
        int id = scanner.nextInt();

        for (ProdutoService produto : produtos) {
            if (produto.getId() == id) {
                System.out.print("Quantidade a remover: ");
                int qtd = scanner.nextInt();

                if (qtd > produto.getQuantidade()) {
                    System.out.println("Quantidade inválida. Produto possui apenas " + produto.getQuantidade() + " unidades.");
                } else {
                    produto.setQuantidade(produto.getQuantidade() - qtd);
                    System.out.println("Quantidade atualizada com sucesso!");
                }
                return;
            }
        }

        System.out.println("Produto com ID " + id + " não encontrado.");
    }
}
