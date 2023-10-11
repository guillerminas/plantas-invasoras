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
        this.velocidad = 10;
        this.x = x;
        this.y = y;
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
    }

    public void mover(int d, Entorno e) {
        this.direccion = d;
        // Mover el auto hacia la derecha (de izquierda a derecha)
        x += velocidad;
        if (x > e.ancho() + 50) {
            x = -40; // Reiniciar el auto al llegar al final de la pantalla
        }
    }
}
