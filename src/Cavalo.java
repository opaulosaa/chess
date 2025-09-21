import java.util.ArrayList;
import java.util.List;

public class Cavalo extends Peca{
    public Cavalo(Cor cor, int pontuacao) {
        super(cor, 3);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        List<Movimento> movimentosPossiveis = new ArrayList<>();
        Posicao posicaoAtual = posicao;
        int linhaAtual = posicao.linha();
        int colunaAtual = posicao.coluna();

        // todas as 8 deltas do cavalo
        int[][] deltas = {
                {-2, -1}, {-2,  1},
                {-1, -2}, {-1,  2},
                { 1, -2}, { 1,  2},
                { 2, -1}, { 2,  1}
        };

        for (int[] d : deltas) {
            Posicao destino = new Posicao(linhaAtual + d[0], colunaAtual + d[1]);
            if (!isPosicaoValida(destino)) continue;

            if (tabuleiro.getCasa(destino).isVazia()) {
                movimentosPossiveis.add(new Movimento(posicaoAtual, destino, this, null, null));
            } else {
                Peca pecaAlvo = tabuleiro.getPeca(destino);
                if (pecaAlvo.getCor() != this.getCor()) {
                    movimentosPossiveis.add(new Movimento(posicaoAtual, destino, this, pecaAlvo, null));
                }
                // se for mesma cor, não adiciona
            }
        }

        return movimentosPossiveis;
    }

    private boolean isPosicaoValida(Posicao posicao) {
        return posicao.linha() >= 0 && posicao.linha() < Tabuleiro.LINHAS &&
                posicao.coluna() >= 0 && posicao.coluna() < Tabuleiro.COLUNAS;
    }
}