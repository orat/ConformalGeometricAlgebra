package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * A motor/screw is no blade because it contains blades of grade 0, 2 and 4 
 * all together. 
 * 
 * It describes a rotation around a rotation axis combined with a
 * translation in direction of this axis.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMotor extends CGAVersor {
    
    public CGAMotor(CGAMultivector m){
        super(m.impl);
    }
    
    public CGAMotor(CGARotor rotor, CGATranslator translator){
        this(rotor.gp(translator));
    }
    
    public CGAMotor(CGABivector B, Vector3d d){
        this(B.add(createE3(d).gp(createInf(1d))));
    }
}
