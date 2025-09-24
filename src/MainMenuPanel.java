import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Painel do menu principal do jogo de Xadrez.
 * Oferece opções para partida manual e partida simulada.
 */
public class MainMenuPanel extends JPanel {
    private ChessGUI parentFrame;

    public MainMenuPanel(ChessGUI parentFrame) {
        this.parentFrame = parentFrame;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Painel central com os componentes principais
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();

        // Título do jogo
        JLabel titleLabel = new JLabel("XADREZ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0);
        centerPanel.add(titleLabel, gbc);

        // Botão para partida manual
        JButton manualGameButton = createMenuButton("Partida Manual");
        manualGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.startManualGame();
            }
        });
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        centerPanel.add(manualGameButton, gbc);

        // Botão para partida simulada
        JButton simulatedGameButton = createMenuButton("Partida Simulada");
        simulatedGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.startSimulatedGame();
            }
        });
        gbc.gridy = 2;
        centerPanel.add(simulatedGameButton, gbc);

        // Botão para sair
        JButton exitButton = createMenuButton("Sair");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 0, 10, 0);
        centerPanel.add(exitButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Cria um botão estilizado para o menu
     */
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }
}
