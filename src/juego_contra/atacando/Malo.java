/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego_contra.atacando;

import java.awt.Image;

/**
 *
 * @author angtr
 */
public class Malo extends Base {
    
    private boolean bPersigue;// si persigue o no
     
    public Malo(int iX, int iY, Image imaImagen,boolean bPersigue) {
        
        super(iX, iY, imaImagen);
        this.bPersigue = bPersigue;
    }
    
    public boolean siPersigue() {
        return bPersigue;
    }
    
    public void setPersigue (boolean bPer) {
        
        bPersigue = bPer;
    }
}
