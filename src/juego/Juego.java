package juego;

import java.awt.*;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class  Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    Entorno entorno;

    // Variables y métodos propios de cada grupo
    Laika laika;
    Barra barra;
    Manzana[][] manzanas;
    Auto[] autos;
    int fila = 2;
    int columna = 3;

    public Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Plantas Invasoras - Grupo 2 - v1", 1024, 768);

        // Inicializar lo que haga falta para el juego
        barra = new Barra((double) this.entorno.ancho() / 2, this.entorno.alto() - 20, this.entorno.ancho(), 80);
        laika = new Laika((double) 1024 / 2, barra.y - barra.alto);
        manzanas = new Manzana[fila][columna];
        double y0 = (768 / 2) / 1.8;
        double y1 = (768 / 2) * 1.3;
        manzanas[0][0] = new Manzana((double) (1024 / 2) / 2.3, y0, 0.30);
        manzanas[0][1] = new Manzana((double) (1024 / 2), y0, 0.30);
        manzanas[0][2] = new Manzana((double) (1024 / 2) * 1.56, y0, 0.30);
        manzanas[1][0] = new Manzana((double) (1024 / 2) / 2.3, y1, 0.30);
        manzanas[1][1] = new Manzana((double) (1024 / 2), y1, 0.30);
        manzanas[1][2] = new Manzana((double) (1024 / 2) * 1.56, y1, 0.30);
        autos = new Auto[4];
        for (int i = 0; i < autos.length; i++) {
            if (i % 2 == 0) {
                autos[i] = new Auto(30, 255 * i + this.entorno.alto() / 7.5, 0.30, 1);
            } else {
                autos[i] = new Auto(260 * i + this.entorno.ancho() / 7.5, 30, 0.30, 2);
            }
        }


        // Inicia el juego!
        this.entorno.iniciar();
    }

    /**
     * Durante el juego, el método tick() será ejecutado en cada instante y
     * por lo tanto es el método más importante de esta clase. Aquí se debe
     * actualizar el estado interno del juego para simular el paso del tiempo
     * (ver el enunciado del TP para mayor detalle).
     */
    public void tick() {
        // Procesamiento de un instante de tiempo
        if (entorno.estaPresionada(entorno.TECLA_DERECHA) && restriccionm(manzanas, laika) != 1) {
            laika.mover(1, this.entorno);
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && restriccionm(manzanas, laika) != 3) {
            laika.mover(3, this.entorno);
        }
        if (entorno.estaPresionada(entorno.TECLA_ABAJO) && restriccionm(manzanas, laika) != 2) {
            laika.mover(2, this.entorno);
        }
        if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && restriccionm(manzanas, laika) != 0) {
            laika.mover(0, this.entorno);
        }


        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                manzanas[i][j].dibujarse(this.entorno);
            }
        }

        for (int i = 0; i < autos.length; i++) {
            if (i % 2 == 0) {
                autos[i].dibujarse(this.entorno);
                autos[i].mover(this.entorno);
            } else {
                autos[i].dibujarse(this.entorno);
                autos[i].mover(this.entorno);
            }
        }

        laika.dibujarse(this.entorno);
        barra.dibujarse(this.entorno);
        entorno.cambiarFont("Arial", 18, Color.black);

        entorno.escribirTexto("posicion en x:" + laika.x, barra.ancho / 1.2, barra.y);
        entorno.escribirTexto("posicion en y:" + laika.y, barra.ancho / 1.5, barra.y);
    }

    private int restriccionm(Manzana[][] m, Laika a) {
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++)
                if (estaTocando(m[i][j], a, 35) < 5) {
                    return estaTocando(m[i][j], a, 35);
                }
        }
        return 5;
    }

    public int estaTocando(Manzana m, Laika a, double guarda) {
        double lado_izquierdo = m.x - m.ancho / 2;
        double lado_arriba = m.y - m.alto / 2;
        double lado_abajo = m.y + m.alto / 2;
        double lado_derecho = m.x + m.ancho / 2;

        if (a.y < lado_abajo && a.y > lado_arriba && a.x > lado_izquierdo - guarda && a.x < lado_derecho) {
            return 1;
        }
        if (a.y < lado_abajo && a.y > lado_arriba && a.x > lado_izquierdo && a.x < lado_derecho + guarda) {
            return 3;
        }
        if (a.y < lado_abajo && a.y > lado_arriba - guarda && a.x > lado_izquierdo && a.x < lado_derecho) {
            return 2;
        }
        if (a.y < lado_abajo + guarda && a.y > lado_arriba && a.x > lado_izquierdo && a.x < lado_derecho) {
            return 0;
        }
        return 5;
    }


    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}
