package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Laika {
    // Variables de instancia
    double x, y, ancho, alto, velocidad, escala;
    int direccion;
    Image[] img;

    // constructor

    Laika(double x, double y) {
        this.x = x;
        this.y = y;
        this.direccion = 1;
        this.velocidad = 3.5;
        this.escala = 0.10;
        this.img = new Image[4];
        for (int i = 0; i < img.length; i++) {
            img[i] = Herramientas.cargarImagen("source/Imagen" + i + ".png");
        }
    }

    void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img[this.direccion], this.x, this.y, 0, this.escala);
    }

    double getWidth() {
        return img[this.direccion].getWidth(null) * this.escala;
    }

    double getHeight() {
        return img[this.direccion].getHeight(null) * this.escala;
    }

    void mover(int d, Entorno e) {

        this.direccion = d;

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

        if (x > e.ancho() - 32) {
            this.x = e.ancho() - 32;
        }
        if (x < 30) {
            this.x = 30.0;
        }
        if (y > e.alto() - 90) {
            this.y = e.alto() - 90;
        }
        if (y < 30) {
            this.y = 30;
        }
    }
}
