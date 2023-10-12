package de.orat.math.cga.api;

/**
 * Forque, Wrench, Screw-force.
 *
 * TODO
 * statt von CGAFlatIPNS von CGAScrewAxisIPNS erben sobald ich das implementiert habe
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAForqueIPNS extends CGAFlatIPNS {
    
    public CGAForqueIPNS(double[] values) {
        super(values);
    }
    public CGAForqueIPNS(CGAMultivector m){
        super(m.impl);
    }
    
    @Override
    public CGAAttitudeVectorOPNS attitudeIntern(){
        return new CGAAttitudeVectorOPNS(super.attitudeIntern());
    }
}
