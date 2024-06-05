package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.eps;
import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Translator, a versor.
 * 
 * A translator is no blade, because it contains blades of grades 0 and 2 both:<p>
 * 
 * 1.0 (grade 0) <br>
 * e1^ni,    e2^ni,    e3^ni   (grade 2)<p>
 * 
 * Translator rotors are generated by a lightlike Direction Vector d = e1 ∞ + e2 ∞ + e3 ∞
 * with d2 = 0 and weighted bases.<p>
 * 
 * They can be considered a double reflection in parallel planes, and can be 
 * algorithmically generated as the ratio of two flat points.<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATranslator extends CGAVersor {
    
    public CGATranslator(CGAMultivector m){
        super(m.impl);
        //TODO vorhandener python code nach java portieren?
        // auskommentierten Code aus iCGAMultivector vergleichen für den Test ob
        // es sich bei m um einen versor handelt
        test(m.impl);
        // oder auf folgende blades explizit testen:
        // 1, e1inf, e2inf, e3inf
    }
    
    public Vector3d decompose(){
        Vector3d result = extractE3FromE3einf();
        result.scale(-2d);
        return result;
    }
    
    public CGATranslator(Vector3d d){
        //this(createInf(1d).gp(createE3(d)).gp(0.5).exp());
        this((new CGAScalarOPNS(1d)).sub(createE3(d).gp(inf.gp(0.5d))));
    }

    
    @Override
    public boolean isEven() {
        return true;
    }
    
     
    // test of 0,2 blades
    // and with gp with reverse == 1
    private void test(iCGAMultivector m){
        if (decomposeScalar() != 1) throw new IllegalArgumentException("The given multivector is no translator!");
        int[] grades = m.grades();
        if (grades.length == 1 && grades[0] != 0){
            throw new IllegalArgumentException("The given multivector of grade 0 is no translator because the scalar != 1!");
        } else if (grades.length != 1 && grades.length != 2) {
            throw new IllegalArgumentException("The given multivector is no translator it includes more than two grades!");
        }
        if (grades[0] != 0 && grades[1] != 2) throw new IllegalArgumentException("The given multivector is no translator!");
        if (!m.reverse().gp(m).isOne(eps)) throw new IllegalArgumentException("The given multivector is no translator!");
    }
}
