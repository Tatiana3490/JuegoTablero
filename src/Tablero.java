import java.util.Random;

public class Tablero {
    private char[][] tablero;
    private int tamano;
    private Random random;

    public Tablero(int tamano) {
        this.tamano = tamano;
        tablero = new char[tamano][tamano];
        random = new Random();
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                tablero[i][j] = 'L'; // Todas las casillas libres inicialmente
            }
        }
    }

    public void colocarJugador(Jugador jugador) {
        int x = random.nextInt(tamano);
        int y = random.nextInt(tamano);
        while (tablero[x][y] != 'L') {
            x = random.nextInt(tamano);
            y = random.nextInt(tamano);
        }
        tablero[x][y] = jugador.getRepresentacion();
        jugador.setPosicion(x, y);
    }

    public void colocarEnemigos(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            int x = random.nextInt(tamano);
            int y = random.nextInt(tamano);
            while (tablero[x][y] != 'L') {
                x = random.nextInt(tamano);
                y = random.nextInt(tamano);
            }
            tablero[x][y] = 'E'; // Representamos los enemigos con 'E'
        }
    }

    public void colocarVidasExtras(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            int x = random.nextInt(tamano);
            int y = random.nextInt(tamano);
            while (tablero[x][y] != 'L') {
                x = random.nextInt(tamano);
                y = random.nextInt(tamano);
            }
            tablero[x][y] = 'V'; // Representamos las vidas extras con 'V'
        }
    }

    public void colocarBombas(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            int x = random.nextInt(tamano);
            int y = random.nextInt(tamano);
            while (tablero[x][y] != 'L') {
                x = random.nextInt(tamano);
                y = random.nextInt(tamano);
            }
            tablero[x][y] = 'B'; // Representamos las bombas con 'B'
        }
    }

    public void colocarSalida() {
        int x = random.nextInt(tamano);
        int y = random.nextInt(tamano);
        while (tablero[x][y] != 'L') {
            x = random.nextInt(tamano);
            y = random.nextInt(tamano);
        }
        tablero[x][y] = 'S'; // Representamos la salida con 'S'
    }

    public void mostrarTablero() {
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                if (tablero[i][j] == 'E') {
                    System.out.print("L ");
                } else {
                    System.out.print(tablero[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void mostrarTableroConEnemigos() {
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean moverJugador(Jugador jugador, String movimiento, boolean permitirPasoBorde) {
        int x = jugador.getX();
        int y = jugador.getY();
        int desplazamiento = Character.getNumericValue(movimiento.charAt(0));
        char direccion = movimiento.charAt(1);

        tablero[x][y] = 'L';

        switch (direccion) {
            case 'W':
                x = permitirPasoBorde ? (x - desplazamiento + tamano) % tamano : Math.max(x - desplazamiento, 0);
                break;
            case 'S':
                x = permitirPasoBorde ? (x + desplazamiento) % tamano : Math.min(x + desplazamiento, tamano - 1);
                break;
            case 'A':
                y = permitirPasoBorde ? (y - desplazamiento + tamano) % tamano : Math.max(y - desplazamiento, 0);
                break;
            case 'D':
                y = permitirPasoBorde ? (y + desplazamiento) % tamano : Math.min(y + desplazamiento, tamano - 1);
                break;
            default:
                System.out.println("Dirección no válida.");
                return false;
        }

        if (tablero[x][y] == 'V') {
            jugador.ganarVida();
            System.out.println("¡Encontraste una vida extra! Vidas: " + jugador.getVidas());
        } else if (tablero[x][y] == 'E') {
            jugador.perderVida();
            System.out.println("¡Encontraste un enemigo! Vidas: " + jugador.getVidas());
        } else if (tablero[x][y] == 'B') {
            jugador.recogerBomba();
            System.out.println("¡Encontraste una bomba!");
        } else if (tablero[x][y] == 'S') {
            jugador.setPosicion(x, y);
            tablero[x][y] = jugador.getRepresentacion();
            return true;
        }

        jugador.setPosicion(x, y);
        tablero[x][y] = jugador.getRepresentacion();
        return false;
    }

    public void detonarBomba(int x, int y) {
        int radio = 2;
        for (int i = Math.max(x - radio, 0); i <= Math.min(x + radio, tamano - 1); i++) {
            for (int j = Math.max(y - radio, 0); j <= Math.min(y + radio, tamano - 1); j++) {
                if (tablero[i][j] == 'E') {
                    tablero[i][j] = 'L';
                }
            }
        }
    }
}



