package de.orat.math.cga.utils;

import de.orat.math.cga.api.CGAEuclideanBivector;
import de.orat.math.cga.api.CGAScrew;
import de.orat.math.cga.api.CGARotor;
import de.orat.math.cga.api.CGASpinor;
import de.orat.math.cga.api.CGATranslator;
import java.util.ArrayList;
import java.util.List;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class DHUtils {
    
    public record DH(double d, double theta, double r, double alpha){};
    
    /**
     * Creates a list/sequence of tranformations corresponding to the given
     * list of Denavit-Hartenberg segments.
     * 
     * @param segments list of Denavit-Hartenberg segments/parameter sets
     * @return one transformation for each line of DH-parameters
     */
    public static List<Matrix4d> to(DH[] segments){
        List<CGAScrew> chain = buildChain(segments);
        chain = getSimplified(chain);
        return toMatrix4d(chain);
    }
    
    private static List<Matrix4d> toMatrix4d(List<CGAScrew> chain){
        List<Matrix4d> result = new ArrayList<>();
        for (CGAScrew motor: chain){
            result.add(motor.decompose4PointAndVector());
        }
        return result;
    }
    
    private static List<CGAScrew> buildChain(DH[] segments){
        List<CGAScrew> chain = new ArrayList<>();
        CGAEuclideanBivector Bz = new CGAEuclideanBivector(new Vector3d(1d,0d,0d), new Vector3d(0d,1d,0d));
        CGAEuclideanBivector Bx = new CGAEuclideanBivector(new Vector3d(0d,1d,0d), new Vector3d(0d,0d,1d));

        for (int i=0;i<segments.length;++i){
          DH dh = segments[i];
          CGARotor R = new CGASpinor(Bz, dh.theta());
          CGATranslator T = new CGATranslator(new Vector3d(0d, 0d, dh.d()));
          chain.add(new CGAScrew(T,R));

          R = new CGASpinor(Bx, dh.alpha());
          T = new CGATranslator(new Vector3d(dh.r(), 0d, 0d));
          chain.add(new CGAScrew(T,R));
        }
        return chain;
    }
    
    private static List<CGAScrew> getSimplified(List<CGAScrew> chain){
        List<CGAScrew> result = new ArrayList<>();
        // add first active (theta, d) trafo
        result.add(new CGAScrew(chain.get(0)));

        for (int i=1;i<chain.size()-1; i+=2){
          // add passive trafo (alpha, r) * next active trafo (theta, d)
          CGAScrew m = new CGAScrew(chain.get(i));
          result.add(new CGAScrew(m.transform(chain.get(i+1))));
        }

        // add last passive trafo (alpha, r)
        result.add(chain.get(chain.size()-1));
        return result;
    }
}
