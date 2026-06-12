package view;

import dao.LivroDAO;
import model.Livro;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

public class TelaLivro extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField txtBusca;
    private LivroDAO dao = new LivroDAO();

    private static final String[] COLUNAS = {"ID", "Título", "ISBN", "Editora", "Ano", "Qtd", "Categoria"};

    public TelaLivro() {
    	setLayout(null);
        construirBarraFerramentas();
        construirTabela();
        carregarDados();
    }

    private void construirBarraFerramentas() {
        JPanel barra = new JPanel(null);
        barra.setPreferredSize(new java.awt.Dimension(0, 40));

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(5, 8, 45, 25);
        barra.add(lblTitulo);

        txtBusca = new JTextField();
        txtBusca.setBounds(55, 8, 180, 25);
        txtBusca.setToolTipText("Buscar por título");
        barra.add(txtBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(240, 8, 80, 25);
        barra.add(btnBuscar);

        JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        sep.setBounds(328, 4, 8, 32);
        barra.add(sep);

        JButton btnNovo = new JButton("Novo");
        btnNovo.setBounds(340, 8, 70, 25);
        barra.add(btnNovo);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(415, 8, 70, 25);
        barra.add(btnEditar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(490, 8, 70, 25);
        barra.add(btnExcluir);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(565, 8, 90, 25);
        barra.add(btnAtualizar);

        barra.setBounds(5, 5, 1000, 40);
        add(barra);

        btnBuscar.addActionListener(e -> buscar());
        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
        btnAtualizar.addActionListener(e -> carregarDados());
        txtBusca.addActionListener(e -> buscar());
    }

    private void construirTabela() {
        modelo = new DefaultTableModel(COLUNAS, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getColumnModel().getColumn(0).setMaxWidth(50);
        tabela.setRowHeight(24);
        tabela.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(5, 50, 1005, 230);
        add(scroll);
    }

    private void carregarDados() {
        try {
            popular(dao.listarTodos());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + ex.getMessage());
        }
    }

    private void buscar() {
        try {
            popular(dao.buscarPorTitulo(txtBusca.getText().trim()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void popular(List<Livro> livros) {
        modelo.setRowCount(0);
        for (Livro l : livros) {
            modelo.addRow(new Object[]{
                l.getId(),
                l.getTitulo(),
                l.getIsbn(),
                l.getEditora(),
                l.getAnoPublicacao(),
                l.getQuantidade(),
                l.getCategoria() != null ? l.getCategoria().getNome() : "-"
            });
        }
    }

    private Livro livroSelecionado() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um livro.");
            return null;
        }
        try {
            return dao.buscarPorId((int) modelo.getValueAt(row, 0));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            return null;
        }
    }

    private void editarSelecionado() {
        Livro l = livroSelecionado();
        if (l != null) {
            abrirFormulario(l);
        }
    }

    private void excluirSelecionado() {
        Livro l = livroSelecionado();
        if (l == null) {
            return;
        }
        int op = JOptionPane.showConfirmDialog(this,
            "Excluir livro \"" + l.getTitulo() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            try {
                dao.excluir(l.getId());
                carregarDados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        }
    }

    private void abrirFormulario(Livro livro) {
        FormularioLivro form = new FormularioLivro(
            (JFrame) SwingUtilities.getWindowAncestor(this), livro);
        form.setVisible(true);
        carregarDados();
    }
}
