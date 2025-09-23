# Chess

Este é um projeto de xadrez em Java, implementado para fins de estudo e prática de programação orientada a objetos observando os princípios GRASP.

## Funcionalidades
- Lógica completa do jogo de xadrez
- Controle de turnos e regras oficiais
- Detecção de xeque e xeque-mate
- Registro do histórico dos movimentos (apenas horário MM:SS)
- Suporte a captura de peças

## Estrutura do Projeto
```
Chess/
├── src/
│   ├── Bispo.java
│   ├── Casa.java
│   ├── Cavalo.java
│   ├── Cor.java
│   ├── Dama.java
│   ├── EstadoPartida.java
│   ├── HistoricoMovimentos.java
│   ├── Jogador.java
│   ├── Main.java
│   ├── Movimento.java
│   ├── Partida.java
│   ├── Peao.java
│   ├── Peca.java
│   ├── Posicao.java
│   ├── Rei.java
│   ├── Tabuleiro.java
│   └── Torre.java
└── Chess.iml
```

## Como executar
1. Compile todos os arquivos Java dentro da pasta `src`:
   ```sh
   javac src/*.java
   ```
2. Execute o programa principal:
   ```sh
   java -cp src Main
   ```

## Observações
- O histórico de movimentos mostra apenas o horário (minuto:segundo) de cada jogada.
- O projeto não possui interface gráfica, apenas console.

## Licença
Este projeto é livre para fins de estudo.
