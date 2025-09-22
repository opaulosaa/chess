import java.util.ArrayList;
import java.util.List;

public class Rei extends Peca {
    public Rei(Cor cor, int pontuacao) {
        super(cor, pontuacao);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        List<Movimento> movimentosPossiveis = new ArrayList<>();
        int linhaAtual = posicao.linha();
        int colunaAtual = posicao.coluna();
        // Brancas movem pra cima(índice da linha diminui)
        // Pretas movem pra baixo(índice da linha aumenta)

        // 1. Movimento simples de uma casa para frente
        // Array para representar todas as 8 direções possíveis (linhas e colunas)
        // (cima, baixo, esquerda, direita, e as 4 diagonais)
        int[] deslocamentosLinha = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] deslocamentosColuna = { -1, 0, 1, -1, 1, -1, 0, 1 };

        // Itera sobre todas as 8 direções
        for (int i = 0; i < 8; i++) {
            int novaLinha = linhaAtual + deslocamentosLinha[i];
            int novaColuna = colunaAtual + deslocamentosColuna[i];
            Posicao novaPosicao = new Posicao(novaLinha, novaColuna);
            if (!isPosicaoValida(novaPosicao)) {
                continue;
            }
            Casa casaDestino = tabuleiro.getCasa(novaPosicao);
            if (casaDestino.isVazia()) {
                movimentosPossiveis.add(new Movimento(posicao, novaPosicao, this, null, null));
            } else {
                Peca pecaAlvo = tabuleiro.getPeca(novaPosicao);
                if (pecaAlvo.getCor() != this.getCor()) {
                    // captura
                    movimentosPossiveis.add(new Movimento(posicao, novaPosicao, this, pecaAlvo, null));
                }
            }
        }

        return movimentosPossiveis;
    }

    private boolean isPosicaoValida(Posicao posicao) {
        return posicao.linha() >= 0 && posicao.linha() < Tabuleiro.LINHAS &&
                posicao.coluna() >= 0 && posicao.coluna() < Tabuleiro.COLUNAS;
    }
}
