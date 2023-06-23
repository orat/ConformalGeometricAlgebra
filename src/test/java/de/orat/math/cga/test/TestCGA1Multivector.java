package de.orat.math.cga.test;

import de.orat.math.cga.api.CGAKVector;
import de.orat.math.cga.api.CGAMultivector;
import de.orat.math.cga.api.CGAMultivector;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class TestCGA1Multivector {
    
     public void testEuclideanVector(){
         double[] values = new double[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
         CGAMultivector m = new CGAMultivector(values);
         // m = (1.0*eo + 2.0*e1 + 3.0*eo^e1 + 4.0*e2 + 5.0*eo^e2 + 6.0*e1^e2 + 
         // 7.0*eo^e1^e2 + 8.0*e3 + 9.0*eo^e3 + 10.0*e1^e3 + 11.0*eo^e1^e3 + 
         // 12.0*e2^e3 + 13.0*eo^e2^e3 + 14.0*e1^e2^e3 + 15.0*eo^e1^e2^e3 + 
         // 16.0*ei + 17.0*eo^ei + 18.0*e1^ei + 19.0*eo^e1^ei + 20.0*e2^ei + 
         // 21.0*eo^e2^ei + 22.0*e1^e2^ei + 23.0*eo^e1^e2^ei + 24.0*e3^ei + 
         // 25.0*eo^e3^ei + 26.0*e1^e3^ei + 27.0*eo^e1^e3^ei + 28.0*e2^e3^ei + 
         // 29.0*eo^e2^e3^ei + 30.0*e1^e2^e3^ei + 31.0*eo^e1^e2^e3^ei)
         
         System.out.println(m.toString("internal representation"));
         
         // 0.0,1.0,2.0,4.0,8.0,16.0,3.0,5.0,9.0,17.0,6.0,10.0,18.0,12.0,20.0,24.0,
         // 7.0,11.0,19.0,13.0,21.0,25.0,14.0,22.0,26.0,28.0,15.0,23.0,27.0,29.0,30.0,31.0,
         // s, e0, e1, e2, e3, einf, e01, e02, e03, e0inf, e12, e13, e1inf, e23, e2inf, e3inf
         // e012, e013, e01inf, e023, e02inf, e03inf, e123, e12inf, e13inf, e23inf, e0123, e012inf, e013inf, e023inf, e123inf, e0123inf
         double[] extractedValues = m.extractCoordinates();
         System.out.print("extracted values=");
         for (int i=0;i<extractedValues.length;i++){
             System.out.print(String.valueOf(extractedValues[i]));
             System.out.print(",");
         }
         System.out.println();
         
         
         for (int i=0;i<values.length;i++){
             assertTrue(Test2.equals(values[i], extractedValues[i]));
         }
         
         // setCoordinates:
         // m = (1.0*eo + 2.0*e1 + 3.0*eo^e1 + 4.0*e2 + 5.0*eo^e2 + 6.0*e1^e2 + 
         // 7.0*eo^e1^e2 + 8.0*e3 + 9.0*eo^e3 + 10.0*e1^e3 + 11.0*eo^e1^e3 + 
         // 12.0*e2^e3 + 13.0*eo^e2^e3 + 14.0*e1^e2^e3 + 15.0*eo^e1^e2^e3 + 
         // 16.0*ei + 17.0*eo^ei + 18.0*e1^ei + 19.0*eo^e1^ei + 20.0*e2^ei + 
         // 21.0*eo^e2^ei + 22.0*e1^e2^ei + 23.0*eo^e1^e2^ei + 24.0*e3^ei + 
         // 25.0*eo^e3^ei + 26.0*e1^e3^ei + 27.0*eo^e1^e3^ei + 28.0*e2^e3^ei + 
         // 29.0*eo^e2^e3^ei + 30.0*e1^e2^e3^ei + 31.0*eo^e1^e2^e3^ei)

         //System.out.println(m.toString("m"));
         
         //double[] current = CGAMultivector.fromGaalop(values);
         /*for (int i=0;i<extractedValues.length;i++){
             System.out.print(String.valueOf(current[i]));
             System.out.print(",");
         }
         System.out.println();*/
     }
}
