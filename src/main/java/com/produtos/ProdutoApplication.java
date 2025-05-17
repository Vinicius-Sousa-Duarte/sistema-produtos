package com.produtos;

import com.produtos.controller.ProdutoController;

import java.util.Scanner;

public class ProdutoApplication {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProdutoController controller = new ProdutoController();

    public static void main(String[] args) {
        int opcao;

        do {
            mostrarMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> controller.criarProduto();
                case 2 -> controller.listarProdutos();
                case 3 -> controller.atualizarProduto();
                case 4 -> controller.deletarProduto();
                case 5 -> controller.adicionarQuantidade();
                case 6 -> controller.removerQuantidade();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENU DE PRODUTOS ---");
        System.out.println("1 - Cadastrar produto");
        System.out.println("2 - Listar produtos");
        System.out.println("3 - Atualizar produto");
        System.out.println("4 - Deletar produto");
        System.out.println("5 - Adicionar quantidade");
        System.out.println("6 - Remover quantidade");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }
}
