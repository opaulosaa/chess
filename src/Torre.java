import java.util.ArrayList;
import java.util.List;

public class Torre extends Peca {

    public Torre(Cor cor, int pontuacao) {
        super(cor, 5); // valor padrão da torre
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        List<Movimento> movimentosPossiveis = new ArrayList<>();
        int linha = posicao.linha();
        int coluna = posicao.coluna();

        // Explora quatro direções: cima, baixo, esquerda, direita
        explorarDirecao(-1, 0, linha, coluna, posicao, tabuleiro, movimentosPossiveis); // cima
        explorarDirecao(1, 0, linha, coluna, posicao, tabuleiro, movimentosPossiveis);  // baixo
        explorarDirecao(0, -1, linha, coluna, posicao, tabuleiro, movimentosPossiveis); // esquerda
        explorarDirecao(0, 1, linha, coluna, posicao, tabuleiro, movimentosPossiveis);  // direita

        return movimentosPossiveis;
    }

    /**
     * Adiciona movimentos na direção (dLinha, dColuna) até bater borda ou peça.
     * Se encontrar peça adversária, adiciona movimento de captura e pára.
     */
    private void explorarDirecao(int dLinha, int dColuna, int linhaInicial, int colunaInicial,
                                 Posicao posicao, Tabuleiro tabuleiro, List<Movimento> movimentos) {
        int r = linhaInicial + dLinha;
        int c = colunaInicial + dColuna;

        while (r >= 0 && r < Tabuleiro.LINHAS && c >= 0 && c < Tabuleiro.COLUNAS) {
            Posicao destino = new Posicao(r, c);

            if (tabuleiro.getCasa(destino).isVazia()) {
                movimentos.add(new Movimento(posicao, destino, this, null, null));
            } else {
                Peca pecaAlvo = tabuleiro.getPeca(destino);
                if (pecaAlvo.getCor() != this.getCor()) {
                    movimentos.add(new Movimento(posicao, destino, this, pecaAlvo, null));
                }
                break; // encontrou peça (inimiga ou aliada), não pode ir além
            }

            r += dLinha;
            c += dColuna;
        }
    }
}
