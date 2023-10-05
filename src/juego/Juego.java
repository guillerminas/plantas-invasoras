package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	Entorno entorno;

	// Variables y métodos propios de cada grupo
	Laika laika;
	Manzana[] manzanas;
	/* Auto[] autos;
	Planta[] plantas;
	Rayo rayo;
	BolaFuego[] bolaFuegos;
	Calle[] calles; */

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Plantas Invasoras - Grupo ... - v1", 800, 600);

		// Inicializar lo que haga falta para el juego
		laika = new Laika(392, 540);
		manzanas = new Manzana[6];
		for (int i = 0; i < manzanas.length; i++) {
			if (i % 2 == 0) {
				manzanas[i] = new Manzana(130 * (i + 1), 200, 0.25);
			} else {
				manzanas[i] = new Manzana(130 * (i), 400, 0.25);
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
		// ...

		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && restriccionm(manzanas, laika) != 1) {
			laika.mover(1, this.entorno);

		}
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && restriccionm(manzanas, laika) != 0) {
			laika.mover(0, this.entorno);

		}

		if (entorno.estaPresionada(entorno.TECLA_ABAJO) && restriccionm(manzanas, laika) != 2) {
			laika.mover(2, this.entorno);

		}

		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && restriccionm(manzanas, laika) != 3) {
			laika.mover(3, this.entorno);

		}

		for (int i = 0; i < manzanas.length; i++) {
			manzanas[i].dibujarse(this.entorno);
		}
		laika.dibujarse(this.entorno);


		// entorno.cambiarFont("Arial", 18, Color.white);

		entorno.escribirTexto("posicion en x:" + laika.x, 600, 50);
		entorno.escribirTexto("posicion en y:" + laika.y, 600, 100);

	}

	private int restriccionm(Manzana[] m, Laika a) {
		for (int i = 0; i < m.length; i++) {
			if (restriccion(m[i], a) < 5) {
				return restriccion(m[i], a);
			}
		}
		return 5;
	}

	public int restriccion(Manzana m, Laika a) {
		double zona1 = m.x - m.ancho / 2;
		double zona2 = m.y - m.alto / 2;
		double zona0 = m.y + m.alto / 2;
		double zona3 = m.x + m.ancho / 2;
		if (a.y > zona2 && a.y < zona0 && a.x > zona1 - 30 && a.x < zona3) {
			return 1;
		}
		if (a.y > zona2 && a.y < zona0 && a.x > zona1 && a.x < zona3 + 30) {
			return 3;
		}
		if (a.y > zona2 - 30 && a.y < zona0 && a.x > zona1 && a.x < zona3) {
			return 2;
		}
		if (a.y > zona2 && a.y < zona0 + 30 && a.x > zona1 && a.x < zona3) {
			return 0;
		}
		return 5;
	}
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
