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
 
// This class State create an object of type State that includes all the atributes of the environment
 
public class State {
        
    public int p;                               // position in the lattice
    public int target;                          // binary variable 0=no target 1=target
    public double gamma;                        // if the position is a target gamma will be its weight
    public double vis;                          // FOP of the position p
    
    public int labelcalc;                       // if p is a target, labelcalc will be its compute rank
    public int labelreal;                       // if p is a target, labelreal will be its real rank 
    
    public State (int p , int target , double gamma , double vis){
        
        this.p = p;
        this.target = target;
        this.gamma = gamma;
        this.vis = vis;
    }
    
    @Override
    public String toString(){
        return p + " ";
    }
    
}
