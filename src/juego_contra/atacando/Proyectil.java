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
public class Proyectil extends Base {
    
    private int iDireccion;
    
    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono a partir de una imagen
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * @param iDireccion es la <code>Direccion</code> del objeto.
     * 
     */
    public Proyectil(int iX, int iY, Image imaImagen, int iDireccion) {
        super(iX, iY, imaImagen);
        this.iDireccion = iDireccion;
    }
    
    /**
     * setDireccion
     * 
     * Metodo modificador usado para cambiar la direccion del
     * Proyectil
     * 
     * @param iDireccion es la <code>Direccion</code> del proyectil.
     * 
     */
    
    void setDireccion (int iDireccion) {
        
        this.iDireccion = iDireccion;
    }
    
    /**
     * getDireccion
     * 
     * Metodo de acceso que regresa la direccion del Proyectil. 
     * 
     * @return iDireccion es la <code>Direccion</code> del Proyectil.
     * 
     */
    
    int getDireccion () {
        
        return this.iDireccion;
    }
            
}
