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
    
    /**
     * 
     * @param B
     * @param d 
     * @throws IllegalArgumentException if d=(0,0,0)
     */
    public CGAMotor(CGABivector B, Vector3d d){
        this(B.add(createE3(d).gp(inf)));
    }
    
    public record MotorParameters(Vector3d dir, double alpha){}
    
    public MotorParameters decomposeMotor(){
        double cosAlpha = decomposeScalar();
        
        double[] bivector = impl.extractCoordinates(2);
        
        double a = bivector[4];
        double b = bivector[1];
        double c = bivector[0];
        
        double d1 = a/cosAlpha;
        Vector3d dir = new Vector3d(d1,bivector[5]/cosAlpha,bivector[7]/cosAlpha);
        
        //double sinAlpha = bivector[4]/d1; // e23/d1 or e13/d2 or e12/d3
        
        double[] quadvector = impl.extractCoordinates(4);
        double q = quadvector[0];
        
        double sinAlpha;
        if (q != 0){
            sinAlpha = (Math.pow(a,2)-Math.pow(b,2)+Math.pow(c,2))/q;
        } else {
            sinAlpha = Double.NaN;
        }
        
        double alpha = Math.atan2(sinAlpha,cosAlpha);
        double d = Double.NaN;
        dir.scale(d);
        return new MotorParameters(dir, alpha);
    }

    @Override
    public boolean isEven() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
