package de.orat.math.cga;

import de.orat.math.cga.api.CGACircleIPNS;
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
            Point3d center = new Point3d(); 
            
            // add sphere ipns
            double radius = 0.3;
            double weight = 1d;
            CGASphereIPNS sphereIPNS = new CGASphereIPNS(center, radius, weight);
            viewer3D.addCGAObject(sphereIPNS, "sphere");
            
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
            
        } else {
            System.out.println("No viewer implementation found!");
        }
    }
}
