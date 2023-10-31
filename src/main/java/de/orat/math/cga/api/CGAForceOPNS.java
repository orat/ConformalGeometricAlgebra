package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAForceOPNS extends CGALineOPNS {
    
    public CGAForceOPNS(CGAMultivector m){
        super(m);
    }
    
    public CGAForceOPNS(Point3d location, Vector3d force){
        super(location, new Point3d(location.x+force.x, location.y+force.y, location.z+force.z));
    }
    
    public CGAForceIPNS dual(){
        return new CGAForceIPNS(super.dual());
    }
    /*public CGAForqueOPNS add(CGAForceOPNS force){
        return new CGAForqueOPNS(super.add(force));
    }
    public CGAForqueOPNS sub(CGAForceOPNS force){
        return new CGAForqueOPNS(super.sub(force));
    }*/
}
