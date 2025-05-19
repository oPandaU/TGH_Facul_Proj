
import javax.swing.*;
import java.util.List;

public class TelaCadastro extends JFrame {

    private List<Jogo> listaJogos;

    public TelaCadastro(List<Jogo> listaJogos) {
        this.listaJogos = listaJogos;
        setTitle("Cadastrar Jogo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JTextField campoNome = new JTextField(20);
        JTextField campoDescricao = new JTextField(20);
        JTextField campoData = new JTextField(20);
        JTextField campoDev = new JTextField(20);
        JTextField campoPub = new JTextField(20);
        JButton btnSalvar = new JButton("Salvar");

        panel.add(new JLabel("Nome:"));
        panel.add(campoNome);
        panel.add(new JLabel("Descrição:"));
        panel.add(campoDescricao);
        panel.add(new JLabel("Data de Lançamento:"));
        panel.add(campoData);
        panel.add(new JLabel("Desenvolvedora:"));
        panel.add(campoDev);
        panel.add(new JLabel("Publicadora:"));
        panel.add(campoPub);
        panel.add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            String nome = campoNome.getText();
            String desc = campoDescricao.getText();
            String data = campoData.getText();
            String dev = campoDev.getText();
            String pub = campoPub.getText();

            if (!nome.isEmpty()) {
                listaJogos.add(new Jogo(nome, desc, data, dev, pub));
                JOptionPane.showMessageDialog(this, "Jogo cadastrado!");
                dispose(); // Fecha a tela
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o nome do jogo.");
            }
        });

        add(panel);
    }
}
