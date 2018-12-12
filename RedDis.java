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
 
// This class RedDis return the information transfer network that follows a given degree distribution 
// Implemetation of the algorithm given in ref.[55] 
 
public class RedDis {

    int camN;                                    // number of the walkers (nodes) in the network
    Caminante[] nodos;                           // vector with elements Caminante 
    Random r;                                    // number of type Random
    
    public RedDis(int camN) {
        this.camN = camN;
        nodos = new Caminante[camN];
        r = new Random();
    }

    public void creaRed(int t, int ct[], double q, double rho, double a) {     // method that recives the total time of search, walkers aleatory initial positions
                                                                               // memory use rate, transfer information rate and exponent of the power law distribution

        int sumed = -1;                                                        // auxiliary acumulator that let us to do a even number of edges 

        while (sumed % 2 != 0) {
            
            sumed = 0;

            for (int i = 0; i < nodos.length; i++) { 
                int ed = generadorpowerlaw(a, 1, camN);                        // number that follows a power law distribution with exponent a                          
                nodos[i] = new Caminante(i, ct[i], q, rho, ed, t);             // fill the vector of type Caminante
                sumed = sumed + ed;

            }
        }
        
        for (int i = 0; i < nodos.length; i++) {
            Caminante c = nodos[i];
            ArrayList<Caminante> vecinos = c.vecinos;                          // list that save the neighbors for each walker

            for (int j = 0; j < nodos.length; j++) {

                if (c.id != nodos[j].id && c.edges > 0 && nodos[j].edges > 0 && vecinos.contains(nodos[j]) == false) {
                    vecinos.add(nodos[j]);
                    nodos[j].vecinos.add(c);                                   // the walker i connects to walker j if and only if both have available edges and
                    c.edges--;                                                 // if there are not alredy connected
                    nodos[j].edges--;                                          // if they connect, then the number of edges of both decrease in one
                }
            }
        }

    }

    public void printRed() {                                                    // method for print the network
        for (Caminante c : nodos) {
            System.out.print(c.id + " - [");

            for (Caminante w : c.vecinos) {
                System.out.print(w.id + ",");
            }
            System.out.println("] ,  " + c.x +  ","  + c.edges);
        }
    }

    public double gradoPromedio() {                                             // method for compute the mean degree of the network

        double prom = 0;

        for (Caminante c : nodos) {
            prom += c.vecinos.size();
        }

        return prom / nodos.length;

    }

    public int[] propocionGrado(int camN, Caminante[] n) {                      // method that approximates the degree distribution of the network

        int[] prop = new int[n.length];

        for (int i = 0; i < camN - 1; i++) {
            for (int j = 0; j < n.length; j++) {

                if (n[j].vecinos.size() == i) {
                    prop[i]++;
                }

            }

        }

        return prop;

    }

    public int generadorpowerlaw(double a, double xm, double xM) {             // method tha generates a number that follows a power law distribution
        Random r = new Random();
        double y = r.nextDouble();
        double exp = 1 - a;
        double xmprim = Math.pow(xm, exp);
        double xMprim = Math.pow(xM, exp);
        double yprim = xmprim - y * (xmprim - xMprim);
        double aprim = 1 / (1 - a);
        int x = (int) (Math.pow(yprim, aprim));
        return x;
    }
}
