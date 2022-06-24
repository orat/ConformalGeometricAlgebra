package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 *
 * A motor is no blade because it contains blades of grade 0, 2 and 4 
 * all together.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMotor extends CGAMultivector {
    
    public CGAMotor(CGAMultivector m){
        super(m.impl);
    }
    
    public CGAMotor(CGABivector B, Vector3d d){
        this(B.add(createE3(d).gp(createInf(1d))));
    }
}
