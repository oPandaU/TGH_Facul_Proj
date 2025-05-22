package view;

import dao.ReviewDAO;
import model.Review;
import model.Jogo;
import javax.swing.*;
import java.awt.*;

import java.util.List;

public class TelaReview extends JFrame {

    private ReviewDAO reviewDAO;

    public TelaReview(ReviewDAO reviewDAO, List<Jogo> listaJogos) {
        this.reviewDAO = reviewDAO;

        setTitle("Deixar uma Review");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelTitulo = new JLabel("Escreva uma review para um jogo");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel labelJogo = new JLabel("Selecione o jogo:");
        JComboBox<String> comboJogos = new JComboBox<>();

        for (Jogo j : listaJogos) {
            comboJogos.addItem(j.getNome());
        }

        JLabel labelReview = new JLabel("Sua review:");
        JTextArea areaReview = new JTextArea(5, 30);
        JScrollPane scroll = new JScrollPane(areaReview);

        JButton btnSalvar = new JButton("Salvar Review");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(labelTitulo, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelJogo, gbc);

        gbc.gridy = 2;
        panel.add(comboJogos, gbc);

        gbc.gridy = 3;
        panel.add(labelReview, gbc);

        gbc.gridy = 4;
        panel.add(scroll, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> {
            String jogoSelecionado = (String) comboJogos.getSelectedItem();
            String textoReview = areaReview.getText().trim();

            if (textoReview.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A review não pode estar vazia.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int jogoId = getIdPorNome(listaJogos, jogoSelecionado);
            if (jogoId == -1) {
                JOptionPane.showMessageDialog(this, "Erro ao encontrar o jogo selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                reviewDAO.salvar(new Review("", textoReview), jogoId);
                JOptionPane.showMessageDialog(this, "Review salva com sucesso!");
                areaReview.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a review.", "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        add(panel);
    }

    private int getIdPorNome(List<Jogo> listaJogos, String nome) {
        for (Jogo j : listaJogos) {
            if (j.getNome().equals(nome)) {
                return j.getId();
            }
        }
        return -1;
    }
}
