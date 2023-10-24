package juego;

import entorno.Entorno;

import java.awt.*;

public class Menu {
    Entorno entorno;
    private boolean juegoIniciado = false;

    public Menu(Entorno entorno) {
        this.entorno = entorno;
    }

    public void dibujarMenu() {
        // Lógica para dibujar el menú, por ejemplo, dibujar botones y texto
        this.entorno.cambiarFont("Arial", 40, Color.WHITE);
        this.entorno.escribirTexto(
                "Plantas Invasoras",
                this.entorno.ancho() * 0.35,
                this.entorno.alto() * 0.18);

        this.entorno.cambiarFont("Arial", 30, Color.WHITE);
        this.entorno.escribirTexto(
                "Presione ESPACIO para empezar",
                this.entorno.ancho() * 0.28,
                this.entorno.alto() * 0.56);

        this.entorno.cambiarFont("Arial", 27, Color.WHITE);
        this.entorno.escribirTexto(
                "Presione ESCAPE para salir",
                this.entorno.ancho() * 0.33,
                this.entorno.alto() * 0.64);
    }

    public boolean juegoIniciado() {
        return juegoIniciado;
    }

    public void procesarTecla(char tecla) {
        if (tecla == entorno.TECLA_ESPACIO) {
            juegoIniciado = true;
        }
    }
}
