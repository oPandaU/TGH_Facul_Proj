
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TelaVisualizarReviews extends JFrame {

    private List<Jogo> listaJogos;
    private List<Review> listaReviews;

    public TelaVisualizarReviews(List<Jogo> listaJogos, List<Review> listaReviews) {
        this.listaJogos = listaJogos;
        this.listaReviews = listaReviews;
        setTitle("Ver Reviews por Jogo");
        setSize(800, 600);
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
            StringBuilder texto = new StringBuilder();
            boolean encontrou = false;

            for (Review r : listaReviews) {
                if (r.getJogoNome().equals(jogoSelecionado)) {
                    texto.append("Review: ").append(r.getTexto()).append("\n--------------------\n");
                    encontrou = true;
                }
            }

            if (!encontrou) {
                areaReviews.setText("Nenhuma review encontrada para '" + jogoSelecionado + "'.");
            } else {
                areaReviews.setText(texto.toString());
            }
        });

        btnEditar.addActionListener(e -> {
            String jogoSelecionado = (String) comboJogos.getSelectedItem();
            String novaReview = JOptionPane.showInputDialog(this, "Digite a nova review:");

            if (novaReview != null && !novaReview.trim().isEmpty()) {
                for (int i = 0; i < listaReviews.size(); i++) {
                    if (listaReviews.get(i).getJogoNome().equals(jogoSelecionado)) {
                        listaReviews.set(i, new Review(jogoSelecionado, novaReview));
                        salvarReviews();
                        JOptionPane.showMessageDialog(this, "Review atualizada!");
                        btnVer.doClick();
                        break;
                    }
                }
            }
        });

        btnExcluir.addActionListener(e -> {
            String jogoSelecionado = (String) comboJogos.getSelectedItem();
            boolean encontrou = false;
            for (int i = 0; i < listaReviews.size(); i++) {
                if (listaReviews.get(i).getJogoNome().equals(jogoSelecionado)) {
                    listaReviews.remove(i);
                    salvarReviews();
                    JOptionPane.showMessageDialog(this, "Review excluída!");
                    btnVer.doClick();
                    encontrou = true;
                    break;
                }
            }
            if (!encontrou) {
                JOptionPane.showMessageDialog(this, "Nenhuma review encontrada para esse jogo.");
            }
        });

        add(panel);
    }

    private void salvarReviews() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reviews.txt"))) {
            for (Review r : listaReviews) {
                writer.write(r.getJogoNome() + "||" + r.getTexto());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar as reviews.");
        }
    }
}
