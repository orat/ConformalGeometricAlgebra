package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * e1^e2^e3^ei
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeScalarIPNS extends CGAAttitudeIPNS {
    
    public CGAAttitudeScalarIPNS(CGAMultivector m) {
        super(m.compress());
    }
    
    protected CGAAttitudeScalarIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // composition
    
    public CGAAttitudeScalarIPNS(double scalar){
        super(createInf(scalar));
        //TODO wie kann ich das direkt ohne dual bauen?
        //FIXME mit dem dual wechselt das Vorzeichen. Ist das korrekt?
        impl = impl.dual();
        //compress();
    }
    
    
    // etc
    
    @Override
    public void testGrade(){
        // test sollte noch darauf erfolgen dass nur die component inf != 0 ist
        //TODO
        int grade = grade();
        if (grade != 4 && grade != 0) throw new IllegalArgumentException("The given multivector is not of grade 4 or a null vector:");
    }
    
    public CGAAttitudeScalarOPNS undual(){
        return new CGAAttitudeScalarOPNS(super.undual()/*dual().negate()*/.compress());
    }
    
    
    // decomposition
    
    @Override
    public Vector3d direction(){
        return new Vector3d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
}
