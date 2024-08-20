package de.orat.math.cga.api;

import org.jogamp.vecmath.AxisAngle4d;
import org.jogamp.vecmath.Matrix3d;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * A motor/screw is no blade because it contains blades of grade 0, 2 and 4 
 * all together. 
 * 
 * Every rigid body motion can be represented by a rotation and translation in the
 * direction of the rotation axis, according to Chasles' theorem.<p>
 * 
 * This combinded motion is called screw. If used for velocity, it is called twist.
 * A twist has 6 independent parameters - it is a screw with 5 parameters plus an 
 * additional scalar quantity omega called amplitude. The amplitude describes the 
 * rate which a rigid body twists about the screw.<p>
 * 
 * If it is used for force/torque, it is called wrench.<p>
 * 
 * It describes a rotation around a rotation axis combined with a
 * translation in direction of this axis or in other words it describes a
 * rotation about an arbitrary axis in space.<p>
 * 
 * Translation is coded into e1^ni,    e2^ni,    e3^ni and 1.0
 * Rotation is coded into    e1^e2,    e2^e3,    e3^e1 and 1.0
 * 
 * 1.0,                                         // grade 0<br>
 * e1^e2, e2^e3, e3^e1, eo^e1, eo^e2, eo^e3     // grade 2<br>
 * eo^e1^e2^e3                                  // grade 4<p>
 *
 * In priciple a screw has a minimal set of 5 parameters, 4 describing a line in
 * space and one for the pitch.<p>
 * 
 * Motor Parameterization<br>
 * Lars Tingelstad∗ and Olav Egeland<br>
 * 2018<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAScrew extends CGAVersor {
    
    // composition 
    
    public CGAScrew(CGAMultivector m){
        // test m auf even und darauf das gp mit reverse == 1
        // oder das nur folgende blades bestehen:
        // 1, e12, e13, e23, e1inf, e2inf, i3inf, e123inf
        //TODO
        super(m.impl);
    }
    
    // Voraussetzung: translator ist in Richtung der rotation-axis
    // oder rotation um eine Achse durch den Ursprung aber letzteres nennt man vermutlich
    // nicht mehr motor?
    //FIXME überprüfen und exception werfen?
    public CGAScrew(CGATranslator translator, CGARotor rotor){
        // muss ich hier nicht noch mit dem reverse des rotor multiplizieren
        // oder einfach transform() aufrufen? Nein! siehe [Perwass2000-Book]
        // T R T.reverse() funktioniert nicht für Translation in Richtung der rot-axis von R
        // allgmeine Form ist daher:
        // T2 T1 R T1.reverse mit T2 in Richtung der rot-axis und T1 in der rot-Ebene von R
        // wenn ich dann aber gar keine T1 brauche dann bleibt T2 R übrig
        //this(rotor.gp(translator));
        //TODO
        // aus dem Rotor die Drehachse bestimmen und überprüfen, ob die identisch ist 
        // mit der tranlator axis
        // m2 = (7.914094654658532E-4, -0.9553796199693654, -0.29537968010384336, 0.0;
        // 0.9949522374162776, -0.028888053619718246, 0.09610164212128369, 0.0;
        //-0.10034649436527905, -0.2939647293558449, 0.9505342281913597, 0.0;
        //0.0, 0.0, 0.0, 1.0)
        this(translator.gp(rotor));
        // m2 = (7.914094654684067E-4, -0.9553796199693629, -0.2953796801038425, 0.0;
        // 0.9949522374162751, -0.028888053619715803, 0.09610164212128341, 0.0;
        // -0.10034649436527877, -0.29396472935584406, 0.9505342281913599, 0.0;
        // 0.0, 0.0, 0.0, 1.0)
        // scheint keinen Unterschied zu machen
        //this(translator.gp(rotor).gp(translator.reverse()));
    }
    
    /**
     * Create a specific screw which passes the origin.
     * 
     * @param a
     * @param b
     * @param theta
     * @param d 
     */
    public CGAScrew(Vector3d a, Vector3d b, double theta, double d){
         this(createTranslator(a,b,d), createRotor(a,b, theta));
    }
    private static CGARotor createRotor(Vector3d a, Vector3d b, double theta){
        CGAEuclideanBivector B = new CGAEuclideanBivector(a,b);
        B.normalize();
        return new CGASpinor(B, theta);
    }
    private static CGATranslator createTranslator(Vector3d a, Vector3d b, double d){
        Vector3d vec = new Vector3d();
        vec.cross(a,b);
        vec.normalize();
        vec.scale(d);
        return new CGATranslator(vec);
    }
    
    /*public CGAScrew(CGALineIPNS line, double theta, double d){
        this()
    }*/
    
    // ungetested
    // https://ieeexplore.ieee.org/stamp/stamp.jsp?arnumber=9488174
    public CGAScrew(CGAScrewAxisIPNS screwAxis, double theta){
        this(screwAxis.gp(theta).exp());
    }
    
    /**
     * The pitch of a screw is the ratio of the tranlational displacement and the
     * rotational displacement of a body.
     * 
     * @return pitch
     */
    public double getPitch(){
        //TODO implement it
        return 0;
    }
    // scheint nicht zu stimmen
    /*public CGAScrew(AxisAngle4d axisAngle){
        this(createRotor(axisAngle), new CGATranslator(
                new Vector3d(axisAngle.getX(), axisAngle.getY(), axisAngle.getZ())));
    }
    
    private static CGARotor createRotor(AxisAngle4d axisAngle){
        Vector3d vec = new Vector3d(axisAngle.getX(), axisAngle.getY(), axisAngle.getZ());
        vec.normalize();
        System.out.println("vec="+String.valueOf(vec.x)+", "+String.valueOf(vec.y)+", "+String.valueOf(vec.z));
        CGAEuclideanBivector B = (new CGAEuclideanVector(vec)).euclideanDual();
        System.out.println(B.toString("B"));
        return new CGARotor(B, axisAngle.getAngle());
    }*/
    
    public CGAScrew gp(CGAScrew m){
        return new CGAScrew(super.gp(m));
    }
    
    /**
     * ??????
     * @param B
     * @param d 
     * @throws IllegalArgumentException if d=(0,0,0)
     */
    public CGAScrew(CGABivector B, Vector3d d){
        this(B.add(createE3(d).gp(inf)));
    }
    
    //public record MotorParameters(Vector3d dir, double alpha){}
    
    
    // decomposition
    
    // unklar ob das so überhaupt geht
    // [Dorst2007]-book 13.15.
    public CGARotor splitRotor(){
        // p. 384
        //return new CGARotor(o.negate().lc(this.gp(inf)));
        //unklar ob das überhaupt einen Unterschied macht
        return new CGARotor(o.lc(this.gp(inf)).negate());
    }
    // [Dorst2007]-book 13.15.
    public Vector3d splitTranslation(CGARotor rotor){
        CGAMultivector m = o.lc(this).gp(-2d).div(rotor);
        return m.extractE3ToVector3d();
    }
    // p 384 unvollständig
    /*public CGAMultivector log(){
        CGARotor R = splitRotor();
        CGAMultivector t = o.lc(this).gp(-2d).div(R);
        if (R.isScalar() && R.decomposeScalar() == 1d) {
            return t.negate().gp(inf.gp(0.5));
        } else {
            CGAMultivector R2 = R.extractGrade(2);
            CGAMultivector I = R2.gp(1d/Math.sqrt(-R2.gp(R2)));
            return t.op(I).negate().div(I)+1/(1-R.gp(R))...
        }
        
    }*/
    
    // ungetestet
    // macht so keinen Sinn hier
    /*public Matrix4d toMatrix4d(){
        MotorParameters param = decomposeMotor();
        Matrix4d R = new Matrix4d();
        R.setIdentity();
        R.setRotation(new AxisAngle4d(param.dir(), param.alpha()));
        R.setTranslation(new Vector3d(param.dir()));
        return R;
    }*/
    
    //TODO
    /*public MotorParameters decomposeMotor(){
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
    }*/

    // mir ist unklar, ob ich eine screw überhaupt in eine 4x4-Matrix darstellen kann
    //TODO
    public Matrix4d decompose4PointAndVector(){
        CGARotor R = splitRotor();
        Vector3d t = splitTranslation(R);
        Matrix4d m = R.decompose4PointAndVector();
        m.setM03(m.getM03()+t.x);
        m.setM13(m.getM03()+t.y);
        m.setM23(m.getM03()+t.z);
        return m;
    }
    
    /**
     * Decompose the motor into a homogenious matrix.
     * 
     * General form of a motor:<br>
     * Qvxe41 + Qvye42 + Qvze43 +Qvw1234 + Qmxe23 + Qmye31 + Qmze12 + Qmw
     * und e4 ist origin
     * TODO
     * Wie passt das mit nachfolgender blade-list zusammen?
     * 1, e12, e13, e23, e1inf, e2inf, i3inf, e123inf
     * 
     * https://discourse.bivector.net/t/how-to-decompose4PointAndVector-efficiently-a-motor-into-4x4-matrix/825/2
     * 
     * Vermutlich geht das gar nicht so einfach, da Rotor*Translator die Komponenten 
     * vermischt und ich die dann gar nicht mehr so leicht hier auseinanderziehen kann?
     * FIXME
     * 
     * @return a homoegenious matrix representing the motor
     */
    /*public Matrix4d decompose4PointAndVector(){
        // s, eo, e1, eo^e1, e2, eo^e2, e1^e2, eo^e1^e2, e3, eo^e3, e1^e3, eo^e1^e3,
        // e2^e3, eo^e2^e3, e1^e2^e3, eo^e1^e2^e3, ei, eo^ei, e1^ei, eo^e1^ei,
        // e2^ei, eo^e2^ei, e1^e2^ei, eo^e1^e2^ei, e3^ei, eo^e3^ei, e1^e3^ei, 
        // eo^e1^e3^ei, e2^e3^ei, eo^e2^e3^ei, e1^e2^e3^ei, eo^e1^e2^e3^ei
        double[] coords = extractCoordinates();
        
        // das ergibt die Rotation
        
        double R0 = coords[0]; // s
        double R1 = coords[3]; // e01
        double R2 = coords[5]; // e02
        double R3 = coords[9]; // e03
        double R4 = coords[6]; // e12
        double R5 = -coords[10]; //e31
        double R6 = coords[12]; // e23
        double R7 = coords[15]; // e0123
        
        //TODO
        // für mehr Effizient: Produkte vorher bilden und identy als static final vorher erzeugen
        Matrix4d R = new Matrix4d(-R4*R4-R5*R5, R0*R4+R5*R6, R4*R6-R0*R5, R3*R5-R0*R1-R2*R4-R6*R7,
		    R5*R6-R0*R4, -R4*R4-R6*R6, R0*R6+R4*R5, R1*R4-R0*R2-R3*R6-R5*R7,
		    R0*R5+R4*R6, R4*R5-R0*R6, -R5*R5-R6*R6, R2*R6-R0*R3-R1*R5-R4*R7,
		    0d, 0d, 0d, 0d);
        R.mul(2d, R);
        Matrix4d identity = new Matrix4d();
        identity.setIdentity();
        R.add(identity);
        
        
        // R = (0.6932599772158182 - 0.7033190727446562*e1^e2 - 0.07033190727446562*e1^e3 + 0.14066381454893123*e2^e3)
        // in R keine Komponenten für e01, e02, e03, e0123 da Rotation um Achse durch Ursprung
        // T = (1.0 + 0.6831300510639731*e1^ei + 0.34156502553198653*e2^ei - 3.415650255319865*e3^ei)
        // M = (0.6932599772158182 - 0.7033190727446562*e1^e2 - 0.07033190727446562*e1^e3 + 0.14066381454893123*e2^e3 
        // + 0.4735867236360506*e1^ei + 0.2367933618180253*e2^ei - 2.367933618180253*e3^ei + 2.522406568911196*e1^e2^e3^ei)
        //
        // also das kann so nicht funtionieren, da e1inf, e2inf und e3inf so nicht mehr im Motor stehen
        // sondern andere Werte + zusätzlich e123inf
        // da steckt quasi die vorherige Drehung des Punkts mit drin?
        // add the translation 
        
        
        // e1^ni,    e2^ni,    e3^ni
       double x = coords[18];
       double y = coords[20];
       double z = coords[24];
       System.out.println("e1inf="+String.valueOf(x)+", e2inf="+String.valueOf(y)+" e3inf="+String.valueOf(z));
       // R.setM03(R.getM03() +x);
       // R.setM13(R.getM13() +y);
       // R.setM23(R.getM23() +z);
        //Matrix4d T = new Matrix4d();
        //T.setIdentity();
        //T.setTranslation(new Vector3d(x,y,z));
        //T.mul(R);
        return R;
    }*/
    
    /*public Matrix4d decompose2ScrewMatrix(){
        Vector3d s = new Vector3d();
        Matrix3d S = new Matrix3d(0,-s.x, s.y, s.z, 0, -s.x, -s.y, s.x, 0);
    }*/
    
    @Override
    public boolean isEven() {
        // because the product of two even Multivectors is also even
        // https://rastergraphics.wordpress.com/2012/12/06/brief-introduction-to-conformal-geometric-algebra/
        return true;
    }
}
