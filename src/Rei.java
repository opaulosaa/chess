import java.util.List;

public class Rei extends Peca{
    public Rei(Cor cor, int pontuacao) {
        super(cor, pontuacao);
    }

    @Override
    public List<Movimento> calcularMovimentosPossiveis(Posicao posicao, Tabuleiro tabuleiro) {
        return List.of();
    }
}
