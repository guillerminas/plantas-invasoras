package juego;

import java.awt.*;

import entorno.Entorno;
import entorno.Herramientas;

public class Calle {
    // Variables de instancia
    double x, y, ancho, alto;
    int grados;
    Image img;

    public Calle(double x, double y, int grados) {
        this.x = x;
        this.y = y;
        this.grados = grados;
        this.ancho = 100;
        this.alto = 700;
        System.out.println("ancho " + this.ancho + "  alto " + this.alto);

    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, Herramientas.radianes(this.grados), Color.white);
    }
}
