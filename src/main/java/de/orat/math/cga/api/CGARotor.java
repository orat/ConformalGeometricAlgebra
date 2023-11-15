package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Quat4d;

/**
 * A Rotor. This is versor. The geometric product of an even number of unit vectors.
 * 
 * 1.0, // grade 0
 * e1^e2,    e2^e3,    e3^e1    // grade 2
 * 
 * Euclidean Spin Rotors are generated by a spacelike Bivector B = e 12 + e 13 + e 23 with
 * B 2 < 0 and weighted bases (i.e. αe 1 , βe 2 , γe 3 ). The exponential expression R = e B
 * with B = I 2 θ admits a familiar expansion: e B = cos θ 2 − si n θ 2 I .
 * 
 * Hints: - A rotator is no blade, because it contains blades of grade 0 and 2 both.
 *        - The inverse of a rotor is identical with the reverse.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGARotor extends CGAVersor {
    
    CGARotor(iCGAMultivector impl){
        super(impl);
    }
    public CGARotor(CGAMultivector m){
        super(m.impl);
        //TODO
        // test auf 0,2 blades
    }
    
    
    // composition 
    
    /**
     * @param B normalized bivector representing the rotation axis
     * @param theta in radians
     */
    public CGARotor(CGABivector B, double theta){
        this(B.gp(-theta/2d).exp());
    }
    
    /*public CGAMultivector rotate(CGAMultivector m){
        return transform(m);
    }*/
    
    
    // decomposition
    
    /**
     * Decompose rotation around origin.
     * 
     * @return quaternion representing a rotation around the origin
     */
    /*public Quat4d decompose(){
        Quat4d result = new Quat4d();
        //FIXME grade = 0 als argument scheint falsch zu sein
        // getOriginIndex() gibts nicht mehr, statt dessen vielleicht getOriginValue() einführen?
        result.w = impl.extractCoordinates(0)[impl.getOriginIndex()];
        double[] vector = impl.extractCoordinates(2);
        //TODO weitere Indizes definieren
        result.x = -vector[4]; // i
        result.y = vector[5];  // j
        result.z = -vector[8]; // k
        return result;
    }*/
    
    /**
     * An more efficient implementation can use the information that the multivector 
     * a versor.
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
