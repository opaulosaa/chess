public class Casa {
    private final Posicao posicao;
    private Peca peca;

    public Casa(Posicao posicao) {
        this.posicao = posicao;
        this.peca = null;
    }

    public Peca getPeca(){return this.peca;}
    public void setPeca(Peca peca){this.peca = peca;}
    public Peca removerPeca(){
        Peca pecaRemovida = this.peca;
        this.peca = null;
        return pecaRemovida;
    }

    public boolean isVazia(){return this.peca == null;}
    public boolean isOcupada(){return this.peca != null;}
    public Posicao getPosicao(){return this.posicao;}
}
