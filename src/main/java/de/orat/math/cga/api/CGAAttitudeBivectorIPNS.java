package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeBivectorIPNS extends CGAAttitudeIPNS {
    
    public CGAAttitudeBivectorIPNS(CGAMultivector m){
        super(m);
    }
    
    protected CGAAttitudeBivectorIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // decomposition
    
    @Override
    public Vector3d direction(){
        // das ist noch nicht getestet
        //return extractAttitudeFromBivectorEinfRepresentation();
        CGAMultivector m = extractGrade(3).rc(o).negate().lc(I3i); //euclideanDual(); //extractE3ToVector3d();
        //System.out.println("###"+m.toString("extractAttFromBiVecEinf")+" "+toString("orig")+
        //        " vec=("+String.valueOf(v.x)+","+String.valueOf(v.y)+","+String.valueOf(v.z)+")");
        return m.extractE3ToVector3d();
    }
}
