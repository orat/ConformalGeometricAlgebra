package de.orat.math.cga.impl1;

import de.orat.math.cga.api.CGAMultivector;
import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.ga.basis.Metric;
import de.orat.math.ga.metric.MetricException;
import java.util.HashMap;
import java.util.Map;

/**
 * CGA basis vectors and metric.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGA1Metric {
    
    public static final int IPNS = 0; // standard representation (inner product null space)
    public static final int OPNS = 1; // dual representation (outer product null space)
 
    //public static enum BaseVectorNames {"eo", "e1", "e2", "e3", "ei"};
    
    // ehemals n0, e1, e2, e3, ni
    public static final String[] baseVectorNames = {"eo", "e1", "e2", "e3", "ei"};
    
    // e0, e1, e2, e3, einf
    // e0=0.5*(e- - e+) und einf=(e- + e+)
    //FIXME
    // muss ich das 0.5 nicht noch irgendwie in der Matrix berücksichtigen?
    // corresponding to D. Hildenbrand2013, Kleppe2016
    public static Metric CGA_METRIC;
    static {
        try {
            // e-*e- = -1, e+*e+=1
            // ?
            // Metric: [-1.0, 0.9999999999999998, 1.0, 1.0, 1.0]
            // null-basis
            CGA_METRIC = new Metric(new double[][]{
                {0.0, 0.0, 0.0, 0.0, -1.0},
                {0.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 1.0, 0.0},
                {-1.0, 0.0, 0.0, 0.0, 0.0}});
        } catch (MetricException e){}
    }
    
    // representational space - Minkovski space
    // e0, e1, e2, e3, einf
    // e0=(e- - e+) und einf=e- + e+)
    public static Metric CGA1_METRIC;
    static {
        try {
            // Metric: [-1.0, 0.9999999999999998, 1.0, 1.0, 1.0]
            // null-basis
            CGA1_METRIC = new Metric(new double[][]{
                {0.0, 0.0, 0.0, 0.0, -1.0},
                {0.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 1.0, 0.0},
                {-1.0, 0.0, 0.0, 0.0, 0.0}});
        } catch (MetricException e){}
    }
    
    // e+, e1, e2, e3, e-
    // non-degenerate basis
    public static Metric CGA2_METRIC;
    static {
        try {
            CGA2_METRIC = new Metric(new double[][]{
                {1.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, -1.0}});
        } catch (MetricException e){}
    }
    private static CGA1Metric instance = null;
    
    // key= bitset as int, value = index in the multivector
    private final Map<Integer, Integer> bitsetMap = new HashMap<>();
    
    // key=index in the multivector, value = index = the grade coordinates
    private final Map<Integer, Integer> gradeCoordinatesMap = new HashMap<>();
    
    // besser hier die Dimension n übergeben und dann die Tabellen automatisch passend
    // generieren statt hier die Fixkodierung. Dann kann ich das auch allg. IndexTable nennen
    //FIXME
    static CGA1Metric getInstance(){
        if (instance == null){
            instance = new CGA1Metric();
        }
        return instance;
    }
    
    int getIndex(int bitset){
        return bitsetMap.get(bitset);
    }
    int getIndex(int bitset, int grade){
        return gradeCoordinatesMap.get(bitsetMap.get(bitset));
    }
    
    public static String[] basisBladeNames(){
        String[] result = new String[]{"","e0", "e1","e2","e3","einf","e01","e02","e03","e0i","e12","e13","e1i","e23","e2i","e3i","e012","e013",
        "e01i","e023","e02i","e03i","e123","e12i","e13i","e23i","e0123","e012i","e013i","e023i","e123i","e0123i"};
        return result;
    }
    /**
     * Create index table for CGA.
     * 
     * 03/22 Oliver Rettig
     * Better implement generic functionality with dimension as parameter.
     * 
     * @Deprecated 
     */
    private CGA1Metric(){
        // scalar
        bitsetMap.put(0,0);
        gradeCoordinatesMap.put(0,0);
        // vectors
        bitsetMap.put(1,1);
        gradeCoordinatesMap.put(1,0); // e0, n0 // gaalop e1
        bitsetMap.put(2,2);
        gradeCoordinatesMap.put(2,1); // e1 // gaalop e2
        bitsetMap.put(4,3);
        gradeCoordinatesMap.put(3,2); // e2 // gaalop e3
        bitsetMap.put(8,4);
        gradeCoordinatesMap.put(4,3); // e3 // gaalop ni
        bitsetMap.put(16,5);
        gradeCoordinatesMap.put(5,4); // einf, ni // gaalop n0
        // bivectors
        bitsetMap.put(1+2,6);
        gradeCoordinatesMap.put(6,0); // e01
        bitsetMap.put(1+4,7);
        gradeCoordinatesMap.put(7,1); // e02
        bitsetMap.put(1+8,8);
        gradeCoordinatesMap.put(8,2); // e03
        bitsetMap.put(1+16,9);
        gradeCoordinatesMap.put(9,3); // e0i
        bitsetMap.put(2+4,10); // e12
        gradeCoordinatesMap.put(10,4);
        bitsetMap.put(2+8,11); // e13
        gradeCoordinatesMap.put(11,5);
        bitsetMap.put(2+16,12); // e1i
        gradeCoordinatesMap.put(12,6);
        bitsetMap.put(4+8,13); // e23
        gradeCoordinatesMap.put(13,7);
        bitsetMap.put(4+16,14); // e2i
        gradeCoordinatesMap.put(14,8);
        bitsetMap.put(8+16,15); // e3i
        gradeCoordinatesMap.put(15,9);
        //trivectors
        bitsetMap.put(1+2+4,16); //e012
        gradeCoordinatesMap.put(16,0);
        bitsetMap.put(1+2+8,17); //e013
        gradeCoordinatesMap.put(17,1);
        bitsetMap.put(1+2+16,18); //e014
        gradeCoordinatesMap.put(18,2);
        bitsetMap.put(1+4+8,19); //e023
        gradeCoordinatesMap.put(19,3);
        bitsetMap.put(1+4+16,20); //e02i
        gradeCoordinatesMap.put(20,4);
        bitsetMap.put(1+8+16,21); //e03i
        gradeCoordinatesMap.put(21,5);
        bitsetMap.put(2+4+8,22); //e123
        gradeCoordinatesMap.put(22,6);
        bitsetMap.put(2+4+16,23); // e12i
        gradeCoordinatesMap.put(23,7);
        bitsetMap.put(2+8+16,24); // e13i
        gradeCoordinatesMap.put(24,8);
        bitsetMap.put(4+8+16,25); // e23i
        gradeCoordinatesMap.put(25,9);
        //quatvectors
        bitsetMap.put(1+2+4+8,26);
        gradeCoordinatesMap.put(26,0); // e0123
        bitsetMap.put(1+2+4+16,27);
        gradeCoordinatesMap.put(27,1); // e012i
        bitsetMap.put(1+2+8+16,28);
        gradeCoordinatesMap.put(28,2); // e013i
        bitsetMap.put(1+4+8+16,29);
        gradeCoordinatesMap.put(29,3); // e023i
        bitsetMap.put(2+4+8+16,30);
        gradeCoordinatesMap.put(30,4); // e123i
        // pseudoscalar
        bitsetMap.put(1+2+4+8+16,31); // e0123i
        gradeCoordinatesMap.put(31,0);
    }
    
    // scheint zu stimmen
    // braucht es hier vermutlich gar nicht? der faktor hängt aber von der Metrik ab?
    /**
     * Squared distance between two points.
     * 
     * Hildenbrand1998 page 35
     * 
     * @param p1 normalized first point
     * @param p2 normalized second point
     * @return squared distance between two normalized points
     */
    public static double squareDistanceBetweenPoints(CGAMultivector p1, CGAMultivector p2){
        return -2*p1.scp(p2);
    }
    
    
    // base vector creation dependend on the used metric
    
    /**
     * Create origin base vector.
     * 
     * @param scale
     * @return origin base vector.
     */
    public static iCGAMultivector createOrigin(double scale){
        return CGA1Multivector.createBasisVector(0, scale);
    }
    public static iCGAMultivector createEx(double scale){
        return CGA1Multivector.createBasisVector(1, scale);
    }
    public static iCGAMultivector createEy(double scale){
        return CGA1Multivector.createBasisVector(2, scale);
    }
    public static iCGAMultivector createEz(double scale){
        return CGA1Multivector.createBasisVector(3, scale);
    }
    public static iCGAMultivector createBasisInf(double scale){
        return CGA1Multivector.createBasisVector(4, scale);
    }
}