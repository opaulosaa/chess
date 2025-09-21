public record Movimento(
        Posicao origem,
        Posicao destino,
        Peca pecaMovida,
        Peca pecaCapturada,
        Jogador jogador
) {
}
