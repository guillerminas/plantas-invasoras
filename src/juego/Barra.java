package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;


public class Barra {
    double x, y, ancho, alto;

    Barra(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public void dibujarse(Entorno e) {
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.white);
    }
}