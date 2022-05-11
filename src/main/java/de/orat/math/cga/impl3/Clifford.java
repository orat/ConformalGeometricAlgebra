/**
 * Clifford.java
 *
 * This file is part of jclifford package and it's distributed under the terms of the MIT license.
 *
 * The MIT License :
 * -----------------
 * Copyright (c) 2002, 2003, 2004, 2005 Giorgio Vassallo, Pietro Brignola
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package de.orat.math.cga.impl3;

import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

/**
 * <p>This class represents a Clifford element and all the operation in a signed space.</p>
 * <p>Use this class for p + q <= 8. For p + q > 8 use CliffordBitSet or CliffordTreeSet instead.</p>
 * @version <p>0.9</p>
 * @author <p>Realized by <a href="mailto:vassallo@csai.unipa.it">Giorgio Vassallo</a>, <a href="mailto:pietro.brignola@libero.it">Pietro Brignola</a>, November 2002.</p>
 * @see CliffordBitSet
 * @see CliffordTreeSet
 */
public class Clifford{

	/**
	 * Element's blade-value mappings.
	 */
	protected TreeMap map;

	/**
	 * Algebra's Precision.
	 */
	protected static double eps = 1e-12;

	/**
	 * Number of dimensions with positive square versors.
	 */
	protected static int p = 0;

	/**
	 * Number of dimensions with negative square versors.
	 */
	protected static int q = 0;

	/**
	 * Algebra's dimension.
	 */
	protected static int dim = 0;
	
	/**
	 * Algebra's possible blades (2 ^ dim).
	 */
	protected static int hdim = 1;

	/**
	 * Mask with bits 1 corresponding to the presence of dimensions (starting from l.s.b.).
	 *
	 * Es.: dim = p + q = 5, spacemask = [0...011111].
	 */
	protected static int spaceMask = 0;

	/**
	 * Mask with p bits 0 and q bits 1 corresponding to negative square versors (starting from l.s.b.).
	 *
	 * Es.: p = 3, q = 2, signmask = [0...011000].
	 */
	protected static int signMask = 0;

	/**
	 * Stores the grade (number of bits 1 in the binary rappresentation) of a blade.
	 */
	protected static int[] gradeTable = {0};

	/**
	 * Stores the grade (number of bits 1 in the binary rappresentation) of a blade.
	 */
	protected static boolean[][] signTable = {{false}};
	
	/**
	 * Algebra's pseudoscalar.
	 */
	protected static Clifford pseudoScalar;

	/**
	 * Gets algebra's precision.
	 * @return algebra's precision.
	 */
	public static double getEps()
	{
		return eps;
	}

	/**
	 * Sets algebra's precision.
	 * @throws IllegalArgumentException if the precision is negative.
	 * @param e positive precision.
	 */
	public static void setEps(double e) throws IllegalArgumentException{
		//Positive precision required
		if(e <= 0)
			throw new IllegalArgumentException("Invalid precision: positive precision required.");
		eps = e;
	}

	/**
	 * Gets algebra's dimension.
	 * @return algebra's dimension.
	 */
	public static int getDim(){
		return dim;
	}

	/**
	 * Gets algebra's possible blades.
	 * @return algebra's possible blades.
	 */
	public static int getPossibleBlades(){
		return hdim;
	}

	/**
	 * Counts the number of bits 1 in the binary rappresentation of a blade.
	 * @param bld the blade whose number of bits 1 in its binary rappresentation are to be counted.
	 * @return the number of bits 1 in the binary rappresentation of the specified blade.
	 */
	private static int bitCount(int bld){
		int count = 0;
		while(bld != 0){
			count ++;
			bld &= bld - 1;
		}
		return count;
	}

	/**
	 * Computes the sign of the product of two blades.
	 * @param bld1 the first blade of the product.
	 * @param bld2 the second blade of the product.
	 * @return true if the sign of the product of the specified blades is negative, false otherwise.
	 */
	private static boolean getSign(int bld1,int bld2){
		int k,l,sign = 0;
		for(k = 1; k < hdim; k <<= 1){
			if((bld1 & k) != 0){
				l = bld2 & (k -1);
				if((gradeTable[l] & 1) != 0)
					sign ^= 1;
			}
		}
		l = bld1 & bld2 & signMask;
		if((gradeTable[l] & 1) != 0)
			sign ^= 1;
		return sign != 0;
	}

	/**
	 * Computes binomial coefficents.
	 * @param n the dimension.
	 * @param k the grade.
	 * @return binomial coefficent.
	 */
	private static int bCoefficent(int n, int k){
		if((k == 0) || (k == n))
			return 1;
		if(k > (n / 2))
			k = n - k;
		double c = ((double) n) / k;
		n--;
		k--;
		while(k != 0){
			c *= n;
			c /= k;
			n--;
			k--;
		}
		return ((int) c);
	}

	/**
	 * Initializes algebra's signature.
	 * @throws IllegalArgumentException if p or q are negative or their sum is greater than 8.
	 * @param pdim the number of dimensions with versors that square in 1.
	 * @param ndim the number of dimensions with versors that square in -1.
	 */
	public static void init(int pdim, int ndim) throws IllegalArgumentException{
		//Checking for valid signature
		if((pdim < 0) || (ndim < 0) || ((pdim + ndim) > 8))
			throw new IllegalArgumentException("Invalid signature: use p >= 0, q >= 0, 0 <= p + q <= 8");
		//Initializing signature
		p = pdim;
		q = ndim;
		dim = p + q;
		hdim = 1 << dim;
		//Initializing spaceMask
		spaceMask = hdim - 1;
		//Initializing signMask
		signMask = ~((1 << p) - 1) & spaceMask;
		//Initializing gradeTable
		gradeTable = new int[hdim];
		for(int i = 0; i < hdim; i ++)
			gradeTable[i] = bitCount(i);
		//Initializing signTable
		signTable = new boolean[hdim][hdim];
		for(int i = 0; i < hdim; i++)
			for(int j = 0; j < hdim; j++)
				signTable[i][j] = getSign(i,j);
		//Initialiting pseudoscalar
		pseudoScalar = new Clifford();
		pseudoScalar.map.put(new Blade(spaceMask),new Value(1.0));
	}

	/**
	 * Sets meet operation subspace.
	 * @throws IllegalArgumentException if subspace is negative or outside algebra's space mask.
	 * @param subspace the meet operation subspace.
	 */
	public static void setMeetSubSpace(int subspace) throws IllegalArgumentException{
		//Checking for valid subspace
		if(subspace < 0)
			throw new IllegalArgumentException("Blade cannot be negative.");
		if(subspace > spaceMask)
			throw new IllegalArgumentException("Invalid signature for this operation.");
		//Setting pseudoscalar
		pseudoScalar = new Clifford();
		pseudoScalar.map.put(new Blade(subspace),new Value(1.0));
	}

	/**
	 * Creates and returns an element with no blade-value mappings.
	 * @throws RuntimeException if algebra's signature is not initialized.
	 */
	public Clifford() throws RuntimeException{
		map = new TreeMap();
	}
	
	/**
	 * Creates and returns an element with specified blade-value mappings.
	 * @throws RuntimeException if algebra's signature is not initialized.
	 * @throws IllegalArgumentException if the arrays have different lenght.
	 * @param blades the int array representing the specified blades.
	 * @param values the double array representing the corresponding values.
	 */
	public Clifford(int blades[], double values[]) throws RuntimeException, IllegalArgumentException{
		//Checking for same length arrays
		if(blades.length != values.length)
			throw new IllegalArgumentException("Different length arrays.");
		//Creating element and adding mappings
		map = new TreeMap();
		for(int i = 0; i < blades.length; i ++)
			map.put(new Blade(blades[i]),new Value(values[i]));
	}

	/**
	 * Gets the value of a blade.
	 * @throws IllegalArgumentException if blade is negative or outside algebra's space mask.
	 * @param blade the int representing the specified blade whose value is to be retrieved.
	 * @return the value of the specified blade, 0.0 if blade is not present.
	 */
	public double get(int blade) throws IllegalArgumentException{
		if(blade < 0)
			throw new IllegalArgumentException("Blade cannot be negative.");
		if(blade > spaceMask)
			throw new IllegalArgumentException("Invalid signature for this operation.");
		Value val = (Value) map.get(new Blade(blade));
		return (val != null) ? val.value : 0.0;
	}

	/**
	 * Gets the value of a blade.
	 * @throws IllegalArgumentException if blademask is invalid, blade is negative or outside algebra's space mask.
	 * @param blademask the binary mask representing the specified blade whose value is to be retrieved.
	 * @return the value of the specified blade, 0.0 if blade is not present.
	 */
	public double get(String blademask) throws IllegalArgumentException{
		int blade;
		try{
			blade = Integer.parseInt(blademask,2);
		}catch(Exception e){
			throw new IllegalArgumentException("Invalid mask for this operation.");
		}
		if(blade < 0)
			throw new IllegalArgumentException("Blade cannot be negative.");
		if(blade > spaceMask)
			throw new IllegalArgumentException("Invalid signature for this operation.");
		Value val = (Value) map.get(new Blade(blade));
		return (val != null) ? val.value : 0.0;
	}

	/**
	 * Puts a new blade-value mapping or updates existing.
	 * @throws IllegalArgumentException if blade is negative or outside algebra's space mask.
	 * @param blade the int representing the specified blade that is to be put.
	 * @param value the double representing the corresponding value of the specified blade.
	 */
	public void put(int blade, double value) throws IllegalArgumentException{
		if(blade < 0)
			throw new IllegalArgumentException("Blade cannot be negative.");
		if(blade > spaceMask)
			throw new IllegalArgumentException("Invalid signature for this operation.");
		//Adding tresholded value
		map.put(new Blade(blade), new Value((Math.abs(value) >= eps) ? value : 0.0));
	}

	/**
	 * Puts a new blade-value mapping or updates existing.
	 * @throws IllegalArgumentException if blademask is invalid, blade is negative or outside algebra's space mask.
	 * @param blademask the binary mask representing the specified blade that is to be put.
	 * @param value the double representing the corresponding value of the specified blade.
	 */
	public void put(String blademask, double value) throws IllegalArgumentException{
		int blade;
		try{
			blade = Integer.parseInt(blademask,2);
		}catch(Exception e){
			throw new IllegalArgumentException("Invalid mask for this operation.");
		}
		if(blade < 0)
			throw new IllegalArgumentException("Blade cannot be negative.");
		if(blade > spaceMask)
			throw new IllegalArgumentException("Invalid signature for this operation.");
		//Adding tresholded value
		map.put(new Blade(blade), new Value((Math.abs(value) >= eps) ? value : 0.0));
	}

	/**
	 * Removes blade-value mapping if existing.
	 * @throws IllegalArgumentException if blade is negative or outside algebra's space mask.
	 * @param blade the int representing the specified blade that is to be removed.
	 */
	public void remove(int blade) throws IllegalArgumentException{
		if(blade < 0)
			throw new IllegalArgumentException("Blade cannot be negative.");
		if(blade > spaceMask)
			throw new IllegalArgumentException("Invalid signature for this operation.");
		map.remove(new Blade(blade));
	}

	/**
	 * Returns a string representation of the element.
	 * @return the string representation of the element.
	 */
	public String toString(){
		//String representation of the element
		String str = new String();
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			str = str + bld.toString() + "\t==>\t" + val.value + "\n";
		}
		str = str + "---------\n";
		//Returning the string
		return str;
	}	

	/**
	 * Creates and returns an element deeply cloning this element.
	 */
	public Object clone(){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Adding new blade-value mapping to newcl
			newcl.map.put(new Blade(bld.blade), new Value(val.value));
		}
		return newcl;
	}

	/**
	 * Removes blades with values lower than eps.
	 */
	public final void noZero(){
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Tresholding
			if(java.lang.Math.abs(val.value) < eps)
				//Removing blade-value mappings
				it.remove();
		}
	}

	/**
	 * Computes the quad module of an element discarding signature.
	 * @return the quad module of the specified element discarding signature.
	 */
	public final double uQuadMod(){
		//Temporary unsigned quad module
		double uqm = 0.0;
		//Temporary reference variables
		Map.Entry entry;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting value
			entry = (Map.Entry) it.next();
			val = (Value) entry.getValue();
			//Uptdating temporary module
			uqm += val.value * val.value;
		}
		return uqm;
	}

	/**
	 * Computes the quad module of an element regarding signature.
	 * @return the quad module of the specified element regarding signature.
	 */
	public final double sQuadMod(){
		//Temporary module
		double sqm = 0.0;
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Uptdating temporary module regarding versor square sign
			sqm += val.value * (signTable[bld.blade][bld.blade] ? -val.value : val.value);
		}
		return sqm;
	}

	/**
	 * Normalizes this element respect the unsigned module.
	 */
	public final void normalize(){
		//Unsigned module
		double um = Math.sqrt(uQuadMod());
		//Checking for null module
		if(um == 0.0)
			return;
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Updating value
			val.value /= um;
			//Tresholding
			if(java.lang.Math.abs(val.value) < eps)
				//Removing blade-value mappings
				it.remove();
		}
	}

	/**
	 * Gets highest grade of this element.
	 * @return highest grade of this element.
	 */
	public final int getMaxGrade(){//Not using entrySet().iterator
		//Temporary grade
		int maxgrade = 0;
		//Temporary reference variable
		Blade bld;
		//Defining an array of cl blades
		Object[] arrbld = map.keySet().toArray();
		//For all cl blades
		for(int x = 0; x < arrbld.length; x ++){
			//Getting blade
			bld = (Blade) arrbld[x];
			//Comparing blade grade with temporary grade
			if(gradeTable[bld.blade] > maxgrade)
				maxgrade = gradeTable[bld.blade];
		}
		return maxgrade;
	}

	/**
	 * Verifies if this element is a scalar.
	 * @return true if this element is a scalar, false otherwise.
	 */
	public final boolean isScalar(){
		return (getMaxGrade() == 0) ? true : false;
	}

	/**
	 * Verifies if this element is a vector.
	 * @return true if this element is a vector, false otherwise.
	 */
	public final boolean isVector(){
		return ((get(0) == 0.0) && (getMaxGrade() == 1)) ? true : false;
	}

	/**
	 * Compares two elements for equality.
	 * Two elements are considered equals if they have same blades and corresponding values differing less than 2*EPS.
	 * @param obj the second element that is to be compared.
	 * @return true if the two specified elements are equals, false otherwise.
	 */
	 public boolean equals(Object obj){
	 	//Casting to Clifford object
		Clifford cl = (Clifford) obj;
	 	//Verifyng if blades maps have the same size (number of blade-value mappings)
	 	if(map.size() != cl.map.size())
	 		return false;
	 	//Tollerance
	 	double tol = 2*eps;
		//Temporary reference variables
		Blade bld1, bld2;
		Value val1, val2;
		//Defining ordered arrays of blades and values of cl1 and cl2
		Object[] arrbld1 = map.keySet().toArray();
		Object[] arrbld2 = cl.map.keySet().toArray();
		Object[] arrval1 = map.values().toArray();
		Object[] arrval2 = cl.map.values().toArray();
		//For all blade-value mappings
		for(int x = 0; x < arrbld1.length; x ++){
			bld1 = (Blade) arrbld1[x];
			bld2 = (Blade) arrbld2[x];
			val1 = (Value) arrval1[x];
			val2 = (Value) arrval2[x];
			//Comparing blades
			if(bld1.blade != bld2.blade)
				return false;
			//Comparing values
	 		if(java.lang.Math.abs(val1.value - val2.value) > tol)
	 			return false;
	 	}
	 	//Elements are equals
	 	return true;
	 }

	/**
	 * Adds two elements.
	 * @param cl the second element of the sum.
	 * @return a new element from the sum of the two specified elements.
	 */
	public final Clifford add(final Clifford cl){
		//Cloning this element
		Clifford newcl = (Clifford) clone();
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val, newval;
		//For all cl blade-value mappings
		Iterator it = cl.map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Searching bld in newcl
			newval = (Value) newcl.map.get(bld);
			//If newcl already contains bld
			if(newval != null)
				//Updating corresponding value
				newval.value += val.value;
			else
				//Adding new blade-value mappings to newcl
				newcl.map.put(new Blade(bld.blade),new Value(val.value));
		}
		//Returning tresholded element
		newcl.noZero();
		return newcl;
	}

	/**
	 * Subtracts two elements.
	 * @param cl the second element of the difference.
	 * @return a new element from the difference of the two specified elements.
	 */
	public final Clifford sub(final Clifford cl){
		//Cloning this element
		Clifford newcl = (Clifford) clone();
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val, newval;
		//For all cl blade-value mappings
		Iterator it = cl.map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Searching bld in newcl
			newval = (Value) newcl.map.get(bld);
			//If newcl already contains bld
			if(newval != null)
				//Updating corresponding value
				newval.value -= val.value;
			else
				//Adding new blade-value mappings to newcl
				newcl.map.put(new Blade(bld.blade),new Value(-val.value));
		}
		//Returning tresholded element
		newcl.noZero();
		return newcl;

	}

	/**
	 * Computes the grade involution of this element.
	 * @return a new element from the grade involution of the specified element.
	 */
	public final Clifford gradeInv(){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Resulting value
		double result;
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Computing resulting value regarding parity of the grade (number of inversions)
			result = ((gradeTable[bld.blade] & 1) != 0) ? -val.value : val.value;
			//Adding new blade-value mapping to newcl
			newcl.map.put(new Blade(bld.blade), new Value(result));
		}
		return newcl;
	}

	/**
	 * Computes the reverse of this element.
	 * @return a new element from the reversion of this element.
	 */
	public final Clifford rev(){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Resulting value
		double result;
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
   /*
			Computing resulting value regarding the sign: (-1)^((r(r-1))/2)
			Sign only depends upon the odd-ness or even-ness of the number of transpositions
			required to get things back in order. This obeys a simple recurrance relationship.
			Let T(n) be the number of transpositions required to revert an n-form.
			Then, T(n+1) = T(n) + n - 1 because it will require T(n) transpositions to reorder
			the first n subscripts and n-1 transpositions to get the n+1-th subscript from one
			end of the list to the other.
			So odd-ness or even-ness of T(n+4) is the same as that of T(n), because:
			T(n+4) = T(n+3) + n + 2 = T(n+2) + 2n + 3 = T(n+1) + 3n + 3 = T(n) + 4n + 2
			And, because T(0) and T(1) are even while T(2) and T(3) are odd an n-form requires:
			an odd number of transpositions to revert if n = 2 or n = 3 modulo 4.
			In code, this translates to whether the second bit of the grade is set.
			*/
			result = ((gradeTable[bld.blade] & 2) != 0) ? -val.value : val.value;
			//Adding new blade-value mapping to newcl
			newcl.map.put(new Blade(bld.blade), new Value(result));
		}
		return newcl;
	}

	/**
	 * Computes the inverse of this element.
	 * @return a new element from the inversion of this element.
	 */
	public final Clifford inv(){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Temporary sign
		boolean sign;
		//Resulting value
		double result;
		//Temporary module
		double module = 0.0;
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Computing resulting sign
			sign = ((gradeTable[bld.blade] & 2) != 0) ? true : false;
			//Computing resulting value
			result = sign ? -val.value : val.value;
			//Adding new blade-value mapping to newcl
			newcl.map.put(new Blade(bld.blade), new Value(result));
			//Updating temporary module
			module += val.value * ((signTable[bld.blade][bld.blade] ^ sign) ? -val.value : val.value);
		}
		return newcl.gP(1/module);
	}

	/**
	 * Computes the conjugation of this element.
	 * The conjugation of an element is a grade involution and a reversion.
	 * @return a new element from the conjugation of the specified element.
	 */
	public final Clifford conj(){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Resulting value
		double result = 0.0;
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Computing resulting value: it is a grade involution and a reversion.
			switch(gradeTable[bld.blade] & 3){
				case 0:
				case 3:
					result = val.value;
				break;
				case 1:
				case 2:
					result = -val.value;
				break;
			}
			//Adding new blade-value mapping to newcl
			newcl.map.put(new Blade(bld.blade), new Value(result));
		}
		return newcl;
	}

	/**
	 * Grades an element.
	 * @param grade the specified grade of the grading operation.
	 * @return a new element with only terms of the specified grade.
	 */
	public final Clifford grade(int grade){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Selecting blades
			if(gradeTable[bld.blade] == grade)
				//Adding new blade-value mapping to newcl
				newcl.map.put(new Blade(bld.blade), new Value(val.value));
		}
		return newcl;
	}

	/**
	 * Computes the geometric product of an element and a scalar.
	 * @param scalar the scalar of the geometric product.
	 * @return a new element from the geometric product of the specified element and the specified scalar.
	 */
	public final Clifford gP(double scalar){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		double newvalue;
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Resulting value for newcl bld
			newvalue = val.value * scalar;
			//Tresholding
			if(java.lang.Math.abs(newvalue) > eps)
				//Adding new blade-value mapping to newcl blades map
				newcl.map.put(new Blade(bld.blade), new Value(newvalue));
		}
		return newcl;
	}

	/**
	 * Computes the geometric product of two elements.
	 * @param cl the second element of the geometric product.
	 * @return a new element from the geometric product of the two specified elements.
	 */
	public final Clifford gP(final Clifford cl){

		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Temporary reference variables
		Blade bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//Defining ordered arrays of blades and values of cl1 and cl2
		Object[] arrbld1 = map.keySet().toArray();
		Object[] arrbld2 = cl.map.keySet().toArray();
		Object[] arrval1 = map.values().toArray();
		Object[] arrval2 = cl.map.values().toArray();
		//For all blade-value mappings
		for(int x = 0; x < arrbld1.length; x ++){
			bld1 = (Blade) arrbld1[x];
			val1 = (Value) arrval1[x];
			//For all cl blade-value mappings
			for(int y = 0; y < arrbld2.length; y ++){
				bld2 = (Blade) arrbld2[y];
				val2 = (Value) arrval2[y];
				//Computing resulting blade
				newbld = new Blade(bld1.blade ^ bld2.blade);
				//Computing resulting value (regarding the sign)
				result = val1.value * (signTable[bld1.blade][bld2.blade] ? -val2.value : val2.value);
				//Searching newbld in newcl
				newval = (Value) newcl.map.get(newbld);
				//Updating newval by adding result
				if(newval != null)
					newval.value += result;
				//Adding new blade-value mapping to newcl
				else
					newcl.map.put(newbld, new Value(result));
			}
		}
		//Returning tresholded element
		newcl.noZero();
		return newcl;
/*
		//Using iterators

		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Temporary reference variables
		Map.Entry entry1, entry2;
		Blade bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//Defining an iterator on cl1 blade-value mappings
		Iterator it1 = cl1.map.entrySet().iterator(), it2;
		//For all cl1 blade-value mappings
		while(it1.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it1.next();
			bld1 = (Blade) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//Defining an iterator on cl2 blade-value mappings
			it2 = cl2.map.entrySet().iterator();
			//For all cl1 blade-value mappings
			while(it2.hasNext()) {
				//Getting blade and value
				entry2 = (Map.Entry) it2.next();
				bld2 = (Blade) entry2.getKey();
				val2 = (Value) entry2.getValue();
				//Computing resulting blade
				newbld = new Blade(bld1.blade ^ bld2.blade);
				//Computing resulting value (regarding the sign)
				result = val1.value * (signTable[bld1.blade][bld2.blade] ? -val2.value : val2.value);
				//Searching newbld in newcl
				newval = (Value) newcl.map.get(newbld);
				//Updating newval by adding result
				if(newval != null)
					newval.value += result;
				//Adding new blade-value mapping to newcl
				else
					newcl.map.put(newbld, new Value(result));
			}
		}
		//Returning tresholded element
		newcl.noZero();
		return newcl;
*/
	}

	/**
	 * Computes the wedge product of two elements.
	 * @param cl the second element of the wedge product.
	 * @return a new element from the wedge product of the two specified elements.
	 */
	public final Clifford wP(final Clifford cl){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Temporary reference variables
		Blade bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//Defining ordered arrays of blades and values
		Object[] arrbld1 = map.keySet().toArray();
		Object[] arrbld2 = cl.map.keySet().toArray();
		Object[] arrval1 = map.values().toArray();
		Object[] arrval2 = cl.map.values().toArray();
		//For all blade-value mappings
		for(int x = 0; x < arrbld1.length; x ++){
			bld1 = (Blade) arrbld1[x];
			val1 = (Value) arrval1[x];
			//For all cl blade-value mappings
			for(int y = 0; y < arrbld2.length; y ++){
				bld2 = (Blade) arrbld2[y];
				val2 = (Value) arrval2[y];
				//
				if((bld1.blade & bld2.blade) != 0)
					continue;
				//Computing resulting blade
				newbld = new Blade(bld1.blade ^ bld2.blade);
				//Computing resulting value (regarding the sign)
				result = val1.value * (signTable[bld1.blade][bld2.blade] ? -val2.value : val2.value);
				//Searching newbld in newcl
				newval = (Value) newcl.map.get(newbld);
				//Updating newval by adding result
				if(newval != null)
					newval.value += result;
				//Adding new blade-value mapping to newcl
				else
					newcl.map.put(newbld, new Value(result));
			}
		}
		//Returning tresholded element
		newcl.noZero();
		return newcl;
	}

	/**
	 * Computes the left contraction with the specified element.
	 * @param cl the second element of the left contraction.
	 * @return a new element from the left contraction with the specified element.
	 */
	public final Clifford lC(final Clifford cl){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Temporary reference variables
		Blade bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//Defining ordered arrays of blades and values
		Object[] arrbld1 = map.keySet().toArray();
		Object[] arrbld2 = cl.map.keySet().toArray();
		Object[] arrval1 = map.values().toArray();
		Object[] arrval2 = cl.map.values().toArray();
		//For all blade-value mappings
		for(int x = 0; x < arrbld1.length; x ++){
			bld1 = (Blade) arrbld1[x];
			val1 = (Value) arrval1[x];
			//For all cl blade-value mappings
			for(int y = 0; y < arrbld2.length; y ++){
				bld2 = (Blade) arrbld2[y];
				val2 = (Value) arrval2[y];
				//Continue if bld1 has same versor not in bld2
				if((bld1.blade & ~(bld2.blade)) != 0)
					continue;
				//Computing resulting blade
				newbld = new Blade(bld1.blade ^ bld2.blade);
				//Computing resulting value (regarding the sign)
				result = val1.value * (signTable[bld1.blade][bld2.blade] ? -val2.value : val2.value);
				//Searching newbld in newcl
				newval = (Value) newcl.map.get(newbld);
				//Updating newval by adding result
				if(newval != null)
					newval.value += result;
				//Adding new blade-value mapping to newcl
				else
					newcl.map.put(newbld, new Value(result));
			}
		}
		//Returning tresholded element
		newcl.noZero();
		return newcl;
	}

	/**
	 * Computes the right contraction with the specified element.
	 * @param cl the second element of the right contraction.
	 * @return a new element from the right contraction with the specified element.
	 */
	public final Clifford rC(final Clifford cl){
		//Creating a new empty element
		Clifford newcl = new Clifford();
		//Temporary reference variables
		Blade bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//Defining ordered arrays of map and values
		Object[] arrbld1 = map.keySet().toArray();
		Object[] arrbld2 = cl.map.keySet().toArray();
		Object[] arrval1 = map.values().toArray();
		Object[] arrval2 = cl.map.values().toArray();
		//For all blade-value mappings
		for(int x = 0; x < arrbld1.length; x ++){
			bld1 = (Blade) arrbld1[x];
			val1 = (Value) arrval1[x];
			//For all cl blade-value mappings
			for(int y = 0; y < arrbld2.length; y ++){
				bld2 = (Blade) arrbld2[y];
				val2 = (Value) arrval2[y];
				//Continue if bld2 has same versor not in bld1
				if((~ (bld1.blade) & bld2.blade) != 0)
					continue;
				//Computing resulting blade
				newbld = new Blade(bld1.blade ^ bld2.blade);
				//Computing resulting value (regarding the sign)
				result = val1.value * (signTable[bld1.blade][bld2.blade] ? -val2.value : val2.value);
				//Searching newbld in newcl
				newval = (Value) newcl.map.get(newbld);
				//Updating newval by adding result
				if(newval != null)
					newval.value += result;
				//Adding new blade-value mapping to newcl
				else
					newcl.map.put(newbld, new Value(result));
			}
		}
		//Returning tresholded element
		newcl.noZero();
		return newcl;
	}

	/**
	 * Computes the fast dot product of two vector.
	 * @throws IllegalArgumentException if elements are not vectors.
	 * @param cl the second vector of the fast dot product.
	 * @return the dot product of the two specified vector.
	 */
	public final double dot(final Clifford cl) throws IllegalArgumentException{
		//Temporary dot product
		double d = 0.0;
		//Temporary reference variables
		Map.Entry entry1;
		Blade bld1;
		Value val1,val2;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it.next();
			bld1 = (Blade) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//Checking for grade 1 blade
			if(gradeTable[bld1.blade] != 1)
				throw new RuntimeException("Invalid argument: fast dot product is only for vectors.");
			//Searching bld1 in cl
			val2 = (Value) cl.map.get(bld1);
			//Updating temporary dot product
			if(val2 != null)
				d += (val1.value * (((bld1.blade & signMask) != 0) ? -val2.value : val2.value));
		}
		return d;
	}

	/**
	 * Computes the commutation with the specified element.
	 * @param cl the second element of the commutation.
	 * @return a new element from the commutation with the specified element.
	 */
	public final Clifford comm(final Clifford cl){
		return ((gP(cl)).sub(cl.gP(this))).gP(0.5);
	}

	/**
	 * Computes the dual of this element.
	 * @return a new element that is the dual of this element.
	 */
	public final Clifford dual(){
		return gP(pseudoScalar.rev());
	}

	/**
	 * Computes the meet with the specified element.
	 * @param cl the second element of the meet.
	 * @return a new element from the meet with the specified element.
	 */
	public final Clifford meet(final Clifford cl){
		return (gP(pseudoScalar)).lC(cl);
	}

	/**
	 * Computes the meet with the specified element in a common subspace.
	 * @param cl the second element of the meet.
	 * @param is the element representing a common subspace.
	 * @return a new element from the meet with the specified element.
	 */
	public final Clifford meet(final Clifford cl, final Clifford is){
		return (gP(is)).lC(cl);
	}

	/**
	 * Computes the reflection against the specified vector.
	 * @param n the vector against wich reflect.
	 * @return a new element from the reflection against the specified vector.
	 */
	public final Clifford reflect(final Clifford n){
		return (gP(n.sQuadMod())).sub(n.gP(2.0 * dot(n)));
	}

}
