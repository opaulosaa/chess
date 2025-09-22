import java.util.ArrayList;
import java.util.List;

public class Dama extends Peca {

    public Dama(Cor cor, int pontuacao) {
        super(cor, 9); // dama vale 9 pontos
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        List<Movimento> movimentosPossiveis = new ArrayList<>();
        Posicao posicaoAtual = posicao;

        // direções da dama: combina torre (↑↓←→) e bispo (↖↗↙↘)
        int[][] direcoes = {
            {-1, 0}, {1, 0},   // vertical
            {0, -1}, {0, 1},   // horizontal
            {-1, -1}, {-1, 1}, // diagonais
            {1, -1}, {1, 1}
        };

        for (int[] dir : direcoes) {
            int linha = posicao.linha();
            int coluna = posicao.coluna();

            while (true) {
                linha += dir[0];
                coluna += dir[1];
                Posicao destino = new Posicao(linha, coluna);

                if (!isPosicaoValida(destino)) break; // fora do tabuleiro

                if (tabuleiro.getCasa(destino).isVazia()) {
                    // casa livre → pode andar
                    movimentosPossiveis.add(new Movimento(posicaoAtual, destino, this, null, null));
                } else {
                    // encontrou uma peça
                    Peca pecaAlvo = tabuleiro.getPeca(destino);
                    if (pecaAlvo.getCor() != this.getCor()) {
                        // peça adversária → pode capturar
                        movimentosPossiveis.add(new Movimento(posicaoAtual, destino, this, pecaAlvo, null));
                    }
                    // para em qualquer peça (não pode "pular")
                    break;
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
