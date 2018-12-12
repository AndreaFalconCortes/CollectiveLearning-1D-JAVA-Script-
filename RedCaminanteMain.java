/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collen1d;

import java.util.Random;

/**
 *
 * @author andreafalconcortes
 */
 
//Main
 
public class RedCaminanteMain {

    public static void main(String[] args) {

        int a = -1000;                        // position of the left frontier of the lattice
        int b = 1000;                         // position of the right frontier of the lattice 
        double gamma = 0.9;                   // gamma_max
        double d = 0.025;                     // target density in the lattice
            
            int[] rv = HVectorInt.hvectorint(a, b, 1);    // vector from a to b 

            int indice = 0;                               // variable that saves the index of the best target

            State[] s;                                    // vector of type State with length b-a+1
            s = new State[b - a + 1];
            
            int cantar = 0;                               // variable that saves the total number of targets in the lattice

            Random rand = new Random();                   // number of type Random

            for (int i = 0; i < s.length; i++) {          // fill the lattice
                double rnd = rand.nextDouble();
                if (rnd < d) {                            // with probability d we put a target in the position i+a of the lattice
                    cantar++;
                    double rndg = rand.nextDouble() * gamma;   // the target has a random weight between zero and gamma_max
                    s[i] = new State(i + a, 1, rndg, 0.0);         
                    if (s[i].gamma > s[indice].gamma) {
                        indice = i;
                    } else {
                        indice = indice;
                    }
                } else {
                    s[i] = new State(i + a, 0, 0.0, 0.0);
                }
            }

                    int camN = 200;                       // total walkers in the lattice               
                    double sc = 1.0;                      // conection probability of the ER network or exponent of the power law distribution for de BA network              

                    double q = 0.1;                       // memory use rate              
                    double rho = 0.5;                     // transfer information rate                 

                    int t = 10000;                        // tatal time for the search
                    int n = 1000;                         // total number for the runs over which averaging

                    int radio = 5;                        // ratio for the eculidian neighborhood 

                    int[] ct = Permuta.permuta(rv, rv.length);                // random initial positions for the walkers
                    Walk w = new Walk(camN, ct, sc, a, b, t, gamma, q, rho, d, s, radio);   // create the object Walk

                    for (int j = 1; j <= n; j++) {
                        w.inicia();                  // begin the dynamic
                        w.reinicia();                // reboot initial conditions (walkers positions)
                    }

                    for (int k = 0; k < w.disNr.length; k++) {
                        w.disNr[k] = w.disNr[k] / n;  // average neighbors per walker in time
                    }
                    
                    double ptbest = w.s[indice].vis / (((long) (1)) * n * camN);  //FOP of the best target
                    
                    State[] star;
                    star = new State[cantar];

                    int cont = 0;
                    while (cont < star.length) {
                        for (int l = 0; l < w.s.length; l++) {
                            if (w.s[l].target == 1) {
                                star[cont] = new State(w.s[l].p, 1, w.s[l].gamma, w.s[l].vis / (((long) (1)) * n * camN));    //FOP of the targets in the lattice
                                cont++;
                            }
                        }
                    }

    }
}
