package juego;

import java.awt.Image;
import java.awt.*;
import entorno.Entorno;
import entorno.Herramientas;

public class Rayo {
    Image img;
    double x, y, escala;
    double velocidad;
    int direccion;

    Rayo(double x, double y, double e, int direccion) {
        img = Herramientas.cargarImagen("source/rayo.png");
        this.escala = e;
        this.x = x;
        this.y = y;
        this.velocidad = 5;
        this.direccion = direccion;
    }

    void mover() {
        if (direccion == 0) {
            y -= this.velocidad;
        }
        if (direccion == 1) {
            x += this.velocidad;
        }
        if (direccion == 2) {
            y += this.velocidad;
        }
        if (direccion == 3) {
            x -= this.velocidad;
        }
    }

    void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
    }

}
