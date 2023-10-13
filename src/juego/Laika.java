package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Laika {
    // Variables de instancia
    double x;
    double y;
    double velocidad;
    int direccion;
    Image[] img;

//constructor

    public Laika(double x, double y) {
        this.x = x;
        this.y = y;
        this.direccion = 1;
        this.velocidad = 3.5;
        this.img = new Image[4];
        for (int i = 0; i < img.length; i++) {

            img[i] = Herramientas.cargarImagen("source/Imagen" + i + ".png");

        }

    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img[this.direccion], this.x, this.y, 0, 0.15);
    }

    public void mover(int d, Entorno e) {

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


        if (x > e.ancho() + 50) {
            this.x = -50.0;
        }
        if (x < -50.0) {
            this.x = e.ancho() + 50.0;
        }
        if (y > e.alto() - 20) {
            this.y = -30.0;
        }
        if (y < -50.0) {
            this.y = e.alto() - 20;
        }
    }
}
