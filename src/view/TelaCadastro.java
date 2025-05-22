package view;


import model.Jogo;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCadastro extends JFrame {

    private List<Jogo> listaJogos;

    public TelaCadastro(List<Jogo> listaJogos) {
        this.listaJogos = listaJogos;
        setTitle("Cadastrar Jogo");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelTitulo = new JLabel("Cadastrar Novo Jogo");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel labelNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField(25);

        JLabel labelDescricao = new JLabel("Descrição:");
        JTextField campoDescricao = new JTextField(25);

        JLabel labelData = new JLabel("Data de Lançamento:");
        JTextField campoData = new JTextField(25);

        JLabel labelDev = new JLabel("Desenvolvedora:");
        JTextField campoDev = new JTextField(25);

        JLabel labelPub = new JLabel("Publicadora:");
        JTextField campoPub = new JTextField(25);

        JButton btnSalvar = new JButton("Salvar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(labelTitulo, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(labelNome, gbc);
        gbc.gridx = 1;
        panel.add(campoNome, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(labelDescricao, gbc);
        gbc.gridx = 1;
        panel.add(campoDescricao, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(labelData, gbc);
        gbc.gridx = 1;
        panel.add(campoData, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(labelDev, gbc);
        gbc.gridx = 1;
        panel.add(campoDev, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        panel.add(labelPub, gbc);
        gbc.gridx = 1;
        panel.add(campoPub, gbc);

        gbc.gridy = 6;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> {
            String nome = campoNome.getText();
            String desc = campoDescricao.getText();
            String data = campoData.getText();
            String dev = campoDev.getText();
            String pub = campoPub.getText();

            if (!nome.isEmpty()) {
                listaJogos.add(new Jogo(nome, desc, data, dev, pub));
                JOptionPane.showMessageDialog(this, "Jogo cadastrado!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o nome do jogo.");
            }
        });

        add(panel);
    }
}
