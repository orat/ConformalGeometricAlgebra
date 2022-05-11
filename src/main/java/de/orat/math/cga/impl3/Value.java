/**
 * Value.java
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
 * <p>This class wrapps the double value of a blade (wedge product of generic basis versors).</p>
 * <p>it is an utility class for the Clifford, CliffordBitSet and CliffordTreeSet classes.</p>
 * @version <p>0.9</p>
 * @author <p>Realized by <a href="mailto:vassallo@csai.unipa.it">Giorgio Vassallo</a>, <a href="mailto:pietro.brignola@libero.it">Pietro Brignola</a>, November 2002.</p>
 * @see Blade Versor
 */
class Value implements Comparable{

	/**
	 * Double value of a blade.
	 */
	public double value;

	/**
	 * Creates and returns a Value object representing the double value of a blade.
	 * @param value the double value of a blade.
	 */
	public Value(double value)
	{
		this.value = value;
	}

	/**
	 * Creates and returns an new Object deeply cloning this Object.
	 */
	public Object clone()
	{
		return new Value(value);
	}

	/**
	 * Compare this object with another comparing value field.
	 */
	public int compareTo(Object object)
	{
		double v = ((Value) object).value;
		return (value < v ? -1 : (value == v ? 0 : 1));
	}
	
	/**
	 * Returns a string representation of this value.
	 * @return the string representation of this value.
	 */
	public String toString()
	{
		return String.valueOf(value);
	}

}
