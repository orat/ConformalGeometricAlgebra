package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAScalor extends CGAVersor {
    
    public CGAScalor(CGAMultivector m){
        super(m);
    }
    public void testGrade(){
        // test auf o^einf und scalar only
    }

    @Override
    public boolean isEven() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
