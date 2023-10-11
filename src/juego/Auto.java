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

    Auto(double x, double y, double e) {
        this.direccion = 1;
        this.escala = e;
        img = Herramientas.cargarImagen("source/auto.png");
        this.ancho = img.getWidth(null) * this.escala;
        this.alto = img.getHeight(null) * this.escala;
        this.velocidad = 3;
        this.x = x;
        this.y = y;
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
    }

    public void mover(int d, Entorno e) {
        if (this.direccion == 1) {
            this.x += velocidad;
        }
        if (this.direccion == 3) {
            this.x -= velocidad;
        }
        // Mover el auto hacia la derecha (de izquierda a derecha)

        if (x > e.ancho() - 30) {
            this.direccion = 3;
        }
        if (x < 20) {
            this.direccion = 1;
        }
    }
}
