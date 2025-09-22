import java.util.ArrayList;
import java.util.List;

public class Peao extends Peca {
    public Peao(Cor cor, int pontuacao) {
        super(cor, 1);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        List<Movimento> movimentosPossiveis = new ArrayList<>();
        int linhaAtual = posicao.linha();
        int colunaAtual = posicao.coluna();
        // Brancas movem pra cima(índice da linha diminui)
        // Pretas movem pra baixo(índice da linha aumenta)
        int direcao = (this.getCor() == Cor.BRANCO) ? -1 : 1;

        // 1. Movimento simples de uma casa para frente
        Posicao umaCasaFrente = new Posicao(linhaAtual + direcao, colunaAtual);
        if (isPosicaoValida(umaCasaFrente) && tabuleiro.getCasa(umaCasaFrente).isVazia()) {
            movimentosPossiveis.add(new Movimento(posicaoAtual, umaCasaFrente, this, null, null)); // Jogador será
                                                                                                   // preenchido na
                                                                                                   // classe Partida

            // 2. Primeiro movimento (duas casas para frente)
            // Só pode ser feito se o movimento de uma casa também for possível.
            boolean estaNaCasaInicial = (this.getCor() == Cor.BRANCO && linhaAtual == 6) ||
                    (this.getCor() == Cor.PRETO && linhaAtual == 1);

            Posicao duasCasasFrente = new Posicao(linhaAtual + (2 * direcao), colunaAtual);
            if (estaNaCasaInicial && isPosicaoValida(duasCasasFrente) && tabuleiro.getCasa(duasCasasFrente).isVazia()) {
                movimentosPossiveis.add(new Movimento(posicaoAtual, duasCasasFrente, this, null, null));
            }
        }

        // 3. Lógica de Captura na Diagonal

        // 3.1 Captura para a "esquerda" (do ponto de vista do jogador)
        Posicao capturaEsquerda = new Posicao(linhaAtual + direcao, colunaAtual - 1);
        if (isPosicaoValida(capturaEsquerda) && tabuleiro.getCasa(capturaEsquerda).isOcupada()) {
            Peca pecaAlvo = tabuleiro.getPeca(capturaEsquerda);
            if (pecaAlvo.getCor() != this.getCor()) { // Verifica se a peça é do adversário
                movimentosPossiveis.add(new Movimento(posicaoAtual, capturaEsquerda, this, pecaAlvo, null));
            }
        }

        // 3.2 Captura para a "direita" (do ponto de vista do jogador)
        Posicao capturaDireita = new Posicao(linhaAtual + direcao, colunaAtual + 1);
        if (isPosicaoValida(capturaDireita) && tabuleiro.getCasa(capturaDireita).isOcupada()) {
            Peca pecaAlvo = tabuleiro.getPeca(capturaDireita);
            if (pecaAlvo.getCor() != this.getCor()) { // Verifica se a peça é do adversário
                movimentosPossiveis.add(new Movimento(posicaoAtual, capturaDireita, this, pecaAlvo, null));
            }
        }

        return movimentosPossiveis;
    }

    private boolean isPosicaoValida(Posicao posicao) {
        return posicao.linha() >= 0 && posicao.linha() < Tabuleiro.LINHAS &&
                posicao.coluna() >= 0 && posicao.coluna() < Tabuleiro.COLUNAS;
    }
}
