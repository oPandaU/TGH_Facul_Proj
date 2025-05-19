
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private List<Jogo> listaJogos = new ArrayList<>();

    public TelaPrincipal() {
        setTitle("Pesquisar Jogos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        listaJogos.add(new Jogo("The Legend of Zelda", "Aventura", "2023-05-12", "Nintendo", "Nintendo"));
        listaJogos.add(new Jogo("God of War", "Ação", "2022-01-14", "Santa Monica Studio", "Sony"));

        JPanel panel = new JPanel();
        JTextField campoPesquisa = new JTextField(20);
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnCadastrar = new JButton("Cadastrar Jogo");
        JButton btnReview = new JButton("Fazer Review");

        panel.add(new JLabel("Pesquisar jogo:"));
        panel.add(campoPesquisa);
        panel.add(btnPesquisar);
        panel.add(btnCadastrar);
        panel.add(btnReview);

        btnPesquisar.addActionListener(e -> {
            String termo = campoPesquisa.getText().toLowerCase();
            StringBuilder resultado = new StringBuilder("Resultados:\n");
            for (Jogo j : listaJogos) {
                if (j.getNome().toLowerCase().contains(termo)) {
                    resultado.append(j.getNome()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, resultado.toString());
        });

        btnCadastrar.addActionListener(e -> new TelaCadastro(listaJogos).setVisible(true));
        btnReview.addActionListener(e -> new TelaReview(listaJogos).setVisible(true));

        add(panel);
    }

}
