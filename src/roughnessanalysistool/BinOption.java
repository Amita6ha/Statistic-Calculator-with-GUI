/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roughnessanalysistool;

/**
 * Parent class to define ways to calculate number of bin
 * @author Simon
 */
public abstract class BinOption {
    
    protected int n;
    
    public static enum Option{SquareRoot,SturgeFormula,RiceRule};
    
    /* constructor */
    public BinOption(double[] data){
        n=data.length;
    };
    
    /* Method to get protected member. */
    public int getBinNo(){
        return NoOfBin();
    }
    
    /* Formula used to calculate number of bin. */
    public abstract int NoOfBin();
    
}