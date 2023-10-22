package juego;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class  Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    Entorno entorno;

    // Variables y métodos propios de cada grupo
    Laika laika;
    Barra barra;
    Auto[] autos;
    Manzana[][] manzanas;
    Rayo[] rayos;


    // Variables de setup
    int vidas = 2;
    int row = 2;
    int column = 3;
    int carsAmount = 4;
    private int numRayos;

    public Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Plantas Invasoras - Grupo 2 - v1", 1024, 768);

        // Medidas de la pantalla
        int windowX = this.entorno.ancho();
        double windowMiddle = windowX * 0.5;
        int windowY = this.entorno.alto();

        // Inicializar lo que haga falta para el juego
        barra = new Barra(windowMiddle, windowY - 20, windowX, 80);
        laika = new Laika(windowMiddle, barra.y - barra.alto);

        rayos = new Rayo[10000];


        // Setup Manzanas
        manzanas = new Manzana[row][column];
        double row0 = (768 / 2) / 1.8;
        double row1 = (768 / 2) * 1.3;
        manzanas[0][0] = new Manzana(windowMiddle / 2.3, row0, 0.30);
        manzanas[0][1] = new Manzana(windowMiddle, row0, 0.30);
        manzanas[0][2] = new Manzana(windowMiddle * 1.56, row0, 0.30);
        manzanas[1][0] = new Manzana(windowMiddle / 2.3, row1, 0.30);
        manzanas[1][1] = new Manzana(windowMiddle, row1, 0.30);
        manzanas[1][2] = new Manzana(windowMiddle * 1.56, row1, 0.30);

        // Setup Autos
        autos = new Auto[carsAmount];
        for (int i = 0; i < autos.length; i++) {
            double random = ThreadLocalRandom.current().nextDouble(1.5, 5);
            if (i % 2 == 0) {
                autos[i] = new Auto(30, 255 * i + windowY / 7.5, 0.30, random, 1);
            } else {
                autos[i] = new Auto(260 * i + windowX / 7.5, 30, 0.30, random, 2);
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


        if (entorno.sePresiono(' ')) {
            if (numRayos < rayos.length) {
                rayos[numRayos] = new Rayo(laika.x, laika.y, 0.23);
                numRayos++; }
        }


        for (int i = 0; i < numRayos; i++) {
            rayos[i].mover();
            rayos[i].dibujarse(this.entorno);

            if (rayos[i].y < 0) {
                rayos[i] = rayos[numRayos - 1];
                rayos[numRayos - 1] = null;
                numRayos--;
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                manzanas[i][j].dibujarse(this.entorno);
            }
        }

        for (int i = 0; i < autos.length; i++) {
            if (i % 2 == 0) {
                autos[i].dibujarse(this.entorno);
                autos[i].mover(this.entorno);
                if (colissionCheck(autos[i])) {
                    laika.x = this.entorno.ancho() * 0.5;
                    laika.y = this.entorno.alto() - 20;
                }
            } else {
                autos[i].dibujarse(this.entorno);
                autos[i].mover(this.entorno);
                if (colissionCheck(autos[i])) {
                    laika.x = this.entorno.ancho() * 0.5;
                    laika.y = this.entorno.alto() - 20;
                }
            }
        }

        laika.dibujarse(this.entorno);
        barra.dibujarse(this.entorno);
        entorno.cambiarFont("Arial", 18, Color.black);

        entorno.escribirTexto("posicion en x:" + laika.x, barra.ancho / 1.2, barra.y);
        entorno.escribirTexto("posicion en y:" + laika.y, barra.ancho / 1.5, barra.y);
        entorno.escribirTexto("Vidas: " + vidas, barra.ancho/barra.ancho, barra.y);

        if(vidas == 1) {
            entorno.cambiarFont("Arial", 150, Color.RED);
            entorno.escribirTexto("Perdiste", 250,384);
        }
    }

    private boolean colissionCheck(Auto auto) {
        if (laika.x < auto.x + auto.ancho && laika.x + laika.getWidth() > auto.x && laika.y < auto.y + auto.alto
                && laika.y + laika.getHeight() > auto.y) {
            vidas -= 1;
            return true;
        } else {
            return false;
        }
    }

    private int restriccionm(Manzana[][] m, Laika a) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++)
                if (estaTocando(m[i][j], a, 35.0) < 5) {
                    return estaTocando(m[i][j], a, 35.0);
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
