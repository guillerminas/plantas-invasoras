package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Manzana {

    // Variables de instancia
    double x, y, ancho, alto, escala;
    Image img;

    public Manzana(double x, double y, double e) {

        this.x = x;
        this.y = y;
        this.escala = e;
        img = Herramientas.cargarImagen("source/manzana.png");
        this.ancho = img.getWidth(null) * this.escala;
        this.alto = img.getHeight(null) * this.escala;
        System.out.println("ancho " + this.ancho + "  alto " + this.alto);
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
    }

    public double ancho() {
        return this.ancho;
    }

    public boolean intersects(double x, double y, double width, double height) {
        double left = this.x - this.ancho / 2;
        double right = this.x + this.ancho / 2;
        double top = this.y - this.alto / 2;
        double bottom = this.y + this.alto / 2;

        return !(left > x + width || right < x || top > y + height || bottom < y);
    }
}
