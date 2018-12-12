/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collen1d;

/**
 *
 * @author andreafalconcortes
 */
 
//Class that creates an objetc Posicion which atributes are the position x of a walker 
 
public class Posicion {   

    public int x;
    
    public Posicion(int x) {
        
        this.x = x;
    }
    
    @Override
    public String toString (){
        return x + " ";
    }
}
