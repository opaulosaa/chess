import java.util.List;

public class Torre extends Peca{
    public Torre(Cor cor, int pontuacao) {
        super(cor, 5);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        return List.of();
    }
}
