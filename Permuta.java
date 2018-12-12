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
 
// This class Permuta is equivalent to generate aleatory integer numbers without repetition in a given interval.
// This class is use to generate the aleatory initial positions fo the walkers inside of a given lattice 

public class Permuta {

    public static int[] permuta(int[] vec, int x) {         // method that recives a vector vec and its length 

        Random r = new Random();                            // r is a number of type Random

        int[] res = new int[x];                             // auxiliary vector of length x

        for (int j = 0; j < vec.length; j++) {              // this for exchanges the elements of vec  in a ranodom way
            int m = r.nextInt(vec.length - j) + j;
            int temp = vec[j];
            vec[j] = vec[m];
            vec[m] = temp;
        }

        for (int k = 0; k < res.length; k++) {              // to avoid overwriting we creat a vector res that is the resultant vector after the aleatorization
            res[k] = vec[k];
        }

        return res;
    }

}
