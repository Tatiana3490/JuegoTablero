public class Jugador {
    private char representacion;
    private int x;
    private int y;
    private int vidas;
    private boolean tieneBomba;

    public Jugador(char representacion) {
        this.representacion = representacion;
        this.vidas = 3; // Vidas iniciales
        this.tieneBomba = false;
    }

    public char getRepresentacion() {
        return representacion;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getVidas() {
        return vidas;
    }

    public void ganarVida() {
        vidas++;
    }

    public void perderVida() {
        vidas--;
    }

    public boolean tieneBomba() {
        return tieneBomba;
    }

    public void recogerBomba() {
        tieneBomba = true;
    }

    public void usarBomba() {
        tieneBomba = false;
    }
}
