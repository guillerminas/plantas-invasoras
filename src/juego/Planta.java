package juego;

import java.awt.Image;
import java.util.concurrent.ThreadLocalRandom;
import juego.Juego;
import juego.Manzana;
import entorno.Entorno;
import entorno.Herramientas;

public class Planta {
    Image img;
    double x, y, ancho, alto, velocidad, escala;
    int direccion;
    Manzana[] manzanas;
    Juego juego;

    Planta(double x, double y, double e, double velocidad, int direccion) {
        this.escala = e;
        img = Herramientas.cargarImagen("source/planta.png");
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
                y -= velocidad;
                break;
            case 1:
                x += velocidad;
                break;
            case 2:
                y += velocidad;
                break;
            case 3:
                x -= velocidad;
                break;
        }

        // Check for collisions with Manzana objects and change direction if necessary
        for (Manzana manzana : Juego.manzanas) {
            if (manzana.intersects(x, y, ancho, alto)) {
                // If the Planta collides with a Manzana, change its direction to be opposite
                direccion = (direccion + 2) % 4;
                break;
            }
        }

        // Check for collisions with other Plantas and change direction if necessary
        for (Planta planta : Juego.plantas) {
            if (planta != this && intersects(planta)) {
                // If the two Plantas collide, change their directions to be opposite
                direccion = (direccion + 2) % 4;
                planta.direccion = (planta.direccion + 2) % 4;
                break;
            }
        }

        // Check for collisions with the edges of the window and change direction if
        // necessary
        if (x < ancho / 2 || x > Juego.windowX - ancho / 2) {
            direccion = (direccion + 2) % 4;
        }
        if (y < alto / 2 || y > Juego.windowY - alto / 2) {
            direccion = (direccion + 2) % 4;
        }

        // if (x > e.ancho() + 35) {
        // x = -35;
        // }

        // if (y < -35) {
        // y = e.alto() + 35;
        // }

        // if (y > e.alto() + 35) {
        // y = -35;
        // }

        // if (x < -35) {
        // x = e.ancho() + 35;
        // }
    }

    public boolean intersects(Planta other) {
        // Calculate the coordinates of the corners of the two Plantas
        double left1 = this.x - this.ancho / 2;
        double right1 = this.x + this.ancho / 2;
        double top1 = this.y - this.alto / 2;
        double bottom1 = this.y + this.alto / 2;
        double left2 = other.x - other.ancho / 2;
        double right2 = other.x + other.ancho / 2;
        double top2 = other.y - other.alto / 2;
        double bottom2 = other.y + other.alto / 2;

        // Check if the two Plantas intersect
        return !(left1 > right2 || right1 < left2 || top1 > bottom2 || bottom1 < top2);
    }
}
