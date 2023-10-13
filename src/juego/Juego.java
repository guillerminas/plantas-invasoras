package juego;

import java.awt.*;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
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
        this.entorno = new Entorno(this, "Plantas Invasoras - Grupo ... - v1", 1024, 768);

        // Inicializar lo que haga falta para el juego
        autos = new Auto[1];
        autos[0] = new Auto(50, 330, 0.35);
        barra = new Barra((double) this.entorno.ancho() / 2, this.entorno.alto() - 20, this.entorno.ancho(), 80);
        laika = new Laika((double) 1024 / 2, barra.y - barra.alto);
        manzanas = new Manzana[fila][columna];
        manzanas[0][0] = new Manzana((double) (1024 / 2) / 2.3, (double) (768 / 2) / 1.8, 0.30);
        manzanas[0][1] = new Manzana((double) (1024 / 2), (double) (768 / 2) / 1.8, 0.30);
        manzanas[0][2] = new Manzana((double) (1024 / 2) * 1.56, (double) (768 / 2) / 1.8, 0.30);
        manzanas[1][0] = new Manzana((double) (1024 / 2) / 2.3, (double) (768 / 2) * 1.3, 0.30);
        manzanas[1][1] = new Manzana((double) (1024 / 2), (double) (768 / 2) * 1.3, 0.30);
        manzanas[1][2] = new Manzana((double) (1024 / 2) * 1.56, (double) (768 / 2) * 1.3, 0.30);


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
            autos[i].dibujarse(this.entorno);
            autos[i].mover(1, this.entorno);
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
                if (estaTocando(m[i][j], a, 50) < 5) {
                    return estaTocando(m[i][j], a, 50);
                }
        }
        return 5;
    }

    public int estaTocando(Manzana m, Laika a, double guarda) {
        double borde_izquierdo = m.x - m.ancho / 2;
        double borde_arriba = m.y - m.alto / 2;
        double borde_abajo = m.y + m.alto / 2;
        double borde_derecho = m.x + m.ancho / 2;

        if (a.y < borde_abajo && a.y > borde_arriba && a.x > borde_izquierdo - guarda && a.x < borde_derecho) {
            return 1;
        }
        if (a.y < borde_abajo && a.y > borde_arriba && a.x > borde_izquierdo && a.x < borde_derecho + guarda) {
            return 3;
        }
        if (a.y < borde_abajo && a.y > borde_arriba - guarda && a.x > borde_izquierdo && a.x < borde_derecho) {
            return 2;
        }
        if (a.y < borde_abajo + guarda && a.y > borde_arriba && a.x > borde_izquierdo && a.x < borde_derecho) {
            return 0;
        }
        return 5;
    }


    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}
