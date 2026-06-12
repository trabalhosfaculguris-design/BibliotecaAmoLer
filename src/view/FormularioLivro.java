package view;

import dao.CategoriaDAO;
import dao.LivroDAO;
import model.Categoria;
import model.Livro;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.List;

public class FormularioLivro extends JDialog {

    private JTextField txtTitulo;
    private JTextField txtIsbn;
    private JTextField txtAno;
    private JTextField txtEditora;
    private JTextField txtQtd;
    private JComboBox<Categoria> cbCategoria;
    private LivroDAO livroDAO    = new LivroDAO();
    private CategoriaDAO catDAO  = new CategoriaDAO();
    private Livro livro;

    public FormularioLivro(JFrame pai, Livro livro) {
        super(pai, livro == null ? "Novo Livro" : "Editar Livro", true);
        this.livro = livro;
        setSize(420, 320);
        setLocationRelativeTo(pai);
        setLayout(null);
        construirUI();
        if (livro != null) {
            preencherCampos();
        }
    }

    private void construirUI() {
        int lx = 20;
        int fx = 130;
        int fw = 250;
        int fh = 25;
        int y  = 20;
        int dy = 38;

        JLabel lblTitulo = new JLabel("Título*:");
        lblTitulo.setBounds(lx, y, 100, fh);
        add(lblTitulo);
        txtTitulo = new JTextField();
        txtTitulo.setBounds(fx, y, fw, fh);
        add(txtTitulo);
        y += dy;

        JLabel lblIsbn = new JLabel("ISBN:");
        lblIsbn.setBounds(lx, y, 100, fh);
        add(lblIsbn);
        txtIsbn = new JTextField();
        txtIsbn.setBounds(fx, y, fw, fh);
        add(txtIsbn);
        y += dy;

        JLabel lblAno = new JLabel("Ano:");
        lblAno.setBounds(lx, y, 100, fh);
        add(lblAno);
        txtAno = new JTextField();
        txtAno.setBounds(fx, y, fw, fh);
        add(txtAno);
        y += dy;

        JLabel lblEditora = new JLabel("Editora:");
        lblEditora.setBounds(lx, y, 100, fh);
        add(lblEditora);
        txtEditora = new JTextField();
        txtEditora.setBounds(fx, y, fw, fh);
        add(txtEditora);
        y += dy;

        JLabel lblQtd = new JLabel("Quantidade*:");
        lblQtd.setBounds(lx, y, 100, fh);
        add(lblQtd);
        txtQtd = new JTextField("1");
        txtQtd.setBounds(fx, y, fw, fh);
        add(txtQtd);
        y += dy;

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(lx, y, 100, fh);
        add(lblCategoria);
        cbCategoria = new JComboBox<>();
        cbCategoria.setBounds(fx, y, fw, fh);
        carregarCategorias();
        add(cbCategoria);
        y += dy;

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, y, 90, 30);
        add(btnCancelar);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(300, y, 90, 30);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarCategorias() {
        try {
            cbCategoria.addItem(null);
            List<Categoria> cats = catDAO.listarTodos();
            for (Categoria c : cats) {
                cbCategoria.addItem(c);
            }
        } catch (Exception ex) {
            // ignora erro ao carregar categorias
        }
    }

    private void preencherCampos() {
        txtTitulo.setText(livro.getTitulo());
        txtIsbn.setText(livro.getIsbn());
        txtAno.setText(String.valueOf(livro.getAnoPublicacao()));
        txtEditora.setText(livro.getEditora());
        txtQtd.setText(String.valueOf(livro.getQuantidade()));
        if (livro.getCategoria() != null) {
            for (int i = 0; i < cbCategoria.getItemCount(); i++) {
                Categoria c = cbCategoria.getItemAt(i);
                if (c != null && c.getId() == livro.getCategoria().getId()) {
                    cbCategoria.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void salvar() {
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título é obrigatório.");
            return;
        }
        try {
            if (livro == null) {
                livro = new Livro();
            }
            livro.setTitulo(titulo);
            livro.setIsbn(txtIsbn.getText().trim());
            livro.setAnoPublicacao(Integer.parseInt(
                txtAno.getText().trim().isEmpty() ? "0" : txtAno.getText().trim()));
            livro.setEditora(txtEditora.getText().trim());
            livro.setQuantidade(Integer.parseInt(
                txtQtd.getText().trim().isEmpty() ? "0" : txtQtd.getText().trim()));
            livro.setCategoria((Categoria) cbCategoria.getSelectedItem());

            if (livro.getId() == 0) {
                livroDAO.inserir(livro);
            } else {
                livroDAO.atualizar(livro);
            }

            JOptionPane.showMessageDialog(this, "Livro salvo com sucesso!");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ano e Quantidade devem ser números.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}
