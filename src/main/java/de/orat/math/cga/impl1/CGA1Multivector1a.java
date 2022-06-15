package de.orat.math.cga.impl1;

import de.orat.math.ga.basis.Multivector;
import de.orat.math.ga.basis.ScaledBasisBlade;
import de.orat.math.ga.basis.Util;
import static de.orat.math.cga.impl1.CGA1Metric.CGA_METRIC;
import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.ga.basis.MeetJoin;
import java.util.ArrayList;
import java.util.List;
import org.jogamp.vecmath.Tuple3d;

/**
 * CGA Multivector reference implementation based on the reference implementation 
 * described in Dorst2007.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGA1Multivector1a extends Multivector implements iCGAMultivector {
   
    /** 
     * Creates a new instance of CGAMultivector.
     */
    public CGA1Multivector1a() {
        super();
    }

    /** 
     * Creates a new instance of CGAMultivector.
     * 
     * @param s scalar value
     */
    public CGA1Multivector1a(double s) {
        super(s);
    }

    /** 
     * Creates a new instance of CGAMultivector.
     * 
     * Do not modify 'B' for it is not copied.
     * 
     * @param B list of scaled blades
     */
    public CGA1Multivector1a(List<ScaledBasisBlade> B) {
	super(B);
    }

    /** 
     * Creates a new instance of CGAMultivector.
     * 
     * Do not modify 'B' for it is not copied.
     * 
     * @param B 
     */
    public CGA1Multivector1a(ScaledBasisBlade B) {
        super(B);
    }
    
    /** 
     * Creates a new instance of CGAMultivector.
     * 
     * Do not modify mv or its blades for it is not copied.
     * 
     * @param mv multivector 
     */
    public CGA1Multivector1a(Multivector mv) {
        // throws exception if mv contains blades not corresponding to cga
        super(mv.getBlades());
    }
    
    
    // coordinate extaction
    
    @Override
    public CGA1Multivector1a extractGrade(int g){
        return new CGA1Multivector1a(super.extractGrade(g));
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
     * Extract the coordinates from all basis blades of the given grade
     * inclusive 0 values.
     * 
     * Equivalent to k-vector/k-blades.
     * 
     * @param grade grade
     * @return all coordinates corresponding to the given grade inclusive 0 values.
     */
    @Override
    public double[] extractCoordinates(int grade){
        List<ScaledBasisBlade> gblades = extractBlades(new int[]{grade});
        int n =  5;//spaceDim();
        CGA1Metric indexTable = CGA1Metric.getInstance();
        double[] result = new double[Util.binominal(n, grade)];
        for (int i=0;i<gblades.size();i++){
            ScaledBasisBlade basisBlade = gblades.get(i);
            result[indexTable.getIndex(basisBlade.bitmap, grade)] = basisBlade.scale;
        }
        return result;
    }
    
    // ungetestet
    public void setCoordinates(double[] values){
        blades = new ArrayList<>();
        for (int i=0;i<values.length;i++){
            if (values[i] != 0){
                blades.add(new ScaledBasisBlade(i, values[i]));
            }
        }
    }
    // ungetestet
    @Override
    public void setCoordinates(int grade, double[] values){
        
    }
    // ungetested
    @Override
    public double[] extractCoordinates(){
        return null;
    }
    @Override
    public iCGAMultivector create(double[] values){
        return null;
    }
    
    // base vector creation
    
    /**
     * Create origin base vector.
     * 
     * @param scale
     * @return origin base vector.
     */
    @Override
    public iCGAMultivector createOrigin(double scale){
        return CGA1Metric.createOrigin(scale);
    }
    @Override
    public iCGAMultivector createEx(double scale){
        return CGA1Metric.createEx(scale);
    }
    @Override
    public iCGAMultivector createEy(double scale){
        return CGA1Metric.createEy(scale);
    }
    @Override
    public iCGAMultivector createEz(double scale){
        return CGA1Metric.createEz(scale);
    }
    @Override
    public iCGAMultivector createInf(double scale){
        return CGA1Metric.createBasisInf(scale);
    }
    @Override
    public iCGAMultivector createScalar(double d){
        return new CGA1Multivector1a(d);
    }
    
    /**
     * Create Basis vector.
     * 
     * @param idx 0..5
     * @param s scale of the basis blade
     * @return basis vector
     */
    public static CGA1Multivector1a createBasisVector(int idx, double s){
        if (idx >= CGA1Metric.baseVectorNames.length) throw new IllegalArgumentException("Idx must be smaller than 5!");
        return new CGA1Multivector1a(Multivector.createBasisVector(idx, s));
    }
    
    
    // Operators
    
    @Override
    public double scp(iCGAMultivector x) {
        return super.scp((CGA1Multivector1a) x, CGA_METRIC);
    }
    
    /**
     * Inner product.
     * 
     * @param x right side argument of the inner product
     * @param type gives the type of inner product:
     * LEFT_CONTRACTION: if subspace of the left side is bigger than the subspace from the right side than the result is zero.
     * RIGHT_CONTRACTION,
     * HESTENES_INNER_PRODUCT or
     * MODIFIED_HESTENES_INNER_PRODUCT.
     * @return inner product of this with a 'x' using metric 'M'
     */
    @Override
    public iCGAMultivector ip(iCGAMultivector x, int type){
        return new CGA1Multivector1a(super.ip((CGA1Multivector1a) x, CGA_METRIC, type));
    }
    @Override
    public CGA1Multivector1a gp(iCGAMultivector x){
        return new CGA1Multivector1a(super.gp((CGA1Multivector1a) x));
    }
    @Override
    public CGA1Multivector1a gp(double x){
        return new CGA1Multivector1a(super.gp(x));
    }
    
    /**
     * This plays an analogous role to transposition in matrix algebra.
     * 
     * @return reverse of the Multivector.
     */
    @Override
    public CGA1Multivector1a reverse() {
        return new CGA1Multivector1a(super.reverse());
    }
    
    @Override
    public CGA1Multivector1a add(iCGAMultivector x){
        return new CGA1Multivector1a(super.add((CGA1Multivector1a) x));
    }
    @Override
    public CGA1Multivector1a add(double d){
        return new CGA1Multivector1a(super.add(d));
    }
    @Override
    public CGA1Multivector1a sub(iCGAMultivector x) {
        return new CGA1Multivector1a(super.sub((CGA1Multivector1a) x));
    }
    @Override
    public CGA1Multivector1a sub(double x){
        return new CGA1Multivector1a(super.sub(x));
    }
    @Override
    public CGA1Multivector1a exp() {
        return new CGA1Multivector1a(super.exp(CGA_METRIC));
    }
    /**
     * Grade inversion of the multivector.
     * 
     * This is also called "involution" in Dorst2007.
     * 
     * @return grade inversion/involution of this multivector
     */
    @Override
    public CGA1Multivector1a gradeInversion() {
        return new CGA1Multivector1a(super.gradeInversion());
    }
    /**
     * The inverse of the multivector even if it is not a versor (returns 0 if 
     * inverse does not exist).
     * 
     * @return the inverse of an arbitray multivector or 0 if no inverse exist.
     */
    @Override
    public iCGAMultivector generalInverse() {
        return new CGA1Multivector1a(super.generalInverse(CGA_METRIC));
    }
    
    /**
     * A versor is a multivector that can be expressed as the geometric product 
     * of a number of non-null 1-vectors. 
     * 
     * A sum of two versors does not in general result in a versor!<p>
     * 
     * @return inverse of this (assuming, it is a versor, no check is made!)
     * @throws java.lang.ArithmeticException if the multivector is not invertable
     */
    @Override
    public iCGAMultivector versorInverse(){
        return new CGA1Multivector1a(super.versorInverse(CGA_METRIC));
    }
   
    @Override
    public iCGAMultivector conjugate(){
        return new CGA1Multivector1a(super.cliffordConjugate());
    }
    @Override
    public iCGAMultivector dual() {
        return new CGA1Multivector1a(super.dual(CGA_METRIC));
    }
    @Override
    public iCGAMultivector undual(){
        return new CGA1Multivector1a(super.undual(CGA_METRIC));
    }
    /**
     * Unit multivector.
     * 
     * @return unit
     * @throws java.lang.ArithmeticException if multivector is null.
     */
    @Override
    public iCGAMultivector normalize() {
	return new CGA1Multivector1a(super.unit_r(CGA_METRIC));
    }
    /**
     * Squared norm.
     * 
     * @return squared euclidean norm
     */
    @Override
    public double squaredNorm(){
        // alternative implementation: use default implementation in the spi
        return super.norm_e2(CGA_METRIC);
    }
    /**
     * Calculate the Euclidean norm. (strict positive).
     * 
     * @return euclidean norm
     */
    public double norm(){
        // alternative implementation: return Math.sqrt(squaredNorm)
        return super.norm_e(CGA_METRIC);
    }
            
    @Override
    public iCGAMultivector meet(iCGAMultivector b){
        MeetJoin mj = new MeetJoin(this, (CGA1Multivector1a) b);
        return new CGA1Multivector1a(mj.getMeet());
    }
    public iCGAMultivector join(iCGAMultivector b){
        MeetJoin mj = new MeetJoin(this, (CGA1Multivector1a) b);
        return new CGA1Multivector1a(mj.getJoin());
    }

    @Override
    public iCGAMultivector op(iCGAMultivector b) {
       return new CGA1Multivector1a(super.op((CGA1Multivector1a) b));
    }

    @Override
    public String toString(){
        return toString(CGA1Metric.baseVectorNames);
    }
}