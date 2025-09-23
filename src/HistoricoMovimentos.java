

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoricoMovimentos {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("mm:ss");
    private final List<LocalTime> historico;

    public HistoricoMovimentos() {
        this.historico = new ArrayList<>();
    }

    public void adicionarMovimento() {
        historico.add(LocalTime.now());
    }

    public List<LocalTime> getHistorico() {
        return historico;
    }

    public void mostrarHistorico() {
        for (LocalTime horario : historico) {
            System.out.println("[" + horario.format(TIME_FORMATTER) + "]");
        }
    }
}
