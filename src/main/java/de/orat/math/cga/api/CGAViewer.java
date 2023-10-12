package de.orat.math.cga.api;

import de.orat.view3d.euclid3dviewapi.api.ViewerService;
import de.orat.view3d.euclid3dviewapi.spi.iEuclidViewer3D;
import java.awt.Color;
import java.util.Optional;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAViewer {
    
    public static Color COLOR_GRADE_1 = Color.RED;    // ipns: sphere, planes, round points
    public static Color COLOR_GRADE_2 = Color.GREEN;  // ipns: lines, ipns circle, oriented points; opns: flat-points, point-pairs
    public static Color COLOR_GRADE_3 = Color.BLUE;   // ipns: point-pairs, (tangent vector), flat-points; opns:circle, line, oriented-points
    public static Color COLOR_GRADE_4 = Color.YELLOW; // (ipns euclidean vector), opns: plane, round-point, spheres

    //TODO
    // nur als Faktoren verwenden und skalieren auf Basis des angezeigten Volumens
    public static float POINT_RADIUS = 0.01f; // in m
    public static float LINE_RADIUS = 0.005f; // in m
    public static float TANGENT_LENGTH = 0.1f*3f; // testweise *3 damit trotz Roboter sichtbar

    private final iEuclidViewer3D impl;
   
    public static Optional<CGAViewer> getInstance(){
         Optional<iEuclidViewer3D> viewer = ViewerService.getInstance().getViewer();
         CGAViewer cgaViewer = null;
         if (viewer.isPresent()){
             cgaViewer = new CGAViewer(viewer.get());
         }
         return Optional.ofNullable(cgaViewer);
    }
    
    CGAViewer(iEuclidViewer3D impl){
        this.impl = impl;
    }
    
    
    
    // grade 1 multivectors

  
    /**
     * Add a point to the 3d view.
     *
     * @param parameters unit is [m]
     * @param isIPNS
     * @param label label or null if no label needed
     */
    void addPoint(iCGATangentOrRound.EuclideanParameters parameters, String label, boolean isIPNS){
        Color color = COLOR_GRADE_1;
        if (!isIPNS) color = COLOR_GRADE_4;
        addPoint(parameters, label, color);
    }
    
    void addPoint(iCGATangentOrRound.EuclideanParameters parameters, String label, Color color){
        
        if (color == null) throw new IllegalArgumentException("color==null not allowed, use method with argument ipns instead!");
        
        Point3d location = parameters.location();
        System.out.println("Add point \""+label+"\" at ("+String.valueOf(location.x)+","+
                        String.valueOf(location.y)+", "+String.valueOf(location.z)+")!");
        location.scale(1000d);
        impl.addSphere(location, POINT_RADIUS*2*1000, color, label);
    }
    
    
    /**
     * Add a sphere to the 3d view.
     *
     * @param parameters unit is [m]
     * @param label
     * @param isIPNS
     */
    void addSphere(iCGATangentOrRound.EuclideanParameters parameters,
                                              String label, boolean isIPNS){
            Color color = COLOR_GRADE_1;
            if (!isIPNS) color = COLOR_GRADE_4;
            addSphere(parameters, label, color);
    }
    void addSphere(iCGATangentOrRound.EuclideanParameters parameters,
                                              String label, Color color){
        
        if (color == null) throw new IllegalArgumentException("color==null not allowed, use method with argument ipns instead!");
        
        Point3d location = parameters.location();
        location.scale(1000d);
        boolean imaginary = false;
        if (parameters.squaredSize() < 0){
            imaginary = true;
        }
        //TODO
        // Farbe ändern für imaginäre Kugeln
        double radius = Math.sqrt(Math.abs(parameters.squaredSize()));
        radius *= 1000;
        System.out.println("Add sphere \""+label+"\" at ("+String.valueOf(location.x)+"mm,"+
                        String.valueOf(location.y)+"mm, "+String.valueOf(location.z)+"mm) with radius "+
                        String.valueOf(radius)+"mm!");

        impl.addSphere(location, radius, color, label);
    }

    /**
     * Add a plane to the 3d view.
     *
     * @param parameters unit is [m]
     * @param label
     * @param isIPNS
     * @param showPolygon 
     * @param showNormal 
     * @return true, if the plane is visible in the current bounding box
     */
    boolean addPlane(iCGAFlat.EuclideanParameters parameters, String label,
                     boolean isIPNS, boolean showPolygon, boolean showNormal){
        Color color = COLOR_GRADE_1;
        if (!isIPNS) color = COLOR_GRADE_4;
        return addPlane(parameters, label, color, showPolygon, showNormal);
    }
    boolean addPlane(iCGAFlat.EuclideanParameters parameters, String label,
                     Color color, boolean showPolygon, boolean showNormal){
        
        if (color == null) throw new IllegalArgumentException("color==null not allowed, use method with argument ipns instead!");
        
        Point3d location = parameters.location();
        location.scale(1000d);
        Vector3d a = parameters.attitude();
        System.out.println("plane "+label+" a=("+String.valueOf(a.x)+", "+String.valueOf(a.y)+", "+String.valueOf(a.z)+
                "), o=("+String.valueOf(location.x)+", "+String.valueOf(location.y)+", "+String.valueOf(location.z)+")");
        boolean result = true;
        if (showPolygon){
            //TODO
            result = true;
            impl.addPlane(location, a, color, label, false);
        }
        // scheint zum Absturz zu führen
        /*if (result && showNormal){
            addArrow(p1, a, TANGENT_LENGTH, 
                         LINE_RADIUS*1000, color, label);
        }*/
        return result;
    }
    
    
    // grade 2

    /**
     * Add a line to the 3d view.
     *
     * @param parameters, unit is [m]
     * @param isIPNS
     * @param label
     * @return true if the line is inside the bounding box and therefore visible
     */
    boolean addLine(iCGAFlat.EuclideanParameters parameters, String label, boolean isIPNS){
        Color color = COLOR_GRADE_2;
        if (!isIPNS) color = COLOR_GRADE_3;
        return addLine(parameters, label, color);
    }
    boolean addLine(iCGAFlat.EuclideanParameters parameters, String label, Color color){
        
        if (color == null) throw new IllegalArgumentException("color==null not allowed, use method with argument ipns instead!");
        
        Point3d p1 = parameters.location();
        p1.scale(1000d);
        Vector3d a = parameters.attitude();
        System.out.println("add line \""+label+"\" at ("+String.valueOf(p1.x)+", "+String.valueOf(p1.y)+
                        ", "+String.valueOf(p1.z)+") with a=("+String.valueOf(a.x)+", "+String.valueOf(a.y)+", "+
                        String.valueOf(a.z)+")");
        
        Point3d p2 = null;
        //TODO
        // bounding box cut in cga und damit die beiden Punkte bestimmen und
        // diese dann übergeben
        impl.addLine(p1, p2, color, LINE_RADIUS*1000,  label);
        //TODO
        return true;
    }
    /**
     * Add oriented-point visualized as point and arrow.
     * 
     * @param parameters, unit is [m]
     * @param label
     * @param isIPNS 
     */
    void addOrientedPoint(iCGATangentOrRound.EuclideanParameters parameters, 
                                                String label, boolean isIPNS){
       Color color = COLOR_GRADE_2;
       if (!isIPNS) color = COLOR_GRADE_3;
       addOrientedPoint(parameters, label, color);
    }
    void addOrientedPoint(iCGATangentOrRound.EuclideanParameters parameters, 
                                                String label, Color color){
       if (color == null) throw new IllegalArgumentException("color==null not allowed, use method with argument ipns instead!");
       
       // p1
       Point3d location = parameters.location();
       location.scale(1000d);
       
       // orientation
       Vector3d direction = parameters.attitude();
       //FIXME soll die length von direction der length der attitude, also dem weight
       // des cga-Objekts entsprechen?
       direction.normalize();
       direction.scale(TANGENT_LENGTH*1000/2d);
       
       // point
       impl.addSphere(location, POINT_RADIUS*2*1000, color, label);
       
       // arrow
       Point3d location2 = new Point3d(location);
       location2.sub(direction);
       direction.scale(2d);
       impl.addArrow(location2, direction, 
                        LINE_RADIUS*1000, color, null);
    }

    /**
     * Add a circle to the 3d view.
     *
     * @param parameters units in [m]
     * @param label name of the circle shown in the visualisation
     * @param isIPNS true, if circle is given in inner-product-null-space representation
     */
    void addCircle(iCGATangentOrRound.EuclideanParameters parameters,
                                              String label, boolean isIPNS){
        Color color = COLOR_GRADE_2;
        if (!isIPNS) color = COLOR_GRADE_3;
        addCircle(parameters, label, color);
    }
    void addCircle(iCGATangentOrRound.EuclideanParameters parameters,
                                              String label, Color color){
        
        boolean isImaginary = false;
        double r2 = parameters.squaredSize();
        if (r2 <0) {
            isImaginary = true;
            System.out.println("Circle \""+label+"\" is imaginary!");
            r2 = -r2;
        }
        double r = Math.sqrt(r2)*1000;
        Point3d location = parameters.location();
        location.scale(1000d);
        Vector3d direction = parameters.attitude();
        
        System.out.println("Add circle \""+label+"\" at ("+String.valueOf(location.x)+"mm,"+
                        String.valueOf(location.y)+"mm, "+String.valueOf(location.z)+"mm) with radius "+
                        String.valueOf(r)+"\"[mm] and n= ("+String.valueOf(direction.x)+","+
                        String.valueOf(direction.y)+", "+String.valueOf(direction.z)+") ");
        impl.addCircle(location, direction, (float) r, color, label, isImaginary);
    }


    // grade 3

    /**
     * Add a point-pair to the 3d view.
     *
     * No imaginary point-pairs, because these are ipns circles.
     *
     * @param pp unit in [m]
     * @param label
     * @param isIPNS true, if ipns representation
     */
    void addPointPair(iCGAPointPair.PointPair pp, String label, boolean isIPNS){
            Color color = COLOR_GRADE_3;
            if (!isIPNS) color = COLOR_GRADE_2;
            Point3d[] points = new Point3d[]{pp.p1(), pp.p2()};
            points[0].scale(1000d);
            points[1].scale(1000d);
            impl.addSphere(points[0],  POINT_RADIUS*2*1000, color, label);
            impl.addSphere(points[1],  POINT_RADIUS*2*1000, color, label);
            //impl.addPointPair(points[0], points[1], label, color, color, LINE_RADIUS*1000, POINT_RADIUS*2*1000);
    }

    /**
     * Add a point-pair to the 3d view.
     *
     * Because parameters are decomposed the point-pair can not be imaginary.
     *
     * @param parameters unit in [m]
     * @param label
     * @param isIPNS true, if ipns represenation
     */
    void addPointPair(iCGATangentOrRound.EuclideanParameters parameters,
                                                     String label, boolean isIPNS){
        Color color = COLOR_GRADE_3;
        if (!isIPNS) color = COLOR_GRADE_2;
        addPointPair(parameters, label, color);
    }
    void addPointPair(iCGATangentOrRound.EuclideanParameters parameters,
                                                     String label, Color color){    
        Point3d l = parameters.location();
        Vector3d att = parameters.attitude();
        System.out.println("pp(tangendRound) \""+label+"\" loc=("+String.valueOf(l.x)+", "+String.valueOf(l.y)+", "+String.valueOf(l.z)+
                  ", att=("+String.valueOf(att.x)+", "+String.valueOf(att.y)+", "+String.valueOf(att.z)+
                "), r2="+String.valueOf(parameters.squaredSize()));
        Point3d[] points = decomposePointPair(parameters);
        System.out.println("pp \""+label+"\" p1=("+String.valueOf(points[0].x)+", "+String.valueOf(points[0].y)+", "+String.valueOf(points[0].z)+
                    ", p2=("+String.valueOf(points[1].x)+", "+String.valueOf(points[1].y)+", "+String.valueOf(points[1].z)+")");

        points[0].scale(1000d);
        points[1].scale(1000d);
        //impl.addPointPair(points[0], points[1], label, color, color, LINE_RADIUS*1000, POINT_RADIUS*2*1000);
        
        impl.addSphere(points[0],  POINT_RADIUS*2*1000, color, label);
        impl.addSphere(points[1],  POINT_RADIUS*2*1000, color, label);
        //TODO
        // eventuell Linie zwischen beiden Punkten
    }
    
    /**
     * Decompose euclidean parameters of a point-pair into two points.
     *
     * Implementation based on determination of location and squared-size.<p>
     *
     * TODO sollte das nicht in CGAPointPair... verschoeben werden?
     * Nein, ich brauche das ja ausserhalb der CGAAPI, aber wohin dann damit?<p>
     * 
     * @param parameters
     * @return the two decomposed points in Point3d[2]
     */
    private static Point3d[] decomposePointPair(iCGATangentOrRound.EuclideanParameters parameters){
        Point3d c = parameters.location();
        double r = Math.sqrt(Math.abs(parameters.squaredSize()));
        Vector3d v = parameters.attitude();
        v.normalize();
        v.scale(r); 
        Point3d[] result = new Point3d[2];
        result[0] = new Point3d(c);
        result[0].add(v);
        result[1] = new Point3d(c);
        result[1].sub(v);
        return result;
    }

    
    /**
     * Add a tangent to the 3d view.
     *
     * @param parameters
     * @param label
     * @param isIPNS
     */
    void addTangentVector(iCGATangentOrRound.EuclideanParameters parameters,
                                                             String label, boolean isIPNS){
            Color color = COLOR_GRADE_3;
            if (!isIPNS) color = COLOR_GRADE_2;
            Vector3d dir = new Vector3d(parameters.attitude());
            dir.normalize();
            dir.scale(TANGENT_LENGTH);
            impl.addArrow(parameters.location(), dir,
                            LINE_RADIUS*1000, color, label);
    }

    public boolean addCGAObject(CGAMultivector m, String label, boolean isIPNS){
        //TODO
        // wenn generischer Multivektor, diesen analysieren um herauszufinden welcher
        // Subtyp das sein sollte und dann in diesen casten
        // d.h. dieser Code muss hier raus und zuvor überprüft werden ...
        return false;
    }
    /**
     * Add cga object into the visualization.
     *
     * @param m cga multivector
     * @param label label
     * @return true if the given object can be visualized, false if it is outside 
     * the axis-aligned bounding box and this box is not extended for the given object
     * @throws IllegalArgumentException if multivector is no visualizable type
     */
    public boolean addCGAObject(CGAKVector m, String label){
         return addCGAObject(m, label, null);
    }
    public boolean addCGAObject(CGAKVector m, String label, Color color){

        // cga ipns objects
        if (m instanceof CGARoundPointIPNS roundPointIPNS){
            //TODO decompose bestimmt auch eine Attitude, was bedeutet das für einen round-point?
            // brauche ich das überhaupt?
            // decompose bestimmt zuerst auch einen round-point, aber den hab ich ja bereits
            //FIXME
            if (color == null){
                addPoint(roundPointIPNS.decompose(), label, true);
            } else {
                addPoint(roundPointIPNS.decompose(), label, color);
            }
            return true;
        } else if (m instanceof CGALineIPNS lineIPNS){
            if (color == null){
                return addLine(lineIPNS.decomposeFlat(), label, true);
            } else {
                return addLine(lineIPNS.decomposeFlat(), label, color);
            }
        } else if (m instanceof CGAPointPairIPNS pointPairIPNS){
            //addPointPair(m.decomposeTangentOrRound(), label, true);
            
            iCGATangentOrRound.EuclideanParameters parameters = pointPairIPNS.decompose();
            double r2 = parameters.squaredSize();
            //double r2 = pointPairIPNS.squaredSize();
            if (r2 < 0){
                //FIXME
                // decomposition schlägt fehl
                // show imaginary point pairs as circles
                //CGACircleIPNS circle = new CGACircleIPNS(pointPairIPNS);
                //addCircle(pointPairIPNS.decomposeTangentOrRound(), label, true);
                //return true;
                System.out.println("Visualize imaginary point pair \""+label+"\" failed!");
                return false;
            // tangent vector
            } else if (r2 == 0){
                System.out.println("CGA-Object \""+label+"\" is a tangent vector - not yet supported!");
                return false;

            // real point pair only?
            //FIXME
            } else {
                //ddPointPair(cGAPointPairIPNS.decomposePoints(), label, true);
                //iCGATangentOrRound.EuclideanParameters parameters = pointPairIPNS.decompose();
                Point3d loc = parameters.location();
                System.out.println("pp \""+label+"\" loc=("+String.valueOf(loc.x)+", "+String.valueOf(loc.y)+", "+String.valueOf(loc.z)+
                        " r2="+String.valueOf(parameters.squaredSize()));
                
                if (color == null){
                    addPointPair(parameters, label, true);
                } else {
                    addPointPair(parameters, label, color);
                }  
                // scheint zum gleichen Ergebnis zu führen
                //iCGAPointPair.PointPair pp = pointPairIPNS.decomposePoints();
                //addPointPair(pp, label, true);
                //double r_ = pp.p1().distance(pp.p2())/2;
                //System.out.println("Visualize real point pair \""+label+"\"with r="+String.valueOf(r_));
                return true;
            }
            
        } else if (m instanceof CGASphereIPNS sphereIPNS){
            if (color == null){
                addSphere(sphereIPNS.decompose(), label, true);
            } else {
                addSphere(sphereIPNS.decompose(), label, color);
            }
            return true;
        } else if (m instanceof CGAPlaneIPNS planeIPNS){
            if (color == null){
                return addPlane(planeIPNS.decomposeFlat(), label, true, true, true);
            } else {
                return addPlane(planeIPNS.decomposeFlat(), label, color, true, true);
            }
        } else if (m instanceof CGAOrientedPointIPNS orientedPointIPNS){
            if (color == null){
                addOrientedPoint(orientedPointIPNS.decompose(), label, true);
            } else {
                addOrientedPoint(orientedPointIPNS.decompose(), label, color);
            }
            return true;
        } else if (m instanceof CGACircleIPNS circleIPNS){
            if (color == null){
                addCircle(circleIPNS.decompose(), label, true);
            } else {
                addCircle(circleIPNS.decompose(), label, color);
            }
            return true;
        }
        //TODO
        // flat-point
        // attitude/free vector dashed/stripled arrow at origin
        // tangent vector solid arrow at origin?
        
        // cga opns objects
        if (m instanceof CGARoundPointOPNS roundPointOPNS){
            if (color == null){
                addPoint(roundPointOPNS.decompose(), label, false);
            } else {
                addPoint(roundPointOPNS.decompose(), label, color);
            }
            return true;
        } else if (m instanceof CGALineOPNS lineOPNS){
            if (color == null){
                addLine(lineOPNS.decomposeFlat(), label, false);
            } else {
                addLine(lineOPNS.decomposeFlat(), label, color);
            }
            return true;
        } else if (m instanceof CGAPointPairOPNS pointPairOPNS){
            iCGATangentOrRound.EuclideanParameters parameters = pointPairOPNS.decompose();
            
            if (color == null){
                addPointPair(parameters, label, false);
            } else {
                addPointPair(parameters, label, color);
            }
            //iCGAPointPair.PointPair pp = pointPairOPNS.decomposePoints();
            //addPointPair(pp, label, false);
            return true;
        } else if (m instanceof CGASphereOPNS sphereOPNS){
            if (color == null){
                addSphere(sphereOPNS.decompose(), label, false);
            } else {
                addSphere(sphereOPNS.decompose(), label, color);
            }
            return true;
        } else if (m instanceof CGAPlaneOPNS planeOPNS){
            if (color == null){
                addPlane(planeOPNS.decomposeFlat(), label, false, true, true);
            } else {
                addPlane(planeOPNS.decomposeFlat(), label, color, true, true);
            }
            return true;
        } else if (m instanceof CGACircleOPNS circleOPNS){
            if (color == null){
                addCircle(circleOPNS.decompose(), label, false);
            } else {
                addCircle(circleOPNS.decompose(), label, color);
            }
            return true;
        } else if (m instanceof CGAOrientedPointOPNS orientedPointOPNS){
            if (color == null){
                addOrientedPoint(orientedPointOPNS.decompose(), label, false);
            } else {
                addOrientedPoint(orientedPointOPNS.decompose(), label, color);
            }
            return true;
        }
        //TODO
        // flat-point als Würfel darstellen

        
        throw new IllegalArgumentException("\""+m.toString("")+"\" has unknown type!");
    }
}
