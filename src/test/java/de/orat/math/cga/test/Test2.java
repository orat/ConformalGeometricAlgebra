package de.orat.math.cga.test;

import de.orat.math.cga.impl1.CGA1Multivector;
import de.orat.math.cga.impl1.CGA1Utils;
import static de.orat.math.cga.impl1.CGA1Utils.CGA2_METRIC;
import static de.orat.math.cga.impl1.CGA1Utils.CGA_METRIC;
import de.orat.math.cga.util.Decomposition3d;
import de.orat.math.cga.util.Decomposition3d.FlatAndDirectionParameters;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import de.orat.math.ga.basis.Multivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    
    public Test2() {
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
    
    public void testPlane(){
        System.out.println("---------------------- plane ----");
        Vector3d n = new Vector3d(0d,0d,1d);
        double d = 2d;
        CGA1Multivector plane = CGA1Multivector.createPlane(n, d);
        System.out.println("n=("+String.valueOf(n.x)+","+String.valueOf(n.y)+", "+String.valueOf(n.z)+")");
        System.out.println("plane="+plane.toString(CGA1Utils.baseVectorNames));
        CGA1Multivector cp = CGA1Multivector.createPoint(new Point3d(0.5d,0.5d,0.5d));
        System.out.println("probe="+cp.toString(CGA1Utils.baseVectorNames));
        FlatAndDirectionParameters flat = plane.decomposeFlat(cp);
        System.out.println("location=("+String.valueOf(flat.location().x)+", "+
                String.valueOf(flat.location().y)+", "+String.valueOf(flat.location().z)+")");
        System.out.println("n=("+String.valueOf(flat.attitude().x)+", "+
                String.valueOf(flat.attitude().y)+", "+String.valueOf(flat.attitude().z)+")");
    }
    public void testSphere(){
        System.out.println("----------------- sphere -----");
        Point3d p = new Point3d(0.02,0.02,1);
        System.out.println("p=("+String.valueOf(p.x)+","+String.valueOf(p.y)+","+String.valueOf(p.z)+")");
        double radius = 2d;
        System.out.println("radius="+String.valueOf(radius));
        CGA1Multivector sphere = CGA1Multivector.createSphere(p, radius);
        System.out.println("sphere="+sphere.toString(CGA1Utils.baseVectorNames));
        
        // radius = 2.061455835083547 das ist auch falsch
        RoundAndTangentParameters rp = sphere.decomposeRound();
        System.out.println("radius = "+String.valueOf(Math.sqrt(Math.abs(rp.squaredSize()))));
        
        // radius = 1.7318198520631412 das ist falsch
        RoundAndTangentParameters rp2 = sphere.decomposeSphere();
        System.out.println("radius2 = "+String.valueOf(Math.sqrt(Math.abs(rp2.squaredSize()))));
        System.out.println("radius2squared = "+String.valueOf(Math.abs(rp2.squaredSize())));
        
        // weight bestimmen
        double weight = CGA1Multivector.decomposeWeight(sphere.decomposeTangentAndRoundDirectionAsMultivector(), 
                CGA1Multivector.createBasisVectorOrigin(1d));
        // weight=0.9999999999999989 richtig
        System.out.println("weight="+String.valueOf(weight));
        
        // origin = (0.02000000000000001, 0.02000000000000001, 1.0000000000000004) stimmt
        System.out.println("location = ("+String.valueOf(rp.location().x)+", "+
                String.valueOf(rp.location().y)+", "+String.valueOf(rp.location().z)+")");
        
        // Dorst2007: -einf*P = 1 stimmt? soll das die Normierung sein?
        System.out.println("-einf*sphere = "+
                String.valueOf(-CGA1Multivector.createBasisVectorEinf(1d).scp(sphere)));
        // norm(p) = 2 ist sollte das nicht 1 sein?
        System.out.println("norm(sphere) = "+String.valueOf(sphere.norm()));
        
    }
    // scheint zu funktionieren
    public void testPoint(){
        System.out.println("--------------- point -------");
        Point3d p = new Point3d(0.02,0.02,1);
        System.out.println("p=("+String.valueOf(p.x)+","+String.valueOf(p.y)+","+String.valueOf(p.z)+")");
        CGA1Multivector cp = CGA1Multivector.createPoint(p);
        System.out.println("cp="+cp.toString(CGA1Utils.baseVectorNames));
        Vector3d a1 = cp.decomposeRound().attitude();
        System.out.println("attitude=("+String.valueOf(a1.x)+", "+String.valueOf(a1.y)+", "+String.valueOf(a1.z)+")");
        Point3d p1 = cp.decomposeRound().location();
        System.out.println("location=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        // sollte aber 0 sein.
        // squaredSize=-2.2512001600000007
        double squaredSize = cp.decomposeRound().squaredSize();
        System.out.println("squaredSize="+String.valueOf(squaredSize));
        // weight sollte auch 1 sein
        // weight=0.9999999999999976
        CGA1Multivector attitude = cp.decomposeTangentAndRoundDirectionAsMultivector();
        CGA1Multivector probePoint = CGA1Multivector.createPoint(new Point3d(0d,0d,0d));
        double weight = CGA1Multivector.decomposeSquaredWeight(attitude, probePoint);
        System.out.println("weight="+String.valueOf(weight));
    }
    
    // scheint zu funktionieren
    // TODO Vergleich mit festen Ergebniszahlen einbauen etc.
    public void testSquareDistanceBetweenPoints(){
        System.out.println("--------------- square dist -------");
        Point3d p1 = new Point3d(0.02,0.02,1);
        Point3d p2 = new Point3d(2,0.02,1);
        System.out.println("distsquare="+String.valueOf(p2.distanceSquared(p1)));
        
        // die beiden Multivektoren brauchen scheinbar nicht normalisiert zu werden
        CGA1Multivector cp1 = CGA1Multivector.createPoint(p1);
        System.out.println("cp1="+cp1.toString(CGA1Utils.baseVectorNames));
        //System.out.println("cp1.unit="+cp1.unit().toString(CGA1Utils.baseVectorNames));
        CGA1Multivector cp2 = CGA1Multivector.createPoint(p2);
        System.out.println("cp2="+cp2.toString(CGA1Utils.baseVectorNames));
        
        double result = CGA1Utils.squareDistanceBetweenPoints(cp1, cp2);
        System.out.println("distsquare="+result);
    }
    
    /**
     * @Test2
     */
    public void testLine() {
        System.out.println("-------------- line --------");
        /*Multivector no = Multivector.createBasisVectorOrigin(1d);
        Multivector e1 = Multivector.createBasisVectorEx(1d);
        Multivector e2 = Multivector.createBasisVectorEy(1d);
        Multivector e3 = Multivector.createBasisVectorEz(1d);
        Multivector ni = Multivector.createBasisVectorEinf(1d);*/
        
        Point3d p1 = new Point3d(0.02,0.02,1);
        System.out.println("p1=("+String.valueOf(p1.x)+","+String.valueOf(p1.y)+","+String.valueOf(p1.z)+")");
        CGA1Multivector cp1 = CGA1Multivector.createPoint(p1);
        System.out.println("cp1="+cp1.toString(CGA1Utils.baseVectorNames));
        
        Point3d p2 = new Point3d(1,0.02,1);
        System.out.println("p2=("+String.valueOf(p2.x)+","+String.valueOf(p2.y)+","+String.valueOf(p2.z)+")");
        CGA1Multivector cp2 = CGA1Multivector.createPoint(p2);
        System.out.println("cp2="+cp2.toString(CGA1Utils.baseVectorNames));
        
        Vector3d n = new Vector3d(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        System.out.println("n1=("+String.valueOf(n.x)+","+String.valueOf(n.y)+","+String.valueOf(n.z)+")");
        
        CGA1Multivector l1dual = CGA1Multivector.createDualLine(p1, p2);
        // dual line represented as tri-vector
        // l1dual= 0.98*no^e1^ni - 0.0196*e1^e2^ni - 0.98*e1^e3^ni
        System.out.println("l1(dual)= "+l1dual.toString(CGA1Utils.baseVectorNames));
      
        CGA1Multivector l1 = l1dual.undual();
        // line represented as bivector
        // 5.551115123125783E-17*no^e2 - 1.734723475976807E-18*no^e3 + 0.9799999999999993*e2^e3 + 0.9799999999999995*e2^ni - 0.019599999999999985*e3^ni
        // 0.979993*e2^e3 + 0.979999995*e2^ni - 0.0195999985*e3^ni
        System.out.println("l1= "+l1.toString(CGA1Utils.baseVectorNames));
        
        FlatAndDirectionParameters flatParameters = l1dual.decomposeDualFlat(CGA1Multivector.createPoint(new Point3d()));
        Vector3d attitude = flatParameters.attitude();
        // sollte (0.98,0.0,0.0) sein
        // ist aber attitude=-5.551115123125785E-17*e2 + 5.7777898331617076E-34*e3
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
        CGA1Multivector cp1 = CGA1Multivector.createPoint(p1);
        
        Point3d p2 = new Point3d(1,0.02,1);
        CGA1Multivector cp2 = CGA1Multivector.createPoint(p2);
        
        Vector3d n1 = new Vector3d(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z);
        System.out.println("n1=("+String.valueOf(n1.x)+","+String.valueOf(n1.y)+","+String.valueOf(n1.z)+")");
       
        CGA1Multivector l1 = CGA1Multivector.createDualLine(p1, p2);
        System.out.println("l1= "+l1.toString(CGA1Utils.baseVectorNames));
        System.out.println("l1 normiert= "+l1.unit().toString(CGA1Utils.baseVectorNames));
        // ipns representation
        //CGA1Multivector l1dual = new CGA1Multivector(l1dual.dual(CGA1Utils.CGA_METRIC));
        
        Point3d p3 = new Point3d(0.02,0.02,2);
        Point3d p4 = new Point3d(1,1,2.2);
        Vector3d n2 = new Vector3d(p4.x-p3.x, p4.y-p3.y, p4.z-p3.z);
        System.out.println("n2=("+String.valueOf(n2.x)+","+String.valueOf(n2.y)+","+String.valueOf(n2.z)+")");
       
        System.out.println("alpha = "+String.valueOf(n2.angle(n1)*180d/Math.PI));
        
        Vector3d cross = new Vector3d();
        cross.cross(n1, n2);
        System.out.println("cross=("+String.valueOf(cross.x)+","+String.valueOf(cross.y)+","+String.valueOf(cross.z)+")");
       
        CGA1Multivector l2 = CGA1Multivector.createDualLine(p3, p4);
        System.out.println("l2= "+l2.toString(CGA1Utils.baseVectorNames));
        System.out.println("l2 normiert= "+l1.unit().toString(CGA1Utils.baseVectorNames));
        CGA1Multivector l2l1 = new CGA1Multivector(l2.gp(l1).unit());
        // bi- und trivector Anteile
        // l2l1= -2.87728 + 0.21520800000000018*no^e1 - 0.019208*no^e2 + 
        //                  2.87728*e1^e2 + 0.95648*no^e3 + 0.15766240000000017*e1^e3 + 
        //                  0.0383376*e2^e3 
        //                  - 0.9604*no^e1^e2^e3

        //System.out.println("l2l1= "+l2l1.unit().toString(CGA1Utils.baseVectorNames));
        
        CGA1Utils.decomposeLinePair(l2l1);
    }
}