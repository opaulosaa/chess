import javax.swing.*;
import java.awt.*;

/**
 * Classe principal da interface gráfica do jogo de Xadrez.
 * Gerencia a navegação entre as diferentes telas (menu principal e tabuleiro).
 */
public class ChessGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MainMenuPanel menuPanel;
    private ChessBoardPanel boardPanel;

    public ChessGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Xadrez");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centraliza na tela

        // Configuração do CardLayout para navegação entre telas
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Criação dos painéis
        menuPanel = new MainMenuPanel(this);
        boardPanel = new ChessBoardPanel(this);

        // Adição dos painéis ao CardLayout
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(boardPanel, "BOARD");

        add(mainPanel);

        // Inicia no menu principal
        showMenu();
    }

    /**
     * Exibe o menu principal
     */
    public void showMenu() {
        cardLayout.show(mainPanel, "MENU");
    }

    /**
     * Inicia uma partida manual
     */
    public void startManualGame() {
        boardPanel.initializeManualGame();
        cardLayout.show(mainPanel, "BOARD");
    }

    /**
     * Inicia uma partida simulada
     */
    public void startSimulatedGame() {
        boardPanel.initializeSimulatedGame();
        cardLayout.show(mainPanel, "BOARD");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ChessGUI().setVisible(true);
        });
    }
}
