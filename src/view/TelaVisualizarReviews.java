package view;

import dao.ReviewDAO;
import model.Review;
import model.Jogo;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaVisualizarReviews extends JFrame {

    private List<Jogo> listaJogos;
    private ReviewDAO reviewDAO;

    public TelaVisualizarReviews(List<Jogo> listaJogos, ReviewDAO reviewDAO) {
        this.listaJogos = listaJogos;
        this.reviewDAO = reviewDAO;

        setTitle("Ver Reviews por Jogo");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelTitulo = new JLabel("Escolha um jogo para ver as reviews:");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JComboBox<String> comboJogos = new JComboBox<>();
        for (Jogo j : listaJogos) {
            comboJogos.addItem(j.getNome());
        }

        JTextArea areaReviews = new JTextArea(15, 40);
        areaReviews.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaReviews);

        JButton btnVer = new JButton("Ver Reviews");
        JButton btnEditar = new JButton("Editar Review");
        JButton btnExcluir = new JButton("Excluir Review");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(labelTitulo, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Selecione o jogo:"), gbc);

        gbc.gridy = 2;
        panel.add(comboJogos, gbc);

        gbc.gridy = 3;
        panel.add(btnVer, gbc);

        gbc.gridy = 4;
        panel.add(scroll, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnEditar, gbc);
        gbc.gridx = 1;
        panel.add(btnExcluir, gbc);

        btnVer.addActionListener(e -> {
            String jogoSelecionado = (String) comboJogos.getSelectedItem();
            int jogoId = getIdPorNome(jogoSelecionado);
            if (jogoId == -1) {
                areaReviews.setText("Nenhum ID encontrado para esse jogo.");
                return;
            }

            List<String> reviews = reviewDAO.listarPorJogo(jogoId)
                    .stream()
                    .map(r -> r.getTexto())
                    .toList();

            if (reviews.isEmpty()) {
                areaReviews.setText("Nenhuma review encontrada.");
            } else {
                StringBuilder texto = new StringBuilder();
                for (String rev : reviews) {
                    texto.append("Review: ").append(rev).append("\n--------------------\n");
                }
                areaReviews.setText(texto.toString());
            }
        });

        btnEditar.addActionListener(e -> {
            String jogoSelecionado = (String) comboJogos.getSelectedItem();
            int jogoId = getIdPorNome(jogoSelecionado);
            if (jogoId == -1) {
                JOptionPane.showMessageDialog(this, "Erro ao encontrar jogo.");
                return;
            }

            List<String> reviews = reviewDAO.listarPorJogo(jogoId)
                    .stream()
                    .map(r -> r.getTexto())
                    .toList();

            if (reviews.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma review encontrada.");
                return;
            }

            String novaReview = JOptionPane.showInputDialog(this, "Edite a review:", reviews.get(0));
            if (novaReview != null && !novaReview.trim().isEmpty()) {
                reviewDAO.excluirPrimeiraReview(jogoId);
                reviewDAO.salvar(new Review("", novaReview), jogoId);
                JOptionPane.showMessageDialog(this, "Review atualizada!");
                btnVer.doClick();
            }
        });

        btnExcluir.addActionListener(e -> {
            String jogoSelecionado = (String) comboJogos.getSelectedItem();
            int jogoId = getIdPorNome(jogoSelecionado);
            if (jogoId == -1) {
                JOptionPane.showMessageDialog(this, "Erro ao encontrar jogo.");
                return;
            }

            reviewDAO.excluirTodasReviews(jogoId);
            JOptionPane.showMessageDialog(this, "Todas as reviews excluídas!");
            areaReviews.setText("");
        });

        add(panel);
    }

    private int getIdPorNome(String nome) {
        for (Jogo j : listaJogos) {
            if (j.getNome().equals(nome)) {
                return j.getId();
            }
        }
        return -1;
    }

}
