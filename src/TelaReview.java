
import javax.swing.*;
import java.util.List;

public class TelaReview extends JFrame {

    private List<Jogo> listaJogos;

    public TelaReview(List<Jogo> listaJogos) {
        this.listaJogos = listaJogos;
        setTitle("Fazer Review");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JComboBox<String> comboJogos = new JComboBox<>();
        JTextArea areaReview = new JTextArea(5, 20);
        JButton btnEnviar = new JButton("Enviar Review");

        for (Jogo j : listaJogos) {
            comboJogos.addItem(j.getNome());
        }

        panel.add(new JLabel("Selecione o jogo:"));
        panel.add(comboJogos);
        panel.add(new JLabel("Sua review:"));
        panel.add(areaReview);
        panel.add(btnEnviar);

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
