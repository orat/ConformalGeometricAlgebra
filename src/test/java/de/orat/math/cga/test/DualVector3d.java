package de.orat.math.cga.test;

import org.jogamp.vecmath.Matrix3d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A dual vector - represents a ray/spear (straight line + direction).
 * 
 * General line vectors depend upon five independent scalars. This can be achived
 * by normalization of the real part of the dual vector. Such vectors are called
 * unit dual vectors.<p>
 * 
 * The dual part of the vector, represents the position of the line in space,
 * is defined by contravariant vector components. The real part defining the 
 * direction of the ray are defined in covariant components.<p>
 * 
 * Plücker coordinates represent a line in 3-D space using 6 coordinates.<p>
 * 
 * This representation of lines is useful because it allows for compact 
 * formulation of solutions to various geometric problems such as:<p>
 *
 * - line-line intersection (meet).
 * - line-plane intersection (meet).
 * - point-line union (join).<p>
 *
 * There are lookup tables which tell you how to compute quantities efficiently 
 * using Plücker coordinates.<p>
 *
 * However, Plücker coordinates are just one special case of representing k-dimensional 
 * linear subspaces. Using geometric algebra, any k-dimensional linear subspace 
 * can be represented as a computational element called a blade, and you won't 
 * need the lookup tables anymore because equations will become intuitive, simple 
 * and generic. <p>
 * 
 * The Plücker coordinates do not include (0, 0). They are considered as 
 * homogeneous coordinates (in a five-dimensional projective space) which 
 * uniquely represent lines in the three-dimensional space.<p>
 * 
 * The length of the dual part corresponds to the area of the triangle defined 
 * by the real part and the origin and is not dependend from any concrete point
 * on the line.<p>
 * 
 * https://www.quora.com/What-are-%E2%80%9Ccovariant%E2%80%9D-and-%E2%80%9Ccontravariant%E2%80%9D-vectors-as-intuitive-representations?top_ans=43040605
 * http://www.realtimerendering.com/resources/RTNews/html/rtnv11n1.html#art3
 * 
 * https://geometricalgebra.org/plucker_coordinates.html
 * --> Zusammenhang mit geometric algebra
 * 
 * In the dual algebra 1-vectors are lines and the wedge operation is the meet (^) of 
 * two vectors
 * TODO
 * - add() implementieren ist schwierig
 * - rotate(DualNumber, DualVector) Drehung um einen beliebigen DualVector um 
 *   einen dual-winkel
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class DualVector3d {
    
    // Plücker- or Grassmann coordinates, homogeneous line coordinates
    private Vector3d e; // direction of the line (covariant components), spear vector
    private Vector3d k; // point of the line represented as a moment (contravariant components), moment of the spear
    
    private DualVector3d(){
        this.e = new Vector3d();
        this.k = new Vector3d();
    }
    public DualVector3d(DualVector3d s){
        this.e = new Vector3d(s.real());
        this.k = new Vector3d(s.dual());
    }
    public DualVector3d(DualNumber x, DualNumber y, DualNumber z){
        this.e = new Vector3d(x.real(), y.real(), z.real());
        this.k = new Vector3d(x.dual(), y.dual(), z.dual());
    }
    /**
     * Build a dual vector from a line direction and the lines moment vector.
     * 
     * General line vectors depend upon five independent scalars.<p>
     *
     * @param e direction of the line
     * @param k moment vector of the line
     */
    public DualVector3d(Vector3d e, Vector3d k){
        if (e== null || (e.x==0d && e.y==0d && e.z==0d) || 
                        (Double.isNaN(e.x) || Double.isNaN(e.y) || Double.isNaN(e.z))) 
            throw new IllegalArgumentException("DualVector3d(): Argument e==null, e==(0,0,0), NaN-values not allowed!");
        if (k== null || (Double.isNaN(k.x) || Double.isNaN(k.y) || Double.isNaN(k.z))) 
            throw new IllegalArgumentException("DualVector3d(): Argument k==null, NaN-values not allowed!");
        
        // k=(0,0,0) sollte grundsäzlich erlaubt sein, vermutlich sind da nur 
        // Operationen die auf DualVector3d definiert sind nicht mehr korrekt implementiert
        //FIXME
        //if (k.x==0d && k.y==0d && k.z==0d)
        //    throw new IllegalArgumentException("DualVector3d(): Argument k== (0,0,0) not allowed!");
        
        this.e = new Vector3d(e);
        //this.e.normalize();
        this.k = new Vector3d(k);
        // Achtung: k darf nicht normalisiert werden
    }
    /**
     * Build a dual vector from point and direction of a line.
     * 
     * General line vectors depend upon five independent scalars.
     * 
     * @param c Point on a straight line 
     * @param e Direction (must not be normalized?) of a straight line
     * @throws IllegalArgumentException if c==null, c==(0,0,0), e==null, e==(0,0,0), NaN-values
     */
    public DualVector3d(Point3d c, Vector3d e){
        if (e == null){
            throw new IllegalArgumentException("DualVector3d(): Argument e==null not allowed!");
        }
        if (e.x==0d && e.y==0d && e.z==0d) {
            throw new IllegalArgumentException("DualVector3d(): Argument e==(0,0,0) not allowed!");
        }
        if (Double.isNaN(e.x) || Double.isNaN(e.y) || Double.isNaN(e.z)){
            throw new IllegalArgumentException("DualVector3d(): Argument e: NaN-values not allowed!");
        }
        
        this.e = new Vector3d(e);
        
        if (c == null){
             throw new IllegalArgumentException("DualVector3d(): Argument c==null not allowed!");
        } 
        if (Double.isNaN(c.x) || Double.isNaN(c.y) || Double.isNaN(c.z)){
             throw new IllegalArgumentException("DualVector3d(): Argument c, NaN-values not allowed!");
        }
        // sollte eigentlich erlaubt sein, oder?
        if (c.x==0d && c.y==0d && c.z==0d) {
            throw new IllegalArgumentException("DualVector3d(): Argument c= (0,0,0) not allowed!");
        }
        
        k = new Vector3d();
        k.cross(new Vector3d(c), e);
    }
    public DualNumber getX(){
        return new DualNumber(e.x, k.x);
    }
    public DualNumber getY(){
        return new DualNumber(e.y, k.y);
    }
    public DualNumber getZ(){
        return new DualNumber(e.z, k.z);
    }
    public Vector3d real(){
        return e;
    }
    public void setReal(Vector3d e){
        this.e = e;
    }
    // der Betrag von k also |k| gives the distance from the origin to the line
    // if k=(0,0,0) the line is through the origin
    public Vector3d dual(){
        return k;
    }
    public void setDual(Vector3d k){
        this.k = k;
    }
    
    /**
     * Sum of two dual vectors.
     * 
     * Pennestrı̀ 2007
     * 
     * TODO unvollständig
     * 
     * @param s second dual vector
     * @return sum of two dual vectors
     */
    public DualVector3d add(DualVector3d s){
        DualVector3d E1 = new DualVector3d(this);
        E1.normalize();
        DualVector3d E2 = new DualVector3d(s);
        E2.normalize();
        
        DualNumber theta = E1.dualAngle(E2);
        
        // senkrecht zu E1 und E2
        DualVector3d E12 = new DualVector3d();
        E12.cross(E1,E2); 
        
        // module of the dual vector sum
        DualNumber module = this.dot(s);
        module.scale(2d);
        module.add(this.dot(this));
        module.add(s.dot(s));
        module.sqrt();
        
        DualNumber sinAlpha1 = theta.sin().mul(s.dot(s).sqrt()).div(module);
        DualNumber cosAlpha1 = this.dot(this).sqrt().div(module).add(sinAlpha1.mul(theta.cos().div(theta.sin())));
        DualNumber t = DualNumber.atan2(sinAlpha1, cosAlpha1);
        t.scale(0.5);
        
        DualVector3d T = new DualVector3d(E12);
        T = T.scale(t);
        
        DualVector3d result = new DualVector3d(E1);
        //TODO
        result.scale(module);
        
        return null;
    }
    
    /**
     * Determine an arbitrary point on the line represented by this dual vector.
     * 
     * @return point on the line
     * @throws IllegalArgumentException if real()=(0d,0d,0d)
     */
    public Point3d getP(){
        if (e.length() != 0d){
            Vector3d result = cross();
            result.scale(1d/e.lengthSquared());
            return new Point3d(result);
        } else {
            throw new IllegalArgumentException("P can not be calculated because real()=(0d,0d,0d)!");
        }
    }
 
    /**
     * Determine the angle to the given ray projected on the given vector.
     * 
     * @param s2
     * @param n
     * @return angle in [rad]
     */
    public double angle(DualVector3d s2, Vector3d n){
        double x = e.dot(s2.real());
        Vector3d test = new Vector3d();
        test.cross(e, s2.real());
        double y = test.dot(n);
        return Math.atan2(y, x);
    }
    
    /**
     * Creates a unit dual vector by normalization.
     * 
     * Sign is not defined?<p>
     * 
     * @throws IllegalArgumentException if e.length() == 0d;
     * 
     * TODO
     * // Vergleich mit Division durch magnitude()
     */
    public void normalize(){
        double eLength = e.length();
        if (eLength != 0d){
            Vector3d k1 = k;
            k = new Vector3d();
            k.cross(k1, e);
            k.cross(e, k);
            k.scale(1d/(eLength*eLength*eLength));
            e.normalize();
        } else {
            throw new IllegalArgumentException("e.length()==0d not allowed!");
        }
    }
    /**
     * Normalize a dual vector with real().length() == 0d.
     * 
     * @param v must be not parallel to k
     * @throws IllegalArgumentException if v parallel to k
     */
    public void normalize(Vector3d v){
        double a = e.x/v.x;
        if (e.y/v.y != a || e.z/v.z != a){
            Vector3d a0 = new Vector3d(k);
            a0.normalize();
            e = new Vector3d(a0);
            v.cross(v,a0);
            k = v;
        } else {
            throw new IllegalArgumentException("Argument v must not be the same direction as e!");
        }
    }
    /**
     * Determines the magnitude of the dual vector.
     * 
     * General rigid body motion parameterization using modified Cayley transform 
     * for dual tensors and dual quaternions. May 2016 Conference: The 4 th Joint 
     * International Conference on Multibody System Dynamics, May 29 - June 2, 
     * Montreal, Canada At: Montréal, Canada
     * Daniel Condurache A. and Burlacu A. Burlacu<p>
     * 
     * @return magnitude of the dual vector
     */
    public DualNumber magnitude(){
        double eLength = e.length();
        if (eLength == 0){
            return new DualNumber(0d, k.length());
        } else {
            return new DualNumber(eLength, k.dot(e)/eLength);
        }
    }
    // euclidian norm
    //Angeles1998
    public DualNumber lengthSquare(){
        return new DualNumber(real().dot(real()), 2d*real().dot(dual()));
    }
    /**
     * Determines the distance of the line to the origin of the coordinate system.
     * 
     * @return distance to the origin
     */
    public double distance(){
        if (e.length() == 0d) throw new IllegalArgumentException("e.length() == 0 not allowed!");
        return k.length()/e.length();
    }
    /**
     * Footpoint where the line reaches the distance() to the origin.
     * 
     * @return foot point
     */
    public Point3d foot(){
        Point3d result = new Point3d(cross());
        result.scale(1d/e.lengthSquared());
        return result;
    }
    /**
     * Determine the dual angle between two rays.
     * 
     * This characterised the relative position and orientation of two line vectors
     * together.<p>
     * 
     * The angle is measured counter-clockwise.
     * 
     * @param s2 second ray
     * @return real()==theta, dual()==Abstand der beiden Rays
     * @throws IllegalArgumentException if both lines are parallel
     * 
     * FIXME hat vermutlich falsches Vorzeichen
     * Das stimmt noch gar nicht!!!
     * @Deprecated
     */
    public DualNumber dualAngleOld(DualVector3d s2){
        
        if (this.e.x == s2.real().x && this.e.y == s2.real().y && this.e.z == s2.real().z)
            throw new IllegalArgumentException("Calculation not possible if both lines are parallel!");
        
        DualNumber dot = dot(s2);
        
        double theta1 = Math.acos(dot.real());
        double theta2 = - theta1;
        
        double d1 = -dot.dual()/Math.sin(theta1);
        //double d2 = -dot.dual()/Math.sin(theta2);
        if (d1 > 0){
            return new DualNumber(theta1, d1);
        } else {
            return new DualNumber(theta2, -d1 /*d2*/);
        }
    }
    
    /**
     * Determine the dual angle between two rays.
     * 
     * This characterised the relative position and orientation of two line vectors
     * together. There is an ambiguity: Also the dual angle scaled with -1 is a 
     * correct solution. The reason is that the method does not distinguish between the
     * two given dual vectors. It is not defined which is the first one and which 
     * the second.<p>
     * 
     * To implement Denavit Hartenberg parameters better use an intersection() method
     * to determine the also needed points on the line.
     * 
     * The angle is measured counter-clockwise.<p>
     * 
     * TODO
     * - aufräumen
     * 
     * @param s2 second ray
     * @return real()==theta, dual()==Abstand der beiden Rays
     * @throws IllegalArgumentException if both lines are parallel
     */
    public DualNumber dualAngle(DualVector3d s2){
        
        // beide Geraden parallel
        //TODO
        // kann ich den Fehler nicht anderweitig abfangen
        if (this.e.x == s2.real().x && this.e.y == s2.real().y && this.e.z == s2.real().z){
            //TODO
            // ex entfernen und statt dessen andere Berechnungsmethode
            throw new IllegalArgumentException("Calculation not possible if both lines are parallel!");
        }
        
        DualNumber sinTheta;
        DualNumber cosTheta;
        
        if (real().length() != 0d && s2.real().length() != 0d){
            DualVector3d s12 = new DualVector3d();
            s12.cross(this,s2);
            sinTheta = s12.magnitude();
            cosTheta = dot(s2);
            
        // Determination of unit dual vectors needed
        // Real-value von einem oder beiden dual-vectors == 0
        // aber dann ist doch normalize() nicht mehr definiert?
        //FIXME
        } else {
            DualVector3d E1 = new DualVector3d(this);
            E1.normalize(); 
            DualVector3d E2 = new DualVector3d(s2);
            E2.normalize();
            DualVector3d E3 = new DualVector3d();
            // E1 und E2 dürfen nicht parallel sein
            E3.cross(E2,E1);
            sinTheta = E3.magnitude(); 
            cosTheta = E1.dot(E2);
        }
        
        //TODO unklar warum das gleich sein soll
        // Alternative Pennestri2007
        // This procedure is not valid, if line vectors are parallel. In this case, 
        // there is an infinite set of dual vectors.
        //TODO
        DualVector3d E1 = new DualVector3d(this);
        E1.normalize(); 
        DualVector3d E2 = new DualVector3d(s2);
        E2.normalize();
        DualVector3d E3 = new DualVector3d();
        E3.cross(E1,E2);
        DualVector3d E3N = new DualVector3d(E3);
        E3N.normalize();
        sinTheta = E3.dot(E3N);
        cosTheta = E1.dot(E2);
        return DualNumber.atan2(sinTheta, cosTheta);
    }
    
    /**
     * Tests, if the two rays are co-planar: Laying in a single plane having one
     * common point or are parallel.
     * 
     * Jia2020
     * 
     * @param s2
     * @return true, if the two rays are co-planar (theta=s=0). Then the two
     * lines are coincident or intersect in a unique point.
     */
    public boolean isCoPlanar(DualVector3d s2){
        if (isParallel(s2)){
            return true;
        }
        return (reciprocalProduct(s2) == 0d); 
    }
    /**
     * Are the two lines skewed? That means: no intersection point and not parallel.
     * 
     * Two lines are ether skewed or coplanar.<p>
     * 
     * When the lines are skewed, the sign of the result indicates the sense of crossing: 
     * positive if a right-handed screw takes L into L′, else negative.<p>
     * 
     * If the d sin θ term is not equal to zero, then
     * the lines do not intersect (d != 0) and are not parallel (sin θ != 0).
     * 
     * If d sin θ is equal to zero and the cos θ term does not equal one,
     * then the lines intersect (d=0) and are not parallel. 
     * 
     * If the cos θ term of the dot product is equal to 1 then the lines are parallel
     * and the resultant dual vector of the cross product will have a 0
     * real component. The cross product’s dual component (d cos θ)
     * will be 0 when the lines are identical. <p>
     * 
     * If the d cos θ term is non-zero then the distance, d, can be calculated. <p>
     * 
     * @param s2
     * @return true if the two lines are skewed. In this case the two planes are not coplanar
     * 
     */
    public boolean isSkewed(DualVector3d s2){
        if (isParallel(s2)) return false;
        return (reciprocalProduct(s2) != 0d); 
        
        // or !isCoPlanar(s2)
        
        /*DualNumber test = dot(s2);
        boolean result = false;
        if (test.dual() != 0d) result = true;
        return result;*/
    }
    
    /**
     * Tests, if the two rays are parallel.
     * 
     * Ist das das gleiche wie coaxial? Nein!
     * 
     * @param s2
     * @return true, if the two rays are parallel (theta=0). This includes coaxial
     */
    public boolean isParallel(DualVector3d s2){
        boolean result = false;
        
        // naiver Vergleich der Richtungsvektoren
        /*if (e.dot(s2.real())==0d){
            result = true;
        }*/
        
        // warum nicht das Kreuzprodukt bilden und auf 0 testen?
        // Jia2020 schlägt das vor
        Vector3d test = new Vector3d();
        test.cross(e, s2.real());
        if (test.lengthSquared() == 0d){
            result = true;
        }
        /*if (test.x==0d && test.y==0d && test.z==0d){
            result = true;
        }*/
        
        return result;
    }
    /**
     * ungetestet
     * 
     * @param s2
     * @return true if the two lines are parallel
     */
    public boolean side(DualVector3d s2){
       // a[0]*b[4] + a[1]*b[5] + a[2]*b[3] + a[3]*b[2] + a[4]*b[0] + a[5]*b[1]
       double value = e.x*s2.dual().y+e.y*s2.dual().z+e.z*s2.dual().x+k.x*s2.dual().z+k.y*s2.real().x+k.z*s2.dual().y;
       return value==0d;
    }
    
    /**
     * Test, if both rays are coaxial.
     * 
     * @param s2
     * @return true, if both rays are coaxial
     */
    public boolean isCoaxial(DualVector3d s2){
        DualNumber dot = this.dot(s2);
        boolean result = false;
        if (dot.real() == 0d && dot.dual()==0d) {
            result = true;
        }
        return result;
    }
    
    /**
     * Tests, if the two rays intersects.
     * 
     * l1*m2+l2*m1=0 und l1xl2 != 0, dann gibt einen Schnittpunkt<p>
     * 
     * If d sin θ is equal to zero and the cos θ term does not equal one,
     * then the lines intersect (d=0) and are not parallel.
     * 
     * @param s2
     * @return true, if s=0
     */
    public boolean isIncident(DualVector3d s2){
        if (isParallel(s2)){
            return false;
        }
        if (reciprocalProduct(s2)== 0d){
            return true;
        } else {
            // the two lines are not co-planar.
        }
        return false;
    }
    
    /**
     * Are the lines identical?
     * 
     * The cross product’s dual component (d cos θ)
     * will be 0 when the lines are identical. 
     * If the d cos θ term is non-zero then the distance, d, can be calculated.
     * 
     * @param s2
     * @return true, if both lines are identical
     */
    public boolean isIdentical(DualVector3d s2){
        DualVector3d result = new DualVector3d();
        result.cross(this, s2);
        return result.dual().length() == 0d;
    }
    
    /**
     * Reciprocal product.
     * 
     * The moment of the ray given as argument about the ray defined bei this 
     * object. The product is kommutativ.<p>
     * 
     * The reciprocal product is is positive if and only if the
     * cross product l̂1 × l̂2 is in the direction of p1 − p2, where p1 and p2 are 
     * the feet of the common perpendicular on l1 and l2 and respectively.<p>
     *
     * The reciprocal product is 0 if the lines are parallel oder have an 
     * intersection point.<p>
     * 
     * @param s2
     * @return reciprocal product
     */
    public double reciprocalProduct(DualVector3d s2){
        return e.dot(s2.dual()) + s2.real().dot(k);
    }
    /**
     * Multiplication by a dual number.
     * 
     * @param lambda
     * @return product of the dual vector with a dual number
     */
    public DualVector3d scale(DualNumber lambda){
        return new DualVector3d(getX().mul(lambda), getY().mul(lambda), getZ().mul(lambda));
        // Angeles1998
        /*Vector3d real = new Vector3d(real());
        real.scale(lambda.real());
        Vector3d dual = new Vector3d(real());
        dual.scale(lambda.dual());
        Vector3d temp = new Vector3d(dual());
        temp.scale(lambda.real());
        dual.add(temp);
        return new DualVector3d(real, dual);*/
    }
    /**
     * Line dot (scalar) product.
     * 
     * @param s2
     * @return line dot product = cos (theta-dual)
     */
    public DualNumber dot(DualVector3d s2){
        // Alternative: bisher ungetestet
        // On the Use of Dual Number, Vectors and Matrices in Instantaneous, Spatial Kinematics
        // G.R. Veldkamp
        // Mechanism and Machien Theory, 1976, Vol 11, pp 141-156
        //DualNumber result = new DualNumber(getX().mul(s2.getX()));
        //result.add(getY().mul(s2.getY()));
        //result.add(getZ().mul(s2.getZ()));
        
        return new DualNumber(e.dot(s2.real()), reciprocalProduct(s2));
    }
    
    /**
     * Calculates the (line) cross product between given dual vectors.
     * 
     * = sin(theta-dual)*common-normal-line
     * = sin(theta)+eps*d*cos(theta)<p>
     * 
     * @param s1 first dual vector
     * @param s2 second dual vector
     */
    public void cross(DualVector3d s1, DualVector3d s2){
        e.cross(s1.real(), s2.real());
        
        Vector3d k1 = new Vector3d();
        k1.cross(s1.real(),s2.dual());
        
        Vector3d k2 = new Vector3d();
        k2.cross(s1.dual(), s2.real());
        
        k2.add(k1);
        k.x = k2.x;
        k.y = k2.y;
        k.z = k2.z;
    }
    /**
     * Triple scalar product.
     * 
     * @param s1
     * @param s2
     * @param s3 
     * @return triple scalar product <E1,E2,s3>
     */
    public DualNumber dot(DualVector3d s1, DualVector3d s2, DualVector3d s3){
        DualVector3d temp = new DualVector3d();
        temp.cross(s2, s3);
        return s1.dot(temp);
    }
    
    /**
     * Cross product between real an dual part of the dual vector.
     * 
     * @return cross product of e and k (direction and moment part of the ray)
     */
    public Vector3d cross(){
        Vector3d result = new Vector3d();
        result.cross(e,k);
        return result;
    }
    
    /**
     * Determines a ray which is perpendicular to both (input)-rays.
     * 
     * Jia2020<p>
     * 
     * @param s2 second ray
     * @return ray perpendicular to both given rays.
     */
    public DualVector3d commonPerpendicular(DualVector3d s2){
        Vector3d es = new Vector3d();
        es.cross(e,s2.real());
        Vector3d ms = new Vector3d();
        ms.cross(k,s2.real());
        Vector3d temp = new Vector3d();
        temp.cross(s2.dual(),e);
        ms.sub(temp);
        temp.cross(e, s2.real());
        temp.scale(e.dot(s2.real())*reciprocalProduct(s2)/temp.lengthSquared());
        ms.add(temp);
        return new DualVector3d(es, ms);
    }
    
    /**
     * Determines the feet of common perpendicular.
     * 
     * Linear algebra and numerical algorithms using dual numbers.E.
     * Pennestrı̀, R. Stefanelli
     * Multibody System Dynamics · October 2007
     * DOI: 10.1007/s11044-007-9088-9<p>
     * 
     * following M. A. Gonzáles-Palacios and J. Angeles
     * 
     * TODO
     * -stürzt ab <p>
     * 
     * @param s2
     * @return intersection points
     */
    public Point3d[] intersect2(DualVector3d s2){
        
        //TODO
        // throw ex if
        // the line vectors are  coincident or parallel.
        // was ist mit coaxial?
        if (isParallel(s2)){
            throw new IllegalArgumentException("intersect2() but s1 || s2 is not allowed!");
        }
        
        DualVector3d E1 = new DualVector3d(this);
        E1.normalize();
        DualVector3d E2 = new DualVector3d(s2);
        E2.normalize();
        
        DualVector3d E3 = new DualVector3d();
        E3.cross(E1,E2);
        E3.normalize();
        
        Vector3d q1 = E1.cross();
        
        Vector3d p1 = new Vector3d(q1);
        double h1 = 0d;
        if (q1.length() != 0){
            q1.normalize();
            DualVector3d Q1 = new DualVector3d(q1, new Vector3d());
            // Kreuzprodukt von Q1 und E3 hat einen real-Teil von 0, damit fliege ich für i=1 raus
            h1 = Q1.dualAngle(E3).dual();
        }
        Vector3d temp = E1.real();
        temp.scale(h1);
        p1.add(temp);
        
        Vector3d q2 = E2.cross();
        Vector3d p2 = new Vector3d(q2);
        double h2 = 0d;
        if (q2.length() != 0) {
            q2.normalize();
            DualVector3d Q2 = new DualVector3d(q2, new Vector3d());
            h2 = Q2.dualAngle(E3).dual();
        }
        temp = E2.real();
        temp.scale(h2);
        p2.add(temp);
        
        return new Point3d[]{new Point3d(p1), new Point3d(p2)};
    }
    
    /**
     * Determines the feet of common perpendicular.
     * 
     * Jia 2020<p>
     * 
     * Simple implementation which assumes that the rays are skewed.<p>
     * 
     * TODO
     * - Fehlersuchen, mehrfach überprüft, unklar warum das nicht geht.
     * - vielleicht ist das Ergebnis ja unerwarteter Weise in Plückerkoordinaten?
     * - Erweiterung um parallel Geraden und sich schneidende Geraden<p>
     * 
     * @param s
     * @return feet of common perpendicular
     * @throws IllegalArgumentException if the rays are not skewed
     */
    public Point3d[] intersect(DualVector3d s){
        if (!isSkewed(s)) throw new IllegalArgumentException("Rays must be skewed!");
        
        // Normierung scheint unnötig, also wieder entfernen
        DualVector3d E1 = new DualVector3d(this);
        E1.normalize();
        DualVector3d E2 = new DualVector3d(s);
        E2.normalize();
        
        
        // wenn sich die rays schneiden, dann ergibt sich der Punkt
        // wie folgt, siehe Formel 29 bei Jia2020
        // möglicherweise gehts aber auch einfacher: beide Punkte
        // obiger Formeln sollten zusammenfallen
        if (isIncident(s)){
            Matrix3d I3 = new Matrix3d();
            I3.setIdentity();
            I3.mul(dual().dot(s.real()));
            Matrix3d A = new Matrix3d();
            throw new RuntimeException("not yet implemented!");
        } else {
            Vector3d cross = new Vector3d();
            cross.cross(E1.real(), E2.real());
            double lengthSqr = cross.lengthSquared();

            Vector3d p1 = new Vector3d(E1.real());
            p1.scale(E2.dual().dot(cross));
            
            Vector3d temp = new Vector3d();
            temp.cross(E2.real(), cross);
            temp.cross(E1.dual(), cross);
            temp.negate();
            p1.add(temp);
            p1.scale(1d/lengthSqr);

            Vector3d p2 = new Vector3d(E2.real());
            p2.scale(E1.dual().dot(cross));
            p2.negate();
            temp.cross(E1.real(),cross);
            temp.cross(E2.dual(), temp);
            p2.add(temp);
            p2.scale(1d/lengthSqr);

            return new Point3d[]{new Point3d(p1), new Point3d(p2)};
        }
    }
    /**
     * Intersection of two straingt lines.
     * 
     * The implementation follows:<br>
     * Automatic Extraction of DH Parameters of Serial Manipulators using Line Geometry
     * Rajeevlochana C.G.*,Subir K. Saha*, Shivesh Kumar
     * The 2nd Joint International Conference on Multibody System Dynamics
     * Mai 29-June 1, 2012, Stuttgart, Germany<p>
     * 
     * erscheint mir mittlerweile nicht mehr so elegant, aber die Aufsplittung
     * parallel, schneiden, schief ist gut.<p>
     * 
     * @param s representing seconds straight line
     * @param c2 used only if the two lines are exactly parallel
     * @param c1 used only if the two lines are exactly parallel, in this case this 
     *           point is the first element of the angle array
     * @return two points (feet of the common perpendicular), one on each line 
     * with minimal distance between the two lines, 
     * the intersection point, or if the two lines are parallel use the additional point parameter
     * to define a vector between the two lines, E1||E2 dann return c1 und Aufpunkt auf E2, wenn
     * E1 identisch E2, dann return c1, c2
     */
    public Point3d[] intersect(DualVector3d s, Point3d c1, Point3d c2){
        
        DualVector3d s2 = new DualVector3d(s);
        DualNumber test = this.dot(s2);
        
        // skew
        if (test.dual() != 0d){
            //System.out.println("skewed!");
            Vector3d n = new Vector3d();
            n.cross(e, s2.real());
            n.normalize();

            // Angelehnt am Original, produziert Vorzeichenfehler,
            // da kein atan2()
            /*double cosBeta = test.real(); 
            double sinBeta = Math.sin(Math.acos(cosBeta)); 
            if (test.dual()<0){
                sinBeta = -sinBeta;
            }*/
            
            // Alternative: Da wird intern atan2() verwendet
            // so stimmt jetzt auch das Vorzeichen
            double beta = dualAngle(s).real();
            double cosBeta = Math.cos(beta);
            double sinBeta = Math.sin(beta);
            
            // skewed lines 2.4
            Point3d p1 = new Point3d(real());
            p1.scale((s2.dual().dot(n)-cosBeta*(dual().dot(n)))/sinBeta);
            p1.add(cross());

            Point3d p2 = new Point3d(s2.real());
            p2.scale((-dual().dot(n)+cosBeta*s2.dual().dot(n))/sinBeta);
            p2.add(s2.cross());

            return new Point3d[]{p1,p2};
            
        // parallel, identisch oder Schnittpunkt
        } else {
            DualVector3d s1;
               
            // parallel oder identisch
            if (test.real() == 1d){
                if (c1 == null || c2 == null){
                    throw new IllegalArgumentException("intersect for parallel lines needs c1!=0 and c2!= null!");
                }
                if (c1.x == c2.x && c1.y == c2.y && c1.z == c2.z)
                    throw new IllegalArgumentException("intersect for parallel lines with c1==c2 is not allowed!");
        
                Vector3d c12 = new Vector3d(c2);
                c12.sub(c1);
                Vector3d e1s =  new Vector3d();
                e1s.cross(c12, e);
                Vector3d e1ss = new Vector3d();
                e1ss.cross(e, e1s);
                //e1ss.scale(1d/(e.length()*e1s.length()));
                e1ss.normalize();

                Vector3d k =  new Vector3d();
                k.cross(new Vector3d(c1),e1ss);
                s1 = new DualVector3d(e1ss,k);
            
            // intersecting
            } else {
                s1 = this;
            }
            
            Vector3d p = new Vector3d();
            if (s1.real().dot(s2.dual()) - s2.real().dot(s1.dual()) == 0d){
                p.cross(s2.dual(),s1.dual());
                p.scale(1d/(s1.real().dot(s2.dual())));
            } else {
                p.cross(s1.dual(),s2.dual());
                p.scale(1d/(s2.real().dot(s1.dual())));
            }
            
            // Wenn die beiden Geraden exakt parallel sind
            if (test.real() == 1d){
                // wenn die beiden Geraden identisch sind
                if (e.x==s2.real().x && e.y==s2.real().y && e.z==s2.real().z &&
                    k.x==s2.dual().x && k.y==s2.dual().y && k.z==s2.dual().z){
                    return new Point3d[]{c1, c2};
                // parallel aber nicht(!!!) identisch
                } else {
                    return new Point3d[]{c1, new Point3d(p)};
                }
            // Schnittpunkt
            } else {
                return new Point3d[]{new Point3d(p)};
            }
        }
    }
    
    /**
     * Intersection of the plane through c1 normal to E1, intersecting E2.
     *
     * E2 und E1 dürfen dabei nicht orthogonal zueinander stehen
     * TODO
     *
     * the line E2
     * 
     * @param s2
     * @param c1
     * @return 
     */
    public Point3d[] intersect(DualVector3d s2, Point3d c1){
        return new Point3d[]{c1, intersect(new Plane4d(e,c1))};
    }
    /**
     * Intersection of the line with a given (non parallel) plane.
     * 
     * @param plane
     * @return intersection point of the plane with the line
     */
    public Point3d intersect(Plane4d plane){
        return plane.intersect(this);
    }
    /**
     * Create two dualvectors from the given arguments, which do not include the
     * origin of the coordinate system. 
     * 
     * To do this the coordinate system is translated
     * if needed.
     * 
     * @param t !=null, set to the created translation if one or both straight lines (rays)
     * contains the coordinate systems origin
     * @param c1 point on the first straight line (ray)
     * @param z1 normalized direction of the first straight line (ray)
     * @param c2 point on the seconds staight line (ray)
     * @param z2 normalized direction of the seconds staight line (ray)
     * @return two Dualvectors without intersection of the coordinate systems origin
     * @throws IllegalArgumentException if some components are null, 0,0,0 or NaN
     */
    public static DualVector3d[] createRays(
            Vector3d t, Point3d c1, Vector3d z1, Point3d c2, Vector3d z2){
        
        if (c1 == null){
            throw new IllegalArgumentException("DualVector3d(): Argument c1==null not allowed!");
        }
        /*if (c1.x==0d && c1.y==0d && c1.z==0d) {
            throw new IllegalArgumentException("DualVector3d(): Argument c1==(0,0,0) not allowed!");
        }*/
        if (Double.isNaN(c1.x) || Double.isNaN(c1.y) || Double.isNaN(c1.z)){
            throw new IllegalArgumentException("DualVector3d(): Argument c1: NaN-values not allowed!");
        }
        
        // TODO alle anderen Argumente auch überprüfen
        
        DualVector3d[] result = new DualVector3d[2];
        Point3d c1g = new Point3d(c1);
        Point3d c2g = new Point3d(c2);
        double epsilon = 0.00001;
        // If one of the rays gos through the origin than both rays are translated
        // in direction of its cross product.
        if (dist2Origin(c1, z1) < epsilon || dist2Origin(c2, z2) < epsilon){
            t.cross(z1, z2);
            c1g.sub(t);
            c2g.sub(t);
        } else {
            t.x = 0d; t.y = 0d; t.z = 0d;
        }
        result[0] = new DualVector3d(c1g, z1); 
        result[1] = new DualVector3d(c2g, z2); // IllegalArgumentException mit z2 mindestens eine component ist nan
        return result;
    }
    /**
     * Distance of a given line (o, result) to the origin of the coordinate system.
     * 
     * @param o origin of a line
     * @param n normal vector of a line
     * @return distance of the line to the origin
     */
    public static double dist2Origin(Point3d o, Vector3d n){
        return dist(o,n,new Point3d());
    }
    /**
     * Distance of a point p from the line (o,result).
     * 
     * @param o a point on a line
     * @param n normal vector of a line
     * @param p a point to which the distance is calculated.
     * 
     * @return distance between a line defined by normalvector and a point to a given point.
     */
    public static double dist(Point3d o, Vector3d n, Point3d p){
        Vector3d n1 = new Vector3d(n);
        n1.normalize();
        Vector3d p1 = new Vector3d(p);
        p1.sub(o);
        p1.cross(p1,n);
        return p1.length();
    }
     /**
     * Directed distance between two straight lines.
     * 
     * FIXME
     * Ich bekomme falsches Vorzeichen 
     * 
     * @param s2
     * @return directed distance
     * @Deprecated
     * 
     */
    public double ddist(DualVector3d s2){
        DualNumber test = dot(s2);
        double angle = Math.acos(test.real());
        //temp.cross(z1,z2);
        //this.alpha = Math.atan2(temp.dot(x2),z1.dot(z2));
        //FIXME
        if (test.dual()<0){
            angle = -angle;
        }
        return test.dual()/(-Math.sin(angle));
    }
    
    /**
     * Perpendicular distance between two skewed or parallel rays.
     * 
     * Jia 2020<p>
     * 
     * FIXME (r2) hat falsches Vorzeichen bei der ersten und dritten Achse, bei
     * fast parallelen Achse viel zu große Werte. Vermutlich da dann das Kreuzprodukt
     * sehr schlecht definiert ist. <p>
     * 
     * @param s2
     * @return perpendicular distance, positive if and only if the
     * cross product s1 × s2 is in the direction of p1 − p2, where p1 and p2 are 
     * the feet of the common perpendicular on E1 and E2 and respectively
     * @Deprecated use dualAngle2() instead
     */
    public double dist(DualVector3d s2){
        double result;
        
        Vector3d cross = new Vector3d();
        cross.cross(e, s2.real());
        
        // verschraubt?
        if (isSkewed(s2)){
            result = reciprocalProduct(s2)/cross.lengthSquared();
        // parallel, identisch oder Schnittpunkt
        // FIXME
        // der zweig ist vermutlich noch ungetestet
        // isIdentical(), isIncident(), isCoaxial()
        } else {
            double s = s2.real().x/e.x;
            Vector3d m = new Vector3d(s2.dual());
            m.scale(-1d/s);
            m.add(k);
            cross.cross(e,m);
            result =  cross.length()/e.lengthSquared(); 
        }
        return result;
    }
    
    private static String toString(String name, Tuple3d value){
        return name+" = ("+String.valueOf(value.x)+","+String.valueOf(value.y)+","+String.valueOf(value.z)+")";
    }
    public String toString(String name){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(": ");
        sb.append(toString("k", this.k));
        sb.append(", ");
        sb.append(toString("e", this.e));
        return sb.toString();
    }
}
    