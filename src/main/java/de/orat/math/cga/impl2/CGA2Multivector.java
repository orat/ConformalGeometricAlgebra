package de.orat.math.cga.impl2;

import de.orat.math.cga.api.CGAMultivector;
import de.orat.math.cga.impl2.generated.CGA;
import de.orat.math.cga.spi.iCGAMultivector;
import static de.orat.math.ga.basis.InnerProductTypes.LEFT_CONTRACTION;
import static de.orat.math.ga.basis.InnerProductTypes.RIGHT_CONTRACTION;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGA2Multivector extends de.orat.math.cga.impl2.generated.CGA implements iCGAMultivector {
    
    static CGA2Multivector defaultInstance = new CGA2Multivector(0,0d);
         
    // creation of a default instance
    public CGA2Multivector(){}
    
    CGA2Multivector(de.orat.math.cga.impl2.generated.CGA cga){
        super(cga._mVec);
    }
    public CGA2Multivector(int idx, double value){
        super(idx, value);
    }
    
    @Override
    public String toString(){
        return toString(this.basisBladeNames());
    }
    
    /**
     * TODO
     * Umstellen auf e0 und einf
     *
     * @param bvNames
     * @return 
     */
    public String toString(String[] bvNames) {
        boolean empty = true;
        for (int i=0;i<_mVec.length;i++){
            if (_mVec[i] != 0) {
                empty = false;
                break;
            }
        }
        if (empty) {
            return "0";
        } else {
            StringBuilder result = new StringBuilder();
            boolean firstSummand = true;
            for (int i=0; i<_mVec.length; i++) {
                double value = _mVec[i];
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
    public CGA2Multivector op(iCGAMultivector b){
        return new CGA2Multivector(binop_Wedge(this, (CGA) b));
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
    public CGA2Multivector vee(CGA2Multivector b){
        return new CGA2Multivector(binop_Vee(this,b));
    }
    
    /**
     * Dot procuct.
     * 
     * The dot product implemented is the left contraction - without any 
     * extensions or modifications. The geometric meaning is usually formulated
     * as the dot product between a and b gives the orthogonal complement in b of the
     * projection of x onto y.
     * 
     * @param b
     * @return 
     */
    public CGA2Multivector ip(iCGAMultivector b){
        return new CGA2Multivector(CGA.binop_Dot(this, (CGA) b));
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
    public CGA2Multivector dual(){
        return new CGA2Multivector(CGA.unop_Dual(this));
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
     * identities<p>
     *
     * (A * B)~ = B~* A~<p>
     * 
     * @return Cifford conjugate
     */
    @Override
    public CGA2Multivector conjugate(){
        return new CGA2Multivector(super.Conjugate());
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
    public CGA2Multivector reverse(){
        return new CGA2Multivector(unop_Reverse(this));
    }
    
    /**
     * a# = sum k=0(N (-1)k a <k> = a<+> - a<-> .
     * 
     * @return main involution
     */
    @Override
    public CGA2Multivector gradeInversion(){
         return new CGA2Multivector(super.Involute());
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
     * equivalent to k-vector/k-blades
     * @param grade
     * @return 
     */
    @Override
    public double[] extractCoordinates(int grade){
        switch (grade){
            case 0 -> {
                return new double[]{_mVec[0]};
            }
            case 1 -> {
                return new double[]{_mVec[1],_mVec[2],_mVec[3],_mVec[4],_mVec[5]};
            }
            case 2 -> {
                return new double[]{_mVec[6],_mVec[7],_mVec[8],_mVec[9],_mVec[10],
                    _mVec[11],_mVec[12],_mVec[13],_mVec[14],_mVec[15]};
            }
            case 3 -> {
                return new double[]{_mVec[16],_mVec[17],_mVec[18],_mVec[19],_mVec[20],
                    _mVec[21],_mVec[22],_mVec[23],_mVec[24],_mVec[25]};
            }
            case 4 -> {
                return new double[]{_mVec[26],_mVec[27],_mVec[28],_mVec[29],_mVec[30]};
            }
            case 5 -> {
                return new double[]{_mVec[31]};
            }
        }
        throw new IllegalArgumentException("Only 0 < grade <= 5 is allowed!");
    }
    
    /**
     * Set the coordinates of the given grade.
     * 
     * @throws IllegalArgumentException if the given grade does not correspoind to the
     * length of the value array or the given grade does correspond to CGA at all.
     * @Override
     */
    public void setCoordinates(int grade, double[] values){
        if (grade < 0 || grade > 5)
            throw new IllegalArgumentException("Only 0 < grade <= 5 is allowed!");
        switch (grade){
            case 0 -> {
                if (values.length != 1) throw new IllegalArgumentException("Grade 0 must have only 1 value but \""+
                        String.valueOf(values.length)+"\" found!");
                _mVec[0] = values[0];
                break;
            }
            case 1 -> {
                if (values.length != 5) throw new IllegalArgumentException(
                        "Grade 1 must have 5 value but found \""+String.valueOf(values.length)+"\"!");
                System.arraycopy(values, 1, _mVec, 1, 5);
                break;
            }
            case 2 -> {
                if (values.length != 10) throw new IllegalArgumentException(
                        "Grade 2 must have 10 value but found \""+String.valueOf(values.length)+"\"!");
                System.arraycopy(values, 0, _mVec, 6, 10);
                break;
            }
            case 3 -> {
                if (values.length != 10) throw new IllegalArgumentException(
                        "Grade 3 must have 10 value but found \""+String.valueOf(values.length)+"\"!");
                System.arraycopy(values, 0, _mVec, 16, 10);
                break;
            }
            case 4 -> {
                if (values.length != 5) throw new IllegalArgumentException(
                        "Grade 4 must have 5 value but found \""+String.valueOf(values.length)+"\"!");
                System.arraycopy(values, 1, _mVec, 26, 5);
                break;
            }
            case 5 -> {
                if (values.length != 1) throw new IllegalArgumentException("Grade 5 must have only 1 value but \""+
                        String.valueOf(values.length)+"\" found!");
                _mVec[31] = values[0];
                break;
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
    public CGA2Multivector extractGrade(int grade){
        if (grade > 5 || grade < 0) 
            throw new IllegalArgumentException ("Grade "+String.valueOf(grade)+" not allowed!");
        
        double[] arr = new double[32];
        switch (grade){
            case 0 -> arr[0] = _mVec[0];
            case 1 -> {
                for (int i=1;i<=5;i++){
                    arr[i] = _mVec[i];
                }
            }
            case 2 -> {
                for (int i=6;i<=15;i++){
                    arr[i] = _mVec[i];
                }
            }
            case 3 -> {
                for (int i=16;i<=26;i++){
                    arr[i] = _mVec[i];
                }
            }
            case 4 -> {
                for (int i=26;i<=30;i++){
                    arr[i] = _mVec[i];
                }
            }
            case 5 -> arr[31] = _mVec[31];
        }
        CGA result = new CGA(arr);
        return new CGA2Multivector(result);
    }
    
    /**
     * Get a specific coordinates representation.
     * 
     * @return 
     * s, e0, e1, e2, e3, einf, e01, e02, e03, e0inf, e12, e13, e1inf, e23, e2inf, e3inf
     * e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf, 
     * e0123, e012inf, e013inf, e023inf, e123inf, 
     * e0123inf
     */
    @Override
    public double[] extractCoordinates(){
        double[] result = new double[_mVec.length];
        
        // interne representation
        // static String[] basisBladesNames = new String[]{"","e1","e2","e3","e4","e5","e12","e13","e14","e15","e23","e24","e25","e34","e35","e45",
        // "e123","e124","e125","e134","e135","e145","e234","e235","e245","e345",
        // "e1234","e1235","e1245","e1345","e2345",
        //"e12345"};
        
        // scalar
        result[0] = _mVec[0];
        
        // 1-vec
        // extern: startindex=1 e0, e1, e2, e3, einf
        result[1] = (_mVec[5] -_mVec[4])*0.5; // e0=0.5*(e5-e4)
        result[2] = _mVec[1]; // e1
        result[3] = _mVec[2]; // e2
        result[4] = _mVec[3]; // e3
        result[5] = (_mVec[4]+_mVec[5])*0.5; // einf = e5+e4
         
        // 2-vec
        // intern: start index=6 "e12","e13","e14","e15","e23","e24","e25","e34","e35","e45"
        // extern: start index=6 "e01 , e02,  e03, e0inf, e12,  e13, e1inf, e23, e2inf, e3inf
        result[6] = (_mVec[8]  - _mVec[9]) *0.5; // e01 = 0.5(e14-e15)
        result[7] = (_mVec[11] - _mVec[12])*0.5; // e02 = 0.5(e24-e25)
        result[8] = (_mVec[13] - _mVec[14])*0.5; // e03 = 0.5(e34-e35)
        result[9] = -1 - _mVec[15]; // e0inf = -1 - e45
        result[10] = _mVec[6]; // e12
        result[11] = _mVec[7]; // e13
        result[12] = _mVec[8]  + _mVec[9]; // e1inf = e15+e14
        result[13] = _mVec[10]; // e23
        result[14] = _mVec[11] + _mVec[12]; // e2inf=e25+e24
        result[15] = _mVec[13] + _mVec[14]; // e3inf=e35+e34
        
        // 3-vec
        // intern: index=16 "e123","e124","e125","e134","e135","e145","e234","e235","e245","e345"
        // extern: index=16 e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf, 
        result[16] = 0.5*(_mVec[18]-_mVec[17]); // e012=0.5*(e125-e124)
        result[17] = 0.5*(_mVec[20]-_mVec[19]); // e013=0.5*(e135-e134)
        result[18] = _mVec[21]; // e01inf=e145
        result[19] = 0.5*(_mVec[23]-_mVec[22]);// e023=0.5*(e235-e234)
        result[20] = _mVec[24]; // e02inf=e245
        result[21] = _mVec[25]; // e03inf=e345
        result[22] = _mVec[16]; // e123
        result[23] = _mVec[17]+_mVec[18]; // e12inf=e124+e125
        result[24] = _mVec[19]+_mVec[20]; // e13inf=e134+e135
        result[25] = _mVec[22]+_mVec[23]; // e23inf=e234+e235
                
        // 4-vec
        // intern: index=26 "e1234","e1235","e1245","e1345","e2345",
        // extern: index=26 e0123, e012inf, e013inf, e023inf, e123inf, 
        result[26] = 0.5*(_mVec[26]-_mVec[27]); // e0123=0.5*(e1234-e1235)
        result[27] = _mVec[28]; // e012inf=-e1245
        result[28] = _mVec[29]; // e013inf=-e1345
        result[29] = _mVec[30]; // e023inf=-e2345
        result[30] = _mVec[26]+_mVec[27]; // e123inf=e1234+e1235
                
        // 5-vec
        // intern: index=31 "e12345"
        // extern: index=31 e0123inf
        result[31] = _mVec[31]; // e12345
        return result;
    }

    
    // implementation of iCGAMultivector 
    
    @Override
    public iCGAMultivector add(iCGAMultivector b) {
         return new CGA2Multivector (binop_Add (this, (CGA2Multivector) b));
    }
    @Override
    public iCGAMultivector add (double b){
        return new CGA2Multivector (binop_adds (this, b));
    }
    @Override
    public iCGAMultivector sub (iCGAMultivector b){
        return new CGA2Multivector (binop_Sub (this, (CGA2Multivector) b));
    }
    @Override
    public iCGAMultivector sub (double b){
        return new CGA2Multivector (binop_subs (this, b));
    }
    @Override
    public iCGAMultivector gp(iCGAMultivector b){
        return new CGA2Multivector(binop_Mul(this, (CGA2Multivector) b));
    }
    @Override
    public iCGAMultivector gp(double s){
        return new CGA2Multivector(binop_smul (s, this));
    }

    @Override
    public double scalarPart() {
       return _mVec[0];
    }
    
    @Override
    public iCGAMultivector ip(iCGAMultivector b, int type) {
        switch (type){
            //TODO
            // Hesteness inner product implementieren
            case RIGHT_CONTRACTION:
                return new CGA2Multivector(CGA.binop_Dot(this, (CGA) b));
            case LEFT_CONTRACTION:
                return new CGA2Multivector(CGA.binop_Dot(this, (CGA) b));
            default:
                throw new RuntimeException("Inner product type \""+String.valueOf(type)+"\" not yet implemented!");
        }
    }

    @Override
    public iCGAMultivector create(double[] values){
        return new CGA2Multivector(new CGA(values));
    }
    @Override
    public iCGAMultivector createOrigin(double scale) {
        CGA e4s = new CGA(4, -0.5*scale);
	CGA e5s = new CGA(5, 0.5*scale);
        return new CGA2Multivector(CGA.binop_Add(e4s,e5s));
    }
    @Override
    public iCGAMultivector createInf(double scale) {
        CGA e4s = new CGA(4, scale);
	CGA e5s = new CGA(5, scale);
        return new CGA2Multivector(CGA.binop_Add(e5s, e4s));
    }
    @Override
    public iCGAMultivector createEx(double scale) {
        return new CGA2Multivector(new CGA(1, scale));
    }
    @Override
    public iCGAMultivector createEy(double scale) {
       return new CGA2Multivector(new CGA(2, scale));
    }
    @Override
    public iCGAMultivector createEz(double scale) {
       return new CGA2Multivector(new CGA(3, scale));
    }
    
    @Override
    public iCGAMultivector createScalar(double scale){
        return new CGA2Multivector(0, scale);
    }
    
    @Override
    public boolean isScalar() {
        for (int i=1;i<_mVec.length;i++){
            if (_mVec[i] != 0d) return false;
        }
        return true;
    }

    public boolean isNull() {
        for (int i=0;i<_mVec.length;i++){
            if (_mVec[i] != 0d){
                return false;
            }
        }
        return true;
    }

    @Override
    public double length2Squared() {
        return Math.abs(binop_Mul(this, this.Conjugate())._mVec[0]);
    }

    /**
     * Determine the grade of the multivector.
     * 
     * This multivector implementation does not include any compression of structural
     * zeros. Thatś why this grade()-impl uses an epsilon value to descide if a
     * component is available.<p>
     * 
     * @return -1 if the multivector is no k-vector, 0 if all components are 0, 1-5 else
     */
    @Override
    public int grade() {
        // 1 | index = 0
        // e1,e2,e3,e4,e5 | index = 1-5
        // e12,e13,e14,e15,e23,e24,e25,e34,e35,e45 | Index = 6-15
        // e123,e124,e125,e134,e135,e145,e234,e235,e245,e345 | Index = 16-25
        // e1234,e1235,e1245,e1345,e2345 | Index = 26-30
        // e12345 | Index = 31
        
	int result = -1;
        
        // scalar
        if (Math.abs(_mVec[0]) > CGAMultivector.eps){
            result = 0;
        }
        
        // 1-vectors
        boolean found = false;
        for (int i=1;i<6;i++){
            if (Math.abs(_mVec[i]) > CGAMultivector.eps) found = true;
        }
        if (found){
            // kein Scalar
            if (result == -1){
                // weitere blades müssen noch getestet werden
                result = 1;
            } else {
                return -1;
            }
        }
        
        // 2-vectors
        found = false;
        for (int i=6;i<16;i++){
             if (Math.abs(_mVec[i]) > CGAMultivector.eps) found = true;
        }
        if (found){
            // kein scalar und kein 1-vector 
            if (result == -1){
                // weitere blades müssen noch getestet werden
                result = 2;
            } else {
                return -1;
            }
        }
        
        // 3-vectors
        found = false;
        for (int i=16;i<26;i++){
             if (Math.abs(_mVec[i]) > CGAMultivector.eps) found = true;
        }
        if (found){
            // kein scalar, 1-vector und 2-vector
            if (result == -1){
                // weitere blades müssen noch getestet werden
                result = 3;
            } else {
                return -1;
            }
        }
        
        // 4-vectors
        found = false;
        for (int i=26;i<31;i++){
             if (Math.abs(_mVec[i]) > CGAMultivector.eps) found = true;
        }
        if (found){
            if (result == -1){
                // weitere blades müssen noch getestet werden
                result = 4;
            } else {
                return -1;
            }
        }
        
        // pseudo-scalar
        found = false;
        if (Math.abs(_mVec[31]) > CGAMultivector.eps) found = true;
        if (found){
            if (result == -1){
                return 5;
            } else {
                return -1;
            }
        }
        
        // wenn alle 32 Element == 0 dann grade 0
        if (result == -1) return 0;
        
        return result;
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
   
    static String[] basisBladesNames = new String[]{"","e1","e2","e3","e4","e5","e12","e13","e14","e15","e23","e24","e25","e34","e35","e45","e123","e124","e125","e134",
            "e135","e145","e234","e235","e245","e345","e1234","e1235","e1245","e1345","e2345","e12345"};
    @Override
    public String[] basisBladeNames() {
       return basisBladesNames;
    }
}