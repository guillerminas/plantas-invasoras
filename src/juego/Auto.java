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
        int r = ThreadLocalRandom.current().nextInt(4);

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

        if (x > e.ancho() - 35 && r != 1) {
            this.direccion = r;
        }

        if (y < 35 && r != 0) {
            this.direccion = r;
        }

        if (y > e.alto() - 90 && r != 2) {
            this.direccion = r;
        }

        if (x < 35 && r != 3) {
            this.direccion = r;
        }
    }
}
