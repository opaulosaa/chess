import java.util.List;

public class Bispo extends Peca{
    public Bispo(Cor cor, int pontuacao) {
        super(cor, 3);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        return List.of();
    }
}
