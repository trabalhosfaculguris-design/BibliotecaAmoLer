package view;

import dao.EmprestimoDAO;
import dao.LivroDAO;
import dao.MembroDAO;
import model.Emprestimo;
import model.Livro;
import model.Membro;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

public class TelaEmprestimo extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;
    private EmprestimoDAO empDAO = new EmprestimoDAO();
    private MembroDAO memDAO     = new MembroDAO();
    private LivroDAO livDAO      = new LivroDAO();

    private static final String[] COLUNAS = {
        "ID", "Membro", "Livro", "Emprestado em", "Devolver em", "Devolvido em", "Status"
    };

    public TelaEmprestimo() {
    	setLayout(null);
        construirBarraFerramentas();
        construirTabela();
        carregarDados();
    }

    private void construirBarraFerramentas() {
        JPanel barra = new JPanel(null);
        barra.setPreferredSize(new java.awt.Dimension(0, 40));

        JButton btnNovo = new JButton("Novo Empréstimo");
        btnNovo.setBounds(5, 8, 150, 25);
        barra.add(btnNovo);

        JButton btnDevolver = new JButton("Registrar Devolução");
        btnDevolver.setBounds(160, 8, 160, 25);
        barra.add(btnDevolver);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(325, 8, 90, 25);
        barra.add(btnAtualizar);

        barra.setBounds(5, 5, 450, 40);
        add(barra);

        btnNovo.addActionListener(e -> novoEmprestimo());
        btnDevolver.addActionListener(e -> registrarDevolucao());
        btnAtualizar.addActionListener(e -> carregarDados());
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
        tabela.setRowHeight(24);
        tabela.getColumnModel().getColumn(0).setMaxWidth(45);

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                String status = (String) modelo.getValueAt(r, 6);
                if (!sel) {
                    if ("Atrasado".equalsIgnoreCase(status)) {
                        comp.setBackground(new Color(255, 220, 220));
                    } else if ("Devolvido".equalsIgnoreCase(status)) {
                        comp.setBackground(new Color(220, 255, 220));
                    } else {
                        comp.setBackground(Color.WHITE);
                    }
                }
                return comp;
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(5, 50, 1005, 230);
        add(scroll);
    }

    private void carregarDados() {
        try {
            modelo.setRowCount(0);
            List<Emprestimo> lista = empDAO.listarTodos();
            for (Emprestimo e : lista) {
                modelo.addRow(new Object[]{
                    e.getId(),
                    e.getMembro().getNome(),
                    e.getLivro().getTitulo(),
                    e.getDataEmprestimo(),
                    e.getDataPrevista(),
                    e.getDataDevolucao() != null ? e.getDataDevolucao() : "-",
                    e.getStatus().toString()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void novoEmprestimo() {
        try {
            List<Membro> membros = memDAO.listarTodos();
            List<Livro> livros   = livDAO.listarTodos();

            if (membros.isEmpty() || livros.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Cadastre membros e livros antes de realizar empréstimos.");
                return;
            }

            JComboBox<Membro> cbMembro = new JComboBox<>(membros.toArray(new Membro[0]));
            JComboBox<Livro> cbLivro   = new JComboBox<>(livros.stream()
                .filter(Livro::isDisponivel).toArray(Livro[]::new));

            if (cbLivro.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Nenhum livro disponível em estoque.");
                return;
            }

            JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
            form.add(new JLabel("Membro:"));
            form.add(cbMembro);
            form.add(new JLabel("Livro:"));
            form.add(cbLivro);

            int op = JOptionPane.showConfirmDialog(this, form, "Novo Empréstimo",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (op == JOptionPane.OK_OPTION) {
                Emprestimo emp = new Emprestimo();
                emp.setMembro((Membro) cbMembro.getSelectedItem());
                emp.setLivro((Livro) cbLivro.getSelectedItem());
                empDAO.inserir(emp);
                JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!");
                carregarDados();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void registrarDevolucao() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo.");
            return;
        }
        String status = (String) modelo.getValueAt(row, 6);
        if ("Devolvido".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Este empréstimo já foi devolvido.");
            return;
        }
        int id = (int) modelo.getValueAt(row, 0);
        int op = JOptionPane.showConfirmDialog(this,
            "Confirmar devolução?", "Devolução", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            try {
                empDAO.registrarDevolucao(id);
                JOptionPane.showMessageDialog(this, "Devolução registrada!");
                carregarDados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        }
    }
}
