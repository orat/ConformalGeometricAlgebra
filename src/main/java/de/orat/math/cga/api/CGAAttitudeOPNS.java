package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Attitudes, also called free or direction vectors (directions), free k-blades 
 * are elements without position. 
 * 
 * They are made by wedging any Euclidean element (vector, bivector, or 
 * trivector) with ∞. Directions are invariant under translations 
 * (they do not change if moved), but they can of course be rotated.<p>

 * They represent directions without a location. They are translation 
 * invariant but rotation covariant.<p>
 * 
 * This means there is no e0-component in its formula.<p>
 * 
 * This is the base class for all attitude classes (Vector, Bivector, TreeVector).<p>
 * 
 * [Dorst2007] p.376<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeOPNS extends CGAKVector implements iCGAAttitude {
    
    public CGAAttitudeOPNS(CGAMultivector m){
        super(m.impl);
    }
    
    protected CGAAttitudeOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    public static boolean is(CGAMultivector m){
        if (!inf.op(m).isNull()) return false;
        return inf.lc(m).isNull();
    }
    
    
    // decomposition
    
    public Vector3d direction(){
        throw new RuntimeException("Implementation only for derviced classes available because implementation is grade depenend!");
    }
    
    
    // etc
    
    @Override
    public CGATangentOPNS inverse(){
        // warum ist das so?
        //TODO
        throw new RuntimeException("An attitude has no inverse!");
    }
    
    @Override
    public CGAAttitudeIPNS dual(){
        return new CGAAttitudeIPNS(impl.dual());
    }
    
    public CGAKVector undual(){
        throw new RuntimeException("undual() not supported for opns attitude!");
    }
}