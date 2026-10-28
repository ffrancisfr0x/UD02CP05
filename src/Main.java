import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Ascensor ascensor = new Ascensor(0);
        Thread hiloAscensor = new Thread(ascensor);

        List<Thread> hilosUsuarios = new ArrayList<>();
        hilosUsuarios.add(new Thread(new Usuario("Usuario1", 1, 5, ascensor)));
        hilosUsuarios.add(new Thread(new Usuario("Usuario2", 2, 5, ascensor)));
        hilosUsuarios.add(new Thread(new Usuario("Usuario3", 3, 5, ascensor)));
        hilosUsuarios.add(new Thread(new Usuario("Usuario4", 4, 5, ascensor)));

        hiloAscensor.start();
        for (Thread t : hilosUsuarios) {
            t.start();
        }

        // CORRECCIÓN EJERCICIO:
        // 1. Esperar a que todos los usuarios hayan terminado su viaje
        for (Thread t : hilosUsuarios) {
            t.join();
        }

        // 2. Avisar al ascensor de que no vendrá nadie más
        ascensor.cerrar();

        // 3. Esperar a que el ascensor llegue al -1 y termine
        hiloAscensor.join();
    }
}