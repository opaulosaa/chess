import java.util.List;

public abstract class Peca {
    protected final Cor cor;
    protected final int pontuacao;

    public Peca(Cor cor, int pontuacao) {
        this.cor = cor;
        this.pontuacao = pontuacao;
    }

    public Cor getCor() {
        return cor;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public abstract List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro);
}
