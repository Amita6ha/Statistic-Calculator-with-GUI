/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roughnessanalysistool;

import static java.lang.Math.log;
import static java.lang.Math.rint;

/**
 * The class is derived from class BinOption and defines Sturge's Formula
 * @author Simon
 */
public class SturgeFormula extends BinOption{
    
    /* constructor */
    SturgeFormula(double[] data){
        super(data);
    }
    
    @Override
    public int NoOfBin(){
        return (int)rint(3.3*log((double)n)+1);
    }
    
}