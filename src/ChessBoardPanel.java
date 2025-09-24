import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Painel que exibe o tabuleiro de xadrez e gerencia as interações do jogo.
 * Suporta partidas manuais e simuladas.
 */
public class ChessBoardPanel extends JPanel {

    private ChessGUI parentFrame;
    private Partida partida;
    private JButton[][] boardButtons;
    private JLabel statusLabel;
    private JLabel turnLabel;
    private JButton backButton;
    private JButton simulateButton;

    private Posicao selectedPosition;
    private boolean isManualMode;
    private boolean isSimulationRunning;

    // Cores do tabuleiro
    private static final Color LIGHT_SQUARE = new Color(240, 217, 181);
    private static final Color DARK_SQUARE = new Color(181, 136, 99);
    private static final Color SELECTED_SQUARE = new Color(255, 255, 0, 150);
    private static final Color POSSIBLE_MOVE = new Color(0, 255, 0, 100);

    private Map<String, ImageIcon> pieceImages;

    public ChessBoardPanel(ChessGUI parentFrame) {
        this.parentFrame = parentFrame;
        this.selectedPosition = null;
        loadPieceImages();
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Painel superior com informações
        add(createTopPanel(), BorderLayout.NORTH);

        // Painel central com tabuleiro
        add(createBoardPanel(), BorderLayout.CENTER);

        // Painel inferior com botões
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        turnLabel = new JLabel("Vez do Jogador: ", SwingConstants.LEFT);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));

        statusLabel = new JLabel("Pronto para jogar", SwingConstants.RIGHT);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(turnLabel, BorderLayout.WEST);
        panel.add(statusLabel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createBoardPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        boardButtons = new JButton[8][8];

        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(60, 60));
                button.setFont(new Font("Arial", Font.BOLD, 24));
                button.setFocusPainted(false);
                button.setBorderPainted(true);

                // Cor alternada do tabuleiro
                button.setBackground((linha + coluna) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE);

                final int finalLinha = linha;
                final int finalColuna = coluna;

                button.addActionListener(e -> {
                    if (isManualMode && !isSimulationRunning) {
                        handleSquareClick(finalLinha, finalColuna);
                    }
                });

                boardButtons[linha][coluna] = button;
                panel.add(button);
            }
        }

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        backButton = new JButton("Voltar ao Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> parentFrame.showMenu());

        simulateButton = new JButton("Iniciar Simulação");
        simulateButton.setFont(new Font("Arial", Font.BOLD, 14));
        simulateButton.setBackground(new Color(34, 139, 34));
        simulateButton.setForeground(Color.WHITE);
        simulateButton.setFocusPainted(false);
        simulateButton.addActionListener(e -> startSimulation());

        panel.add(backButton);
        panel.add(simulateButton);

        return panel;
    }

    private void loadPieceImages() {
        pieceImages = new HashMap<>();

        // Peças Brancas
        pieceImages.put("BRANCO_REI", loadImage("/assets/Brei.png"));
        pieceImages.put("BRANCO_DAMA", loadImage("/assets/Brainha.png"));
        pieceImages.put("BRANCO_TORRE", loadImage("/assets/Btorre.png"));
        pieceImages.put("BRANCO_BISPO", loadImage("/assets/Bbispo.png"));
        pieceImages.put("BRANCO_CAVALO", loadImage("/assets/Bcavalo.png"));
        pieceImages.put("BRANCO_PEAO", loadImage("/assets/Bpeao.png"));

        // Peças Pretas
        pieceImages.put("PRETO_REI", loadImage("/assets/Prei.png"));
        pieceImages.put("PRETO_DAMA", loadImage("/assets/Pdama.png"));
        pieceImages.put("PRETO_TORRE", loadImage("/assets/Ptorre.png"));
        pieceImages.put("PRETO_BISPO", loadImage("/assets/Pbispo.png"));
        pieceImages.put("PRETO_CAVALO", loadImage("/assets/Pcavalo.png"));
        pieceImages.put("PRETO_PEAO", loadImage("/assets/Ppeao.png"));
    }

    private ImageIcon loadImage(String resourcePath) {
        try {
            java.net.URL imgURL = getClass().getResource(resourcePath);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image image = originalIcon.getImage();
                Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                System.err.println("Não foi possível encontrar o recurso: " + resourcePath);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void initializeManualGame() {
        partida = new Partida();
        isManualMode = true;
        isSimulationRunning = false;
        selectedPosition = null;
        simulateButton.setVisible(false);
        updateBoard();
        updateStatus();
    }

    public void initializeSimulatedGame() {
        partida = new Partida();
        isManualMode = false;
        isSimulationRunning = false;
        selectedPosition = null;
        simulateButton.setVisible(true);
        updateBoard();
        updateStatus();
    }

    private void updateBoard() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                JButton button = boardButtons[linha][coluna];
                Posicao pos = new Posicao(linha, coluna);
                Peca peca = partida.getTabuleiro().getPeca(pos);

                if (peca != null) {
                    button.setIcon(getPieceImage(peca));
                    button.setText("");
                } else {
                    button.setIcon(null);
                    button.setText("");
                }

                button.setBackground((linha + coluna) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE);
            }
        }

        if (selectedPosition != null) {
            JButton selectedButton = boardButtons[selectedPosition.linha()][selectedPosition.coluna()];
            selectedButton.setBackground(SELECTED_SQUARE);
            highlightPossibleMoves();
        }
    }

    private void highlightPossibleMoves() {
        if (selectedPosition == null)
            return;

        Peca peca = partida.getTabuleiro().getPeca(selectedPosition);
        if (peca == null)
            return;

        List<Movimento> movimentos = peca.calcularMovimentosPossiveis(selectedPosition, partida.getTabuleiro());

        for (Movimento movimento : movimentos) {
            Posicao destino = movimento.destino();
            JButton button = boardButtons[destino.linha()][destino.coluna()];

            Color originalColor = ((destino.linha() + destino.coluna()) % 2 == 0) ? LIGHT_SQUARE : DARK_SQUARE;
            Color mixedColor = new Color(
                    (originalColor.getRed() + POSSIBLE_MOVE.getRed()) / 2,
                    (originalColor.getGreen() + POSSIBLE_MOVE.getGreen()) / 2,
                    (originalColor.getBlue() + POSSIBLE_MOVE.getBlue()) / 2);
            button.setBackground(mixedColor);
        }
    }

    private void handleSquareClick(int linha, int coluna) {
        Posicao clickedPosition = new Posicao(linha, coluna);

        if (selectedPosition == null) {
            Peca peca = partida.getTabuleiro().getPeca(clickedPosition);
            if (peca != null && peca.getCor() == partida.getJogadorAtual().getCor()) {
                selectedPosition = clickedPosition;
                updateBoard();
                statusLabel.setText("Peça selecionada. Escolha o destino.");
            }
        } else {
            if (clickedPosition.equals(selectedPosition)) {
                selectedPosition = null;
                updateBoard();
                statusLabel.setText("Seleção cancelada.");
            } else {
                boolean sucesso = partida.realizarJogada(selectedPosition, clickedPosition);
                selectedPosition = null;
                updateBoard();

                if (sucesso) {
                    statusLabel.setText("Movimento realizado com sucesso!");
                    updateStatus();

                    if (partida.getEstado() == EstadoPartida.XEQUE_MATE) {
                        JOptionPane.showMessageDialog(this,
                                "Xeque-mate! Vencedor: " + getOpponentPlayer().getName(),
                                "Fim de Jogo",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else if (partida.getEstado() == EstadoPartida.XEQUE) {
                        JOptionPane.showMessageDialog(this,
                                "Xeque!",
                                "Atenção",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    statusLabel.setText("Movimento inválido!");
                }
            }
        }
    }

    private void startSimulation() {
        if (isSimulationRunning)
            return;

        isSimulationRunning = true;
        simulateButton.setText("Simulação em Andamento...");
        simulateButton.setEnabled(false);

        Posicao[][] movimentos = {
                { new Posicao(6, 4), new Posicao(4, 4) }, // e2-e4
                { new Posicao(1, 4), new Posicao(3, 4) }, // e7-e5
                { new Posicao(7, 1), new Posicao(5, 2) }, // Bc1-c4
                { new Posicao(0, 1), new Posicao(2, 2) }, // Nb8-c6
                { new Posicao(7, 3), new Posicao(4, 0) }, // Qd1-h5
                { new Posicao(0, 6), new Posicao(2, 5) }, // Ng8-f6
                { new Posicao(4, 0), new Posicao(1, 5) } // Qh5xf7# (xeque-mate)
        };

        Timer timer = new Timer(1500, null);
        final int[] moveIndex = { 0 };

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (moveIndex[0] < movimentos.length && partida.getEstado() != EstadoPartida.XEQUE_MATE) {
                    Posicao origem = movimentos[moveIndex[0]][0];
                    Posicao destino = movimentos[moveIndex[0]][1];

                    partida.realizarJogada(origem, destino);
                    updateBoard();
                    updateStatus();
                    statusLabel.setText("Movimento " + (moveIndex[0] + 1) + " executado");

                    if (partida.getEstado() == EstadoPartida.XEQUE_MATE) {
                        timer.stop();
                        JOptionPane.showMessageDialog(ChessBoardPanel.this,
                                "Simulação concluída! Xeque-mate alcançado!",
                                "Fim da Simulação",
                                JOptionPane.INFORMATION_MESSAGE);
                        isSimulationRunning = false;
                        simulateButton.setText("Reiniciar Simulação");
                        simulateButton.setEnabled(true);
                    }
                    moveIndex[0]++;
                } else {
                    timer.stop();
                    isSimulationRunning = false;
                    simulateButton.setText("Reiniciar Simulação");
                    simulateButton.setEnabled(true);
                }
            }
        });

        timer.start();
    }

    private void updateStatus() {
        if (partida != null) {
            turnLabel.setText("Vez do Jogador: " + partida.getJogadorAtual().getName() +
                    " (" + partida.getJogadorAtual().getCor() + ")");

            switch (partida.getEstado()) {
                case EM_ANDAMENTO -> statusLabel.setText("Jogo em andamento");
                case XEQUE -> statusLabel.setText("XEQUE!");
                case XEQUE_MATE -> statusLabel.setText("XEQUE-MATE!");
                case EMPATE -> statusLabel.setText("EMPATE!");
            }
        }
    }

    private ImageIcon getPieceImage(Peca peca) {
        String cor = peca.getCor().name();
        String tipo = peca.getClass().getSimpleName().toUpperCase();

        if (tipo.equals("RAINHA"))
            tipo = "DAMA";
        if (tipo.equals("CAVAL"))
            tipo = "CAVALO";

        return pieceImages.get(cor + "_" + tipo);
    }

    private Jogador getOpponentPlayer() {
        return partida.getJogadorAtual().getCor() == Cor.BRANCO ? partida.getJogadorPreto()
                : partida.getJogadorBranco();
    }
}
