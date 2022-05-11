/**
 * CliffordBitSet.java
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
 * FITNESS FOR A PARTICif(blade > spaceMask)
			throw ULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package de.orat.math.cga.impl3;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>This class represents a BitSet - value map implementation of the Clifford element and all the operation in an arbitrary dimension space.</p>
 * @version <p>0.9</p>
 * @author <p>Realized by <a href="mailto:vassallo@csai.unipa.it">Giorgio Vassallo</a>, <a href="mailto:pietro.brignola@libero.it">Pietro Brignola</a>, November 2002.</p>
 * @see Clifford
 * @see CliffordTreeSet
 */
public class CliffordBitSet{

	/**
	 * Precision.
	 */
	private static double eps = 1e-12;

	/**
	 * Blade-value mappings of the element.
	 */
	private TreeMap map;

	/**
	 * Creates and returns an element with no blade-value mappings.
	 */
	public CliffordBitSet()
	{
		map = new TreeMap();
	}

	/**
	 * Gets the value of a blade.
	 * @param blade the blade whose value is to be retrieved.
	 * @return the value of the specified blade, 0.0 if blade is not present.
	 */
	public final double get(BladeBitSet blade)
	{
		Value val = (Value) map.get(blade);
		return (val != null) ? val.value : 0.0;
	}

	/**
	 * Gets the value of a blade.
	 * @param blade the binary mask representing the specified blade (format is "i, j, ...", where i, j are versor indexes) whose value is to be retrieved.
	 * @return the value of the specified blade, 0.0 if blade is not present.
	 */
	public final double get(String blade)
	{
		return get(new BladeBitSet(blade));
	}

	/**
	 * Puts a new blade-value mapping or updates existing.
	 * @param blade the specified blade that is to be put.
	 * @param value the corresponding value of the specified blade.
	 */
	public final void put(BladeBitSet blade, double value)
	{
		map.put(blade.clone(), new Value(value));
	}

	/**
	 * Puts a new blade-value mapping or updates existing.
	 * @param blade the binary mask representing the specified blade (format is "i, j, ...", where i, j are versor indexes) that is to be put.
	 * @param value the corresponding value of the specified blade.
	 */
	public final void put(String blade, double value)
	{
		map.put(new BladeBitSet(blade), new Value(value));
	}

	/**
	 * Removes blade-value mapping if existing.
	 * @param blade the specified blade that is to be removed.
	 */
	public final void remove(BladeBitSet blade)
	{
		map.remove(blade);
	}

	/**
	 * Removes blade-value mapping if existing.
	 * @param blade the binary mask representing the specified blade (format is "i, j, ...", where i, j are versor indexes) that is to be removed.
	 */
	public final void remove(String blade)
	{
		map.remove(new BladeBitSet(blade));
	}

	/**
	 * Returns a string representation of this element.
	 * @return the string representation of this element.
	 */
	public String toString()
	{
		//String representation of the element
		String str = new String();
		//Temporary reference variables
		Map.Entry entry;
		BladeBitSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeBitSet) entry.getKey();
			val = (Value) entry.getValue();
			//Printing blade-value mapping
			str += bld.toString() + "\t\t==>\t\t" + val.toString() + "\n";
		}
		str += "----------------\n";
		//Returning the string
		return str;
	}

	/**
	 * Creates and returns an element deeply cloning this element.
	 */
	public Object clone()
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		//Temporary reference variables
		Map.Entry entry;
		BladeBitSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeBitSet) entry.getKey();
			val = (Value) entry.getValue();
			//Adding a copy of blade-value mapping to newcl
			newcl.map.put(bld.clone(), val.clone());
		}
		return newcl;
	}

	/**
	 * Removes blades with values lower than eps.
	 */
	public final void noZero()
	{
		//Temporary reference variables
		Map.Entry entry;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			entry = (Map.Entry) it.next();
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
	public final double uQuadMod()
	{
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
	 * Normalizes this element respect the unsigned module.
	 */
	public final void normalize()
	{
		//Unsigned module
		double um = Math.sqrt(uQuadMod());
		//Checking for null module
		if(um == 0.0)
			return;
		//Temporary reference variables
		Map.Entry entry;
		BladeBitSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			entry = (Map.Entry) it.next();
			bld = (BladeBitSet) entry.getKey();
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
	public final int getMaxGrade()//Not using entrySet().iterator
	{
		//Temporary grade
		int maxgrade = 0, bldgrade;
		//Temporary reference variable
		BladeBitSet bld;
		//Defining an array of blades
		Object[] arrbld = map.keySet().toArray();
		//For all cl blades
		for(int x = 0; x < arrbld.length; x ++){
			//Getting blade
			bld = (BladeBitSet) arrbld[x];
			//Comparing blade grade with temporary grade
			bldgrade = bld.getGrade();
			if(bldgrade > maxgrade)
				maxgrade = bldgrade;
		}
		return maxgrade;
	}

	/**
	 * Verifies if this element is a scalar.
	 * @return true if this element is a scalar, false otherwise.
	 */
	public final boolean isScalar()
	{
		return (getMaxGrade() == 0) ? true : false;
	}

	/**
	 * Verifies if this element is a scalar.
	 * @return true if this element is a scalar, false otherwise.
	 */
	public final boolean isZero()
	{
		return ((get(new BladeBitSet()) == 0.0) && (getMaxGrade() == 0)) ? true : false;
	}

	/**
	 * Verifies if this element is a vector.
	 * @return true if this element is a vector, false otherwise.
	 */
	public final boolean isVector()
	{
		return ((get(new BladeBitSet()) == 0.0) && (getMaxGrade() == 1)) ? true : false;
	}

	/**
	 * Gets highest dimension present in this element.
	 * @return highest present in this element.
	 */
	public final int getMaxDimension()//Not using entrySet().iterator
	{
		//Temporary grade
		int maxdim = 0, bldmaxdim;
		//Temporary reference variable
		BladeBitSet bld;
		//Defining an array of blades
		Object[] arrbld = map.keySet().toArray();
		//For all cl blades
		for(int x = 0; x < arrbld.length; x ++){
			//Getting blade
			bld = (BladeBitSet) arrbld[x];
			//Comparing blade grade with temporary grade
			bldmaxdim = bld.getMaxDimension();
			if(bldmaxdim > maxdim)
				maxdim = bldmaxdim;
		}
		return maxdim;
	}

	/**
	 * Compares this element with the specified element for equality.
	 * Two elements are considered equals if they have same blades and corresponding values differing less than tollerance.
	 * @param obj the second element that is to be compared.
	 * @return true if this Element and the specified element are equals, false otherwise.
	 */
	 public boolean equals(Object obj)
	 {
	 	//Casting to Clifford object
		CliffordBitSet cl = (CliffordBitSet) obj;
		//Verifyng if elements have the same size (number of blade-value mappings)
		if(map.size() != cl.map.size())
			return false;
		//Tollerance
	 	double tol = 2 * eps;
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeBitSet bld1, bld2;
		Value val1, val2;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator();
		Iterator it2 = cl.map.entrySet().iterator();
		while(it1.hasNext()) {
			entry1 = (Map.Entry) it1.next();
			entry2 = (Map.Entry) it2.next();
			//Getting blades
			bld1 = (BladeBitSet) entry1.getKey();
			bld2 = (BladeBitSet) entry2.getKey();
			//Comparing blades
			if(!bld1.equals(bld2))
				return false;
			//Getting values
			val1 = (Value) entry1.getValue();
			val2 = (Value) entry2.getValue();
			//Comparing values
			if(java.lang.Math.abs(val1.value - val2.value) > tol)
				return false;
		}
		//Elements are equals
		return true;
	}

	/**
	 * Computes the sum with the specified element.
	 * @param cl the second element of the sum.
	 * @return a new element from the sum with the specified element.
	 */
	public final CliffordBitSet add(final CliffordBitSet cl)
	{
		//Creating a new empty element
		CliffordBitSet newcl = (CliffordBitSet) clone();
		//Temporary reference variables
		Map.Entry entry2;
		BladeBitSet bld2;
		Value val2, newval;
		//For all cl blade-value mappings
		Iterator it2 = cl.map.entrySet().iterator();
		while(it2.hasNext()) {
			//Getting blade and value
			entry2 = (Map.Entry) it2.next();
			bld2 = (BladeBitSet) entry2.getKey();
			val2 = (Value) entry2.getValue();
			//Searching bld2 in newcl
			newval = (Value) newcl.map.get(bld2);
			//If newcl already contains bld2
			if(newval != null)
				//Updating corresponding value
				newval.value += val2.value;
			else
				//Adding new blade-value mappings to newcl
				newcl.map.put(bld2.clone(),new Value(val2.value));
		}
		//Returning tresholded element
		return newcl;
	}

	/**
	 * Computes the difference with the specified element.
	 * @param cl the second element of the difference.
	 * @return a new element subtracting the second specified element from this.
	 */
	public final CliffordBitSet sub(final CliffordBitSet cl)
	{
		//Creating a new empty element
		CliffordBitSet newcl = (CliffordBitSet) clone();
		//Temporary reference variables
		Map.Entry entry2;
		BladeBitSet bld2;
		Value val2, newval;
		//For all cl blade-value mappings
		Iterator it2 = cl.map.entrySet().iterator();
		while(it2.hasNext()) {
			//Getting blade and value
			entry2 = (Map.Entry) it2.next();
			bld2 = (BladeBitSet) entry2.getKey();
			val2 = (Value) entry2.getValue();
			//Searching bld2 in newcl
			newval = (Value) newcl.map.get(bld2);
			//If newcl already contains bld2
			if(newval != null)
				//Updating corresponding value
				newval.value -= val2.value;
			else
				//Adding new blade-value mappings to newcl
				newcl.map.put(bld2.clone(),new Value(-val2.value));
		}
		//Returning tresholded element
		return newcl;
	}

	/**
	 * Computes the grade involution of this element.
	 * @return a new element from the grade involution of the specified element.
	 */
	public final CliffordBitSet gradeInv()
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		//Resulting value
		double result;
		//Temporary reference variables
		Map.Entry entry;
		BladeBitSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeBitSet) entry.getKey();
			val = (Value) entry.getValue();
			//Computing resulting value regarding parity of the grade (number of inversions)
			result = ((bld.getGrade() & 1) != 0) ? -val.value : val.value;
			//Adding new blade-value mapping to newcl
			newcl.map.put(bld.clone(), new Value(result));
		}
		return newcl;
	}

	/**
	 * Computes the reverse of this element.
	 * @return a new element from the reversion of this element.
	 */
	public final CliffordBitSet rev()
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		//Resulting value
		double result;
		//Temporary reference variables
		Map.Entry entry;
		BladeBitSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeBitSet) entry.getKey();
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
			result = ((bld.getGrade() & 2) != 0) ? -val.value : val.value;
			//Adding new blade-value mapping to newcl
			newcl.map.put(bld.clone(), new Value(result));
		}
		return newcl;
	}

	/**
	 * Computes the inverse of this element.
	 * @return a new element from the inversion of this element.
	 */
	public final CliffordBitSet inv()
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		//Temporary sign
		boolean sign;
		//Resulting value
		double result;
		//Temporary module
		double module = 0.0;
		//Temporary reference variables
		Map.Entry entry;
		BladeBitSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeBitSet) entry.getKey();
			val = (Value) entry.getValue();
			//Computing resulting sign
			sign = ((bld.getGrade() & 2) != 0) ? true : false;
			//Computing resulting value
			result = sign ? -val.value : val.value;
			//Adding new blade-value mapping to newcl
			newcl.map.put(bld.clone(), new Value(result));
			//Updating temporary module
			module += val.value * ((bld.getSign(bld) ^ sign) ? -val.value : val.value);
		}
		return newcl.gP(1/module);
	}

	/**
	 * Computes the conjugation of this element.
	 * The conjugation of an element is a grade involution and a reversion.
	 * @return a new element from the conjugation of the specified element.
	 */
	public final CliffordBitSet conj()
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		//Resulting value
		double result = 0.0;
		//Temporary reference variables
		Map.Entry entry;
		BladeBitSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeBitSet) entry.getKey();
			val = (Value) entry.getValue();
			//Computing resulting value: it is a grade involution and a reversion.
			switch(bld.getGrade() & 3){
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
			newcl.map.put(bld.clone(), new Value(result));
		}
		return newcl;
	}

	/**
	 * Computes the geometric product with the specified scalar.
	 * @param scalar the scalar of the geometric product.
	 * @return a new element from the geometric product with the specified scalar.
	 */
	public final CliffordBitSet gP(double scalar)
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		double newvalue;
		//Temporary reference variables
		Map.Entry entry;
		BladeBitSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeBitSet) entry.getKey();
			val = (Value) entry.getValue();
			//Resulting value for newcl bld
			newvalue = val.value * scalar;
			//Tresholding
			if(java.lang.Math.abs(newvalue) > eps)
				//Adding new blade-value mapping to newcl blades map
				newcl.map.put(bld.clone(), new Value(newvalue));
		}
		return newcl;
	}


	/**
	 * Computes the geometric product with the specified element.
	 * @param cl the second element of the geometric product.
	 * @return a new element from the geometric product with the specified element.
	 */
	public final CliffordBitSet gP(final CliffordBitSet cl)
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeBitSet bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator(), it2;
		while(it1.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it1.next();
			bld1 = (BladeBitSet) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//For all cl blade-value mappings
			it2 = cl.map.entrySet().iterator();
			while(it2.hasNext()) {
				//Getting blade and value
				entry2 = (Map.Entry) it2.next();
				bld2 = (BladeBitSet) entry2.getKey();
				val2 = (Value) entry2.getValue();
				//Computing resulting blade
				newbld = bld1.geometricProduct(bld2);
				//Computing resulting value (regarding the sign)
				result = val1.value * (bld1.getSign(bld2) ? -val2.value : val2.value);
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
	 * Computes the wedge product with the specified element.
	 * @param cl the second element of the wedge product.
	 * @return a new element from the wedge product with the specified element.
	 */
	public final CliffordBitSet wP(final CliffordBitSet cl)
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeBitSet bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator(), it2;
		while(it1.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it1.next();
			bld1 = (BladeBitSet) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//For all cl blade-value mappings
			it2 = cl.map.entrySet().iterator();
			while(it2.hasNext()) {
				//Getting blade and value
				entry2 = (Map.Entry) it2.next();
				bld2 = (BladeBitSet) entry2.getKey();
				val2 = (Value) entry2.getValue();
				//Computing resulting blade
				newbld = bld1.wedgeProduct(bld2);
				//If bld1 and bld2 have common versor wedge product is null
				if(newbld == null) continue;
				//Computing resulting value (regarding the sign)
				result = val1.value * (bld1.getSign(bld2) ? -val2.value : val2.value);
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
	public final CliffordBitSet lC(final CliffordBitSet cl)
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeBitSet bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator(), it2;
		while(it1.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it1.next();
			bld1 = (BladeBitSet) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//For all cl blade-value mappings
			it2 = cl.map.entrySet().iterator();
			while(it2.hasNext()) {
				//Getting blade and value
				entry2 = (Map.Entry) it2.next();
				bld2 = (BladeBitSet) entry2.getKey();
				val2 = (Value) entry2.getValue();
				//Computing resulting blade
				newbld = bld1.leftContraction(bld2);
				//If bld1 versors are not a subset of bld2 versors left contraction is null
				if(newbld == null) continue;
				//Computing resulting value (regarding the sign)
				result = val1.value * (bld1.getSign(bld2) ? -val2.value : val2.value);
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
	public final CliffordBitSet rC(final CliffordBitSet cl)
	{
		//Creating a new empty element
		CliffordBitSet newcl = new CliffordBitSet();
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeBitSet bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator(), it2;
		while(it1.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it1.next();
			bld1 = (BladeBitSet) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//Defining an iterator on cl2 blade-value mappings
			it2 = cl.map.entrySet().iterator();
			//For all cl blade-value mappings
			while(it2.hasNext()) {
				//Getting blade and value
				entry2 = (Map.Entry) it2.next();
				bld2 = (BladeBitSet) entry2.getKey();
				val2 = (Value) entry2.getValue();
				//Computing resulting blade
				newbld = bld1.rightContraction(bld2);
				//If bld2 versors are not a subset of bld1 versors right contraction is null
				if(newbld == null) continue;
				//Computing resulting value (regarding the sign)
				result = val1.value * (bld1.getSign(bld2) ? -val2.value : val2.value);
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
	 * Computes the meet with the specified element in a common subspace.
	 * @param cl the second element of the meet.
	 * @param i the element representing a common subspace.
	 * @return a new element from the meet with the specified element.
	 */
	public final CliffordBitSet meet(final CliffordBitSet cl, final CliffordBitSet i){
		return (gP(i)).lC(cl);
	}

}
