package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * A k-Vector is a multivector which is a linear combination of the 32 basis blades 
 * (in form of a linear combination) of the same grade k (0..5). 
 * 
 * It is also called blade or k-blade or shorter a blade.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAKVector extends CGAMultivector implements iCGABlade {
    
    CGAKVector(CGAMultivector m){
        super(m.impl);
        try {
            testGrade();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage()+" "+impl.toString());
            throw(e);
        }
    }
    CGAKVector(iCGAMultivector impl){
        super(impl);
        try {
            testGrade();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage()+" "+impl.toString());
            throw(e);
        }
    }
    /*CGAKVector(double value){
        super(value);
    }*/
    
     // decomposeMotor
   
    /**
     * Determine direction/attitude from tangent or round objects in OPNS 
     * representation.
     * 
     * @ipns true for blade in ipns representation
     * @return attitude
     */
    CGAAttitudeOPNS attitudeFromTangentAndRound2(boolean ipns){
        // e.g. -1.9999999999999982*e1^e2^e3^ei 
        // grade 4 if invoked from a sphere
        // Dorst2007 p. 562
        
        // e.g. attitude=1.9999999999999982*e2^e3^ei
        // grade 3, if invoked from a circle 
        CGAKVector m = this;
        if (ipns) m = m.dual();
        CGAAttitudeOPNS result = new CGAAttitudeOPNS(inf.lc(m).op(inf).negate().compress());
        System.out.println(result.toString("attitude (round/tangent)"));
        
        return result;
    }
    /**
     * Determine direction/attitude from tangent or round objects.
     * 
     * Hildenbrand2004
     * 
     * @return direction/attitude
     */
    /*protected CGAMultivector attitudeFromTangentAndRound(){
        // see errata, dual tangend/round formula Dorst2007
        CGAMultivector einf = CGAMultivector.createInf(1d);
        CGAMultivector einfM = CGAMultivector.createInf(-1d);
        CGAMultivector result = einfM.ip(dual()).op(einf);
        System.out.println("attitude(round/attitude)="+result.toString());
        return result;
    }*/
    /*protected CGAMultivector attitudeFromDualTangentAndDualRound(){
        // see errata, dual tangend/round formula Dorst2007
        CGAMultivector einf = CGAMultivector.createInf(1d);
        CGAMultivector result = (einf.ip(this)).gp(-1d).op(einf);
        System.out.println("attitude(round/attitude)="+result.toString());
        return result;
    }*/
    
    
    /**
     * Determine the location of the geometric object, which is represented by
     * the k-Vector. 
     * 
     * For a flat object this is defined by the perpendicular 
     * distance vector of the origin to the carrier plane.
     * 
     * @return location
     */
    /*@Override
    public Point3d location(){
        CGAMultivector result = carrierFlat().inverse().gp(this.op((new CGAScalar(1d).add(I0)))).rc(I0);
        return result.extractE3ToPoint3d();
    }*/
    
    // plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;
    public CGARoundPointIPNS reject(CGARoundPointIPNS p){
        return new CGARoundPointIPNS(p.op(inf).lc(this).gp(p.op(inf)));
    }
    
    @Override
    public CGAKVector undual(){
        //FIXME ist hier Vorzeichen mit gp(-1) überhaupt richtig, oder muss das
        // nicht eine formel sein, die vom grade abhängt?
        return new CGAKVector(super.undual().compress()); //impl.dual().gp(-1));
    }
    @Override
    public CGAKVector dual(){
        return new CGAKVector(impl.dual());
    }
}
