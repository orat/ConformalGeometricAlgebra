package de.orat.math.cga.test;

/**
 * https://blog.demofox.org/2014/12/30/dual-numbers-automatic-differentiation/
 * 
 * https://samritchie.io/dual-numbers-and-automatic-differentiation/
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class DualNumber {
    
    private double theta; // theta
    private double eps; // eps
    
    public DualNumber(DualNumber d){
        theta = d.real();
        eps = d.dual();
    }
    public DualNumber(double theta, double eps){
        this.theta = theta;
        this.eps = eps;
    }
    public double real(){
        return theta;
    }
    public double dual(){
        return eps;
    }
    /**
     * Determines the non negative real number, also called modulus, magnitude, absolute value or
     * norm of a dual number.
     * 
     * Keep in mind that the absolute value is degenerate, in that abs(z) = 0 need 
     * not to imply that z=0 because the dual part can be != 0.
     * 
     * FIXME ist das so überhaupt sinnvoll, oder sollte besser nur nachfolgende
     * Methode magnitude() zur Verfügung stehen?
     * @return absolute value
     */
    public double abs(){
        //return Math.abs(theta);
        // Cheng1994
        return Math.sqrt(theta*theta+eps*eps);
    }
    
    // Cheng1994
    public DualNumber conjugate(){
        return new DualNumber(theta, -eps);
    }
    public DualNumber magnitude(){
        return new DualNumber(Math.abs(theta), eps);
    }
    
    /**
     * Multipliation with a real value.
     * 
     * e.g. Cheng1994<p>
     * 
     * @param s 
     */
    public void scale(double s){
        theta *=s;
        eps *=s;
    }
    
    /**
     * An involution is an endomorphism whose composition with itself is the dentity
     * morphism.
     * 
     * @return involution
     */
    public DualNumber involution(){
        return new DualNumber(theta,-eps);
    }
    public DualNumber mul(DualNumber b){
        return new DualNumber(theta*b.real(), eps*b.real()+theta*b.dual());
    }
    public DualNumber add(DualNumber b){
        return new DualNumber(theta+b.real(), eps+b.dual());
    }
    /**
     * Square root.
     * 
     * @return 
     * @throws IllegalArgumentException if theta <= 0
     */
    public DualNumber sqrt(){
        if (theta <= 0) throw new IllegalArgumentException("theta <= 0 now allowed!");
        double sqrte = Math.sqrt(theta);
        return new DualNumber(sqrte,dual()/(2*sqrte));
    }
    /**
     * Division.
     * 
     * @param b
     * @return quotient
     * @throws new IllegalArgumentexception for division by a pure dual number (theta==0).
     */
    public DualNumber div(DualNumber b){
        if (b.theta == 0) throw new IllegalArgumentException("Division by a pure dual number with real()==theta==0 is not allowed!");
        return new DualNumber(theta/b.real(), (eps*b.real()-theta*b.dual())/(b.real()*b.real()));
    }
    public static DualNumber pow(DualNumber a, DualNumber x){
        return new DualNumber(Math.pow(a.real(),x.real()),
                x.real()*a.dual()*Math.pow(Math.abs(a.real()),x.real()-1)+
                        x.dual()*Math.log(Math.abs(a.real()))*Math.pow(Math.abs(a.real()),x.real()));
    }
    public DualNumber log(){
        return new DualNumber(Math.log(real()),dual()/real());
    }
    public DualNumber sin(){
        return new DualNumber(Math.sin(theta),eps*Math.cos(theta));
    }
    public DualNumber cos(){
        return new DualNumber(Math.cos(theta),-eps*Math.sin(theta));
    }
    public DualNumber tan(){
        double cos2 = Math.cos(theta)*Math.cos(theta);
        return new DualNumber(Math.tan(theta), eps/cos2);
    }
    /**
     * Cotangens is the reziprocal of atan(): cot()=1/tan().
     * 
     * @return 1/tan()
     * @throws new IllegalArgumentException if sin(theta) == 0.
     */
    public DualNumber cot(){
        if (Math.sin(theta) == 0) throw new IllegalArgumentException("sin(theta) == 0 is not allowed!");
        return new DualNumber(1d/Math.tan(theta),-dual()/(Math.sin(theta)*Math.sin(theta)));
    }
    public DualNumber asin(){
        return new DualNumber(Math.asin(theta),eps/Math.sqrt(1-theta*theta));
    }
    public DualNumber acos(){
        return new DualNumber(Math.acos(theta),-eps/Math.sqrt(1-theta*theta));
    }
    public DualNumber atan(){
        return new DualNumber(Math.atan(theta),eps/(1+theta*theta));
    }
    /**
     * Dual version of atan2.
     * 
     * Bivariate ‘sign-sensitive’ version ‘atan2".<p>
     * 
     * An analysis of the dual-complex unit circle with pplications to line geometry
     * Bertold Bongardt; Nov. 2019<p>
     * 
     * B. Bongardt, The adjoint trigonometric representation of displacements
     * and a closed-form solution to the ikp of general 3c chains, Mechanism and
     * Machine Theory.<p>
     * 
     * @param y
     * @param x
     * @return atan2(y,x)
     * @throws IllegalArgumentException if y.real()==0d or x.real()==0d
     */
    public static DualNumber atan2(DualNumber y, DualNumber x){
        
        // d==0d is wrong if x.real()==0
        if (x.real() == 0d){
            throw new IllegalArgumentException("x.real() == 0d is not allowed!");
        }
        // d==0d is wrong if y.real()==0
        if (y.real() == 0d){
            throw new IllegalArgumentException("y.real() == 0d is not allowed!");
        }
        // unklare Alternative
        //return new DualNumber(Math.atan2(y.real(),x.real()),y.dual()/(1+y.real()*y.real()));
        
        return new DualNumber(Math.atan2(y.real(),x.real()), 
                (x.real()*y.dual()-x.dual()*y.real())/(x.real()*x.real()+y.real()*y.real()));
    }
    
    public String toString(String name){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("= (");
        sb.append(String.valueOf(this.theta));
        sb.append(", ");
        sb.append(String.valueOf(this.eps));
        sb.append("e)");
        return sb.toString();
    }
}
