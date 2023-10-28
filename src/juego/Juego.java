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
    BolaDeFuego[] bolasDeFuego;
    static Manzana[] manzanas;


    // Auxiliares de Setup

    // int vidas = 2;
    int row = 2;
    int column = 3;
    int numAutos = 4;
    static int numPlantas = 4;
    int numRayos = 0;
    int numBolasDeFuego = 0;
    int temp = 120;

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
        bolasDeFuego = new BolaDeFuego[300];

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

        // Inicializar las calles
        /* calles = new Calle[18];
        for (int i = 0; i <= 10; i++) {
            if (i % 2 == 0) {
                double x = (i + 60);
                double y = 384;
                double ancho = 60;
                double alto = 1024;
                int grados = 180;
                calles[i] = new Calle(x,y,ancho,alto,grados);
            }
            if (i % 2 == 1) {
                double x = (i + 160);
                double y = 384;
                double ancho = 60;
                double alto = 1024;
                int grados = 180;
                calles[i] = new Calle(x,y,ancho,alto,grados);
            }
        }

        for (int i = 10; i < 18; i++) {
            if (i % 2 == 0) {
                double x = 512;
                double y = (i + 60);
                double ancho = 60;
                double alto = 1024;
                int grados = 0;
                calles[i] = new Calle(x,y,ancho,alto,grados);
            }
            if (i % 2 == 1) {
                double x = 512;
                double y = (i + 160);
                double ancho = 60;
                double alto = 1024;
                int grados = 0;
                calles[i] = new Calle(x,y,ancho,alto,grados);
            }

        } */


        // Setup Autos
        autos = new Auto[numAutos];
        autos[0] = new Auto(260, windowY * 0.156, 0.18, 2, 2);
        autos[1] = new Auto(700, windowY * 0.156, 0.18, 2, 2);
        autos[2] = new Auto(330, windowY * 0.82, 0.18, 2, 0);
        autos[3] = new Auto(772, windowY * 0.82, 0.18, 2, 0);

        // Setup Plantas
        // int r = ThreadLocalRandom.current().nextInt(4);

        plantas = new Planta[numPlantas];
        plantas[0] = new Planta(260, windowY * 0.156, 0.18, 2, 2);
        plantas[1] = new Planta(700, windowY * 0.156, 0.18, 2, 2);
        plantas[2] = new Planta(330, windowY * 0.82, 0.18, 2, 0);
        plantas[3] = new Planta(772, windowY * 0.82, 0.18, 2, 0);

        // Inicia el juego!|
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

            temp -= 1;

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
                    //manzanas[j][i].dibujarse(this.entorno);
                }
            }

            for (int i = 0; i < manzanas.length; i++) {
                manzanas[i].dibujarse(this.entorno);
            }

            // Autos
            for (int i = 0; i < autos.length; i++) {
                if (i % 2 == 0) {
                    // autos[i].dibujarse(this.entorno);
                    // autos[i].mover(this.entorno);

                    if (colissionCheckLaika(autos[i])) {
                        laika.x = windowMiddle;
                        laika.y = barraDefinedY;
                    }
                } else {
                    // autos[i].dibujarse(this.entorno);
                    // autos[i].mover(this.entorno);

                    if (colissionCheckLaika(autos[i])) {
                        laika.x = windowMiddle;
                        laika.y = barraDefinedY;
                    }
                }
            }


            /* for (int i = 0; i < calles.length; i++) {
                if (i < 10) {
                    calles[i].dibujarse(this.entorno);
                } else {
                    calles[i].dibujarse(this.entorno);
                }
            } */

            // Plantas
            for (int i = 0; i < autos.length; i++) {
                if (i % 2 == 0) {
                    plantas[i].dibujarse(this.entorno);
                    plantas[i].mover(this.entorno);

                    if (colissionCheckLaika(plantas[i])) {
                        laika.x = windowMiddle;
                        laika.y = barraDefinedY;
                    }
                } else {
                    plantas[i].dibujarse(this.entorno);
                    plantas[i].mover(this.entorno);

                    if (colissionCheckLaika(plantas[i])) {
                        laika.x = windowMiddle;
                        laika.y = barraDefinedY;
                    }
                }
            }

            if (temp == 0) {
                int r = ThreadLocalRandom.current().nextInt(plantas.length);
                Planta p = plantas[r];
                BolaDeFuego bolaDeFuego = p.dispararBolaDeFuego();
                bolasDeFuego[numBolasDeFuego] = bolaDeFuego;
                numBolasDeFuego++;
                temp = 120;
            }
            for (int i = 0; i < numBolasDeFuego; i++) {
                bolasDeFuego[i].mover();
                bolasDeFuego[i].dibujarse(this.entorno);


                if (bolasDeFuego[i].y < 0) {
                    bolasDeFuego[i] = bolasDeFuego[numBolasDeFuego - 1];
                    bolasDeFuego[numBolasDeFuego - 1] = null;
                    numBolasDeFuego--;
                }
            }

            System.out.println(temp);
        }




        laika.dibujarse(this.entorno);
        // barra.dibujarse(this.entorno);
        entorno.cambiarFont("Arial", 18, Color.black);

        /* entorno.dibujarRectangulo(30.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.cyan); //Calle 1 Mano 1
        entorno.dibujarRectangulo(100.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.magenta); // Calle 1 Mano 2(70)
        entorno.dibujarRectangulo(270.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.cyan); // Calle 2 Mano 1 (170)
        entorno.dibujarRectangulo(330.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.magenta); // Calle 2 Mano 2 (60)
        entorno.dibujarRectangulo(490.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.cyan); // Calle 3 Mano 1 (160)
        entorno.dibujarRectangulo(550.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.magenta); // Calle 3 Mano 2 (60)
        entorno.dibujarRectangulo(710.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.cyan); // Calle 4 Mano 1 (160)
        entorno.dibujarRectangulo(770.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.magenta); // Calle 4 Mano 2 (60)
        entorno.dibujarRectangulo(930.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.cyan); // Calle 5 Mano 1 (160)
        entorno.dibujarRectangulo(990.0,384.0,60.0,1024.0,Herramientas.radianes(180), Color.magenta); // Calle 5 Mano 2 (60)

        entorno.dibujarRectangulo(512,30,60.0,1024.0,Herramientas.radianes(90), Color.pink); //Calle 6 Mano 1
        entorno.dibujarRectangulo(512,70,60.0,1024.0,Herramientas.radianes(90), Color.ORANGE); // Calle 6 Mano 2 (40)
        entorno.dibujarRectangulo(512,230,60.0,1024.0,Herramientas.radianes(90), Color.pink); //Calle 7 Mano 1 (160)
        entorno.dibujarRectangulo(512,290,60.0,1024.0,Herramientas.radianes(90), Color.ORANGE); // Calle 7 Mano 2 (60)
        entorno.dibujarRectangulo(512,450,60.0,1024.0,Herramientas.radianes(90), Color.pink); //Calle 8 Mano 1 (160)
        entorno.dibujarRectangulo(512,510,60.0,1024.0,Herramientas.radianes(90), Color.ORANGE); // Calle 8 Mano 2 (60)
        entorno.dibujarRectangulo(512,670,60.0,1024.0,Herramientas.radianes(90), Color.pink); //Calle 9 Mano 1 (160)
        entorno.dibujarRectangulo(512,730,60.0,1024.0,Herramientas.radianes(90), Color.ORANGE); // Calle 9 Mano 2 (60) */



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