import java.util.List;

public class Peao extends Peca{
    public Peao(Cor cor, int pontuacao) {
        super(cor, 1);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        return List.of();
    }
}
