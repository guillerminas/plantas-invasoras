package juego;

import java.awt.Image;
import java.awt.*;
import entorno.Entorno;
import entorno.Herramientas;

public class Rayo {
    Image img;
    double x, y, escala;
    double velocidadY;


    Rayo(double x, double y, double e) {
        img = Herramientas.cargarImagen("source/rayo.png");
        this.escala = e;
        this.x = x;
        this.y = y;
        this.velocidadY = -5;
    }

    void mover() {
        y += velocidadY;
    }


    void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
    }

}
