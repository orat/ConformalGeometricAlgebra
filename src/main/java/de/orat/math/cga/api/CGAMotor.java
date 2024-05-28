package de.orat.math.cga.api;

import org.jogamp.vecmath.AxisAngle4d;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Vector3d;

/**
 * A motor/screw is no blade because it contains blades of grade 0, 2 and 4 
 * all together. 
 * 
 * It describes a rotation around a rotation axis combined with a
 * translation in direction of this axis or in other words it describes a
 * rotation about an arbitrary axis in space.<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMotor extends CGAVersor {
    
    public CGAMotor(CGAMultivector m){
        // test m auf even und darauf das gp mit reverse == 1
        //TODO
        super(m.impl);
    }
    
    // Voraussetzung: tranlator ist in Richtung der rotation-axis
    //FIXME überprüfen und exception werfen?
    public CGAMotor(CGARotor rotor, CGATranslator translator){
        // muss ich hier nicht noch mit dem reverse des rotor multiplizieren
        // oder einfach transform() aufrufen? Nein! siehe [Perwass2000-Book]
        // T R T.reverse() funktioniert nicht für Translation in Richtung der rot-axis von R
        // allgmeine Form ist daher:
        // T2 T1 R T1.reverse mit T2 in Richtung der rot-axis und T1 in der rot-Ebene von R
        // wenn ich dann aber gar keine T1 brauche dann bleibt T2 R übrig
        //this(rotor.gp(translator));
        this(translator.gp(rotor));
    }
    
    public CGAMotor gp(CGAMotor m){
        return new CGAMotor(super.gp(m));
    }
    
    /**
     * ??????
     * @param B
     * @param d 
     * @throws IllegalArgumentException if d=(0,0,0)
     */
    public CGAMotor(CGABivector B, Vector3d d){
        this(B.add(createE3(d).gp(inf)));
    }
    
    public record MotorParameters(Vector3d dir, double alpha){}
    
    // ungetestet
    public Matrix4d toMatrix4d(){
        MotorParameters param = decomposeMotor();
        Matrix4d result = new Matrix4d();
        result.setIdentity();
        result.setRotation(new AxisAngle4d(param.dir(), param.alpha()));
        result.setTranslation(new Vector3d(param.dir()));
        return result;
    }
    
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
        //FIXME
        double d = Double.NaN;
        dir.scale(d);
        return new MotorParameters(dir, alpha);
    }

    @Override
    public boolean isEven() {
        // because the product of two even Multivectors is also even
        // https://rastergraphics.wordpress.com/2012/12/06/brief-introduction-to-conformal-geometric-algebra/
        return true;
    }
}
