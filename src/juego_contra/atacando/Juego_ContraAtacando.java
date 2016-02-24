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
import java.util.LinkedList;

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
    
    //Imagen de Jugador
    private Image imaJugador;
    
    //Imagen del Disparo
    private Image imaDisparo;
    
    //Base de Jugador
    private Base basJugador; // Objeto Base del Jugador Principal
    
    //Proyectil de Disparo
    private Proyectil pylDisparo; // Objeto Disparo
    
    //Lista de Disparos
    private LinkedList lklDisparos = new LinkedList();
    
    //Direccion del Jugador
    private int iDireccion = 0;
    
    //Bandera para controlar Disparos
    private boolean bAllowShoot = true;
    
    
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
        
        //Definir la Imagen del Jugador
        imaJugador =  Toolkit.getDefaultToolkit().getImage(this.getClass().
               getResource("Principal.gif"));
        
        //Definir la Imagen de los Disparos
        imaDisparo = Toolkit.getDefaultToolkit().getImage(this.getClass().
               getResource("Disparo.gif"));
        
        //Definir el Jugador con su Imagen
        basJugador = new Base (0,0,imaJugador);
        
        //Inicializa Pos del Jugador
       PosInicialJugador(basJugador); 
        
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
        
        //Si la direccion del personaje es 1->Derecha
        if(iDireccion == 1) {
            
            //Muevelo a la derecha
            basJugador.setX(basJugador.getX()+3);
        }
        //Si la direccion del personaje es 2->Izquierda
        else if(iDireccion == 2) {
            
            //Muevelo a la Izquierda
            basJugador.setX(basJugador.getX()-3);
        }
        
        //Actualiza el Movimiento de los Disparos Existentes
        for(int iI = 0; iI<lklDisparos.size();iI++) {
            
            //Genera Instancia de Proyectil
            Proyectil pylInstance = (Proyectil) lklDisparos.get(iI);
            
            //Checa la direccion del Proyectil
            switch (pylInstance.getDireccion()) {
                case 1:
                    //Proyectil Derecho
                    pylInstance.setY(pylInstance.getY()-2);
                    break;
                case 2:
                    //Proyectil 45 grados a la izquierda
                    pylInstance.setY(pylInstance.getY()-2);
                    pylInstance.setX(pylInstance.getX()-2);
                    break;
                case 3:
                    //Proyectil 45 grados a la derecha
                    pylInstance.setY(pylInstance.getY()-2);
                    pylInstance.setX(pylInstance.getX()+2);
                    break;
                default:
                    break;
            }
            
        }
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
        if (imaImagenJFrame == null) {
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
        
        if(iVidas>0) {
            
            if(basJugador != null) {
                
                //Dibuja al Principal
                basJugador.paint(graDibujo,this);
            }
            
            //Si la Lista no esta vacia
            if(lklDisparos != null) {
                
                //Dibuja todos los Disparos
                for(int iI = 0; iI<lklDisparos.size();iI++) {
                    
                    //Tomar Instancia de la Lista
                    Proyectil pylInstancia = (Proyectil) lklDisparos.get(iI);
                    
                    //Dibuja la Instancia
                    pylInstancia.paint(graDibujo, this);
                    
                }
            }
                
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        
        //Si el Usuario presiona la tecla Derecha
        if(keyEvent.getKeyCode() == keyEvent.VK_RIGHT) 
        {
            //Has la direccion del Jugador a la derecha
            iDireccion = 1;
        }
        //Si el Usuario presiona la tecla de la Izquierda
        else if(keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
            
            //Has la direccion del Jugador a la derecha
            iDireccion = 2;
        }
        
        //Si el Usuario presiona la tecla de Disparo Vertical y puede disparar
        if(keyEvent.getKeyCode() == keyEvent.VK_SPACE && bAllowShoot) {
                        
            //Remueve el privilegio para disparar
            //hasta que el usuario suelte la tecla
            bAllowShoot = false;
            
            //Crea el disparo con direccion en Vertical '1'
            CrearDisparo(1);
        }
        
        //Si el Usuario presiona la tecla de Disparo 45 grados
        // a la izquierda y puede disparar
        if(keyEvent.getKeyCode() == keyEvent.VK_A && bAllowShoot) {
                        
            //Remueve el privilegio para disparar
            //hasta que el usuario suelte la tecla
            bAllowShoot = false;
            
            //Crea el disparo con direccion 45 grados a la izquierda '2'
            CrearDisparo(2);
        }
        
        //Si el Usuario presiona la tecla de Disparo 45 grados
        // a la derecha y puede disparar
        if(keyEvent.getKeyCode() == keyEvent.VK_S && bAllowShoot) {
                        
            //Remueve el privilegio para disparar
            //hasta que el usuario suelte la tecla
            bAllowShoot = false;
            
            //Crea el disparo con direccion 45 grados a la izquierda '3'
            CrearDisparo(3);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        
        //Si el Usuario solto las teclas de Movimiento
        if(keyEvent.getKeyCode() == keyEvent.VK_RIGHT || 
                keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
            
            //Has su direccion 0, (No movimiento)
            iDireccion = 0;
        }
        
        //Si el Usuario Solto las teclas de disparo: 'Spacebar' , 'A' , 'S'
        if(keyEvent.getKeyCode() == keyEvent.VK_SPACE || 
                keyEvent.getKeyCode() == keyEvent.VK_A ||
                keyEvent.getKeyCode() == keyEvent.VK_S) {
            
            //Regresale el privilegio de disparar
            bAllowShoot = true;
        }
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
     * PosInicialJugador
     * 
     * Metodo que recibe a el jugador y lo coloca
     * en su posicion inicial
     * 
     */
    void PosInicialJugador (Base basObj)
    {
       basObj.setX( (getWidth()/2) - basObj.getAncho()/2 );
       basObj.setY( (getHeight() - 100));
    }
    
    /**
     * CrearDisparo
     * 
     * Metodo que Crea y pinta un Objeto Disparo
     * @param iDir Es la <code> Direccion</code> del Proyectil
     */
    void CrearDisparo (int iDir)
    {    
        //Crear el Objeto Disparo
        pylDisparo = new Proyectil (0,0,imaDisparo,0);

        //Posicionar Disparo
        pylDisparo.setX(basJugador.getX() - 8 + basJugador.getAncho()/2);
        pylDisparo.setY(basJugador.getY() + basJugador.getAlto()/2);

        //Direccionar Disparo
        pylDisparo.setDireccion(iDir);
        
        //Agregar Disparo a la Lista
        lklDisparos.addLast(pylDisparo);
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
