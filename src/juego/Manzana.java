package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Manzana {

    // Variables de instancia
    double x;
    double y;
    double ancho;
    double alto;
    Image img;
    double escala;

    public Manzana(double x, double y, double e) {

        this.x = x;
        this.y = y;
        this.escala=e;
        img = Herramientas.cargarImagen("source/manzana.png");
        this.ancho=img.getWidth(null)*this.escala;
        this.alto=img.getHeight(null)*this.escala;
        System.out.println("ancho "+this.ancho+"  alto "+this.alto);
    }

    public void dibujarse(Entorno entorno)
    {
        entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
    }

}
