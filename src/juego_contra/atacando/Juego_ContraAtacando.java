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
    
    //Medidas del JFrame
    private static int iWidth;
    private static int iHeight;
    
    //Vidas del Jugador
    private int iVidas;
    
    //Puntaje del Jugador
    private int iScore;
    
    //Si esta o no en pausa
    private boolean bPausa;
    
    //Contador de Colisiones con Obstaculos
    private int iContCol;
    
    /* objetos para manejar el buffer del JFrame y 
       que la imagen no parpadee */
    private Image    imaImagenJFrame;   // Imagen a proyectar en JFrame
    private Graphics graGraficaJFrame;  // Objeto grafico de la Imagen
    
    //Imagen de Jugador
    private Image imaJugador;
    
    //Imagen del Disparo
    private Image imaDisparo;
    
    //Imagen del Obstaculo
    private Image imaObstaculo;
    
    //Imagen del fondo
    private Image imaImagenFondo;
    
    //Imagen de Game Over
    private Image imaGameOver;
    
    //Imagen de 1 vida
    private Image imaImagenVida;
    
    //Sonido Obstaculo derretido
    private SoundClip Derretir;
    
    //Sonido Obstaculo que choca con personaje principal
    private SoundClip Boom;
    
    //Base de Jugador
    private Base basJugador; // Objeto Base del Jugador Principal
    
    //Proyectil de Disparo
    private Proyectil pylDisparo; // Objeto Disparo
    
    //Lista de Disparos
    private LinkedList lklProyectiles = new LinkedList();
    
    //Lista de Obstaculos
    private LinkedList lklObstaculos = new LinkedList();
    
    //Boleano que dice si esta oprimida la flecha izquierda
    private boolean bI = false;
    
    //Boleano que dice si esta oprimida la flecha derecha
    private boolean bD = false;
    
    //Bandera para controlar Disparos donde 0 es que nadie acaba de disparar
    // 1 es que acaba de disparar con A
    // 2 es que acaba de disparar S
    // 3 es que acaba de disparar <Espacio>
    private int iJustShoot = 0;
    
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
   
        // Dar click en el JFrame para poder usar las teclas
        addKeyListener(this);
        
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
        
        // Empieza con 0 de Score
        iScore = 0;
        
        // Empieza sin pausa
        bPausa = false;
        
        //Contador de Colisiones con Obstaculos empieza en 0
        iContCol = 0;
        
        //Definir la Imagen del Jugador
        imaJugador =  Toolkit.getDefaultToolkit().getImage(this.getClass().
               getResource("Principal.gif"));
        
        //Definir la Imagen del Jugador
        imaImagenVida =  Toolkit.getDefaultToolkit().getImage(this.getClass().
               getResource("1Corazon.png"));
        
        //Definir la Imagen de los Disparos
        imaDisparo = Toolkit.getDefaultToolkit().getImage(this.getClass().
               getResource("Disparo.gif"));
        
        //Definir la Imagen de los Obstaculos
        imaObstaculo = Toolkit.getDefaultToolkit().getImage(this.getClass().
               getResource("Copo.gif"));
        
        //Definir la Imagen del fondo
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(this.getClass().
               getResource("Fondo.png"));
        
        //Definir la Imagen de Game Over
        imaGameOver = Toolkit.getDefaultToolkit().getImage(this.getClass().
               getResource("GameOver.png"));
        
        //Definir el Jugador con su Imagen
        basJugador = new Base (0,0,imaJugador);
        
        //Definir sonido Derretir
        Derretir = new SoundClip("Bien.wav");
        
        //Definir sonido Boom
        Boom = new SoundClip("Boom.wav");
        
        //Inicializa Pos del Jugador
        PosInicialJugador(basJugador); 
       
        //Crea los Obstaculos
        CreaObstaculos(lklObstaculos);
       
        //Inicializa Pos de los Obstaculos
        PosicionaTodosObstaculos(lklObstaculos);
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
        
        while (iVidas >= 0) {
            // Mientras haya vidas y no este en pausa...
            // actualizar posicion, checar colisiones y pintar.
            if(iVidas > 0 && !bPausa) {
                actualiza();
                checaColision();
                repaint();
            }
            
            // Si no hay vidas o esta en pausa, solo pintas
            if(iVidas == 0 || bPausa) {
                repaint();
            }
            // Mandar los hilos a dormir 20 milisegundos.
            try	{
                // El hilo del juego se duerme.
                Thread.sleep(20);
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
        
        //Si la direccion del personaje es derecha
        if(bD) {
            
            //Si el Jugador esta dentro del JFrame
            if(basJugador.getX() + basJugador.getAncho() < getWidth() - 8) {
                
                //Muevelo a la derecha
                basJugador.setX(basJugador.getX() + 3);
            }
        }
        //Si la direccion del personaje es izquierda
        else if(bI) {
            
            //Si el Jugador esta dentro del JFrame
            if(basJugador.getX() > 8) {
                //Muevelo a la Izquierda
                basJugador.setX(basJugador.getX() - 3);
            }
        }
        
        //Actualiza el Movimiento de los Disparos Existentes
        for(int iI = 0; iI < lklProyectiles.size(); iI++) {
            
            //Obten Instancia de Proyectil de la Lista
            Proyectil pylInstance = (Proyectil) lklProyectiles.get(iI);
            
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
        
        //Actualiza el Movimiento de los Obstaculos Existentes
        for(int iI = 0; iI<lklObstaculos.size();iI++) {
            
            //Obten Instancia de Obstaculo de la Lista
            Base basInstancia = (Base) lklObstaculos.get(iI);
            
            //Altera velocidad cada instante
            int iVel = (int) (Math.random()*3)+7;
            
            //Direcciona la Instancia hacia abajo (Caer)
            //La velocidad depende de las vidas
            basInstancia.setY(basInstancia.getY()+iVel - (iVidas));
        }
    }
    
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision entre objetos
     * 
     */
    public void checaColision(){
        
        //Revisa que los Obstaculos no se salgan del JFrame
        //Si es asi, reposisionalos
        ChecaObstaculosLimites(lklObstaculos);
        
        //Revisa la colision de los Disparos con los limites del applet
        //o la colision con los Obstaculos
        ChecaColisionDisparos(lklProyectiles);
        
        //Revisa la colision del Jugador con los Obstaculos
        ChecaColisionJugador(lklObstaculos);
        
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
        
        if(iVidas > 0) {
            
            if (imaImagenFondo != null) {
                // Dibuja la imagen de fondo
                graDibujo.drawImage(imaImagenFondo, 0, 0, getWidth(),
                        getHeight(), this);
                if(imaImagenVida != null) {
                    for(int iI = 0; iI < iVidas; iI++) {
                        // Dibuja la cantidad de vidas que hay
                        graDibujo.drawImage(imaImagenVida,
                        15 + (imaImagenVida.getWidth(this) + 1) * iI, 35,
                        imaImagenVida.getWidth(this),
                        imaImagenVida.getHeight(this), this);
                    }
                }
                graDibujo.setColor(Color.white);
                graDibujo.drawString("Score: " + iScore, 15, 80);
            } // si no se ha cargado se dibuja un mensaje
            else {
                    //Da un mensaje mientras se carga el dibujo	
                    graDibujo.drawString("No se cargo la imagen..", 40, 40);
            }
            
            if(basJugador != null) {
                
                //Dibuja al Principal
                basJugador.paint(graDibujo,this);
            }
            
            //Si la Lista de Disparos no esta vacia
            if(lklProyectiles != null) {
                
                //Dibuja todos los Disparos
                for(int iI = 0; iI<lklProyectiles.size();iI++) {
                    
                    //Tomar Instancia de la Lista
                    Proyectil pylInstancia = (Proyectil) lklProyectiles.get(iI);
                    
                    //Dibuja la Instancia
                    pylInstancia.paint(graDibujo, this);
                    
                }
            }
            
            //Si la Lista de Disparos no esta vacia
            if(lklObstaculos != null) {
                
                //Dibuja todos los Obstaculos
                for(int iI = 0; iI<lklObstaculos.size();iI++) {
                    
                    //Tomar Instancia de la Lista
                    Base basInstancia = (Base) lklObstaculos.get(iI);
                    
                    //Dibuja la Instancia
                    basInstancia.paint(graDibujo, this);
                    
                }
            }       
        }
        else if(iVidas == 0){
            if(imaGameOver != null)
            // Dibuja la imagen de fondo
            graDibujo.drawImage(imaGameOver, 0, 0, getWidth(),
                    getHeight(), this);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(iVidas > 0) {
            //Si el Usuario presiona la tecla Derecha
            if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT ) 
            {
                bI = false; // Desactivar izquierda
                bD = true; // Activar derecha
            }
            //Si el Usuario presiona la tecla de la Izquierda
            if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {

                bD = false; // Desactivar derecha
                bI = true; // Activar izquierda
            }
            if(!bPausa) {
                //Si el Usuario presiona la tecla de Disparo Vertical
                //y puede disparar
                if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE &&
                        iJustShoot != 3) {
                    //Remueve el privilegio para disparar
                    //hasta que el usuario suelte las teclas o le pique a otro
                    iJustShoot = 3;

                    //Crea el disparo con direccion en Vertical '1'
                    CrearDisparo(1);
                }

                //Si el Usuario presiona la tecla de Disparo 45 grados
                // a la izquierda y puede disparar
                if(keyEvent.getKeyCode() == KeyEvent.VK_A && iJustShoot != 1) {

                    //Remueve el privilegio para disparar
                    //hasta que el usuario suelte las teclas o le pique a otro
                    iJustShoot = 1;

                    //Crea el disparo con direccion 45 grados a la izquierda '2'
                    CrearDisparo(2);
                }

                //Si el Usuario presiona la tecla de Disparo 45 grados
                // a la derecha y puede disparar
                if(keyEvent.getKeyCode() == KeyEvent.VK_S && iJustShoot != 2) {

                    //Remueve el privilegio para disparar
                    //hasta que el usuario suelte las teclas o le pique a otro
                    iJustShoot = 2;

                    //Crea el disparo con direccion 45 grados a la izquierda '3'
                    CrearDisparo(3);
                }
            }
        }
        if(iVidas == 0) { // Si no hay vidas solo sirve la tecla reinicio
            if(keyEvent.getKeyCode() == KeyEvent.VK_R) {
                init();
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        
        // Si esta en pausa uso la tecla de pausa para quitar pausa
        if(keyEvent.getKeyCode() == KeyEvent.VK_P) {
            bPausa = !bPausa;
        }
        //Si el Usuario solto las teclas de Movimiento
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Desactiva derecha
            bD = false;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            // Desactiva izquierda
            bI = false;
        }
        if(!bPausa) { // Generar disparos si no esta en pausa
            if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE && iJustShoot == 3) {
                //Remueve el privilegio para disparar
                //hasta que el usuario suelte las teclas o le pique a otro
                iJustShoot = 0;
            }

            //Si el Usuario presiona la tecla de Disparo 45 grados
            // a la izquierda y puede disparar
            if(keyEvent.getKeyCode() == KeyEvent.VK_A && iJustShoot == 1) {

                //Remueve el privilegio para disparar
                //hasta que el usuario suelte las teclas o le pique a otro
                iJustShoot = 0;
            }

            //Si el Usuario presiona la tecla de Disparo 45 grados
            // a la derecha y puede disparar
            if(keyEvent.getKeyCode() == KeyEvent.VK_S && iJustShoot == 2) {

                //Remueve el privilegio para disparar
                //hasta que el usuario suelte las teclas o le pique a otro
                iJustShoot = 0;
            }
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
        lklProyectiles.addLast(pylDisparo);
    }
    
    /**
     * PosicionaTodosProyectiles
     * 
     * Metodo que Posiciona Todos los Proyectiles
     * @param lklProyectiles Es la <code> Lista Encadenada</code> de proyectiles
     */
    void PosicionaTodosObstaculos (LinkedList lklScan) {
        
        for(int iI = 0; iI < lklScan.size(); iI++) {
            
            Base basInstancia = (Base) lklScan.get(iI);
            
            PosicionaObstaculo(basInstancia);
        }
    }
    
    /**
     * PosicionaObstaculo
     * 
     * Metodo que Posiciona un Obstaculo
     * @param basObj Es el <code> Objeto Base</code> a reposicionar
     */
    void PosicionaObstaculo (Base basObj) {
        
        int iRandomX = (int) (40 + Math.random() * (getWidth() - 80));
        
        basObj.setX(iRandomX);
        basObj.setY((int)(0 - Math.random() * getHeight()));
    }
    
    /**
     * CreaObstaculos
     * 
     * Metodo que crea los Obstaculos y los agrega a la lista encadenada
     * @param lklProyectiles Es la <code> Lista Encadenada</code> de Obstaculos
     */
    void CreaObstaculos (LinkedList lklScan) {
        
        int iRandCant = (int) (Math.random() * 6) + 10;
        
        for(int iI=0; iI < iRandCant; iI++) {
            Base basInstancia = new Base (0,0,imaObstaculo);
            lklScan.addLast(basInstancia);
        }
    }
    
    /**
     * ChecaObstaculosLimites
     * 
     * Metodo que checa que los obstaculos no se salgan del Jframe
     * @param lklProyectiles es la <code> Lista Encadenada</code> de Obstaculos
     */
    void ChecaObstaculosLimites (LinkedList lklProyectiles) {
        
        //Checar todos los Obstaculos de la lista
        for(int iI=0; iI<lklProyectiles.size();iI++) {
            
            //Toma una instancia de la lista
            Base basInstancia = (Base) lklProyectiles.get(iI);
            
            //Checar que la Instancia de Obstaculo no haya llegado al fondo
            if(basInstancia.getY()>getHeight()) {
                
                //Si llego al fondo, reposicionala
                PosicionaObstaculo(basInstancia);
            }
            
        }    
    }
    
    /**
     * ChecaColisionDisparos
     * 
     * Metodo que checa que revisa las colisiones de los disparos
     * Con los limites del JFrame y con los Obstaculos
     * 
     * @param lklProyectiles es la <code> Lista Encadenada</code> de Disparos
     */
    void ChecaColisionDisparos (LinkedList lklProyectiles) {
        
        for(int iI = 0; iI<lklProyectiles.size(); iI++) {
            
            //Tomar instancia de Proyectil de la lista
            Proyectil pylInstancia =  (Proyectil) lklProyectiles.get(iI);
            
            //Si el Proyectil se sale del JFrame
            if(pylInstancia.getX() < 0 ||
                    pylInstancia.getX() > getWidth() || 
                    pylInstancia.getY() < 0 ) {
                
                //Borra el Proyectil
                lklProyectiles.remove(iI);
            }
            
            //Checar si el Proyectil Choca con algun Obstaculo
            for(int iJ = 0; iJ < lklObstaculos.size(); iJ++) {
                
                //Tomar una instancia de la lista de Obstaculos
                Base basInstancia = (Base) lklObstaculos.get(iJ);
                
                //Si una instancia de Proyectil colisiona con una
                //una instancia de Obstaculo
                if(pylInstancia.colisiona(basInstancia)) {
                    
                    //Reposiciona el Obstaculo
                    PosicionaObstaculo(basInstancia);
                    
                    //Borra el Proyectil
                    lklProyectiles.remove(iI);
                   
                    //Ganas 10 puntos
                    iScore += 10;
                    
                    //Reproduce sonido
                    Derretir.play();
                }
                    
            }
        }
    }
    
    
    
    /**
     * ChecaColisionJugador
     * 
     * Metodo que revisa la colision del Jugador con los
     * Con los Obstaculos
     * 
     * @param lklProyectiles es la <code> Lista Encadenada</code> de Obstaculos
     */ 
    void ChecaColisionJugador (LinkedList lklProyectiles) {
        
        for(int iI=0; iI < lklProyectiles.size(); iI++) {
            
            Base basInstancia = (Base) lklProyectiles.get(iI);
            
            //Si un Obstaculo Choco con el Jugador
            if(basJugador.colisiona(basInstancia)) {
                
                //Reposiciona el Obstaculo
                PosicionaObstaculo(basInstancia);
                
                //Actualiza el Contador de Colisiones
                iContCol++;
                
                //Checa si hay que eliminar una vida
                if(iContCol>=5) {
                    
                    //Reduce Vidas en 1
                    iVidas--;
                    
                    //Resetea Contador
                    iContCol = 0;
                }
                
                // Pierde 1 punto
                iScore--;
                
                // Reproducir sonido
                Boom.play();
            }
        }
        
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
