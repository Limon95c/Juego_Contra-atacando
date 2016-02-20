/**
 * Juevo_ContraAtacando
 * A01280927 - A01280734
 *
 * Juego donde el personaje principal debe estar al fondo del JFrame, y se
 * moverá con las flechas <-    ->   solamente, cuando se opriman las flechas,
 * el personaje no se saldrá del JFrame. Ahora deberás de atacar a los objetos
 * que caen con disparos, cada vez que oprimas un espacio se generá un disparo
 * nuevo y este debe de seguir avanzando hasta que destruya el obstaculo en
 * cuyo caso se desaparecen ambos objetos, o choque con la orilla superior del
 * JFrame en cuyo caso debe desaparecer el disparo. Si se oprime la tecla A el
 * disparo saldrá en 45 grados a la izquierda. Si se oprime la tecla S el
 * disparo saldrá en 45 grados a la derecha. Si se oprime la tecla P se pausa
 * o despauda el juego. Al pausar el juego debe aparecer una imagen de pausa.
 * El juego inicia con 5 vidas, cada vez que se colisione al personaje
 * principal se perdera 1 punto, y por cada 5 colisiones se pierde una vida.
 * Cada vida que se pierda los obstáculos avanzan más rápido. Cada obstaculo
 * que haya sido destruido se acumulan 10 puntos. Desplegar puntos con numeros
 * y vidas con imágenes. Una vez que se termine el juego, puedes inicializarlo
 * de nuevo con alguna tecla, o con el mouse, o como se te ocurra, pero debes
 * decirle al usuario al pintar el dibujo GameOver.
 *
 * @author Jorge Gonzalez Borboa - Jorge Limón Cabrera
 * @version 0.0
 * @date dd/mm/aa
 */

package juego_contra.atacando;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import java.io.IOException;

/**
 *
 * @author Jorge
 */
public class Juego_ContraAtacando extends JFrame implements Runnable,
        KeyListener {
    
    private static int iWidth;
    private static int iHeight;
    private int iVidas;
    
    /* objetos para manejar el buffer del JFrame y 
       que la imagen no parpadee */
    private Image    imaImagenJFrame;   // Imagen a proyectar en JFrame
    private Graphics graGraficaJFrame;  // Objeto grafico de la Imagen
    
    /** 
     * Constructor
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>.<P>
     * En este metodo es el constructor y corre los metodos init y start para
     * correr el juego.
     * 
     */
    public Juego_ContraAtacando() {
        
        // Definir el ancho
        iWidth = 630;
        // Definir el alto
        iHeight = 390;
        // Correr el init
        init();
        // Correr el start
        start();
    }
    
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>JFrame</code> y se definen funcionalidades.
     * 
     */
    public void init() {
        // Empieza con 5 vidas
        iVidas = 5;
        // Dar click en el Applet para poder usar las teclas
        addKeyListener(this);
    }
    
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>JFrame</code>
     * 
     */
    public void start () {
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start();
    }
    
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     * 
     */
    public void run() {
        // Mientras se tengan vidas, actualizar posicion, checar colisiones,
        // volver a pintar todo y mandar los hilos a dormir 20 milisegundos.
        while (iVidas > 0) {
            actualiza();
            checaColision();
            repaint();
            try	{
                // El hilo del juego se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
        }
    }
    
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion de los objetos 
     * 
     */
    public void actualiza(){
        
    }
    
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision entre objetos
     * 
     */
    public void checaColision(){
        
    }
    
    /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * 
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint(Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenJFrame == null){
                imaImagenJFrame = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaJFrame = imaImagenJFrame.getGraphics ();
        }

        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("Fondo.png");
        Image imaImagenFondo =
                Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
         graGraficaJFrame.drawImage(imaImagenFondo, 0, 0, getWidth(),
                 getHeight(), this);

        // Actualiza el Foreground.
        graGraficaJFrame.setColor (getForeground());
        paint1(graGraficaJFrame);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenJFrame, 0, 0, this);
    }
    
    /**
     * paint1
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * 
     * @param graDibujo es el objeto de <code>Graphics</code> usado para
     * dibujar.
     * 
     */
    public void paint1(Graphics graDibujo) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        
    }
    
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        
    }
    
    /**
     * getWidth
     * 
     * Metodo de acceso para la variable iWidth
     * 
     * @return iWidth es el ancho de la ventana
     */
    public int getWidth() {
        return iWidth;
    }
    
    /**
     * getHeight
     * 
     * Metodo de acceso para la variable iHeight
     * 
     * @return iHeight es el alto de la ventana
     */
    public int getHeight() {
        return iHeight;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Juego_ContraAtacando Juego_ContraAtacando = new Juego_ContraAtacando();
        // Define el tamaño del JFrame
        Juego_ContraAtacando.setSize(iWidth, iHeight);
        // Define el boton de cerrar
        Juego_ContraAtacando.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Hace visible el JFrame
        Juego_ContraAtacando.setVisible(true);
    }
    
}
