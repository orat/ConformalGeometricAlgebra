package de.orat.math.cga.spi.sym.util;

/**
  * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SparsityCGAKVector extends Sparsity {
    
    /**
     * Creates a sparse definition for a colum k-vector.
     * 
     * @param basisBladeNames 
     * @param grade grade of the k-vector
     */
    public SparsityCGAKVector(String[] basisBladeNames, int grade){
        super(32, 1, new int[]{0,colind(grade)}, rows(basisBladeNames, 
              grade, colind(grade)));
    }
    
    private static int colind(int grade){
        int result;
        switch (grade){
            case 0:
            case 5:
                result = 1;
                break;
            case 1:
                result = 5;
                break;
            case 2:
            case 3:
                result = 10;
                break;
            case 4:
                result = 5;
                break;
            default:
                throw new IllegalArgumentException("In CGA only 0<=grade<=5 is possible!");
        }
        return result;
    }
   
    private static int[] rows(String[] basisBladeNames, int grade, int colind){
        int[] rows = new int[colind];
        int charcount = grade;
        if (charcount >0) charcount++;
        int j=0;
        for (int i=0;i<32;i++){
            if (basisBladeNames[i].length() == charcount) rows[j] =i;
        }
        return rows;
    }
}
