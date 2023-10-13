package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Auto {
    // Variables de instancia
    Image img;
    double x, y, ancho, alto, escala;
    int velocidad;
    int direccion;

    Auto(double x, double y, double e, int direccion) {
        this.escala = e;
        img = Herramientas.cargarImagen("source/auto.png");
        this.ancho = img.getWidth(null) * this.escala;
        this.alto = img.getHeight(null) * this.escala;
        this.direccion = direccion;
        this.velocidad = 3;
        this.x = x;
        this.y = y;
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
    }

    public void mover(Entorno e) {
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

        if (x > e.ancho() - 30) {
            this.direccion = 3;
        }

        if (y < 10) {
            this.direccion = 2;
        }

        if (y > e.alto() - 80) {
            this.direccion = 0;
        }

        if (x < 20) {
            this.direccion = 1;
        }
    }
}
