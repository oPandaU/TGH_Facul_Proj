package view;

import dao.JogoDAO;
import dao.ReviewDAO;
import model.Jogo;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private List<Jogo> listaJogos;
    private JogoDAO jogoDAO = new JogoDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();

    public TelaPrincipal() {
        setTitle("Pesquisar Jogos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        listaJogos = jogoDAO.listarTodos();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelTitulo = new JLabel("Pesquisar Jogos");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JTextField campoPesquisa = new JTextField(25);
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnCadastrar = new JButton("Cadastrar Jogo");
        JButton btnVerReviews = new JButton("Ver Reviews por Jogo");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(labelTitulo, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Digite o nome do jogo:"), gbc);

        gbc.gridy = 2;
        panel.add(campoPesquisa, gbc);

        gbc.gridy = 3;
        panel.add(btnPesquisar, gbc);

        gbc.gridy = 4;
        panel.add(btnCadastrar, gbc);

        gbc.gridy = 5;
        panel.add(btnVerReviews, gbc);

        btnPesquisar.addActionListener(e -> {
            String termo = campoPesquisa.getText().toLowerCase();
            StringBuilder resultado = new StringBuilder("<html><b>Resultados:</b><br>");
            for (Jogo j : listaJogos) {
                if (j.getNome().toLowerCase().contains(termo)) {
                    resultado.append(j.getNome()).append("<br>");
                }
            }
            resultado.append("</html>");
            JOptionPane.showMessageDialog(this, resultado.toString());
        });

        btnCadastrar.addActionListener(e -> {
            List<Jogo> listaJogos = jogoDAO.listarTodos();
            new TelaCadastro(listaJogos).setVisible(true);
        });

        btnVerReviews.addActionListener(e -> {
            new TelaReview(reviewDAO, listaJogos).setVisible(true);
        });

        add(panel);
    }
}
