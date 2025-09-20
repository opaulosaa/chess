public class Jogador {
    private final String name; //nome do jogador
    private final Cor cor; //cor das peças que o jogador controla
    private int pontuacao; //pontuação total acumulada

    public Jogador(String name, Cor cor) {
        this.name = name;
        this.cor = cor;
        this.pontuacao = 0; //pontuação inicial sempre é zero
    }

    public void adicionarPontos(int pontos){
        if (pontos > 0){
            this.pontuacao += pontos;
        }
    }

    public String getName() {
        return name;
    }

    public Cor getCor() {
        return cor;
    }

    public int getPontuacao() {
        return pontuacao;
    }
}
