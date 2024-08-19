import java.util.Scanner;

public class JuegoTablero {
    private Tablero tablero1;
    private Tablero tablero2;
    private Jugador jugador1;
    private Jugador jugador2;
    private Scanner scanner;
    private boolean juegoTerminado;
    private boolean permitirPasoBorde;
    private int dificultad;

    public JuegoTablero() {
        scanner = new Scanner(System.in);
        int tamanoTablero = obtenerTamanoTablero();
        seleccionarDificultad();

        tablero1 = new Tablero(tamanoTablero);
        tablero2 = new Tablero(tamanoTablero);
        jugador1 = new Jugador('1');
        jugador2 = new Jugador('2');
        juegoTerminado = false;

        configurarJuego();
        inicializarJuego();
    }

    private int obtenerTamanoTablero() {
        int tamano = 0;
        while (tamano < 4 || tamano > 10) {
            System.out.println("Introduce el tamaño del tablero (entre 4 (4x4) y 10 (10x10):");
            tamano = scanner.nextInt();
            if (tamano < 4 || tamano > 10) {
                System.out.println("Tamaño no válido. Por favor, introduzca un valor entre 4 y 10.");
            }
        }
        return tamano;
    }

    private void seleccionarDificultad() {
        System.out.println("Selecciona la dificultad del juego:");
        System.out.println("1. Fácil (4 enemigos)");
        System.out.println("2. Medio (8 enemigos)");
        System.out.println("3. Difícil (12 enemigos)");
        dificultad = scanner.nextInt();
        scanner.nextLine();
    }

    private void configurarJuego() {
        System.out.println("¿Desea permitir que los jugadores pasen de un lado al otro del tablero? (S/N):");
        String respuesta = scanner.nextLine();
        permitirPasoBorde = respuesta.equalsIgnoreCase("S");
    }

    private void inicializarJuego() {
        tablero1.colocarJugador(jugador1);
        tablero2.colocarJugador(jugador2);

        int enemigos = 4;
        if (dificultad == 2) {
            enemigos = 8;
        } else if (dificultad == 3) {
            enemigos = 12;
        }

        tablero1.colocarEnemigos(enemigos);
        tablero2.colocarEnemigos(enemigos);
        tablero1.colocarVidasExtras(2);
        tablero2.colocarVidasExtras(2);
        tablero1.colocarBombas(1);
        tablero2.colocarBombas(1);
        tablero1.colocarSalida();
        tablero2.colocarSalida();
    }

    public void jugar() {
        while (!juegoTerminado) {
            turnoJugador(jugador1, tablero1);
            if (juegoTerminado) break;
            turnoJugador(jugador2, tablero2);
        }
    }

    private void turnoJugador(Jugador jugador, Tablero tablero) {
        System.out.println("Turno del jugador: " + jugador.getRepresentacion());
        tablero.mostrarTablero();

        System.out.println("Presiona 'T' para activar el modo trucos y ver los enemigos, 'B' para usar la bomba, o cualquier otra tecla para continuar.");
        String opcion = scanner.nextLine();

        if (opcion.equalsIgnoreCase("T")) {
            tablero.mostrarTableroConEnemigos();
        } else if (opcion.equalsIgnoreCase("B") && jugador.tieneBomba()) {
            tablero.detonarBomba(jugador.getX(), jugador.getY());
            jugador.usarBomba();
            System.out.println("¡Bomba detonada! Enemigos en un radio de 2 casillas eliminados.");
            tablero.mostrarTablero();
        }

        System.out.println("Introduce tu movimiento (por ejemplo, 2W):");
        String movimiento = scanner.nextLine();

        while (!esMovimientoValido(movimiento)) {
            System.out.println("Movimiento no válido. Ingresa su movimiento (por ejemplo, 2W):");
            movimiento = scanner.nextLine();
        }

        boolean haLlegadoASalida = tablero.moverJugador(jugador, movimiento, permitirPasoBorde);
        tablero.mostrarTablero();

        if (haLlegadoASalida) {
            System.out.println("¡El jugador " + jugador.getRepresentacion() + " ha llegado a la salida! ¡Ganaste!");
            juegoTerminado = true;
        } else if (jugador.getVidas() <= 0) {
            System.out.println("¡El jugador " + jugador.getRepresentacion() + " ha perdido todas sus vidas!");
            juegoTerminado = true;
        }
    }

    private boolean esMovimientoValido(String movimiento) {
        return movimiento.length() >= 2 && Character.isDigit(movimiento.charAt(0)) && "WSAD".indexOf(movimiento.charAt(1)) != -1;
    }

    public static void main(String[] args) {
        JuegoTablero juego = new JuegoTablero();
        juego.jugar();
    }
}


