/* package juego;

import entorno.Entorno;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Fondo {
    int anchoFondo= 1024;
    int altoFondo= 768;
    int velocidad=1;
    int imx1=0;
    int imy1=0;
    int imx2 = anchoFondo;
    int imy2= 0;
    String imagenFondo="source/preview.jpg";

    public void dibujarFondo(Graphics2D pintarFondo) {
        ImageIcon imagenFondo = new ImageIcon(getClass().getResource(imagenFondo));
        pintarFondo.drawImage(imagenFondo.getImage(),imx1,imy1,anchoFondo,altoFondo,null);
        pintarFondo.drawImage(imagenFondo.getImage(),imx2,imy2,anchoFondo,altoFondo,null);
    }

    public void moverFondo() {
        imx1 =imx1 - velocidad;
        imx2 =imx2 - velocidad;

        if(imx1 < 0) {
            imx1= anchoFondo;
            imx2=0;
        }

    }
}
*/
