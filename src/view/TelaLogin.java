package view;

import dao.UsuarioDAO;
import model.UsuarioSistema;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Frame;

public class TelaLogin extends JDialog {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnCancelar;

    public TelaLogin() {
        super((Frame) null, "Login – Biblioteca", true);
        setSize(340, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        construirUI();
    }

    private void construirUI() {
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(30, 30, 60, 25);
        add(lblLogin);

        txtLogin = new JTextField();
        txtLogin.setBounds(100, 30, 190, 25);
        add(txtLogin);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(30, 70, 60, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(100, 70, 190, 25);
        add(txtSenha);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(100, 125, 90, 30);
        add(btnCancelar);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(200, 125, 90, 30);
        add(btnEntrar);

        getRootPane().setDefaultButton(btnEntrar);

        btnEntrar.addActionListener(e -> autenticar());
        btnCancelar.addActionListener(e -> System.exit(0));
        txtSenha.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha login e senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();
            UsuarioSistema usuario = dao.autenticar(login, senha);
            if (usuario != null) {
                dispose();
                TelaPrincipal principal = new TelaPrincipal();
                principal.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                txtSenha.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
