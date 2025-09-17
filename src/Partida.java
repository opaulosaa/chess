import java.util.ArrayList;
import java.util.List;

public class Partida {
    private Tabuleiro tabuleiro;
    private Jogador jogadorPreto;
    private Jogador jogadorBranco;
    private Jogador jogadorAtual;
    private EstadoPartida estado;
    private List<Peca> pecasCapturadas;

    public Partida() {
        this.tabuleiro = new Tabuleiro();
        this.jogadorBranco = new Jogador(Cor.BRANCO);
        this.jogadorPreto = new Jogador(Cor.PRETO);
        this.pecasCapturadas = new ArrayList<>();

        this.tabuleiro.iniciarPecas(jogadorBranco, jogadorPreto);
        this.jogadorAtual = jogadorBranco;
        this.estado = EstadoPartida.EM_ANDAMENTO;
    }

    public boolean realizarJogada(Posicao origem, Posicao destino){
        //jogo em andamento?
        if (this.estado != EstadoPartida.EM_ANDAMENTO && this.estado != EstadoPartida.XEQUE){
            System.out.println("Erro: A partida já terminou");
            return false;
        }
        //validação do movimento
        Movimento movimento = validarMovimento(origem, destino){
            if (movimento == null){
                return false;
            }
            executarMovimento(movimento);

            //TODO: verificar se o movimento resultou em xeque ou xeque mate

            trocarTurno();

            return true;
        }

    }

    public Tabuleiro getTabuleiro(){
        return this.tabuleiro;
    }

    public Jogador getJogadorAtual(){
        return this.jogadorAtual;
    }

    public EstadoPartida getEstado(){
        return this.estado;
    }
}
