import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Partida partida = new Partida();

            Scanner scanner = new Scanner(System.in);
            int jogadaNum = 1;
            while (partida.getEstado() != EstadoPartida.XEQUE_MATE) {
                System.out.println("\nTabuleiro atual:");
                partida.getTabuleiro().imprimirTabuleiro();
                System.out.println("\nVez de: " + partida.getJogadorAtual().getName() + " (" + partida.getJogadorAtual().getCor() + ")");
                System.out.print("Digite a linha de origem (0-7): ");
                int ol = scanner.nextInt();
                System.out.print("Digite a coluna de origem (0-7): ");
                int oc = scanner.nextInt();
                System.out.print("Digite a linha de destino (0-7): ");
                int dl = scanner.nextInt();
                System.out.print("Digite a coluna de destino (0-7): ");
                int dc = scanner.nextInt();
                boolean sucesso = partida.realizarJogada(new Posicao(ol, oc), new Posicao(dl, dc));
                System.out.printf("Jogada %d: (%d,%d) -> (%d,%d) | Sucesso: %s\n", jogadaNum, ol, oc, dl, dc, sucesso);
                jogadaNum++;
                if (partida.getEstado() == EstadoPartida.XEQUE_MATE) {
                    System.out.println("Partida encerrada por xeque-mate.");
                    partida.getTabuleiro().imprimirTabuleiro();
                    break;
                }
            }
            scanner.close();
    }
}
