/**
 * Versor.java
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
 * <p>This class represents a basis versor.</p>
 * <p>It is an utility class for the BladeTreeSet class.</p>
 * @version <p>0.9</p>
 * @author <p>Realized by <a href="mailto:vassallo@csai.unipa.it">Giorgio Vassallo</a>, <a href="mailto:pietro.brignola@libero.it">Pietro Brignola</a>, November 2002.</p>
 * @see Blade Value
 */
class Versor implements Comparable{

	/**
	 * Basis versor.
	 */
	public int versor;

	/**
	 * Creates and returns a Versor object representing a basis versor.
	 * @param versor the int representing the basis versor.
	 */
	public Versor(int versor)
	{
		this.versor = versor;
	}

	/**
	 * Creates and returns an new Object deeply cloning this Object.
	 */
	public Object clone()
	{
		return new Versor(versor);
	}

	/**
	 * Compare this object with another comparing versor field.
	 */
	public int compareTo(Object object)
	{
		int v = ((Versor) object).versor;
		return (versor < v ? -1 : (versor == v ? 0 : 1));
	}

	/**
	 * Returns a string representation of the versor.
	 * @return the string representation of the versor.
	 */
	public String toString()
	{
		return "e" + String.valueOf(versor);
	}

}
