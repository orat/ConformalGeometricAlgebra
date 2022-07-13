package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualRoundPoint extends CGADualRound implements iCGAQuadvector {
    
    public CGADualRoundPoint(CGAMultivector m){
        super(m);
    }
    
    /**
     * Create a conformal result in inner product null space representation 
     * (grade 4 multivector).
     * 
     * @param sphere1 first sphere in inner product null space representation
     * @param sphere2 seconds sphere in inner product null space represenation
     * @param sphere3 third sphere in inner product null space representation
     * @param sphere4 forth sphere in inner product null space represenation
     */
    public CGADualRoundPoint(CGASphere sphere1, CGASphere sphere2, 
                        CGASphere sphere3, CGASphere sphere4){
        this(sphere1.op(sphere2).op(sphere3).op(sphere4));
    }
    
    public CGARoundPoint undual(){
        return new CGARoundPoint(impl.undual());
    }
}