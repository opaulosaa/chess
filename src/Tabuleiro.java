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
}
