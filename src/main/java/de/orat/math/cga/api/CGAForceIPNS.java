package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAForceIPNS extends CGALineIPNS {
    
    public CGAForceIPNS(Point3d location, Vector3d force) {
        super(location, normalize(force), force.length());
    }
    
    private static Vector3d normalize(Vector3d force){
        Vector3d normalizedForce = new Vector3d(force);
        normalizedForce.normalize();
        return normalizedForce;
    }
    
    public CGAForqueIPNS add(CGAForceIPNS force){
        return new CGAForqueIPNS(super.add(force));
    }
    public CGAForqueIPNS sub(CGAForceIPNS force){
        return new CGAForqueIPNS(super.sub(force));
    }
}
