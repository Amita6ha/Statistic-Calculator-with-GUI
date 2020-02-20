/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roughnessanalysistool;

import static java.lang.Math.pow;
import static java.lang.Math.rint;

/**
 * The class is derived from class BinOption and defines Rice Rule
 * @author Simon
 */
public class RiceRule extends BinOption{
    
    /* constructor */
    RiceRule(double[] data){
        super(data);
    }
    
    @Override
    public int NoOfBin(){
        return (int)rint(2.*pow((double)n,1./3.));
    }
    
}