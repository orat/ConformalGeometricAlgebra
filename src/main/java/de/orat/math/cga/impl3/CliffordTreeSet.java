/**
 * CliffordTreeSet.java
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

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>This class represents a TreeSet - value map implementation of the Clifford element and all the operation in an arbitrary dimension space.</p>
 * @version <p>0.9</p>
 * @author <p>Realized by <a href="mailto:vassallo@csai.unipa.it">Giorgio Vassallo</a>, <a href="mailto:pietro.brignola@libero.it">Pietro Brignola</a>, November 2002.</p>
 * @see Clifford
 * @see CliffordBitSet
 */
public class CliffordTreeSet{

	/**
	 * Blade-value mappings of the element.
	 */
	private TreeMap map;

	/**
	 * Creates and returns an element with no blade-value mappings.
	 */
	public CliffordTreeSet()
	{
		map = new TreeMap();
	}

	/**
	 * Gets the value of a blade.
	 * @param blade the blade whose value is to be retrieved.
	 * @return the value of the specified blade, 0.0 if blade is not present in this element.
	 */
	public final double get(BladeTreeSet blade)
	{
		Value val = (Value) map.get(blade);
		return (val != null) ? val.value : 0.0;
	}

	/**
	 * Puts a new blade-value mapping or updates existing.
	 * @param blade the specified blade that is to be put.
	 * @param value the corresponding value of the specified blade.
	 */
	public final void put(BladeTreeSet blade, double value)
	{
		map.put(blade.clone(), new Value(value));
	}

	/**
	 * Removes blade-value mapping if existing.
	 * @param blade the int representing the specified blade that is to be removed.
	 */
	public final void remove(BladeTreeSet blade)
	{
		map.remove(blade);
	}

	/**
	 * Computes the sum with the specified element.
	 * @param cl the second element of the sum.
	 * @return a new element from the sum with the specified element.
	 */
	public CliffordTreeSet add(final CliffordTreeSet cl)
	{
		//Creating a new empty element
		CliffordTreeSet newcl = (CliffordTreeSet) this.clone();
		//Temporary reference variables
		Map.Entry entry;
		BladeTreeSet bld2;
		Value val2, newval;
		//For all cl blade-value mappings
		Iterator it2 = cl.map.entrySet().iterator();
		while(it2.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it2.next();
			bld2 = (BladeTreeSet) entry.getKey();
			val2 = (Value) entry.getValue();
			//Searching bld2 in newcl
			newval = (Value) newcl.map.get(bld2);
			//If newcl already contains bld2
			if(newval != null)
				//Updating corresponding value
				newval.value += val2.value;
			else
				//Adding new blade-value mappings to newcl
				newcl.map.put(bld2.clone(),val2.clone());
		}
		//Returning tresholded element
		return newcl;
	}

	/**
	 * Computes the difference with the specified element.
	 * @param cl the second element of the difference.
	 * @return a new element subtracting the second specified element from this.
	 */
	public CliffordTreeSet sub(final CliffordTreeSet cl)
	{
		//Creating a new empty element
		CliffordTreeSet newcl = (CliffordTreeSet) this.clone();
		//Temporary reference variables
		Map.Entry entry;
		BladeTreeSet bld2;
		Value val2, newval;
		//For all cl blade-value mappings
		Iterator it2 = cl.map.entrySet().iterator();
		while(it2.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it2.next();
			bld2 = (BladeTreeSet) entry.getKey();
			val2 = (Value) entry.getValue();
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
	 * Computes the geometric product with the specified scalar.
	 * @param scalar the scalar of the geometric product.
	 * @return a new element from the geometric product with the specified scalar.
	 */
	public CliffordTreeSet gP(double scalar)
	{
		//Creating a new empty element
		CliffordTreeSet newcl = new CliffordTreeSet();
		double newvalue;
		//Temporary reference variables
		Map.Entry entry;
		BladeTreeSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeTreeSet) entry.getKey();
			val = (Value) entry.getValue();
			//Resulting value for newcl bld
			newvalue = val.value * scalar;
			//Tresholding
			if(java.lang.Math.abs(newvalue) > 1e-13)
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
	public CliffordTreeSet gP(final CliffordTreeSet cl)
	{
		//Creating a new empty element
		CliffordTreeSet newcl = new CliffordTreeSet();
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeTreeSet bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator(), it2;
		while(it1.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it1.next();
			bld1 = (BladeTreeSet) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//For all cl blade-value mappings
			it2 = cl.map.entrySet().iterator();
			while(it2.hasNext()) {
				//Getting blade and value
				entry2 = (Map.Entry) it2.next();
				bld2 = (BladeTreeSet) entry2.getKey();
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
		return newcl;
	}

	/**
	 * Computes the wedge product with the specified element.
	 * @param cl the second element of the wedge product.
	 * @return a new element from the wedge product with the specified element.
	 */
	public CliffordTreeSet wP(final CliffordTreeSet cl)
	{
		//Creating a new empty element
		CliffordTreeSet newcl = new CliffordTreeSet();
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeTreeSet bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator(), it2;
		while(it1.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it1.next();
			bld1 = (BladeTreeSet) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//For all cl blade-value mappings
			it2 = cl.map.entrySet().iterator();
			while(it2.hasNext()) {
				//Getting blade and value
				entry2 = (Map.Entry) it2.next();
				bld2 = (BladeTreeSet) entry2.getKey();
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
		return newcl;
	}

	/**
	 * Computes the left contraction with the specified element.
	 * @param cl the second element of the left contraction.
	 * @return a new element from the left contraction with the specified element.
	 */
	public CliffordTreeSet lC(final CliffordTreeSet cl)
	{
		//Creating a new empty element
		CliffordTreeSet newcl = new CliffordTreeSet();
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeTreeSet bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator(), it2;
		while(it1.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it1.next();
			bld1 = (BladeTreeSet) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//For all cl blade-value mappings
			it2 = cl.map.entrySet().iterator();
			while(it2.hasNext()) {
				//Getting blade and value
				entry2 = (Map.Entry) it2.next();
				bld2 = (BladeTreeSet) entry2.getKey();
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
		return newcl;
	}

	/**
	 * Computes the right contraction with the specified element.
	 * @param cl the second element of the right contraction.
	 * @return a new element from the right contraction with the specified element.
	 */
	public CliffordTreeSet rC(final CliffordTreeSet cl)
	{
		//Creating a new empty element
		CliffordTreeSet newcl = new CliffordTreeSet();
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeTreeSet bld1, bld2, newbld;
		Value val1, val2, newval;
		double result;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator(), it2;
		while(it1.hasNext()) {
			//Getting blade and value
			entry1 = (Map.Entry) it1.next();
			bld1 = (BladeTreeSet) entry1.getKey();
			val1 = (Value) entry1.getValue();
			//For all cl blade-value mappings
			it2 = cl.map.entrySet().iterator();
			while(it2.hasNext()) {
				//Getting blade and value
				entry2 = (Map.Entry) it2.next();
				bld2 = (BladeTreeSet) entry2.getKey();
				val2 = (Value) entry2.getValue();
				//Computing resulting blade
				newbld = bld1.rigthContraction(bld2);
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
		return newcl;
	}

	/**
	 * Compares this element with the specified element for equality.
	 * Two elements are considered equals if they have same blades and corresponding values differing less than tollerance.
	 * @param cl the second element that is to be compared.
	 * @param tol the tollerance.
	 * @return true if this element and the specified element are equals, false otherwise.
	 */
	public boolean equals(final CliffordTreeSet cl, double tol)
	{
		//Verifyng if elements have the same size (number of blade-value mappings)
		if(map.size() != cl.map.size())
			return false;
		//Temporary reference variables
		Map.Entry entry1, entry2;
		BladeTreeSet bld1, bld2;
		Value val1, val2;
		//For all blade-value mappings
		Iterator it1 = map.entrySet().iterator();
		Iterator it2 = cl.map.entrySet().iterator();
		while(it1.hasNext()) {
			entry1 = (Map.Entry) it1.next();
			entry2 = (Map.Entry) it2.next();
			//Getting blades
			bld1 = (BladeTreeSet) entry1.getKey();
			bld2 = (BladeTreeSet) entry2.getKey();
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
	 * Creates and returns an element deeply cloning this element.
	 */
	public Object clone()
	{
		//Creating a new empty element
		CliffordTreeSet newcl = new CliffordTreeSet();
		//Temporary reference variables
		Map.Entry entry;
		BladeTreeSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeTreeSet) entry.getKey();
			val = (Value) entry.getValue();
			//Adding a copy of blade-value mapping to newcl
			newcl.map.put(bld.clone(), val.clone());
		}
		return newcl;
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
		BladeTreeSet bld;
		Value val;
		//For all blade-value mappings
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (BladeTreeSet) entry.getKey();
			val = (Value) entry.getValue();
			//Printing blade-value mapping
			str += bld.toString() + "\t\t==>\t\t" + val.toString() + "\n";
		}
		str += "----------------\n";
		//Returning the string
		return str;
	}

}
