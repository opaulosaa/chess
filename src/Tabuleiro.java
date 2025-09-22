public class Tabuleiro {
    public static final int LINHAS = 8;
    public static final int COLUNAS = 8;

    private final Casa[][] casas;

    public Tabuleiro() {
        this.casas = new Casa[LINHAS][COLUNAS];
        for (int linha = 0; linha < LINHAS; linha++){
            for (int coluna = 0; coluna < COLUNAS; coluna++){
                casas[linha][coluna] = new Casa(new Posicao(linha, coluna));
            }
        }
    }

    /**
     * Imprime o tabuleiro no console, mostrando as peças e casas.
     */
    public void imprimirTabuleiro() {
        for (int linha = 0; linha < LINHAS; linha++) {
            System.out.print((LINHAS - linha) + " "); // Numeração das linhas
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                Peca peca = casas[linha][coluna].getPeca();
                if (peca == null) {
                    System.out.print(". ");
                } else {
                    String simbolo = obterSimboloPeca(peca);
                    System.out.print(simbolo + " ");
                }
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h"); // Letras das colunas
    }

    // Retorna um símbolo para cada tipo de peça, maiúsculo para branco, minúsculo para preto
    private String obterSimboloPeca(Peca peca) {
        String s;
        if (peca instanceof Rei) s = "R";
        else if (peca instanceof Dama) s = "D";
        else if (peca instanceof Torre) s = "T";
        else if (peca instanceof Bispo) s = "B";
        else if (peca instanceof Cavalo) s = "C";
        else if (peca instanceof Peao) s = "P";
        else s = "?";
        if (peca.getCor() == Cor.PRETO) s = s.toLowerCase();
        return s;
    }

    /**
     * Posiciona todas as peças no tabuleiro para o início da partida.
     */
    public void iniciarPecas(Jogador jogadorBranco, Jogador jogadorPreto){
        // Pretas (linha 0 e 1)
        colocarPeca(new Torre(jogadorPreto.getCor(), 5), new Posicao(0, 0));
        colocarPeca(new Cavalo(jogadorPreto.getCor(), 3), new Posicao(0, 1));
        colocarPeca(new Bispo(jogadorPreto.getCor(), 3), new Posicao(0, 2));
        colocarPeca(new Dama(jogadorPreto.getCor(), 9), new Posicao(0, 3));
        colocarPeca(new Rei(jogadorPreto.getCor(), 1000), new Posicao(0, 4));
        colocarPeca(new Bispo(jogadorPreto.getCor(), 3), new Posicao(0, 5));
        colocarPeca(new Cavalo(jogadorPreto.getCor(), 3), new Posicao(0, 6));
        colocarPeca(new Torre(jogadorPreto.getCor(), 5), new Posicao(0, 7));
        for (int coluna = 0; coluna < COLUNAS; coluna++) {
            colocarPeca(new Peao(jogadorPreto.getCor(), 1), new Posicao(1, coluna));
        }

        // Brancas (linha 7 e 6)
        colocarPeca(new Torre(jogadorBranco.getCor(), 5), new Posicao(7, 0));
        colocarPeca(new Cavalo(jogadorBranco.getCor(), 3), new Posicao(7, 1));
        colocarPeca(new Bispo(jogadorBranco.getCor(), 3), new Posicao(7, 2));
        colocarPeca(new Dama(jogadorBranco.getCor(), 9), new Posicao(7, 3));
        colocarPeca(new Rei(jogadorBranco.getCor(), 1000), new Posicao(7, 4));
        colocarPeca(new Bispo(jogadorBranco.getCor(), 3), new Posicao(7, 5));
        colocarPeca(new Cavalo(jogadorBranco.getCor(), 3), new Posicao(7, 6));
        colocarPeca(new Torre(jogadorBranco.getCor(), 5), new Posicao(7, 7));
        for (int coluna = 0; coluna < COLUNAS; coluna++) {
            colocarPeca(new Peao(jogadorBranco.getCor(), 1), new Posicao(6, coluna));
        }
    }

    /**
     * Coloca uma peça em uma posição específica do tabuleiro.
     * Atualiza a casa correspondente com a peça informada.
     */
    public void colocarPeca(Peca peca, Posicao posicao){
        if (posicao.linha() < 0 || posicao.linha() >= LINHAS ||
            posicao.coluna() < 0 || posicao.coluna() >= COLUNAS) {
            throw new IllegalArgumentException("Posição fora do tabuleiro");
        }
        casas[posicao.linha()][posicao.coluna()].setPeca(peca);
    }

    /**
     * Retorna a casa na posição informada.
     */
    public Casa getCasa(Posicao posicao) {
        if (posicao.linha() < 0 || posicao.linha() >= LINHAS ||
            posicao.coluna() < 0 || posicao.coluna() >= COLUNAS) {
            throw new IllegalArgumentException("Posição fora do tabuleiro");
        }
        return casas[posicao.linha()][posicao.coluna()];
    }

    /**
     * Retorna a peça na posição informada, ou null se não houver peça.
     */
    public Peca getPeca(Posicao posicao) {
        return getCasa(posicao).getPeca();
    }
}
