package juego;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

    /***************************************
     * Declaracion de Objetos y auxiliares *
     ***************************************/

    Entorno entorno;
    Menu menu;

    Laika laika;
    Barra barra;

    static Planta[] plantas;
    Auto[] autos;
    Rayo[] rayos;
    static Manzana[] manzanas;
    // Manzana[][] manzanas;

    // Auxiliares de Setup

    // int vidas = 2;
    int row = 2;
    int column = 3;
    int numAutos = 4;
    static int numPlantas = 6;
    int numRayos = 0;

    // Pantalla
    static int windowX = 1024;
    static int windowY = 768;
    double windowMiddle = windowX * 0.5;
    double barraDefinedY = windowY - 20;

    public Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Plantas Invasoras - Grupo 2 - v1", windowX, windowY);
        this.menu = new Menu(this.entorno);

        // Inicializar lo que haga falta para el juego
        barra = new Barra(windowMiddle, barraDefinedY, windowX, 80);
        laika = new Laika(windowMiddle, barra.y - barra.alto);

        rayos = new Rayo[10000];

        // Setup Manzana
        manzanas = new Manzana[12];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                double x = windowX * 0.18 + j * (106 + 115);
                double y = windowY * 0.20 + i * (106 + 115);
                int index = i * 4 + j;
                if (index < manzanas.length) {
                    manzanas[index] = new Manzana(x, y, 0.22);
                }
            }
        }

        // Setup Autos
        autos = new Auto[numAutos];
        autos[0] = new Auto(260, windowY * 0.156, 0.18, 2, 2);
        autos[1] = new Auto(700, windowY * 0.156, 0.18, 2, 2);
        autos[2] = new Auto(330, windowY * 0.82, 0.18, 2, 0);
        autos[3] = new Auto(772, windowY * 0.82, 0.18, 2, 0);

        // Setup Plantas
        plantas = new Planta[numPlantas];
        for (int i = 0; i < plantas.length; i++) {
            double x = ThreadLocalRandom.current().nextDouble(0, windowX);
            double y = ThreadLocalRandom.current().nextDouble(0, windowY);
            double e = 0.18;
            double velocidad = ThreadLocalRandom.current().nextDouble(1, 3);
            int direccion = ThreadLocalRandom.current().nextInt(0, 4);
            plantas[i] = new Planta(x, y, e, velocidad, direccion);
        }

        // Inicia el juego!
        this.entorno.iniciar();
    }

    /*
     * Durante el juego, el método tick() será ejecutado en cada instante y
     * por lo tanto es el método más importante de esta clase. Aquí se debe
     * actualizar el estado interno del juego para simular el paso del tiempo
     * (ver el enunciado del TP para mayor detalle). *
     */

    public void tick() {
        /********
         * Menu *
         ********/

        if (!menu.juegoIniciado()) {
            menu.dibujarMenu();
            if (this.entorno.sePresiono(this.entorno.TECLA_ESPACIO)) {
                menu.procesarTecla(this.entorno.TECLA_ESPACIO);
            }
        } else {

            if (entorno.estaPresionada(entorno.TECLA_DERECHA) && restriccionLaika(manzanas, laika) != 1) {
                laika.mover(1, this.entorno);
            } else if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && restriccionLaika(manzanas, laika) != 3) {
                laika.mover(3, this.entorno);
            } else if (entorno.estaPresionada(entorno.TECLA_ABAJO) && restriccionLaika(manzanas, laika) != 2) {
                laika.mover(2, this.entorno);
            } else if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && restriccionLaika(manzanas, laika) != 0) {
                laika.mover(0, this.entorno);
            }

            /***********************
             * Tick de los Objetos *
             **********************/

            // Rayo
            if (this.entorno.sePresiono(this.entorno.TECLA_ESPACIO)) {
                if (numRayos < rayos.length) {
                    rayos[numRayos] = new Rayo(laika.x, laika.y, 0.23, laika.direccion);
                    numRayos++;
                }
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

            // Manzana
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    // manzanas[i][j].dibujarse(this.entorno);
                }
            }

            for (int i = 0; i < manzanas.length; i++) {
                manzanas[i].dibujarse(this.entorno);
            }

            // Autos
            for (int i = 0; i < autos.length; i++) {
                if (i % 2 == 0) {
                    autos[i].dibujarse(this.entorno);
                    // autos[i].mover(this.entorno);

                    if (colissionCheckLaika(autos[i])) {
                        laika.x = windowMiddle;
                        laika.y = barraDefinedY;
                    }
                } else {
                    autos[i].dibujarse(this.entorno);
                    // autos[i].mover(this.entorno);

                    if (colissionCheckLaika(autos[i])) {
                        laika.x = windowMiddle;
                        laika.y = barraDefinedY;
                    }
                }
            }

            // Plantas
            for (int i = 0; i < plantas.length; i++) {
                if (i < 4) {
                    plantas[i].dibujarse(this.entorno);
                    plantas[i].mover(this.entorno);

                    if (colissionCheckLaika(plantas[i])) {
                        laika.x = windowMiddle;
                        laika.y = barraDefinedY;
                    }
                }
            }
        }

        laika.dibujarse(this.entorno);
        // barra.dibujarse(this.entorno);
        entorno.cambiarFont("Arial", 18, Color.black);

        entorno.escribirTexto("posicion en x:" + laika.x, barra.ancho / 1.2, barra.y);
        entorno.escribirTexto("posicion en y:" + laika.y, barra.ancho / 1.5, barra.y);
        // entorno.escribirTexto("Vidas: " + vidas, barra.ancho / barra.ancho, barra.y);

        // if (vidas == 1) {
        // entorno.cambiarFont("Arial", 150, Color.RED);
        // entorno.escribirTexto("Perdiste", 250, 384);
        // }
    }

    /**********************
     * Metodos auxiliares *
     **********************/

    private boolean colissionCheckLaika(Auto auto) {
        if (laika.x < auto.x + auto.ancho && laika.x + laika.getWidth() > auto.x && laika.y < auto.y + auto.alto
                && laika.y + laika.getHeight() > auto.y) {
            return true;
        } else {
            return false;
        }
    }

    private boolean colissionCheckLaika(Planta planta) {
        if (laika.x < planta.x + planta.ancho && laika.x + laika.getWidth() > planta.x
                && laika.y < planta.y + planta.alto && laika.y + laika.getHeight() > planta.y) {
            return true;
        } else {
            return false;
        }
    }

    private int restriccionLaika(Manzana[] m, Laika a) {
        for (int i = 0; i < manzanas.length; i++) {
            if (estaTocandoLaika(m[i], a, 35.0) < 5) {
                return estaTocandoLaika(m[i], a, 35.0);
            }
        }
        return 5;
    }

    public int estaTocandoLaika(Manzana m, Laika a, double guarda) {
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