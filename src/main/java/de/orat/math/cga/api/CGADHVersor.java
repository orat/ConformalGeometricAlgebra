package de.orat.math.cga.api;

/**
 * TODO
 * durch Schraubachse ersetzen?
 * 
 * Linie hat 4 Parameter und wird zur Schraubachse durch zusätzlich 2 Parameter:
 * Winkel und Ganghöhe (pitch?)
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADHVersor extends CGAVersor {

    public CGADHVersor(CGAMultivector m) {
        super(m);
    }
    
    /**
     * @param v1, v2 normalized daher d, a extra - ist das sinnvoll? 
     * @param theta
     * @param a
     * @param alpha 
     */
    public CGADHVersor(CGAEuclideanVector v1, CGAEuclideanVector v2, double theta, 
            double d, double a, double alpha){
        this(create(v1,v2, theta, d, a, alpha));
    }

    // aber wenn v1, v2 die beiden Gelenkachsen sind, dann wäre doch alpha darüber
    // bereits festgelegt
    // eventuell sogar statt CGAEuclideanVector gleich Lines verwenden, dann wäre
    // damit aber alle Parameter fix? Das macht keinen Sinn
    private static CGADHVersor create(CGAEuclideanVector v1, CGAEuclideanVector v2,
                                      double theta, double d, double a, double alpha){
        CGAEuclideanBivector Bxy = new CGAEuclideanBivector(v1,v2);
        CGARotor Rz = new CGARotor(Bxy, theta);
        CGAEuclideanVector z = Bxy.euclideanDual();
        CGATranslator Tz = new CGATranslator(z.gp(d));
        CGATranslator Tx = new CGATranslator(v1.gp(a));
        CGAEuclideanBivector Byz = new CGAEuclideanBivector(v2,z);
        CGARotor Rx = new CGARotor(Byz, alpha);
        return new CGADHVersor(Rz.gp(Tz).gp(Tx).gp(Rx));
    }
    
    @Override
    public boolean isEven() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
