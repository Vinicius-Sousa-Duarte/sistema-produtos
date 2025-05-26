package com.produtos;

import com.produtos.controller.ProdutoController;
import com.produtos.service.ProdutoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class ProdutoSwingUI extends JFrame {
    private ProdutoController controller;
    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;
    private JTextField campoNome, campoPreco, campoQuantidade, campoId;
    private JButton btnCriar, btnAtualizar, btnDeletar, btnAdicionarQtd, btnRemoverQtd, btnLimpar;

    public ProdutoSwingUI() {
        controller = new ProdutoController();
        configurarLookAndFeel();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        atualizarTabela();
    }

    private void initializeComponents() {
        setTitle("Sistema de Gerenciamento de Produtos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // Inicializar campos de texto
        campoNome = new JTextField(20);
        campoPreco = new JTextField(10);
        campoQuantidade = new JTextField(10);
        campoId = new JTextField(10);
        campoId.setEditable(false); // ID não deve ser editável pelo usuário
        campoId.setBackground(new Color(245, 245, 245)); // Cor de fundo para indicar que não é editável

        // Inicializar botões
        btnCriar = new JButton("Criar Produto");
        btnAtualizar = new JButton("Atualizar");
        btnDeletar = new JButton("Deletar");
        btnAdicionarQtd = new JButton("Adicionar Qtd");
        btnRemoverQtd = new JButton("Remover Qtd");
        btnLimpar = new JButton("Limpar");

        // Configurar cores e estilos dos botões para uma aparência mais moderna
        styleButton(btnCriar, new Color(46, 125, 50)); // Verde
        styleButton(btnAtualizar, new Color(25, 118, 210)); // Azul
        styleButton(btnDeletar, new Color(211, 47, 47)); // Vermelho
        styleButton(btnAdicionarQtd, new Color(255, 152, 0)); // Laranja
        styleButton(btnRemoverQtd, new Color(156, 39, 176)); // Roxo
        styleButton(btnLimpar, new Color(97, 97, 97)); // Cinza

        // Configurar tabela
        String[] colunas = {"ID", "Nome", "Preço (R$)", "Quantidade", "Total (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.getTableHeader().setBackground(new Color(63, 81, 181));
        tabelaProdutos.getTableHeader().setForeground(Color.WHITE);
        tabelaProdutos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 11));
        tabelaProdutos.setFillsViewportHeight(true);

        // Configurar largura das colunas
        tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(200); // Nome
        tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(100); // Preço
        tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(80);  // Quantidade
        tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(120); // Total
    }

    /**
     * Aplica um estilo padrão aos botões.
     * @param button O botão a ser estilizado.
     * @param bgColor A cor de fundo do botão.
     */
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel principal que contém todos os outros painéis
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Margem interna

        // Painel superior - Formulário de entrada de dados
        JPanel painelFormulario = criarPainelFormulario();
        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);

        // Painel central - Tabela de produtos
        JPanel painelTabela = criarPainelTabela();
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        // Painel inferior - Botões de ação
        JPanel painelBotoes = criarPainelBotoes();
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal, BorderLayout.CENTER);
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(63, 81, 181), 2),
                "Dados do Produto",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(63, 81, 181) // Título
        ));
        painel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        painel.add(campoId, gbc);

        // Nome
        gbc.gridx = 2; gbc.gridy = 0;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 3;
        painel.add(campoNome, gbc);

        // Preço
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Preço (R$):"), gbc);
        gbc.gridx = 1;
        painel.add(campoPreco, gbc);

        // Quantidade
        gbc.gridx = 2; gbc.gridy = 1;
        painel.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 3;
        painel.add(campoQuantidade, gbc);

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(63, 81, 181), 2),
                "Lista de Produtos",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(63, 81, 181)
        ));

        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(63, 81, 181), 2),
                "Ações",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(63, 81, 181)
        ));

        painel.add(btnCriar);
        painel.add(btnAtualizar);
        painel.add(btnDeletar);
        painel.add(btnAdicionarQtd);
        painel.add(btnRemoverQtd);
        painel.add(btnLimpar);

        return painel;
    }

    private void setupEventListeners() {
        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linhaSelecionada = tabelaProdutos.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    preencherCamposComLinhaSelecionada(linhaSelecionada);
                } else {
                    limparCampos();
                }
            }
        });

        btnCriar.addActionListener(e -> criarProduto());
        btnAtualizar.addActionListener(e -> atualizarProduto());
        btnDeletar.addActionListener(e -> deletarProduto());
        btnAdicionarQtd.addActionListener(e -> adicionarQuantidade());
        btnRemoverQtd.addActionListener(e -> removerQuantidade());
        btnLimpar.addActionListener(e -> limparCampos());
    }

    /**
     * Preenche os campos do formulário com os dados da linha selecionada na tabela.
     * @param linha O índice da linha selecionada.
     */
    private void preencherCamposComLinhaSelecionada(int linha) {
        campoId.setText(modeloTabela.getValueAt(linha, 0).toString());
        campoNome.setText(modeloTabela.getValueAt(linha, 1).toString());
        campoPreco.setText(modeloTabela.getValueAt(linha, 2).toString().replace(",", "."));
        campoQuantidade.setText(modeloTabela.getValueAt(linha, 3).toString());
    }

    /**
     * Tenta criar um novo produto usando o controller.
     */
    private void criarProduto() {
        try {
            // Validações de entrada
            String nome = campoNome.getText().trim();
            if (nome.isEmpty()) {
                mostrarMensagemErro("O nome do produto é obrigatório!");
                return;
            }

            double preco;
            try {
                preco = Double.parseDouble(campoPreco.getText().replace(",", "."));
                if (preco < 0) {
                    mostrarMensagemErro("O preço deve ser um valor positivo!");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarMensagemErro("Preço inválido. Por favor, insira um número válido.");
                return;
            }

            int quantidade;
            try {
                quantidade = Integer.parseInt(campoQuantidade.getText());
                if (quantidade < 0) {
                    mostrarMensagemErro("A quantidade deve ser um número inteiro positivo ou zero!");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarMensagemErro("Quantidade inválida. Por favor, insira um número inteiro válido.");
                return;
            }

            ProdutoService produtoCriado = controller.criarProduto(nome, preco, quantidade);
            if (produtoCriado != null) {
                mostrarMensagemSucesso("Produto criado com sucesso!");
                limparCampos();
                atualizarTabela();
            } else {
                mostrarMensagemErro("Não foi possível criar o produto. Verifique os dados.");
            }

        } catch (Exception ex) {
            mostrarMensagemErro("Erro inesperado ao criar produto: " + ex.getMessage());
        }
    }

    /**
     * Tenta atualizar um produto existente usando o controller.
     */
    private void atualizarProduto() {
        try {
            if (campoId.getText().trim().isEmpty()) {
                mostrarMensagemErro("Selecione um produto na tabela para atualizar!");
                return;
            }

            int id = Integer.parseInt(campoId.getText());
            String nome = campoNome.getText().trim();
            if (nome.isEmpty()) {
                mostrarMensagemErro("O nome do produto é obrigatório!");
                return;
            }

            double preco;
            try {
                preco = Double.parseDouble(campoPreco.getText().replace(",", "."));
                if (preco < 0) {
                    mostrarMensagemErro("O preço deve ser um valor positivo!");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarMensagemErro("Preço inválido. Por favor, insira um número válido.");
                return;
            }


            boolean sucesso = controller.atualizarProduto(id, nome, preco);
            if (sucesso) {
                mostrarMensagemSucesso("Produto atualizado com sucesso!");
                limparCampos();
                atualizarTabela();
            } else {
                mostrarMensagemErro("Produto não encontrado ou dados inválidos para atualização.");
            }

        } catch (NumberFormatException ex) {
            mostrarMensagemErro("ID inválido. Por favor, selecione um produto válido.");
        } catch (Exception ex) {
            mostrarMensagemErro("Erro inesperado ao atualizar produto: " + ex.getMessage());
        }
    }

    /**
     * Tenta deletar um produto usando o controller.
     */
    private void deletarProduto() {
        try {
            if (campoId.getText().trim().isEmpty()) {
                mostrarMensagemErro("Selecione um produto na tabela para deletar!");
                return;
            }

            int id = Integer.parseInt(campoId.getText());

            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja deletar o produto '" + campoNome.getText() + "' (ID: " + id + ")?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (resposta == JOptionPane.YES_OPTION) {
                boolean sucesso = controller.deletarProduto(id);
                if (sucesso) {
                    mostrarMensagemSucesso("Produto removido com sucesso!");
                    limparCampos();
                    atualizarTabela();
                } else {
                    mostrarMensagemErro("Produto não encontrado para deletar.");
                }
            }
        } catch (NumberFormatException ex) {
            mostrarMensagemErro("ID inválido. Por favor, selecione um produto válido.");
        } catch (Exception ex) {
            mostrarMensagemErro("Erro inesperado ao deletar produto: " + ex.getMessage());
        }
    }

    /**
     * Tenta adicionar quantidade ao estoque de um produto.
     */
    private void adicionarQuantidade() {
        try {
            if (campoId.getText().trim().isEmpty()) {
                mostrarMensagemErro("Selecione um produto na tabela para adicionar quantidade!");
                return;
            }

            int id = Integer.parseInt(campoId.getText());
            String qtdStr = JOptionPane.showInputDialog(this, "Quantidade a adicionar:", "Adicionar Estoque", JOptionPane.QUESTION_MESSAGE);

            if (qtdStr == null || qtdStr.trim().isEmpty()) {
                return;
            }

            int qtdAdicionar;
            try {
                qtdAdicionar = Integer.parseInt(qtdStr.trim());
                if (qtdAdicionar <= 0) {
                    mostrarMensagemErro("A quantidade a adicionar deve ser um número inteiro positivo!");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarMensagemErro("Quantidade inválida. Por favor, insira um número inteiro válido.");
                return;
            }

            boolean sucesso = controller.adicionarQuantidade(id, qtdAdicionar);
            if (sucesso) {
                mostrarMensagemSucesso("Quantidade adicionada com sucesso!");
                atualizarTabela();
                Optional<ProdutoService> produtoAtualizado = controller.buscarProdutoPorId(id);
                produtoAtualizado.ifPresent(this::preencherCamposComProduto);
            } else {
                mostrarMensagemErro("Não foi possível adicionar a quantidade. Produto não encontrado ou quantidade inválida.");
            }

        } catch (NumberFormatException ex) {
            mostrarMensagemErro("ID inválido. Por favor, selecione um produto válido.");
        } catch (Exception ex) {
            mostrarMensagemErro("Erro inesperado ao adicionar quantidade: " + ex.getMessage());
        }
    }

    /**
     * Tenta remover quantidade do estoque de um produto.
     */
    private void removerQuantidade() {
        try {
            if (campoId.getText().trim().isEmpty()) {
                mostrarMensagemErro("Selecione um produto na tabela para remover quantidade!");
                return;
            }

            int id = Integer.parseInt(campoId.getText());
            String qtdStr = JOptionPane.showInputDialog(this, "Quantidade a remover:", "Remover Estoque", JOptionPane.QUESTION_MESSAGE);

            if (qtdStr == null || qtdStr.trim().isEmpty()) {
                return;
            }

            int qtdRemover;
            try {
                qtdRemover = Integer.parseInt(qtdStr.trim());
                if (qtdRemover <= 0) {
                    mostrarMensagemErro("A quantidade a remover deve ser um número inteiro positivo!");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarMensagemErro("Quantidade inválida. Por favor, insira um número inteiro válido.");
                return;
            }

            Optional<ProdutoService> produtoOpt = controller.buscarProdutoPorId(id);
            if (produtoOpt.isPresent()) {
                ProdutoService produto = produtoOpt.get();
                if (qtdRemover > produto.getQuantidade()) {
                    mostrarMensagemErro("Não é possível remover " + qtdRemover + " unidades. O produto '" + produto.getNome() + "' tem apenas " + produto.getQuantidade() + " em estoque.");
                    return;
                }
            } else {
                mostrarMensagemErro("Produto não encontrado para remover quantidade.");
                return;
            }

            boolean sucesso = controller.removerQuantidade(id, qtdRemover);
            if (sucesso) {
                mostrarMensagemSucesso("Quantidade removida com sucesso!");
                atualizarTabela();
                Optional<ProdutoService> produtoAtualizado = controller.buscarProdutoPorId(id);
                produtoAtualizado.ifPresent(this::preencherCamposComProduto);
            } else {
                mostrarMensagemErro("Não foi possível remover a quantidade. Verifique o ID do produto ou a quantidade.");
            }

        } catch (NumberFormatException ex) {
            mostrarMensagemErro("ID inválido. Por favor, selecione um produto válido.");
        } catch (Exception ex) {
            mostrarMensagemErro("Erro inesperado ao remover quantidade: " + ex.getMessage());
        }
    }

    /**
     * Preenche os campos do formulário com os dados de um objeto ProdutoService.
     * Útil após operações de adicionar/remover quantidade.
     * @param produto O objeto ProdutoService cujos dados serão exibidos.
     */
    private void preencherCamposComProduto(ProdutoService produto) {
        campoId.setText(String.valueOf(produto.getId()));
        campoNome.setText(produto.getNome());
        campoPreco.setText(String.format("%.2f", produto.getPreco()));
        campoQuantidade.setText(String.valueOf(produto.getQuantidade()));
    }

    /**
     * Limpa e recarrega os dados na tabela de produtos.
     */
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        List<ProdutoService> produtos = controller.listarTodosProdutos();
        for (ProdutoService produto : produtos) {
            Object[] linha = {
                    produto.getId(),
                    produto.getNome(),
                    String.format("%.2f", produto.getPreco()),
                    produto.getQuantidade(),
                    String.format("%.2f", produto.getTotalProduto())
            };
            modeloTabela.addRow(linha);
        }
    }

    /**
     * Limpa todos os campos de entrada do formulário e deseleciona a tabela.
     */
    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoPreco.setText("");
        campoQuantidade.setText("");
        tabelaProdutos.clearSelection();
    }

    /**
     * Exibe uma mensagem de sucesso ao usuário.
     * @param mensagem A mensagem a ser exibida.
     */
    private void mostrarMensagemSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exibe uma mensagem de erro ao usuário.
     * @param mensagem A mensagem de erro a ser exibida.
     */
    private void mostrarMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Configura o Look and Feel da aplicação Swing.
     * Prioriza o Nimbus para uma aparência moderna, e tenta o System Look and Feel como fallback.
     */
    private void configurarLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            System.out.println("Look and Feel aplicado: NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println("Look and Feel aplicado: SystemLookAndFeel (fallback)");
            } catch (Exception systemLafException) {
                System.err.println("Falha ao aplicar System Look and Feel: " + systemLafException.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Classe NimbusLookAndFeel não encontrada. Verifique o classpath ou a versão do JRE: " + e.getMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            System.err.println("Erro ao instanciar ou acessar NimbusLookAndFeel: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro geral ao configurar Look and Feel, usando padrão: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProdutoSwingUI().setVisible(true);
        });
    }
}