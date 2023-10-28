package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;

public class BolaDeFuego {
    double x;
    double y;
    double radio, velocidad;
    int direccion;

    public BolaDeFuego(double x, double y, double radio, double velocidad, int direccion) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.velocidad = velocidad;
        this.direccion = direccion;
        mover();
    }

    public void mover() {
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
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarCirculo(this.x, this.y, this.radio, Color.orange);
    }
}
