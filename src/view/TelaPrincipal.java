package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class TelaPrincipal extends JFrame {

    private JTabbedPane abas;

    public TelaPrincipal() {
        super("Sistema de Gerenciamento de Biblioteca");
        configurarJanela();
        construirMenu();
        construirAbas();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 950);
        setLocationRelativeTo(null);

        setLayout(null);

        JLabel titulo = new JLabel("  Biblioteca AmoLer", SwingConstants.LEFT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(44, 62, 80));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));

        titulo.setBounds(0, 0, 900, 40);

        add(titulo);
    }

    private void construirMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu mCadastros  = new JMenu("Cadastros");
        JMenu mMovimentos = new JMenu("Movimentos");
        JMenu mRelatorios = new JMenu("Relatórios");
        JMenu mSistema    = new JMenu("Sistema");

        JMenuItem miLivros     = new JMenuItem("Livros");
        JMenuItem miMembros    = new JMenuItem("Membros");
        JMenuItem miAutores    = new JMenuItem("Autores");
        JMenuItem miCategorias = new JMenuItem("Categorias");

        JMenuItem miEmprestimos = new JMenuItem("Empréstimos");
        JMenuItem miDevolucoes  = new JMenuItem("Devoluções");

        JMenuItem miSair = new JMenuItem("Sair");

        mCadastros.add(miLivros);
        mCadastros.add(miMembros);
        mCadastros.add(miAutores);
        mCadastros.add(miCategorias);

        mMovimentos.add(miEmprestimos);
        mMovimentos.add(miDevolucoes);

        mSistema.add(miSair);

        menuBar.add(mCadastros);
        menuBar.add(mMovimentos);
        menuBar.add(mRelatorios);
        menuBar.add(mSistema);
        setJMenuBar(menuBar);

        miLivros.addActionListener(e -> abas.setSelectedIndex(0));
        miMembros.addActionListener(e -> abas.setSelectedIndex(1));
        miEmprestimos.addActionListener(e -> abas.setSelectedIndex(2));
        miSair.addActionListener(e -> System.exit(0));
    }

    private void construirAbas() {
        abas = new JTabbedPane();

        abas.addTab("Livros",      new TelaLivro());
        abas.addTab("Membros",     new TelaMembro());
        abas.addTab("Empréstimos", new TelaEmprestimo());

        abas.setBounds(0, 40, 1300, 950);

        add(abas);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin login = new TelaLogin();
            login.setVisible(true);
        });
    }
}
