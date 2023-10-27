package juego;

import java.awt.Image;
import java.util.concurrent.ThreadLocalRandom;

import entorno.Entorno;
import entorno.Herramientas;

public class Auto {
    // Variables de instancia
    Image img;
    double x, y, ancho, alto, velocidad, escala;
    int direccion;

    Auto(double x, double y, double e, double velocidad, int direccion) {
        this.escala = e;
        img = Herramientas.cargarImagen("source/auto.png");
        this.ancho = img.getWidth(null) * this.escala;
        this.alto = img.getHeight(null) * this.escala;
        this.direccion = direccion;
        this.velocidad = velocidad;
        this.x = x;
        this.y = y;
    }

    void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
    }

    void mover(Entorno e) {

        switch (direccion) {
            case 0:
                y -= this.velocidad;
                break;
            case 1:
                x += this.velocidad;
                break;
            case 2:
                y += this.velocidad;
                break;
            case 3:
                x -= this.velocidad;
                break;
        }

        if (x > e.ancho() + 35) {
            x = -35;
        }

        if (y < -35) {
            y = e.alto() + 35;
        }

        if (y > e.alto() + 35) {
            y = -35;
        }

        if (x < -35) {
            x = e.ancho() + 35;
        }
    }
}
