package de.orat.math.cga.test;

import de.orat.math.cga.api.CGAAttitudeVectorOPNS;
import de.orat.math.cga.api.CGAOrientedCircleIPNS;
import de.orat.math.cga.api.CGAOrientedCircleOPNS;
import de.orat.math.cga.api.CGALineOPNS;
import de.orat.math.cga.api.CGAPlaneOPNS;
import de.orat.math.cga.api.CGAOrientedPointPairOPNS;
import de.orat.math.cga.api.CGASphereOPNS;
import de.orat.math.cga.api.CGALineIPNS;
import de.orat.math.cga.api.CGAMultivector;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.api.CGAPlaneIPNS;
import de.orat.math.cga.api.CGAOrientedPointPairIPNS;
import de.orat.math.cga.api.CGARoundPointIPNS;
import de.orat.math.cga.api.CGARoundPointOPNS;
import de.orat.math.cga.api.CGAScalar;
import de.orat.math.cga.api.CGASphereIPNS;
import de.orat.math.cga.api.CGATangentVectorIPNS;
import de.orat.math.cga.api.CGATangentVectorOPNS;
import de.orat.math.cga.impl1.CGA1Metric;
import static de.orat.math.cga.impl1.CGA1Metric.CGA_METRIC;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    
    public static CGAMultivector o = CGAMultivector.createOrigin(1d);
    public static CGAMultivector inf = CGAMultivector.createInf(1d);
    public static CGAMultivector e1 = CGAMultivector.createEx(1d);
    public static CGAMultivector e2 = CGAMultivector.createEy(1d);
    public static CGAMultivector e3 = CGAMultivector.createEz(1d);
    
    public Test2() {
    }

    private boolean equals(double a, double b){
        boolean result = true;
        if (Math.abs(a-b) > epsilon){
            result = false;
        }
        return result;
    }
    private static boolean equals(Tuple3d a, Tuple3d b){
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
        
        /*values = CGA2_METRIC.getEigenMetric();
        sb = new StringBuilder();
        sb.append("[");
        for (int i=0;i<values.length;i++){
            sb.append(String.valueOf(values[i]));
            sb.append(", ");
        }
        sb.append("]");
        sb.delete(sb.length()-3, sb.length()-1);
        System.out.println("CGA2_METRIC: "+sb.toString());*/
        
        CGARoundPointIPNS p = new CGARoundPointIPNS(new Point3d(2d,2d,0d));
        System.out.println(p.toString("p")); 
        CGARoundPointIPNS origin = new CGARoundPointIPNS(CGAMultivector.createOrigin(1d));
        System.out.println(origin.toString("origin"));  
        double distSquare = p.distSquare(origin);
        System.out.println(toString("distSquare",distSquare));
        assertTrue(equals(distSquare,8d));
    }
    
    public void testDorst2007DrillsBasicObjects(){
        System.out.println("------------------ Dorst2007 drills (chapter 13.9.1): basic objects ------------------");
        
        // 1.
        // composition of a point in IPNS representation
        Point3d p1 = new Point3d(1d,1d,0d);
        double weight1 = 2;
        CGARoundPointIPNS p1cga = new  CGARoundPointIPNS(p1,weight1);
        CGARoundPointIPNS p1cgaTest = new CGARoundPointIPNS((CGAMultivector.createOrigin(1d).
                add(CGAMultivector.createE3(p1)).add(CGAMultivector.createInf(1d))).gp(2d));
        assertTrue(p1cga.equals(p1cgaTest));
        
        // decomposition
        // locationIPNS
        Point3d p1Test = p1cga.location();
        assert(equals(p1,p1Test));
        
        // squared weight
        double squaredWeight1Test = p1cga.squaredWeight();
        assert(equals(squaredWeight1Test, weight1*weight1));
        
        // 2.
        Point3d p2 = new Point3d(1d,0d,1d);
        double weight2 = -1;
        CGARoundPointIPNS p2cga = new  CGARoundPointIPNS(p2,weight2);
        
        // Abstände Punkte in IPNS representation
        double d = p1cga.distSquare(p2cga);
        assert(equals(d,2));
        
        // 3.
        // line in OPNS representation
        CGALineOPNS line = new CGALineOPNS(p1cga, p2cga);
        System.out.println(line.toString("lineOPNS"));
        CGALineOPNS lineTest = new CGALineOPNS(CGAMultivector.createOrigin(1d).add(CGAMultivector.createE3(p1)).
                gp(-2).op(CGAMultivector.createE3(new Point3d(0d,-1d,1d))).op(CGAMultivector.createInf(1d)));
        System.out.println(lineTest.toString("lineOPNS (test)"));
        assertTrue(line.equals(lineTest));
        
        // 4.
        // direction of the line
        Vector3d attitude = line.attitude();
        Vector3d attitudeTest = new Vector3d(0d,2d,-2d);
        assertTrue(equals(attitude, attitudeTest));
        
        // weight2 of the line
        double squaredLineWeight = line.squaredWeight();
        //FIXME mit 8 ist das um einen Faktor 2 zu gross
        // squaredLineWeight = 7.999999999999988
        // squaredLineWeight (test) = 4.0
        System.out.println(toString("squaredLineWeight",squaredLineWeight));
        //FIXME Wie bekomme ich das Vorzeichen?
        double lineWeightTest = -2;
        System.out.println(toString("squaredLineWeight (test)",lineWeightTest*lineWeightTest));
        //assertTrue(equals(squaredLineWeight, lineWeightTest*lineWeightTest));
        
        // 6.
        // plane through line and origin in OPNS representation
        CGAPlaneOPNS planeOPNS = new CGAPlaneOPNS(line.op(o));
        System.out.println(planeOPNS.toString("planeOPNS"));
        CGAPlaneOPNS planeOPNSTest = new CGAPlaneOPNS(o.op(e1).op(e3).op(inf).sub(o.op(e1).op(e2).op(inf)).add(
                                o.op(e2).op(e3).op(inf)).gp(2d));
        System.out.println(planeOPNSTest.toString("planeOPNS (test)"));
        assertTrue(planeOPNS.equals(planeOPNSTest));
        
        // 7.
        // direction (Normalenvektor?)
        // attitudeIntern(CGAOrientedFiniteFlatOPNS) = (-1.9999999999999991*e1^e2^ei + 1.9999999999999991*e1^e3^ei + 1.9999999999999991*e2^e3^ei)
        // The given multivector is not of grade 2: -1.9999999999999991*e1^e2^ei + 1.9999999999999991*e1^e3^ei + 1.9999999999999991*e2^e3^ei
        Vector3d attitudePlaneOPNS = planeOPNS.attitude();
        attitudePlaneOPNS.normalize();
        System.out.println(toString("attitudePlaneOPNS",attitudePlaneOPNS));
        CGAMultivector attitudePlaneOPNSTest = (e1.op(e3).op(inf).add(
                        e1.op(e2).op(inf)).add(e2.op(e3).op(inf))).gp(2d);
        System.out.println(attitudePlaneOPNSTest.toString("attitudePlaneOPNS (test)"));
        Vector3d dirTest = new Vector3d(2,2,-2);
        dirTest.normalize();
        assertTrue(equals(attitudePlaneOPNS, dirTest));
    }
    
    
    public void testDorst2007DrillsPointPairs(){
        System.out.println("------------------ Dorst2007 drills (chpater 14.9.1): point pair --------------");
       
        // point pair composition
        Point3d p1 = new Point3d(1d,0d,0d);
        double weight1 = 2d;
        Point3d p2 = new Point3d(0d,1d,0d);
        double weight2 = -1d;
        
        // 1.
        CGAOrientedPointPairOPNS pp = new CGAOrientedPointPairOPNS(p1, weight1, p2, weight2);
        // pp = (2.0*eo^e1 - 2.0*eo^e2 - 2.0*e1^e2 - 1.0*e1^ei + 1.0*e2^ei) (korrekt)
        System.out.println(pp.toString("pp"));
        CGAMultivector ppTest = CGAMultivector.createOrigin(2d).op(CGAMultivector.createEx(1d)).sub(
            CGAMultivector.createOrigin(2d).op(CGAMultivector.createEy(1d))).sub(
            CGAMultivector.createEx(2d).op(CGAMultivector.createEy(1d))).sub(
            CGAMultivector.createEx(1d).op(CGAMultivector.createInf(1d))).add(
            CGAMultivector.createEy(1d).op(CGAMultivector.createInf(1d)));
        System.out.println(ppTest.toString("ppTest"));
        assertTrue(pp.equals(ppTest));
        
        // 2.
        // locationIPNS
        Point3d c = pp.location();
        System.out.println(toString("c",c));
        // (e1+e2)/2
        Point3d cTest  = new Point3d(p1); cTest.add(p2); cTest.scale(0.5d);
        // new Point3d(0.5d,0.5d,0d);
        System.out.println(toString("cTest",cTest));
        assertTrue(equals(c,cTest));
        
        // 4. 
        // beide Punkte wieder aus dem paar rausholen
        Point3d[] points = pp.decomposePoints();
        System.out.println(toString("p1",points[0]));
        assert(equals(p1, points[0]));
        System.out.println(toString("p2", points[1]));
        assert(equals(p2, points[1]));
        
        // 2.
        // squaredSize
        //FIXME
        // falsches Ergebnis mit 1,75 statt 0.5
        // die input weights auf 1 zu setzen hat keinen Unterschied gebracht
        // FIXME dual() führt zu: The given multivector is not not grade 1! grade()=0
        //double radiusSquared = pp.dual().squaredSize();
        double radiusSquared = pp.squaredSize();
        System.out.println("radiusSquared="+String.valueOf(radiusSquared));
        double radiusSquaredTest = 0.5d;
        System.out.println("radiusSquaredTest="+String.valueOf(radiusSquaredTest));
        assertTrue(equals(radiusSquared,radiusSquaredTest));
    }
    
    public static String toString(String name, Tuple3d value){
        return name+" = ("+String.valueOf(value.x)+","+String.valueOf(value.y)+","+String.valueOf(value.z)+")";
    }
    public static String toString(String name, double value){
        return name+" = "+String.valueOf(value);
    }
    
    // alles korrekt
    public void testGanjaExampleCreatePointsCircleLine(){
        System.out.println("------------------Ganja.js expample: creation of points, circle, line --------------");
        
        // p1
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(new Vector3d(1d,0d,0d));
        // p1=1.0*eo + 1.0*e1 + 0.5*ei (korrekt)
        System.out.println(p1.toString("p1"));
        CGAMultivector p1Test = o.add(e1).add(inf.gp(0.5d));
        System.out.println(p1Test.toString("p1Test"));
        assert(p1.equals(p1Test));
        
        // p2
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(new Vector3d(0d,1d,0d));
        // p2=1.0*eo + 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p2="+p2.toString());
        CGAMultivector p2Test = o.add(e2).add(inf.gp(0.5d));
        System.out.println(p2Test.toString("p2Test"));
        assert(p2.equals(p2Test));
        
        // p3
        CGARoundPointIPNS p3 = new CGARoundPointIPNS(new Vector3d(0d,0d,1d));
        // p3=1.0*eo + 1.0*e3 + 0.5*ei (korrekt)
        System.out.println("p3="+p3.toString());
        CGAMultivector p3Test = o.add(e3).add(inf.gp(0.5d));
        System.out.println(p3Test.toString("p3Test"));
        assert(p3.equals(p3Test));
        
        // Compose circle from three points (OPNS representation)
        CGAOrientedCircleOPNS c = new CGAOrientedCircleOPNS(p1,p2,p3);
        // c=1.0*eo^e1^e2 - 1.0*eo^e1^e3 + 1.0*eo^e2^e3 + 1.0*e1^e2^e3 
        // + 0.5*e1^e2^ei - 0.5*e1^e3^ei + 0.5*e2^e3^ei (korrekt)
        System.out.println("c="+c.toString());
        CGAMultivector cTest = o.op(e1).op(e2).sub(o.op(e1).op(e3)).
                add(o.op(e2).op(e3)).add(e1.op(e2).op(e3)).
                add(e1.op(e2).op(inf).gp(0.5d).sub(e1.op(e3).op(inf).gp(0.5d)).
                add(e2.op(e3).op(inf).gp(0.5d)));
        System.out.println("cTest="+c.toString());
        assert(c.equals(cTest));
        
        double radiusSquared = c.squaredSize();
        System.out.println(toString("radiusSquared",radiusSquared));
        
        // composition of a line OPNS based on two points
        CGALineOPNS l = new CGALineOPNS(p1,p2);
        // l=-1.0*eo^e1^ei + 1.0*eo^e2^ei + 1.0*e1^e2^ei (korrekt)
        System.out.println("l="+l.toString());
        CGAMultivector lTest = o.op(e1).op(inf).gp(-1d).add(o.op(e2).op(inf).add(e1.op(e2).op(inf)));
        assert(l.equals(lTest));
    }
    
    // alles korrekt!
    public void testGanjaExampleCreatePointsPlaneSphere(){
        System.out.println("------------------Ganja.js expample: creation of points, plane, sphere --------------");
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(new Vector3d(1d,0d,0d));
        // p1=1.0*eo + 1.0*e1 + 0.5*ei (korrekt)
        System.out.println("p1="+p1.toString());
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(new Vector3d(0d,1d,0d));
        // p2=1.0*eo + 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p2="+p2.toString());
        CGARoundPointIPNS p3 = new CGARoundPointIPNS(new Vector3d(0d,0d,-1d));
        // p3=1.0*eo - 1.0*e3 + 0.5*ei (korrekt)
        System.out.println("p3="+p3.toString());
        CGARoundPointIPNS p4 = new CGARoundPointIPNS(new Vector3d(0d,-1d,-0d));
        // p4=1.0*eo - 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p4="+p4.toString());
        
        CGASphereOPNS s = new CGASphereOPNS(p1,p2,p3,p4);
        // s=2.0*eo^e1^e2^e3 - 1.0*e1^e2^e3^ei (korrekt)
        System.out.println("s="+s.toString());
        
        CGAPlaneOPNS p = new CGAPlaneOPNS(p1,p2,p3);
        // p1=1.0*eo^e1^e2^ei + 1.0*eo^e1^e3^ei - 1.0*eo^e2^e3^ei - 1.0*e1^e2^e3^ei (korrekt)
        System.out.println("p="+p.toString());
    }
    
    public void testGanjaExampleCreateDualSphereAndPlane(){
        System.out.println("------------------Ganja.js expample: creation of dual sphere and plane --------------");
        // We start by defining a null basis, and upcasting for points
        //var ni = 1e4+1e5, no = .5e5-.5e4, nino = ni^no;
        //var up = (x)=> no + x + .5*x*x*ni;

        // var p1 = up(1e1+.5e2)
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(new Vector3d(1d,0.5d,0d));
        // ganja.js: e1+0.5e2+0.12e4+1.12e5 = e1+0.5e2+0.625ei+e0 (korrekt)
        // java.js: p1=1.0*eo + 1.0*e1 + 0.5*e2 + 0.625*ei
        System.out.println(p1.toString("p1"));
        CGAMultivector p1Test = CGAMultivector.createE3(new Vector3d(1d,0.5d,0d)).
                add(CGAMultivector.createInf(0.625)).add(CGAMultivector.createOrigin(1d));
        assert(p1.equals(p1Test));
        
        // p2 = up(1e2-.5e3);
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(new Vector3d(0d,1d,0.5d));
        // java: p2=1.0*eo + 1.0*e2 + 0.5*e3 + 0.625*ei
        // ganja.js: e2-0.5e3+0.12e4+1.12e5
        System.out.println("p2="+p2.toString());
        CGAMultivector p2Test = CGAMultivector.createE3(new Vector3d(0d,1d,0.5d)).
                add(CGAMultivector.createOrigin(1d)).add(CGAMultivector.createInf(0.625d));
        assertTrue(p2.equals(p2Test));
        
        // The distance between two points.
        //var d = (a,b)=>((a<<b).Length*2)**.5;
        
        // The duality operator can be used to produce a sphereIPNS from a point.
        // var s = ()=>!(p1 - .3**2*.5*ni);
        // ganja.js: -1.08e1234 - 0.07e1235 - 0.5e1345 + e2345
        // s=-eo^e1^e2^e3 + 0.5*eo^e1^e3^ei 
        //   -eo^e2^e3^ei - 0.625*e1^e2^e3^ei
        CGASphereIPNS s1 = new CGASphereIPNS(p1, 0.3);
        // java: s1=1.0*eo + 1.0*e1 + 0.5*e2 + 0.58*ei (korrekt)
        System.out.println("s1="+s1.toString());
        CGASphereOPNS s = s1.undual();
        System.out.println("s="+s.toString());
        
        CGAMultivector inf_no = createInf(1d).op(createOrigin(1d));
        // Planes also have a direct dual representation. (but start from vector not point)
        // var p1 = ()=>!(d(p2,no)*ni + (p2^nino*nino).Normalized);
        
        CGAMultivector n = ((new CGARoundPointIPNS(p2)).op(inf_no).gp(inf_no)).normalize();
        // n=-0.8944271909999159*e2 - 0.4472135954999579*e3
        System.out.println("n="+n.toString());
        CGAPlaneOPNS p = new CGAPlaneIPNS((CGAMultivector.createInf(Math.sqrt(p2.distSquare(new CGARoundPointIPNS(CGAMultivector.createOrigin(1d)))))).add(
                        n)).undual();
        // java p1=-0.447*eo^e1^e2^ei - 0.89*eo^e1^e3^ei - 1.118*e1^e2^e3^ei
        // ganja.js p1= -1.11e1234-1.11e1235-0.44e1245-0.89e1345
        // TODO
        System.out.println("p="+p.toString());
       
        double d = p2.distSquare(new CGARoundPointIPNS(CGAMultivector.createOrigin(1d)));
        // java d=1.2500000000000007
        System.out.println("d="+String.valueOf(d));
        // vermutlich brauche ich n als CGAAttitude und dann einen passenden Konstruktor
        //TODO
        //CGAPlane pa = CGAPlaneIPNS(n, d);
        
        // p1= -1.11e1234-1.11e1235-0.44e1245-0.89e1345

        // You can use the regressive product to calculate intersections..
        //var c = ()=>s&p1;
        CGAOrientedCircleOPNS c = new CGAOrientedCircleOPNS(s.vee(p));
        // ganja.js: c=-1.11e123 - 0.48e124 - 0.03e125 - 0.40e134 + 0.48e135 + 
        // 0.22e145 - 1.11e234 -1.11e235-0.44e245-0.89e345
        // java: c=0.447*eo^e1^e2 - 0.89*eo^e1^e3 - 1.118*e1^e2^e3 
        // + 0.2236*eo^e1^ei - 0.4472135954999568*eo^e2^ei - 0.259*e1^e2^ei 
        // + 0.894*eo^e3^ei + 1.0777*e1^e3^ei - 1.1180*e2^e3^ei
        // TODO
        System.out.println("c="+c.toString());
    }
    
    public void testGanjaExampleIntersections(){
        
        System.out.println("------------------Ganja.js expample: intersections --------------");
        
        //p  = up(0)                          // point
        CGARoundPointIPNS p = new CGARoundPointIPNS(new Vector3d(0d,0d,0d));
        // p1=1.0*eo (korrekt)
        System.out.println("p="+p.toString());
        
        //S  = ()=>!(p1-.5*ni),                 // main dual sphereIPNS around point 
        CGASphereOPNS S = (new CGASphereIPNS(p, 1d)).undual();
        // java: S=-eo^e1^e2^e3 + 0.49*e1^e2^e3^ei
        // ganja.js e1235 = 0.5e123i-e0123 (korrekt)
        System.out.println("S="+S.toString());
        
        //S2 = !(up(-1.4e1)-0.125*ni),         // left dual sphereIPNS
        CGASphereOPNS S2 = (new CGASphereIPNS(new CGARoundPointIPNS(new Vector3d(-1.4,0d,0d)), 0.5d)).undual();
        // ganja.js: -1.35e1234-0.35e1235-1.39e2345 = -0.85e123i - e0123 +1.4e023i
        // java: S2=-eo^e1^e2^e3 + 1.399*eo^e2^e3^ei - 0.85*e1^e2^e3^ei (korrekt)
        System.out.println("S2="+S2.toString());
        
        CGAPlaneIPNS plane = new CGAPlaneIPNS(new Vector3d(0d,0d,1d), 0d);
        System.out.println("plane="+plane.toString());
        //C  = !(up(1.4e1)-.125*ni)&!(1e3),    // right circleIPNS
        CGAOrientedCircleOPNS C = new CGAOrientedCircleOPNS(S2.vee(plane.dual()));
        // ganja.js: 1.35e124+0.35e125+1.39e245 = 1.3e02i + 0.85e12i -e012 (korrekt)
        // java: C=-eo^e1^e2 - 1.34*eo^e2^ei + 0.85*e1^e2^ei
        System.out.println("C="+C.toString());
        
        //L  = up(.9e2)^up(.9e2-1e1)^ni,       // top line
        // ganja.js: 0.89e124+0.89e125-e145 = -e01i+0.9e12i
        // java: l=-1.0*eo^e1^ei + 0.9*e1^e2^ei (korrekt)
        CGALineOPNS L = new CGALineOPNS(new Point3d(0d,0.9d,0d), new Point3d(-1d,0.9d,0d));
        System.out.println("L="+L.toString());
        
        //P  = !(1e2-.9*ni),                   // bottom dual planeIPNS
        // ganja.js: 0.89e1234+0.89e1235-e1345 = e013i + 0.9e123i (korrekt)
        // P=5.551115123125783E-17*eo^e1^e2^e3 + eo^e1^e3^ei + 0.9*e1^e2^e3^ei
        CGAPlaneOPNS P = (new CGAPlaneIPNS(new Vector3d(0d,1d,0d), -0.9d)).undual();
        System.out.println("P="+P.toString());
        
        //P2 = !(1e1+1.7*ni);                  // right dual planeIPNS
        // ganja.js: -1.70e1234-1.70e1235+e2345 = -1.7e123i - e023i (korrekt)
        // P2=-1.1102230246251565E-16*eo^e1^e2^e3 - eo^e2^e3^ei - 1.7*e1^e2^e3^ei
        CGAPlaneOPNS P2 = (new CGAPlaneIPNS(new Vector3d(1d,0d,0d), 1.7d)).undual();
        System.out.println("P2="+P2.toString());
        
        // The intersections of the big sphereIPNS with the other 4 objects.
        //var C1 = ()=>S&P sphereIPNS meets planeIPNS
        CGAOrientedCircleOPNS C1 = new CGAOrientedCircleOPNS(S.vee(P));
        // ganja.js: 0.89e123+e135 = 0.89e123 + 0.5e13i + e013 (korrekt)
        // java: C1=eo^e1^e3 + 0.9*e1^e2^e3 + 0.49*e1^e3^ei
        System.out.println("C1="+C1.toString());
        
        // C2 = ()=>S&L 
        CGAOrientedPointPairOPNS pp = new CGAOrientedPointPairOPNS(S.vee(L));
        // java: s&l=eo^e1 - 0.89*e1^e2 - 0.5*e1^ei
        // ganja.js: -0.89e12-e15 = 0.89e12-0.5e1i + e01 (korrekt)
        System.out.println("s&l="+pp.toString());
        
        // C3 = ()=>S&S2 sphereIPNS meet sphereIPNS
        CGAOrientedCircleOPNS C3 = new CGAOrientedCircleOPNS(S.vee(S2));
        // ganja.js: -1.35e123+1.39e235 = -1.35e123+0.7e23i+1.39e023
        // java: s&s=1.4*eo^e2^e3 - 1.35*e1^e2^e3 - 0.7*e2^e3^ei (korrekt)
        System.out.println("s&s="+C3.toString());
        
        // C4 = ()=>S&C 
        CGAOrientedPointPairOPNS C4 = new CGAOrientedPointPairOPNS(S.vee(C));
        // java s&c=1.39*eo^e2 - 1.35*e1^e2 - 0.69*e2^ei
        // ganja -1.35e12+1.39e25
        // TODO
        System.out.println("s&c="+C4.toString());
        
        // C5 = ()=>C&P2;  circleIPNS meet planeIPNS
        CGAOrientedPointPairOPNS C5 = new CGAOrientedPointPairOPNS(C.vee(P2));
        // ganja.js: 1.7e12-1.02e24-2.02e25 = 1.7e12 
        // java: c&p1=eo^e2 + 1.7*e1^e2 + 3.23*e2^ei
        //FIXME stimmt nur in einer Komponente überein
        //TODO vermutlich sind bereits der Ausgangs-circleIPNS oder planeIPNS flasch --> überprüfen
        System.out.println("c&p="+C5.toString());
        
        // For line meet planeIPNS its a bit more involved.
        //var lp = up(nino<<(P2&L^no));
        CGARoundPointIPNS lp = new CGARoundPointIPNS(CGAMultivector.createInf(1d).
                op(CGAMultivector.createOrigin(1d)).lc(P2.vee(L).op(
                        CGAMultivector.createInf(1d))).extractE3ToPoint3d());
        System.out.println("lp="+lp.toString());
        // ganja.js 1.7e1+0.89e2+1.35e4+2.34e5
        //TODO
    }
    
    public void testGanjaExampleProjectReject(){
        
        System.out.println("------------------Ganja.js expample: project, reject --------------");
        
        // We start by defining a null basis, and upcasting for points
        // var ni = 1e4+1e5, no = .5e5-.5e4, nino = ni^no,
        // up = (x)=>no+x+.5*x*x*ni,
        // sphereIPNS = (P,r)=>!(P-r**2*.5*ni),
        // planeIPNS  = (v,h=0)=>!(v-h*ni);

        // Project and reject.      
        // var project_point_on_round            = (point,sphereIPNS)=>-point^ni<<sphere<<sphere,
        // project_point_on_flat             = (point,planeIPNS)=>up(-point<<plane<<plane^nino*nino),
        // plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;

        // var p1  = up(.5e2),                     // point
        CGARoundPointIPNS p = new CGARoundPointIPNS(new Vector3d(0d,0.5d,0d));
        // ganja.js: 0.5e2-0.37e4+0.62e5 = e0 + 0.5e2 + 0.125ei (korrekt)
        // java p1=1.0*eo + 0.5*e2 + 0.125*ei
        System.out.println("p="+p.toString());
        
        // S  = sphereIPNS(up(-1.4e1),.5),            // left sphereIPNS
        // sphereIPNS = (P,r)=>!(P-r**2*.5*ni)
        CGASphereOPNS S = new CGASphereOPNS((new CGASphereIPNS(new Point3d(-1.4d,0d,0d),0.5)).dual());
        // java S*=1.0*eo - 1.4*e1 + 0.85*ei
        // java S=-eo^e1^e2^e3 + 1.34*eo^e2^e3^ei - 0.856*e1^e2^e3^ei
        // ganja: -1.35e1234-0.35e1235-1.39e2345 = -0.8e123i-e0123+1.4e023i (korrekt)
        System.out.println("S="+S.toString());
        
        // sphereIPNS = (P,r)=>!(P-r**2*.5*ni)
        // planeIPNS  = (v,h=0)=>!(v-h*ni);
        // C  = sphereIPNS(up(1.4e1),.5)&planeIPNS(1e3),  // right circleIPNS
        CGASphereOPNS sphere = (new CGASphereIPNS(new Point3d(1.4d,0d,0d),0.5d)).undual();
        System.out.println("sphere="+sphere.toString());
        // sphereIPNS=-eo^e1^e2^e3 - 1.34*eo^e2^e3^ei - 0.8545*e1^e2^e3^ei
        //TODO
        CGAOrientedCircleOPNS C = new CGAOrientedCircleOPNS(sphere.vee((
                new CGAPlaneIPNS(new Vector3d(0d,0d,1d),0d)).dual()));
        // ganja.js: 1.35e124+0.35e125+1.39e245
        // C=-eo^e1^e2 + 1.34*eo^e2^ei + 0.855*e1^e2^ei
        System.out.println("C="+C.toString());
        //TODO
        
        // L  = up(.9e2)^up(.9e2-1e1)^ni,         // top line
        CGALineOPNS L = new CGALineOPNS(new Point3d(0d,0.9d,0d), new Point3d(-1d,0.9d,0d));
        // ganja.js: 0.89e124+0.89e125-e145 (grade3)
        // java L=-1.0*eo^e1^ei + 0.9*e1^e2^ei (korrekt)
        System.out.println("L="+L.toString());
        
       
        // planeIPNS  = (v,h=0)=>!(v-h*ni);
        // P  = planeIPNS(1e2,.9);                    // bottom planeIPNS
        CGAPlaneOPNS P = (new CGAPlaneIPNS(new Vector3d(0d,1d,0d),0.9d)).undual();
        // ganja.js: 0.89e1234+0.89e1235-e1345
        // java: P*=1.0*e2 + 0.9*ei
        // java eo^e1^e3^ei - 0.9*e1^e2^e3^ei (korrekt)
        System.out.println("P="+P.toString());
        
        // project point on sphereIPNS
        // var project_point_on_round            = (point,sphereIPNS)=>-point^ni<<sphere<<sphere
        // ()=>project_point_on_round(p1,S), "p1 on S"
        CGAOrientedPointPairOPNS pOnS = S.project(p);
        // ganja.js: 0.7e12-1.89e14-0.49e15+0.30e24+0.80e25-1.95e45 = 0.7e12-1.19e1i-1.44e01+0.55e2i-0.5e02+1.95e0i
        // java POnS=-1.4*eo^e1 - 0.5*eo^e2 + 0.7*e1^e2 + 1.96*eo^ei - 1.197*e1^ei + 0.557*e2^ei (korrekt)
        System.out.println("POnS="+pOnS.toString());
        
        //()=>project_point_on_round(~p1,C), "p1 on C",   // point on circleIPNS
        // java.lang.IllegalArgumentException: The given multivector is not not grade 1! grade()=2
        CGAOrientedPointPairOPNS pOnC = C.project(new CGARoundPointIPNS(p.conjugate()));
        // ganja.js -0.70e12 + 1.89e14+0.49e15+0.30e24+0.80e25-1.95e4 = -0.7e12+1.19e1i-1.44e01+0.55e2i-0.5e02
        // java pOnC=1.4*eo^e1 - 0.5*eo^e2 - 0.7*e1^e2 + 1.96*eo^ei + 1.197*e1^ei + 0.553*e2^ei
        System.out.println("pOnC="+pOnC.toString()); // (korrekt)
        
        // project_point_on_flat             = (point,planeIPNS)=>up(-point<<plane<<plane^nino*nino),
        
        // ()=>project_point_on_flat(p1,P),   "p1 on P",   // point on planeIPNS
        CGARoundPointIPNS pOnP = P.project(p);
        // ganja.js: -0.90e2-0.09e4+0.90e5 = e0-0.9e2+0.41ei
        // java pOnP=1.0*eo - 0.899*e2 + 0.4049*ei (korrekt)
        System.out.println("pOnP="+pOnP.toString());
        
        // ()=>project_point_on_flat(~p1,L),  "p1 on L",   // point on line
        CGARoundPointIPNS pConjugate = new CGARoundPointIPNS(p.conjugate());
        // java pConjugate= -eo - 0.5*e2 - 0.125*ei
        // ganja.js: pConjugate = -e0 - 0.5e2 - 0.125ei (korrekt)
        System.out.println("pConjugate="+pConjugate.toString());
        CGAMultivector pOnL = L.project(pConjugate); 
        // ganja.js: 0.89e2-0.09e4+0.90e5 = e0 + 0.89e2 + 0.4ei 
        // java pOnL=eo - 0.899*e2 + 0.4049*ei
        //FIXME Das Vorzeichen bei e2 scheint falsch zu sein
        System.out.println("pOnL="+pOnL.toString());
        //TODO Da conjugate korrekt scheint muss wohl die project-impl für die
        // Projektion auf Linien falsch sein, obowohl die Projektion auf Ebenen korrekt ist.
        
        
        // ()=>plane_through_point_tangent_to_x(p1,S),    // planeIPNS through p1 tangent to S
        // tangent() error da falscher grade
        //FIXME
        //CGADualPlane pToS = S.tangent(p1);
        // ganja.js 0.25e1234+0.25e1235+0.5e1345-1.39e2345
        // java pToS=0.0724999999999999*e1^e3
        //System.out.println("pToS="+pToS.toString());
        //TODO
        
        // ()=>plane_through_point_tangent_to_x(p1,P),    // planeIPNS through p1 tangent to P
        CGAPlaneOPNS pToP = P.tangent(p);
        System.out.println("pToP="+pToP.toString());
        // java pToP=-0.19999999999999984*e1^e3
        // ganja.js -0.5e1234-0.5e1235-e1345
        //TODO
        
        // Graph the items. (hex numbers are html5 colors, two extra first bytes = alpha)
        // document.body.appendChild(this.graph([ 
        //      0x00FF0000, p1, "p1",                                       // point 
        //      0x00008800, ()=>project_point_on_round(p1,S), "p1 on S",    // point on sphereIPNS
        //      0x000000FF, ()=>project_point_on_round(~p1,C), "p1 on C",   // point on circleIPNS
        //  0x00888800, ()=>project_point_on_flat(p1,P),   "p1 on P",   // point on planeIPNS
        //  0x00008888, ()=>project_point_on_flat(~p1,L),  "p1 on L",   // point on line
        //  0xc0FF0000, ()=>plane_through_point_tangent_to_x(p1,S),    // planeIPNS through p1 tangent to S2
        //  0xc000FF00, ()=>plane_through_point_tangent_to_x(p1,P),    // planeIPNS through p1 tangent to P
        //  0,L,0,C,                                                  // line and circleIPNS
        //  0xE0008800, P,                                            // planeIPNS
        //  0xE0FFFFFF, S                                             // spheres
  
    }
    
    // DefVarsE3(); // Define variables for E3
    // ?M = 1 + e1 + e2^e3; // Define some multivector
    // ?iM = !M; // Evaluate inverse of M
    // ?"M * iM = " + M*iM; // Check that iM is inverse of M
    // ?W = 1 + e1; // A non-invertible multivector
    // ?iW = !W; // The inversion
    //FIXME ist das wirklich für cga?
    public void testInverse(){
        // TODO
        
        /* output
        M = 1 + 1^e1 + 1^e23
        iM = 0.2 + 0.2^e1 + -0.6^e23 + 0.4^I
        M * iM = 1
        W = 1 + 1^e1
        iW = 0
        */
    }
    
    public void testBasisBlades(){
        System.out.println("------------------Basis blades--------------");
        CGAMultivector m = CGAMultivector.createOrigin(1d).ip(CGAMultivector.createInf(1d));
        System.out.println("e0.einf="+m.toString());
        assertTrue(m.equals(new CGAScalar(-1d)));
    }
    
    public void testPlane(){
        System.out.println("---------------------- plane ----");
        
        // hessissche Normalenform der Ebene
        Vector3d n = new Vector3d(0d,0d,1d); n.normalize();
        double d = 2d;
        System.out.println("HNF: n=("+String.valueOf(n.x)+","+String.valueOf(n.y)+", "+String.valueOf(n.z)+"), d="+String.valueOf(d));
        
        CGAPlaneIPNS planeIPNS = new CGAPlaneIPNS(n, d);
        // planeIPNS=1.0*e3 + 2.0*ei
        System.out.println(planeIPNS.toString("planeIPNS"));
        
        // attitude
        Vector3d attitude = planeIPNS.attitude();
        System.out.println(toString("attitude (planeIPNS)",attitude));
        // attitude_cga=-0.9999999999999996*e1^e2^ei
        // attitude (planeIPNS) = (0.0,0.0,-0.9999999999999996)
        // FIXME
        // Das Vorzeichen stimmt nicht, möglicherweise ist das aber auch korrekt so
        // und das Vorzeichen wird nur durch die Definition des Pseudoskalars im 
        // in CGA festgelegt
        
        // squaredWeight
        double squaredWeight = planeIPNS.squaredWeight();
        // 1 da nichts bei der composition angegeben wurde
        System.out.println(toString("squaredWeight", squaredWeight));
        assertTrue(equals(squaredWeight,1d));
        
        Point3d probe = new Point3d(5,5,2);
        Point3d location = planeIPNS.location(probe);
        // location E3 (CGAOrientedFiniteFlatIPNS) = (24.99999999999999*e1 + 24.99999999999999*e2 - 46.0*e3)
        // location_cga (Finite flat) = (24.99999999999999*e1 + 24.99999999999999*e2 - 46.0*e3)
        // location (plane IPNS) = (24.99999999999999,24.99999999999999,-46.0)
        System.out.println(toString("location (plane IPNS)",location));
        Vector3d locationTest = new Vector3d(n);
        locationTest.normalize();
        locationTest.scale(d);
        assertTrue(equals(location, locationTest));
        
        // nach Kleppe2016
        // liefet aber 0 sowohl für IPNS als auch für OPNS
        //FIXME
        CGAMultivector normal = planeIPNS.undual().op(inf).negate().ip(o);
        System.out.println(normal.toString("normal vector nach Kleppe 2016"));
        
        CGAPlaneIPNS plane1 = new CGAPlaneIPNS(new Point3d(0d,0d,2d));
        // plane1=-1.0*e3 + 2.0000000000000004*ei
        //FIXME Vorzeichenfehler bei e3 egal welche normalize()-Methode ich verwende
        System.out.println(plane1.toString("plane1"));
        
    }
    
    public void testCircle(){
        System.out.println("----------------- circle -----");
        
        // Kugel 1
        Point3d p1 = new Point3d(1d,0d,0d);
        System.out.println(toString("p1",p1));
        double radius = 1.5d;
        System.out.println("radius="+String.valueOf(radius));
        CGASphereIPNS sphere1 = new CGASphereIPNS(p1, radius);
        System.out.println("sphere1="+sphere1.toString());
        
        // Kugel 2
        Point3d p2 = new Point3d(-1d,0d,0d);
        System.out.println(toString("p2",p2));
        CGASphereIPNS sphere2 = new CGASphereIPNS(p2, radius);
        System.out.println("sphere2="+sphere2.toString());
        
        // Circle from two spheres
        CGAOrientedCircleIPNS circleIPNS = new CGAOrientedCircleIPNS(sphere1, sphere2);
        System.out.println("circle="+circleIPNS.toString());
        // circle=-2.0*eo^e1 - 1.25*e1^ei (grade 2 ok)
        // squared size
        double squaredSize = circleIPNS.squaredSize();
        // squaredSize/radiusSquared = (-1.3906250000000013)
        System.out.println(toString("squaredSize (circleIPNS)",squaredSize));
        double squaredSize2 = circleIPNS.squaredSize2();
        // squaredSize2 (circleIPNS) = 6.249999999999992
        System.out.println(toString("squaredSize2 (circleIPNS)",squaredSize2));
        
        // squaredSize (circleIPNS) = 1.3906250000000013
        //FIXME
        assert(equals(squaredSize,1.25d));
        
        //double squaredSize2 = circleIPNS.squaredSize2();
        // The given multivector m is not of grade 3! -Infinity*e1
        //FIXME
        //System.out.println(toString("squaredSize2 (circleIPNS)",squaredSize2));
        
        // locationIPNS
        Point3d locationIPNS = circleIPNS.location();
        // sollte in x-Richtung zeigen, aber welches Vorzeichen?
        System.out.println(toString("location",locationIPNS));
        
        // attitude
        Vector3d attitudeIPNS = circleIPNS.attitude();
        // The given multivector m is not of grade 3! -Infinity*e1
        System.out.println(toString("attitude IPNS", attitudeIPNS));
        
        // weight
        // The given multivector m is not of grade 3! -Infinity*e1
        //FIXMe
        //double squaredWeight = circleIPNS.squaredWeight();
        //System.out.println(toString("squaredWeight", squaredWeight));
        
        // ipns circle based on euclidean parameters
        Point3d centerTest = new Point3d(0d,0d,0d);
        Vector3d normalTest = new Vector3d(1d,0d,0d);
        double radiusTest = 1.25;
        double weightTest = 1d;
        //The given multivector is not not grade 1! grade()=0 0
        //FIXME failed
        //CGAOrientedCircleIPNS circleIPNSTest = new CGAOrientedCircleIPNS(centerTest, normalTest, radiusTest, weightTest);
        
        //circle=1.0*eo^e1 + 1.0*eo^e2 - 1.0*e1^e3 - 1.0*e2^e3 + 1.04*eo^ei + 1.5204*e1^ei + 1.5204*e2^ei + 1.04*e3^ei
        //attitude=Infinity*e1 + Infinity*e2
        //The given multivector is not not grade 1! grade()=-1 Infinity*e3 + NaN*e1^e2^e3
        //FIXME
        /*RoundAndTangentParameters decomposition = circleIPNS.decompose();
        Vector3d attitude = decomposition.attitude();
        Point3d locCP1 = decomposition.locCP1();
        
        System.out.println("attitude = "+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z));
        System.out.println("locCP1 = "+String.valueOf(locCP1.x)+", "+String.valueOf(locCP1.y)+", "+String.valueOf(locCP1.z));
    */
    }
   
    public void testSpheresOPNS(){
        System.out.println("----------------- spheres OPNS --------------------");
        
        // Def. Kugel durch 4 Punkte die auf der Kugeloberflächen liegen sollen
        Point3d p1 = new Point3d(0d,-1d,0d);
        Point3d p2 = new Point3d(1d,0d,0d);
        Point3d p3 = new Point3d(0d,1d,0d);
        Point3d p4 = new Point3d(0d,0d,1d);
        CGASphereOPNS sphereOPNS = new CGASphereOPNS(p1,p2,p3,p4);
        System.out.println(sphereOPNS.toString("sphereOPNS"));
        
        // squaredWeight
        double squaredWeight = sphereOPNS.squaredWeight();
        //squaredWeight(sphereOPNS) = 3.9999999999999964
        // Unklar, ob das so stimmt!!!
        //TODO
        System.out.println(toString("squaredWeight(sphereOPNS)",squaredWeight));
        
        // attitude
        Vector3d attitude = sphereOPNS.attitude();
        System.out.println(toString("attitude(sphereOPNS)",attitude));
        
        // locationIPNS
        Point3d location = sphereOPNS.location();
        System.out.println(toString("location",location));
        assertTrue(equals(new Point3d(0d,0d,0d),location));
        
        // squared size
        double radiusSquared = sphereOPNS.squaredSize();
        System.out.println(toString("radiusSquared",radiusSquared));
        // failed da 1.25 statt 1
        //FIXME warum kommt da 1.25 raus?
        assertTrue(equals(radiusSquared,1d));
    }
    
    public void testSpheresIPNS(){
        
        System.out.println("----------------- sphere IPNS-----");
        Point3d p1 = new Point3d(0d,-1d,0d);
        System.out.println(toString("p1", p1));
        
        double radius = 2d;
        System.out.println("input radius="+String.valueOf(radius));
        double weight = 3d;
        System.out.println("input weight="+String.valueOf(weight));
         
        CGASphereIPNS sphereIPNS = new CGASphereIPNS(p1, radius, weight);
        System.out.println(sphereIPNS.toString("sphereIPNS"));
        
        
        // squaredWeight
        double squaredweight = sphereIPNS.squaredWeight(); // ich bekomme jetzt aber -1 heraus
        System.out.println(toString("squaredWeight",squaredweight));
        assert(equals(squaredweight,weight*weight));
       
        // squaredSize/squaredRadius
        // squaredSize/radiusSquared = (4.2500000000000036) (failed)
        double squaredRadius = sphereIPNS.squaredSize();
        System.out.println(toString("radius2squared", squaredRadius));
        //assertTrue(equals(radius*radius,squaredRadius));
        double squaredRadius2 = sphereIPNS.squaredSize2();
        System.out.println(toString("radius2squared2", squaredRadius2)); // 4.0 stimmt
        assertTrue(equals(radius*radius,squaredRadius2));
        
        // locationIPNS
        Point3d loc1 = sphereIPNS.location();
        System.out.println(toString("location", loc1));
        assertTrue(equals(loc1, p1));

        // nach Hildenbrand1998 locationIPNS --> grade 1
        CGARoundPointIPNS m = new CGARoundPointIPNS(sphereIPNS.gp(CGAMultivector.createInf(1d)).gp(sphereIPNS));
        System.out.println("sphere center from sandwhich product = "+m.toString());
        Point3d loc2 = m.location();
        assert(equals(p1, loc2));
         
        //???????????
        // Dorst2007: -einf*P = 3 stimmt? soll das die Normierung sein?
        System.out.println("-einf*sphere = "+
                String.valueOf(-CGAMultivector.createInf(1d).scp(sphereIPNS)));
        // norm(p1) = 6 ist sollte das nicht 1 sein?
        System.out.println("norm(sphere) = "+String.valueOf(sphereIPNS.norm()));
    }
    
    public void testDecomposeLocation(){
        System.out.println("--------------- decompose location for several objecs in IPNS and OPNS representation  -------");
        
        Point3d p1 = new Point3d(0.02,0.02,1);
        System.out.println("p1=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        
        Point3d p2 = new Point3d(0.02,0.02,2);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        
        Point3d p3 = new Point3d(0.5,0.02,2);
        System.out.println("p3=("+String.valueOf(p3.x)+","+String.valueOf(p3.y)+","+String.valueOf(p3.z)+")");
        
        
        // test IPNS representations
        
        // test round points IPNS
        
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1, 2d);
        System.out.println("cp1="+cp1.toString());
        //RoundAndTangentParameters decomposed1 = cp1.decompose();
        Point3d locCP1 = cp1.location();
        System.out.println("loc(cp1) = ("+String.valueOf(locCP1.x)+", "+String.valueOf(locCP1.y)+", "+
                String.valueOf(locCP1.z)+")");
        assertTrue(equals(p1,locCP1));
        
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2, 2d);
        System.out.println("cp2="+cp2.toString());
        //RoundAndTangentParameters decomposed2 = cp2.decompose();
        Point3d locCP2 = cp2.location();
        System.out.println("loc(cp2) = ("+String.valueOf(locCP2.x)+", "+String.valueOf(locCP2.y)+", "+
                String.valueOf(locCP2.z)+")");
        assertTrue(equals(p2,locCP2));
        
        // point-pairs IPNS representation (failed)
        CGAOrientedPointPairIPNS pp1 = new CGAOrientedPointPairIPNS(cp1,cp2);
        //RoundAndTangentParameters decomposed3 = pp1.decompose();
        // loc(pp1) = (Infinity, Infinity, -Infinity) (ERROR)
        //FIXME
        Point3d location3 = pp1.location();
        System.out.println("loc(pp1) = ("+String.valueOf(location3.x)+", "+String.valueOf(location3.y)+", "+
                String.valueOf(location3.z)+")");
        
        
        // spheres
        CGASphereIPNS s1 = new CGASphereIPNS(p1, 2d);
        System.out.println(s1.toString("s1"));
        //RoundAndTangentParameters decomposed4 = s1.decompose();
        Point3d location4 = s1.location();
        System.out.println(toString("s1loc",location4));
        assertTrue(equals(p1,location4)); 
        
        CGASphereIPNS s2 = new CGASphereIPNS(p2, 3d);
        System.out.println("s2="+s2.toString());
        //RoundAndTangentParameters decomposed5 = s2.decompose();
        Point3d location5 = s2.location();
        System.out.println("loc(s2) = ("+String.valueOf(location5.x)+", "+String.valueOf(location5.y)+", "+
                String.valueOf(location5.z)+")");
        assertTrue(equals(p2,location5));
        
        // circles (failed)
        CGAOrientedCircleIPNS c1 = new CGAOrientedCircleIPNS(s1,s2);
        // c1=1.0*eo^e3 + 0.02*e1^e3 + 0.02*e2^e3 - 1.0*eo^ei - 0.02*e1^ei - 0.02*e2^ei + 0.49960000000000004*e3^ei
        System.out.println(c1.toString("c1"));
        Point3d location6 = c1.location();
        // c1=1.0*eo^e3 + 0.02*e1^e3 + 0.02*e2^e3 - 1.0*eo^ei - 0.02*e1^ei - 0.02*e2^ei + 0.49960000000000004*e3^ei
        // The given multivector m is not of grade 3! Infinity*e3
        //FIXME
        System.out.println("loc(c1) = ("+String.valueOf(location6.x)+", "+String.valueOf(location6.y)+", "+
                String.valueOf(location6.z)+")");
        
        
        // opns representations
        
        //points in OPNS representation (failed)
        CGARoundPointOPNS cp1b = new CGARoundPointOPNS(p1);
        System.out.println(cp1b.toString("cp1b"));
        Point3d location7 = cp1b.location();
        // FIXME locationIPNS hat nur die halbe Größe
        System.out.println(toString("p1",p1));
        System.out.println(toString("loc(cp1b)",location7));
        //assertTrue(equals(p1,location7));
        
        // point pair OPNS (failed)
        CGAOrientedPointPairOPNS pp2 = new CGAOrientedPointPairOPNS(p1, 1d ,p2, 1d);
        // pp=1.0*eo^e3 + 0.02*e1^e3 + 0.02*e2^e3 + 1.5*eo^ei + 0.030000000000000002*e1^ei + 0.030000000000000002*e2^ei + 0.9996*e3^ei
        System.out.println("pp2="+pp2.toString());
        // FIXME e1 und e2 die doppelt so grosse Werte wie erwartet
        // locationFromTangentAndRound=3.250000000000001*eo + 0.06500000000000002*e1 + 0.06500000000000002*e2 + 1.4994000000000005*e3 + 5.088522196198628E-19*eo^e1^e3 + 5.088522196198628E-19*eo^e2^e3 - 0.9996*ei + 7.632783294297935E-19*eo^e1^ei + 7.632783294297935E-19*eo^e2^ei - 3.7001512964707234E-16*eo^e3^ei - 6.9375061251264494E-18*e1^e3^ei - 6.9375061251264494E-18*e2^e3^ei
        //RoundAndTangentParameters decomposed8 = pp2.decompose();
        Point3d location8 = pp2.location();
        System.out.println(toString("loc(pp2)",location8));
        Point3d p12 = new Point3d(p1); p12.add(p2); p12.scale(0.5d);
        System.out.println(toString("p12",p12));
        //assertTrue(equals(p12,location8));
        
         // point-pairs OPNS represenation locationIPNS (failed)
        CGAOrientedPointPairOPNS pp1OPNS = new CGAOrientedPointPairOPNS(cp1,cp2);
        Point3d locPPOPNS = pp1OPNS.location();
        // pp1OPNS = (0.06500000000000002,0.06500000000000002,1.4994000000000005)
        // locPPOPNSTest = (0.02,0.02,1.5)
        System.out.println(toString("pp1OPNS",locPPOPNS));
        Point3d locPPOPNSTest = new Point3d(p1); locPPOPNSTest.add(p2); locPPOPNSTest.scale(0.5d);
        System.out.println(toString("locPPOPNSTest",locPPOPNSTest));
        //assertTrue(equals(locPPOPNS,locPPOPNSTest));
       
        
        // spheres (failed)
        CGASphereOPNS s1b = new CGASphereOPNS(p1, 2d);
        System.out.println("s1b="+s1b.toString());
        Point3d location9 = s1b.location();
        System.out.println(toString("loc(s1b)",location9));
        //assertTrue(equals(p1, location9));
        
        // circles
        CGAOrientedCircleOPNS c1b = new CGAOrientedCircleOPNS(p1,p2,p3);
        System.out.println(c1b.toString("c1b"));
        Point3d location10 = c1b.location();
        System.out.println(toString("loc(c1b)",location10));
        //assert(equals(location10, ));
    }
        
    public void testPointsIPNS(){
        System.out.println("--------------- points in IPNS representation -------");
        Point3d p = new Point3d(0d,0d,1d);
        System.out.println(toString("p",p));
        double weight = 2d;
        CGARoundPointIPNS cp = new CGARoundPointIPNS(p, weight);
        System.out.println("cp="+cp.toString());
        
        // products
        CGAMultivector testgp = cp.gp(cp);
        // P*P=9.00480064 Warum eigentlich?
        // ist das gleich p.sqr()?
        // muesst da nicht mit dem reverse multipliziert werden um norm quadrdat zu bekommen
        //FIXME
        System.out.println("P*P="+testgp);
        CGAMultivector testip = cp.ip(cp);
        // P.P=1.6653345369377348E-15 (korrekt) sollte praktisch 0 sein
        System.out.println("P.P="+testip);
        assertTrue(equals(testip.scalarPart(),0));
        
        // squaredWeight
        System.out.println(toString("squaredWeight",weight*weight));
        double squaredWeight = cp.squaredWeight();
        System.out.println(toString("squaredWeight (decompose)",squaredWeight)); //(sollte 4 sein)="+String.valueOf(cp.squaredWeight()));
        // squaredWeight ist aber -4
        assert(equals(weight*weight, squaredWeight));
        
        //RoundAndTangentParameters decomposed = cp.decompose();
        
        // attitude
        Vector3d a1 = cp.attitude();
        System.out.println("attitude=("+String.valueOf(a1.x)+", "+String.valueOf(a1.y)+", "+String.valueOf(a1.z)+")");
        assert(equals(a1,new Vector3d(0d,0d,0d)));
        
        // locationIPNS
        Point3d p1 = cp.location(); // ok input = (0.02,0.02,1)
        // locCP1 lua=0.019999999999999993*e1 + 0.019999999999999993*e2 + 0.9999999999999996*e3
        // locationFromTangentAndRound=1.0000000000000002*eo + 0.020000000000000004*e1 + 0.020000000000000004*e2 + 1.0000000000000002*e3 + 0.5004000000000001*ei

        // locCP1=(0.020000000000000004,0.020000000000000004,1.0000000000000002) (korrekt)
        System.out.println(toString("location",p1));
        assert(equals(p,p1));
        

        // squared size
        double squaredSize = cp.squaredSize();
        // squaredSize (sollte 0 sein)=2.2512001600000007 (failed)
        System.out.println("squaredSize (sollte 0 sein)="+String.valueOf(squaredSize));
        //FIXME
        assertTrue(equals(squaredSize,0d));
        
        // Abstände zwischen Punkten
        Point3d p2 = new Point3d(2.02,0.02,1);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println("cp2="+cp2.toString());
        double distSquare = cp2.distSquare(cp);
        System.out.println("distSquare="+String.valueOf(distSquare));
        assertTrue(equals(p.distanceSquared(p2),distSquare));
    }

    public void testPointsOPNS(){
        System.out.println("--------------- points in OPNS representation -------");
        Point3d p = new Point3d(0.02,0.02,1);
        System.out.println("p=("+String.valueOf(p.x)+","+String.valueOf(p.y)+","+String.valueOf(p.z)+")");
        double weight = 2d;
        CGARoundPointIPNS cp = new CGARoundPointIPNS(p, weight);
        System.out.println("cp="+cp.toString());
        
        CGARoundPointOPNS cpOPNS = cp.undual();
        System.out.println(cpOPNS.toString("cpOPNS"));
        //RoundAndTangentParameters decomposed = cpOPNS.decompose();
        Point3d loc = cp.location();
        System.out.println(toString("p1",p));
        System.out.println(toString("p1 (decomposed)", loc));
        // alle decomposed coordinaten scheinen genau halb so gross zu sein
        // ist der dual-Operator korrekt? 
        //FIXME
        assert(equals(p,loc));
    }
    
    // scheint zu funktionieren
    // TODO Vergleich mit festen Ergebniszahlen einbauen etc.
    public void testSquareDistanceBetweenPoints(){
        System.out.println("--------------- square dist -------");
        Point3d p1 = new Point3d(0.02,0.02,1);
        Point3d p2 = new Point3d(2,0.02,1);
        System.out.println("distsquare="+String.valueOf(p2.distanceSquared(p1)));
        
        // die beiden Multivektoren brauchen scheinbar nicht normalisiert zu werden
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println("cp1="+cp1.toString());
        //System.out.println("cp1.unit="+cp1.unit().toString(CGA1Metric.baseVectorNames));
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println("cp2="+cp2.toString());
        
        double result = CGA1Metric.squareDistanceBetweenPoints(cp1, cp2);
        System.out.println("distsquare="+result);
    }
    
    public void testDorst2007DrillsLines(){
        System.out.println("-------------- Dorst 2007 drills (chapter 13.9.1): lines -------------------");
        
        Point3d p1 = new Point3d(1d,1d,0d);
        double weight1 = 2d;
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1, weight1);
        System.out.println("cp1="+cp1); // stimmt
        double weight1Test = cp1.squaredWeight();
        assertTrue(equals(weight1*weight1,weight1Test));
        Point3d p1Test = cp1.location();
        assertTrue(equals(p1Test,p1));
        
        Point3d p2 = new Point3d(1d,0d,1d);
        double weight2 = -1d;
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2, weight2);
        System.out.println("cp2="+cp2); // stimmt
        double weight2Test = cp2.squaredWeight();
        assertTrue(equals(weight2*weight2,weight2Test));
        Point3d p2Test = cp2.location();
        assertTrue(equals(p2Test, p2));
        
        double distSquare = cp2.distSquare(cp1);
        System.out.println("distSquare="+String.valueOf(distSquare)); // 2 stimmt
        assertTrue(equals(distSquare,2));
        
        CGALineOPNS lineOPNS = new CGALineOPNS(cp1, cp2);
        System.out.println(lineOPNS.toString("OPNS line"));
        
        CGAMultivector o = CGAMultivector.createOrigin(1d);
        CGAMultivector inf = CGAMultivector.createInf(1d);
        CGAMultivector e3 = CGAMultivector.createEz(1d);
        CGAMultivector e2 = CGAMultivector.createEy(1d);
        CGAMultivector e1 = CGAMultivector.createEx(1d);
        CGALineOPNS lineOPNSTest = new CGALineOPNS(o.op(e3).op(inf).sub(
                o.op(e2).op(inf)).add(
                e1.op(e3).op(inf)).sub(
                e1.op(e2).op(inf)).add(
                e2.op(e3).op(inf)).gp(-2d));
        System.out.println(lineOPNSTest.toString("OPNS line (Test)"));
        assertTrue(lineOPNS.equals(lineOPNSTest));
        // line=2.0*eo^e2^ei + 2.0*e1^e2^ei - 2.0*eo^e3^ei - 2.0*e1^e3^ei - 2.0*e2^e3^ei
        // sollte p1 ∧ p2 ∧ ∞ = −2(o + (e1 + e2 )) ∧ (e3 − e2 ) ∧ ∞. sein
        // stimmt
        double squaredWeight = lineOPNS.squaredWeight();
        System.out.println(toString("squaredWeight(CGALineOPNS)",squaredWeight));
        // squaredWeight=7.999999999999988
        // falsch, sollte doch 4 sein oder?
        
        // the weight2 is -2, and the conformal direction is −2(e3 − e2 ) ∧ ∞.
        Vector3d attitude = lineOPNS.attitude();
        System.out.println(toString("attitude", attitude));
        // attitude=0.0,1.9999999999999991,-1.9999999999999991
        assertTrue(equals(attitude,new Vector3d(0d,2d,-2d)));
        
        Point3d location = lineOPNS.location();
        System.out.println("location="+String.valueOf(location.x)+","+String.valueOf(location.y)+","+
                String.valueOf(location.z));
        // locCP1=-4.6259292692714784E-17,8.789265611615823E-17,-4.39463280580791E-16
        // vermutlich falsch
        
        
        CGALineIPNS line = lineOPNS.dual(); // grade 2
        System.out.println(line.toString("IPNS line"));
        
        double squaredWeight2 = line.squaredWeight();
        // squaredWeight=0.0, ist das richtig?
        // wenn das so ist, dann kann die attitude vermutlich nicht mehr korrekt bestimmt werden
        System.out.println(toString("squaredWeight(CGALineIPNS)",squaredWeight2));
        
        // attitudeIntern = (Infinity*e2 - Infinity*eo^e1^e2 - Infinity*e3 + Infinity*eo^e1^e3 + Infinity*eo^e2^e3 + NaN*e1^e2^ei + NaN*e1^e3^ei + NaN*e2^e3^ei)
        //The given multivector is not of grade 2: Infinity*e2 - Infinity*eo^e1^e2 - Infinity*e3 + Infinity*eo^e1^e3 + Infinity*eo^e2^e3 + NaN*e1^e2^ei + NaN*e1^e3^ei + NaN*e2^e3^ei
        //FIXME
        Vector3d direction = line.attitude();
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
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println("cp1="+cp1);
        
        Point3d p2 = new Point3d(1,0.02,1);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println("cp2="+cp2);
        
        Vector3d n = new Vector3d(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        System.out.println(toString("n",n));
        
        
        // line OPNS representation
        
        CGALineOPNS l = new CGALineOPNS(p1, p2);
        // dual line represented as tri-vector
        // l11= 0.98*no^e1^ni - 0.0196*e1^e2^ni - 0.98*e1^e3^ni
        System.out.println(l.toString("l OPNS"));
      
        // attitude
        Vector3d attitude = l.attitude();
        // sollte (0.98,0.0,0.0) 
        System.out.println(toString("attitude",attitude));
        assertTrue(equals(n, attitude));
        
        // Kleppe2016 directional component of a line
        CGAMultivector m = l.ip(o).ip(inf);
        System.out.println(m.toString("dir"));
        
        // locationIPNS
        Point3d location = l.location();
        // locationIPNS = (0.0,0.0,0.0) (failed)
        // Gerade geht offensichtlich nicht durch 0,0.0
        //FIXME
        System.out.println(toString("location",location));
        
        
        // line OPNs representation
        
        CGALineIPNS ldual = l.dual();
        // line represented as bivector
        // l*= 0.9799999999999993*e2^e3 + 0.9799999999999995*e2^ei - 0.019599999999999985*e3^ei
        System.out.println("l*= "+ldual);
       
        // attitude
        // attitude = (-0.9799999999999989,0.0,0.0)
        Vector3d attitudeDual = ldual.attitude();
        // sollte (0.98,0.0,0.0) (failed) falsches Vorzeichen
        System.out.println(toString("attitude",attitudeDual));
        assertTrue(equals(n, attitudeDual));
    }
    
    public void testLinePair(){
    
        System.out.println("-------------- linepair --------");
        
        /*Multivector no = Multivector.createBasisVector(0);
        Multivector e1 = Multivector.createBasisVector(1);
        Multivector e2 = Multivector.createBasisVector(2);
        Multivector e3 = Multivector.createBasisVector(3);
        Multivector ni = Multivector.createBasisVector(4);*/
        
        Point3d p1 = new Point3d(0.02,0.02,1);
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        
        Point3d p2 = new Point3d(1,0.02,1);
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        
        Vector3d n1 = new Vector3d(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        System.out.println("n1=("+String.valueOf(n1.x)+","+String.valueOf(n1.y)+","+String.valueOf(n1.z)+")");
       
        CGALineOPNS l1 = new CGALineOPNS(p1, p2);
        System.out.println("l1= "+l1.toString());
        // l1= 5.551115123125783E-17*eo^e2 - 1.734723475976807E-18*eo^e3 + 0.9799999999999993*e2^e3 + 0.9799999999999995*e2^ei - 0.019599999999999985*e3^ei
        System.out.println("l1 normiert= "+l1.normalize().toString());
        
        // l1.squaredNorm()=0.0
        System.out.println("l1.squaredNorm()="+String.valueOf(l1.squaredNorm()));
        
        // ipns representation
        //CGA1Multivector l11 = new CGA1Multivector(l11.dual(CGA1Metric.CGA_METRIC));
        
        Point3d p3 = new Point3d(0.02,0.02,2);
        Point3d p4 = new Point3d(1,1,2.2);
        Vector3d n2 = new Vector3d(p4.x-p3.x, p4.y-p3.y, p4.z-p3.z);
        System.out.println("n2=("+String.valueOf(n2.x)+","+String.valueOf(n2.y)+","+String.valueOf(n2.z)+")");
       
        System.out.println("alpha = "+String.valueOf(n2.angle(n1)*180d/Math.PI));
        
        Vector3d cross = new Vector3d();
        cross.cross(n1, n2);
        System.out.println("cross=("+String.valueOf(cross.x)+","+String.valueOf(cross.y)+","+String.valueOf(cross.z)+")");
       
        CGALineOPNS l2 = new CGALineOPNS(p3, p4);
        System.out.println("l2= "+l2.toString());
        //System.out.println("l2 normiert= "+l2.normalize().toString());
        //CGALinePair l2l1 = new CGALinePair(l1, l2); //l2.gp(l1).normalize();
        // bi- und trivector Anteile
        // l2l1= -2.87728 + 0.21520800000000018*no^e1 - 0.019208*no^e2 + 
        //                  2.87728*e1^e2 + 0.95648*no^e3 + 0.15766240000000017*e1^e3 + 
        //                  0.0383376*e2^e3 
        //                  - 0.9604*no^e1^e2^e3

        //System.out.println("l2l1= "+l2l1.unit().toString(CGA1Metric.baseVectorNames));
        
        //l2l1.decomposeLinePair();
    }
    
     public void testAttitudeCompositionAndDecomposition(){
        System.out.println("------------- test attitude composition and decomposition in OPNS representation ---------------------");
        // test attitude vector construction and decomposition
        Vector3d yDirVec = new Vector3d(0d,1d,0.5d);
        System.out.println(toString("yDir", yDirVec));
        CGAAttitudeVectorOPNS ydir = new CGAAttitudeVectorOPNS(yDirVec);
        System.out.println(ydir.toString("AttitudeVector ydir"));
        // y-richtung wieder rausholen - test
        Vector3d yDirVec2 = ydir.direction();
        toString("yDir (decomposed)",yDirVec2);
        assert(equals(yDirVec, yDirVec2));
    }
     
    // test tangent vector construction and decomposition
    public void testTangentCompositionAndDecomposition(){
        System.out.println("----------------- tangent composition and decomposition ------------------");
        Point3d p = new Point3d(0d,1d,0d);
        toString("P", p);
        Vector3d u =  new Vector3d(0d,1d,0d);
        toString("u", u);
        
        // TangentVector ytangent = (eo^e2 + eo^ei + 0.5*e2^ei)
        CGATangentVectorOPNS ytangent = new CGATangentVectorOPNS(p,u);
        CGAMultivector ytangent2 = CGAMultivector.createOrigin(1d).op(CGAMultivector.createEy(1d)).add(
        CGAMultivector.createOrigin(1d).op(CGAMultivector.createInf(1d)).add(
        CGAMultivector.createEy(0.5).op(CGAMultivector.createInf(1d))));
        System.out.println(ytangent2.toString("ytangentTest"));
        System.out.println(ytangent.toString("TangentVector ytangent"));
        assertTrue(ytangent.equals(ytangent2));
        
        // TangentVector* ytangentDual = (eo^e1^e3 - e1^e2^e3 + 0.45*e1^e3^ei)
        CGATangentVectorIPNS ytangentDual = ytangent.dual();
        System.out.println(ytangentDual.toString("TangentVector* ytangentDual"));
        
        // The given multivector m is not of grade 3! 
        // 5.55111512312578E-17*eo^e1^e2^e3 - 5.551115123125781E-17*eo^e1^e3^ei + 2.7755575615628904E-17*e1^e2^e3^ei
        //CGATangentVectorIPNS ytangentDual2 = new CGATangentVectorIPNS(p,u);
        //System.out.println("TangentVector* ytangentDual2 = "+ytangentDual2.toString());
        
        
        Vector3d yTangentVec = ytangent.attitude();
        toString("yTangent (decomposed) u",yTangentVec);
        assertTrue(equals(yTangentVec,u));
        
        Point3d yTangentPoint = ytangent.location();
        toString("yTangent (decomposed) P", yTangentPoint);
        assertTrue(equals(yTangentPoint,p));
    }
}