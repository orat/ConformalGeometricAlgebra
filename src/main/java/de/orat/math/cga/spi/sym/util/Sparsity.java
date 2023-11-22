package de.orat.math.cga.spi.sym.util;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 * 
 * TODO
 * ins package GeometricAlgebra verschieben oder sogar in eines von dem GeometricAlgebra
 * abhängt...
 */
public class Sparsity {
    
    final int n_row;
    final int n_col;
    final int[] colind; // cumulative count of non zeros for each column, started with 0
    final int[] row; // row for each non zero value
    
    public Sparsity(int n_row, int n_col, int[] colind, int[] row){
        this.n_row = n_row;
        this.n_col = n_col;
        this.colind = colind;
        this.row = row;
    }
    
    /*public Sparsity(double[][] m){
        for (int i=0;i<m.length;i++){
            for (int j=0;j<m[i].length;j++){
                //TODO
                // Datenstruktur aufbauen
            }
        }
    }*/
}
