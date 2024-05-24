package de.orat.math.cga.utils;

import de.orat.math.cga.api.CGAEuclideanBivector;
import de.orat.math.cga.api.CGAMotor;
import de.orat.math.cga.api.CGARotor;
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
        List<CGAMotor> chain = buildChain(segments);
        chain = getSimplified(chain);
        return toMatrix4d(chain);
    }
    
    private static List<Matrix4d> toMatrix4d(List<CGAMotor> chain){
        List<Matrix4d> result = new ArrayList<>();
        for (CGAMotor motor: chain){
            result.add(motor.toMatrix4d());
        }
        return result;
    }
    
    private static List<CGAMotor> buildChain(DH[] segments){
      List<CGAMotor> chain = new ArrayList<>();
      CGAEuclideanBivector Bz = new CGAEuclideanBivector(new Vector3d(1d,0d,0d), new Vector3d(0d,1d,0d));
      CGAEuclideanBivector Bx = new CGAEuclideanBivector(new Vector3d(0d,1d,0d), new Vector3d(0d,0d,1d));
        
      for (int i=0;i<segments.length;++i){
        DH dh = segments[i];
        CGARotor R = new CGARotor(Bz, dh.theta());
        //chain.add(R);
        
        CGATranslator T = new CGATranslator(new Vector3d(0d, 0d, dh.d()));
        //chain.add(T);
       
        chain.add(new CGAMotor(R, T));
        
        R = new CGARotor(Bx, dh.alpha());
        //chain.add(R);
        
        T = new CGATranslator(new Vector3d(dh.r(), 0d, dh.d()));
        //chain.add(T);
        
         chain.add(new CGAMotor(R, T));
      }
      return chain;
    }
    
    private static List<CGAMotor> getSimplified(List<CGAMotor> chain){
      List<CGAMotor> result = new ArrayList<>();
      // add first active (theta, d) trafo
      result.add(new CGAMotor(chain.get(0)));

      for (int i=1;i<chain.size()-1; i+=2){
        // add passive trafo (alpha, r) * next active trafo (theta, d)
        CGAMotor m = new CGAMotor(chain.get(i));
        result.add(m.gp(chain.get(i+1)));
      }

      // add last passive trafo (alpha, r)
      result.add(chain.get(chain.size()-1));
      return result;
    }
}
