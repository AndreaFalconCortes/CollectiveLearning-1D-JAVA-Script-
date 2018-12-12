/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collen1d;

import java.util.*;

/**
 *
 * @author andreafalconcortes
 */
 
//This class RedCaminante creates the object RedCaminnte that defines the information transfer network between the walkers
 
public class RedCaminante {

    int camN;                     // number of walkers in the network
    Caminante[] nodos;            // vector of type Caminante
    Random r;                     // number of type Random
    double s;                     // network conection probability

    public RedCaminante(int camN, double s) {        
        this.camN = camN;
        this.s = s;
        nodos = new Caminante[camN];
        r = new Random();
    }

    public void creaRedAleatoria(int t, int[] ct, double q, double rho) {     // method that recives the total time of the search, initial aleatory positions of the walkers,           
                                                                              // memory use rate and tranfer information rate

        for (int i = 0; i < nodos.length; i++) {
            nodos[i] = new Caminante(i, ct[i], q, rho, 0, t);                 // create camN objetcs of type Caminante  
        }

        for (int i = 0; i < nodos.length; i++) {
            Caminante c = nodos[i];
            ArrayList<Caminante> vecinos = c.vecinos;                          // list of with elements of type Caminante that saves the neighbors of each walker

            for (int j = i + 1; j < nodos.length; j++) {
                double al = r.nextDouble();

                if (al < s) {                                                  // with probability s the walker i connects to walker j. There is not self conections
                    vecinos.add(nodos[j]);
                    nodos[j].vecinos.add(c);
                }
            }
        }

    }

    public void printRed() {                                                    // method that prints the network
        for (Caminante c : nodos) {
            System.out.print(c.id + " - [");

            for (Caminante w : c.vecinos) {
                System.out.print(w.id + ",");
            }
            System.out.println("] ,  " + c.x + " , " + c.q + " , " + c.rho);
        }
    }

    public double gradoPromedio() {                                             // method that compute the mean degree of the network

        double prom = 0;

        for (Caminante c : nodos) {
            prom += c.vecinos.size();
        }

        return prom / nodos.length;

    }

}
