/**
 * Blade.java
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

/**
 * <p>This class represents a blade (wedge product of generic basis vectors).</p>
 * <p>It is an utility class for the Clifford class.</p>
 * @version <p>0.9</p>
 * @author <p>Realized by <a href="mailto:vassallo@csai.unipa.it">Giorgio Vassallo</a>, <a href="mailto:pietro.brignola@libero.it">Pietro Brignola</a>, November 2002.</p>
 * @see BladeBitSet
 * @see BladeTreeSet
 */
class Blade implements Comparable{

	/**
	 * Each bit 1 in the binary rappresentation of this field corresponds to the presence of a basis vector in the blade (starting from l.s.b.).
	 */
	public int blade;

	/**
	 * Creates and returns a new Blade representing the specified blade.
	 * @param blade the int whose binary rappresentation corresponds to the presence of basis vectors (starting from l.s.b.).
	 */
	public Blade(int blade)
	{
		this.blade = blade;
	}

	/**
	 * Compare this object with another comparing blade fields.
	 */
	public int compareTo(Object object)
	{
		int b = ((Blade) object).blade;
		return (blade < b ? -1 : (blade == b ? 0 : 1));
	}

	/**
	 * Returns a string representation of this blade.
	 * @return the string representation of this blade.
	 */
	public String toString()
	{
		//String representation of the blade
		String str = new String();
		//For all versors
		for(int i = 0; i < 8; i++)
			if((blade & (1 << i)) != 0)
				str += "e" + String.valueOf(i);
		//Scalar case
		if(str.length() == 0)
			str += "scalar";
		//Returning the string
		return str;
	}

}
