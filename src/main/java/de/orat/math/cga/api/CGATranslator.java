package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Translator a versor.
 * 
 * A translator is no blade because it contains blades of grades 0 and 2 both:
 * 
 * 1.0, // grade 0
 * e1^ni,    e2^ni,    e3^ni    // grade 2
 * 
 * Translator rotors are generated by a lightlike Direction Vector d = e1 ∞ + e2 ∞ + e3 ∞
 * with d2 = 0 and weighted bases. 
 * 
 * They can be considered a double reflection in parallel planes, and can be 
 * algorithmically generated as the ratio of two flat points.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATranslator extends CGAVersor {
    
    public CGATranslator(CGAMultivector m){
        super(m.impl);
        // test auf blades 0,2
    }
    
    public CGATranslator(Vector3d d){
        //this(createInf(1d).gp(createE3(d)).gp(0.5).exp());
        this((new CGAScalar(1d)).sub(createE3(d).gp(inf.gp(0.5d))));
    }

    @Override
    public boolean isEven() {
        return true;
    }
    /*private void test(){
        this.grade()
    }*/
}
