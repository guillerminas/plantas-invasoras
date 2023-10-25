package juego;

import java.awt.*;

import entorno.Entorno;

public class Menu {
    Entorno entorno;
    Rectangle botonJugar;

    public Menu(Entorno entorno) {
        this.entorno = entorno;
        this.botonJugar = new Rectangle(500, 360, 300, 100);
    }

    public void dibujarMenu() {
        entorno.cambiarFont("Arial", 24, Color.WHITE);
        entorno.escribirTexto("Haz clic en 'Jugar' para empezar", 350, 290);

        int x = (int) botonJugar.getX();
        int y = (int) botonJugar.getY();
        int width = (int) botonJugar.getWidth();
        int height = (int) botonJugar.getHeight();

        // Dibujar botón "Jugar"
        entorno.dibujarRectangulo(x, y, width, height, 0, Color.GREEN);
        entorno.cambiarFont("Arial", 60, Color.BLACK);
        entorno.escribirTexto("Jugar", x - 55, y + 20);
    }

    public boolean procesarClick(int x, int y) {
        return botonJugar.contains(x, y);
    }
}