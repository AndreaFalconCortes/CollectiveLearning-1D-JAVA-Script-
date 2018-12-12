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
 
//This class Walk help to create the object Walk which methods and attributes help us to update the swarm following the dynamics exposed in the paper
 
public class Walk {

    int t;         // total time of the search                
    int tpr;       // auxiliary variable to update the position of the walker by use of individual memory or by transfer information              

    double gamma;  // auxiliary variable to safe the value gamma_i of each target
    double q;      // rate of use of individual memory
    double rho;    // rate of tranfer information
    

    double d;      // average density of targets in the environment

    double cp;     // conection probability for the ER network or exponent of the power law ditribution for BA network

    int camN;      // amount of walkers in the environment               
    int[] ct;                      

    State[] s;     // object of type State that save all the environment features
    RedCaminante c;        // object of type RedCaminante that save the sructure of the information transfer network (ER network)
//    RedDis c;            // object of type RedDis that save the sructure of the information transfer network (BA network)
    double[] distar;       // vector that helps to measure the distance between walkrs at each time step
    double[] disNr;        // vector that save the average euclidian neighbors per walker at certain time

    long visitas;          // counter that save the total number of visits to every target        
    int a;                 // left frontier of the lattice       
    int b;                 // right frontier of the lattice

    int camtemp;           // auxiliary varible that let us update the walker position if it leaves the lattice

    int radio;             // ratio of the walkers euclidian neighborhood 
    double Nr;             // number of euclidian neighbors for each walker

    Random r = new Random();

    public Walk(int camN, int[] ct, double cp, int a, int b, int t, double gamma, double q, double rho, double d, State[] s, int radio) {

        this.camN = camN;

        this.ct = ct;
        this.a = a;
        this.b = b;
        this.t = t;
        this.s = s;

        this.gamma = gamma;
        this.q = q;
        this.rho = rho;

        this.d = d;
        this.cp = cp;

        this.radio = radio;

        c = new RedCaminante(camN, cp);
        c.creaRedAleatoria(t, ct, q, rho);
//        c = new RedDis(camN);
//        c.creaRed(t, ct, q, rho, cp);

        distar = new double[t + 1];
        disNr = new double[100];

        reinicia();

    }

    public void step(int tempo, int cj) {          // method that updates the positions of the walker cj

        int bandera = 0;                           // binary variable 0=no target 1=target
        double g = 0; 

        if (s[c.nodos[cj].x - a].target == 1) {   
            bandera = 1;                           // if the walker is in one target we update the value of bandera
            visitas++;
            g = s[c.nodos[cj].x - a].gamma;        // g is equal to the weight of the target 
        }

        if (bandera == 1) {                        // if the walker is in a target:
            double rnd = r.nextDouble();
            if (rnd < g) {                         // with probability g stays in it  
                c.nodos[cj].x = c.nodos[cj].x;
            } else {                           
                rnd = r.nextDouble();
                if (rnd > c.nodos[cj].q) {         // with probability (1-q)*(1-g) moves in a random way
                    int dl = 1;
                    rnd = r.nextDouble();
                    if (rnd > 0.5) {
                        dl = -1;
                    }
                    c.nodos[cj].x = c.nodos[cj].x + dl;
                    camtemp = c.nodos[cj].pos[tempo].x;

                    while (c.nodos[cj].x > b || c.nodos[cj].x < a) {  // if the random movement take out the walker of the lattice try again     
                        dl = 1;
                        rnd = r.nextDouble();
                        if (rnd > 0.5) {
                            dl = -1;
                        }
                        c.nodos[cj].x = camtemp + dl;
                    }
                } else {
                    rnd = r.nextDouble();
                    if (rnd < c.nodos[cj].rho && c.nodos[cj].vecinos.size() != 0) {        // with probability (1-g)*q*rho chose one of its neighbors for "communicate"

                        int rndpos = (int) (r.nextDouble() * (c.nodos[cj].vecinos.size() - 1));
                        tpr = (int) (r.nextDouble() * (tempo+1));
                        c.nodos[cj].x = c.nodos[cj].vecinos.get(rndpos).pos[tpr].x;

                    } else {                                                                // with probability (1-g)*(1-rho)*q use its own memory
                        rnd = r.nextDouble();
                        tpr = (int) (rnd * (tempo+1));
                        c.nodos[cj].x = c.nodos[cj].pos[tpr].x;
                    }
                }
            }
        } else {                                                            // if the walker is not on a target:
            double rnd = r.nextDouble();
            if (rnd > c.nodos[cj].q) {                                      // with probability (1-q) moves in a random way 
                int dl = 1;
                rnd = r.nextDouble();
                if (rnd > 0.5) {
                    dl = -1;
                }
                c.nodos[cj].x = c.nodos[cj].x + dl;
                camtemp = c.nodos[cj].pos[tempo].x;
 
                while (c.nodos[cj].x > b || c.nodos[cj].x < a) {            // if the random movement take out the walker of the lattice try again 
                    dl = 1;
                    rnd = r.nextDouble();
                    if (rnd > 0.5) {
                        dl = -1;
                    }
                    c.nodos[cj].x = camtemp + dl;
                }
            } else {
                rnd = r.nextDouble();
                if (rnd < c.nodos[cj].rho && c.nodos[cj].vecinos.size() != 0) {            // with probability q*rho chose one of its neighbors for "communicate"

                    int rndpos = (int) (r.nextDouble() * (c.nodos[cj].vecinos.size() - 1));
                    tpr = (int) (r.nextDouble() * (tempo+1));
                    c.nodos[cj].x = c.nodos[cj].vecinos.get(rndpos).pos[tpr].x;

                } else {                                                                   // with probability (1-rho)*q use its own memory 
                    rnd = r.nextDouble();
                    tpr = (int) (rnd * (tempo+1));
                    c.nodos[cj].x = c.nodos[cj].pos[tpr].x;
                }
            }
        }
        
      c.nodos[cj].pos[tempo + 1] = new Posicion(c.nodos[cj].x);                            // update the position

    }

    public void inicia() {

        for (int m = 0; m < c.nodos.length; m++) {
            c.nodos[m].pos[0] = new Posicion(c.nodos[m].x);                                // initial positions of the walkers at t=0
        }

        int cont = 0;

        for (int l = 0; l < t; l++) {
            Nr = 0;
            for (int j = 0; j < c.nodos.length; j++) {                                     // update the position of the walker j
                step(l, j);
                if (l == t - 1) {                                                          // at the end save the FOP 
                    s[c.nodos[j].pos[l + 1].x - a].vis++;
                }

            }

            if (l % (t/10) == 0) {                                                       
                for (int i = 0; i < c.nodos.length; i++) {
                    for (int m = 0; m < c.nodos.length; m++) {
                        double dis = (double) (Math.abs(c.nodos[i].pos[l].x - c.nodos[m].pos[l].x));   
                        if (dis < radio && i != m) {   // if walker m is inside of the neighborhood of the walker i then:
                            Nr++;                      // Nr increase in one
                        }
                    }
                }
                disNr[cont] = disNr[cont] + (Nr / camN);     // at time cont each walker has in average (Nr/camN) neighbors
                cont++;
            }

        }

    }

    public void reinicia() {                                 // method that reboot the initial positions of the walkers (for to do multiple runs and averaging)
        visitas = 0;

        int[] rv = HVectorInt.hvectorint(a, b, 1);    
        ct = Permuta.permuta(rv, rv.length);

        for (int i = 0; i < c.nodos.length; i++) {
            c.nodos[i].x = ct[i];
            c.nodos[i].pos = new Posicion[t + 1];
        }

    }

}
