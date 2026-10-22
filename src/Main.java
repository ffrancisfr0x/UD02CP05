import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        Ascensor ascensor = new Ascensor(0);

        Usuario usuario1 = new Usuario("Usuario1", 1, 5, ascensor);
        Usuario usuario2 = new Usuario("Usuario2", 2, 5, ascensor);
        Usuario usuario3 = new Usuario("Usuario3", 3, 5, ascensor);
        Usuario usuario4 = new Usuario("Usuario4", 4, 5, ascensor);

        new Thread(usuario1).start();
        new Thread(usuario2).start();
        new Thread(usuario3).start();
        new Thread(usuario4).start();
        new Thread(ascensor).start();
    }
}
