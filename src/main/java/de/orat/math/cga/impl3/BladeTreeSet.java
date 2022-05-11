/**
 * BladeTreeSet.java
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
import java.util.TreeSet;

/**
 * <p>This class represents a TreeSet implementation of a blade (wedge product of generic basis versors).</p>
 * <p>It is an utility class for the CliffordTreeSet class.</p>
 * @version <p>0.9</p>
 * @author <p>Realized by <a href="mailto:vassallo@csai.unipa.it">Giorgio Vassallo</a>, <a href="mailto:pietro.brignola@libero.it">Pietro Brignola</a>, November 2002.</p>
 * @see Blade
 * @see BladeBitSet
 */
public class BladeTreeSet implements Comparable{

	/**
	 * Tree set of the versors present in this blade. Empty blade represents a scalar.
	 */
	private TreeSet treeset;

	/**
	 * Creates and returns an new empty blade representing a scalar.
	 */
	public BladeTreeSet()
	{
		treeset = new TreeSet();
	}

	/**
	 * Creates and returns an new Object deeply cloning this Object.
	 */
	public Object clone()
	{
		//Creating a new empty blade
		BladeTreeSet newbld = new BladeTreeSet();
		//Defining an iterator on versors
		Iterator it = treeset.iterator();
		//For all versors
		while(it.hasNext())
			//Adding a copy to newbld
			newbld.treeset.add(((Versor) it.next()).clone());
		//Returning new Object
		return newbld;
	}

	/**
	 * Puts specified versor in this blade if it is not already present.
	 * @param versor the specified versor to be put to the blade.
	 */
	public void put(int versor)
	{
		treeset.add(new Versor(versor));
	}

	/**
	 * Compare this object with another comparing versors.
	 */
	public int compareTo(Object object)
	{
		BladeTreeSet bld = (BladeTreeSet) object;
		int result;
		int grade1 = treeset.size();
		int grade2 = bld.treeset.size();
		//Comparing grades
		if(grade1 < grade2)
			return -1;
		if(grade1 > grade2)
			return 1;
		//Comparing versors
		Iterator it1 = treeset.iterator();
		Iterator it2 = bld.treeset.iterator();
		while(it1.hasNext()){
			result = ((Versor) it1.next()).compareTo((Versor) it2.next());
			if(result!=0)
				return result;
		}
		//Blades are equals
		return 0;
	}

	/**
	 * Returns the grade of this blade.
	 * @return the grade of the blade.
	 */
	public int getGrade()
	{
		return treeset.size();
	}

	/**
	 * Computes the sign of the product with the specified blade.
	 * @param bld the second blade of the product.
	 * @return true if the sign of the product with the specified blade is negative, false otherwise.
	 */
	boolean getSign(BladeTreeSet bld)
	{
		int sign = 0;
		int count = 0;
		//Indexes
		int i1, i2, ix = 0;
		//Defining ordered arrays of versors
		Versor[] arrvers1 = (Versor[]) treeset.toArray(new Versor[0]);
		Versor[] arrvers2 = (Versor[]) bld.treeset.toArray(new Versor[0]);
		//Counting versors swapping
		for(i1 = 0; i1 < arrvers1.length; ++i1){
			sign ^= (count & 1);
			for(i2 = ix; (i2<arrvers2.length)&&((arrvers2[i2]).versor<arrvers1[i1].versor);++i2){
				sign ^= 1;
				++count;
			}
			ix = i2;
		}
		return sign != 0;
	}

	/**
	 * Computes the geometric product with the specified blade.
	 * @param bld the second blade of the geometric product.
	 * @return new blade from the geometric product with the specified blade.
	 */
	BladeTreeSet geometricProduct(BladeTreeSet bld)
	{
		BladeTreeSet bld1 = (BladeTreeSet) clone();
		BladeTreeSet bld2 = (BladeTreeSet) bld.clone();
		BladeTreeSet newbld = (BladeTreeSet) clone();
		newbld.treeset.removeAll(bld2.treeset);
		bld2.treeset.removeAll(bld1.treeset);
		newbld.treeset.addAll(bld2.treeset);
		return newbld;
	}

	/**
	 * Computes the wedge product with the specified blade.
	 * @param bld the second blade of the wedge product.
	 * @return null if blades have common versors or a new blade from the wedge product with the specified blade.
	 */
	BladeTreeSet wedgeProduct(BladeTreeSet bld)
	{
		BladeTreeSet newbld = (BladeTreeSet) clone();
		newbld.treeset.retainAll(bld.treeset);
		if(! newbld.treeset.isEmpty())
			return null;
		newbld = (BladeTreeSet) clone();
		newbld.treeset.addAll(bld.treeset);
		return newbld;
	}

	/**
	 * Computes the left contraction with the specified blade.
	 * @param bld the second blade of the left contraction.
	 * @return null if versors are not a subset of the specified blade, or a new blade from the left contraction with the specified blade.
	 */
	BladeTreeSet leftContraction(BladeTreeSet bld)
	{
		if(! bld.treeset.containsAll(treeset))
			return null;
		BladeTreeSet newbld = (BladeTreeSet) bld.clone();
		newbld.treeset.removeAll(treeset);
		return newbld;
	}

	/**
	 * Computes the rignt contraction with the specified blade.
	 * @param bld the second blade of the right contraction.
	 * @return null if versors of the specified blade are not a subset of this blade, or a new blade from the left contraction with the specified blade.
	 */
	BladeTreeSet rigthContraction(BladeTreeSet bld)
	{
		if(! treeset.containsAll(bld.treeset))
			return null;
		BladeTreeSet newbld = (BladeTreeSet) clone();
		newbld.treeset.removeAll(bld.treeset);
		return newbld;
	}

	/**
	 * Returns a string representation of this blade.
	 * @return the string representation of this blade.
	 */
	public String toString()
	{
		//String representation of the blade
		String str = new String();
		//Defining an iterator on versors
		Iterator it = treeset.iterator();
		//For all blade-value mappings
		while(it.hasNext())
			str += ((Versor) it.next()).toString();
		//Scalar case
		if(str.length() == 0)
			str += "scalar";
		//Returning the string
		return str;
	}

}
