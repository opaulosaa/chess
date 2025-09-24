import java.util.ArrayList;
import java.util.List;

/**
 * Versão modificada da classe Partida para suportar a interface gráfica.
 * Adiciona métodos getter necessários para a GUI.
 */
public class Partida {
    private Tabuleiro tabuleiro;
    private Jogador jogadorPreto;
    private Jogador jogadorBranco;
    private Jogador jogadorAtual;
    private EstadoPartida estado;
    private List<Peca> pecasCapturadas;
    private HistoricoMovimentos historico;

    public Partida() {
        this.tabuleiro = new Tabuleiro();
        this.jogadorBranco = new Jogador("Branco", Cor.BRANCO);
        this.jogadorPreto = new Jogador("Preto", Cor.PRETO);
        this.pecasCapturadas = new ArrayList<>();
        this.historico = new HistoricoMovimentos();

        this.tabuleiro.iniciarPecas(jogadorBranco, jogadorPreto);
        this.jogadorAtual = jogadorBranco;
        this.estado = EstadoPartida.EM_ANDAMENTO;
    }

    public boolean realizarJogada(Posicao origem, Posicao destino) {
        // Verifica se o jogo está em andamento
        if (this.estado != EstadoPartida.EM_ANDAMENTO && this.estado != EstadoPartida.XEQUE) {
            System.out.println("Erro: A partida já terminou");
            return false;
        }
        // Validação do movimento
        Movimento movimento = validarMovimento(origem, destino);
        if (movimento == null) {
            System.out.println("Movimento inválido!");
            return false;
        }
        // Registra apenas o horário do movimento
        historico.adicionarMovimento();
        executarMovimento(movimento);

        // Verifica xeque e xeque-mate após o movimento
        Cor corAdversaria = (jogadorAtual == jogadorBranco) ? Cor.PRETO : Cor.BRANCO;
        if (estaEmXeque(corAdversaria)) {
            if (estaEmXequeMate(corAdversaria)) {
                this.estado = EstadoPartida.XEQUE_MATE;
                System.out.println("Xeque-mate! Vencedor: " + jogadorAtual.getName());
            } else {
                this.estado = EstadoPartida.XEQUE;
                System.out.println("Xeque!");
            }
        } else {
            this.estado = EstadoPartida.EM_ANDAMENTO;
        }

        trocarTurno();

        return true;
    }

    /**
     * Verifica se o rei da cor informada está em xeque.
     */
    private boolean estaEmXeque(Cor corRei) {
        Posicao posicaoRei = encontrarRei(corRei);
        if (posicaoRei == null)
            return false;
        // Para cada peça do adversário, verifica se pode capturar o rei
        Cor corAdversaria = (corRei == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
        for (int linha = 0; linha < Tabuleiro.LINHAS; linha++) {
            for (int coluna = 0; coluna < Tabuleiro.COLUNAS; coluna++) {
                Posicao pos = new Posicao(linha, coluna);
                Peca peca = tabuleiro.getPeca(pos);
                if (peca != null && peca.getCor() == corAdversaria) {
                    var movimentos = peca.calcularMovimentosPossiveis(pos, tabuleiro);
                    for (Movimento mov : movimentos) {
                        if (mov.destino().equals(posicaoRei)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifica se o rei da cor informada está em xeque-mate.
     */
    private boolean estaEmXequeMate(Cor corRei) {
        if (!estaEmXeque(corRei))
            return false;
        // Para cada peça do jogador, verifica se existe algum movimento que tira o rei
        // do xeque
        for (int linha = 0; linha < Tabuleiro.LINHAS; linha++) {
            for (int coluna = 0; coluna < Tabuleiro.COLUNAS; coluna++) {
                Posicao origem = new Posicao(linha, coluna);
                Peca peca = tabuleiro.getPeca(origem);
                if (peca != null && peca.getCor() == corRei) {
                    var movimentos = peca.calcularMovimentosPossiveis(origem, tabuleiro);
                    for (Movimento mov : movimentos) {
                        // Simula o movimento
                        Peca capturada = tabuleiro.getPeca(mov.destino());
                        tabuleiro.getCasa(mov.origem()).setPeca(null);
                        tabuleiro.getCasa(mov.destino()).setPeca(peca);
                        boolean reiAindaEmXeque = estaEmXeque(corRei);
                        // Desfaz o movimento
                        tabuleiro.getCasa(mov.origem()).setPeca(peca);
                        tabuleiro.getCasa(mov.destino()).setPeca(capturada);
                        if (!reiAindaEmXeque) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Encontra a posição do rei da cor informada.
     */
    private Posicao encontrarRei(Cor cor) {
        for (int linha = 0; linha < Tabuleiro.LINHAS; linha++) {
            for (int coluna = 0; coluna < Tabuleiro.COLUNAS; coluna++) {
                Posicao pos = new Posicao(linha, coluna);
                Peca peca = tabuleiro.getPeca(pos);
                if (peca instanceof Rei && peca.getCor() == cor) {
                    return pos;
                }
            }
        }
        return null;
    }

    /**
     * Valida se o movimento é permitido para a peça e turno.
     * Retorna um objeto Movimento se válido, ou null se inválido.
     */
    private Movimento validarMovimento(Posicao origem, Posicao destino) {
        Peca pecaOrigem = tabuleiro.getPeca(origem);
        if (pecaOrigem == null) {
            System.out.println("Não há peça na posição de origem.");
            return null;
        }
        if (pecaOrigem.getCor() != jogadorAtual.getCor()) {
            System.out.println("A peça selecionada não pertence ao jogador atual.");
            return null;
        }
        // Calcula movimentos possíveis para a peça
        var movimentosPossiveis = pecaOrigem.calcularMovimentosPossiveis(origem, tabuleiro);
        boolean movimentoValido = movimentosPossiveis.stream()
                .anyMatch(m -> m.destino().equals(destino));
        if (!movimentoValido) {
            System.out.println("Movimento não permitido para esta peça.");
            return null;
        }
        Peca pecaDestino = tabuleiro.getPeca(destino);
        // Não pode capturar peça da mesma cor
        if (pecaDestino != null && pecaDestino.getCor() == jogadorAtual.getCor()) {
            System.out.println("Não é permitido capturar peça da mesma cor.");
            return null;
        }
        return new Movimento(origem, destino, pecaOrigem, pecaDestino, jogadorAtual);
    }

    /**
     * Executa o movimento no tabuleiro, atualizando as casas e capturando peças se
     * necessário.
     */
    private void executarMovimento(Movimento movimento) {
        // Remove peça da origem
        tabuleiro.getCasa(movimento.origem()).setPeca(null);
        // Se houver captura, adiciona à lista de capturadas
        if (movimento.pecaCapturada() != null) {
            pecasCapturadas.add(movimento.pecaCapturada());
            // Adiciona pontos ao jogador
            movimento.jogador().adicionarPontos(movimento.pecaCapturada().getPontuacao());
        }
        // Coloca peça no destino
        tabuleiro.getCasa(movimento.destino()).setPeca(movimento.pecaMovida());
    }

    /**
     * Alterna o turno entre os jogadores.
     */
    private void trocarTurno() {
        this.jogadorAtual = (this.jogadorAtual == jogadorBranco) ? jogadorPreto : jogadorBranco;
    }

    // Métodos getter adicionados para a interface gráfica
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public Jogador getJogadorBranco() {
        return jogadorBranco;
    }

    public Jogador getJogadorPreto() {
        return jogadorPreto;
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public List<Peca> getPecasCapturadas() {
        return new ArrayList<>(pecasCapturadas);
    }

    public HistoricoMovimentos getHistorico() {
        return historico;
    }
}
