
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class TelaPrincipal extends JFrame {

    private List<Jogo> listaJogos = new ArrayList<>();
    private List<Review> listaReviews = new ArrayList<>();

    public TelaPrincipal() {
        setTitle("Pesquisar Jogos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarReviews();

        listaJogos.add(new Jogo("The Legend of Zelda", "Aventura", "2023-05-12", "Nintendo", "Nintendo"));
        listaJogos.add(new Jogo("God of War", "Ação", "2022-01-14", "Santa Monica Studio", "Sony"));
        listaJogos.add(new Jogo("Dark Souls II Scholar of the First Sin", "Souls-Like", "01-04-2015", "From Software", "Bandai Namco"));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel labelTitulo = new JLabel("Pesquisar Jogos");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JTextField campoPesquisa = new JTextField(20);
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnCadastrar = new JButton("Cadastrar Jogo");
        JButton btnReview = new JButton("Fazer Review");
        JButton btnVerReviews = new JButton("Ver Todas as Reviews");
        JButton btnVerReviewsPorJogo = new JButton("Ver Reviews por Jogo");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelTitulo, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Digite o nome do jogo:"), gbc);

        gbc.gridy = 2;
        panel.add(campoPesquisa, gbc);

        gbc.gridy = 3;
        panel.add(btnPesquisar, gbc);

        gbc.gridy = 4;
        panel.add(btnCadastrar, gbc);

        gbc.gridy = 5;
        panel.add(btnReview, gbc);

        gbc.gridy = 6;
        panel.add(btnVerReviews, gbc);

        gbc.gridy = 7;
        panel.add(btnVerReviewsPorJogo, gbc);

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

        btnCadastrar.addActionListener(e -> new TelaCadastro(listaJogos).setVisible(true));
        btnReview.addActionListener(e -> new TelaReview(listaJogos, listaReviews).setVisible(true));
        btnVerReviewsPorJogo.addActionListener(e -> new TelaVisualizarReviews(listaJogos, listaReviews).setVisible(true));

        btnVerReviews.addActionListener(e -> {
            if (listaReviews.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma review cadastrada ainda.");
            } else {
                StringBuilder todas = new StringBuilder("<html><b>Todas as Reviews:</b><br>");
                for (Review r : listaReviews) {
                    todas.append(r.toString().replace("\n", "<br>"));
                }
                todas.append("</html>");
                JOptionPane.showMessageDialog(this, todas.toString());
            }
        });

        add(panel);
    }

    private void carregarReviews() {
        File file = new File("reviews.txt");
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\|\\|");
                if (partes.length == 2) {
                    listaReviews.add(new Review(partes[0], partes[1]));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar as reviews.");
        }
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
