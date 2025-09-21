import java.util.List;

public class Cavalo extends Peca{
    public Cavalo(Cor cor, int pontuacao) {
        super(cor, 3);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        return List.of();
    }
}
