/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roughnessanalysistool;

import static java.lang.Math.rint;
import static java.lang.Math.sqrt;

/**
 * The class is derived from class BinOption and defines Square Root Choice
 * @author Simon
 */
public class SquareRoot extends BinOption{

    /* constructor */
    SquareRoot(double[] data){
        super(data);
    }
    
    @Override
    public int NoOfBin(){
        return (int)rint(sqrt((double)n));
    }
    
}