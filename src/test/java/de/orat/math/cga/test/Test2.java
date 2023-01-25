package de.orat.math.cga.test;

import de.orat.math.cga.api.CGAAttitudeScalarOPNS;
import de.orat.math.cga.api.CGAAttitudeVectorOPNS;
import de.orat.math.cga.api.CGAEuclideanVector;
import de.orat.math.cga.api.CGAFlatPointIPNS;
import de.orat.math.cga.api.CGAOrientedCircleIPNS;
import de.orat.math.cga.api.CGAOrientedCircleOPNS;
import de.orat.math.cga.api.CGALineOPNS;
import de.orat.math.cga.api.CGAPlaneOPNS;
import de.orat.math.cga.api.CGAOrientedPointPairOPNS;
import de.orat.math.cga.api.CGASphereOPNS;
import de.orat.math.cga.api.CGALineIPNS;
import de.orat.math.cga.api.CGALinePair;
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
import de.orat.math.cga.api.CGATranslator;
import de.orat.math.cga.api.iCGAPointPair.PointPair;
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
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

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
        // lineOPNS_ in OPNS representation
        CGALineOPNS lineOPNS_ = new CGALineOPNS(p1cga, p2cga);
        System.out.println(lineOPNS_.toString("lineOPNS"));
        CGALineOPNS lineTest = new CGALineOPNS(CGAMultivector.createOrigin(1d).add(CGAMultivector.createE3(p1)).
                gp(-2).op(CGAMultivector.createE3(new Point3d(0d,-1d,1d))).op(CGAMultivector.createInf(1d)));
        System.out.println(lineTest.toString("lineOPNS (test)"));
        assertTrue(lineOPNS_.equals(lineTest));
        
        // 4.
        // direction of the lineOPNS_
        CGAEuclideanVector attitude2 = lineOPNS_.attitudeIntern2();
        System.out.println(attitude2.toString("attitude2 (line OPNS)"));
        
        Vector3d attitude = lineOPNS_.attitudeIntern().direction(); // without normalizationd
        Vector3d attitudeTest = new Vector3d(p1); 
        attitudeTest.sub(p2); // // 0,1,-1
        attitudeTest.scale(Math.sqrt(lineOPNS_.squaredWeight()));
        System.out.println(toString("attitude Test (scaled with weight)",attitudeTest));
        // warum p1-p2 und nicht umgekehrt?
        // was ist das mit weight?
        //FIXME
        attitudeTest = new Vector3d(0d,2d,-2d);
        System.out.println(toString("n (line opns)", attitudeTest)); 
        assertTrue(equals(attitude, attitudeTest));
        
        CGAMultivector carrierFlat = lineOPNS_.carrierFlat();
        // anderes Vorzeichen als Dorst
        //FIXME
        // failed für OPNS line, warum?
        System.out.println(carrierFlat.toString("carrierFlat (line OPNS)"));
        
        // weightIntern2 of the lineOPNS_
        double squaredLineWeight = lineOPNS_.squaredWeight();
        // test
        lineOPNS_.squaredWeight(new Point3d(0d,0d,0d));
        Vector3d test = lineOPNS_.attitudeIntern().direction();
        
        //FIXME mit 8 ist das um einen Faktor 2 zu gross
        // squaredLineWeight = 7.999999999999988
        // squaredLineWeight (test) = 4.0
        System.out.println(toString("squaredLineWeight (line OPNS)",squaredLineWeight));
        //FIXME Wie bekomme ich das Vorzeichen?
        double lineWeightTest = -2;
        System.out.println(toString("squaredLineWeight (test)",lineWeightTest*lineWeightTest));
        //assertTrue(equals(squaredLineWeight, lineWeightTest*lineWeightTest));
        
        // 6.
        // plane through lineOPNS_ and origin in OPNS representation
        CGAPlaneOPNS planeOPNS = new CGAPlaneOPNS(lineOPNS_.op(o));
        System.out.println(planeOPNS.toString("planeOPNS"));
        CGAPlaneOPNS planeOPNSTest = new CGAPlaneOPNS(o.op(e1).op(e3).op(inf).sub(o.op(e1).op(e2).op(inf)).add(
                                o.op(e2).op(e3).op(inf)).gp(2d));
        System.out.println(planeOPNSTest.toString("planeOPNS (test)"));
        assertTrue(planeOPNS.equals(planeOPNSTest));
        
        // 7.
        // direction (Normalenvektor?)
        // attitudeIntern (CGAOrientedFiniteFlatOPNS, Dorst) = (-1.9999999999999991*e1^e2^ei + 1.9999999999999991*e1^e3^ei + 1.9999999999999991*e2^e3^ei)
        // attitudePlaneOPNS = (0.5773502691896258,0.5773502691896258,-0.5773502691896258)
        // attitudePlaneOPNS (test) = (2.0*e1^e2^ei + 2.0*e1^e3^ei + 2.0*e2^e3^ei)

        Vector3d attitudePlaneOPNS = planeOPNS.attitude();
        attitudePlaneOPNS.normalize();
        System.out.println(toString("attitudePlaneOPNS",attitudePlaneOPNS));
        CGAMultivector attitudePlaneOPNSTest = (e1.op(e3).op(inf).add(
                        e1.op(e2).op(inf)).add(e2.op(e3).op(inf))).gp(2d);
        System.out.println(attitudePlaneOPNSTest.toString("attitudePlaneOPNS (test)"));
        Vector3d dirTest = new Vector3d(2,2,-2);
        dirTest.normalize();
        assertTrue(equals(attitudePlaneOPNS, dirTest));
        
        CGAMultivector carrierFlatPlaneOPNS = planeOPNS.carrierFlat();
        // carrier flat (plane OPNS) = (0)
        // failed 
        System.out.println(carrierFlatPlaneOPNS.toString("carrier flat (plane OPNS)"));
    }
    
    
    public void testDorst2007DrillsPointPairs(){
        System.out.println("------------------ Dorst2007 drills (chpater 14.9.1): point pair --------------");
       
        // point pair composition
        Point3d p1 = new Point3d(1d,0d,0d);
        System.out.println(toString("p1",p1));
        double weight1 = 2d;
        Point3d p2 = new Point3d(0d,1d,0d);
        System.out.println(toString("p2",p2));
        double weight2 = -1d;
        
        // 1.
        CGAOrientedPointPairOPNS ppOPNS = new CGAOrientedPointPairOPNS(p1, weight1, p2, weight2);
        // ppOPNS = (2.0*eo^e1 - 2.0*eo^e2 - 2.0*e1^e2 - 1.0*e1^ei + 1.0*e2^ei) (korrekt)
        System.out.println(ppOPNS.toString("pp"));
        CGAMultivector ppTest = CGAMultivector.createOrigin(2d).op(CGAMultivector.createEx(1d)).sub(
            CGAMultivector.createOrigin(2d).op(CGAMultivector.createEy(1d))).sub(
            CGAMultivector.createEx(2d).op(CGAMultivector.createEy(1d))).sub(
            CGAMultivector.createEx(1d).op(CGAMultivector.createInf(1d))).add(
            CGAMultivector.createEy(1d).op(CGAMultivector.createInf(1d)));
        System.out.println(ppTest.toString("ppTest"));
        assertTrue(ppOPNS.equals(ppTest));
        
        // 2.
        // locationOPNS
        Point3d location = ppOPNS.location();
        System.out.println(toString("location (pp OPNS)",location));
        // (e1+e2)/2
        Point3d locationTest  = new Point3d(p1); locationTest.add(p2); locationTest.scale(0.5d);
        // new Point3d(0.5d,0.5d,0d);
        System.out.println(toString("locationTest (pp OPNS)",locationTest));
        assertTrue(equals(location,locationTest));
        
        // 4. 
        // beide Punkte wieder aus dem paar rausholen
        PointPair points = ppOPNS.decomposePoints();
        System.out.println(toString("p1",points.p1()));
        System.out.println(toString("p2", points.p2()));
        boolean p1test = equals(p1, points.p1());
        boolean p12test = equals(p1, points.p2());
        boolean p2test = equals(p2, points.p2());
        boolean p21test = equals(p2, points.p1());
        assertTrue((p1test & p2test) || (p12test & p21test));
        
        // attitude
        // attitude (round/tangent) = (1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei)
        // attitude(dualRound/dualTangent)=1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei
        // attitude (ppIPNS OPNS) = (0.0,0.0,0.0)
        Vector3d direction = ppOPNS.attitude();
        System.out.println(toString("attitude (pp OPNS)", direction));
        // attitude (round/tangent) = (1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei)
        // attitude (CGAOrientedPointPairOPNS)=1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei
        // attitude (pp OPNS) = (1.9999999999999996,-1.9999999999999996,0.0)
        // carrier flat (ppOPNS) = (-1.9999999999999987*e1 + 1.9999999999999987*e2)
        //FIXME
        // anderes Vorzeichen als Dorst
        CGAMultivector carrierFlat = ppOPNS.carrierFlat();
        System.out.println(carrierFlat.toString("carrier flat (ppOPNS)"));
        
        // weight
        // squaredWeight (ppIPNS OPNS)=7.999999999999993
        //FIXME ist das richtig so?
        double squaredWeight = ppOPNS.squaredWeight();
        System.out.println("squaredWeight (pp OPNS)="+String.valueOf(squaredWeight));
        // unklar was rauskommen muss
        //assertTrue(equals(squaredWeight,weight1*weightIntern2)); 
        
        // 2.
        // squaredSizeIntern1
        double radiusSquaredTest = 0.5d;
        System.out.println("radiusSquaredTest="+String.valueOf(radiusSquaredTest));
        
        double radiusSquared = ppOPNS.squaredSizeIntern3().decomposeScalar();
        System.out.println("radiusSquared3 (pp OPNS)="+String.valueOf(radiusSquared));
        assertTrue(equals(radiusSquared,radiusSquaredTest)); 
        
        //FIXME
        // falsches Ergebnis mit 1,75 statt 0.5
        // die input weights auf 1 zu setzen hat keinen Unterschied gebracht
        //System.out.println(ppOPNS.dual().toString("ppIPNS (IPNS)"));
        // FIXME dual() führt zum gleichen Ergebnis
        // double radiusSquared = ppOPNS.dual().squaredSizeIntern1();
        radiusSquared = ppOPNS.squaredSize();
        // squaredSize (Dorst2007) = (-1.7500000000000007)
        // radiusSquared (ppIPNS OPNS)=-1.7500000000000007
        System.out.println("radiusSquared (pp OPNS)="+String.valueOf(radiusSquared));
        assertTrue(equals(radiusSquared,radiusSquaredTest)); // failed because location of ppOPNS is not the origin
    }
    
    public void testPointPairIPNS(){
        System.out.println("----------- test pointpair IPNS---------");
        Point3d c = new Point3d(0d,1d,0d);
        System.out.println(toString("center",c));
        Vector3d n = new Vector3d(0d,0d,1d);
        double r = 2d;
        double weight = 3d;
        boolean sign = true;
        CGAOrientedPointPairIPNS ppIPNS = new CGAOrientedPointPairIPNS(c, n, r, weight, sign);
        System.out.println(ppIPNS.toString("ppIPNS"));
        testPointPairIPNS(ppIPNS);
        
        System.out.println("--------------------------");
        Point3d p1 = new Point3d(1d,0d,0d);
        System.out.println(toString("p1",p1));
        double weight1 = 2d;
        Point3d p2 = new Point3d(0d,1d,0d);
        System.out.println(toString("p2",p2));
        double weight2 = -1d;
        CGAOrientedPointPairOPNS ppOPNS = new CGAOrientedPointPairOPNS(p1, weight1, p2, weight2);
        testPointPairIPNS(ppOPNS.dual());
        System.out.println(ppOPNS.toString("ppOPNS"));
    }
    
    private void testPointPairIPNS(CGAOrientedPointPairIPNS ppIPNS){
        
        System.out.println("------- test ppp IPNS ---------");
        // location
        Point3d location = ppIPNS.location();
        // location (ppIPNS) = (0.0,-1.4999999999999996,0.0) failed
        System.out.println(toString("location (ppIPNS)", location));
        // The given multivector is not of grade 2 or a null vector: Infinity*e3
        location = ppIPNS.locationIntern2().location();
        System.out.println(toString("locationIntern2 (ppIPNS)", location));
        location = ppIPNS.locationIntern3().location();
        // locationIntern3 (ppIPNS) = (0.0,0.6666666666666669,0.0) failed
        System.out.println(toString("locationIntern3 (ppIPNS)", location));
        
        // squaredSize
        double squaredSize = ppIPNS.squaredSize();
        System.out.println(toString("squaredSize (ppIPNS)",squaredSize));
        squaredSize = ppIPNS.squaredSizeIntern5().decomposeScalar();
        System.out.println(toString("squaredSize2 (ppIPNS)",squaredSize));
        
        // weight
        double squaredWeight = ppIPNS.squaredWeight();
        System.out.println(toString("squaredWeight (ppIPNS)", squaredWeight));
        squaredWeight = ppIPNS.weightIntern2()*ppIPNS.weightIntern2();
        System.out.println(toString("squaredWeight2 (ppIPNS)", squaredWeight));
        
        // attitude
        Vector3d attitude = ppIPNS.attitude();
        System.out.println(toString("attitude (ppIPNS)", attitude));
        attitude = ppIPNS.attitudeIntern2().direction();
        System.out.println(toString("attitude2 (ppIPNS)", attitude));
        
        //attitude (round/tangent) = (2.999999999999998*e3^ei)
        //attitude=2.999999999999998*e3^ei
        //attitude (ppIPNS) = (NaN,NaN,NaN)
        //attitude2 (ppIPNS) = (0.0,0.0,1.0)
        //carrier flat (ppIPNS) = (2.999999999999998*e3)
        CGAMultivector carrierFlat = ppIPNS.carrierFlat();
        System.out.println(carrierFlat.toString("carrier flat (ppIPNS)"));
        
        // points
        PointPair points = ppIPNS.decomposePoints();
        System.out.println(toString("P1 (ppIPNS)", points.p1()));
        System.out.println(toString("P2 (ppIPNS)", points.p2()));
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
        CGAOrientedCircleOPNS circ = new CGAOrientedCircleOPNS(p1,p2,p3);
        // location=1.0*eo^e1^e2 - 1.0*eo^e1^e3 + 1.0*eo^e2^e3 + 1.0*e1^e2^e3 
        // + 0.5*e1^e2^ei - 0.5*e1^e3^ei + 0.5*e2^e3^ei (korrekt)
        System.out.println(circ.toString("circ"));
        CGAMultivector cTest = o.op(e1).op(e2).sub(o.op(e1).op(e3)).
                add(o.op(e2).op(e3)).add(e1.op(e2).op(e3)).
                add(e1.op(e2).op(inf).gp(0.5d).sub(e1.op(e3).op(inf).gp(0.5d)).
                add(e2.op(e3).op(inf).gp(0.5d)));
        System.out.println("cTest="+circ.toString());
        assert(circ.equals(cTest));
        
        double radiusSquared = circ.squaredSize();
        System.out.println(toString("radiusSquared",radiusSquared));
        
        // composition of a lineOPNS_ OPNS based on two points
        CGALineOPNS l = new CGALineOPNS(p1,p2);
        // l_OPNS=-1.0*eo^e1^ei + 1.0*eo^e2^ei + 1.0*e1^e2^ei (korrekt)
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

        // point
        // var p1 = up(1e1+.5e2)
        Vector3d p1euclid = new Vector3d(1d,0.5d,0d);
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(p1euclid);
        // ganja.js: e1+0.5e2+0.12e4+1.12e5 = e1+0.5e2+0.625ei+e0 (korrekt)
        // java.js: p1=1.0*eo + 1.0*e1 + 0.5*e2 + 0.625*ei
        System.out.println(toString("p1 (euclid)", p1euclid));
        System.out.println(p1.toString("p1"));
        CGAMultivector p1Test = CGAMultivector.createE3(new Vector3d(1d,0.5d,0d)).
                add(CGAMultivector.createInf(0.625)).add(CGAMultivector.createOrigin(1d));
        assert(p1.equals(p1Test));
        
        // point
        // p2 = up(1e2-.5e3);
        Vector3d p2euclid = new Vector3d(0d,1d,0.5d);
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(p2euclid);
        // java: p2=1.0*eo + 1.0*e2 + 0.5*e3 + 0.625*ei
        // ganja.js: e2-0.5e3+0.12e4+1.12e5
        System.out.println(toString("p2 (euclid)",p2euclid));
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
        double weightS1 = 1d;
        double radius1 = 0.3;
        CGASphereIPNS s1 = new CGASphereIPNS(p1, radius1, weightS1);
        // java: s1=1.0*eo + 1.0*e1 + 0.5*e2 + 0.58*ei (korrekt)
        CGAMultivector s1Test = o.add(e1).add(e2.gp(0.5)).add(inf.gp(0.58));
        assertTrue(s1.equals(s1Test));
        System.out.println(s1.toString("s1"));
        
        
        // test Spencer implementation
        
        double squaredSize5 = s1.squaredSizeIntern5().decomposeScalar();
        System.out.println("squaredSize2 (s1)="+String.valueOf(squaredSize5));
        assertTrue(equals(squaredSize5, radius1*radius1));
        double weight2 = s1.weight2();
        System.out.println("weight2 (s1)="+String.valueOf(weight2));
        assertTrue(equals(weight2, weightS1));
        Vector3d location2 = s1.locationIntern2().direction();
        System.out.println(toString("location2 (s1)", location2));
        assertTrue(equals(location2, p1euclid));
        
        // test Dorst implementation
        
        double squaredWeight1 = s1.squaredWeight();
        System.out.println("squaredWeight (S1 IPNS)"+String.valueOf(squaredWeight1));
        assertTrue(equals(weightS1*weightS1, squaredWeight1));
        Point3d location1 = s1.location();
        System.out.println(toString("location (S1 IPNS)", location1));
        assertTrue(equals(location1, p1euclid));
                
        // radiusSquared
        double radiusSquared1 = s1.squaredSize();
        // radiusSquared  = 2.586400000000001
        // failed da 2.58... es wird aber 0.09 erwartet
        // liegt vermutlich daran dass die Formel von normalisierter Kugel im Ursprung ausgeht
        //FIXME
        System.out.println(toString("radiusSquared (sphere1, Dorst)", radiusSquared1));
        assertTrue(equals(radiusSquared1, 0.3d*0.3d)); // failed, da location nicht im Ursprung ist
        
        
        
        CGASphereOPNS s = s1.undual();
        System.out.println("s="+s.toString());
        
        CGAMultivector inf_no = createInf(1d).op(createOrigin(1d));
        // Planes also have a direct dual representation. (but start from vector not point)
        // var p1 = ()=>!(d(p2,no)*ni + (p2^nino*nino).Normalized);
        
        CGAMultivector n = ((new CGARoundPointIPNS(p2)).op(inf_no).gp(inf_no)).normalize();
        // n1=-0.8944271909999159*e2 - 0.4472135954999579*e3
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
        // vermutlich brauche ich n1 als CGAAttitude und dann einen passenden Konstruktor
        //TODO
        //CGAPlane pa = CGAPlaneIPNS(n1, d);
        
        // p1= -1.11e1234-1.11e1235-0.44e1245-0.89e1345

        // You can use the regressive product to calculate intersections..
        //var location = ()=>s&p1;
        CGAOrientedCircleOPNS c = new CGAOrientedCircleOPNS(s.vee(p));
        // ganja.js: location=-1.11e123 - 0.48e124 - 0.03e125 - 0.40e134 + 0.48e135 + 
        // 0.22e145 - 1.11e234 -1.11e235-0.44e245-0.89e345
        // java: location=0.447*eo^e1^e2 - 0.89*eo^e1^e3 - 1.118*e1^e2^e3 
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
        
        CGAPlaneIPNS plane = new CGAPlaneIPNS(new Vector3d(0d,0d,1d), 0d,0d);
        System.out.println("plane="+plane.toString());
        //C  = !(up(1.4e1)-.125*ni)&!(1e3),    // right circleIPNS
        CGAOrientedCircleOPNS C = new CGAOrientedCircleOPNS(S2.vee(plane.dual()));
        // ganja.js: 1.35e124+0.35e125+1.39e245 = 1.3e02i + 0.85e12i -e012 (korrekt)
        // java: C=-eo^e1^e2 - 1.34*eo^e2^ei + 0.85*e1^e2^ei
        System.out.println("C="+C.toString());
        
        //L  = up(.9e2)^up(.9e2-1e1)^ni,       // top lineOPNS_
        // ganja.js: 0.89e124+0.89e125-e145 = -e01i+0.9e12i
        // java: l_OPNS=-1.0*eo^e1^ei + 0.9*e1^e2^ei (korrekt)
        CGALineOPNS L = new CGALineOPNS(new Point3d(0d,0.9d,0d), new Point3d(-1d,0.9d,0d));
        System.out.println("L="+L.toString());
        
        //P  = !(1e2-.9*ni),                   // bottom dual planeIPNS
        // ganja.js: 0.89e1234+0.89e1235-e1345 = e013i + 0.9e123i (korrekt)
        // P=5.551115123125783E-17*eo^e1^e2^e3 + eo^e1^e3^ei + 0.9*e1^e2^e3^ei
        CGAPlaneOPNS P = (new CGAPlaneIPNS(new Vector3d(0d,1d,0d), -0.9d, 0d)).undual();
        System.out.println("P="+P.toString());
        
        //P2 = !(1e1+1.7*ni);                  // right dual planeIPNS
        // ganja.js: -1.70e1234-1.70e1235+e2345 = -1.7e123i - e023i (korrekt)
        // P2=-1.1102230246251565E-16*eo^e1^e2^e3 - eo^e2^e3^ei - 1.7*e1^e2^e3^ei
        CGAPlaneOPNS P2 = (new CGAPlaneIPNS(new Vector3d(1d,0d,0d), 1.7d, 0d)).undual();
        System.out.println("P2="+P2.toString());
        
        // The intersections of the big sphereIPNS with the other 4 objects.
        //var C1 = ()=>S&P sphereIPNS meets planeIPNS
        CGAOrientedCircleOPNS C1 = new CGAOrientedCircleOPNS(S.vee(P));
        // ganja.js: 0.89e123+e135 = 0.89e123 + 0.5e13i + e013 (korrekt)
        // java: C1=eo^e1^e3 + 0.9*e1^e2^e3 + 0.49*e1^e3^ei
        System.out.println("C1="+C1.toString());
        
        // C2 = ()=>S&L 
        CGAOrientedPointPairOPNS pp = new CGAOrientedPointPairOPNS(S.vee(L));
        // java: s&l_OPNS=eo^e1 - 0.89*e1^e2 - 0.5*e1^ei
        // ganja.js: -0.89e12-e15 = 0.89e12-0.5e1i + e01 (korrekt)
        System.out.println("s&l="+pp.toString());
        CGAOrientedPointPairOPNS ppTest = new CGAOrientedPointPairOPNS(o.op(e1).sub(e1.op(e2).gp(0.89)).
                sub(e1.op(inf).gp(0.5d)));
        System.out.println(ppTest.toString("s&l test"));
        //s&l test = (1.0*eo^e1 - 0.89*e1^e2 - 0.5*e1^ei)
        // stimmt in der zweiten Komponente nicht sehr exakt überein, 3. Nachkommastelle ...
        //FIXMe
        //assertTrue(ppIPNS.equals(ppTest));
        
        // C3 = ()=>S&S2 sphereIPNS meet sphereIPNS
        CGAOrientedCircleOPNS C3 = new CGAOrientedCircleOPNS(S.vee(S2));
        // ganja.js: -1.35e123+1.39e235 = -1.35e123+0.7e23i+1.39e023
        // java: s&s=1.4*eo^e2^e3 - 1.35*e1^e2^e3 - 0.7*e2^e3^ei (korrekt)
        System.out.println("s&s="+C3.toString());
        
        // C4 = ()=>S&C 
        CGAOrientedPointPairOPNS C4 = new CGAOrientedPointPairOPNS(S.vee(C));
        // java s&location=1.39*eo^e2 - 1.35*e1^e2 - 0.69*e2^ei
        // ganja -1.35e12+1.39e25
        // TODO
        System.out.println("s&c="+C4.toString());
        
        // C5 = ()=>C&P2;  circleIPNS meet planeIPNS
        CGAOrientedPointPairOPNS C5 = new CGAOrientedPointPairOPNS(C.vee(P2));
        // ganja.js: 1.7e12-1.02e24-2.02e25 = 1.7e12 
        // java: location&p1=eo^e2 + 1.7*e1^e2 + 3.23*e2^ei
        //FIXME stimmt nur in einer Komponente überein
        //TODO vermutlich sind bereits der Ausgangs-circleIPNS oder planeIPNS flasch --> überprüfen
        System.out.println("c&p="+C5.toString());
        
        // For lineOPNS_ meet planeIPNS its a bit more involved.
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
                new CGAPlaneIPNS(new Vector3d(0d,0d,1d),0d, 0d)).dual()));
        // ganja.js: 1.35e124+0.35e125+1.39e245
        // C=-eo^e1^e2 + 1.34*eo^e2^ei + 0.855*e1^e2^ei
        System.out.println("C="+C.toString());
        //TODO
        
        // L  = up(.9e2)^up(.9e2-1e1)^ni,         // top lineOPNS_
        CGALineOPNS L = new CGALineOPNS(new Point3d(0d,0.9d,0d), new Point3d(-1d,0.9d,0d));
        // ganja.js: 0.89e124+0.89e125-e145 (grade3)
        // java L=-1.0*eo^e1^ei + 0.9*e1^e2^ei (korrekt)
        System.out.println("L="+L.toString());
        
       
        // planeIPNS  = (v,h=0)=>!(v-h*ni);
        // P  = planeIPNS(1e2,.9);                    // bottom planeIPNS
        CGAPlaneOPNS P = (new CGAPlaneIPNS(new Vector3d(0d,1d,0d),0.9d, 0d)).undual();
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
        
        // ()=>project_point_on_flat(~p1,L),  "p1 on L",   // point on lineOPNS_
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
        //  0x00008888, ()=>project_point_on_flat(~p1,L),  "p1 on L",   // point on lineOPNS_
        //  0xc0FF0000, ()=>plane_through_point_tangent_to_x(p1,S),    // planeIPNS through p1 tangent to S2
        //  0xc000FF00, ()=>plane_through_point_tangent_to_x(p1,P),    // planeIPNS through p1 tangent to P
        //  0,L,0,C,                                                  // lineOPNS_ and circleIPNS
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
        double weight = 1d;
        System.out.println("HNF: n=("+String.valueOf(n.x)+","+String.valueOf(n.y)+", "+String.valueOf(n.z)+"), d="+String.valueOf(d));
        
        CGAPlaneIPNS planeIPNS = new CGAPlaneIPNS(n, d, weight);
        CGAPlaneIPNS planeIPNSTest = new CGAPlaneIPNS(CGAMultivector.createEz(1d).add(CGAMultivector.createInf(2d)));
        // planeIPNS=1.0*e3 + 2.0*ei
        System.out.println(planeIPNS.toString("planeIPNS"));
        assertTrue(planeIPNS.equals(planeIPNSTest));
        
        //TODO
        // Wie ist die Orientierung dieser Ebene festgelegt?
        
        // attitude
        
        // attitude (planeIPNS, Dorst) = (0.0,0.0,-0.9999999999999996)
        Vector3d attitude = planeIPNS.attitude();
        System.out.println(toString("attitude (planeIPNS, Dorst)",attitude));
        // attitude_cga=-0.9999999999999996*e1^e2^ei
        // attitude (planeIPNS) = (0.0,0.0,-0.9999999999999996)
        // FIXME
        // Das Vorzeichen stimmt nicht, möglicherweise ist das aber auch korrekt so
        // und das Vorzeichen wird nur durch die Definition des Pseudoskalars im 
        // in CGA festgelegt
        
        // nach Spencer
        // attitude (planeIPNS, Spencer) = (0.0,0.0,1.0)
        attitude = planeIPNS.attitudeIntern2().direction();
         System.out.println(toString("attitude (planeIPNS, Spencer)",attitude));
        // normal vector/attitude nach Kleppe2016
        CGAMultivector normal = planeIPNS.op(CGAMultivector.createInf(1d)).ip(CGAMultivector.createOrigin(1d)).negate();
        // normal (planeIPNS, Kleppe 1) = (0)
        System.out.println(normal.toString("normal (planeIPNS, Kleppe 1)"));
        
        normal = planeIPNS.undual().ip(CGAMultivector.createOrigin(1d)).ip(CGAMultivector.createInf(1d))/*.normalize()*/.negate();
        // normal (planeIPNS, Kleppe 2) = (0)
        System.out.println(normal.toString("normal (planeIPNS, Kleppe 2)"));
        
        CGAMultivector carrierFlat = planeIPNS.carrierFlat();
        System.out.println(carrierFlat.toString("carrierFlat (planeIPNS)"));
        //carrierFlat (planeIPNS) = (0.9999999999999991*e1^e2)
        //attitudeIntern (CGAPlaneIPNS) = (0.9999999999999996*e1^e2^ei)
        
        // squaredWeight
        double squaredWeight = planeIPNS.squaredWeight();
        // 1 da nichts bei der composition angegeben wurde
        System.out.println(toString("squaredWeight (planeIPNS, Dorst)", squaredWeight));
        assertTrue(equals(squaredWeight,1d));
        
        
        // location
        
        Point3d location = planeIPNS.locationIntern2().location(); 
        System.out.println(toString("location (planeIPNS, Spencer)",location));
        assertTrue(equals(location, new Point3d(0,0,2))); 
        
        Point3d probe = new Point3d(5,5,1); 
        // von Hand bestimmt (Projektion von probe auf die Ebene)
        Vector3d locationTest = new Vector3d(5,5,2);
       
        // location planeIPNS, Dorst
        location = planeIPNS.location(probe);
        // location normalized dual sphere (CGAOrientedFiniteFlatIPNS) = (5.000000000000002*eo + 25.000000000000007*e1 + 25.000000000000007*e2 - 46.00000000000002*e3 + 23.00000000000001*ei)
        // location E3 (CGAOrientedFiniteFlatIPNS) = (24.99999999999999*e1 + 24.99999999999999*e2 - 46.0*e3)
        // location (plane IPNS) = (24.99999999999999,24.99999999999999,-46.0)
        System.out.println(toString("location (planeIPNS, Dorst)",location));
        assertTrue(equals(location, locationTest)); // failed
        
        
        
        CGAPlaneIPNS plane1 = new CGAPlaneIPNS(new Point3d(0d,0d,2d));
        // plane1=-1.0*e3 + 2.0000000000000004*ei
        //FIXME Vorzeichenfehler bei e3 egal welche normalize()-Methode ich verwende
        System.out.println(plane1.toString("plane1"));
        
    }
    
    public void testCircleIPNS(){
        System.out.println("----------------- circle IPNS (origin) -----");
        
        double radius = 1.5d;
        System.out.println("radius="+String.valueOf(radius));
        
        // Kugel 1
        Point3d p1 = new Point3d(1d,0d,0d);
        System.out.println(toString("p1",p1));
        CGASphereIPNS sphere1 = new CGASphereIPNS(p1, radius);
        System.out.println("sphere1="+sphere1.toString());
        
        // Kugel 2
        Point3d p2 = new Point3d(-1d,0d,0d);
        System.out.println(toString("p2",p2));
        CGASphereIPNS sphere2 = new CGASphereIPNS(p2, radius);
        System.out.println("sphere2="+sphere2.toString());
        
        // Circle from two spheres
        CGAOrientedCircleIPNS circleIPNS = new CGAOrientedCircleIPNS(sphere1, sphere2);
        Point3d location = new Point3d(0d,0d,0d);
        Vector3d attitudeTest = new Vector3d(-1,0,0);
        // circle=-2.0*eo^e1 - 1.25*e1^ei (grade 2 ok)
        System.out.println("circleIPNS="+circleIPNS.toString());
        
        testCircleIPNS(circleIPNS, location, 1.25, 4d, attitudeTest);
       
        System.out.println("----------------- circle from OPNS via dual (origin) -----");
        
        // aus opns mit dual ipns im Ursprung
        //Point3d point1 = new Point3d(1,0,0);
        //Point3d point2 = new Point3d(0,1,0);
        //Point3d point3 = new Point3d(-1,0,0);
        //Point3d circ = new Point3d(0,0,0);
        
        // um 1 in x verschoben
        Point3d point1 = new Point3d(1+1,0,0);
        Point3d point2 = new Point3d(0+1,1,0);
        Point3d point3 = new Point3d(-1+1,0,0);
        Point3d c = new Point3d(1,0,0);
        attitudeTest = new Vector3d(0,0,-1);
        
        CGAOrientedCircleOPNS circleOPNS = new CGAOrientedCircleOPNS(point1, point2, point3);
        System.out.println(circleOPNS.toString("circleOPNS2"));
        
        double radiusSquared = 1d;
        double squaredWeight = 4d;
        
        CGAOrientedCircleIPNS circleIPNS2 = circleOPNS.dual();
        System.out.println(circleIPNS2.toString("circleIPNS2"));
        
        testCircleIPNS(circleIPNS2, c, radiusSquared, squaredWeight, attitudeTest);
    }
    private void testCircleIPNS(CGAOrientedCircleIPNS circleIPNS, Point3d c, 
                                double radiusSquared, double squaredWeight, Vector3d attitudeTest){
        System.out.println("---------------------------------------------");
        // squared Size
        
        // hier schlägt intern undual() fehl, da norm(circleIPNS) == 0 ist
        // Wie ist das möglich?
        //FIXME
        double squaredSize = circleIPNS.squaredSize(); // failed!!!!
        // squaredSizeIntern1/radiusSquared = (-1.3906250000000013)
        System.out.println(toString("squaredSize (circleIPNS)",squaredSize));
        
        double squaredSize2 = circleIPNS.squaredSizeIntern5().decomposeScalar();
        // squaredSizeIntern5 (circleIPNS) = 1.2500000000000007 //ok
        System.out.println(toString("squaredSize2 (circleIPNS)",squaredSize2));
        assertTrue(equals(squaredSize2,radiusSquared));
        
        double squaredSize3 = circleIPNS.squaredSizeIntern3().decomposeScalar();
        // squaredSizeIntern3 (circleIPNS) = 1.2500000000000004
        System.out.println(toString("squaredSize3 (circleIPNS)", squaredSize3));
        assertTrue(equals(squaredSize3,radiusSquared));
        
        // locationIPNS
        // Dorst
        Point3d locationIPNS = circleIPNS.location();
        System.out.println(toString("location (Dorst, circleIPNS)",locationIPNS));
        assertTrue(equals(locationIPNS,c));
        
        // Spencer
        Point3d locationIPNS2 = circleIPNS.locationIntern2().location();
        System.out.println(toString("location (Spencer, circleIPNS)",locationIPNS));
        assertTrue(equals(locationIPNS2,c));
        
        //FIXME failed für circle der nicht durch den Usprung geht
        CGARoundPointIPNS m = circleIPNS.locationIntern3();
        System.out.println(m.toString("locationIntern3 (Hildenbrand1998, circleIPNS)"));
        Point3d locationIPNS3 = m.location();
        //assertTrue(equals(circ,locationIPNS3));
        
        
        // squaredWeight 
        
        // Dorst
        double squaredWeight1 = circleIPNS.squaredWeight();
        System.out.println(toString("squaredWeight (circleIPNS, Dorst)",squaredWeight1));
        assertTrue(equals(squaredWeight1,squaredWeight));
        
        // Spencer
        double weight2 = circleIPNS.weight2();
        System.out.println(toString("squaredWeight2 (circleIPNS, Spencer)",weight2));
        assertTrue(equals(weight2*weight2,squaredWeight));
        
        
        
        // attitude
        
        // Dorst
        // failed bei ipns im Ursprung liefert 2,0,0 statt (-1,0,0)
        Vector3d attitudeIPNS = circleIPNS.attitude();
        System.out.println(toString("attitude (circleIPNS, Dorst)", attitudeIPNS));
        //assertTrue(equals(attitudeIPNS,attitudeTest));
        
        // Spencer
        attitudeIPNS = circleIPNS.attitudeIntern2().direction();
        System.out.println(toString("attitude (circleIPNS, Spencer)", attitudeIPNS));
        //assertTrue(equals(attitudeIPNS,attitudeTest));
        
        
        // squaredSizeIntern1
        // squaredSizeIntern1 (circleIPNS) = 1.3906250000000013
        //FIXME
        //assert(equals(squaredSize,radiusSquared)); // 1.25 wäre  korrekt
    }
    
    public void testSpheresOPNS(){
        System.out.println("----------------- spheres OPNS --------------------");
        
        // Def. Kugel durch 4 Punkte die auf der Kugeloberflächen liegen sollen
        Vector3d v = new Vector3d(1d,0d,0d);
        double s = 2;
        Point3d p1 = new Point3d(0d,-1d*s,0d);
        p1.add(v);
        Point3d p2 = new Point3d(1d*s,0d,0d);
        p2.add(v);
        Point3d p3 = new Point3d(0d,1d*s,0d);
        p3.add(v);
        Point3d p4 = new Point3d(0d,0d,1d*s);
        p4.add(v);
        // damit sollte das Zentrum der Kugel im Ursprung+v liegen
        CGASphereOPNS sphereOPNS = new CGASphereOPNS(p1,p2,p3,p4);
        System.out.println(sphereOPNS.toString("sphereOPNS"));
        
        // squaredWeight
        double squaredWeight = sphereOPNS.squaredWeight();
        // squaredWeight(sphereOPNS) = 3.9999999999999964
        // Unklar, ob das so stimmt!!!
        // vielleicht bringt jeder der 4 normierten Punkte weight=1 mit und diese
        // addieren sich zu 4?
        //TODO
        
        // attitude
        // extract ist noch nicht richtig!!!!
        //TODO
        Vector3d attitude = sphereOPNS.attitude();
        // attitude (dualRound/dualTangent)=15.999999999999996*e1^e2^e3^ei
        System.out.println(toString("attitude (sphereOPNS, Dorst)",attitude));
        
        // location
        // Kugel im Ursprung, test damit nicht vollständig
        Point3d location = sphereOPNS.location();
        System.out.println(toString("location (sphereOPNS, Dorst)",location));
        assertTrue(equals(new Point3d(v),location));
        
        
        // squared size 
        
        // Hitzer
        double radiusSquared = sphereOPNS.squaredSizeIntern3().decomposeScalar();
        System.out.println(toString("radiusSquared (sphereOPNS, Hitzer2005)",radiusSquared));        
        assertTrue(equals(radiusSquared,s*s));
        
        // Spencer via dual
        double radiusSquared5 = sphereOPNS.dual().squaredSizeIntern5().decomposeScalar();
        System.out.println(toString("radiusSquared (sphereOPNS via dual, Spencer)",radiusSquared5));        
        assertTrue(equals(radiusSquared5,s*s));
        
        // Dorst/Hitzer2
        radiusSquared = sphereOPNS.squaredSize();
        System.out.println(toString("radiusSquared (sphereOPNS, Dorst)",radiusSquared));
        assertTrue(equals(radiusSquared,s*s)); 
    }
    
    public void testSpheresIPNS(){
        
        System.out.println("----------------- sphere IPNS-----");
        Point3d p1 = new Point3d(0d,-1d,0d); //new Point3d(0d,-1d,0d);
        System.out.println(toString("p1", p1));
        
        double radius = 2d;
        System.out.println("input radius="+String.valueOf(radius));
        double weight = 3d;
        System.out.println("input weight="+String.valueOf(weight));
         
        CGASphereIPNS sphereIPNS = new CGASphereIPNS(p1, radius, weight);
        System.out.println(sphereIPNS.toString("sphereIPNS"));
        
        
        // squaredWeight
        double squaredweight = sphereIPNS.squaredWeight(); // ich bekomme jetzt aber -1 heraus
        System.out.println(toString("squaredWeight (sphereIPNS)",squaredweight));
        assert(equals(squaredweight,weight*weight));
        
        double weight2 = sphereIPNS.weight2(); 
        System.out.println(toString("weight2 (sphereIPNS)", weight2));
        assert(equals(weight2*weight2,weight*weight));
        
        // squaredSizeIntern1
        double squaredRadius5 = sphereIPNS.squaredSizeIntern5().decomposeScalar();
        System.out.println(toString("squaredRadius2 (sphereIPNS)", squaredRadius5)); // 4.0 stimmt
        assertTrue(equals(radius*radius,squaredRadius5));
        
        // squaredSizeIntern1/squaredRadius
        // squaredSizeIntern1/radiusSquared = (4.2500000000000036) (failed)
        // wenn Kugel im Ursprung: radius2squared = 5.000000000000004 (failed)
        // auch wenn zusätzlich weight auf 1 gesetzt wurde, ändert sich nichts
        double squaredRadius = sphereIPNS.squaredSize();
        System.out.println(toString("squaredRadius (sphereIPNS)", squaredRadius)); // failed
        //assertTrue(equals(radiusSquared*radiusSquared,squaredRadius));
        
        
        // locationIPNS
        Point3d loc1 = sphereIPNS.location();
        System.out.println(toString("location (sphereIPNS)", loc1));
        assertTrue(equals(loc1, p1));
        
        loc1 = sphereIPNS.locationIntern2().location();
        System.out.println(toString("location2 (sphereIPNS)", loc1));
        assertTrue(equals(loc1, p1));
        
        loc1 = sphereIPNS.locationIntern3().location();
        System.out.println(toString("location3 (sphereIPNS)", loc1));
        assertTrue(equals(loc1, p1));
        
        // attitude
        // e.g. sphereIPNS mit weight = 3 durch (0,-1,0)
        // attitude (round/tangent) = (-2.999999999999997*e1^e2^e3^ei)
        // attitude=-2.999999999999997*e1^e2^e3^ei
        // attitude (sphere IPNS) = (0.0,0.0,0.0)
        //FIXME scheint falsch zu sein
        Vector3d attitude = sphereIPNS.attitude();
        System.out.println(toString("attitude (sphere IPNS)", attitude));
        
        // attitude2 (sphere IPNS) = (0.0,-0.9999999999999998,0.0)
        //FIXME vielleicht richtig, was bedeutet attitude für eine Kugel?
        Vector3d attitude2 = sphereIPNS.locationIntern2().direction();
        System.out.println(toString("attitude2 (sphere IPNS)", attitude2));
        
        
        // location() nach Hildenbrand1998
        CGARoundPointIPNS m = sphereIPNS.locationIntern3();
        System.out.println(m.toString("locationIntern3 (Hildenbrand1998)"));
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
        //RoundAndTangentParameters decomposed1 = cp1.decomposeMotor();
        Point3d locCP1 = cp1.location();
        System.out.println("loc(cp1) = ("+String.valueOf(locCP1.x)+", "+String.valueOf(locCP1.y)+", "+
                String.valueOf(locCP1.z)+")");
        assertTrue(equals(p1,locCP1));
        
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2, 2d);
        System.out.println("cp2="+cp2.toString());
        //RoundAndTangentParameters decomposed2 = cp2.decomposeMotor();
        Point3d locCP2 = cp2.location();
        System.out.println("loc(cp2) = ("+String.valueOf(locCP2.x)+", "+String.valueOf(locCP2.y)+", "+
                String.valueOf(locCP2.z)+")");
        assertTrue(equals(p2,locCP2));
        
        // point-pairs IPNS representation (failed)
        CGAOrientedPointPairIPNS pp1 = new CGAOrientedPointPairIPNS(cp1,cp2);
        //RoundAndTangentParameters decomposed3 = pp1.decomposeMotor();
        // loc(pp1) = (Infinity, Infinity, -Infinity) (ERROR)
        //FIXME
        Point3d location3 = pp1.location();
        System.out.println("loc(pp1) = ("+String.valueOf(location3.x)+", "+String.valueOf(location3.y)+", "+
                String.valueOf(location3.z)+")");
        
        
        // spheres
        CGASphereIPNS s1 = new CGASphereIPNS(p1, 2d);
        System.out.println(s1.toString("s1"));
        //RoundAndTangentParameters decomposed4 = s1.decomposeMotor();
        Point3d location4 = s1.location();
        System.out.println(toString("s1loc",location4));
        assertTrue(equals(p1,location4)); 
        
        CGASphereIPNS s2 = new CGASphereIPNS(p2, 3d);
        System.out.println("s2="+s2.toString());
        //RoundAndTangentParameters decomposed5 = s2.decomposeMotor();
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
        // ppOPNS=1.0*eo^e3 + 0.02*e1^e3 + 0.02*e2^e3 + 1.5*eo^ei + 0.030000000000000002*e1^ei + 0.030000000000000002*e2^ei + 0.9996*e3^ei
        System.out.println("pp2="+pp2.toString());
        // FIXME e1 und e2 die doppelt so grosse Werte wie erwartet
        // locationFromTangentAndRound=3.250000000000001*eo + 0.06500000000000002*e1 + 0.06500000000000002*e2 + 1.4994000000000005*e3 + 5.088522196198628E-19*eo^e1^e3 + 5.088522196198628E-19*eo^e2^e3 - 0.9996*ei + 7.632783294297935E-19*eo^e1^ei + 7.632783294297935E-19*eo^e2^ei - 3.7001512964707234E-16*eo^e3^ei - 6.9375061251264494E-18*e1^e3^ei - 6.9375061251264494E-18*e2^e3^ei
        //RoundAndTangentParameters decomposed8 = pp2.decomposeMotor();
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
        System.out.println(cp.toString("cp"));
        
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
        assertTrue(equals(testip.decomposeScalar(),0));
        
        // squaredWeight
        System.out.println(toString("squaredWeight (cp test)",weight*weight));
        double squaredWeight = cp.squaredWeight();
        System.out.println(toString("squaredWeight (cp, Dorst)",squaredWeight)); //(sollte 4 sein)="+String.valueOf(cp.squaredWeight()));
        assert(equals(weight*weight, squaredWeight));
        
        // attitude
        Vector3d a1 = cp.attitude();
        System.out.println("attitude=("+String.valueOf(a1.x)+", "+String.valueOf(a1.y)+", "+String.valueOf(a1.z)+")");
        assert(equals(a1,new Vector3d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)));
        
        // locationIPNS
        Point3d p1 = cp.location(); 
        System.out.println(toString("location (p, Dorst)",p1));
        assertTrue(equals(p,p1));
        

        // squared size
        double squaredSize = cp.squaredSizeIntern3().decomposeScalar();
        System.out.println(toString("squaredSize (cp, Hitzer2005)",squaredSize));
        assertTrue(equals(squaredSize,0d));
        
        squaredSize = cp.squaredSize();
        // squaredSizeIntern1 (sollte 0 sein)=2.2512001600000007 (failed)
        System.out.println(toString("squaredSize (CGARoundPointIPNS, sollte 0 sein, Dorst)",squaredSize));
        //FIXME
        assertTrue(equals(squaredSize,0d)); // failed das location != origin und weight != 0
        
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
        //RoundAndTangentParameters decomposed = cpOPNS.decomposeMotor();
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
        // lineOPNS_=2.0*eo^e2^ei + 2.0*e1^e2^ei - 2.0*eo^e3^ei - 2.0*e1^e3^ei - 2.0*e2^e3^ei
        // sollte p1 ∧ p2 ∧ ∞ = −2(o + (e1 + e2 )) ∧ (e3 − e2 ) ∧ ∞. sein
        // stimmt
        double squaredWeight = lineOPNS.squaredWeight();
        System.out.println(toString("squaredWeight(CGALineOPNS)",squaredWeight));
        // squaredWeight=7.999999999999988
        // falsch, sollte doch 4 sein oder?
        
        // the weightIntern2 is -2, and the conformal direction is −2(e3 − e2 ) ∧ ∞.
        Vector3d attitude = lineOPNS.attitudeIntern().direction();
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
        
        Point3d p1 = new Point3d(1.0,1.0,0);
        double weight1 = 2d;
        System.out.println("p1=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1, weight1);
        System.out.println("cp1="+cp1);
        
        Point3d p2 = new Point3d(1,0.0,1);
        double weight2 = -1d;
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2, weight2);
        System.out.println("cp2="+cp2);
        
        Vector3d n = new Vector3d(p1.x-p2.x, p1.y-p2.y, p1.z-p2.z);
        n.normalize();
        System.out.println(toString("n",n));
        
        
        // lineOPNS_ OPNS representation
        
        CGALineOPNS l_OPNS = new CGALineOPNS(p1, weight1, p2, weight2);
        // dual lineOPNS_ represented as tri-vector
        System.out.println(l_OPNS.toString("l OPNS"));
      
        CGALineOPNS l_OPNSTest = new CGALineOPNS(o.add(e1.add(e2)).gp(-2d).op(e3.sub(e2)).op(inf));
        System.out.println(l_OPNSTest.toString("l_OPNSTest"));
        assertTrue(l_OPNSTest.equals(l_OPNS));
        
        // attitude
        Vector3d attitude = l_OPNS.attitude();
        // l_OPNSTest = (2.0*eo^e2^ei + 2.0*e1^e2^ei - 2.0*eo^e3^ei - 2.0*e1^e3^ei - 2.0*e2^e3^ei)
        // attitudeIntern (CGAOrientedFiniteFlatOPNS, Dorst) = (1.9999999999999991*e2^ei - 1.9999999999999991*e3^ei)
        // attitudeIntern ist korrekt, aber output Dorst hat falsches Vorzeichen
        System.out.println(toString("attitude (l_OPNS, Dorst2007)",attitude));
        assertTrue(equals(n, attitude));
        
        // Kleppe2016 directional component of a lineOPNS_
        CGAMultivector m = l_OPNS.ip(o).ip(inf);
        System.out.println(m.toString("attitude (Kleppe2016) V1")); // failed liefert 0
        
        // Variante 2
        m = l_OPNS.dual().op(inf).ip(o);
        System.out.println(m.toString("attitude (Kleppe2016) V2")); // failed liefert 0
        
        // locationOPNS
        Point3d location = l_OPNS.location();
        // locationIPNS = (0.0,0.0,0.0) (failed)
        // Gerade geht offensichtlich nicht durch 0,0.0
        //FIXME
        System.out.println(toString("location (line OPNS, Dorst)",location));
        
        // lineOPNS_ IPNS representation
        
        CGALineIPNS l_IPNS = l_OPNS.dual();
        // lineOPNS_ represented as bivector
        // l_OPNS*= 0.9799999999999993*e2^e3 + 0.9799999999999995*e2^ei - 0.019599999999999985*e3^ei
        System.out.println(l_IPNS.toString("line (IPNS)"));
       
        // attitude
        Vector3d attitudeDual = l_IPNS.attitude();
        // sollte (0.98,0.0,0.0) (failed) falsches Vorzeichen
        // aber vielleicht stimmt das ja, da das Vorzeichen ja durch den euclidischen
        // Pseudoscalar festgelegt wird?????
        //FIXME
        System.out.println(toString("attitude (Dorst, lineIPNS)",attitudeDual));
        //assertTrue(equals(n1, attitudeDual));
        
        Point3d locationIPNS = l_IPNS.location();
        //FIXME
        // ist auch fälschlicherweise 0
        System.out.println(toString("location (line IPNS, Dorst)",locationIPNS));
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
    
    public void testSquaredSizeOfRounds(){
        
        System.out.println("----------------- squared size of rounds -----");
        System.out.println("RoundPointIPNS:");
        Point3d p = new Point3d(0d,-1d,0d); 
        System.out.println(toString("p", p));
        double weight = 3d;
        System.out.println("input weight="+String.valueOf(weight));
        //CGARoundPointIPNS pc = new CGARoundPointIPNS(p, weight);
        //System.out.println(pc.toString("pc"));
        //double squaredRadius1 = pc.squaredSizeIntern1().decomposeScalar(); // ok 0
        //System.out.println("squaredSizeIntern1 (RoundPointIPNS)="+String.valueOf(squaredRadius1));
        //assertTrue(equals(0d, squaredRadius1));
        //double squaredRadius2 = pc.squaredSizeIntern5().decomposeScalar(); // ok 0
        //System.out.println("squaredSizeIntern2 (RoundPointIPNS)="+String.valueOf(squaredRadius2));
        //assertTrue(equals(0d, squaredRadius2));
        //double squaredRadius3 = pc.squaredSizeIntern3().decomposeScalar(); // ok 0
        //System.out.println("squaredSizeIntern3 (RoundPointIPNS)="+String.valueOf(squaredRadius3));
        //assertTrue(equals(0d, squaredRadius3));
        //double squaredRadius4 = pc.squaredSizeIntern4().decomposeScalar(); 
        // squaredSizeIntern4 (RoundPointIPNS)=-6.0
        //System.out.println("squaredSizeIntern4 (RoundPointIPNS)="+String.valueOf(squaredRadius4)); // failed
        //assertTrue(equals(0d, squaredRadius4));
        //double squaredRadius5 = pc.squaredSizeIntern5().decomposeScalar();
        //System.out.println("squaredSizeIntern5 (RoundPointIPNS)="+String.valueOf(squaredRadius5)); // ok
        //assertTrue(equals(0d, squaredRadius5));
        
        System.out.println("\nSphereIPNS:");
        double radiusSquared = 2d;
        System.out.println("input radius="+String.valueOf(radiusSquared)+", weight="+String.valueOf(weight));
        CGASphereIPNS sphereIPNS = new CGASphereIPNS(p, radiusSquared, weight);
        System.out.println(sphereIPNS.toString("sphereIPNS"));
        double squaredRadius1 = sphereIPNS.squaredSizeIntern1().decomposeScalar(); // failed mit -4.25
        System.out.println("squaredSizeIntern1="+String.valueOf(squaredRadius1));
        double squaredRadius2 = sphereIPNS.squaredSizeIntern5().decomposeScalar(); // ok 0
        System.out.println("squaredSizeIntern2="+String.valueOf(squaredRadius2));
        double squaredRadius3 = sphereIPNS.squaredSizeIntern3().decomposeScalar(); // ok 0
        System.out.println("squaredSizeIntern3="+String.valueOf(squaredRadius3));
        double squaredRadius4 = sphereIPNS.squaredSizeIntern4().decomposeScalar(); // failed mit 6
        System.out.println("squaredSizeIntern4="+String.valueOf(squaredRadius4));
        double squaredRadius5 = sphereIPNS.squaredSizeIntern5().decomposeScalar(); // ok
        System.out.println("squaredSizeIntern5="+String.valueOf(squaredRadius5)); 
       
        // circle
        System.out.println("\nCircleIPNS:");
        
        // Kugel 1
        Point3d p1 = new Point3d(1d,0d,0d);
        System.out.println(toString("p1",p1));
        double weight1 = 1.0d;
        radiusSquared = 1.5d;
        CGASphereIPNS sphere1 = new CGASphereIPNS(p1, radiusSquared, weight1);
        System.out.println(sphere1.toString("sphere1"));
        
        // Kugel 2
        Point3d p2 = new Point3d(-1d,0d,0d);
        System.out.println(toString("p2",p2));
        double weight2 = 1.0d;
        CGASphereIPNS sphere2 = new CGASphereIPNS(p2, radiusSquared, weight2);
        System.out.println("sphere2="+sphere2.toString());
        
        // Circle from two spheres
        CGAOrientedCircleIPNS circleIPNS = new CGAOrientedCircleIPNS(sphere1, sphere2);
        Point3d location = new Point3d(0d,0d,0d);
        Vector3d attitudeTest = new Vector3d(-1,0,0);
        radiusSquared = 1.25;
        
        // circle=-2.0*eo^e1 - 1.25*e1^ei (grade 2 ok)
        System.out.println(circleIPNS.toString("circleIPNS"));
        CGAMultivector circleIPNSTest = o.op(CGAMultivector.createEx(-2d)).sub(CGAMultivector.createEx(1.25d).op(inf));
        System.out.println(circleIPNSTest.toString("circleIPNSTest"));
        assertTrue(circleIPNS.equals(circleIPNSTest));
        
        System.out.println("input radius (circleIPNS)="+String.valueOf(radiusSquared)+", weight="+String.valueOf(weight));
        System.out.println(circleIPNS.toString("circleIPNS"));
        
        squaredRadius1 = circleIPNS.squaredSize(); 
        System.out.println("squaredSizeIntern1 (circleIPNS)="+String.valueOf(squaredRadius1));
        assertTrue(equals(squaredRadius1, radiusSquared));
        
        //FIXME
        // ex Multivector is not invertable
        //squaredRadius2 = circleIPNS.squaredSizeIntern2().decomposeScalar(); 
        //System.out.println("squaredSizeIntern2="+String.valueOf(squaredRadius2));
        //assertTrue(equals(squaredRadius2, radiusSquared));
        
        squaredRadius3 = circleIPNS.squaredSizeIntern3().decomposeScalar(); // ok 0
        System.out.println("squaredSizeIntern3 (circleIPNS)="+String.valueOf(squaredRadius3));
        assertTrue(equals(squaredRadius3, radiusSquared));
        //squaredRadius4 = circleIPNS.squaredSizeIntern4().decomposeScalar(); // not available
        //System.out.println("squaredSizeIntern4="+String.valueOf(squaredRadius4));
        squaredRadius5 = circleIPNS.squaredSizeIntern5().decomposeScalar(); // ok
        System.out.println("squaredSizeIntern5 (circleIPNS)="+String.valueOf(squaredRadius5)); 
        assertTrue(equals(squaredRadius5, radiusSquared));
        
        // pointpair
        System.out.println("\nPointPairIPNS:");
        Point3d p3 = new Point3d(0d,0d,1d); 
        double weight3 = 1d;
        CGASphereIPNS sphere3 = new CGASphereIPNS(p3, radiusSquared, weight3);
        CGAOrientedPointPairIPNS ppIPNS = new CGAOrientedPointPairIPNS(sphere1, sphere2, sphere3);
        
        PointPair points = ppIPNS.decomposePoints(); // via undual and following Fernandes
        double r = points.p1().distance(points.p2())/2d;
        System.out.println("radiusSquared from decomposed points="+String.valueOf(r*r));
        
        squaredRadius1 = ppIPNS.squaredSize(); 
        System.out.println("squaredSizeIntern1 (ppIPNS)="+String.valueOf(squaredRadius1));
        assertTrue(equals(squaredRadius1, r*r));
        //squaredRadius2 = ppIPNS.squaredSizeIntern2().decomposeScalar(); 
        //System.out.println("squaredSizeIntern2="+String.valueOf(squaredRadius2));
        squaredRadius3 = ppIPNS.squaredSizeIntern3().decomposeScalar(); // ok 0
        System.out.println("squaredSizeIntern3 (ppIPNS)="+String.valueOf(squaredRadius3));
        assertTrue(equals(squaredRadius3, r*r));
        
        //squaredRadius4 = ppIPNS.squaredSizeIntern4().decomposeScalar(); // not available
        //System.out.println("squaredSizeIntern4="+String.valueOf(squaredRadius4));
        squaredRadius5 = ppIPNS.squaredSizeIntern5().decomposeScalar(); // ok
        System.out.println("squaredSizeIntern5 (ppIPNS)="+String.valueOf(squaredRadius5));
        assertTrue(equals(squaredRadius5, r*r));
        
        // location from decomposed Points = (0.0,0.0,0.3437500000000001)
        // FIXME stimmt das?
        Point3d locTest = new Point3d(points.p1());
        locTest.add(points.p2());
        locTest.scale(1d/2d);
        System.out.println(toString("location from decomposed Points",locTest));
        
        Point3d loc = ppIPNS.locationIntern2().location();
        System.out.println(toString("locationIntern2",loc));
    }
    
    public void testAttitudeOfFlats(){
        System.out.println("----------------- attitude of flats -----");
        System.out.println("\nFlatPointIPNS:");
        Point3d c = new Point3d(0d,0d,1d);
        double weight = 3d;
        CGAFlatPointIPNS flatPointIPNS = new CGAFlatPointIPNS(c, weight);
        System.out.println(flatPointIPNS.toString("flatPointIPNS (location)"));
        Vector3d attitude = flatPointIPNS.attitude();
        System.out.println(toString("attitude (flatPointIPNS)", attitude));
        CGAMultivector carrierFlat = flatPointIPNS.carrierFlat();
        System.out.println(carrierFlat.toString("carrierFlat (flatPointIPNS)"));
       
        System.out.println("\nLineIPNS:");
        Vector3d n1 = new Vector3d(0d,0d,1d); n1.normalize();
        Vector3d n2 = new Vector3d(0d,1d,0d); n2.normalize();
        double d = 2d;
        weight = 2d;
        System.out.println("HNF1: n=("+String.valueOf(n1.x)+","+String.valueOf(n1.y)+", "+String.valueOf(n1.z)+"), d="+String.valueOf(d));
        CGAPlaneIPNS plane1IPNS = new CGAPlaneIPNS(n1, d, weight);
        CGAPlaneIPNS plane2IPNS = new CGAPlaneIPNS(n2, d, weight);
        CGALineIPNS lineIPNS = new CGALineIPNS(plane1IPNS, plane2IPNS);
        Vector3d lineIPNSAttitude = lineIPNS.attitude();
        //FIXME
        // Spencer als attitude 2 scheint normalisiert zu sein
        // carrier hat anders Vorzeichen als dorst und Spencer
        System.out.println(toString("attitude (lineIPNS)", lineIPNSAttitude));
        Vector3d lineIPNSAttitude2 = lineIPNS.attitudeIntern2().direction();
        System.out.println(toString("attitude2 (lineIPNS)", lineIPNSAttitude2));
        CGAMultivector carrier = lineIPNS.carrierFlat();
        System.out.println(carrier.toString("carrier (lineIPNS)"));
        
        System.out.println("\nPlaneIPNS:");
        CGAMultivector carrierPlane1 = plane1IPNS.carrierFlat();
        System.out.println(carrierPlane1.toString("carrier (plane1IPNS)"));
        Vector3d plane1IPNSAttitude = plane1IPNS.attitude();
        System.out.println(plane1IPNS.toString("attitude (plane1IPNS)"));
        CGAMultivector plane1IPNSAttitude2 = plane1IPNS.attitudeIntern2();
        System.out.println(plane1IPNSAttitude2.toString("attitude2 (plane1IPNS)"));
    }
    
    
    public void testAttitudeOfRounds(){
        
        System.out.println("----------------- attitude of rounds -----");
        System.out.println("\nRoundPointIPNS:");
        Point3d p = new Point3d(0d,-1d,0d); 
        System.out.println(toString("p", p));
        double weight = 3d;
        System.out.println("input weight="+String.valueOf(weight));
        CGARoundPointIPNS pc = new CGARoundPointIPNS(p, weight);
        
        Vector3d attitude = pc.attitude();
        System.out.println(toString("attitude (CGARoundPointIPNS)", attitude));
        // attitude (round/tangent) = (-2.9999999999999973*e1^e2^e3^ei)
        // attitude (CGAOrientedFiniteRoundIPNS)=-2.9999999999999973*e1^e2^e3^ei
        // carrierFlat (RoundPointIPNS) = (-2.999999999999997*e1^e2^e3)
        CGAMultivector carrierFlat = pc.carrierFlat();
        System.out.println(carrierFlat.toString("carrierFlat (RoundPointIPNS)"));
        
        System.out.println("\nSphereIPNS:");
        double radiusSquared = 2d;
        CGASphereIPNS sphereIPNS = new CGASphereIPNS(p, radiusSquared, weight);
        //System.out.println(sphereIPNS.toString("sphereIPNS"));
        
        Vector3d attitudeSphereIPNS = sphereIPNS.attitude();
        System.out.println(toString("attitude (CGASphereIPNS)",attitudeSphereIPNS));
        // attitude (round/tangent) = (-2.999999999999997*e1^e2^e3^ei)
        // attitude (CGAOrientedFiniteRoundIPNS)=-2.999999999999997*e1^e2^e3^ei
        // carrierFlat (sphereIPNS) = (-2.999999999999996*e1^e2^e3)
        CGAMultivector carrierFlatSphereIPNS = sphereIPNS.carrierFlat();
        System.out.println(carrierFlatSphereIPNS.toString("carrierFlat (sphereIPNS)"));
       
        // circle
        System.out.println("\nCircleIPNS:");
        
        // Kugel 1
        Point3d p1 = new Point3d(1d,0d,0d);
        //System.out.println(toString("p1",p1));
        double weight1 = 1.0d;
        radiusSquared = 1.5d;
        CGASphereIPNS sphere1 = new CGASphereIPNS(p1, radiusSquared, weight1);
        //System.out.println(sphere1.toString("sphere1"));
        
        // Kugel 2
        Point3d p2 = new Point3d(-1d,0d,0d);
        //System.out.println(toString("p2",p2));
        double weight2 = 1.0d;
        CGASphereIPNS sphere2 = new CGASphereIPNS(p2, radiusSquared, weight2);
        //System.out.println("sphere2="+sphere2.toString());
        
        // Circle from two spheres
        CGAOrientedCircleIPNS circleIPNS = new CGAOrientedCircleIPNS(sphere1, sphere2);
        Point3d location = new Point3d(0d,0d,0d);
        Vector3d attitudeTest = new Vector3d(-1,0,0);
        radiusSquared = 1.25;
        
        Vector3d attitudeCircleIPNS = circleIPNS.attitude();
        System.out.println(toString("attitude (circleIPNS)",attitudeCircleIPNS));
        CGAEuclideanVector attituceCircleIPNS2 = circleIPNS.attitudeIntern2();
        System.out.println(toString("attitude2 (circleIPNS)",attituceCircleIPNS2.direction()));
        // FIXME Vorzeichenfehler
        // attitude (round/tangent) = (1.9999999999999982*e2^e3^ei)
        // carrierFlat (circleIPNS) = (-1.9999999999999976*e2^e3)
        CGAMultivector carrierFlatCircleIPNS = circleIPNS.carrierFlat();
        System.out.println(carrierFlatCircleIPNS.toString("carrierFlat (circleIPNS)"));
        
        // pointpair
        System.out.println("\nPointPairIPNS:");
        Point3d p3 = new Point3d(0d,0d,1d); 
        double weight3 = 1d;
        CGASphereIPNS sphere3 = new CGASphereIPNS(p3, radiusSquared, weight3);
        CGAOrientedPointPairIPNS ppIPNS = new CGAOrientedPointPairIPNS(sphere1, sphere2, sphere3);
        
        Vector3d attitudeppIPNS = ppIPNS.attitude();
        System.out.println(toString("attitude (ppIPNS)", attitudeppIPNS));
        CGAEuclideanVector attitudePPIPNS2 = ppIPNS.attitudeIntern2();
        System.out.println(attitudePPIPNS2.toString("attitude2 (ppIPNS)"));
        //System.out.println(attitudePPIPNS2.toString("attitudeIntern2 (PPIPNS)"));
        // attitude (round/tangent) = (1.9999999999999982*e2^ei)
        // attitude (CGAOrientedFiniteRoundIPNS)=1.9999999999999982*e2^ei
        // carrierFlat (PPIPNS) = (1.9999999999999976*e2)
        CGAMultivector carrierFlatPPIPNS = ppIPNS.carrierFlat();
        System.out.println(carrierFlatPPIPNS.toString("carrierFlat (ppIPNS)\n"));
    }
    
    public void testLinePair2(){
        System.out.println("--------------- test Line pair decomposition -----------------");
        CGALineOPNS l = new CGALineOPNS(new Point3d(1,0,0), new Vector3d(1,1,0));
        CGALineOPNS l1 = new CGALineOPNS(new Point3d(1,0,0), new Point3d(2,1,0));
        System.out.println(l.toString("l"));
        System.out.println(l1.toString("l1"));
        CGALineOPNS m = new CGALineOPNS(new Point3d(0,0,1), new Vector3d(0,0,1));
        CGALineOPNS m1 = new CGALineOPNS(new Point3d(0,0,1), new Point3d(0,0,2));
        System.out.println(m.toString("m"));
        System.out.println(m1.toString("m1"));
        
        CGALinePair lp = new CGALinePair(l1, m1);
        CGALinePair.LinePairParameters parameters = lp.decomposeLinePair();
        System.out.println(toString("alpha",parameters.alpha()));
        System.out.println(toString("location",parameters.location()));
        System.out.println(toString("attitude",parameters.attitude()));
    }
    
    
    public void testTranslation(){
        System.out.println("---------------- translation--------------");
        // transform a point
        Point3d p = new Point3d(0,0,1);
        CGARoundPointIPNS cp = new CGARoundPointIPNS(p);
        System.out.println(cp.toString("p"));
        Point3d pout = cp.location();
        //System.out.println(toString("pout",pout));
        assertTrue(equals(p,pout));
        
        Vector3d v = new Vector3d(1,0,0);
        CGATranslator t = new CGATranslator(v);
        CGARoundPointIPNS cp1Transform = new CGARoundPointIPNS(t.transform(cp));
        // transformed point = (1.25*eo - 0.5*e1 + 1.25*e3 + 0.375*ei)
        System.out.println(cp1Transform.toString("transformed point"));
        Point3d locationTransformed = cp1Transform.location();
        // transformed point (location, Dorst) = (-0.40000000000000013,0.0,1.0000000000000004)
        System.out.println(toString("transformed point (location, Dorst)", locationTransformed));
        
        //Point3d locationTransformed2 = cp1Transform.locationIntern2().location();
        // transformed point (location, 2) = (-0.3999999999999998,0.0,0.9999999999999998)
        //System.out.println(toString("transformed point (location, 2)", locationTransformed2));
        
        Point3d locationTransformedTest = new Point3d(p);
        locationTransformedTest.add(v);
        System.out.println(toString("Transformed point (test)", locationTransformedTest));
        // Transformed point = (-0.40000000000000013,0.0,1.0000000000000004) failed sollte 1,0,1 sein
        assertTrue(equals(locationTransformed, locationTransformedTest));
    }
    
    public void testTangentFromLineExtraction(){
        System.out.println("------------- test tanget extraction from line -------------");
        Point3d p1 = new Point3d(1,0,0);
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println(cp1.toString("p1"));
        Point3d p2 = new Point3d(0,1,0);
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println(cp2.toString("p2"));
        CGALineOPNS l1 = new CGALineOPNS(p1,p2);
        System.out.println(l1.toString("l1"));
        Vector3d l1Dir = new Vector3d(p1);
        l1Dir.sub(p2);
        System.out.println(toString("l1Dir",l1Dir));
        Point3d p3 = new Point3d(0,0,1.2);
        CGARoundPointIPNS cp3 = new CGARoundPointIPNS(p3);
        System.out.println(cp3.toString("p3"));
        Point3d p4 = new Point3d(0,0,-1.2);
        CGARoundPointIPNS cp4 = new CGARoundPointIPNS(p4);
        System.out.println(cp4.toString("p4"));
        CGALineOPNS l2 = new CGALineOPNS(p3,p4);
        System.out.println(l2.toString("l2"));
        Vector3d l2Dir = new Vector3d(p3);
        l2Dir.sub(p4);
        System.out.println(toString("l2Dir",l2Dir));
        
        // Winkel und gerichteter Abstand 
        
        // 1. CGA approach
        
        CGAAttitudeScalarOPNS vee = new CGAAttitudeScalarOPNS(l1.normalize().vee(l2.normalize()));
        System.out.println(vee.toString("vee"));
        //double distCGA = vee.
        
        // 2. Vector Algebra approach
        
        LineTuple3dComposition l1Vec = new LineTuple3dComposition(p1,l1Dir);
        LineTuple3dComposition l2Vec = new LineTuple3dComposition(p3,l2Dir);
        
        double dist = l1Vec.dist(l2Vec);
        System.out.println("distl1l2="+String.valueOf(dist));
        
        
        // Schnitt-/Aufpunkte bestimmen
        
        // L1dir = (-0.9999999999999992*e1 + 0.9999999999999992*e2)
        CGAEuclideanVector L1Dir = new CGAEuclideanVector(inf.lc(l1).rc(o));
        System.out.println(L1Dir.toString("L1dir"));
        
        Vector3d testL1Dir = L1Dir.direction();
        testL1Dir.normalize();
        
        Vector3d testl1Dir = new Vector3d(l1Dir);
        testl1Dir.normalize();
        System.out.println(toString("testl1Dir",testl1Dir)+" ? "+toString("testL1Dir",testL1Dir));
        // falsche Vorzeichen
        //assertTrue(testL1Dir.equals(l1Dir));
        
        CGAEuclideanVector L2Dir = new CGAEuclideanVector(inf.lc(l2).rc(o));
        Vector3d testL2Dir = L2Dir.direction();
        testL2Dir.normalize();
        l2Dir.normalize();
        System.out.println(L2Dir.toString("L2dir"));
        // vermutlich auch falsche Vorzeichen
        //assertTrue(testL2Dir.equals(l2Dir));
        
        // 1. dual vector algebra approach
        
        Vector3d u1 = new Vector3d(p2);
        u1.sub(p1);
        DualVector3d dualVecL1 = new DualVector3d(p1, u1);
        System.out.println(dualVecL1.toString("L1"));
        
        Vector3d u2 = new Vector3d(p4);
        u2.sub(p3);
        DualVector3d dualVecL2 = new DualVector3d(p3, u2);
        System.out.println(dualVecL2.toString("L2"));
        
        //DualNumber dn = dualVecL1.dualAngle(dualVecL2);
        //System.out.println(dn.toString("dn"));
         
        /*CGAMultivector m = l.rc(o).rc(inf);
        System.out.println(m.toString("att(l) Kleppe 2016")); // not normalized aber Richtung korrekt
        
        Vector3d v = l.attitude(); // normalized, aber falsches Vorzeichen
        System.out.println(toString("v",v));
        
        CGAMultivector m1 = inf.lc(l).negate(); //o.negate().lc(l);
        System.out.println(m1.toString("m1")); // not normalized, aber falsche Richtung
        m1 = m1.rc(o);
        System.out.println(m1.toString("m1"));*/
    }
    
    public void testNormalVectorOf2Lines(){
        System.out.println("-------------- test normal vector of two lines -----------------");
        Point3d p1 = new Point3d(1,0,0);
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println(cp1.toString("p1"));
        Point3d p2 = new Point3d(0,1,0);
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println(cp2.toString("p2"));
        CGALineOPNS l1 = new CGALineOPNS(p1,p2);
        System.out.println(l1.toString("l1"));
        Vector3d l1Dir = new Vector3d(p1);
        l1Dir.sub(p2);
        System.out.println(toString("l1Dir",l1Dir));
        Point3d p3 = new Point3d(0,0,1.2);
        CGARoundPointIPNS cp3 = new CGARoundPointIPNS(p3);
        System.out.println(cp3.toString("p3"));
        Point3d p4 = new Point3d(0,0,-1.2);
        CGARoundPointIPNS cp4 = new CGARoundPointIPNS(p4);
        System.out.println(cp4.toString("p4"));
        CGALineOPNS l2 = new CGALineOPNS(p3,p4);
        System.out.println(l2.toString("l2"));
        Vector3d l2Dir = new Vector3d(p3);
        l2Dir.sub(p4);
        System.out.println(toString("l2Dir",l2Dir));
        
        Vector3d n = new Vector3d();
        n.cross(l1Dir,l2Dir);
        System.out.println(toString("n (euclidean)",n));
        
        CGAMultivector u1CGA = inf.lc(l1).rc(o).normalize();
        System.out.println(u1CGA.toString("u1CGA"));
        CGAMultivector u2CGA = inf.lc(l2).rc(o).normalize();
        System.out.println(u2CGA.toString("u2CGA"));
        
        // Test common normal mit CGA - Vergleich mit Kreuzprodukt im Euclidean space
        CGAMultivector nCGA = u1CGA.op(u2CGA).gp(CGAMultivector.createI3().inverse());
        System.out.println(nCGA.toString("n (CGA)"));
        Vector3d nNormalized = new Vector3d(n);
        nNormalized.normalize();
        //System.out.println(toString("nNormalized",nNormalized));
        Vector3d nCGANormalized = new Vector3d(nCGA.extractE3ToVector3d());
        nCGANormalized.normalize();
        //System.out.println(toString("nCGAExtractE3Normalized",nCGANormalized));
        assertTrue(nNormalized.equals(nCGANormalized));
        
        // test skewed lines closest points
        CGAPlaneOPNS pi2 = new CGAPlaneOPNS(l2.op(nCGA));
        System.out.println(pi2.toString("pi2"));
        
        // pi2 durch euclid konstruieren
        Vector3d npi2 = new Vector3d();
        npi2.cross(l2Dir,n);
        CGAPlaneIPNS pi2IPNS = new CGAPlaneIPNS(npi2,p1);
        System.out.println(pi2IPNS.toString("pi2 (IPNS)"));
        CGAPlaneOPNS pi2OPNS = pi2IPNS.undual();
        System.out.println(pi2OPNS.toString("pi2 (OPNS)"));
        
        CGARoundPointOPNS c1 = new CGARoundPointOPNS(pi2.vee(l1));
        System.out.println(c1.toString("c1 (OPNS)"));
        CGAMultivector c1NormalizedDualSphere = c1.div(inf.lc(c1)).negate();
        System.out.println(c1NormalizedDualSphere.toString("c1 (normalizedDualSphere)"));
        CGAMultivector c1Euclid = o.op(inf).lc(o.op(inf).op(c1NormalizedDualSphere));
        System.out.println(c1Euclid.toString("c1 (euclid)"));
    }
    
    public void testEuclideanVector(){
        System.out.println("-------------- test euclidean vector -----------------");
        CGAEuclideanVector ev = new CGAEuclideanVector(new Vector3d(1,1,1));
        CGAMultivector m = ev.ip(ev);
        System.out.println(m.toString("m.ip(m)"));
        m = ev.op(ev);
        System.out.println(m.toString("m.op(m)"));
        m = ev.dual();
        System.out.println(m.toString("dual(m)"));
    }
    
    public void testEpsilon0Contraction(){
        System.out.println("----------------- test epsilon_0 projection of attitude scalar ---------------");
        CGAAttitudeScalarOPNS test1 = new CGAAttitudeScalarOPNS(1d);
        System.out.println(test1.toString("r=1: "));
        CGAMultivector test2 = test1.rc(o).negate();
        System.out.println(test2.toString("r=1(decomposed): "));
        CGAAttitudeScalarOPNS test3 = new CGAAttitudeScalarOPNS(0d);
        System.out.println(test3.toString("r=0: "));
    }
    
    public void testinf(){
        CGAMultivector test = inf.op(inf);
        System.out.println(test.toString("infsquare"));
    }
}