/* package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Fondo{
    double x, y;
    BufferedImage imgFondo;
    int ancho;
    int alto;

    public Fondo(double x, double y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public void cargarImagen(String ruta) {
        try {
            imgFondo = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dibujarFondo(Entorno entorno) {
        int fondoAncho = imgFondo.getWidth();
        int tiles = (int) Math.ceil((double) ancho / fondoAncho) + 1;

        for (int i = 0; i < tiles; i++) {
            entorno.dibujarImagen(imgFondo, x + i * fondoAncho, y, 0, 1);
        }
    }

    public void moverFondo() {
        x -= 1;
        if (Math.abs(x) > imgFondo.getWidth()) {
            x = 0;
        }
    }
}

*/