package de.orat.math.cga;

import de.orat.math.cga.api.CGACircleIPNS;
import de.orat.math.cga.api.CGAEuclideanVector;
import de.orat.math.cga.api.CGAKVector;
import de.orat.math.cga.api.CGALineOPNS;
import de.orat.math.cga.api.CGAMultivector;
import static de.orat.math.cga.api.CGAMultivector.inf;
import static de.orat.math.cga.api.CGAMultivector.o;
import de.orat.math.cga.api.CGAPlaneIPNS;
import de.orat.math.cga.api.CGAPointPairIPNS;
import de.orat.math.cga.api.CGARoundPointIPNS;
import de.orat.math.cga.api.CGASphereIPNS;
import de.orat.math.cga.api.CGAViewer;
import java.util.Optional;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class DemoView {
 
    public static void main(String[] args){
        Optional<CGAViewer> viewer = CGAViewer.getInstance();
        if (viewer.isPresent()){
            CGAViewer viewer3D = viewer.get();
            
            CGARoundPointIPNS pm = new CGARoundPointIPNS(new Point3d(1, 0.3, -0.7));
            viewer3D.addCGAObject(pm, "pm");
            CGARoundPointIPNS pp = new CGARoundPointIPNS(new Point3d(1, 1, 1));
             viewer3D.addCGAObject(pp, "pp");
            
            CGARoundPointIPNS p1 = new CGARoundPointIPNS(new Point3d(1, 0.3, -0.2));
            //viewer3D.addCGAObject(p1, "p1");
            CGARoundPointIPNS p2 = new CGARoundPointIPNS(new Point3d(-1, -0.3, -0.2));
            viewer3D.addCGAObject(p2, "p2");
            CGALineOPNS L1 = (new CGALineOPNS(p1, p2)).normalize();
            viewer3D.addCGAObject(L1, "L1");
            
            //CGARoundPointIPNS midPoint = p1.midPoint(p2);
            //viewer3D.addCGAObject(midPoint, "midPoint(p1,p2)");
            
            CGARoundPointIPNS p3 = new CGARoundPointIPNS(new Point3d(0.3, 1, 1));
            //viewer3D.addCGAObject(p3, "p3");
            CGARoundPointIPNS p4 = new CGARoundPointIPNS(new Point3d(-0.3, -1, -1));
            viewer3D.addCGAObject(p4, "p4");
            CGALineOPNS L2 = (new CGALineOPNS(p3, p4)).normalize();
            viewer3D.addCGAObject(L2, "L2");
            
            // Inverse hat keinen Effekt gezeigt
            CGALineOPNS L21 = new CGALineOPNS(L2.gp(L1).gp(L2/*.inverse()*/));
            System.out.println(L21.toString("L2")); // normalize ändert nichts
            
            viewer3D.addCGAObject(L21, "L21");
            CGALineOPNS L212 = new CGALineOPNS(L1.gp(L21).gp(L1));
            //CGALineOPNS L212 = new CGALineOPNS(L1.sub(L21)); // senkrecht zu L2 wenn sich L1 und L2 schneiden
            System.out.println(L212.toString("L212")); // trivector aber keine line? att schlägt fehl
            viewer3D.addCGAObject(L212, "L212");
           
            /*
            CGARoundPointIPNS a = new CGARoundPointIPNS(L1.gp(p4).gp(L1));
            CGARoundPointIPNS midPoint1 = a.midPoint(new CGARoundPointIPNS(o)); // Punkt auf L1
            viewer3D.addCGAObject(midPoint1, "midPoint(o,L1oL1)");
            CGARoundPointIPNS b = new CGARoundPointIPNS(L2.gp(midPoint1).gp(L2));
            CGARoundPointIPNS midPoint2 = midPoint1.midPoint(b); // Punkt auf L2
            viewer3D.addCGAObject(midPoint2, "P2 midPoint(midpoint1, b)");
            CGARoundPointIPNS c = new CGARoundPointIPNS(L1.gp(midPoint2).gp(L1));
            CGARoundPointIPNS midPoint3 = midPoint2.midPoint(c);
            viewer3D.addCGAObject(midPoint3, "P1 midPoint(midpoint2, c)");
           */
                   
            CGAMultivector L1L2 = L1.gp(L2);
            CGAMultivector X = L1L2.sub(o.ip(L1L2).op(inf));
            CGAMultivector Y = o.ip(L1L2);
            
            CGAMultivector X2 = X.extractGrade(2);
            CGAEuclideanVector P = new CGAEuclideanVector(X.extractGrade(0).gp(Y.extractGrade(3)).
                    sub(Y.extractGrade(1).gp(X2)).div(X2.sqr()));
            System.out.println(P.toString("P")); // normaler 3d-Vector
            CGARoundPointIPNS PA = new CGARoundPointIPNS(P);
            viewer3D.addCGAObject(PA, "PA"); // Punkt auf der Lotlinie
            
        } else {
            System.out.println("No viewer implementation found!");
        }
    }
    
   
    
    public void todo(){
         
        Optional<CGAViewer> viewer = CGAViewer.getInstance();
        if (viewer.isPresent()){
            CGAViewer viewer3D = viewer.get();
            
            Point3d center = new Point3d(); 
            
            // add sphere ipns
            double radius = 0.3;
            double weight = 1d;
            CGASphereIPNS sphereIPNS = new CGASphereIPNS(center, radius, weight);
            //viewer3D.addCGAObject(sphereIPNS, "sphere");
            CGASphereIPNS m = (CGASphereIPNS) CGAKVector.specialize(sphereIPNS, true);
            viewer3D.addCGAObject(m, "sphere");
            
            // add plane ipns
            Point3d p = new Point3d();
            Vector3d n = new Vector3d(1,0,0);
            CGAPlaneIPNS planeIPNS = new CGAPlaneIPNS(p,n);
            //FIXME Diese Ebene wird falsch dargestellt, clipping funktioniert nicht richtig
            viewer3D.addCGAObject(planeIPNS,"plane");
            
            // add point ipns
            CGARoundPointIPNS pointIPNS = new CGARoundPointIPNS(center);
            viewer3D.addCGAObject(pointIPNS, "point");
            
            // add circle ipns
            Point3d center1 = new Point3d(1,1,1);
            Vector3d normal = new Vector3d(1,0,0);
            double radius1 = 0.3;
            CGACircleIPNS circleIPNS = new CGACircleIPNS(center1, normal, radius1, weight);
            viewer3D.addCGAObject(circleIPNS,"circle");
            
            // add point-pair ipns
            Point3d center2 = new Point3d (2,2,2);
            CGAPointPairIPNS pointPairIPNS = new CGAPointPairIPNS(
                    new CGARoundPointIPNS(center1), new CGARoundPointIPNS(center2));
            viewer3D.addCGAObject(pointPairIPNS,"pp");
        }
    }
}
