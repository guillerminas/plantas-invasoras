package juego;

import java.awt.Image;

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

        if (x > e.ancho() - 35) {
            this.direccion = 3;
        }

        if (y < 35) {
            this.direccion = 2;
        }

        if (y > e.alto() - 90) {
            this.direccion = 0;
        }

        if (x < 35) {
            this.direccion = 1;
        }
    }
}
