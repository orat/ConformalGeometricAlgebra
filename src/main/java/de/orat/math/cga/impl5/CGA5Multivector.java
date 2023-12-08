package de.orat.math.cga.impl5;

import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.orat.math.cga.impl2.*;
import de.orat.math.cga.api.CGAMultivector;
import de.orat.math.cga.impl2.generated.CGA;
import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cgacasadi.CGABasisBladeSparsity;
import de.orat.math.cgacasadi.CGACayleyTable;
import static de.orat.math.cgacasadi.CGACayleyTable.CGABasisBladeNames;
import de.orat.math.cgacasadi.CGACayleyTableGeometricProduct;
import de.orat.math.cgacasadi.CGAKVectorSparsity;
import de.orat.math.cgacasadi.CGAMultivectorSparsity;
import de.orat.math.cgacasadi.CasADiUtil;
import de.orat.math.cgacasadi.DenseCGAColumnVector;
import static de.orat.math.ga.basis.InnerProductTypes.LEFT_CONTRACTION;
import static de.orat.math.ga.basis.InnerProductTypes.RIGHT_CONTRACTION;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.MatrixSparsity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGA5Multivector implements iCGAMultivector {
    
    private CGAMultivectorSparsity sparsity; 
    private DM dm; 
    
    final static CGACayleyTableGeometricProduct baseCayleyTable = CGACayleyTableGeometricProduct.instance();
    
    //SparseCGASymbolicMultivector 
            
    //static CGA5Multivector defaultInstance = new CGA5Multivector(0,0d);
         
    // creation of a default instance
    public CGA5Multivector(){}
    
    /**
     * 
     * @param sparsity
     * @param values nonzeros only
     */
    public CGA5Multivector(CGAMultivectorSparsity sparsity, double[] values){
        this.sparsity = sparsity;
        dm = CasADiUtil.toDM(sparsity, values);
    }
    
    public CGA5Multivector(DM dm, CGAMultivectorSparsity sparsity){
        this.sparsity = sparsity;
        this.dm = dm;
    }
    
    public CGA5Multivector(int idx, double value){
        sparsity = new CGABasisBladeSparsity(CGABasisBladeNames, idx);
        //FIXME wozu dummy?
        boolean dummy = true;
        // non-symbolic DMs have no name
        dm = new DM(CasADiUtil.toCasADiSparsity(sparsity), value, dummy);
    }
    
    private static String getBasisBladeName(int idx){
        return CGACayleyTableGeometricProduct.getBasisBladeName(idx);
    }
    
    @Override
    public String toString(){
        return toString(this.basisBladeNames());
    }
    
    /**
     * @param bvNames
     * @return 
     */
    public String toString(String[] bvNames) {
        boolean empty = true;
        StdVectorDouble values = dm.nonzeros();
        for (int i=0;i<values.size();i++){
            if (values.get(i) != 0) {
                empty = false;
                break;
            }
        }
        if (empty) {
            return "0";
        } else {
            StringBuilder result = new StringBuilder();
            boolean firstSummand = true;
            int[] row = sparsity.getrow();
        
            for (int i=0; i<row.length; i++) {
                double value = values.get(i);
                String S = Double.toString(value);
                if  (Math.abs(value) > CGAMultivector.eps){
                    if (i == 0) {
                        result.append(S);
                    } else if (S.charAt(0) == '-') {
                        if (!firstSummand) result.append(" - ");
                        result.append(S.substring(1));
                        result.append("*");
                        result.append(bvNames[i]);
                        
                    } else {
                        if (!firstSummand) result.append(" + ");
                        result.append(S);
                        result.append("*");
                        result.append(bvNames[i]);
                    }
                    firstSummand = false;
                }
            }
            return result.toString();
        }
    }
    
    @Override
    public CGA5Multivector op(iCGAMultivector b){
        throw new RuntimeException("wedge - not yet implemented!");
        //return new CGA5Multivector(binop_Wedge(this, (CGA) b));
    }
    
    /**
     * Vee product.
     * 
     * Implements the vee product as an optimized shorthand for the dual of 
     * the wedge of the duals.<p>
     * 
     * @param b second argument of the vee product
     * @return vee product
     */
    public CGA5Multivector vee(CGA5Multivector b){
        throw new RuntimeException("vee - not yet implemented!");
        //return new CGA5Multivector(binop_Vee(this,b));
    }
    
    /**
     * Inner/Dot product.
     * 
     * The dot product implemented is the left contraction - without any 
     * extensions or modifications. The geometric meaning is usually formulated
     * as the dot product between a and b gives the orthogonal complement in b of the
     * projection of x onto y.
     * 
     * @param b
     * @return inner (dot) product
     */
    public CGA5Multivector ip(iCGAMultivector b){
        throw new RuntimeException("ip - not yet implemented!");
        //return new CGA5Multivector(CGA.binop_Dot(this, (CGA) b));
    }
    /**
     * Dual operation.
     * 
     * TODO<br>
     * warum funktioniert diese Implementierung nicht. Diese sollte viel performanter
     * sein, als die default impl<p>
     * 
     * @return poincare duality operator
     */
    @Override
    public CGA5Multivector dual(){
        throw new RuntimeException("dual - not yet implemented!");
        //return new CGA5Multivector(CGA.unop_Dual(this));
    }
    
    /**
     * Conjugation.
     * 
     * For Quaternionen:<br>
     * The conjugate is useful because it has the following properties:<p>
     *
     * qa' * qb' = (qb*qa)' In this way we can change the order of the multiplicands.
     * q * q' = a2 + b2 + c2 + d2 = real number. Multiplying a quaternion by its 
     * conjugate gives a real number. This makes the conjugate useful for finding 
     * the multiplicative inverse. For instance, if we are using a quaternion q 
     * to represent a rotation then conj(q) represents the same rotation in the reverse direction.
     * Pout = q * Pin * q' We use this to calculate a rotation transform.<p>
     *
     * For CGA:<p>
     * 
     * This reverses all directions in space<p>
     *
     * A~ denotes the conjugate of A<p>
     *
     * conjugate, reverse and dual are related as follows.<p>
     *
     * A~= (A†)* = (A*)†<p>
     *
     * Identities<p>
     *
     * (A * B)~ = B~* A~<p>
     * 
     * @return Clifford conjugate
     */
    @Override
    public CGA5Multivector conjugate(){
        throw new RuntimeException("conjugate - not yet implemented!");
        //return new CGA5Multivector(super.Conjugate());
    }
    
    /**
     * The reverse function of a multivector reverses the order of its factors, 
     * including the order of the base values within a component. 
     * 
     * The reverse function is denoted by †, so the reversal of A is denoted by A†.<p>
     * 
     * @return reverse the order of the basis blades
     * 
     */
    @Override
    public CGA5Multivector reverse(){
        throw new RuntimeException("reverse - not yet implemented!");
        //return new CGA5Multivector(unop_Reverse(this));
    }
    
    /**
     * a# = sum k=0(N (-1)k a <k> = a<+> - a<-> .
     * 
     * @return main involution
     */
    @Override
    public CGA5Multivector gradeInversion(){
        throw new RuntimeException("gradeInversion - not yet implemented!");
        //return new CGA5Multivector(super.Involute());
    }
 
    @Override
    public int getEStartIndex(){
        return 0; // e4, e5 kommen am Ende daher start bei 0
    }
    
    /**
     * Get the k-vector of the given grade k.
     * 
     * 0-blades are scalars, 1-blades are vectors, 2-blades are bivectors, 
     * 3-blades are threevectors, 4-blades are quad-vectors and 5-blades are
     * called pseudo-scalars.<p>
     * 
     *  "","e1","e2","e3","e4","e5","e12","e13","e14","e15","e23","e24","e25","e34","e35","e45","e123","e124","e125","e134",
     *  "e135","e145","e234","e235","e245","e345","e1234","e1235","e1245","e1345","e2345","e12345"<p>
     *
     * equivalent to k-vector/k-blades<p>
     * 
     * @param grade
     * @return 
     */
    @Override
    public double[] extractCoordinates(int grade){
        if (grade < 0 || grade > 5)
            throw new IllegalArgumentException("Only 0 < grade <= 5 is allowed!");
        int[] neededRows = CGAKVectorSparsity.instance(grade).getrow();
        StdVectorDouble availableValues = dm.nonzeros();
        List<Integer> availableRows = Arrays.stream(sparsity.getrow())     // IntStream
                         .boxed()             // Stream<Integer>
                         .collect(Collectors.toList());
        double[] result = new double[neededRows.length];
        for (int i=0;i<result.length;i++){
            if (availableRows.contains(neededRows[i])){
                result[i] = availableValues.get(i);
            } 
        }
        return result;
    }
    
    /**
     * Set the coordinates of the given grade.
     * 
     * @throws IllegalArgumentException if the given grade does not correspoind to the
     * length of the value array or the given grade does correspond to CGA at all.
     */
    public void setCoordinates(int grade, double[] values){
        if (grade < 0 || grade > 5)
            throw new IllegalArgumentException("Only 0 < grade <= 5 is allowed!");
        
        //TODO
        // Umstellung mit Verwendung von intersect() 
        
        int[] neededRows = CGAKVectorSparsity.instance(grade).getrow();
        if (neededRows.length != values.length){
            throw new IllegalArgumentException("Grade "+String.valueOf(grade)+
                    " does not corresponds to the given values array length of "+
                    String.valueOf(values.length)+"!");
        }
        List<Integer> availableRows = Arrays.stream(sparsity.getrow())     // IntStream
                         .boxed()             // Stream<Integer>
                         .collect(Collectors.toList());
        for (int i=0;i<values.length;i++){
            if (availableRows.contains(neededRows[i])){
                dm.at(neededRows[i]).assign(new DM(values[i]));
            }
        }
    }
    
    /**
     * Grade projection/extraction.
     * 
     * Retrives the k-grade part of the multivector.
     * 
     * @param grade
     * @return k-grade part of the multivector
     * @throws IllegalArgumentException if grade <0 or grade > 5
     */
    @Override
    public CGA5Multivector extractGrade(int grade){
        if (grade > 5 || grade < 0) 
            throw new IllegalArgumentException ("Grade "+String.valueOf(grade)+" not allowed!");
        
        CGAMultivectorSparsity resultSparsity = CGAKVectorSparsity.instance(grade).intersect(sparsity);
        double[] resultValues = new double[resultSparsity.getrow().length];
        StdVectorDouble values = dm.nonzeros();
        int[] indizes = sparsity.getrow();
        for (int i=0;i<resultValues.length;i++){
            resultValues[i] = values.get(indizes[i]);
        }
        return new CGA5Multivector(resultSparsity, resultValues);
    }
    
    /**
     * Set coordinates corresponding to getCoordinates().
     * 
     * @param values 
     * s, e0, e1, e2, e3, einf, e01, e02, e03, e0inf, e12, e13, e1inf, e23, e2inf, e3inf
     * e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf, 
     * e0123, e012inf, e013inf, e023inf, e123inf, 
     * e0123inf
     */
    @Override
    public void setCoordinates(double[] values){
        // interne representation
        // "","e1","e2","e3","e4","e5","e12","e13","e14","e15","e23","e24","e25","e34","e35","e45",
        // startindex 16: "e123","e124","e125","e134","e135","e145","e234","e235","e245","e345",
        // "e1234","e1235","e1245","e1345","e2345",
        // "e12345";
        
        int[] rows = sparsity.getrow();
        for (int i=0;i<rows.length;i++){
            double value = createImplCoordinate(rows[i], values);
            dm.at(i).assign(new DM(value));
        }
    }
    
    /**
     * Create implementation coordinate from standard basis values.
     * 
     * @param index result index
     * @param values
     * @return coordinate for the given coordinate index
     */
    private double createImplCoordinate(int index, double[] values){
        switch (index){
            
            // scalar
            case 0:
                return values[0];

           // 1-vectors
            case 1:
                return values[2];
            case 2:
                return values[3];
            case 3:
                return values[4];
            case 4:
                return 0.5*values[5]-values[1];
            case 5:
                return values[1]+0.5*values[5];

            // 2-vectors
            // "e12","e13","e14","e15","e23","e24","e25","e34","e35","e45"
            case 6:
                return values[10];
            case 7:
                return values[11];
            case 8:
                return 0.5*values[12]+values[6]; // e14 = 0.5e1inf+e01
            case 9:
                return -values[6] + 0.5*values[12]; // e15 = -e01+0.5e1inf
            case 10:
                return values[13]; // e23 
            case 11:
                return values[7]+0.5*values[14]; // e24 = 0.5e2inf+e02 
            case 12:
                return 0.5*values[14]-values[7]; // e25 = 0.5e2inf-e02
            case 13:
                return 0.5*values[15]-values[8]; // e34 = 0.5e3inf+e03
            case 14:
                return 0.5*values[15]-values[8]; // e35 = 0.5e3inf-e03
            case 15:
                return -values[9]; // e45 = -e0inf

            // 3-vectors
            // target: "e123","e124","e125","e134","e135","e145","e234","e235","e245","e345",
            // start 16: e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf
            case 16:
                return values[22]; // e123
            case 17:
                return 0.5*values[23]-values[16]; // e124 = 0.5e12inf-e012
            case 18:
                return 0.5*values[23]+values[16]; // e125 = 0.5e12inf+e012
            case 19:
                return 0.5*values[24]-values[17]; // e134 = 0.5e13inf-e013
            case 20:
                return 0.5*values[24]+values[17]; // e135 = 0.5e13inf+e013
            case 21:
                return values[18]; // e145 = e01inf
            case 22:
                return 0.5*values[25]-values[19]; // e234 = 0.5e23inf-e023
            case 23:
                return values[25]+values[19]; // e235 = e023+e23inf
            case 24:
                return values[20]; // e245 = e02inf
            case 25:
                return values[21]; // e345 = e03inf

            // 4-vectors
            // "e1234","e1235","e1245","e1345","e2345",
            // start bei 26: e0123, e012inf, e013inf, e023inf, e123inf
            case 26:
                return 0.5*values[30]+values[26]; // e1234=0.5e123inf-e0123
            case 27:
                return 0.5*values[30]-values[26]; // e1235=0.5e123inf-e0123
            case 28:
                return -values[27]; // e1245=-e012inf
            case 29:
                return -values[28]; // e1345=-e013inf
            case 30:
                return -values[29]; // e2345=-e023inf

            // pseudoscalar
            //  "e12345"
            case 31:
                return values[31]; // e12345=e0123inf
            default:
                throw new IllegalArgumentException("Unknown index "+String.valueOf(index));
        }
    }
    
    /**
     * Get the specific default coordinates representation.
     * 
     * @return 
     * [s, e0, e1, e2, e3, einf, e01, e02, e03, e0inf, e12, e13, e1inf, e23, e2inf, e3inf,
     * e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf, 
     * e0123, e012inf, e013inf, e023inf, e123inf, 
     * e0123inf]
     */
    @Override
    public double[] extractCoordinates(){
        double[] result = new double[CGACayleyTable.CGABasisBladeNames.length];
        double[] values = new DenseCGAColumnVector(
                      CasADiUtil.nonzeros(dm), sparsity.getrow()).toArray();
        int[] targetNonZeros = toAPISparsity().getrow();
        for (int i=0;i<targetNonZeros.length;i++){
            result[i] = createAPICoordinate(targetNonZeros[i], values);
        }
        return result;
    }

    private CGAMultivectorSparsity targetSparsity;
    private CGAMultivectorSparsity toAPISparsity(){
        if (targetSparsity == null){
            int[] sourceNonZeros = sparsity.getrow();
            List<Integer> sourceNonZerosList = Arrays.stream(sourceNonZeros).boxed().collect(Collectors.toList());
            List<Integer> apiNonzeros = new ArrayList<>();
            if (sourceNonZerosList.contains(0)) apiNonzeros.add(0);

            // 1-vec
            // extern: startindex=1 e0, e1, e2, e3, einf
            if (sourceNonZerosList.contains(5) && sourceNonZerosList.contains(4)) apiNonzeros.add(1);
            if (sourceNonZerosList.contains(1)) apiNonzeros.add(2);
            if (sourceNonZerosList.contains(2)) apiNonzeros.add(3);  
            if (sourceNonZerosList.contains(3)) apiNonzeros.add(4); 
            if (sourceNonZerosList.contains(4) && sourceNonZerosList.contains(5)) apiNonzeros.add(5); 

            // 2-vec
            // intern: start targetIndex=6 "e12","e13","e14","e15","e23","e24","e25","e34","e35","e45"
            // extern: start targetIndex=6 "e01 , e02,  e03, e0inf, e12,  e13, e1inf, e23, e2inf, e3inf
            if (sourceNonZerosList.contains(8) && sourceNonZerosList.contains(9)) apiNonzeros.add(6);
            if (sourceNonZerosList.contains(11) && sourceNonZerosList.contains(12)) apiNonzeros.add(7);
            if (sourceNonZerosList.contains(13) && sourceNonZerosList.contains(14)) apiNonzeros.add(8);

            if (sourceNonZerosList.contains(15)) apiNonzeros.add(9);
            if (sourceNonZerosList.contains(6)) apiNonzeros.add(10);
            if (sourceNonZerosList.contains(7)) apiNonzeros.add(11);

            if (sourceNonZerosList.contains(8) && sourceNonZerosList.contains(9)) apiNonzeros.add(12);
            if (sourceNonZerosList.contains(10)) apiNonzeros.add(13);
            
            if (sourceNonZerosList.contains(11) && sourceNonZerosList.contains(12)) apiNonzeros.add(14);
            if (sourceNonZerosList.contains(13) && sourceNonZerosList.contains(14)) apiNonzeros.add(15);
               
            // 3-vec
            // intern: targetIndex=16 "e123","e124","e125","e134","e135","e145","e234","e235","e245","e345"
            // extern: targetIndex=16 e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf
            
            if (sourceNonZerosList.contains(17) && sourceNonZerosList.contains(18)) apiNonzeros.add(16);
            if (sourceNonZerosList.contains(19) && sourceNonZerosList.contains(20)) apiNonzeros.add(17);
            if (sourceNonZerosList.contains(21)) apiNonzeros.add(18);
               
            if (sourceNonZerosList.contains(22) && sourceNonZerosList.contains(23)) apiNonzeros.add(19);
                
            if (sourceNonZerosList.contains(24)) apiNonzeros.add(20);
            
            if (sourceNonZerosList.contains(25)) apiNonzeros.add(21);
            if (sourceNonZerosList.contains(16)) apiNonzeros.add(22);
            
            if (sourceNonZerosList.contains(17) && sourceNonZerosList.contains(18)) apiNonzeros.add(23);
            if (sourceNonZerosList.contains(19) && sourceNonZerosList.contains(20)) apiNonzeros.add(24);
            if (sourceNonZerosList.contains(22) && sourceNonZerosList.contains(23)) apiNonzeros.add(25);
            
            // 4-vec
            // intern: targetIndex=26 "e1234","e1235","e1245","e1345","e2345"
            // extern: targetIndex=26 e0123, e012inf, e013inf, e023inf, e123inf
            
            if (sourceNonZerosList.contains(26) && sourceNonZerosList.contains(27)) apiNonzeros.add(26);
               
            if (sourceNonZerosList.contains(28)) apiNonzeros.add(27);
            if (sourceNonZerosList.contains(29)) apiNonzeros.add(28);
            if (sourceNonZerosList.contains(30)) apiNonzeros.add(29);
               
            if (sourceNonZerosList.contains(26) && sourceNonZerosList.contains(27)) apiNonzeros.add(30);  
               

            // 5-vec
            // intern: targetIndex=31 "e12345"
            // extern: targetIndex=31 e0123inf
            if (sourceNonZerosList.contains(31)) apiNonzeros.add(31);
            
            targetSparsity = new CGAMultivectorSparsity (apiNonzeros.stream().mapToInt(Integer::intValue).toArray());
        }
        return targetSparsity;
    }
    
    
    private double createAPICoordinate(int targetIndex, double[] sourceValues){
        switch (targetIndex){
            
            // interne (implementation) representation
            // ["","e1","e2","e3","e4","e5","e12","e13","e14","e15","e23","e24","e25","e34","e35","e45",
            // "e123","e124","e125","e134","e135","e145","e234","e235","e245","e345",
            // "e1234","e1235","e1245","e1345","e2345",
            // "e12345"];

            // scalar
            case 0:
                return sourceValues[0];

            // 1-vec
            // extern: startindex=1 e0, e1, e2, e3, einf
            case 1:
                return (sourceValues[5] -sourceValues[4])*0.5; // e0=0.5*(e5-e4)
            case 2:
                return sourceValues[1]; // e1
            case 3:
                return sourceValues[2]; // e2
            case 4:
                return sourceValues[3]; // e3
            case 5:
                return (sourceValues[4]+sourceValues[5])*0.5; // einf = e5+e4

            // 2-vec
            // intern: start targetIndex=6 "e12","e13","e14","e15","e23","e24","e25","e34","e35","e45"
            // extern: start targetIndex=6 "e01 , e02,  e03, e0inf, e12,  e13, e1inf, e23, e2inf, e3inf
            case 6:
                return (sourceValues[8]  - sourceValues[9]) *0.5; // e01 = 0.5(e14-e15)
            case 7:
                return (sourceValues[11] - sourceValues[12])*0.5; // e02 = 0.5(e24-e25)
            case 8:
                return (sourceValues[13] - sourceValues[14])*0.5; // e03 = 0.5(e34-e35)

            case 9:
                return - sourceValues[15]; // e0inf = -1 - e45

            case 10:
                return sourceValues[6]; // e12
            case 11:
                return sourceValues[7]; // e13
            case 12:
                return  sourceValues[8]  + sourceValues[9]; // e1inf = e15+e14
            case 13:
                return sourceValues[10]; // e23
            case 14:
                return sourceValues[11] + sourceValues[12]; // e2inf=e25+e24
            case 15:
                return sourceValues[13] + sourceValues[14]; // e3inf=e35+e34

            // 3-vec
            // intern: targetIndex=16 "e123","e124","e125","e134","e135","e145","e234","e235","e245","e345"
            // extern: targetIndex=16 e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf
            case 16:
                return 0.5*(sourceValues[18]-sourceValues[17]); // e012=0.5*(e125-e124
            case 17:
                return 0.5*(sourceValues[20]-sourceValues[19]); // e013=0.5*(e135-e134)
            case 18:
                return sourceValues[21]; // e01inf=e145
            case 19:
                return 0.5*(sourceValues[23]-sourceValues[22]);// e023=0.5*(e235-e234)
            case 20:
                return sourceValues[24]; // e02inf=e245
            case 21:
                return sourceValues[25]; // e03inf=e345
            case 22:
                return sourceValues[16]; // e123
            case 23:
                return sourceValues[17]+sourceValues[18]; // e12inf=e124+e125
            case 24:
                return sourceValues[19]+sourceValues[20]; // e13inf=e134+e135
            case 25:
                return  sourceValues[22]+sourceValues[23]; // e23inf=e234+e235

            // 4-vec
            // intern: targetIndex=26 "e1234","e1235","e1245","e1345","e2345"
            // extern: targetIndex=26 e0123, e012inf, e013inf, e023inf, e123inf
            case 26:
                return 0.5*(sourceValues[26]-sourceValues[27]); // e0123=0.5*(e1234-e1235)
            case 27:
                return sourceValues[28]; // e012inf=-e1245
            case 28:
                return sourceValues[29]; // e013inf=-e1345
            case 29:
                return  sourceValues[30]; // e023inf=-e2345
            case 30:
                return sourceValues[26]+sourceValues[27]; // e123inf=e1234+e1235

            // 5-vec
            // intern: targetIndex=31 "e12345"
            // extern: targetIndex=31 e0123inf
            case 31:
                return sourceValues[31]; // e12345
            default:
        }
        throw new IllegalArgumentException("The given index="+String.valueOf(targetIndex)+" is outside the CGA multivector length!");
    }
    
    /**
     * The inverse of the multivector even if it is not a versor.
     * 
     * Implementation of
     * https://core.ac.uk/download/pdf/74374477.pdf<p>
     * 
     * (right side inversion)<p>
     * 
     * Works only with non-degenerative basis.<p>
     * 
     * @return the inverse of an arbitray multivector or 0 if no inverse exist.
     * @throws java.lang.ArithmeticException if multivector is not invertible.
     * 
     * vgl. ganja.js:
     * this.Conjugate.Mul(this.Involute).Mul(this.Reverse).Mul(this.Mul(this.Conjugate).
     * Mul(this.Involute).Mul(this.Reverse).Map(1,4)).Mul(
     * this.constructor.Scalar(1/this.Mul(this.Conjugate).Mul(this.Involute).
     * Mul(this.Reverse).Mul(this.Mul(this.Conjugate).Mul(this.Involute).
     * Mul(this.Reverse).Map(1,4))[0]));
     */
    public iCGAMultivector generalInverse(){
        CGA5Multivector conjugate = conjugate();
        CGA5Multivector gradeInversion = gradeInversion();
        CGA5Multivector reversion = reverse();
        CGA5Multivector part1 = (CGA5Multivector) conjugate.gp(gradeInversion).gp(reversion); 
        CGA5Multivector part2 = (CGA5Multivector) gp(part1); 
        CGA5Multivector part3 = negate14(part2);
        double scalar = part2.gp(part3).scalarPart(); 
        return part1.gp(part3).gp(1d/scalar);
    }
    
    /**
     * Negates only the signs of the vector and 4-vector parts of an multivector. 
     * 
     * @return multivector with changed signs for vector and 4-vector parts
     */
    private CGA5Multivector negate14(CGA5Multivector m){
        // liefert vermutlich die falschen Koordinaten
        //double[] coordinates = m.extractCoordinates();
        
        DM result = new DM(CasADiUtil.toCasADiSparsity(sparsity),dm);
        //StdVectorDouble result = dm.get_nonzeros();
        
        int[] grade1Indizes = sparsity.getIndizes(1);
        for (int i=0;i<grade1Indizes.length;i++){
           result.at(grade1Indizes[i]).assign(new DM(-dm.at(grade1Indizes[i]).scalar()));
        }
        int[] grade4Indizes = sparsity.getIndizes(4);
        for (int i=0;i<grade4Indizes.length;i++){
           result.at(grade4Indizes[i]).assign(new DM(-dm.at(grade4Indizes[i]).scalar()));
        }
        
        /*double[] coordinates = Arrays.copyOf(m._mVec, m._mVec.length);
        for (int i=1;i<6;i++){
            coordinates[i] = -coordinates[i];
            // dm.at(neededRows[i]).assign(new DM(values[i]));
        }
        for (int i=26;i<31;i++){
            coordinates[i] = -coordinates[i];
        }
        return (CGA5Multivector) create(coordinates);*/
        return new CGA5Multivector(result, sparsity);
    }
   
    
    // implementation of iCGAMultivector 
    
    @Override
    public iCGAMultivector add(iCGAMultivector b) {
        throw new RuntimeException("add - not yet implemented!");
        //return new CGA5Multivector (binop_Add (this, (CGA5Multivector) b));
    }
    @Override
    public iCGAMultivector add (double b){
        throw new RuntimeException("adds - not yet implemented!");
        //return new CGA5Multivector (binop_adds (this, b));
    }
    @Override
    public iCGAMultivector sub (iCGAMultivector b){
        throw new RuntimeException("sub - not yet implemented!");
        //return new CGA5Multivector (binop_Sub (this, (CGA5Multivector) b));
    }
    @Override
    public iCGAMultivector sub (double b){
        throw new RuntimeException("subs - not yet implemented!");
        //return new CGA5Multivector (binop_subs (this, b));
    }
    @Override
    public iCGAMultivector gp(iCGAMultivector b){
        throw new RuntimeException("gp - not yet implemented!");
        //return new CGA5Multivector(gp(this, (CGA5Multivector) b));
    }
    @Override
    public iCGAMultivector gp(double s){
        throw new RuntimeException("smul - not yet implemented!");
        //return new CGA5Multivector(binop_smul (s, this));
    }
    
    /**
     * Get the scalar part even if the multivector does not include scalar part only.
     * 
     * @return scalar part
     */
    @Override
    public double scalarPart() {
       return dm.at(0).scalar();
       //return _mVec[0];
    }
    
    @Override
    public iCGAMultivector ip(iCGAMultivector b, int type) {
        /*
        switch (type){
            //TODO
            // Hesteness inner product implementieren
            case RIGHT_CONTRACTION:
                return new CGA5Multivector(CGA.binop_Dot(this, (CGA) b));
            case LEFT_CONTRACTION:
                return new CGA5Multivector(CGA.binop_Dot(this, (CGA) b));
            default:
                throw new RuntimeException("Inner product type \""+String.valueOf(type)+"\" not yet implemented!");
        }*/
        throw new RuntimeException("not yet implemented!");
    }

    public static double[] nonzeros(double[] values){
        List<Double> result = new ArrayList<>();
        for (int i=0;i<values.length;i++){
            if (values[i] != 0) result.add(values[i]);
        }
        return result.stream().mapToDouble(d -> d).toArray();
    }
    public static double[] nonzeros(double[] values, int[] nonzeros){
        List<Integer> nonzerosList = Arrays.stream(nonzeros).boxed().collect(Collectors.toList());
        List<Double> result = new ArrayList<>();
        for (int i=0;i<values.length;i++){
            if (nonzerosList.contains(i)){
                result.add(values[i]);
            }
        }
        return result.stream().mapToDouble(d -> d).toArray();
    }
    
    /**
     * Zero values in the double[] argument define the sparsity.
     * 
     * @param values, nonzeros define the sparsity
     * @return 
     */
    @Override
    public iCGAMultivector create(double[] values){
        // setCoordinates() darf hier nicht verwendet werden, da dem ein anderes
        // Koordinatensystem zugrunde liegen könnte
        CGAMultivectorSparsity resSparsity = new CGAMultivectorSparsity(values);
        return new CGA5Multivector(resSparsity, nonzeros(values));
    }
    
    public static CGAKVectorSparsity e45Sparsity;
    @Override
    public iCGAMultivector createOrigin(double scale) {
        if (e45Sparsity == null){
            int[] nonzeros = new int[]{
                baseCayleyTable.getBasisBladeRow("e4"),
                baseCayleyTable.getBasisBladeRow("e5")};
            e45Sparsity = CGAKVectorSparsity.createSparsity(nonzeros);
        }
        double[] values = new double[]{-0.5*scale, 0.5*scale};
        return new CGA5Multivector(e45Sparsity, values);
    }
    @Override
    public iCGAMultivector createInf(double scale) {
        if (e45Sparsity == null){
            int[] nonzeros = new int[]{
                baseCayleyTable.getBasisBladeRow("e4"),
                baseCayleyTable.getBasisBladeRow("e5")};
            e45Sparsity = CGAKVectorSparsity.createSparsity(nonzeros);
        }
        double[] values = new double[]{scale, scale};
        return new CGA5Multivector(e45Sparsity, values);
    }
    
    public static CGAKVectorSparsity e1Sparsity;
    @Override
    public iCGAMultivector createEx(double scale) {
        if (e1Sparsity == null){
            int[] nonzeros = new int[]{
                baseCayleyTable.getBasisBladeRow("e1")};
            e1Sparsity = CGAKVectorSparsity.createSparsity(nonzeros);
        }
        double[] values = new double[]{scale};
        return new CGA5Multivector(e1Sparsity, values);
    }
    public static CGAKVectorSparsity e2Sparsity;
    @Override
    public iCGAMultivector createEy(double scale) {
        if (e2Sparsity == null){
            int[] nonzeros = new int[]{
                baseCayleyTable.getBasisBladeRow("e2")};
            e2Sparsity = CGAKVectorSparsity.createSparsity(nonzeros);
        }
        double[] values = new double[]{scale};
        return new CGA5Multivector(e2Sparsity, values);
    }
    public static CGAKVectorSparsity e3Sparsity;
    @Override
    public iCGAMultivector createEz(double scale) {
       if (e3Sparsity == null){
            int[] nonzeros = new int[]{
                baseCayleyTable.getBasisBladeRow("e3")};
            e3Sparsity = CGAKVectorSparsity.createSparsity(nonzeros);
        }
        double[] values = new double[]{scale};
        return new CGA5Multivector(e3Sparsity, values);
    }
    
    @Override
    public iCGAMultivector createScalar(double scale){
        return new CGA5Multivector(0, scale);
    }
    
    @Override
    public boolean isScalar() {
        // angenommen, dass der scalar immer bei index 0 steht
        int[] rows = sparsity.getrow();
        return rows.length == 1 && rows[0] == 0;
    }

    @Override
    public double length2Squared() {
        throw new RuntimeException("not yet implemented!");
        //return Math.abs(binop_Mul(this, this.Conjugate())._mVec[0]);
    }

    /**
     * Determine the grade of the multivector.
     * 
     * @return -1 if the multivector is no k-vector, 0 if all components are 0, 1-5 else
     */
    @Override
    public int grade() {
        //TODO
        // oder muss ich hier precision >0 übergeben?
        if (isNull(0)) return 0;
        if (sparsity instanceof CGAKVectorSparsity cGAKVectorSparsity){
            return cGAKVectorSparsity.getGrade();
        }
        return -1;
    }

    @Override
    public iCGAMultivector meet(iCGAMultivector mv) {
        throw new UnsupportedOperationException("meet not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public iCGAMultivector join(iCGAMultivector mv) {
        throw new UnsupportedOperationException("joint not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public iCGAMultivector exp() {
        // eigentlich sollte die class CGA eine method Pow() zur Verfügung stellen
        // unklar warum sie das nicht tut.
        // Das scheint mir die js impl in ganja.js zu sein für n>=4
        // var res = Element.Scalar(1), y=1, M= this.Scale(1), N=this.Scale(1); for (var x=1; x<15; x++) 
        // { res=res.Add(M.Scale(1/y)); M=M.Mul(N); y=y*(x+1); }; return res;
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    // Achtung: Das bezieht sich auf die interne Representation
    
    //TODO
    // als Enumeration bauen, damit ich die Strings und Indizes automatisch robust zusammen definieren kann
   
    //static String[] basisBladesNames = new String[]{"","e1","e2","e3","e4","e5","e12","e13","e14","e15","e23","e24","e25","e34","e35","e45","e123","e124","e125","e134",
    //        "e135","e145","e234","e235","e245","e345","e1234","e1235","e1245","e1345","e2345","e12345"};
    
    @Override
    public String[] basisBladeNames() {
       return baseCayleyTable.getBasisBladeNames();
    }
    
    @Override
    public boolean isNull(double precision){
        StdVectorDouble values = dm.get_nonzeros();
        for (int i=0;i<values.size();i++){
            if (Math.abs(values.get(i)) > precision) return false;
        }
        return true;
    }
}