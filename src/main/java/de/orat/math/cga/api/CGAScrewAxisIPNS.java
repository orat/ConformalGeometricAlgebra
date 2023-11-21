package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * CGALineIPNS + Vielfaches von einf, entsteht bei der Addition von CGALineIPNS-Objekten?
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAScrewAxisIPNS extends CGAFlatIPNS implements iCGABivector {
    
    public CGAScrewAxisIPNS(CGAMultivector m) {
        super(m);
    }
    
    public CGAScrewAxisIPNS(Point3d p, Vector3d dir){
        super(createScrew(p, dir));
    }
    
    public CGAScrewAxisIPNS(CGALineIPNS l, CGATranslator T){
        super(l.add(T));
    }
    
    /**
     * 
     * @param p
     * @param dir |dir|=Ganhhöhe
     * @return 
     */
    private static CGAMultivector createScrew(Point3d p, Vector3d dir){
        double h = dir.length();
        Vector3d dirN = new Vector3d(dir);
        dirN.normalize();
        CGAMultivector m = new CGAEuclideanVector(dirN);
        CGAMultivector summand1 = m.gp(I3);
        CGAMultivector summand2 = (new CGAEuclideanVector(p)).op(m).gp(I3.gp(inf));
        CGAMultivector summand3 = (new CGAEuclideanVector(dir)).gp(inf);
        return summand1.add(summand2).add(summand3);
    }
    
    /*public double getScrewAxis(){
    
    }*/
    public double getPitch(){
        //TODO
        return -1;
    }
     
    // ungetestet
    public CGALineIPNS getLineIPNS(){
        //TODO unklar ob das so funktioniert
        // der code eignet sich zumindest, wenn ich einen Nullvektor sprich einen roundpoint
        // + ein Vielfaches von inf als Multivektor habe, so wie ich das bekommen wenn ich 2 Punkte
        // addiert und durch 2 teile
        return new CGALineIPNS(gp(inf).gp(this).negate().div(ip(inf).sqr().gp(2)));
    }
}
