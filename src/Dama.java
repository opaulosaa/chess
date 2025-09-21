import java.util.List;

public class Dama extends Peca{
    public Dama(Cor cor, int pontuacao) {
        super(cor, 9);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        return List.of();
    }
}
