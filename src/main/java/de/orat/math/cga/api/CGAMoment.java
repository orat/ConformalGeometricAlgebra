package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * 
 * ist das jetzt IPNS oder OPNS?
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMoment extends CGAAttitudeVectorOPNS {
    
    
    public CGAMoment(Vector3d moment){
        // das passt zu OPNS Moment
        // aber vielleicht ist ja auch CGAAttitudeVectorOPNS falsch und IPNS
        //FIXME
        //super((new CGAEuclideanVector(moment)).op(inf));
        super(moment);
    }
    
    public CGAMoment(CGARoundPointIPNS location, CGAForceIPNS force){
        super(force.ip(location).op(inf));
    }
}
