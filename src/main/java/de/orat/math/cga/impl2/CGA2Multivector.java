package de.orat.math.cga.impl2;

import de.orat.math.cga.impl1.CGA1Multivector;
import de.orat.math.cga.impl2.generated.CGA;
import de.orat.math.cga.spi.iCGAMultivector;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGA2Multivector extends de.orat.math.cga.impl2.generated.CGA implements iCGAMultivector {
    
    static CGA2Multivector defaultInstance = new CGA2Multivector(0,0d);
            
    //TODO
    // "1","e1","e2","e3","e4","e5","e12","e13","e14","e15","e23","e24","e25","e34","e35","e45","e123","e124","e125","e134","e135","e145","e234","e235","e245","e345","e1234","e1235","e1245","e1345","e2345","e12345"
    // als Enumeration bauen, damit ich die Strings und Indizes automatisch robust zusammen definieren kann
    
    CGA2Multivector(de.orat.math.cga.impl2.generated.CGA cga){
        super(cga._mVec);
    }
    public CGA2Multivector(int idx, double value){
        super(idx, value);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<_mVec.length;i++){
            sb.append(_basis[i]).append("=").append(String.valueOf(_mVec[i]));
            sb.append("\n");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
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
     * dual.
     * 
     * @return poincare duality operator
     */
    @Override
    public CGA2Multivector dual(){
        return new CGA2Multivector(CGA.unop_Dual(this));
    }
    /**
     * Bei Quaternionen:
     * The conjugate is useful because it has the following properties:
     *
     * qa' * qb' = (qb*qa)' In this way we can change the order of the multiplicands.
     * q * q' = a2 + b2 + c2 + d2 = real number. Multiplying a quaternion by its 
     * conjugate gives a real number. This makes the conjugate useful for finding 
     * the multiplicative inverse. For instance, if we are using a quaternion q 
     * to represent a rotation then conj(q) represents the same rotation in the reverse direction.
     * Pout = q * Pin * q' We use this to calculate a rotation transform.
     *
     * Aber was macht das in GA?
     * 
     * This reverses all directions in space
     *
     * A~ denotes the conjugate of A
     *
     * conjugate, reverse and dual are related as follows.
     *
     * A~= (A†)* = (A*)†
     *
     * identities
     *
     * (A * B)~ = B~* A~
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
     * The reverse 
     * function is denoted by †, so the reversal of A is denoted by A†.
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
        return 1;
    }
    @Override
    public int getEinfIndex(){
        return 0;
    }
    @Override
    public int getOriginIndex(){
        return 4;
    }
    /**
     * Get the k-blade (k-vector) of the given grade k.
     * 
     * 0-blades are scalars, 1-blades are vectors, 2-blades are bivectors, 
     * 3-blades are threevectors, 4-blades are quad-vectors and 5-blades are
     * called pseudo-scalars.
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
    
    @Override
    /**
     * Set the coordinates of the given grade.
     * 
     * @throws IllegalArgumentException if the given grade does not correspoind to the
     * length of the value array or the given grade does correspond to CGA at all.
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
     */
    @Override
    public CGA2Multivector extractGrade(int grade){
        if (grade > 5 || grade < 0) 
            throw new IllegalArgumentException ("Grade "+String.valueOf(grade)+" not allowed!");
        // eigentlich sollte das doch zulässig sein und nur 0 herauskommen, oder?
        
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
    
    @Override
    public double[] extractCoordinates(){
        double[] result = new double[_mVec.length];
        System.arraycopy(_mVec, 0, result, 0, _mVec.length);
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
    
    //TODO
    // bisher nur left contraction implementiert
    @Override
    public iCGAMultivector ip(iCGAMultivector b, int type) {
        switch (type){
            //TODO
            case CGA1Multivector.LEFT_CONTRACTION:
            default:
                return new CGA2Multivector(CGA.binop_Dot(this, (CGA) b));
        }
    }

    @Override
    public iCGAMultivector create(double[] values){
        return new CGA2Multivector(new CGA(values));
    }
    @Override
    public iCGAMultivector createOrigin(double scale) {
        return new CGA2Multivector(CGA.binop_Add(e4,e5));
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
    public iCGAMultivector createInf(double scale) {
        return new CGA2Multivector(CGA.binop_smul(0.5d, CGA.binop_Sub(e5, e4)));
    }
    @Override
    public iCGAMultivector createScalar(double scale){
        return new CGA2Multivector(new CGA(0, scale));
    }
    
    @Override
    public boolean isScalar() {
        for (int i=1;i<_mVec.length;i++){
            if (_mVec[i] != 0d) return false;
        }
        return true;
    }

    @Override
    public iCGAMultivector undual() {
        return dual().gp(-1d);
    }

    @Override
    public boolean isNull() {
        for (int i=0;i<_mVec.length;i++){
            if (_mVec[i] != 0d){
                return false;
            }
        }
        return true;
    }

    //TODO
    // unklar, wie ich das implementieren soll
    @Override
    public iCGAMultivector exp() {
        // eigentlich sollte die class CGA eine method Pow() zur Verfügung stellen
        // unklar warum sie das nicht tut.
        // Das scheint mir die js impl in ganja.js zu sein für n>=4
        // var res = Element.Scalar(1), y=1, M= this.Scale(1), N=this.Scale(1); for (var x=1; x<15; x++) 
        // { res=res.Add(M.Scale(1/y)); M=M.Mul(N); y=y*(x+1); }; return res;
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double squaredNorm() {
        return Math.abs(binop_Mul(this, this.Conjugate())._mVec[0]);
    }

    @Override
    public int grade() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}