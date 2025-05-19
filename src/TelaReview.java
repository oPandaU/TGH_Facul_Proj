
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaReview extends JFrame {

    private List<Jogo> listaJogos;
    private List<Review> listaReviews;

    public TelaReview(List<Jogo> listaJogos, List<Review> listaReviews) {
        this.listaJogos = listaJogos;
        this.listaReviews = listaReviews;
        setTitle("Fazer Review");
        setSize(500, 400);
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
        JTextArea areaReview = new JTextArea(5, 25);
        JScrollPane scroll = new JScrollPane(areaReview);

        JButton btnEnviar = new JButton("Enviar Review");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(labelTitulo, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(labelJogo, gbc);
        gbc.gridx = 1;
        panel.add(comboJogos, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(labelReview, gbc);
        gbc.gridx = 1;
        panel.add(scroll, gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnEnviar, gbc);

        btnEnviar.addActionListener(e -> {
            String jogoSelecionado = (String) comboJogos.getSelectedItem();
            String review = areaReview.getText();
            if (review.length() > 10) {
                JOptionPane.showMessageDialog(this, "Review enviada para: " + jogoSelecionado);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "A review deve ter pelo menos 10 caracteres.");
            }
        });

        add(panel);
    }
}
