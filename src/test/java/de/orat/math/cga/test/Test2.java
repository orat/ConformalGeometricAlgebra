package de.orat.math.cga.test;

import de.orat.math.cga.api.CGACircle;
import de.orat.math.cga.api.CGADualCircle;
import de.orat.math.cga.api.CGADualLine;
import de.orat.math.cga.api.CGADualPlane;
import de.orat.math.cga.api.CGADualSphere;
import de.orat.math.cga.api.CGALine;
import de.orat.math.cga.api.CGALinePair;
import de.orat.math.cga.api.CGAMultivector;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.api.CGAPlane;
import de.orat.math.cga.api.CGARoundPoint;
import de.orat.math.cga.api.CGASphere;
import de.orat.math.cga.impl1.CGA1Metric;
import static de.orat.math.cga.impl1.CGA1Metric.CGA2_METRIC;
import static de.orat.math.cga.impl1.CGA1Metric.CGA_METRIC;
import de.orat.math.cga.util.Decomposition3d.FlatAndDirectionParameters;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 * 
 * For some features to work the IDE needs to associate each Maven NetBeans 
 * module project with a NetBeans Platform/IDE that the module will be part of 
 * at runtime. Such features include running/debugging the project, context 
 * information in various nodes or wizards etc. To fix the problem, either open 
 * the NetBeans platform application project that this project is part of (maven 
 * packaging nbm-application) or define a property named netbeans.installation 
 * in either the pom.xml file or ~/.m2/settings.xml file pointing to a binary 
 * distribution of NetBeans.
 */
public class Test2 {
    
    private static double epsilon = 0.0000001;
    
    public Test2() {
    }

    private boolean equals(Tuple3d a, Tuple3d b){
        boolean result = true;
        if (Math.abs(a.x-b.x) > epsilon){
            result = false;
        }
        if (Math.abs(a.y-b.y) > epsilon){
            result = false;
        }
        if (Math.abs(a.z-b.z) > epsilon){
            result = false;
        }
        return result;
    }
    // Metric: [-1.0, 0.9999999999999998, 1.0, 1.0, 1.0]
    public void testCGAMetric(){
        System.out.println("------------------ Metric -----------");
        double[] values = CGA_METRIC.getEigenMetric();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i=0;i<values.length;i++){
            sb.append(String.valueOf(values[i]));
            sb.append(", ");
        }
        sb.append("]");
        sb.delete(sb.length()-3, sb.length()-1);
        System.out.println("CGA Metric: "+sb.toString());
        
        values = CGA2_METRIC.getEigenMetric();
        sb = new StringBuilder();
        sb.append("[");
        for (int i=0;i<values.length;i++){
            sb.append(String.valueOf(values[i]));
            sb.append(", ");
        }
        sb.append("]");
        sb.delete(sb.length()-3, sb.length()-1);
        System.out.println("CGA CGA2_METRIC: "+sb.toString());
    }
    
    // alles korrekt
    public void testGanjaCreatePointsCircleLineExample(){
        System.out.println("------------------Ganja.js expample: creation of points, circle, line --------------");
        CGARoundPoint p1 = new CGARoundPoint(new Vector3d(1d,0d,0d));
        // p1=1.0*eo + 1.0*e1 + 0.5*ei (korrekt)
        System.out.println("p1="+p1.toString());
        CGARoundPoint p2 = new CGARoundPoint(new Vector3d(0d,1d,0d));
        // p2=1.0*eo + 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p2="+p2.toString());
        CGARoundPoint p3 = new CGARoundPoint(new Vector3d(0d,0d,1d));
        // p3=1.0*eo + 1.0*e3 + 0.5*ei (korrekt)
        System.out.println("p3="+p3.toString());
        
        CGADualCircle c = new CGADualCircle(p1,p2,p3);
        // c=1.0*eo^e1^e2 - 1.0*eo^e1^e3 + 1.0*eo^e2^e3 + 1.0*e1^e2^e3 
        // + 0.5*e1^e2^ei - 0.5*e1^e3^ei + 0.5*e2^e3^ei (korrekt)
        System.out.println("c="+c.toString());
        
        CGADualLine l = new CGADualLine(p1,p2);
        // l=-1.0*eo^e1^ei + 1.0*eo^e2^ei + 1.0*e1^e2^ei (korrekt)
        System.out.println("l="+l.toString());
    }
    
    // alles korrekt!
    public void testGanjaCreatePointsPlaneSphereExample(){
        System.out.println("------------------Ganja.js expample: creation of points, plane, sphere --------------");
        CGARoundPoint p1 = new CGARoundPoint(new Vector3d(1d,0d,0d));
        // p1=1.0*eo + 1.0*e1 + 0.5*ei (korrekt)
        System.out.println("p1="+p1.toString());
        CGARoundPoint p2 = new CGARoundPoint(new Vector3d(0d,1d,0d));
        // p2=1.0*eo + 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p2="+p2.toString());
        CGARoundPoint p3 = new CGARoundPoint(new Vector3d(0d,0d,-1d));
        // p3=1.0*eo - 1.0*e3 + 0.5*ei (korrekt)
        System.out.println("p3="+p3.toString());
        CGARoundPoint p4 = new CGARoundPoint(new Vector3d(0d,-1d,-0d));
        // p4=1.0*eo - 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p4="+p4.toString());
        
        CGADualSphere s = new CGADualSphere(p1,p2,p3,p4);
        // s=2.0*eo^e1^e2^e3 - 1.0*e1^e2^e3^ei (korrekt)
        System.out.println("s="+s.toString());
        
        CGADualPlane p = new CGADualPlane(p1,p2,p3);
        // p=1.0*eo^e1^e2^ei + 1.0*eo^e1^e3^ei - 1.0*eo^e2^e3^ei - 1.0*e1^e2^e3^ei (korrekt)
        System.out.println("p="+p.toString());
    }
    
    
    public void testGanjaDualSpherePlane(){
         System.out.println("------------------Ganja.js expample: creation of dual sphere and plane --------------");
        // We start by defining a null basis, and upcasting for points
        //var ni = 1e4+1e5, no = .5e5-.5e4, nino = ni^no;
        //var up = (x)=> no + x + .5*x*x*ni;

        CGARoundPoint p1 = new CGARoundPoint(new Vector3d(1d,0.5d,0d));
        System.out.println("p1="+p1.toString());
        CGARoundPoint p2 = new CGARoundPoint(new Vector3d(0d,1d,0.5d));
        System.out.println("p2="+p2.toString());
        
        // The distance between two points.
        //var d = (a,b)=>((a<<b).Length*2)**.5;
        
        // The duality operator can be used to produce a sphere from a point.
        // var s = ()=>!(p1 - .3**2*.5*ni);
        // ganja.js: -1.08e1234 - 0.07e1235 - 0.5e1345 + e2345
        // s=-eo^e1^e2^e3 + 0.5*eo^e1^e3^ei 
        //   -eo^e2^e3^ei - 0.625*e1^e2^e3^ei
        // scheint so ungefähr zu passen, ist noch nicht 100% überprüft
        CGADualSphere s = (new CGASphere(p1, Math.pow(0.3,2d))).dual();
        System.out.println("s="+s.toString());
        
        CGAMultivector inf_no = createInf(1d).op(createOrigin(1d));
        // Planes also have a direct dual representation. (but start from vector not point)
        // var p = ()=>!(d(p2,no)*ni + (p2^nino*nino).Normalized);
        
        CGAMultivector n = ((new CGARoundPoint(p2)).op(inf_no).gp(inf_no)).normalize();
        // n=-0.8944271909999159*e2 - 0.4472135954999579*e3
        System.out.println("n="+n.toString());
        CGADualPlane p = (new CGAPlane((CGAMultivector.createInf(Math.sqrt(p2.distSquare(
                new CGARoundPoint(CGAMultivector.createOrigin(1d)))))).add(
                        n))).dual();
        // java p=-2.220446049250313E-16*eo^e1^e2^e3 + 0.44721359549995776*eo^e1^e2^ei 
        // - 0.8944271909999155*eo^e1^e3^ei - 1.1180339887498945*e1^e2^e3^ei
        // ganja.js p= -1.11e1234-1.11e1235-0.44e1245-0.89e1345
        System.out.println("p="+p.toString());
       
        double d = p2.distSquare(new CGARoundPoint(CGAMultivector.createOrigin(1d)));
        // java d=1.2500000000000007
        System.out.println("d="+String.valueOf(d));
        // vermutlich brauche ich n als CGAAttitude und dann einen passenden Konstruktor
        //TODO
        //CGAPlane pa = CGAPlane(n, d);
        
        // p= -1.11e1234-1.11e1235-0.44e1245-0.89e1345

        // c=-1.11e123-0.48e124-0.03e125-0.40e134+0.48e135+0.22e145-1.11e234
        // -1.11e235-0.44e245-0.89e345
        // You can use the regressive product to calculate intersections..
        //var c = ()=>s&p;
        
        CGADualCircle c = new CGADualCircle(s.vee(p));
        System.out.println("c="+c.toString());
    
    }
    public void testBasisBlades(){
        System.out.println("------------------Basis blades--------------");
        CGAMultivector m = CGAMultivector.createOrigin(1d).ip(CGAMultivector.createInf(1d));
        System.out.println("einf*e0="+m.toString());
        // ist -1 also korrekt!!!!
    }
    /**
     * n=(0.0,0.0, 1.0)
     * plane=1.0*e3 + 2.0*ei
     * probe=1.0*eo + 0.5*e1 + 0.5*e2 + 0.5*e3 + 0.375*ei
     * attitude=-5.551115123125783E-17*eo^e1^e2 + 0.9999999999999996*e1^e2^ei
     * location=(2.500000000000001, 2.500000000000001, 1.2500000000000004)
     * n=(0.0, 0.0, 0.9999999999999996)
     */
    public void testPlane(){
        System.out.println("---------------------- plane ----");
        Vector3d n = new Vector3d(0d,0d,2d);
        double d = 2d;
        CGAPlane plane = new CGAPlane(n, d);
        System.out.println("n=("+String.valueOf(n.x)+","+String.valueOf(n.y)+", "+String.valueOf(n.z)+"), d="+String.valueOf(d));
        System.out.println("plane="+plane.toString());
        
        //CGAPoint cp = new CGARoundPoint(new Point3d(0.0d,0.0d,2.0d));
        //System.out.println("probe="+cp.toString());
        FlatAndDirectionParameters flat = plane.decompose(new Point3d(0.1d,0.1d,0d));
        System.out.println("location2=("+String.valueOf(flat.location().x)+", "+
                String.valueOf(flat.location().y)+", "+String.valueOf(flat.location().z)+")");
        Vector3d attitude = flat.attitude();
        System.out.println("nn=("+String.valueOf(attitude.x)+", "+
                String.valueOf(attitude.y)+", "+String.valueOf(attitude.z)+")");
    }
    
    public void testCircle(){
        System.out.println("----------------- circle -----");
        Point3d p1 = new Point3d(0.02,0.02,1);
        System.out.println("p1=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        double radius = 2d;
        System.out.println("radius="+String.valueOf(radius));
        CGASphere sphere1 = new CGASphere(p1, radius);
        System.out.println("sphere1="+sphere1.toString());
        
        Point3d p2 = new Point3d(1.02,1.02,1);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGASphere sphere2 = new CGASphere(p2, radius);
        System.out.println("sphere2="+sphere2.toString());
        
        CGACircle circle = new CGACircle(sphere1.op(sphere2));
        System.out.println("circle="+circle.toString());
        
        RoundAndTangentParameters/*FlatAndDirectionParameters*/ decomposition = circle.decompose();
        Vector3d attitude = decomposition.attitude();
        Point3d location = decomposition.location();
        double squaredSize = decomposition.squaredSize();
        System.out.println("squaredSize="+String.valueOf(squaredSize));
        System.out.println("attitude = "+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z));
        System.out.println("location = "+String.valueOf(location.x)+", "+String.valueOf(location.y)+", "+String.valueOf(location.z));
    }
    /**
     * p1=(0.02,0.02,1.0)
radius=2.0
sphere=1.0*eo + 0.02*e1 + 0.02*e2 + 1.0*e3 - 1.4996*ei
location1=-1.0000000000000004*eo - 0.02000000000000001*e1 - 0.02000000000000001*e2 - 1.0000000000000004*e3 + 1.4996000000000007*ei
location=1.4996000000000014*eo + 0.02999200000000003*e1 + 0.02999200000000003*e2 + 1.4996000000000014*e3 - 0.12400008000000018*ei
radius = 2.061455835083547
location1=-1.0000000000000004*eo - 0.02000000000000001*e1 - 0.02000000000000001*e2 - 1.0000000000000004*e3 + 1.4996000000000007*ei
location=1.4996000000000014*eo + 0.02999200000000003*e1 + 0.02999200000000003*e2 + 1.4996000000000014*e3 - 0.12400008000000018*ei
radius2 = 2.061455835083547
radius2squared = 4.2496001600000035
squaredWeight=0.9999999999999989
location = (0.02999200000000003, 0.02999200000000003, 1.4996000000000014)
-einf*sphere = 0.9999999999999996
norm(sphere) = 1.9999999999999998
     */
    public void testSphere(){
        System.out.println("----------------- sphere -----");
        Point3d p1 = new Point3d(1.0,0.0,1d);
        System.out.println("p1=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        CGARoundPoint cgaPoint = new CGARoundPoint(p1);
        System.out.println("p_cga="+cgaPoint.toString());
        Point3d p2 = new Point3d(0,1,1);
        Point3d p3 = new Point3d(0,0,0);
        Point3d p4 = new Point3d(0,-1,1);
        CGADualSphere cgaDualSphere = new CGADualSphere(p1,p2,p3,p4);
        System.out.println("cgaDualSphere="+cgaDualSphere.toString());
        CGASphere cgaSphere = cgaDualSphere.undual();
        System.out.println("cgaSphere="+cgaSphere.toString());
        RoundAndTangentParameters rp1 = cgaDualSphere.decompose(); 
        System.out.println("radius = "+String.valueOf(Math.sqrt(Math.abs(rp1.squaredSize())))); 
        System.out.println("radius2squared = "+String.valueOf(Math.abs(rp1.squaredSize()))); 
        System.out.println("location1 = ("+String.valueOf(rp1.location().x)+", "+
                String.valueOf(rp1.location().y)+", "+String.valueOf(rp1.location().z)+")"); // location1 = (0.0, 0.0, -1.6225927682921321E31)
        double radius = 2d;
        System.out.println("radius1="+String.valueOf(radius));
        
        CGASphere sphere = new CGASphere(p1, radius);
        System.out.println("sphere="+sphere.toString());
        
        // radius = 1.4576694001041532 das ist auch falsch, sollte 2 sein
        RoundAndTangentParameters rp = sphere.decompose();
        System.out.println("radius = "+String.valueOf(Math.sqrt(Math.abs(rp.squaredSize()))));
        System.out.println("radius2squared = "+String.valueOf(Math.abs(rp.squaredSize())));
        
        // nach Hildenbrand1998
        CGAMultivector m = sphere.gp(CGAMultivector.createInf(1d)).gp(sphere);
        System.out.println("sphere center from sandwhich product = "+m.toString());
        
        // squaredWeight bestimmen
        double weight = //CGAMultivector.decomposeWeight(sphere.determineDirectionFromTangentAndRoundObjectsAsMultivector(), 
                //CGAMultivector.createOrigin(1d));
                sphere.squaredWeight();
        // squaredWeight=0.9999999999999989 richtig
        System.out.println("weight="+String.valueOf(weight));
        
        // origin = (0.02000000000000001, 0.02000000000000001, 1.0000000000000004) stimmt
        System.out.println("location = ("+String.valueOf(rp.location().x)+", "+
                String.valueOf(rp.location().y)+", "+String.valueOf(rp.location().z)+")");
        
        // Dorst2007: -einf*P = 1 stimmt? soll das die Normierung sein?
        System.out.println("-einf*sphere = "+
                String.valueOf(-CGAMultivector.createInf(1d).scp(sphere)));
        // norm(p1) = 2 ist sollte das nicht 1 sein?
        System.out.println("norm(sphere) = "+String.valueOf(sphere.norm()));
    }
    
    public void testPoint(){
        System.out.println("--------------- point -------");
        Point3d p = new Point3d(0.02,0.02,1);
        System.out.println("p=("+String.valueOf(p.x)+","+String.valueOf(p.y)+","+String.valueOf(p.z)+")");
        CGARoundPoint cp = new CGARoundPoint(p, 2d);
        System.out.println("cp="+cp.toString());
        // squared squaredWeight = 4, korrekt 
        System.out.println("squaredWeight1(sollte 4 sein)="+String.valueOf(cp.squaredWeight()));
        
        RoundAndTangentParameters decomposed = cp.decompose();
        Vector3d a1 = decomposed.attitude();
        System.out.println("attitude=("+String.valueOf(a1.x)+", "+String.valueOf(a1.y)+", "+String.valueOf(a1.z)+")");
        Point3d p1 = decomposed.location(); // ok
        System.out.println("location=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        // 2.251 sollte aber 0 sein.
        double squaredSize = decomposed.squaredSize();
        System.out.println("squaredSize="+String.valueOf(squaredSize));
        //CGAMultivector attitude = cp.determineDirectionFromTangentAndRoundObjectsAsMultivector();
        //CGAPoint probePoint = new CGARoundPoint(new Point3d(0d,0d,0d));
        //double squaredWeight = CGAMultivector.squaredWeight(attitude, probePoint);
        double squaredWeight = cp.squaredWeight();
        System.out.println("squaredWeight="+String.valueOf(squaredWeight));
        Point3d p2 = new Point3d(2.02,0.02,1);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGARoundPoint cp2 = new CGARoundPoint(p2);
        System.out.println("cp2="+cp2.toString());
        System.out.println("distSquare="+String.valueOf(cp2.distSquare(cp)));
        // Abstände scheinen zu stimmen
    }
    
    // scheint zu funktionieren
    // TODO Vergleich mit festen Ergebniszahlen einbauen etc.
    public void testSquareDistanceBetweenPoints(){
        System.out.println("--------------- square dist -------");
        Point3d p1 = new Point3d(0.02,0.02,1);
        Point3d p2 = new Point3d(2,0.02,1);
        System.out.println("distsquare="+String.valueOf(p2.distanceSquared(p1)));
        
        // die beiden Multivektoren brauchen scheinbar nicht normalisiert zu werden
        CGARoundPoint cp1 = new CGARoundPoint(p1);
        System.out.println("cp1="+cp1.toString());
        //System.out.println("cp1.unit="+cp1.unit().toString(CGA1Metric.baseVectorNames));
        CGARoundPoint cp2 = new CGARoundPoint(p2);
        System.out.println("cp2="+cp2.toString());
        
        double result = CGA1Metric.squareDistanceBetweenPoints(cp1, cp2);
        System.out.println("distsquare="+result);
    }
    
    public void testLine1(){
        System.out.println("-------------- line1 --------");
        Point3d p1 = new Point3d(1d,1d,0d);
        double weight1 = 2d;
        CGARoundPoint cp1 = new CGARoundPoint(p1, weight1);
        System.out.println("cp1="+cp1); // stimmt
        
        Point3d p2 = new Point3d(1d,0d,1d);
        double weight2 = -1d;
        CGARoundPoint cp2 = new CGARoundPoint(p2, weight2);
        System.out.println("cp2="+cp2); // stimmt
        
        System.out.println("distSquare="+String.valueOf(cp2.distSquare(cp1))); // 2 stimmt
        
        CGADualLine ldual = new CGADualLine(cp1, cp2);
        System.out.println("dual line="+String.valueOf(ldual.toString()));
        // dual line=2.0*eo^e2^ei + 2.0*e1^e2^ei - 2.0*eo^e3^ei - 2.0*e1^e3^ei - 2.0*e2^e3^ei
        // sollte p1 ∧ p2 ∧ ∞ = −2(o + (e1 + e2 )) ∧ (e3 − e2 ) ∧ ∞. sein
        // stimmt
        
        // the weight is -2, and the conformal direction is −2(e3 − e2 ) ∧ ∞.
        FlatAndDirectionParameters decomposition = ldual.decompose(new Point3d(0d,0d,0d));
        Vector3d attitude = decomposition.attitude();
        // attitude_cga=-1.1102230246251565E-16*eo^e2 - 2.220446049250313E-16*e1^e2 + 
        // 1.1102230246251565E-16*eo^e3 + 2.220446049250313E-16*e1^e3 + 
        // 2.220446049250313E-16*e2^e3 - 1.9999999999999991*e2^ei + 1.9999999999999991*e3^ei

        System.out.println("attitude="+String.valueOf(attitude.x)+","+String.valueOf(attitude.y)+","+
                String.valueOf(attitude.z));
        // attitude=0.0,1.9999999999999991,-1.9999999999999991
        // stimmt
        
        Point3d location = decomposition.location();
        System.out.println("location="+String.valueOf(location.x)+","+String.valueOf(location.y)+","+
                String.valueOf(location.z));
        // location=-4.6259292692714784E-17,8.789265611615823E-17,-4.39463280580791E-16
        // vermutlich falsch
        
        System.out.println("squaredWeight="+ldual.squaredWeight());
        // squaredWeight=7.999999999999988
        // vermutlich falsch, sollte doch 4 sein oder?
        
        CGALine line = ldual.undual();
        Vector3d direction = line.attitude();
        double squaredWeight = line.squaredWeight();
        System.out.println("weight="+String.valueOf(squaredWeight));
        System.out.println("attitude=("+String.valueOf(direction.x)+","+
                String.valueOf(direction.y)+","+String.valueOf(direction.z)+")");
    }
    /**
     * @Test2
     */
    public void testLine() {
        System.out.println("-------------- line --------");
        
        Point3d p1 = new Point3d(0.02,0.02,1);
        System.out.println("p1=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        CGARoundPoint cp1 = new CGARoundPoint(p1);
        System.out.println("cp1="+cp1);
        
        Point3d p2 = new Point3d(1,0.02,1);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGARoundPoint cp2 = new CGARoundPoint(p2);
        System.out.println("cp2="+cp2);
        
        Vector3d n = new Vector3d(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        System.out.println("n1=("+String.valueOf(n.x)+","+String.valueOf(n.y)+","+String.valueOf(n.z)+")");
        
        CGADualLine l1dual = new CGADualLine(p1, p2);
        // dual line represented as tri-vector
        // l1dual= 0.98*no^e1^ni - 0.0196*e1^e2^ni - 0.98*e1^e3^ni
        System.out.println("l1*= "+l1dual);
      
        CGALine l1 = l1dual.undual();
        // line represented as bivector
        // 5.551115123125783E-17*no^e2 - 1.734723475976807E-18*no^e3 + 0.9799999999999993*e2^e3 + 0.9799999999999995*e2^ni - 0.019599999999999985*e3^ni
        // 0.979993*e2^e3 + 0.979999995*e2^ni - 0.0195999985*e3^ni
        System.out.println("l1= "+l1);
        
        FlatAndDirectionParameters flatParameters = l1dual.decompose(new Point3d());
        Vector3d attitude = flatParameters.attitude();
        // sollte (0.98,0.0,0.0) - hat aber falsches Vorzeichen
        System.out.println("attitude=("+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+
                ", "+String.valueOf(attitude.z)+")");
        Point3d location = flatParameters.location();
        System.out.println("location=("+String.valueOf(location.x)+", "+String.valueOf(location.y)+
                ", "+String.valueOf(location.z)+")");
    }
    
    public void testLinePair(){
    
        System.out.println("-------------- linepair --------");
        
        /*Multivector no = Multivector.createBasisVector(0);
        Multivector e1 = Multivector.createBasisVector(1);
        Multivector e2 = Multivector.createBasisVector(2);
        Multivector e3 = Multivector.createBasisVector(3);
        Multivector ni = Multivector.createBasisVector(4);*/
        
        Point3d p1 = new Point3d(0.02,0.02,1);
        CGARoundPoint cp1 = new CGARoundPoint(p1);
        
        Point3d p2 = new Point3d(1,0.02,1);
        CGARoundPoint cp2 = new CGARoundPoint(p2);
        
        Vector3d n1 = new Vector3d(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        System.out.println("n1=("+String.valueOf(n1.x)+","+String.valueOf(n1.y)+","+String.valueOf(n1.z)+")");
       
        CGADualLine l1 = new CGADualLine(p1, p2);
        System.out.println("l1= "+l1.toString());
        System.out.println("l1 normiert= "+l1.normalize().toString());
        // ipns representation
        //CGA1Multivector l1dual = new CGA1Multivector(l1dual.dual(CGA1Metric.CGA_METRIC));
        
        Point3d p3 = new Point3d(0.02,0.02,2);
        Point3d p4 = new Point3d(1,1,2.2);
        Vector3d n2 = new Vector3d(p4.x-p3.x, p4.y-p3.y, p4.z-p3.z);
        System.out.println("n2=("+String.valueOf(n2.x)+","+String.valueOf(n2.y)+","+String.valueOf(n2.z)+")");
       
        System.out.println("alpha = "+String.valueOf(n2.angle(n1)*180d/Math.PI));
        
        Vector3d cross = new Vector3d();
        cross.cross(n1, n2);
        System.out.println("cross=("+String.valueOf(cross.x)+","+String.valueOf(cross.y)+","+String.valueOf(cross.z)+")");
       
        CGADualLine l2 = new CGADualLine(p3, p4);
        System.out.println("l2= "+l2.toString());
        System.out.println("l2 normiert= "+l1.normalize().toString());
        //CGALinePair l2l1 = new CGALinePair(l1, l2); //l2.gp(l1).normalize();
        // bi- und trivector Anteile
        // l2l1= -2.87728 + 0.21520800000000018*no^e1 - 0.019208*no^e2 + 
        //                  2.87728*e1^e2 + 0.95648*no^e3 + 0.15766240000000017*e1^e3 + 
        //                  0.0383376*e2^e3 
        //                  - 0.9604*no^e1^e2^e3

        //System.out.println("l2l1= "+l2l1.unit().toString(CGA1Metric.baseVectorNames));
        
        //l2l1.decomposeLinePair();
    }
}