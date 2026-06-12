package view;

import dao.MembroDAO;
import model.Membro;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class TelaMembro extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;
    private MembroDAO dao = new MembroDAO();

    private static final String[] COLUNAS = {"ID", "Nome", "CPF", "E-mail", "Telefone", "Ativo"};

    public TelaMembro() {
    	setLayout(null);
        construirBarraFerramentas();
        construirTabela();
        carregarDados();
    }

    private void construirBarraFerramentas() {
        JPanel barra = new JPanel(null);
        barra.setPreferredSize(new java.awt.Dimension(0, 40));

        JButton btnNovo = new JButton("Novo");
        btnNovo.setBounds(5, 8, 80, 25);
        barra.add(btnNovo);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(90, 8, 80, 25);
        barra.add(btnEditar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(175, 8, 80, 25);
        barra.add(btnExcluir);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(260, 8, 90, 25);
        barra.add(btnAtualizar);

        barra.setBounds(5, 5, 400, 40);
        add(barra);

        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
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
        
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(5, 50, 1005, 230);
        add(scroll);
    }

    private void carregarDados() {
        try {
            modelo.setRowCount(0);
            for (Membro m : dao.listarTodos()) {
                modelo.addRow(new Object[]{
                    m.getId(),
                    m.getNome(),
                    m.getCpf(),
                    m.getEmail(),
                    m.getTelefone(),
                    m.isAtivo() ? "Sim" : "Não"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private Membro membroSelecionado() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um membro.");
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
        Membro m = membroSelecionado();
        if (m != null) {
            abrirFormulario(m);
        }
    }

    private void excluirSelecionado() {
        Membro m = membroSelecionado();
        if (m == null) {
            return;
        }
        int op = JOptionPane.showConfirmDialog(this,
            "Excluir membro \"" + m.getNome() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            try {
                dao.excluir(m.getId());
                carregarDados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        }
    }

    private void abrirFormulario(Membro membro) {
        JTextField txtNome     = new JTextField(membro != null ? membro.getNome()     : "", 22);
        JTextField txtCpf      = new JTextField(membro != null ? membro.getCpf()      : "", 22);
        JTextField txtEmail    = new JTextField(membro != null ? membro.getEmail()    : "", 22);
        JTextField txtTelefone = new JTextField(membro != null ? membro.getTelefone() : "", 22);
        JTextField txtEndereco = new JTextField(membro != null ? membro.getEndereco() : "", 22);

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 6));
        form.add(new JLabel("Nome*:"));
        form.add(txtNome);
        form.add(new JLabel("CPF*:"));
        form.add(txtCpf);
        form.add(new JLabel("E-mail:"));
        form.add(txtEmail);
        form.add(new JLabel("Telefone:"));
        form.add(txtTelefone);
        form.add(new JLabel("Endereço:"));
        form.add(txtEndereco);

        String titulo = membro == null ? "Novo Membro" : "Editar Membro";
        int op = JOptionPane.showConfirmDialog(this, form, titulo,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (op == JOptionPane.OK_OPTION) {
            try {
                if (membro == null) {
                    membro = new Membro();
                }
                membro.setNome(txtNome.getText().trim());
                membro.setCpf(txtCpf.getText().trim());
                membro.setEmail(txtEmail.getText().trim());
                membro.setTelefone(txtTelefone.getText().trim());
                membro.setEndereco(txtEndereco.getText().trim());
                if (membro.getId() == 0) {
                    dao.inserir(membro);
                } else {
                    dao.atualizar(membro);
                }
                carregarDados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        }
    }
}
