package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Vector3d;

/**
 * A general rotor - rotation around an axis (no need of the axis to pass the origin).
 * 
 * 
 * 1.0,                                         // grade 0<br>
 * e1^e2, e2^e3, e3^e1, eo^e1, eo^e2, eo^e3     // grade 2<br>
 * eo^e1^e2^e3                                  // grade 4<p>
 * 
 * This is versor.The geometric product of an even number of unit vectors.<p>
 *
 * Hints: - A rotator is no blade, because it contains blades of grade 0 and 2 both.<br>
 *        - The inverse of a rotor is identical with the reverse.<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGARotor extends CGAVersor {
    
    CGARotor(iCGAMultivector impl){
        super(impl);
        test(impl);
    }
    public CGARotor(CGAMultivector m){
        super(m.impl);
        test(m.impl);
    }
    
    // test of 0,2 blades
    // and with gp with reverse == 0
    protected void test(iCGAMultivector m){
        int[] grades = m.grades();
        if (grades.length != 2) throw new IllegalArgumentException("The given multivector is no rotor!");
        if (grades[0] != 0 && grades[1] != 2) throw new IllegalArgumentException("The given multivector is no rotor!");
        if (!m.reverse().gp(m).isOne(eps)) throw new IllegalArgumentException("The given multivector is no rotor!");
    }
    
    
    // composition 
    
    /**
     * Constructs a rotor with rotation axis not passing the origin, sometimes
     * called general rotation or twist.
     * 
     * @param B rotation axis as a normalized bivector
     * @param theta 
     */
    public CGARotor(CGALineIPNS B, double theta){
        this(B.gp(-theta/2d).exp());
    }
    
    // Dorst s. 488
    // translation, rotation aber auch scaling kann aus der matrix rausgeholt werden
    // und im rotor formuliert werden
    /*public CGARotor(Matrix4d m){
        // normalize
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                
                m.setElement(i, j, value);
            }
        }
    }*/
    
    
    // decomposition
    
    // [Dorst2007]-book 13.15.
    public CGASpinor splitSpinor(){
        return new CGASpinor(o.negate().lc(this.gp(inf)));
    }
    // [Dorst2007]-book 13.15.
    public Vector3d decomposeTranslation(CGASpinor spinor){
        CGAMultivector m = o.lc(this).gp(-2d).div(spinor);
        return m.extractE3ToVector3d();
    }
    
    public Matrix4d decompose4PointAndVector(){
        // s, eo, e1, eo^e1, e2, eo^e2, e1^e2, eo^e1^e2, e3, eo^e3, e1^e3, eo^e1^e3,
        // e2^e3, eo^e2^e3, e1^e2^e3, eo^e1^e2^e3, ei, eo^ei, e1^ei, eo^e1^ei,
        // e2^ei, eo^e2^ei, e1^e2^ei, eo^e1^e2^ei, e3^ei, eo^e3^ei, e1^e3^ei, 
        // eo^e1^e3^ei, e2^e3^ei, eo^e2^e3^ei, e1^e2^e3^ei, eo^e1^e2^e3^ei
        double[] coords = extractCoordinates();
        
        double R0 = coords[0]; // s
        double R1 = coords[3]; // e01
        double R2 = coords[5]; // e02
        double R3 = coords[9]; // e03
        double R4 = coords[6]; // e12
        double R5 = -coords[10]; //e31
        double R6 = coords[12]; // e23
        double R7 = coords[15]; // e0123
        
        double R44 = R4*R4;
        double R66 = R5*R5;
        double R55 = R6*R6;
        
        double R45 = R4*R5;
        double R56 = R5*R6;
        double R46 = R4*R6;
        
        double R04 = R0*R4;
        double R05 = R0*R5;
        double R06 = R0*R6;
        
        Matrix4d result = new Matrix4d(-R44-R55/*m00*/, R04+R56/*m01*/, R46-R05/*m02*/, R3*R5-R0*R1-R2*R4-R6*R7/*m03*/,
		    R56-R04/*m10*/, -R44-R66/*m11*/, R06+R45/*m12*/, R1*R4-R0*R2-R3*R6-R5*R7/*m13*/,
		    R05+R46/*m20*/, R45-R06/*m21*/, -R55-R66/*m22*/, R2*R6-R0*R3-R1*R5-R4*R7/*m23*/,
		    0d/*m30*/, 0d/*m31*/, 0d/*m32*/, 0d/*m33*/);
        result.mul(2d);
        Matrix4d identity = new Matrix4d();
        identity.setIdentity();
        result.add(identity);
        return result;
    }
    
    /**
     * Decompose the rotor into a 4x4-matrix to rotate a plane given by
     * [a,b,c,d] with ae1+be2+ce3+de0 describing the plane.
     * 
     * @return rotation matrix for rotating a plane
     */
    public Matrix4d decompose4Plane(){
        double[] coords = extractCoordinates();
        
        double R0 = coords[0]; // s
        double R1 = coords[3]; // e01
        double R2 = coords[5]; // e02
        double R3 = coords[9]; // e03
        double R4 = coords[6]; // e12
        double R5 = -coords[10]; //e31
        double R6 = coords[12]; // e23
        double R7 = coords[15]; // e0123
        Matrix4d result = new Matrix4d(-R4*R4-R5*R5, R0*R5+R5*R6, R4*R6+R0*R5,0,
                                       R5*R6-R0*R4, -R4*R4-R6*R6, R0*R6+R4*R5,0,
                                       R0*R5+R4*R6, R4*R5-R0*R6, -R5*R5-R6*R6, 0,
                                      R0*R1-R2*R4+R3*R5+R6*R7, R0*R2+R1*R4-R3*R6+R5*R7,R0*R3-R1*R5+R2*R6+R4*R7 ,0);
        result.mul(2d, result);
        Matrix4d identity = new Matrix4d();
        identity.setIdentity();
        result.add(identity);
        return result;
    }
     
    // decomposition into DualQuaternion
    //TODO
    
    
    /**
     * An more efficient implementation can use the information that the 
     * multivector is a versor.
     * 
     * The inverse of a basis blade coincides with the conjugate.
     * 
     * @return the default implementation is identical to generalInverse()
     */
    @Override
    public CGARotor inverse(){
        //TODO eine spezifische implementation for versors only bauen
        return new CGARotor(impl.versorInverse());
    }

    @Override
    public boolean isEven() {
        return true;
    }
}