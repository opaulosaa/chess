import java.util.ArrayList;
import java.util.List;

public class Bispo extends Peca{
    public Bispo(Cor cor, int pontuacao) {
        super(cor, 3);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        List<Movimento> movimentosPossiveis = new ArrayList<>();
        Posicao posicaoAtual = posicao;
        int linhaAtual = posicao.linha();
        int colunaAtual = posicao.coluna();

        // 4 direções diagonais: (dLinha, dColuna)
        int[][] direcoes = {
                {-1, -1},
                {-1,  1},
                { 1, -1},
                { 1,  1}
        };

        for (int[] d : direcoes) {
            int dLinha = d[0];
            int dColuna = d[1];
            int passo = 1;
            while (true) {
                Posicao destino = new Posicao(linhaAtual + passo * dLinha, colunaAtual + passo * dColuna);
                if (!isPosicaoValida(destino)) break;

                if (tabuleiro.getCasa(destino).isVazia()) {
                    movimentosPossiveis.add(new Movimento(posicaoAtual, destino, this, null, null));
                } else {
                    Peca pecaAlvo = tabuleiro.getPeca(destino);
                    if (pecaAlvo.getCor() != this.getCor()) {
                        // captura
                        movimentosPossiveis.add(new Movimento(posicaoAtual, destino, this, pecaAlvo, null));
                    }
                    // encontrou peça — independente da cor, interrompe varredura nessa direção
                    break;
                }
                passo++;
            }
        }

        return movimentosPossiveis;
    }

    private boolean isPosicaoValida(Posicao posicao) {
        return posicao.linha() >= 0 && posicao.linha() < Tabuleiro.LINHAS &&
                posicao.coluna() >= 0 && posicao.coluna() < Tabuleiro.COLUNAS;
    }
}
