import java.util.LinkedList;

public class Ascensor implements Runnable {
    private LinkedList<Usuario> usuarios = new LinkedList<>();
    private int pisoActual;
    private Usuario usuarioActual;
    private boolean usuarioDentro;

    public Ascensor(int pisoActual) {
        this.pisoActual = pisoActual;
        this.usuarioActual = null;
        this.usuarioDentro = false;
    }

    public synchronized void llamar(Usuario usuario) {
        usuarios.add(usuario);
        notifyAll();
    }

    public synchronized int getPisoActual() {
        return pisoActual;
    }

    public synchronized void moverUnPiso(int direccion) {
        pisoActual += direccion;
        System.out.println("Ascensor en piso " + pisoActual);
        notifyAll();
    }

    public void irAPiso(int objetivo) throws InterruptedException {
        while (getPisoActual() != objetivo) {
            int direccion = (objetivo > getPisoActual()) ? 1 : -1;
            moverUnPiso(direccion);
            Thread.sleep(500);
        }
    }

    public synchronized void esperarYSubir(Usuario u) throws InterruptedException {
        while (usuarioActual != u || pisoActual != u.getPisoOrigen()) {
            wait();
        }
        System.out.println(u.getNombre() + " sube en piso " + pisoActual);
        usuarioDentro = true;
        notifyAll();
    }

    public synchronized void esperarYBajar(Usuario u) throws InterruptedException {
        while (pisoActual != u.getPisoDestino()) {
            wait();
        }
        System.out.println(u.getNombre() + " baja en piso " + pisoActual);
        usuarioDentro = false;
        notifyAll();
    }

    public synchronized Usuario siguienteUsuario() throws InterruptedException {
        while (usuarios.isEmpty()) {
            wait();
        }
        usuarioActual = usuarios.removeFirst();
        notifyAll();
        return usuarioActual;
    }

    public synchronized void esperarQueSuba() throws InterruptedException {
        while (!usuarioDentro) {
            wait();
        }
    }

    public synchronized void esperarQueBaje() throws InterruptedException {
        while (usuarioDentro) {
            wait();
        }
        usuarioActual = null;
        notifyAll();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 4; i++) {
                Usuario u = siguienteUsuario();
                irAPiso(u.getPisoOrigen());
                esperarQueSuba();
                irAPiso(u.getPisoDestino());
                esperarQueBaje();
            }
            irAPiso(-1);
            System.out.println("Ascensor llegó a piso -1. Fin del programa.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}