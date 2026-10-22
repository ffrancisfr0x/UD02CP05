import java.util.LinkedList;

public class Usuario implements Runnable {
    private String nombre;
    private int pisoOrigen;
    private int pisoDestino;
    private Ascensor ascensor;

    public Usuario(String nombre, int pisoOrigen, int pisoDestino,  Ascensor ascensor) {
        this.nombre = nombre;
        this.pisoOrigen = pisoOrigen;
        this.pisoDestino = pisoDestino;
        this.ascensor = ascensor;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPisoOrigen() {
        return pisoOrigen;
    }

    public int getPisoDestino() {
        return pisoDestino;
    }

    public Ascensor getAscensor() {
        return ascensor;
    }

    @Override
    public void run() {
        try {
            System.out.println(nombre + " llama al ascensor desde el piso " + pisoOrigen);
            ascensor.llamar(this);
            ascensor.esperarYSubir(this);
            ascensor.esperarYBajar(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
