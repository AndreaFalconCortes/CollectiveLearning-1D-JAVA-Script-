/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collen1d;

import java.util.*;

/**
 *
 * @author andrafalconcortes
 */
 
//This class Caminante create the objets Caminante that determines all the attributes for each walker in the swarm
 
public class Caminante {
    
    
    public int id;                         //walker id
    public int x;                          //initial position
    public double q;                       //individual memory use rate (same for all the walkers)
    public double rho;                     //transfer information rate  (same for all the walkers)
    public Posicion [] pos;                //temporal vector of the class Position to save the walkers positions
    public ArrayList<Caminante> vecinos;   //array to save the ids of the walkers neighbors
    
    public int edges;                      //total number of neighbors for a given network distribution
    
    public Caminante(int id, int x, double q, double rho, int edges, int walkLong){
        this.id = id;
        this.x = x;
        this.q = q;
        this.rho = rho;
        this.edges = edges;
        this.pos = new Posicion[walkLong+1];
        vecinos = new ArrayList<>();        
    }
}
