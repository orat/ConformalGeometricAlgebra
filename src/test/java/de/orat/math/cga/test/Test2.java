package de.orat.math.cga.test;

import de.orat.math.cga.api.CGAAttitudeBivectorOPNS;
import de.orat.math.cga.api.CGAAttitudeScalarOPNS;
import de.orat.math.cga.api.CGAAttitudeVectorIPNS;
import de.orat.math.cga.api.CGAAttitudeVectorOPNS;
import de.orat.math.cga.api.CGACircleIPNS;
//import de.orat.math.cga.api.CGACircleOPNS;
import de.orat.math.cga.api.CGACircleOPNS;
import de.orat.math.cga.api.CGAEuclideanBivector;
import de.orat.math.cga.api.CGAEuclideanVector;
import de.orat.math.cga.api.CGAFlatPointIPNS;
import de.orat.math.cga.api.CGAFlatPointOPNS;
import de.orat.math.cga.api.CGAForceIPNS;
import de.orat.math.cga.api.CGAForqueIPNS;
import de.orat.math.cga.api.CGAKVector;
import de.orat.math.cga.api.CGALineIPNS;
import de.orat.math.cga.api.CGALineOPNS;
import de.orat.math.cga.api.CGALinePair;
import de.orat.math.cga.api.CGALinePair;
import de.orat.math.cga.api.CGAMultivector;
import static de.orat.math.cga.api.CGAMultivector.createInf;
import static de.orat.math.cga.api.CGAMultivector.createOrigin;
import de.orat.math.cga.api.CGAPlaneIPNS;
import de.orat.math.cga.api.CGAPointPairIPNS;
import de.orat.math.cga.api.CGARoundPointIPNS;
import de.orat.math.cga.api.CGARoundPointOPNS;
import de.orat.math.cga.api.CGAScalarOPNS;
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
import static de.orat.math.cga.api.CGAMultivector.I0;
import static de.orat.math.cga.api.CGAMultivector.I3;
import static de.orat.math.cga.api.CGAMultivector.I3i;
import static de.orat.math.cga.api.CGAMultivector.e1;
import static de.orat.math.cga.api.CGAMultivector.e2;
import static de.orat.math.cga.api.CGAMultivector.e3;
import static de.orat.math.cga.api.CGAMultivector.inf;
import static de.orat.math.cga.api.CGAMultivector.o;
import de.orat.math.cga.api.CGAOrientedPointIPNS;
import de.orat.math.cga.api.CGAOrientedPointIPNS;
import de.orat.math.cga.api.CGAOrientedPointOPNS;
import de.orat.math.cga.api.CGAOrientedPointOPNS;
import de.orat.math.cga.api.CGAPlaneIPNS;
import de.orat.math.cga.api.CGAPlaneOPNS;
import de.orat.math.cga.api.CGAPointPairIPNS;
import de.orat.math.cga.api.CGAPointPairOPNS;
import de.orat.math.cga.api.CGARoundPointIPNS;
import de.orat.math.cga.api.CGARoundPointOPNS;
import de.orat.math.cga.api.CGAScalarIPNS;
import de.orat.math.cga.api.CGAScalarOPNS;
import de.orat.math.cga.api.CGAScrewAxisIPNS;
import de.orat.math.cga.api.CGASphereIPNS;
import de.orat.math.cga.api.CGASphereOPNS;
import de.orat.math.cga.api.CGATangentTrivectorOPNS;
import de.orat.math.cga.api.CGATangentTrivectorOPNS;
import de.orat.math.cga.api.CGATangentVectorIPNS;
import de.orat.math.cga.api.CGATangentVectorOPNS;
import de.orat.math.cga.api.CGATranslator;
import de.orat.math.cga.api.iCGAFlat;
import de.orat.math.cga.api.iCGAFlat;
import de.orat.math.cga.api.iCGAFlat.EuclideanParameters;
import de.orat.math.cga.api.iCGATangentOrRound;
import de.orat.math.cga.api.iCGATangentOrRound;
import org.junit.jupiter.api.Test;

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
    
    
    public Test2() {}

    static boolean equals(double a, double b){
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
    @Test
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
    
    @Test
    public void testI3i(){
        CGAMultivector m = I3.inverse();
        CGAMultivector m1 = I3i;
        assertTrue(m.equals(m1));
        
        // I3i = (-1.0*e1^e2^e3)
        System.out.println(m.toString("I3i"));
        CGAMultivector m2 = e1.op(e2).op(e3).gp(-1);
        assertTrue(m1.equals(m2));
    }
    
    @Test
    public void testLineOPNSComposition(){
        System.out.println("------------------------- test line OPNS comosition ------------------");
        //Point3d p1c = new Point3d(1,2,3);
        Point3d p1 = new Point3d(0,0,0);
        //Vector3d d = new Vector3d(4,-1,1);
        Vector3d d = new Vector3d(0,0,1);
        CGALineOPNS test = new CGALineOPNS(p1,d);
        System.out.println(test.toString("lineOPNS"));
                
        Point3d p2 = new Point3d(p1);
        p2.add(d);
        CGALineOPNS test2 = new CGALineOPNS(p1,p2);
        System.out.println(test2.toString("lineOPNS3"));
        assertTrue(test.equals(test2));
        
        Vector3d attitude2 = test2.attitude(); // via attitudeIntern()
        System.out.println(toString("attitude2", attitude2));
        assertTrue(equals(attitude2, d));
        
        Vector3d attitude2a = test2.attitudeIntern2().direction();
        System.out.println(toString("attitude2a", attitude2a));
        assertTrue(equals(attitude2a, d));
    }
    
    @Test
    public void testReverse(){
        System.out.println("------------------------- test reverse ------------------");
        
        Point3d p1 = new Point3d(1,2,3);
        Point3d p2 = new Point3d(p1);
        Vector3d d = new Vector3d(4,-1,1);
        p2.add(d);
        
        // impl1
        // opnsLine =        ( 3.9999999999999987*eo^e1^ei - 0.9999999999999997*eo^e2^ei - 9.0*e1^e2^ei + 0.9999999999999997*eo^e3^ei - 11.0*e1^e3^ei + 5.0*e2^e3^ei)
        // opnsLineReverse = (-3.9999999999999987*eo^e1^ei + 0.9999999999999997*eo^e2^ei + 9.0*e1^e2^ei - 0.9999999999999997*eo^e3^ei + 11.0*e1^e3^ei - 5.0*e2^e3^ei)

        // impl2
        // opnsLine =        (9.0*e124 - 9.0*e125 + 11.0*e134 - 11.0*e135 - 4.0*e145 - 5.0*e234 + 5.0*e235 + 1.0*e245 - 1.0*e345)
        // opnsLineReverse = (9.0*e124 + 9.0*e125 - 11.0*e134 + 11.0*e135 + 4.0*e145 + 5.0*e234 - 5.0*e235 - 1.0*e245 + 1.0*e345)

        CGALineOPNS opnsLine = new CGALineOPNS(p1,p2);
        System.out.println(opnsLine.toString("opnsLine"));
        CGAMultivector opnsLineReverse = opnsLine.reverse();
        System.out.println(opnsLineReverse.toString("opnsLineReverse"));
    }
    
    @Test
    public void testDual(){
        System.out.println("------------------------- test dual line IPNS comosition ------------------");
        
        // impl2
        //ipns_line_by_Dual = (1.0*e12 - 1.0*e13 + 5.0*e14 - 5.0*e15 - 4.0*e23 + 11.0*e24 - 11.0*e25 - 9.0*e34 + 9.0*e35)
        //ipns_line =         (1.0*e12 + 1.0*e13 - 5.0*e14+5.0*e15 + 4.0*e23 - 11.0*e24+11.0*e25 + 9.0*e34-9.0*e35)

        // impl 1 (ok)
        //ipns_line_by_Dual = (1*e1^e2 + 1*e1^e3 + 4*e2^e3 + 5*e1^ei + 11*e2^ei - 9*e3^ei)
        //ipns_line =         (1.0*e1^e2 + 1.0*e1^e3 + 4.0*e2^e3 + 5*e1^ei + 11*e2^ei - 9*e3^ei)

        Point3d p1 = new Point3d(1,2,3);
        Point3d p2 = new Point3d(p1);
        Vector3d d = new Vector3d(4,-1,1);
        p2.add(d);
        CGALineOPNS opnsLine = new CGALineOPNS(p1,p2);
        CGALineIPNS ipnsLine = opnsLine.dual();
        System.out.println(ipnsLine.toString("ipns_line_by_Dual"));
        CGALineIPNS test = new CGALineIPNS(p1,d);
        System.out.println(test.toString("ipns_line"));
        assertTrue(ipnsLine.equals(test));
    }
    
    @Test
    public void testLineIPNSComposition(){
        System.out.println("------------------------- test line IPNS comosition ------------------");
        Point3d p1 = new Point3d(1,2,3);
        //Point3d p1c = new Point3d(0,0,0);
        Vector3d d = new Vector3d(4,-1,1);
        //Vector3d d = new Vector3d(0,0,1);
        CGALineIPNS test = new CGALineIPNS(p1,d);
        System.out.println(test.toString("lineIPNS"));
                
        // ipns.attitude() FIXME falsches Vorzeichen
        // ok, nachdem ich bei undual das Vorzeichen geswitched habe
        Vector3d attitude2 = test.attitude(); // via attitudeIntern()
        System.out.println(toString("attitude2", attitude2));
        assertTrue(equals(attitude2, d));
        
        Vector3d attitude2a = test.attitudeIntern2().direction();
        System.out.println(toString("attitude2a", attitude2a));
        assertTrue(equals(attitude2a, d));
        
        // dual() ok
        Point3d p2 = new Point3d(p1);
        p2.add(d);
        CGALineOPNS opnsLine = new CGALineOPNS(p1,p2);
        CGALineIPNS test2 = opnsLine.dual();
        System.out.println(test2.toString("lineIPNS3"));
        assertTrue(test.equals(test2));
        
        // test undual failed, ok nachdem ich bei undual das Vorzeichen geswitched habe
        CGALineOPNS opnsLineTest = test2.undual();
        assertTrue(opnsLineTest.equals(opnsLine));
        
        //FIXME attitude schlägt fehlt, falsches Vorzeichen
        // ok, nachdem ich bei undual() das Vorzeichen geswitched habe
        attitude2 = test2.attitude(); // via attitudeIntern()
        System.out.println(toString("attitude2", attitude2));
        assertTrue(equals(attitude2, d));
        
        attitude2a = test2.attitudeIntern2().direction();
        System.out.println(toString("attitude2a", attitude2a));
        assertTrue(equals(attitude2a, d));
    }
    
    @Test
    public void testDorst2007DrillsBasicObjects(){
        System.out.println("------------------ Dorst2007 drills (chapter 13.9.1): basic objects ------------------");
        
        // 1.
        // composition of a point in IPNS representation by point and weight
        Point3d p1 = new Point3d(1d,1d,0d);
        double weight1 = 2;
        CGARoundPointIPNS p1cga = new  CGARoundPointIPNS(p1,weight1);
        System.out.println(p1cga.toString("p1_cga"));
        // mit ganja p1_cga = (2.0*e1 + 2.0*e2 + 1.0*e4 + 3.0*e5)
        CGARoundPointIPNS p1cgaTest = new CGARoundPointIPNS((o.
                add(CGAMultivector.createE3(p1)).add(inf)).gp(2d));
        System.out.println(p1cgaTest.toString("p1cgaTest"));
        assertTrue(p1cga.equals(p1cgaTest));
        System.out.println("location1"); // hier komme ich noch an mit ganja
        // decomposition
        // locationIPNS
        Point3d p1Test = p1cga.location();
        System.out.println("location2"); // hier nicht mehr
        // mit location fliege ich raus bei ganja:
        // Composition of the k-vector is failed: mv = (0.25*e1 + 1.0*e2 + 0.5*e4 + 1.0*e5 + 0.5*e125 + 0.25*e145)
        System.out.println(toString("p1Test", p1Test)+" "+toString("p1", p1));
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
        CGALineOPNS lineTest = new CGALineOPNS(o.add(CGAMultivector.createE3(p1)).
                gp(-2).op(CGAMultivector.createE3(new Point3d(0d,-1d,1d))).op(inf));
        System.out.println(lineTest.toString("lineOPNS (test)"));
        assertTrue(lineOPNS_.equals(lineTest));
        
        // test mit composition ohne weight und später gp(weight)
        CGALineOPNS lineOPNS_2 = new CGALineOPNS(p1,p2);
        lineOPNS_2 = new CGALineOPNS(lineOPNS_2.gp(-2));
        //System.out.println(lineOPNS_2.toString("lineOPNS composition ohne weight"));
        assertTrue(lineOPNS_2.equals(lineOPNS_));
        
        // 4.
        // direction of the lineOPNS_
        
        // entsprechend Ergebnis aus "Drills"
        Vector3d attitudeTest = new Vector3d(0d,2d,-2d);
        System.out.println(toString("attitudeTest123", attitudeTest));
        // test attitude via ipns nach Spencer ohne normalization
        CGAEuclideanVector attitude2 = lineOPNS_.attitudeIntern2();
        // attitude2 (line OPNS) = (0.7071067811865475*e2 - 0.7071067811865475*e3) mit Normalisierung nach spencer
        // attitude2 (line OPNS) = (1.9999999999999982*e2 - 1.9999999999999982*e3)
        System.out.println(attitude2.toString("attitude2 (line OPNS)"));
        assertTrue(equals(attitudeTest, attitude2.direction()));
        
        // test attitude euclid
        Vector3d attitudeEuclid = new Vector3d(p2);   // 1,0,1, w2=-1
        attitudeEuclid.sub(p1); // 1,1,0, w1=2
        double w_Line = -2; // = w1*w2? TODO
        attitudeEuclid.scale(w_Line);
        // attitudeEuclid = (-0.0,-2.0,2.0)
        System.out.println(toString("attitudeEuclid", attitudeEuclid));
        assertTrue(equals(attitudeEuclid, attitudeTest));
        
        // test attitude conformal determination
        //FIXME direction()-impl ist nicht coordinate-free, dass muss in CGAKVector geändert werden
        Vector3d attitude = lineOPNS_.attitudeIntern().direction(); // without normalization
        System.out.println(toString("attitude",attitude));
        System.out.println(toString("attitudeTest",attitudeTest));
        // hier fliege ich mit imp2 raus
        //FIXME
        assertTrue(equals(attitude, attitudeTest));
        
        
        
        
        // TODO
        
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
        iCGAFlat.EuclideanParameters planeOPNSParameters = planeOPNS.decomposeFlat();
        Vector3d attitudePlaneOPNS = planeOPNSParameters.attitude(); //planeOPNS.attitude();
        attitudePlaneOPNS.normalize();
        System.out.println(toString("attitudePlaneOPNS",attitudePlaneOPNS));
        CGAAttitudeBivectorOPNS attitudePlaneOPNSTest = 
                new CGAAttitudeBivectorOPNS(e1.add(e2).gp(2).op(e3.sub(e2).op(inf)));
        
        System.out.println(attitudePlaneOPNSTest.toString("attitudePlaneOPNS (test)"));
        Vector3d dirTest = attitudePlaneOPNSTest.direction();//new Vector3d(2,2,-2);
        dirTest.normalize();
        System.out.println(toString("attitudePlaneOPNS (test->vec)",dirTest));
        
        // schlägt fehl, wenn ich den Workaround in CGAPlaneOPNS aktiviere, der die 
        // Rechnung nach Spencer via ipns-plane macht
        //FIXME
        //assertTrue(equals(attitudePlaneOPNS, dirTest));
        
        
        // 
        
        CGAMultivector carrierFlatPlaneOPNS = planeOPNS.carrierFlat();
        // carrier flat (plane OPNS) = (0)
        // failed 
        System.out.println(carrierFlatPlaneOPNS.toString("carrier flat (plane OPNS)"));
    }
    
    @Test
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
        CGAPointPairOPNS ppOPNS = new CGAPointPairOPNS(p1, weight1, p2, weight2);
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
        // attitude (CGAPointPairOPNS)=1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei
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
    
    @Test
    public void testPointPairIPNS(){
        System.out.println("----------- test pointpair IPNS---------");
        Point3d c = new Point3d(0d,1d,0d);
        System.out.println(toString("center",c));
        Vector3d n = new Vector3d(0d,0d,1d);
        double r = 2d;
        double weight = 3d;
        CGAPointPairIPNS ppIPNS = new CGAPointPairIPNS(c, n, r, weight);
        System.out.println(ppIPNS.toString("ppIPNS"));
        testPointPairIPNS(ppIPNS);
        
        System.out.println("--------------------------");
        Point3d p1 = new Point3d(1d,0d,0d);
        System.out.println(toString("p1",p1));
        double weight1 = 2d;
        Point3d p2 = new Point3d(0d,1d,0d);
        System.out.println(toString("p2",p2));
        double weight2 = -1d;
        CGAPointPairOPNS ppOPNS = new CGAPointPairOPNS(p1, weight1, p2, weight2);
        testPointPairIPNS(ppOPNS.dual());
        System.out.println(ppOPNS.toString("ppOPNS"));
    }
    
    private void testPointPairIPNS(CGAPointPairIPNS ppIPNS){
        
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
    @Test
    public void testGanjaExampleCreatePointsCircleLine(){
        System.out.println("------------------Ganja.js expample: creation of points, circle, line --------------");
        
        // p1c
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(new Vector3d(1d,0d,0d));
        // p1c=1.0*eo + 1.0*e1 + 0.5*ei (korrekt)
        System.out.println(p1.toString("p1"));
        CGAMultivector p1Test = o.add(e1).add(inf.gp(0.5d));
        System.out.println(p1Test.toString("p1Test"));
        assert(p1.equals(p1Test));
        
        // p2c
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(new Vector3d(0d,1d,0d));
        // p2c=1.0*eo + 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p2="+p2.toString());
        CGAMultivector p2Test = o.add(e2).add(inf.gp(0.5d));
        System.out.println(p2Test.toString("p2Test"));
        assert(p2.equals(p2Test));
        
        // p3c
        CGARoundPointIPNS p3 = new CGARoundPointIPNS(new Vector3d(0d,0d,1d));
        // p3c=1.0*eo + 1.0*e3 + 0.5*ei (korrekt)
        System.out.println("p3="+p3.toString());
        CGAMultivector p3Test = o.add(e3).add(inf.gp(0.5d));
        System.out.println(p3Test.toString("p3Test"));
        assert(p3.equals(p3Test));
        
        // Compose circle from three points (OPNS representation)
        CGACircleOPNS circ = new CGACircleOPNS(p1,p2,p3);
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
    @Test
    public void testGanjaExampleCreatePointsPlaneSphere(){
        System.out.println("------------------Ganja.js expample: creation of points, plane, sphere --------------");
        Point3d p1 = new Point3d(1d,0d,0d);
        CGARoundPointIPNS p1c = new CGARoundPointIPNS(p1);
        // p1c=1.0*eo + 1.0*e1 + 0.5*ei (korrekt)
        System.out.println("p1="+p1c.toString());
        Point3d p2 = new Point3d(0d,1d,0d);
        CGARoundPointIPNS p2c = new CGARoundPointIPNS(p2);
        // p2c=1.0*eo + 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p2="+p2c.toString());
        Point3d p3 = new Point3d(0d,0d,-1d);
        CGARoundPointIPNS p3c = new CGARoundPointIPNS(p3);
        // p3c=1.0*eo - 1.0*e3 + 0.5*ei (korrekt)
        System.out.println("p3="+p3c.toString());
        Point3d p4 = new Point3d(0d,-1d,-0d);
        CGARoundPointIPNS p4c = new CGARoundPointIPNS(p4);
        // p4c=1.0*eo - 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p4="+p4c.toString());
        
        CGASphereOPNS s = new CGASphereOPNS(p1c,p2c,p3c,p4c);
        // s=2.0*eo^e1^e2^e3 - 1.0*e1^e2^e3^ei (korrekt)
        System.out.println("s="+s.toString());
        
        // location als normalized sphere mit r=0
        //FIXME
        /*java.lang.IllegalArgumentException: The given multivector is not of grade 4 or a null vector!
	at de.orat.math.cga.api.iCGAQuadvector.testGrade(iCGAQuadvector.java:40)
	at de.orat.math.cga.api.CGAKVector.<init>(CGAKVector.java:27)
	at de.orat.math.cga.api.CGARoundOPNS.<init>(CGARoundOPNS.java:18)
	at de.orat.math.cga.api.CGARoundPointOPNS.<init>(CGARoundPointOPNS.java:15)
	at de.orat.math.cga.test.Test2.testGanjaExampleCreatePointsPlaneSphere(Test2.java:484)
*/
        //CGARoundPointOPNS l = new CGARoundPointOPNS(s.negate().div(inf.lc(s)));
        
        // location = (1.0*eo + 0.5*ei) 
        // FIXME ei ist falsch? oder muss ich im Ergenis noch r=0 annehmen, damit der ei-Termin verschwindet?
        // vermutlich liegt das am Soderfall dass die location der Ursprung ist
        // Wie bekomme ich dann den euclidean vector herausprojeziert?
        //System.out.println(l.toString("location"));
        
        // plane opns
        CGAPlaneOPNS p = new CGAPlaneOPNS(p1c,p2c,p3c);
        // p1c=1.0*eo^e1^e2^ei + 1.0*eo^e1^e3^ei - 1.0*eo^e2^e3^ei - 1.0*e1^e2^e3^ei (korrekt)
        System.out.println("p="+p.toString());
        // new constructor comparison
        CGAPlaneOPNS pa = new CGAPlaneOPNS(p1,p2,p3);
        System.out.println("p="+pa.toString());
        assertTrue(p.equals(pa));
    }
    
    @Test
    public void testGanjaExampleCreateDualSphereAndPlane(){
        System.out.println("------------------Ganja.js expample: creation of dual sphere and plane --------------");
        // We start by defining a null basis, and upcasting for points
        //var ni = 1e4+1e5, no = .5e5-.5e4, nino = ni^no;
        //var up = (x)=> no + x + .5*x*x*ni;

        // point
        // var p1c = up(1e1+.5e2)
        Vector3d p1euclid = new Vector3d(1d,0.5d,0d);
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(p1euclid);
        // ganja.js: e1+0.5e2+0.12e4+1.12e5 = e1+0.5e2+0.625ei+e0 (korrekt)
        // java.js: p1c=1.0*eo + 1.0*e1 + 0.5*e2 + 0.625*ei
        System.out.println(toString("p1 (euclid)", p1euclid));
        System.out.println(p1.toString("p1"));
        CGAMultivector p1Test = CGAMultivector.createE3(new Vector3d(1d,0.5d,0d)).
                add(CGAMultivector.createInf(0.625)).add(CGAMultivector.createOrigin(1d));
        assert(p1.equals(p1Test));
        
        // point
        // p2c = up(1e2-.5e3);
        Vector3d p2euclid = new Vector3d(0d,1d,0.5d);
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(p2euclid);
        // java: p2c=1.0*eo + 1.0*e2 + 0.5*e3 + 0.625*ei
        // ganja.js: e2-0.5e3+0.12e4+1.12e5
        System.out.println(toString("p2 (euclid)",p2euclid));
        System.out.println("p2="+p2.toString());
        CGAMultivector p2Test = CGAMultivector.createE3(new Vector3d(0d,1d,0.5d)).
                add(CGAMultivector.createOrigin(1d)).add(CGAMultivector.createInf(0.625d));
        assertTrue(p2.equals(p2Test));
        
        // The distance between two points.
        //var d = (a,b)=>((a<<b).Length*2)**.5;
        
        // The duality operator can be used to produce a sphereIPNS from a point.
        // var s = ()=>!(p1c - .3**2*.5*ni);
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
        System.out.println(toString("radiusSquared (sphere1, Dorst)", radiusSquared1));
        assertTrue(equals(radiusSquared1, 0.3d*0.3d)); 
        
        CGASphereOPNS s = s1.undual();
        System.out.println("s="+s.toString());
        
        CGAMultivector inf_no = createInf(1d).op(createOrigin(1d));
        // Planes also have a direct dual representation. (but start from vector not point)
        // var p1c = ()=>!(d(p2c,no)*ni + (p2c^nino*nino).Normalized);
        
        CGAMultivector n = ((new CGARoundPointIPNS(p2)).op(inf_no).gp(inf_no)).normalize();
        // n1=-0.8944271909999159*e2 - 0.4472135954999579*e3
        System.out.println("n="+n.toString());
        CGAPlaneOPNS p = new CGAPlaneIPNS((CGAMultivector.createInf(Math.sqrt(p2.distSquare(new CGARoundPointIPNS(CGAMultivector.createOrigin(1d)))))).add(
                        n)).undual();
        // java p1c=-0.447*eo^e1^e2^ei - 0.89*eo^e1^e3^ei - 1.118*e1^e2^e3^ei
        // ganja.js p1c= -1.11e1234-1.11e1235-0.44e1245-0.89e1345
        // TODO
        System.out.println("p="+p.toString());
       
        double d = p2.distSquare(new CGARoundPointIPNS(CGAMultivector.createOrigin(1d)));
        // java d=1.2500000000000007
        System.out.println("d="+String.valueOf(d));
        // vermutlich brauche ich n1 als CGAAttitude und dann einen passenden Konstruktor
        //TODO
        //CGAPlane pa = CGAPlaneIPNS(n1, d);
        
        // p1c= -1.11e1234-1.11e1235-0.44e1245-0.89e1345

        // You can use the regressive product to calculate intersections..
        //var location = ()=>s&p1c;
        CGACircleOPNS c = new CGACircleOPNS(s.vee(p));
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
        // p1c=1.0*eo (korrekt)
        System.out.println("p="+p.toString());
        
        //S  = ()=>!(p1c-.5*ni),                 // main dual sphereIPNS around point 
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
        //7 28.2.23 wechsel auf undual 
        //TODO test
        CGACircleOPNS C = new CGACircleOPNS(S2.vee(plane.undual()));
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
        CGACircleOPNS C1 = new CGACircleOPNS(S.vee(P));
        // ganja.js: 0.89e123+e135 = 0.89e123 + 0.5e13i + e013 (korrekt)
        // java: C1=eo^e1^e3 + 0.9*e1^e2^e3 + 0.49*e1^e3^ei
        System.out.println("C1="+C1.toString());
        
        // C2 = ()=>S&L 
        CGAPointPairOPNS pp = new CGAPointPairOPNS(S.vee(L));
        // java: s&l_OPNS=eo^e1 - 0.89*e1^e2 - 0.5*e1^ei
        // ganja.js: -0.89e12-e15 = 0.89e12-0.5e1i + e01 (korrekt)
        System.out.println("s&l="+pp.toString());
        CGAPointPairOPNS ppTest = new CGAPointPairOPNS(o.op(e1).sub(e1.op(e2).gp(0.89)).
                sub(e1.op(inf).gp(0.5d)));
        System.out.println(ppTest.toString("s&l test"));
        //s&l test = (1.0*eo^e1 - 0.89*e1^e2 - 0.5*e1^ei)
        // stimmt in der zweiten Komponente nicht sehr exakt überein, 3. Nachkommastelle ...
        //FIXME
        //assertTrue(ppIPNS.equals(ppTest));
        
        // C3 = ()=>S&S2 sphereIPNS meet sphereIPNS
        CGACircleOPNS C3 = new CGACircleOPNS(S.vee(S2));
        // ganja.js: -1.35e123+1.39e235 = -1.35e123+0.7e23i+1.39e023
        // java: s&s=1.4*eo^e2^e3 - 1.35*e1^e2^e3 - 0.7*e2^e3^ei (korrekt)
        System.out.println("s&s="+C3.toString());
        
        // C4 = ()=>S&C 
        CGAPointPairOPNS C4 = new CGAPointPairOPNS(S.vee(C));
        // java s&location=1.39*eo^e2 - 1.35*e1^e2 - 0.69*e2^ei
        // ganja -1.35e12+1.39e25
        // TODO
        System.out.println("s&c="+C4.toString());
        
        // C5 = ()=>C&P2;  circleIPNS meet planeIPNS
        CGAPointPairOPNS C5 = new CGAPointPairOPNS(C.vee(P2));
        // ganja.js: 1.7e12-1.02e24-2.02e25 = 1.7e12 
        // java: location&p1c=eo^e2 + 1.7*e1^e2 + 3.23*e2^ei
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
    
    @Test
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

        // var p1c  = up(.5e2),                     // point
        CGARoundPointIPNS p = new CGARoundPointIPNS(new Vector3d(0d,0.5d,0d));
        // ganja.js: 0.5e2-0.37e4+0.62e5 = e0 + 0.5e2 + 0.125ei (korrekt)
        // java p1c=1.0*eo + 0.5*e2 + 0.125*ei
        System.out.println("p="+p.toString());
        
        // S  = sphereIPNS(up(-1.4e1),.5),            // left sphereIPNS
        // sphereIPNS = (P,r)=>!(P-r**2*.5*ni)
        CGASphereOPNS S = (new CGASphereIPNS(new Point3d(-1.4d,0d,0d),0.5)).undual();
        // java S*=1.0*eo - 1.4*e1 + 0.85*ei
        // java S=-eo^e1^e2^e3 + 1.34*eo^e2^e3^ei - 0.856*e1^e2^e3^ei
        // ganja: -1.35e1234-0.35e1235-1.39e2345 = -0.8e123i-e0123+1.4e023i (korrekt)
        //27.2.23 dual --> undual
        //TODO
        // überprüfen, ob das jetzt noch so stimmt, Vorzeichen?
        System.out.println("S="+S.toString());
        
        // sphereIPNS = (P,r)=>!(P-r**2*.5*ni)
        // planeIPNS  = (v,h=0)=>!(v-h*ni);
        // C  = sphereIPNS(up(1.4e1),.5)&planeIPNS(1e3),  // right circleIPNS
        CGASphereOPNS sphere = (new CGASphereIPNS(new Point3d(1.4d,0d,0d),0.5d)).undual();
        System.out.println("sphere="+sphere.toString());
        // sphereIPNS=-eo^e1^e2^e3 - 1.34*eo^e2^e3^ei - 0.8545*e1^e2^e3^ei
        //TODO
        CGACircleOPNS C = new CGACircleOPNS(sphere.vee((
                new CGAPlaneIPNS(new Vector3d(0d,0d,1d),0d, 0d)).undual()));
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
        // ()=>project_point_on_round(p1c,S), "p1c on S"
        CGAPointPairOPNS pOnS = S.project(p);
        // ganja.js: 0.7e12-1.89e14-0.49e15+0.30e24+0.80e25-1.95e45 = 0.7e12-1.19e1i-1.44e01+0.55e2i-0.5e02+1.95e0i
        // java POnS=-1.4*eo^e1 - 0.5*eo^e2 + 0.7*e1^e2 + 1.96*eo^ei - 1.197*e1^ei + 0.557*e2^ei (korrekt)
        System.out.println("POnS="+pOnS.toString());
        
        //()=>project_point_on_round(~p1c,C), "p1c on C",   // point on circleIPNS
        // java.lang.IllegalArgumentException: The given multivector is not not grade 1! grade()=2
        CGAPointPairOPNS pOnC = C.project(new CGARoundPointIPNS(p.conjugate()));
        // ganja.js -0.70e12 + 1.89e14+0.49e15+0.30e24+0.80e25-1.95e4 = -0.7e12+1.19e1i-1.44e01+0.55e2i-0.5e02
        // java pOnC=1.4*eo^e1 - 0.5*eo^e2 - 0.7*e1^e2 + 1.96*eo^ei + 1.197*e1^ei + 0.553*e2^ei
        System.out.println("pOnC="+pOnC.toString()); // (korrekt)
        
        // project_point_on_flat             = (point,planeIPNS)=>up(-point<<plane<<plane^nino*nino),
        
        // ()=>project_point_on_flat(p1c,P),   "p1c on P",   // point on planeIPNS
        CGARoundPointIPNS pOnP = P.project(p);
        // ganja.js: -0.90e2-0.09e4+0.90e5 = e0-0.9e2+0.41ei
        // java pOnP=1.0*eo - 0.899*e2 + 0.4049*ei (korrekt)
        System.out.println("pOnP="+pOnP.toString());
        
        // ()=>project_point_on_flat(~p1c,L),  "p1c on L",   // point on lineOPNS_
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
        
        
        // ()=>plane_through_point_tangent_to_x(p1c,S),    // planeIPNS through p1c tangent to S
        // tangent() error da falscher grade
        //FIXME
        //CGADualPlane pToS = S.tangent(p1c);
        // ganja.js 0.25e1234+0.25e1235+0.5e1345-1.39e2345
        // java pToS=0.0724999999999999*e1^e3
        //System.out.println("pToS="+pToS.toString());
        //TODO
        
        // ()=>plane_through_point_tangent_to_x(p1c,P),    // planeIPNS through p1c tangent to P
        CGAPlaneOPNS pToP = P.tangent(p);
        System.out.println("pToP="+pToP.toString());
        // java pToP=-0.19999999999999984*e1^e3
        // ganja.js -0.5e1234-0.5e1235-e1345
        //TODO
        
        // Graph the items. (hex numbers are html5 colors, two extra first bytes = alpha)
        // document.body.appendChild(this.graph([ 
        //      0x00FF0000, p1c, "p1c",                                       // point 
        //      0x00008800, ()=>project_point_on_round(p1c,S), "p1c on S",    // point on sphereIPNS
        //      0x000000FF, ()=>project_point_on_round(~p1c,C), "p1c on C",   // point on circleIPNS
        //  0x00888800, ()=>project_point_on_flat(p1c,P),   "p1c on P",   // point on planeIPNS
        //  0x00008888, ()=>project_point_on_flat(~p1c,L),  "p1c on L",   // point on lineOPNS_
        //  0xc0FF0000, ()=>plane_through_point_tangent_to_x(p1c,S),    // planeIPNS through p1c tangent to S2
        //  0xc000FF00, ()=>plane_through_point_tangent_to_x(p1c,P),    // planeIPNS through p1c tangent to P
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
    
    @Test
    public void testBasisBlades(){
        System.out.println("------------------Basis blades--------------");
        CGAMultivector m = CGAMultivector.createOrigin(1d).ip(CGAMultivector.createInf(1d));
        System.out.println("e0.einf="+m.toString());
        assertTrue(m.equals(new CGAScalarOPNS(-1d)));
    }
    
    @Test
    public void testPlaneOPNS(){
        System.out.println("------------------ PlaneOPNS --------------");
        //Point3d p1 = new Point3d(1,1,1);
        //Point3d p2 = new Point3d(0,0,0);
        //Point3d p3 = new Point3d(1,1,0);
        
        Point3d p1 = new Point3d(1,0,0.1);
        Point3d p2 = new Point3d(0,0,-0.1);
        Point3d p3 = new Point3d(0.3,1,0);
        
        // via point and direction
        Vector3d n = new Vector3d();
        Vector3d v1 = new Vector3d(p3);
        v1.sub(v1);
        Vector3d v2 = new Vector3d(p2);
        v1.sub(v1);
        n.cross(v1, v2);
        CGAPlaneOPNS planeOPNS1 = new CGAPlaneOPNS(p1, v1, v2);
        System.out.println(planeOPNS1.toString("opns-plane 1"));
        CGAPlaneOPNS planeOPNS1a = (new CGAPlaneIPNS(p1, n)).undual();
        System.out.println(planeOPNS1a.toString("opns-plane 1a"));
        assertTrue(planeOPNS1.equals(planeOPNS1a));
        
        
        // Normalenvektor n aus den obigen Punkten bestimmen
        
        // die letzten zwei Vorzeichen sind falsch
        //Vector3d v1 = new Vector3d(p2);
        //v1.sub(p1);
        //Vector3d v2 = new Vector3d(p3);
        //v2.sub(p1);
       
        // erstes und drittes Vorzecien ist falsch
        //Vector3d v1 = new Vector3d(p3);
        //v1.sub(p1);
        //Vector3d v2 = new Vector3d(p2);
        //v2.sub(p1);
        
        // mittleres Vorzeichen ist Falsch
        //Vector3d v1 = new Vector3d(p3);
        //v1.sub(p2);
        //Vector3d v2 = new Vector3d(p1);
        //v2.sub(p2);
        
        // erstes und drittes Vorzeichen falsch
        //Vector3d v1 = new Vector3d(p1);
        //v1.sub(p2);
        //Vector3d v2 = new Vector3d(p3);
        //v2.sub(p2);
        
        // mittleres Vorzeichen falsch
        //Vector3d v1 = new Vector3d(p1);
        //v1.sub(p3);
        //Vector3d v2 = new Vector3d(p2);
        //v2.sub(p3);
        
        // erstes und drittes Vorzeichen falsch
        v1 = new Vector3d(p2);
        v1.sub(p3);
        v2 = new Vector3d(p1);
        v2.sub(p3);
        
        n = new Vector3d();
        n.cross(v1, v2);
        System.out.println(toString("n(p1,p2,p3)",n));
        
        CGAPlaneOPNS planeOPNS = new CGAPlaneOPNS(new CGARoundPointIPNS(p1),
                new CGARoundPointIPNS(p2),new CGARoundPointIPNS(p3));
        CGAPlaneOPNS planeOPNSa = new CGAPlaneOPNS(p1, p2, p3);
        assertTrue(planeOPNS.equals(planeOPNSa));
        EuclideanParameters parameters = planeOPNS.decomposeFlat();
        // FIXME Vorzeichen falsch
        System.out.println(toString("attitude",parameters.attitude()));
        System.out.println(toString("n",n));
        assertTrue(equals(parameters.attitude(), n));
        
        Vector3d attitude = planeOPNS.attitude();
        // FIXME Vorzeichen falsch
        System.out.println(toString("attitude (planeOPNS, Dorst)",attitude));
        assertTrue(equals(attitude, n));
        
        // nach Spencer via dual IPNS
        attitude = planeOPNS.dual().attitudeIntern2().direction();
        // FIXME auch falsches Vorzeichen! 
        System.out.println(toString("attitudeIntern2 (planeOPNS, Spencer)",attitude));
        assertTrue(equals(n, attitude));
        
        //ok
        CGAPlaneIPNS planeIPNS = new CGAPlaneIPNS(p1, n);
        Vector3d planeIPNSAttitude = planeIPNS.attitude();
        //System.out.println(toString("planeIPNS(attitude)", planeIPNSAttitude));
        CGAPlaneOPNS planeOPNS2 = planeIPNS.undual();
        Vector3d planeOPNSAttitude = planeOPNS2.attitude();
        //System.out.println(toString("planeIPNS.dual(attitude)", planeOPNSAttitude));
        assertTrue(equals(planeIPNSAttitude, planeOPNSAttitude));
        
        // c1 = (0.0,0.0,0.0)
        System.out.println(toString("c",parameters.location()));
        CGAMultivector test = planeOPNS.op(new CGARoundPointIPNS(parameters.location()));
        assertTrue(test.isNull());
        
        
        
        
        EuclideanParameters parameters2 = planeOPNS.dual().decomposeFlat();
        // a_ = (0.7071067811865476,0.7071067811865476,0.0)
        // c_ = (0.0,0.0,0.0)
        System.out.println(toString("a_",parameters2.attitude()));
        System.out.println(toString("c_",parameters2.location()));
        Vector3d attitude2 = planeOPNS.dual().attitude();
        System.out.println(toString("attitude (planeIPNS, Dorst)",attitude));
    }
    
    @Test
    public void testPlaneIPNS(){
        System.out.println("---------------------- PlaneIPNS ------------------------");
        
        /*HNF: n=(0.0,0.0, 1.0), d=2.0
        planeIPNS = (1.0*e3 - 2.0*e4 + 2.0*e5)
        HNF: n=(0.7071067811865475,0.7071067811865475, 0.0), d=2.0
        CGAPlaneIPNS.attitudeIntern() invoked!
        attitudeIntern (CGAFlatIPNS, Dorst) = (0.7071067811865475*e134 + 0.7071067811865475*e135 + 0.7071067811865475*e234 - 0.7071067811865475*e235)
        attitude (planeIPNS, Dorst) = (0.0,0.0,-0.7071067811865475)
        attitudeIntern2(CGAPlaneIPNS, Spencer) = (0.7071067811865476*e1 + 0.7071067811865476*e2)
        attitudeIntern2 (planeIPNS, Spencer) = (0.7071067811865476,0.0,0.0)
                */
        // composition test
        
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
        
        // neues n definiert, da diese Orientierung kritischer ist für test der attitude
        n = new Vector3d(1d,1d,0d); n.normalize();
        System.out.println("HNF: n=("+String.valueOf(n.x)+","+String.valueOf(n.y)+", "+String.valueOf(n.z)+"), d="+String.valueOf(d));
        
        planeIPNS = new CGAPlaneIPNS(n, d, weight);
       
        //TODO
        // Wie ist die Orientierung dieser Ebene festgelegt?
        
        // attitude
        
        // nach Dorst
        // attitudeIntern (CGAFlatIPNS, Dorst) = (0.707106781186547*e1^e3^ei - 0.707106781186547*e2^e3^ei)
        // attitude (planeIPNS, Dorst) = (-0.7071067811865477,0.7071067811865477,0.0)
        // FIXME stimmt nicht - eine Komponente hat ein falsches Vorzeichen ...
        Vector3d attitude = planeIPNS.attitude();
        System.out.println(toString("attitude (planeIPNS, Dorst)",attitude));
        
        // nach Spencer
        attitude = planeIPNS.attitudeIntern2().direction();
        System.out.println(toString("attitudeIntern2 (planeIPNS, Spencer)",attitude));
        assertTrue(equals(n, attitude));
        
        // nach Kleppe2016
        //CGAMultivector normal = planeIPNS.op(inf).ip(o).negate();
        CGAMultivector normal = planeIPNS.op(inf).rc(o).negate();
        Vector3d normalEuclid = normal.extractE3ToVector3d();
        System.out.println(normal.toString("attitude (planeIPNS, Kleppe 1)"));
        assertTrue(equals(n, normalEuclid));
        
        // nach Kleppe via dual into opns
        //normal = planeIPNS.undual().ip(o).ip(inf)/*.normalize()*/.negate();
        normal = planeIPNS.undual().rc(o).rc(inf)/*.normalize()*/.negate();
        // attitude (planeIPNS, Kleppe 2 (dual)) = (-0.7071067811865468*e1^e3 + 0.7071067811865468*e2^e3)
        System.out.println(normal.toString("attitude (planeIPNS, Kleppe 2 (dual))"));
        // FIXME falsche, scheint identisch falsch wie Dorst2009 zu sein
        
        CGAMultivector carrierFlat = planeIPNS.carrierFlat();
        // carrierFlat (planeIPNS) = (-0.7071067811865469*e1^e3 + 0.7071067811865469*e2^e3)
        System.out.println(carrierFlat.toString("carrierFlat (planeIPNS)"));
        // FIXME auch falsch
        
        // squaredWeight
        double squaredWeight = planeIPNS.squaredWeight();
        // 1 da nichts bei der composition angegeben wurde
        System.out.println(toString("squaredWeight (planeIPNS, Dorst)", squaredWeight));
        assertTrue(equals(squaredWeight,1d));
        
      
        // location
        
        Point3d location = planeIPNS.locationIntern2().location(); 
        System.out.println(toString("location (planeIPNS, Spencer)",location));
        CGARoundPointIPNS testP = new CGARoundPointIPNS(location);
        CGAMultivector testM = planeIPNS.ip(testP);
        assertTrue(testM.isNull()); 
        
        // location planeIPNS, Dorst
        location = planeIPNS.location(new Point3d(5,5,1));
        System.out.println(toString("location (planeIPNS, Dorst)",location));
        CGAMultivector test = planeIPNS.ip(new CGARoundPointIPNS(location));
        assertTrue(test.isNull()); 
        
        
        
        CGAPlaneIPNS plane1 = new CGAPlaneIPNS(new Point3d(0d,0d,2d));
        // plane1=-1.0*e3 + 2.0000000000000004*ei
        //FIXME Vorzeichenfehler bei e3 egal welche normalize()-Methode ich verwende
        System.out.println(plane1.toString("plane1"));
        
        // based on MacAllans formula as function of cga-point and euclidean-normal
        Point3d p = new Point3d(n);
        p.scale(d);
        CGARoundPointIPNS pc = new CGARoundPointIPNS(p);
        CGAPlaneIPNS planeIPNSa = new CGAPlaneIPNS(pc, new CGAEuclideanVector(n));
        assertTrue(planeIPNS.equals(planeIPNSa));
    }
    
    @Test
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
        CGACircleIPNS circleIPNS = new CGACircleIPNS(sphere1, sphere2);
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
        
        CGACircleOPNS circleOPNS = new CGACircleOPNS(point1, point2, point3);
        System.out.println(circleOPNS.toString("circleOPNS2"));
        
        double radiusSquared = 1d;
        double squaredWeight = 4d;
        
        CGACircleIPNS circleIPNS2 = circleOPNS.dual();
        System.out.println(circleIPNS2.toString("circleIPNS2"));
        
        testCircleIPNS(circleIPNS2, c, radiusSquared, squaredWeight, attitudeTest);
    }
    
    @Test
    private void testCircleIPNS(CGACircleIPNS circleIPNS, Point3d c, 
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
    
    @Test
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
    
    @Test
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
        
        double squaredRadius = sphereIPNS.squaredSize();
        System.out.println(toString("squaredRadius (sphereIPNS)", squaredRadius)); // failed
        assertTrue(equals(radius*radius,squaredRadius));
        
        
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
         
        // composition from center and point on the sphere
        CGARoundPointIPNS c = new CGARoundPointIPNS(p1);
        Point3d pOnSphere = new Point3d(p1);
        p1.x += radius;
        CGASphereIPNS sphereIPNS2 = new CGASphereIPNS(c, new CGARoundPointIPNS (pOnSphere));
        System.out.println(c.toString("c"));
        System.out.println(sphereIPNS2.toString("sphereIPNS2"));
        assertTrue(c.equals(sphereIPNS2));
        

        //???????????
        // Dorst2007: -einf*P = 3 stimmt? soll das die Normierung sein?
        System.out.println("-einf*sphere = "+
                String.valueOf(-CGAMultivector.createInf(1d).scp(sphereIPNS)));
        // norm(p1c) = 6 ist sollte das nicht 1 sein?
        System.out.println("norm(sphere) = "+String.valueOf(sphereIPNS.norm()));
    }
    
    @Test
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
        CGAPointPairIPNS pp1 = new CGAPointPairIPNS(cp1,cp2);
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
        CGACircleIPNS c1 = new CGACircleIPNS(s1,s2);
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
        //assertTrue(equals(p1c,location7));
        
        // point pair OPNS (failed)
        CGAPointPairOPNS pp2 = new CGAPointPairOPNS(p1, 1d ,p2, 1d);
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
        CGAPointPairOPNS pp1OPNS = new CGAPointPairOPNS(cp1,cp2);
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
        //assertTrue(equals(p1c, location9));
        
        // circles
        CGACircleOPNS c1b = new CGACircleOPNS(p1,p2,p3);
        System.out.println(c1b.toString("c1b"));
        Point3d location10 = c1b.location();
        System.out.println(toString("loc(c1b)",location10));
        //assert(equals(location10, ));
    }
        
    @Test
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
        // ist das gleich p1c.sqr()?
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

    @Test
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
    
    @Test
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
        // sollte p1c ∧ p2c ∧ ∞ = −2(o + (e1 + e2 )) ∧ (e3 − e2 ) ∧ ∞. sein
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
    @Test
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
        
        // neu ohne Normalisierung und mit anderer Reihenfolge von p1c und p2c
        Vector3d n = new Vector3d(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        n.scale(weight2*weight1);
        //Vector3d n = new Vector3d(p1c.x-p2c.x, p1c.y-p2c.y, p1c.z-p2c.z);
        //n.normalize();
        System.out.println(toString("n",n));
        
        
        // lineOPNS_ OPNS representation
        
        CGALineOPNS l_OPNS = new CGALineOPNS(p1, weight1, p2, weight2);
        // dual lineOPNS_ represented as tri-vector
        System.out.println(l_OPNS.toString("l OPNS"));
      
        CGALineOPNS l_OPNSTest = new CGALineOPNS(o.add(e1.add(e2)).gp(-2d).op(e3.sub(e2)).op(inf));
        System.out.println(l_OPNSTest.toString("l_OPNSTest"));
        assertTrue(l_OPNSTest.equals(l_OPNS));
        
        // attitude [Dorst2007]
        Vector3d attitude = l_OPNS.attitude();
        // l_OPNSTest = (2.0*eo^e2^ei + 2.0*e1^e2^ei - 2.0*eo^e3^ei - 2.0*e1^e3^ei - 2.0*e2^e3^ei)
        // attitudeIntern (CGAOrientedFiniteFlatOPNS, Dorst) = (1.9999999999999991*e2^ei - 1.9999999999999991*e3^ei)
        System.out.println(toString("attitude (Dorst2007)",attitude));
        // default n = (-0.0,2.0,-2.0)
        // hier fliege ich bei ganja wegen falschem Vorzeichen raus
        //FIXME
        assertTrue(equals(n, attitude));
        
        // attitude [Kleppe2016] directional component of a lineOPNS_
        CGAMultivector m = l_OPNS.rc(o).rc(inf);
        // attitude (Kleppe2016) V1 = (-1.9999999999999984*e2 + 1.9999999999999984*e3)
        // falsches Vorzeichen
        //FIXME vielleicht ist die Formel in Kleppe falsch? ja so ist es vermutlich!
        System.out.println(m.toString("attitude (Kleppe2016)")); 
        
        // attitude [Rettig2023]
        m = inf.lc(l_OPNS).rc(o);
        // attitude (Rettig2023) = (1.9999999999999984*e2 - 1.9999999999999984*e3)
        System.out.println(m.toString("attitude (Rettig2023)")); 
        assertTrue(equals(n, m.extractE3ToVector3d()));
        
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
    
    @Test
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
    
    @Test 
    public void testAttitudeBivectorOPNSDecomposition(){
        Vector3d v1 = new Vector3d(1,2,3);
        Vector3d v2 = new Vector3d(1,0,2);
        Vector3d cross = new Vector3d();
        cross.cross(v1, v2);
        CGAAttitudeBivectorOPNS bi = new CGAAttitudeBivectorOPNS(v1, v2);
        Vector3d dir = bi.direction();
        assertTrue(equals(cross, dir));
    }
    
    @Test
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
        assertTrue(equals(yDirVec, yDirVec2));
    }
     
    // test tangent vector construction and decomposition
     @Test
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
        //CGATangentVectorIPNS ytangentDual2 = new CGATangentVectorIPNS(p1c,u);
        //System.out.println("TangentVector* ytangentDual2 = "+ytangentDual2.toString());
        
        
        Vector3d yTangentVec = ytangent.attitude();
        toString("yTangent (decomposed) u",yTangentVec);
        assertTrue(equals(yTangentVec,u));
        
        Point3d yTangentPoint = ytangent.location();
        toString("yTangent (decomposed) P", yTangentPoint);
        assertTrue(equals(yTangentPoint,p));
    }
    
    @Test
    public void testSquaredSizeOfRounds(){
        
        System.out.println("----------------- squared size of rounds -----");
        System.out.println("RoundPointIPNS:");
        Point3d p = new Point3d(0d,-1d,0d); 
        System.out.println(toString("p", p));
        double weight = 3d;
        System.out.println("input weight="+String.valueOf(weight));
        //CGARoundPointIPNS pc = new CGARoundPointIPNS(p1c, weight);
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
        CGACircleIPNS circleIPNS = new CGACircleIPNS(sphere1, sphere2);
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
        CGAPointPairIPNS ppIPNS = new CGAPointPairIPNS(sphere1, sphere2, sphere3);
        
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
    
    @Test
    public void testAttitudeOfFlats(){
        System.out.println("----------------- attitude of IPNS flats -----------------");
        System.out.println("\nFlatPointIPNS:");
        Point3d c = new Point3d(0d,0d,1d);
        double weight = 3d;
        CGAFlatPointIPNS flatPointIPNS = new CGAFlatPointIPNS(c, weight);
        System.out.println(flatPointIPNS.toString("flatPointIPNS (location)"));
        Vector3d attitude = flatPointIPNS.attitude();
        System.out.println(toString("attitude (flatPointIPNS)", attitude));
        CGAMultivector carrierFlat = flatPointIPNS.carrierFlat();
        System.out.println(carrierFlat.toString("carrierFlat (flatPointIPNS)"));
       
        System.out.println("\nLineIPNS as intersection of two planes:");
        Vector3d n1 = new Vector3d(0d,0d,1d); n1.normalize();
        Vector3d n2 = new Vector3d(0d,1d,0d); n2.normalize();
        double d = 2d;
        weight = 2d;
        System.out.println("HNF1: n=("+String.valueOf(n1.x)+","+String.valueOf(n1.y)+", "+String.valueOf(n1.z)+"), d="+String.valueOf(d));
        CGAPlaneIPNS plane1IPNS = new CGAPlaneIPNS(n1, d, weight);
        System.out.println("HNF2: n=("+String.valueOf(n2.x)+","+String.valueOf(n2.y)+", "+String.valueOf(n2.z)+"), d="+String.valueOf(d));
        CGAPlaneIPNS plane2IPNS = new CGAPlaneIPNS(n2, d, weight);
        CGALineIPNS lineIPNS = new CGALineIPNS(plane1IPNS, plane2IPNS);
        Vector3d lineIPNSAttitude = lineIPNS.attitude();
        
        // attitudeIntern (CGAFlatIPNS, Dorst) = (3.9999999999999973*e1^ei)
        System.out.println(toString("attitude (lineIPNS)", lineIPNSAttitude));
        
        //FIXME Vorzeichen im Vergleich zu attitude()
        // attitueIntern2 (CGALineIPNS, Spencer) = (-3.9999999999999987*e1)
        Vector3d lineIPNSAttitude2 = lineIPNS.attitudeIntern2().direction();
        // das Vorzeichen ist immer noch anders als bei attitude()
        //attitueIntern2 (CGALineIPNS, Spencer) = (-3.9999999999999987*e1)
        // attitude2 (lineIPNS) = (-3.9999999999999987,0.0,0.0)
        System.out.println(toString("attitude2 (lineIPNS)", lineIPNSAttitude2));
        
        // carrier hat anders Vorzeichen als dorst 
        //FIXME
        CGAMultivector carrier = lineIPNS.carrierFlat();
        // carrier (lineIPNS) = (3.9999999999999964*e1)
        System.out.println(carrier.toString("carrier (lineIPNS)"));
        
        System.out.println("\nPlane1IPNS:");
        CGAMultivector carrierPlane1 = plane1IPNS.carrierFlat();
        System.out.println(carrierPlane1.toString("carrier (plane1IPNS)"));
        Vector3d plane1IPNSAttitude = plane1IPNS.attitude();
        System.out.println(plane1IPNS.toString("attitude (plane1IPNS)"));
        CGAMultivector plane1IPNSAttitude2 = plane1IPNS.attitudeIntern2();
        System.out.println(plane1IPNSAttitude2.toString("attitude2 (plane1IPNS)"));
        
        System.out.println("\nPlane2IPNS:");
        CGAMultivector carrierPlane2 = plane2IPNS.carrierFlat();
        System.out.println(carrierPlane2.toString("carrier (plane2IPNS)"));
        Vector3d plane2IPNSAttitude = plane2IPNS.attitude();
        System.out.println(plane2IPNS.toString("attitude (plane2IPNS)"));
        CGAMultivector plane2IPNSAttitude2 = plane2IPNS.attitudeIntern2();
        System.out.println(plane2IPNSAttitude2.toString("attitude2 (plane2IPNS)"));
        Vector3d att = plane2IPNS.decomposeFlat().attitude();
        System.out.println(toString("plane2 att decomposeflat", att));
    }
    
    @Test
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
        //System.out.println(toString("p1c",p1c));
        double weight1 = 1.0d;
        radiusSquared = 1.5d;
        CGASphereIPNS sphere1 = new CGASphereIPNS(p1, radiusSquared, weight1);
        //System.out.println(sphere1.toString("sphere1"));
        
        // Kugel 2
        Point3d p2 = new Point3d(-1d,0d,0d);
        //System.out.println(toString("p2c",p2c));
        double weight2 = 1.0d;
        CGASphereIPNS sphere2 = new CGASphereIPNS(p2, radiusSquared, weight2);
        //System.out.println("sphere2="+sphere2.toString());
        
        // Circle from two spheres
        CGACircleIPNS circleIPNS = new CGACircleIPNS(sphere1, sphere2);
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
        CGAPointPairIPNS ppIPNS = new CGAPointPairIPNS(sphere1, sphere2, sphere3);
        
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
    
    @Test
    public void testAttitudeFromRoundPointIPNS(){
        System.out.println("----------------- test attitude from round point ipns -------------------");
        Point3d p = new Point3d(0.2564423003521458, -0.23434198923703015, 0.5895999999978854);
        p = new Point3d(1,2,3);
        CGARoundPointIPNS pc = new CGARoundPointIPNS(p);
        Vector3d att2 = pc.attitude();
        System.out.println(toString("att2", att2));
        iCGATangentOrRound.EuclideanParameters parameters = pc.decompose();
        Vector3d attitude = parameters.attitude();
        // att = (Infinity,Infinity,Infinity)
        System.out.println(toString("att", attitude));
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
    
    @Test
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
    
    @Test
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
        //assertTrue(testL2Dir.equals(l2DirNormalized));
        
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
    
    @Test
    public void testIntersectionPointOfIntersectingLines(){
        System.out.println("-------------- test intersecting lines intersection point (Lasenby) -----------------");
        Point3d p1 = new Point3d(1,0,0);
        System.out.println(toString("p1 (euclid)",p1));
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println(cp1.toString("p1"));
        Point3d p2 = new Point3d(0,1,0);
        System.out.println(toString("p2 (euclid)",p2));
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println(cp2.toString("p2"));
        CGALineOPNS l1 = new CGALineOPNS(p1,p2);
        System.out.println(l1.toString("l1"));
        Vector3d l1Dir = new Vector3d(p1);
        l1Dir.sub(p2);
        l1Dir.normalize();
        System.out.println(toString("l1Dir (euclid)",l1Dir));
        Point3d p3 = new Point3d(0.5,0.5,1);
        CGARoundPointIPNS cp3 = new CGARoundPointIPNS(p3);
        System.out.println(cp3.toString("p3"));
        Point3d p4 = new Point3d(0.5,0.5,-1);
        CGARoundPointIPNS cp4 = new CGARoundPointIPNS(p4);
        System.out.println(cp4.toString("p4"));
        CGALineOPNS l2 = new CGALineOPNS(p3,p4);
        System.out.println(l2.toString("l2"));
        Vector3d l2Dir = new Vector3d(p3);
        l2Dir.sub(p4);
        l2Dir.normalize();
        System.out.println(toString("l2Dir (eudlid)",l2Dir));
        
        // Intersection nach Lasenby
        // mit reflection of o funktioniert es
        CGARoundPointIPNS m = intersect(l1.normalize(), l2.normalize(), 
                new CGARoundPointIPNS(o)/*new CGARoundPointIPNS(new Point3d(2d,2d,2d))*/);
        System.out.println(m.toString("intersect(Lasenby, o)"));
        
        // funktioniert nicht!
        CGARoundPointIPNS m2 = intersect(l1.normalize(), l2.normalize(), 
                new CGARoundPointIPNS(new Point3d(2d,2d,1d)));
        System.out.println(m2.toString("intersect(Lasenby, 2,2,1)"));
        
        CGAMultivector l2l1 = l2.normalize().gp(l1.normalize());
        System.out.println(l2l1.toString("l2l1"));
       
    }
    
    @Test
    public void testReflectionOfLines(){
        System.out.println("-------------- test orthogonal line reflection -----------------");
        CGALineIPNS l1 = new CGALineIPNS(new Point3d(0,0,0), new Vector3d(0,0,1));
        CGALineIPNS l2 = new CGALineIPNS(new Point3d(1,0,1), new Vector3d(0,1,0));
        CGAMultivector l1ss = l1.sub(l2.gp(l1).gp(l2));
        System.out.println(l1ss.toString("l1ss"));
        //assertTrue(false);
    }
    
    @Test
    public void testDistances(){
        System.out.println("-------------- test distance determination between points -----------------");
        Point3d p1 = new Point3d(0,0,0);//new Point3d(1,1,1);
        Point3d p2 = new Point3d(0.0,0.0,0.16242364179550933);
        double value = p1.distance(p2);
        CGARoundPointIPNS p1c = new CGARoundPointIPNS(p1);
        System.out.println(p1c.toString("p1c"));
        CGARoundPointIPNS p2c = new CGARoundPointIPNS(p2);
        System.out.println(p2c.toString("p2c"));
        
        double dist = Math.sqrt(p1c.ip(p2c).decomposeScalar()*(-2d));
        assertTrue(equals(dist, value));
    }
    
    @Test
    public void testFlatPointsIPNS(){
        System.out.println("-------------- test flat point ipns -----------------");
        Point3d p1 = new Point3d(0,0,100);
        CGAFlatPointIPNS p1c = new CGAFlatPointIPNS(p1);
        // p1c = (-1.0*e1^e2^e3 - 99.99999999999997*e1^e2^ei)
        System.out.println(p1c.toString("p1c"));
        Point3d p1a = p1c.location();
        assertTrue(equals(p1, p1a));
    }
    
    @Test
    public void testOrthogonalLineOfParallelLinesByReflection(){
        System.out.println("-------------- test parallel lines orthogonal line by reflection -----------------");
        Point3d p1 = new Point3d(1,0,0);
        System.out.println(toString("p1 (euclid)",p1));
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println(cp1.toString("p1"));
        Point3d p2 = new Point3d(0,1,0);
        System.out.println(toString("p2 (euclid)",p2));
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println(cp2.toString("p2"));
        CGALineOPNS l1 = new CGALineOPNS(p1,p2);
        System.out.println(l1.toString("l1"));
        Vector3d l1Dir = new Vector3d(p1);
        l1Dir.sub(p2);
        l1Dir.normalize();
        System.out.println(toString("l1Dir (euclid)",l1Dir));
        Point3d p3 = new Point3d(1,0,1);
        CGARoundPointIPNS cp3 = new CGARoundPointIPNS(p3);
        System.out.println(cp3.toString("p3"));
        Point3d p4 = new Point3d(0,1,1);
        CGARoundPointIPNS cp4 = new CGARoundPointIPNS(p4);
        System.out.println(cp4.toString("p4"));
        CGALineOPNS l2 = new CGALineOPNS(p3,p4);
        System.out.println(l2.toString("l2"));
        Vector3d l2Dir = new Vector3d(p3);
        l2Dir.sub(p4);
        l2Dir.normalize();
        System.out.println(toString("l2Dir (euclid)",l2Dir));
        
        // Diese Subtraktion führt irgendwie zur Orthogonalisierung im Falle von
        // skewed lines
        //FIXME
        CGALineOPNS l1Orthogonal = new CGALineOPNS(l1.sub(l2.gp(l1).gp(l2)));
        System.out.println(l1Orthogonal.toString("l1 (orthogonal)"));
        
        //FIXME normalize() schlägt fehlt, da das innere Produkt 0 ist
        CGAMultivector u1Orthogonal = inf.lc(l1Orthogonal).rc(o); //.normalize();
        System.out.println(u1Orthogonal.toString("u1 (orthogonal)"));
        Vector3d u1vec = u1Orthogonal.extractE3ToVector3d();
        // Der Vektor scheint zum Richtungsvektor von l1 zu degenerieren, statt
        // zum benötigten Vektor zwischen beiden Geraden
        // Warum ist das so? Geht das auch anders?
        //FIXME
        System.out.println(toString("u1vec",u1vec));
        
        CGAMultivector l2l1 = l2.normalize().gp(l1.normalize());
        System.out.println(l2l1.toString("l2l1"));
    }
    
    @Test
    public void testIntersectionPointOfParallelLines(){
        System.out.println("-------------- test parallel lines by regressive product -----------------");
        Point3d p1 = new Point3d(1,0,0);
        System.out.println(toString("p1 (euclid)",p1));
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println(cp1.toString("p1"));
        Point3d p2 = new Point3d(0,1,0);
        System.out.println(toString("p2 (euclid)",p2));
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println(cp2.toString("p2"));
        CGALineOPNS l1 = new CGALineOPNS(p1,p2);
        System.out.println(l1.toString("l1"));
        Vector3d l1Dir = new Vector3d(p1);
        l1Dir.sub(p2);
        l1Dir.normalize();
        System.out.println(toString("l1Dir (euclid)",l1Dir));
        Point3d p3 = new Point3d(1,0,1);
        CGARoundPointIPNS cp3 = new CGARoundPointIPNS(p3);
        System.out.println(cp3.toString("p3"));
        Point3d p4 = new Point3d(0,1,1);
        CGARoundPointIPNS cp4 = new CGARoundPointIPNS(p4);
        System.out.println(cp4.toString("p4"));
        CGALineOPNS l2 = new CGALineOPNS(p3,p4);
        System.out.println(l2.toString("l2"));
        Vector3d l2Dir = new Vector3d(p3);
        l2Dir.sub(p4);
        l2Dir.normalize();
        System.out.println(toString("l2Dir (eudlid)",l2Dir));
        
        // Richtungsvector von l1 nach Dorst2007
        CGAMultivector u1CGA = inf.lc(l1).rc(o).normalize();
        System.out.println(u1CGA.toString("u1CGA"));
        Vector3d u1vec = u1CGA.extractE3ToVector3d();
        System.out.println(toString("u1vec",u1vec));
        // hat anderes Vorzeichen als erwartet, vielleicht ist doch Kleppe richtig
         
        // Richtungsvector nach Kleppe1016
        //CGAMultivector u1CGAa = l1.rc(o).rc(inf).normalize();
        // Kleppe2016 hat anderes Vorzeichen
        //System.out.println(u1CGAa.toString("u1CGA [Kleppe2016]"));
        
        CGAMultivector u2CGA = inf.lc(l2).rc(o).normalize();
        System.out.println(u2CGA.toString("u2CGA"));
        Vector3d u2vec = u2CGA.extractE3ToVector3d();
        System.out.println(toString("u2vec",u2vec));
        // hat anderes Vorzeichen als erwartet, vielleicht ist doch Kleppe richtig
        
        //CGAMultivector u2CGAa = l2.rc(o).rc(inf).normalize();
        // Kleppe2016 hat anderes Vorzeichen
        //System.out.println(u2CGAa.toString("u2CGA [Kleppe2016]"));
        
        CGAMultivector nCGA = u1CGA.op(u2CGA).gp(CGAMultivector.createI3().inverse());
        System.out.println(nCGA.toString("n (CGA)"));
        //Vector3d nNormalized = new Vector3d(nNormalized);
        //nNormalized.normalize();
        //System.out.println(toString("nNormalized",nNormalized));
        //Vector3d nCGANormalized = new Vector3d(nCGA.extractE3ToVector3d());
        //nCGANormalized.normalize();
        //System.out.println(toString("nCGAExtractE3Normalized",nCGANormalized));
        //assertTrue(nNormalized.equals(nCGANormalized));
        
        
        // test skewed lines closest points
        // TODO brauche ich normalisiert oder nicht normalisiert?
        //nCGA.normalize(); //FIXME hier fliege ich raus mit null multivector, normalized failed
        CGAMultivector m = l2.op(nCGA);
        try {
            CGAPlaneOPNS pi2 = new CGAPlaneOPNS(m);
            System.out.println(pi2.toString("pi2"));
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage()+" : "+m.toString("l2.op(nCGA)"));
        }
        //CGAPlaneOPNS pi2n = pi2.normalize();
        //System.out.println(pi2n.toString("pi2 (normalize)"));
        
        
        // nach ganja.js example c1 bestimmen
        //CGAMultivector c1cga = I0.lc(pi2n.vee(l1).op(o));
        //System.out.println(c1cga.toString("c1cga"));
        //Point3d p1p = c1cga.extractE3ToPoint3d();
        //CGARoundPointOPNS c1 = new CGARoundPointOPNS(p1p);
        
    }
    
    @Test
    public void testIntersectionPointOfIntersectingLines2(){
        System.out.println("-------------- test intersecting lines by regressive product -----------------");
        Point3d p1 = new Point3d(1,0,0);
        System.out.println(toString("p1 (euclid)",p1));
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println(cp1.toString("p1"));
        Point3d p2 = new Point3d(0,1,0);
        System.out.println(toString("p2 (euclid)",p2));
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println(cp2.toString("p2"));
        CGALineOPNS l1 = new CGALineOPNS(p1,p2);
        System.out.println(l1.toString("l1"));
        Vector3d l1Dir = new Vector3d(p1);
        l1Dir.sub(p2);
        l1Dir.normalize();
        System.out.println(toString("l1Dir (euclid)",l1Dir));
        Point3d p3 = new Point3d(0.5,0.5,1);
        CGARoundPointIPNS cp3 = new CGARoundPointIPNS(p3);
        System.out.println(cp3.toString("p3"));
        Point3d p4 = new Point3d(0.5,0.5,-1);
        CGARoundPointIPNS cp4 = new CGARoundPointIPNS(p4);
        System.out.println(cp4.toString("p4"));
        CGALineOPNS l2 = new CGALineOPNS(p3,p4);
        System.out.println(l2.toString("l2"));
        Vector3d l2Dir = new Vector3d(p3);
        l2Dir.sub(p4);
        l2Dir.normalize();
        System.out.println(toString("l2Dir (eudlid)",l2Dir));
        
        // Richtungsvector von l1 nach Dorst2007
        CGAMultivector u1CGA = inf.lc(l1).rc(o).normalize();
        System.out.println(u1CGA.toString("u1CGA"));
        Vector3d u1vec = u1CGA.extractE3ToVector3d();
        System.out.println(toString("u1vec",u1vec));
        // hat anderes Vorzeichen als erwartet, vielleicht ist doch Kleppe richtig
         
        // Richtungsvector nach Kleppe1016
        //CGAMultivector u1CGAa = l1.rc(o).rc(inf).normalize();
        // Kleppe2016 hat anderes Vorzeichen
        //System.out.println(u1CGAa.toString("u1CGA [Kleppe2016]"));
        
        CGAMultivector u2CGA = inf.lc(l2).rc(o).normalize();
        System.out.println(u2CGA.toString("u2CGA"));
        Vector3d u2vec = u2CGA.extractE3ToVector3d();
        System.out.println(toString("u2vec",u2vec));
        // hat anderes Vorzeichen als erwartet, vielleicht ist doch Kleppe richtig
        
        //CGAMultivector u2CGAa = l2.rc(o).rc(inf).normalize();
        // Kleppe2016 hat anderes Vorzeichen
        //System.out.println(u2CGAa.toString("u2CGA [Kleppe2016]"));
        
        CGAMultivector nCGA = u1CGA.op(u2CGA).gp(CGAMultivector.createI3().inverse());
        System.out.println(nCGA.toString("n (CGA)"));
        //Vector3d nNormalized = new Vector3d(nNormalized);
        //nNormalized.normalize();
        //System.out.println(toString("nNormalized",nNormalized));
        //Vector3d nCGANormalized = new Vector3d(nCGA.extractE3ToVector3d());
        //nCGANormalized.normalize();
        //System.out.println(toString("nCGAExtractE3Normalized",nCGANormalized));
        //assertTrue(nNormalized.equals(nCGANormalized));
        
        
        // test skewed lines closest points
        // TODO brauche ich normalisiert oder nicht normalisiert?
        nCGA.normalize();
        CGAMultivector mm = l2.op(nCGA);
        try {
            CGAPlaneOPNS pi2 = new CGAPlaneOPNS(mm);
            System.out.println(pi2.toString("pi2"));
            CGAPlaneOPNS pi2n = pi2.normalize();
            System.out.println(pi2n.toString("pi2 (normalize)"));


            // nach ganja.js example c1 bestimmen
            //FIXME I0.negate() ? vorzeichen kontrollieren
            CGAMultivector c1cga = I0.lc(pi2n.vee(l1).op(o));
            System.out.println(c1cga.toString("c1cga"));
            //Point3d p1p = c1cga.extractE3ToPoint3d();
            //CGARoundPointOPNS c1 = new CGARoundPointOPNS(p1p);
        } catch (IllegalArgumentException e){
            System.out.println("No k-vec:"+mm.toString("mm"));
            throw e;
        }
    }
        
    @Test
    public void testSkewedLinesClosestPoints(){
        System.out.println("-------------- test skewed lines closest points -----------------");
        Point3d p1 = new Point3d(1,0,0);
        System.out.println(toString("p1 (euclid)",p1));
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println(cp1.toString("p1"));
        Point3d p2 = new Point3d(0,1,0);
        System.out.println(toString("p2 (euclid)",p2));
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println(cp2.toString("p2"));
        CGALineOPNS l1 = new CGALineOPNS(p1,p2);
        System.out.println(l1.toString("l1"));
        Vector3d l1Dir = new Vector3d(p1);
        l1Dir.sub(p2);
        l1Dir.normalize();
        System.out.println(toString("l1Dir (euclid)",l1Dir));
        Point3d p3 = new Point3d(0,0,1.2);
        CGARoundPointIPNS cp3 = new CGARoundPointIPNS(p3);
        System.out.println(cp3.toString("p3"));
        Point3d p4 = new Point3d(0,0,-1.2);
        CGARoundPointIPNS cp4 = new CGARoundPointIPNS(p4);
        System.out.println(cp4.toString("p4"));
        CGALineOPNS l2 = new CGALineOPNS(p3,p4);
        System.out.println(l2.toString("l2"));
        Vector3d l2DirNormalized = new Vector3d(p3);
        l2DirNormalized.sub(p4);
        l2DirNormalized.normalize();
        System.out.println(toString("l2Dir (eudlid)",l2DirNormalized));
        
        Vector3d nNormalized = new Vector3d();
        nNormalized.cross(l1Dir,l2DirNormalized);
        nNormalized.normalize();
        System.out.println(toString("n (l1 X l2, euclidean)",nNormalized));
        
        CGAMultivector u1CGA = inf.lc(l1).rc(o).normalize();
        System.out.println(u1CGA.toString("u1CGA"));
        Vector3d u1CGAAsVector3d = u1CGA.extractE3ToVector3d();
        System.out.println(toString("u1CGA (as Vector3d)",u1CGAAsVector3d));
        
        // Kleppe2016 hat anderes Vorzeichen als erwartet
        CGAMultivector u1CGAa = l1.rc(o).rc(inf).normalize();
        System.out.println(u1CGAa.toString("u1CGA [Kleppe2016]"));
        
        CGAMultivector u2CGA = inf.lc(l2).rc(o).normalize();
        System.out.println(u2CGA.toString("u2CGA"));
        Vector3d u2vec = u2CGA.extractE3ToVector3d();
        System.out.println(toString("u2CGA (as Vector3d)",u2vec));
        
        // Kleppe2016 hat anderes Vorzeichen als erwartet
        CGAMultivector u2CGAa = l2.rc(o).rc(inf).normalize();
        System.out.println(u2CGAa.toString("u2CGA [Kleppe2016]"));
        
        // "Common normal" mit CGA (funktioniert nicht für parallele Linien)
        CGAMultivector nCGA = u1CGA.op(u2CGA).gp(CGAMultivector.createI3().inverse()).normalize();
        System.out.println(nCGA.toString("n (CGA)"));
        Vector3d nCGANormalized = new Vector3d(nCGA.extractE3ToVector3d());
        nCGANormalized.normalize();
        //System.out.println(toString("nCGAExtractE3Normalized",nCGANormalized));
        
        // Vergleich "common normal" mit Kreuzprodukt im Euclidean space
        //System.out.println(toString("nNormalized",nNormalized));
        assertTrue(nNormalized.equals(nCGANormalized));
        
        // "Common normal" mit CGA und reflection
        CGAMultivector nCGA2 = inf.lc(l1.sub(l2.gp(l1).gp(l2))).rc(o).normalize();
        // n (CGA) = (-0.7071067811865475*e1 - 0.7071067811865475*e2)
        // n (CGA reflection) = (-0.7071067811865476*e1 + 0.7071067811865476*e2)
        //FIXME
        // die Methoden mit reflection scheint so noch nicht zu stimmen
        System.out.println(nCGA2.toString("n (CGA reflection)"));
        
        
        // test skewed lines closest points
        
        // pi2 durch plane opns konstrurieren: l2 ist opns, nCGA= u1.op(u2)I3
        // unklar, ob das so richtig ist
        //FIXME
        CGAPlaneOPNS pi2 = new CGAPlaneOPNS(l2.op(nCGA));
        CGAPlaneOPNS pi2n = pi2.normalize();
        System.out.println(pi2n.toString("pi2 (l.op(nCGA))"));
        
        // pi2 durch euclid und plane ipns konstruieren
        Vector3d npi2 = new Vector3d();
        npi2.cross(l2DirNormalized,nNormalized);
        CGAPlaneIPNS pi2IPNS = new CGAPlaneIPNS(p3,npi2);
        //System.out.println(pi2IPNS.toString("pi2 (IPNS)"));
        CGAPlaneOPNS pi2OPNS = pi2IPNS.undual();
        System.out.println(pi2OPNS.toString("pi2 (via cross euclid u. ipns dual)"));
        
        //FIXME hier fliege ich nach der Neuimplementierung von undual() raus
        //assertTrue(pi2OPNS.equals(pi2n));
        
        // Intersection nach Lasenby
        CGAMultivector m = intersect(l1.normalize(), l2.normalize(), new CGARoundPointIPNS(new Point3d(2d,2d,2d)));
        System.out.println(m.toString("intersect"));
        
        //TODO stimmt nicht mit c1Dual überein
        //CGARoundPointOPNS c1 = new CGARoundPointOPNS(pi2.vee(l1));
        
        // nach ganja.js example c1 bestimmen
        // nach ganja.js example c1 bestimmen
        //FIXME I0.negate() ? vorzeichen kontrollieren
        CGAMultivector c1cga = I0.lc(pi2.vee(l1).op(o));
        System.out.println(c1cga.toString("c1cga"));
        Point3d p1p = c1cga.extractE3ToPoint3d();
        CGARoundPointOPNS c1 = new CGARoundPointOPNS(p1p);
        
        System.out.println(c1.toString("c1 (OPNS)"));
        CGAMultivector c1NormalizedDualSphere = c1.div(inf.lc(c1)).negate();
        System.out.println(c1NormalizedDualSphere.toString("c1 (normalizedDualSphere)"));
        CGAMultivector c1Euclid = o.op(inf).lc(o.op(inf).op(c1NormalizedDualSphere));
        System.out.println(c1Euclid.toString("c1 (euclid)"));
        
        // Vergleich mit dual vector space Berechnung
        Plane4d pi2Dual = new Plane4d(npi2,p3);
        DualVector3d l1Dual = new DualVector3d(p1, l1Dir);
        Point3d p1Dual = pi2Dual.intersect(l1Dual);
        System.out.println(toString("c1 (dual)",p1Dual));
    }
    
    @Test
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
    
    @Test
    public void testEuclideanDual(){
        System.out.println("-------------- test euclidean dual -----------------");
        // a cross b = dual(a op b)
        Vector3d a = new Vector3d(1,0,0);
        Vector3d b = new Vector3d(1,1,0);
        CGAEuclideanVector ac = new CGAEuclideanVector(a);
        CGAEuclideanVector bc = new CGAEuclideanVector(b);
        CGAEuclideanBivector bi = new CGAEuclideanBivector(ac.op(bc));
        System.out.println(bi.toString("bi"));
        //CGAMultivector dual_bi = bi.dual();
        // dual_bi = (-0.9999999999999997*eo^e3^ei)
        //System.out.println(dual_bi.toString("dual_bi"));
        
        CGAEuclideanVector bi_div_I3 = new CGAEuclideanVector(bi.div(I3));
        System.out.println(bi_div_I3.toString("bi/I3"));
         
        CGAEuclideanVector bi_mul_I3 = new CGAEuclideanVector(bi.gp(I3));
        System.out.println(bi_mul_I3.toString("bi I3"));
        
        // Vergleich mit Kreuzprodukt
        Vector3d cross = new Vector3d();
        cross.cross(a,b);
        System.out.println(toString("cross",cross));
        assertTrue(equals(cross, bi_div_I3.direction()));
        
        //TODO
        Vector3d e1e = new Vector3d(1,0,0);
        CGAEuclideanVector e1 = new CGAEuclideanVector(e1e);
        CGAEuclideanBivector be1 = e1.euclideanDual();
        // Euclidean bivector = (-1.0*e2^e3)
        System.out.println(be1.toString("Euclidean bivector"));
        // EuclideanBivector direkt aus nur einem Vektor erzeugen wie ist das
        // definiert bezüglich der Vorzeichen?
        //CGAEuclideanBivector be1a = new CGAEuclideanBivector(e1e);
    }
    
    @Test
    public void testEpsilon0Contraction(){
        System.out.println("----------------- test epsilon_0 projection of attitude scalar ---------------");
        CGAAttitudeScalarOPNS test1 = new CGAAttitudeScalarOPNS(1d);
        System.out.println(test1.toString("r=1: "));
        CGAMultivector test2 = test1.rc(o).negate();
        System.out.println(test2.toString("r=1(decomposed): "));
        CGAAttitudeScalarOPNS test3 = new CGAAttitudeScalarOPNS(0d);
        System.out.println(test3.toString("r=0: "));
    }
    
    @Test
    public void testinf(){
        CGAMultivector test = inf.op(inf);
        System.out.println(test.toString("infsquare"));
    }
    
    @Test
    public void testAPlus(){
         System.out.println("----------------- test a+ ---------------");
        CGAEuclideanVector s = new CGAEuclideanVector(new Vector3d(0d,0d,1d));
        CGAMultivector s1 = s.div(I3);
        System.out.println(s1.toString("s1"));
        CGAMultivector s2 = s.op(I3.inverse());
        System.out.println(s2.toString("s2"));
    }
    
    @Test
    public void testNullVectors(){
        System.out.println("----------------- null vectors -----------------");
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(new Point3d(1,0,0));
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(inf);
        CGAMultivector test = p1.gp(p2);
        // test gp = (-1 + eo^ei + *e1^ei)
        System.out.println(test.toString("test gp"));
    }
    @Test
    public void testAttitudeScalar(){
        CGAAttitudeScalarOPNS test = new CGAAttitudeScalarOPNS(5);
        System.out.println("----------------- CGA Attitude Scalar -----------------");
        System.out.println(test.toString("opns"));
        // ipns = (-8.881784197001252E-16*eo^e1^e2^e3 - 4.999999999999997*e1^e2^e3^ei)
        System.out.println(test.dual().toString("ipns"));
    }
   
    
    @Test
    public void testEuclideanBivector(){ 
        System.out.println("------------------  test euclidean bivector ---------------------");
        Point3d p1 = new Point3d(1,0,0.1);
        Point3d p2 = new Point3d(0,0,-0.1);
        Point3d p3 = new Point3d(0.3,1,0);
        
        Vector3d n = new Vector3d();
        Vector3d v1 = new Vector3d(p3);
        v1.sub(p1);
        Vector3d v2 = new Vector3d(p2);
        v2.sub(p1);
        n.cross(v1, v2);
        
        CGAEuclideanVector v1c = new CGAEuclideanVector(v1);
        CGAEuclideanVector V2c = new CGAEuclideanVector(v2);
        CGAEuclideanVector nc = v1c.cross(V2c);
        
        CGAEuclideanVector vec = new CGAEuclideanVector(n);
        assertTrue(nc.equals(vec));
        
        assertTrue(equals(nc.direction(), n));
        
        CGAEuclideanBivector bivec = new CGAEuclideanBivector(v1, v2);
        System.out.println(bivec.toString("bivec"));
        CGAEuclideanBivector bivec2 = new CGAEuclideanBivector(vec.euclideanDual());
        System.out.println(bivec2.toString("bivec2"));
        // bivec = (1.0*e1^e2 + 0.03999999999999998*e1^e3 - 0.2*e2^e3)
        // bivec2 = (-1.0*e1^e2 - 0.03999999999999998*e1^e3 + 0.2*e2^e3)
        assertTrue(bivec.equals(bivec2));
    }
    
    @Test
    public void testAttitudeIPNSVector(){
        System.out.println("------------------  test attitude ipns vector ---------------------");
        Vector3d v = new Vector3d(1,2,3);
        CGAAttitudeVectorOPNS attitudeVecOPNS = new CGAAttitudeVectorOPNS(v);
        System.out.println(attitudeVecOPNS.toString("attVecOPNS"));
        System.out.println(toString("attVecOPNS.dir",attitudeVecOPNS.direction()));
        // default
        // attVecOPNS = (1.0*e1^ei + 2.0*e2^ei + 3.0*e3^ei)
        // attVecOPNS.dir = (0.9999999999999997,1.9999999999999993,2.999999999999999)

        // ganja
        // attVecOPNS = (1.0*e14 + 1.0*e15 + 2.0*e24 + 2.0*e25 + 3.0*e34 + 3.0*e35)
        // attVecOPNS ist ok
        
        //attVecOPNS.dir = (-1.0,-2.0,-3.0) alle Vorzeichen falsch
        assertTrue(equals(v, attitudeVecOPNS.direction()));
        
        CGAAttitudeVectorIPNS attitudeVecIPNS = attitudeVecOPNS.dual();
        System.out.println(toString("attVecIPNS.dir",attitudeVecIPNS.direction()));
        
        //FIXME
        // CGAAttitudeVectorIPNS() scheint mir fehlerhaft zu sein
        CGAAttitudeVectorIPNS attitudeVecIPNS2 = new CGAAttitudeVectorIPNS(v);
        // attVecIPNS = (2.220446049250313E-16*eo^e1^e2 - 1.1102230246251565E-16*eo^e1^e3 + 5.551115123125783E-17*eo^e2^e3 
        //              - 3.0*e1^e2^ei + 2.0*e1^e3^ei - 1.0*e2^e3^ei)
        // attVecIPNS2 = (3.0*e1^e2^ei - 2.0*e1^e3^ei + 1.0*e2^e3^ei)
        // falsches Vorzeichen
        System.out.println(attitudeVecIPNS.toString("attVecIPNS")); // da dessen dir stimmt, sollte das ok sein
        System.out.println(attitudeVecIPNS2.toString("attVecIPNS2")); // falsches Vorzeichen
        assertTrue(attitudeVecIPNS.equals(attitudeVecIPNS2));
    }
    @Test
    public void testgp(){
        System.out.println("------------------  test gp ---------------------");
        CGAMultivector test = CGAMultivector.createE3(new Vector3d(1,2,3)).gp(CGAMultivector.createI3());
        System.out.println(test.toString("test"));
    }
    
    @Test
    public void testScalar(){
        System.out.println("----------------- test Scalar --------------------");
        CGAScalarOPNS test = new CGAScalarOPNS(3d);
        System.out.println(test.toString("scalar (opns)"));
        CGAScalarIPNS test2 = test.dual();
        System.out.println(test2.toString("scalar (opns).dual()"));
        System.out.println("scalar (opns).dual().decompose() "+String.valueOf(test2.value()));
        test = test2.undual();
        System.out.println("scalar (opns).dual().undual().decompose()"+String.valueOf(test.value()));
        
        // ipns
        CGAScalarIPNS test3 = new CGAScalarIPNS(4d);
        System.out.println(test3.toString("scalar (ipns)"));
        double[] coordinates = test3.extractCoordinates();
        System.out.println(toString("test3", coordinates));
        CGAScalarOPNS test4 = test3.undual();
        System.out.println(test4.toString("scalar (ipns).undual()"));
        coordinates = test4.extractCoordinates();
        System.out.println(toString("test4", coordinates));
    }
    
    public static String toString(String name, double[] coordinates){
        StringBuilder result = new StringBuilder();
        result.append(name);
        result.append("=(");
        for (int i=0;i<coordinates.length;i++){
            result.append(String.valueOf(coordinates[i]));
            result.append(",");
        }
        result.deleteCharAt(result.length()-1);
        result.append(")");
        return result.toString();
    }
    
    @Test
    public void testIPNS(){
        System.out.println("----------------- test ipns --------------------");
        CGARoundPointIPNS p4 = new CGARoundPointIPNS(new Vector3d(1d,-1d,-2d));
        CGARoundPointIPNS p3 = new CGARoundPointIPNS(new Vector3d(2d,-1d,-2d));
        CGAMultivector m = p4.ip(p4);
        System.out.println(m.toString("p4.p4"));
        CGAMultivector m2 = p4.op(p4);
        System.out.println(m2.toString("p4^p4"));
    }
    
    @Test
    public void testOPNS(){
        System.out.println("----------------- test opns --------------------");
        CGARoundPointOPNS p4 = new CGARoundPointOPNS(new Point3d(1d,-1d,-2d));
        CGARoundPointOPNS p3 = new CGARoundPointOPNS(new Point3d(2d,-1d,-2d));
        CGAMultivector m = p4.ip(p4);
        System.out.println(m.toString("p4.p4"));
        CGAMultivector m2 = p4.op(p4);
        System.out.println(m2.toString("p4^p4"));
    }
    
    public void testFlatPoints(){
        System.out.println("----------------- test flat points --------------------");
        Point3d p = new Point3d(1,2,3);
        CGAFlatPointOPNS fp1 = new CGAFlatPointOPNS(p,1d);
        System.out.println(fp1.toString("flatpoint opns")); // stimmt mit (x+e0).op(einf) überein
        
        CGAFlatPointOPNS fp1a = new CGAFlatPointOPNS((new CGARoundPointIPNS(p)).op(inf));
        //System.out.println(fp1a.toString("flatpoint opns (via round-point-opns)")); // stimmt mit (x+e0).op(einf) überein
        // Überprüfung der beiden opns Formeln im paper
        assertTrue(fp1.equals(fp1a));
        
        System.out.println(fp1.dual().toString("to ipns"));
        System.out.println(fp1.dual().undual().toString("to ipns and back to opns"));
        
        CGAFlatPointIPNS fp2 = new CGAFlatPointIPNS(new Point3d(1,2,3),1d);
        // f1= 2.9999999999999996e124-1.9999999999999996e134+0.9999999999999998e23)
        // f2= 1.0e123+2.9999999999999996e124-1.9999999999999996e134+0.9999999999999998e23)
        System.out.println(fp2.toString("flatpoint ipns"));
        assertTrue(fp2.equals(fp1.dual()));
        
        System.out.println(fp2.undual().toString("to opns"));
        
        //FIXME die dual varianten haben falsches Vorzeichen!
        // muss dual/undual hier anders implementiert werden?
        
        
        // testweise flat-point aus Schnitt von Ebene und Gerade erzeugen
        CGAPlaneOPNS plane = new CGAPlaneOPNS(new Point3d(1,2,3), new Vector3d(0,0,1)).normalize();
        System.out.println(plane.toString("plane"));
        CGALineOPNS line = new CGALineOPNS(new Point3d(1,2,3), new Vector3d(0,0,1)).normalize();
        System.out.println(line.toString("line"));
        CGAFlatPointOPNS m = new CGAFlatPointOPNS(plane.vee(line));
        System.out.println(m.toString("intersec point"));
         // nach ganja.js example c1 bestimmen
        //FIXME I0.negate() ? vorzeichen kontrollieren
        CGAMultivector meuclid = I0.lc(m.op(o));
        // falsches Vorzeichen
        //FIXME
        System.out.println(meuclid.toString("intersec point euclid 1"));
        
        CGAMultivector meuclid2 = o.lc(m).div(m);
        System.out.println(meuclid2.toString("intersec point euclid 2"));
        
    }
    
    /**
     * Determine closest point.
     * 
     * Sollte mindestens für Linien funktionieren die sich schneiden. 
     * - Was passiert bei Linien, die sich nicht schneiden?
     * - scheint bisher nur zu funktionieren, wenn ich "o" als beliebigen Punkt x übergeben
     * - es sieht so aus, als müsste der Punkt in der Ebene liegen in der sich die Geraden schneiden
     * - ist CGARoundPointIPNS x überhaupt richtig?
     * 
     * @param l1 normalized line 1
     * @param l2 normalized line 2
     * @param x any aritray point, use epsilon_0 as default
     * @return closest point on l1 (equals intersection point if the two lines intersect)
     */
    private static CGARoundPointIPNS intersect(CGALineOPNS l1, CGALineOPNS l2, CGARoundPointIPNS x){
        CGAMultivector l1ss = l1.sub(l2.gp(l1).gp(l2));
        CGAMultivector xs = l1ss.gp(x).gp(l1ss);
        CGAMultivector xss = x.add(xs).gp(0.5d);
        CGAMultivector xsss = l2.gp(xss).gp(l2);
        CGAMultivector ps = xss.add(xsss).gp(0.5);
        //return ps.gp(inf).gp(ps).negate().gp(1d/(ps.rc(inf).sqr().decomposeScalar()*2d));
        return new CGARoundPointIPNS(ps.gp(inf).gp(ps).negate().div(ps.rc(inf).sqr().gp(2d)));
    }
    
    @Test
    public void testGrade1Vectors(){
        System.out.println("------------------------- test grade 1 multivectors ---------------------");
        //CGAAttitudeScalarOPNS test = CGAAttitudeScalarOPNS(1d);
        Vector3d v1 = new Vector3d(1,0.5,0);
        Vector3d v2 = new Vector3d(0,1,0.5);
        Vector3d v3 = new Vector3d(0,0,1);
        CGATangentTrivectorOPNS test = new CGATangentTrivectorOPNS(v1,v2,v3);
        System.out.println(test.toString("v (opns)"));
        CGAMultivector test2 = test.dual();
        // v = (0.9999999999999994*eo - 1.1102230246251565E-16*ei)
        System.out.println(test2.toString("v (ipns)"));
    }
    
    public void testOrientedPoint(){
         System.out.println("------------------------------- test oriented points ipns and opns ---------------------");
         Vector3d v1 = new Vector3d(1,0,0);
         Vector3d v2 = new Vector3d(0,1,0);
         Point3d p = new Point3d(1,1,1);
         
         
         // oriented point opns representation
         
         CGAOrientedPointOPNS op = new CGAOrientedPointOPNS(v1,v2,p);
         // op = (0.9999999999999998*eo^e1^e2 + 1.0*e1^e2^e3 + 1.4999999999999998*e1^e2^ei)
         System.out.println(op.toString("op"));
         
         // location
         Point3d pp = op.location();
         // location (decomposed) euclidean only = (0.9999999999999998*e3)
         //FIXME das ist offensichtlich falsch, sollte (1,1,1) sein
         System.out.println("op "+toString("location",pp));
         
         // attitude
         Vector3d v = op.attitude();
         // op attitude = (0.0,0.0,0.9999999999999997)
         // stimmt zumindest für dieses input-data
         System.out.println("op "+toString("attitude",v));
         
         
         // oriented point ipns representation
         
         Vector3d v1xv2 = new Vector3d();
         v1xv2.cross(v1,v2);
         CGAOrientedPointIPNS op1 = new CGAOrientedPointIPNS(p,v1xv2);
         
         // location
         Point3d pp1 = op1.location();
         // op1 location2 = (1.0000000000000004,1.0000000000000004,1.0)
         System.out.println("op1 "+toString("location2",pp1));
         assertTrue(equals(p,pp1));
         
         // attitude
         Vector3d va = op1.attitude();
         assertTrue(equals(va, v1xv2));
         // op1 attitude2 = (0.0,0.0,-1.0) circleIPNS impl
         // attitude orientedPointIPNS = (0.9999999999999991*e1^e2^ei)
         //System.out.println("op1 "+toString("attitude2",va));
    }
    
    @Test
    public void testTangentBivectors(){
        System.out.println("------------------------------- test tangent bivectors ---------------------");
        CGARoundPointIPNS p = new CGARoundPointIPNS(new Vector3d(0d,0d,0d));
        CGASphereOPNS S = (new CGASphereIPNS(p, 1.1d)).undual();
        
        Point3d p1 = new Point3d(1d,-1,-1);
        CGARoundPointIPNS p1c = new CGARoundPointIPNS(p1);
        Point3d p2 = new Point3d(1,1,0d);
        CGARoundPointIPNS p2c = new CGARoundPointIPNS(p2);
        Point3d p3 = new Point3d(1,0d,1);
        CGARoundPointIPNS p3c = new CGARoundPointIPNS(p3);
        CGAPlaneOPNS pl= new CGAPlaneOPNS(p1c,p2c,p3c);
        // pl = (-1.0*eo^e2^e3^ei - 1.0*e1^e2^e3^ei)
        System.out.println(pl.toString("pl"));
        CGAPlaneOPNS pla= new CGAPlaneOPNS(p1,p2,p3);
        assertTrue(pl.equals(pla));
        
        // ohne dual am Ende
        // m = (-0.9999999999999984*eo^e1 - 0.9999999999999987*eo^ei - 0.4999999999999992*e1^ei)
        // mit dual am Ende
        // m = (0.999999999999998*eo^e2^e3 + 0.9999999999999981*e1^e2^e3 + 0.4999999999999989*e2^e3^ei)
        // mit reduziertem Radius immer noch ein tangent-bivector-opns obwohl kein Berührpunkt mehr
        // m = (0.999999999999998*eo^e2^e3 + 0.9999999999999981*e1^e2^e3 + 0.4049999999999992*e2^e3^ei)
        // das scheint mir ein tangent bivector opns zu sein wie erwartet
        // m = (0.9999999999999982*eo^e2^e3 + 0.9999999999999981*e1^e2^e3 + 0.6049999999999989*e2^e3^ei)
        // mit r>1 das scheint immer noch ein bivector zu sein, 
        CGAMultivector m = S.dual().op(pl.dual()).dual();
        System.out.println(m.toString("m"));
        
        CGASphereIPNS Sipns = new CGASphereIPNS(p, 1d);
        CGAMultivector m1 = Sipns.vee(pl.dual());
        // m1 = (0)
        System.out.println(m1.toString("m1"));
    }
    
    @Test
    public void testScrewAxis(){
        System.out.println("------------------------------- test screw-axis additions ---------------------");
        Point3d c1 = new Point3d(5,5,1);
        Vector3d n1 = new Vector3d(1,1,0);
        CGALineIPNS l1 = new CGALineIPNS(c1, n1);
        
        Point3d c2 = new Point3d(5,5,-1);
        Vector3d n2 = new Vector3d(1,0,0);
        CGALineIPNS l2 = new CGALineIPNS(c2, n2);
        
        CGAScrewAxisIPNS screwLine = l1.add(l2);
        //Vector3d moment = screwLine.moment();
    }
    
    @Test
    public void testSpecializationMethod(){
        Point3d center = new Point3d(); 
        
        // sphere ipns
        double radius = 0.3;
        double weight = 1d;
        CGASphereIPNS sphereIPNS = new CGASphereIPNS(center, radius, weight);
        CGASphereIPNS m = (CGASphereIPNS) CGAKVector.specialize(sphereIPNS, true);
        
        // round point ipns
        CGARoundPointIPNS pointIPNS = new CGARoundPointIPNS(center);
        CGARoundPointIPNS m3 = (CGARoundPointIPNS) CGAKVector.specialize(pointIPNS,true);

        // plane ipns
        Point3d p = new Point3d();
        Vector3d n = new Vector3d(1,0,0);
        CGAPlaneIPNS planeIPNS = new CGAPlaneIPNS(p,n);
        CGAPlaneIPNS m4 = (CGAPlaneIPNS) CGAKVector.specialize(planeIPNS, true);

        // flat point ipns
        CGAFlatPointIPNS flatPoint = new CGAFlatPointIPNS(p);
        CGAFlatPointIPNS m5 = (CGAFlatPointIPNS) CGAKVector.specialize(flatPoint, true);
        
        // line ipns
        CGALineIPNS line = new CGALineIPNS(p,n);
        CGALineIPNS m6 = (CGALineIPNS) CGAKVector.specialize(line, true);
        
        // screw ipns
        //TODO
        
        // circle ipns
        Point3d center1 = new Point3d(1,1,1);
        Vector3d normal = new Vector3d(1,0,0);
        double radius1 = 0.3;
        CGACircleIPNS circleIPNS = new CGACircleIPNS(center1, normal, radius1, weight);
        CGACircleIPNS m7 = (CGACircleIPNS) CGAKVector.specialize(circleIPNS, true);

        // oriented point ipns
        //TODO
        
        // point-pair ipns
        Point3d center2 = new Point3d (2,2,2);
        CGAPointPairIPNS pointPairIPNS = new CGAPointPairIPNS(
                new CGARoundPointIPNS(center1), new CGARoundPointIPNS(center2));
        CGAPointPairIPNS m8 = (CGAPointPairIPNS) CGAKVector.specialize(pointPairIPNS, true);
        
        //TODO
        // opns
    }
    
    @Test
    public void testForceAddition(){
        System.out.println("------------------------------- test force additions ---------------------");
        Point3d c1 = new Point3d(5,5,1);
        Vector3d f1 = new Vector3d(1.5,2,3);
        CGAForceIPNS F1 = new CGAForceIPNS(c1, f1);
        System.out.println(F1.toString("F1"));
        
        
        // test force representation as line
        
        Vector3d attitude = F1.attitude();
        //WORKAROUND
        attitude.normalize();
        System.out.println("attitude(F1)="+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z));
        Vector3d forceDir = new Vector3d(f1);
        forceDir.normalize();
        System.out.println("forceDir(f1)="+String.valueOf(forceDir.x)+", "+String.valueOf(forceDir.y)+", "+String.valueOf(forceDir.z));
        assertTrue(equals(attitude, forceDir));
        
        double squeredWeight = F1.squaredWeight();
        System.out.println("squaredWeight(F1)="+String.valueOf(squeredWeight));
        assertTrue(equals(squeredWeight, f1.lengthSquared()));
        
        
        // adding forces to forque
        
        Point3d c2 = new Point3d(-5,-5, -1);
        Vector3d f2 = new Vector3d(-1,-2,-3);
        CGAForceIPNS F2 = new CGAForceIPNS(c2, f2);
        System.out.println(F2.toString("F2"));
        
        
        CGAForqueIPNS forque1 = F1.add(F2);
        System.out.println(forque1.toString("forque1"));
        System.out.println(forque1.flatBulk().toString("flatBulk=moment"));
        Vector3d fb = forque1.flatBulk().extractE3FromE3einf(); //extractE3InfToVector3d();
        //System.out.println(toString("extracted moment",fb));
        Vector3d m1 = new Vector3d();
        m1.cross(new Vector3d(c1),f1);
        Vector3d m2 = new Vector3d();
        m2.cross(new Vector3d(c2),f2);
        Vector3d m = new Vector3d(m1);
        m.add(m2);
        System.out.println(toString("m=m1+m1", m));
        assertTrue(equals(m,fb));
        
        Vector3d f = new Vector3d(f1);
        f.add(f2);
        System.out.println(toString("f=f1+f2", f));
        Vector3d attitudeF = forque1.attitude();
        assertTrue(equals(attitudeF, f));
        //System.out.println(toString("forque1.attitude()", attitudeF));
        
        
        System.out.println(forque1.flatWeight().toString("flatWeight=direction=force"));
    }
}