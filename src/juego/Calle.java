package juego;

import java.awt.*;

import entorno.Entorno;
import entorno.Herramientas;

public class Calle {
    double x;
    double y;
    double ancho;
    double alto;
    int grados;
    Color color;

    public Calle(double x, double y, double ancho, double alto, int grados) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.grados = grados;
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, Herramientas.radianes(this.grados), color);
    }
}