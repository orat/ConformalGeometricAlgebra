package de.orat.math.cga.test;

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
import de.orat.math.cga.api.CGASphereIPNS;
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
    public void testGanjaExampleCreatePointsCircleLine(){
        System.out.println("------------------Ganja.js expample: creation of points, circle, line --------------");
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(new Vector3d(1d,0d,0d));
        // p1=1.0*eo + 1.0*e1 + 0.5*ei (korrekt)
        System.out.println("p1="+p1.toString());
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(new Vector3d(0d,1d,0d));
        // p2=1.0*eo + 1.0*e2 + 0.5*ei (korrekt)
        System.out.println("p2="+p2.toString());
        CGARoundPointIPNS p3 = new CGARoundPointIPNS(new Vector3d(0d,0d,1d));
        // p3=1.0*eo + 1.0*e3 + 0.5*ei (korrekt)
        System.out.println("p3="+p3.toString());
        
        CGAOrientedCircleOPNS c = new CGAOrientedCircleOPNS(p1,p2,p3);
        // c=1.0*eo^e1^e2 - 1.0*eo^e1^e3 + 1.0*eo^e2^e3 + 1.0*e1^e2^e3 
        // + 0.5*e1^e2^ei - 0.5*e1^e3^ei + 0.5*e2^e3^ei (korrekt)
        System.out.println("c="+c.toString());
        
        CGALineOPNS l = new CGALineOPNS(p1,p2);
        // l=-1.0*eo^e1^ei + 1.0*eo^e2^ei + 1.0*e1^e2^ei (korrekt)
        System.out.println("l="+l.toString());
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
    
    public void testGanjaExampleCreateDualSpherePlane(){
        System.out.println("------------------Ganja.js expample: creation of dual sphere and plane --------------");
        // We start by defining a null basis, and upcasting for points
        //var ni = 1e4+1e5, no = .5e5-.5e4, nino = ni^no;
        //var up = (x)=> no + x + .5*x*x*ni;

        // var p1 = up(1e1+.5e2)
        CGARoundPointIPNS p1 = new CGARoundPointIPNS(new Vector3d(1d,0.5d,0d));
        // ganja.js: e1+0.5e2+0.12e4+1.12e5 = e1+0.5e2+0.625ei+e0 (korrekt)
        // java.js: p1=1.0*eo + 1.0*e1 + 0.5*e2 + 0.625*ei
        System.out.println("p1="+p1.toString());
        
        // p2 = up(1e2-.5e3);
        CGARoundPointIPNS p2 = new CGARoundPointIPNS(new Vector3d(0d,1d,0.5d));
        // java: p2=1.0*eo + 1.0*e2 + 0.5*e3 + 0.625*ei
        // ganja.js: e2-0.5e3+0.12e4+1.12e5
        System.out.println("p2="+p2.toString());
        
        // The distance between two points.
        //var d = (a,b)=>((a<<b).Length*2)**.5;
        
        // The duality operator can be used to produce a sphere from a point.
        // var s = ()=>!(p1 - .3**2*.5*ni);
        // ganja.js: -1.08e1234 - 0.07e1235 - 0.5e1345 + e2345
        // s=-eo^e1^e2^e3 + 0.5*eo^e1^e3^ei 
        //   -eo^e2^e3^ei - 0.625*e1^e2^e3^ei
        CGASphereIPNS s1 = new CGASphereIPNS(p1, 0.3);
        // java: s1=1.0*eo + 1.0*e1 + 0.5*e2 + 0.58*ei (korrekt)
        System.out.println("s1="+s1.toString());
        CGASphereOPNS s = s1.dual();
        System.out.println("s="+s.toString());
        
        CGAMultivector inf_no = createInf(1d).op(createOrigin(1d));
        // Planes also have a direct dual representation. (but start from vector not point)
        // var p1 = ()=>!(d(p2,no)*ni + (p2^nino*nino).Normalized);
        
        CGAMultivector n = ((new CGARoundPointIPNS(p2)).op(inf_no).gp(inf_no)).normalize();
        // n=-0.8944271909999159*e2 - 0.4472135954999579*e3
        System.out.println("n="+n.toString());
        CGAPlaneOPNS p = (new CGAPlaneIPNS((CGAMultivector.createInf(Math.sqrt(p2.distSquare(new CGARoundPointIPNS(CGAMultivector.createOrigin(1d)))))).add(
                        n))).dual();
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
        
        //S  = ()=>!(p1-.5*ni),                 // main dual sphere around point 
        CGASphereOPNS S = (new CGASphereIPNS(p, 1d)).dual();
        // java: S=-eo^e1^e2^e3 + 0.49*e1^e2^e3^ei
        // ganja.js e1235 = 0.5e123i-e0123 (korrekt)
        System.out.println("S="+S.toString());
        
        //S2 = !(up(-1.4e1)-0.125*ni),         // left dual sphere
        CGASphereOPNS S2 = (new CGASphereIPNS(new CGARoundPointIPNS(new Vector3d(-1.4,0d,0d)), 0.5d)).dual();
        // ganja.js: -1.35e1234-0.35e1235-1.39e2345 = -0.85e123i - e0123 +1.4e023i
        // java: S2=-eo^e1^e2^e3 + 1.399*eo^e2^e3^ei - 0.85*e1^e2^e3^ei (korrekt)
        System.out.println("S2="+S2.toString());
        
        CGAPlaneIPNS plane = new CGAPlaneIPNS(new Vector3d(0d,0d,1d), 0d);
        System.out.println("plane="+plane.toString());
        //C  = !(up(1.4e1)-.125*ni)&!(1e3),    // right circle
        CGAOrientedCircleOPNS C = new CGAOrientedCircleOPNS(S2.vee(plane.dual()));
        // ganja.js: 1.35e124+0.35e125+1.39e245 = 1.3e02i + 0.85e12i -e012 (korrekt)
        // java: C=-eo^e1^e2 - 1.34*eo^e2^ei + 0.85*e1^e2^ei
        System.out.println("C="+C.toString());
        
        //L  = up(.9e2)^up(.9e2-1e1)^ni,       // top line
        // ganja.js: 0.89e124+0.89e125-e145 = -e01i+0.9e12i
        // java: l=-1.0*eo^e1^ei + 0.9*e1^e2^ei (korrekt)
        CGALineOPNS L = new CGALineOPNS(new Point3d(0d,0.9d,0d), new Point3d(-1d,0.9d,0d));
        System.out.println("L="+L.toString());
        
        //P  = !(1e2-.9*ni),                   // bottom dual plane
        // ganja.js: 0.89e1234+0.89e1235-e1345 = e013i + 0.9e123i (korrekt)
        // P=5.551115123125783E-17*eo^e1^e2^e3 + eo^e1^e3^ei + 0.9*e1^e2^e3^ei
        CGAPlaneOPNS P = (new CGAPlaneIPNS(new Vector3d(0d,1d,0d), -0.9d)).dual();
        System.out.println("P="+P.toString());
        
        //P2 = !(1e1+1.7*ni);                  // right dual plane
        // ganja.js: -1.70e1234-1.70e1235+e2345 = -1.7e123i - e023i (korrekt)
        // P2=-1.1102230246251565E-16*eo^e1^e2^e3 - eo^e2^e3^ei - 1.7*e1^e2^e3^ei
        CGAPlaneOPNS P2 = (new CGAPlaneIPNS(new Vector3d(1d,0d,0d), 1.7d)).dual();
        System.out.println("P2="+P2.toString());
        
        // The intersections of the big sphere with the other 4 objects.
        //var C1 = ()=>S&P sphere meets plane
        CGAOrientedCircleOPNS C1 = new CGAOrientedCircleOPNS(S.vee(P));
        // ganja.js: 0.89e123+e135 = 0.89e123 + 0.5e13i + e013 (korrekt)
        // java: C1=eo^e1^e3 + 0.9*e1^e2^e3 + 0.49*e1^e3^ei
        System.out.println("C1="+C1.toString());
        
        // C2 = ()=>S&L 
        CGAOrientedPointPairOPNS pp = new CGAOrientedPointPairOPNS(S.vee(L));
        // java: s&l=eo^e1 - 0.89*e1^e2 - 0.5*e1^ei
        // ganja.js: -0.89e12-e15 = 0.89e12-0.5e1i + e01 (korrekt)
        System.out.println("s&l="+pp.toString());
        
        // C3 = ()=>S&S2 sphere meet sphere
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
        
        // C5 = ()=>C&P2;  circle meet plane
        CGAOrientedPointPairOPNS C5 = new CGAOrientedPointPairOPNS(C.vee(P2));
        // ganja.js: 1.7e12-1.02e24-2.02e25 = 1.7e12 
        // java: c&p1=eo^e2 + 1.7*e1^e2 + 3.23*e2^ei
        //FIXME stimmt nur in einer Komponente überein
        //TODO vermutlich sind bereits der Ausgangs-circle oder plane flasch --> überprüfen
        System.out.println("c&p="+C5.toString());
        
        // For line meet plane its a bit more involved.
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
        // sphere = (P,r)=>!(P-r**2*.5*ni),
        // plane  = (v,h=0)=>!(v-h*ni);

        // Project and reject.      
        // var project_point_on_round            = (point,sphere)=>-point^ni<<sphere<<sphere,
        // project_point_on_flat             = (point,plane)=>up(-point<<plane<<plane^nino*nino),
        // plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;

        // var p1  = up(.5e2),                     // point
        CGARoundPointIPNS p = new CGARoundPointIPNS(new Vector3d(0d,0.5d,0d));
        // ganja.js: 0.5e2-0.37e4+0.62e5 = e0 + 0.5e2 + 0.125ei (korrekt)
        // java p1=1.0*eo + 0.5*e2 + 0.125*ei
        System.out.println("p="+p.toString());
        
        // S  = sphere(up(-1.4e1),.5),            // left sphere
        // sphere = (P,r)=>!(P-r**2*.5*ni)
        CGASphereOPNS S = new CGASphereOPNS((new CGASphereIPNS(new Point3d(-1.4d,0d,0d),0.5)).dual());
        // java S*=1.0*eo - 1.4*e1 + 0.85*ei
        // java S=-eo^e1^e2^e3 + 1.34*eo^e2^e3^ei - 0.856*e1^e2^e3^ei
        // ganja: -1.35e1234-0.35e1235-1.39e2345 = -0.8e123i-e0123+1.4e023i (korrekt)
        System.out.println("S="+S.toString());
        
        // sphere = (P,r)=>!(P-r**2*.5*ni)
        // plane  = (v,h=0)=>!(v-h*ni);
        // C  = sphere(up(1.4e1),.5)&plane(1e3),  // right circle
        CGASphereOPNS sphere = (new CGASphereIPNS(new Point3d(1.4d,0d,0d),0.5d)).dual();
        System.out.println("sphere="+sphere.toString());
        // sphere=-eo^e1^e2^e3 - 1.34*eo^e2^e3^ei - 0.8545*e1^e2^e3^ei
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
        
       
        // plane  = (v,h=0)=>!(v-h*ni);
        // P  = plane(1e2,.9);                    // bottom plane
        CGAPlaneOPNS P = (new CGAPlaneIPNS(new Vector3d(0d,1d,0d),0.9d)).dual();
        // ganja.js: 0.89e1234+0.89e1235-e1345
        // java: P*=1.0*e2 + 0.9*ei
        // java eo^e1^e3^ei - 0.9*e1^e2^e3^ei (korrekt)
        System.out.println("P="+P.toString());
        
        // project point on sphere
        // var project_point_on_round            = (point,sphere)=>-point^ni<<sphere<<sphere
        // ()=>project_point_on_round(p1,S), "p1 on S"
        CGAOrientedPointPairOPNS pOnS = S.project(p);
        // ganja.js: 0.7e12-1.89e14-0.49e15+0.30e24+0.80e25-1.95e45 = 0.7e12-1.19e1i-1.44e01+0.55e2i-0.5e02+1.95e0i
        // java POnS=-1.4*eo^e1 - 0.5*eo^e2 + 0.7*e1^e2 + 1.96*eo^ei - 1.197*e1^ei + 0.557*e2^ei (korrekt)
        System.out.println("POnS="+pOnS.toString());
        
        //()=>project_point_on_round(~p1,C), "p1 on C",   // point on circle
        // java.lang.IllegalArgumentException: The given multivector is not not grade 1! grade()=2
        CGAOrientedPointPairOPNS pOnC = C.project(new CGARoundPointIPNS(p.conjugate()));
        // ganja.js -0.70e12 + 1.89e14+0.49e15+0.30e24+0.80e25-1.95e4 = -0.7e12+1.19e1i-1.44e01+0.55e2i-0.5e02
        // java pOnC=1.4*eo^e1 - 0.5*eo^e2 - 0.7*e1^e2 + 1.96*eo^ei + 1.197*e1^ei + 0.553*e2^ei
        System.out.println("pOnC="+pOnC.toString()); // (korrekt)
        
        // project_point_on_flat             = (point,plane)=>up(-point<<plane<<plane^nino*nino),
        
        // ()=>project_point_on_flat(p1,P),   "p1 on P",   // point on plane
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
        
        
        // ()=>plane_through_point_tangent_to_x(p1,S),    // plane through p1 tangent to S
        // tangent() error da falscher grade
        //FIXME
        //CGADualPlane pToS = S.tangent(p1);
        // ganja.js 0.25e1234+0.25e1235+0.5e1345-1.39e2345
        // java pToS=0.0724999999999999*e1^e3
        //System.out.println("pToS="+pToS.toString());
        //TODO
        
        // ()=>plane_through_point_tangent_to_x(p1,P),    // plane through p1 tangent to P
        CGAPlaneOPNS pToP = P.tangent(p);
        System.out.println("pToP="+pToP.toString());
        // java pToP=-0.19999999999999984*e1^e3
        // ganja.js -0.5e1234-0.5e1235-e1345
        //TODO
        
        // Graph the items. (hex numbers are html5 colors, two extra first bytes = alpha)
        // document.body.appendChild(this.graph([ 
        //      0x00FF0000, p1, "p1",                                       // point 
        //      0x00008800, ()=>project_point_on_round(p1,S), "p1 on S",    // point on sphere
        //      0x000000FF, ()=>project_point_on_round(~p1,C), "p1 on C",   // point on circle
        //  0x00888800, ()=>project_point_on_flat(p1,P),   "p1 on P",   // point on plane
        //  0x00008888, ()=>project_point_on_flat(~p1,L),  "p1 on L",   // point on line
        //  0xc0FF0000, ()=>plane_through_point_tangent_to_x(p1,S),    // plane through p1 tangent to S2
        //  0xc000FF00, ()=>plane_through_point_tangent_to_x(p1,P),    // plane through p1 tangent to P
        //  0,L,0,C,                                                  // line and circle
        //  0xE0008800, P,                                            // plane
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
        System.out.println("einf*e0="+m.toString());
        // ist -1 also korrekt!!!!
    }
    
    /**
     * Input:
     * n=(0.0,0.0, 1.0), d=2
     * 
     * plane=1.0*e3 + 2.0*ei
     * probe=1.0*eo + 0.5*e1 + 0.5*e2 + 0.5*e3 + 0.375*ei
     * attitude=-5.551115123125783E-17*eo^e1^e2 + 0.9999999999999996*e1^e2^ei
     * location=(2.500000000000001, 2.500000000000001, 1.2500000000000004)
     * n=(0.0, 0.0, 0.9999999999999996)
     * 
     * aktueller Output
     * plane=2.0*e3 + 2.0*ei
attitude_cga=Infinity*e3
location_cga=2.0000000000000004*eo + 0.20000000000000007*e1 + 0.20000000000000007*e2 - 0.010000000000000004*e3 + 0.010000000000000004*ei
location2=(0.20000000000000007, 0.20000000000000007, -0.010000000000000004)
nn=(0.0, 0.0, 0.0)
     */
    public void testPlane(){
        System.out.println("---------------------- plane ----");
        Vector3d n = new Vector3d(0d,0d,1d);
        double d = 2d;
        System.out.println("n=("+String.valueOf(n.x)+","+String.valueOf(n.y)+", "+String.valueOf(n.z)+"), d="+String.valueOf(d));
        
        CGAPlaneIPNS plane = new CGAPlaneIPNS(n, d);
        // plane=1.0*e3 + 2.0*ei
        System.out.println("plane="+plane.toString());
        
        CGAPlaneIPNS plane1 = new CGAPlaneIPNS(new Point3d(0d,0d,2d));
        // plane1=-1.0*e3 + 2.0000000000000004*ei
        //FIXME Vorzeichenfehler bei e3 egal welche normalize()-Methode ich verwende
        System.out.println("plane1="+plane1.toString());
        
        //CGAPoint cp = new CGARoundPointIPNS(new Point3d(0.0d,0.0d,2.0d));
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
        CGASphereIPNS sphere1 = new CGASphereIPNS(p1, radius);
        System.out.println("sphere1="+sphere1.toString());
        
        Point3d p2 = new Point3d(1.02,1.02,1);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGASphereIPNS sphere2 = new CGASphereIPNS(p2, radius);
        System.out.println("sphere2="+sphere2.toString());
        
        CGAOrientedCircleIPNS circle = new CGAOrientedCircleIPNS(sphere1.op(sphere2));
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
        Point3d p1 = new Point3d(0d,-1d,0d);
        System.out.println("Test opns compose/decompose:");
        System.out.println("p1=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        CGARoundPointIPNS cgaPoint = new CGARoundPointIPNS(p1);
        System.out.println("p1_cga="+cgaPoint.toString());
        Point3d p2 = new Point3d(1d,0d,0d);
        Point3d p3 = new Point3d(0d,1d,0d);
        Point3d p4 = new Point3d(0d,0d,1d);
        CGASphereOPNS cgaDualSphere = new CGASphereOPNS(p1,p2,p3,p4);
        System.out.println("cgaDualSphere="+cgaDualSphere.toString());
        CGASphereIPNS cgaSphere = cgaDualSphere.undual();
        System.out.println("cgaSphere="+cgaSphere.toString());
        RoundAndTangentParameters rp1 = cgaDualSphere.decompose(); 
        System.out.println("Decompose dual sphere:");
        System.out.println("radius = "+String.valueOf(Math.sqrt(Math.abs(rp1.squaredSize())))); 
        System.out.println("radius2squared = "+String.valueOf(Math.abs(rp1.squaredSize()))); 
        System.out.println("location1 = ("+String.valueOf(rp1.location().x)+", "+
                String.valueOf(rp1.location().y)+", "+String.valueOf(rp1.location().z)+")"); // location1 = (0.0, 0.0, -1.6225927682921321E31)
        
        System.out.println("Test ipns compose/decompose:");
        double radius = 2d;
        System.out.println("input radius="+String.valueOf(radius));
        CGASphereIPNS sphere = new CGASphereIPNS(p1, radius);
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
    
    public void testDecomposeLocation(){
        System.out.println("--------------- decompose location -------");
        
        Point3d p1 = new Point3d(0.02,0.02,1);
        System.out.println("p1=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        
        Point3d p2 = new Point3d(0.02,0.02,2);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        
        Point3d p3 = new Point3d(0.5,0.02,2);
        System.out.println("p3=("+String.valueOf(p3.x)+","+String.valueOf(p3.y)+","+String.valueOf(p3.z)+")");
        
        
        // test IPNS representations
        
        // test RoundPoints
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1, 2d);
        System.out.println("cp1="+cp1.toString());
        //RoundAndTangentParameters decomposed1 = cp1.decompose();
        Point3d location = cp1.location();
        System.out.println("loc(cp1) = ("+String.valueOf(location.x)+", "+String.valueOf(location.y)+", "+
                String.valueOf(location.z)+")");
        
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2, 2d);
        System.out.println("cp2="+cp2.toString());
        //RoundAndTangentParameters decomposed2 = cp2.decompose();
        Point3d location2 = cp2.location();
        System.out.println("loc(cp2) = ("+String.valueOf(location2.x)+", "+String.valueOf(location2.y)+", "+
                String.valueOf(location2.z)+")");
        
        // point-pairs
        CGAOrientedPointPairIPNS pp1 = new CGAOrientedPointPairIPNS(cp1,cp2);
        //RoundAndTangentParameters decomposed3 = pp1.decompose();
        // loc(pp1) = (Infinity, Infinity, -Infinity) (ERROR)
        //FIXME
        Point3d location3 = pp1.location();
        System.out.println("loc(pp1) = ("+String.valueOf(location3.x)+", "+String.valueOf(location3.y)+", "+
                String.valueOf(location3.z)+")");
        
        // spheres
        CGASphereIPNS s1 = new CGASphereIPNS(p1, 2d);
        System.out.println("s1="+s1.toString());
        //RoundAndTangentParameters decomposed4 = s1.decompose();
        Point3d location4 = s1.location();
        System.out.println("loc(s1) = ("+String.valueOf(location4.x)+", "+String.valueOf(location4.y)+", "+
                String.valueOf(location4.z)+")");
        
        CGASphereIPNS s2 = new CGASphereIPNS(p2, 3d);
        System.out.println("s2="+s2.toString());
        //RoundAndTangentParameters decomposed5 = s2.decompose();
        Point3d location5 = s2.location();
        System.out.println("loc(s2) = ("+String.valueOf(location5.x)+", "+String.valueOf(location5.y)+", "+
                String.valueOf(location5.z)+")");
        
        // circles
        CGAOrientedCircleIPNS c1 = new CGAOrientedCircleIPNS(s1,s2);
        // c1=1.0*eo^e3 + 0.02*e1^e3 + 0.02*e2^e3 - 1.0*eo^ei - 0.02*e1^ei - 0.02*e2^ei + 0.49960000000000004*e3^ei
        System.out.println("c1="+c1.toString());
        //RoundAndTangentParameters decomposed6 = c1.decompose();
        Point3d location6 = c1.location();
        // loc(c1) = (Infinity, Infinity, 0.0) (ERROR)
        //FIXME
        System.out.println("loc(c1) = ("+String.valueOf(location6.x)+", "+String.valueOf(location6.y)+", "+
                String.valueOf(location6.z)+")");
        
        
        // opns representations
        
        //points
        CGARoundPointOPNS cp1b = new CGARoundPointOPNS(p1);
        System.out.println("cp1b="+cp1b.toString());
        //RoundAndTangentParameters decomposed7 = cp1b.decompose();
        Point3d location7 = cp1b.location();
        System.out.println("loc(cp1b) = ("+String.valueOf(location7.x)+", "+String.valueOf(location7.y)+", "+
                String.valueOf(location7.z)+")");
        
        // point pairs
        CGAOrientedPointPairOPNS pp2 = new CGAOrientedPointPairOPNS(p1,p2);
        // pp=1.0*eo^e3 + 0.02*e1^e3 + 0.02*e2^e3 + 1.5*eo^ei + 0.030000000000000002*e1^ei + 0.030000000000000002*e2^ei + 0.9996*e3^ei
        System.out.println("pp2="+pp2.toString());
        // FIXME e1 und e2 die doppelt so grosse Werte wie erwartet
        // locationFromTangentAndRound=3.250000000000001*eo + 0.06500000000000002*e1 + 0.06500000000000002*e2 + 1.4994000000000005*e3 + 5.088522196198628E-19*eo^e1^e3 + 5.088522196198628E-19*eo^e2^e3 - 0.9996*ei + 7.632783294297935E-19*eo^e1^ei + 7.632783294297935E-19*eo^e2^ei - 3.7001512964707234E-16*eo^e3^ei - 6.9375061251264494E-18*e1^e3^ei - 6.9375061251264494E-18*e2^e3^ei
        //RoundAndTangentParameters decomposed8 = pp2.decompose();
        Point3d location8 = pp2.location();
        System.out.println("loc(pp2) = ("+String.valueOf(location8.x)+", "+String.valueOf(location8.y)+", "+
                String.valueOf(location8.z)+")");
        
        // spheres
        CGASphereOPNS s1b = new CGASphereOPNS(p1, 2d);
        System.out.println("s1b="+s1b.toString());
        //RoundAndTangentParameters decomposed9 = s1b.decompose();
        Point3d location9 = s1b.location();
        System.out.println("loc(s1b) = ("+String.valueOf(location9.x)+", "+String.valueOf(location9.y)+", "+
                String.valueOf(location9.z)+")");
        
        // circles
        CGAOrientedCircleOPNS c1b = new CGAOrientedCircleOPNS(p1,p2,p3);
        System.out.println("c1b="+c1b.toString());
        //RoundAndTangentParameters decomposed11 = c1b.decompose();
        Point3d location10 = c1b.location();
        System.out.println("loc(c1b) = ("+String.valueOf(location10.x)+", "+String.valueOf(location10.y)+", "+
                String.valueOf(location10.z)+")");
    }
        
    public void testPoint(){
        System.out.println("--------------- point -------");
        Point3d p = new Point3d(0.02,0.02,1);
        System.out.println("p=("+String.valueOf(p.x)+","+String.valueOf(p.y)+","+String.valueOf(p.z)+")");
        CGARoundPointIPNS cp = new CGARoundPointIPNS(p, 2d);
        System.out.println("cp="+cp.toString());
        
        CGAMultivector testgp = cp.gp(cp);
        // P*P=9.00480064 Warum eigentlich?
        System.out.println("P*P="+testgp);
        CGAMultivector testgp2 = cp.ip(cp);
        // P.P=1.6653345369377348E-15 (korrekt) sollte praktisch 0 sein
        System.out.println("P.P="+testgp2);
        
        // squared squaredWeight = 4, korrekt 
        System.out.println("squaredWeight1(sollte 4 sein)="+String.valueOf(cp.squaredWeight()));
        
        RoundAndTangentParameters decomposed = cp.decompose();
        Vector3d a1 = decomposed.attitude();
        System.out.println("attitude=("+String.valueOf(a1.x)+", "+String.valueOf(a1.y)+", "+String.valueOf(a1.z)+")");
        
        Point3d p1 = decomposed.location(); // ok input = (0.02,0.02,1)
        // location lua=0.019999999999999993*e1 + 0.019999999999999993*e2 + 0.9999999999999996*e3
        // locationFromTangentAndRound=1.0000000000000002*eo + 0.020000000000000004*e1 + 0.020000000000000004*e2 + 1.0000000000000002*e3 + 0.5004000000000001*ei

        // location=(0.020000000000000004,0.020000000000000004,1.0000000000000002) (korrekt)
        System.out.println("location=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        

        // 2.251 sollte aber 0 sein.
        double squaredSize = decomposed.squaredSize();
        System.out.println("squaredSize="+String.valueOf(squaredSize));
        //CGAMultivector attitude = cp.determineDirectionFromTangentAndRoundObjectsAsMultivector();
        //CGAPoint probePoint = new CGARoundPointIPNS(new Point3d(0d,0d,0d));
        //double squaredWeight = CGAMultivector.squaredWeight(attitude, probePoint);
        double squaredWeight = cp.squaredWeight();
        System.out.println("squaredWeight="+String.valueOf(squaredWeight));
        Point3d p2 = new Point3d(2.02,0.02,1);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
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
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println("cp1="+cp1.toString());
        //System.out.println("cp1.unit="+cp1.unit().toString(CGA1Metric.baseVectorNames));
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println("cp2="+cp2.toString());
        
        double result = CGA1Metric.squareDistanceBetweenPoints(cp1, cp2);
        System.out.println("distsquare="+result);
    }
    
    public void testLine1(){
        System.out.println("-------------- line1 --------");
        Point3d p1 = new Point3d(1d,1d,0d);
        double weight1 = 2d;
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1, weight1);
        System.out.println("cp1="+cp1); // stimmt
        
        Point3d p2 = new Point3d(1d,0d,1d);
        double weight2 = -1d;
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2, weight2);
        System.out.println("cp2="+cp2); // stimmt
        
        System.out.println("distSquare="+String.valueOf(cp2.distSquare(cp1))); // 2 stimmt
        
        CGALineOPNS ldual = new CGALineOPNS(cp1, cp2);
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
        
        CGALineIPNS line = ldual.undual();
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
        CGARoundPointIPNS cp1 = new CGARoundPointIPNS(p1);
        System.out.println("cp1="+cp1);
        
        Point3d p2 = new Point3d(1,0.02,1);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGARoundPointIPNS cp2 = new CGARoundPointIPNS(p2);
        System.out.println("cp2="+cp2);
        
        Vector3d n = new Vector3d(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        System.out.println("n1=("+String.valueOf(n.x)+","+String.valueOf(n.y)+","+String.valueOf(n.z)+")");
        
        CGALineOPNS l1dual = new CGALineOPNS(p1, p2);
        // dual line represented as tri-vector
        // l1dual= 0.98*no^e1^ni - 0.0196*e1^e2^ni - 0.98*e1^e3^ni
        System.out.println("l1*= "+l1dual);
      
        CGALineIPNS l1 = l1dual.undual();
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
        //CGA1Multivector l1dual = new CGA1Multivector(l1dual.dual(CGA1Metric.CGA_METRIC));
        
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
}