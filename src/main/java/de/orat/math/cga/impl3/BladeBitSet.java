/**
 * BladeBitSet.java
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

import java.util.BitSet;

/**
 * <p>This class represents a BitSet implementation of a blade (wedge product of generic basis versors).</p>
 * <p>It is an utility class for the CliffordBitSet class.</p>
 * @version <p>0.9</p>
 * @author <p>Realized by <a href="mailto:vassallo@csai.unipa.it">Giorgio Vassallo</a>, <a href="mailto:pietro.brignola@libero.it">Pietro Brignola</a>, November 2002.</p>
 * @see Blade
 * @see BladeTreeSet
 */
class BladeBitSet implements Comparable{

	/**
	 * Bit set of the versors present in this blade. Empty blade represents a scalar.
	 */
	private BitSet bitset;

	/**
	 * Creates and returns an new BadeBitSet representing a scalar.
	 */
	BladeBitSet()
	{
		bitset = new BitSet();
	}

	/**
	 * Creates and returns an new BadeBitSet representing the specified versor.
	 * @throws IllegalArgumentException if versor is invalid.
	 * @param versor the versor of this blade.
	 */
	BladeBitSet(int versor) throws IllegalArgumentException{
		bitset = new BitSet();
		//Setting versor
		bitset.set(versor, true);
	}

	/**
	 * Creates and returns an new BadeBitSet representing the specified blade.
	 * @throws IllegalArgumentException if blade mask is invalid.
	 * @param blade the mask representing the specified blade (format is "i, j, ...", where i, j are versor indexes).
	 */
	BladeBitSet(String blade) throws IllegalArgumentException{
		bitset = new BitSet();
		//Scalar
		if(blade.equals(""))
			return;
		//Setting versors
		String[] versors = blade.split(",");
		for(int i = 0; i < versors.length; i++)
			bitset.set(Integer.parseInt(versors[i]));
	}

	/**
	 * Creates and returns an new Object deeply cloning this Object.
	 */
	public Object clone(){
		//Creating a new empty blade
		BladeBitSet newbld = new BladeBitSet();
		//Cloning the bit set of the versors present in this blade
		newbld.bitset = (BitSet) bitset.clone();
		//Returning new Object
		return newbld;
	}

	/**
	 * Gets specified versor in this blade.
	 * @param versor the specified versor to be get in this blade.
	 * @return true if the specified versor is present in this blade.
	 */
	boolean getVersor(int versor){
		return bitset.get(versor);
	}

	/**
	 * Sets specified versor in this blade.
	 * @param versor the specified versor to be set in this blade.
	 * @param present the flag corresponding to the presence of the versor in this blade.
	 */
	void setVersor(int versor, boolean present){
		bitset.set(versor, present);
	}

	/**
	 * Returns the grade of this blade.
	 * @return the grade of the blade.
	 */
	int getGrade(){
		return bitset.cardinality();
	}

	/**
	 * Returns highest dimension present in this blade.
	 * @return highest dimension present in this blade.
	 */
	int getMaxDimension(){
		return bitset.length() - 1;
	}

	/**
	 * Compare this object with another comparing versors.
	 */
	public int compareTo(Object object){
		BladeBitSet bld = (BladeBitSet) object;
		int grade1 = bitset.cardinality();
		int grade2 = bld.bitset.cardinality();
		//Comparing grades
		if(grade1 < grade2)
			return -1;
		if(grade1 > grade2)
			return 1;
		//Comparing versors
		int i1 = bitset.nextSetBit(0);
		int i2 = bld.bitset.nextSetBit(0);
		while((i1 >= 0) || (i2 >= 0)){
			if(i1 < i2)
				return -1;
			if(i1 > i2)
				return 1;
			//Getting next set bit index
			i1 = bitset.nextSetBit(i1 + 1);
			i2 = bld.bitset.nextSetBit(i2 + 1);
		}
		//Blades are equals
		return 0;
	}

	/**
	 * Computes the sign of the product with the specified blade.
	 * @param bld the second blade of the product.
	 * @return true if the sign of the product with the specified blades is negative, false otherwise.
	 */
	boolean getSign(BladeBitSet bld){
		int sign = 0;
		int count = 0;
		//Indexes
		int i1, i2, ix = bld.bitset.nextSetBit(0);
		//Counting versors swapping
		for(i1 = bitset.nextSetBit(0); i1 >= 0; i1 = bitset.nextSetBit(i1 + 1)){
			sign ^= (count & 1);
			for(i2 = ix; (i2 >= 0) && (i2 < i1); i2 = bld.bitset.nextSetBit(i2+1)){
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
	BladeBitSet geometricProduct(BladeBitSet bld){
		BladeBitSet newbld = (BladeBitSet) clone();
		newbld.bitset.xor(bld.bitset);
		return newbld;
	}

	/**
	 * Computes the wedge product with the specified blade.
	 * @param bld the second blade of the wedge product.
	 * @return null if blades have common versors or a new blade from the wedge product with the specified blade.
	 */
	BladeBitSet wedgeProduct(BladeBitSet bld){
		BitSet newbitset = (BitSet) bitset.clone();
		newbitset.and(bld.bitset);
		if(! newbitset.isEmpty())
			return null;
		BladeBitSet newbld = (BladeBitSet) clone();
		newbld.bitset.xor(bld.bitset);
		return newbld;
	}

	/**
	 * Computes the left contraction with the specified blade.
	 * @param bld the second blade of the left contraction.
	 * @return null if versors are not a subset of the specified blade, or a new blade from the left contraction with the specified blade.
	 */
	BladeBitSet leftContraction(BladeBitSet bld){
		BitSet newbitset = (BitSet) bitset.clone();
		newbitset.andNot(bld.bitset);
		if(! newbitset.isEmpty())
			return null;
		BladeBitSet newbld = (BladeBitSet) clone();
		newbld.bitset.xor(bld.bitset);
		return newbld;
	}

	/**
	 * Computes the right contraction with the specified blade.
	 * @param bld the second blade of the right contraction.
	 * @return null if versors are not a subset of the specified blade, or a new blade from the left contraction with the specified blade.
	 */
	BladeBitSet rightContraction(BladeBitSet bld){
		BitSet newbitset = (BitSet) bld.bitset.clone();
		newbitset.andNot(bitset);
		if(! newbitset.isEmpty())
			return null;
		BladeBitSet newbld = (BladeBitSet) clone();
		newbld.bitset.xor(bld.bitset);
		return newbld;
	}

	/**
	 * Returns a string representation of this blade.
	 * @return the string representation of this blade.
	 */
	public String toString(){
		//String representation of the blade
		String str = new String();
		//For all versors
		for(int i = bitset.nextSetBit(0); i >= 0; i = bitset.nextSetBit(i + 1))
			str += "e" + String.valueOf(i);
		//Scalar case
		if(str.length() == 0)
			str += "scalar";
		//Returning the string
		return str;
	}

}

