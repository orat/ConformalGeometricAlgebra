/**
 * Homogeneous.java
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
 * <p>This class represents the Homogeneous coordinates.</p>
 * @version <p>0.9</p>
 * @author <p>Realized by <a href="mailto:vassallo@csai.unipa.it">Giorgio Vassallo</a>, <a href="mailto:pietro.brignola@libero.it">Pietro Brignola</a>, November 2002.</p>
 */
public class Homogeneous extends Clifford{

	/**
	 * Homogeneous generalized model initialization flag.
	 */
	private static boolean initialized;

	/**
	 * Embedded Euclidean space dimension.
	 */
	private static int n;

	/**
	 * e = e- + e+, where e+ * e+ = 1 and e- * e- = -1.
	 */
	private static Clifford e;

	/**
	 * eo = 0.5 * (e- - e+), {eo, e} forms a null basis eo * eo = e * e = 0.
	 */
	private static Clifford eo;

	/**
	 * he = 0.5 * e.
	 */
	private static Clifford he;

	/**
	 * E = e ^ eo is the unit pseudoscalar of Minkowski plane R1,1.
	 */
	private static Clifford E;

	/**
	 * Initializes Homogeneous generalized model.
	 */
	public static void init(int dimension){
		//Init can be performed only once
		if(initialized)
			throw new RuntimeException("Homogeneous generalized model already initialized.");
		//Setting embedded Euclidean space dimension.
		n = dimension;
		//Initializing Algebra's signature
		int p = n + 1;
		Clifford.init(p, 1);
		//Initializing Homogeneous generalized model
		e = new Clifford();
		e.put(1 << (p - 1), 1.0);
		e.put(1 << p, 1.0);
		eo = new Clifford();
		eo.put(1 << (p - 1), -0.5);
		eo.put(1 << p, 0.5);
		he = new Clifford();
		he.put(1 << (p - 1), 0.5);
		he.put(1 << p, 0.5);
		E = e.wP(eo);
		//Initializzation performed
		initialized = true;
	}

	/**
	 *Creates and returns an element in Homogeneous generalized model.
	 */
	public Homogeneous(){
		//Homogeneous generalized model must be initialized
		if(!initialized)
			throw new RuntimeException("Homogeneous generalized model initialization required.");
	}


	/**
	 * Creates and returns an element in Homogeneous generalized model.
	 * Maps a grade 1 vector X from Euclidean Rn,0 to Homogeneous generalized Rn+1,1 coordinates:
	 * x = X + 0.5 (X * X * e) + eo = X + (0.5 * X * X - 0.5) * e+ + (0.5 * X * X + 0.5) * e-
	 *
	 */
	public Homogeneous(Clifford cl) throws RuntimeException{
		//Homogeneous generalized model must be initialized
		if(!initialized)
			throw new RuntimeException("Homogeneous generalized model initialization required.");
		//Temporary
		double uqm = 0.0;
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//For all blade-value mappings
		Iterator it = cl.map.entrySet().iterator();
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Not considering e+, e- dimensions
			if(bld.blade == 1 << n)
				break;
			//Checking for grade 1 blade
			if(gradeTable[bld.blade] != 1)
				throw new IllegalArgumentException("Invalid argument for this operation: Clifford vector required.");
			//Copyng blade-value mapping
			map.put(bld, val);
			//Computing unsigned quad mod
			uqm += val.value * val.value;
		}
		//Adding e+ dimension
		put(1 << (p - 1), 0.5 * uqm - 0.5);
		//Adding e- dimension
		put(1 << p, 0.5 * uqm + 0.5);
	}

	/**
	 *Creates and returns Euclidean vector inverse of a this element.
	 *//*
	public Clifford vInversion() throws RuntimeException{
		//Creating a new empty element
		Clifford newcl = new CElement();
		//Temporary
		double sm = 0.0;
		//Temporary reference variables
		Blade bld;
		Value val;
		//Defining ordered array of blades and values of cl
		Object[] arrbld = map.keySet().toArray();
		Object[] arrval = map.values().toArray();
		//For all cl blade-value mappings
		for(int x = 0; x < arrbld.length; x ++){
			bld = (Blade) arrbld[x];
			val = (Value) arrval[x];
			//Checking for grade 1 blade
			if(gradeTable[bld.blade] != 1)
				throw new RuntimeException("Only vectors can be inverted.");
			sm += val.value * (((signMask & bld.blade) != 0) ? -val.value : val.value);
		}
		//For all cl blade-value mappings
		for(int x = 0; x < arrbld.length; x ++){
			bld = (Blade) arrbld[x];
			val = (Value) arrval[x];
			//Adding new blade-value mapping to newcl
			newcl.map.put(new Blade(bld.blade), new Value(val.value / sm));
		}
		return newcl;//il tresholding ???
	}*/

	/**
	 *Creates and returns a new element reflecting this on cl.
	 *//*
	public Clifford reflection(final Clifford cl){
		double mod2 = mod(cl);
		double dualdot = 2.0 * dot(cl2);
		return (gP(mod2)).sub(cl2.gP(dualdot));
	}*/

	/**
	 *Creates and returns the normalized of a specified element.
	 *//*
	public CElement normalization(CElement cl){
		//Creating a new empty element
		CElement newcl = new CElement();
		//Vector Module
		double vmod = java.lang.Math.sqrt(mod(cl));
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//Defining an iterator on cl blade-value mappings
		Iterator it = cl.blades.entrySet().iterator();
		//For all cl blade-value mappings
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Adding new blade-value mapping to newcl
			newcl.blades.put(new Blade(bld.blade), new Value(val.value / vmod));
		}
		return newcl;//il tresholding ???
	}*/

	/**
	 *Creates and returns the blade-normalized of a specified element.
	 *//*
	public CElement normalization1(CElement cl, int blade){
		//Module
		double mod = cl.get(blade);
		//If blade is not present
		if(mod == 0.0)
			return (CElement) cl.clone();
		//Creating a new empty element
		CElement newcl = new CElement();
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//Defining an iterator on cl blade-value mappings
		Iterator it = cl.blades.entrySet().iterator();
		//For all cl blade-value mappings
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			//Adding new blade-value mapping to newcl
			newcl.blades.put(new Blade(bld.blade), new Value(val.value / mod));
		}
		return newcl;//il tresholding ???
	}*/

	/**
	 *Normalizes a specified element respect the value of the bivector E.
	 */
	public void normalize2(){
		double Evalue = get(3 << (p - 1));
		//Temporary reference variables
		Map.Entry entry;
		Blade bld;
		Value val;
		//Defining an iterator on cl blade-value mappings
		Iterator it = map.entrySet().iterator();
		//For all cl blade-value mappings
		while(it.hasNext()) {
			//Getting blade and value
			entry = (Map.Entry) it.next();
			bld = (Blade) entry.getKey();
			val = (Value) entry.getValue();
			val.value /= Evalue;
		}
	}

	/**
	 *Maps a grade 1 element from Euclidean Rn,0 to Generalized Homogeneous Rn+1,1 Coordinate.
	 *X = x + 0.5 (x * x * e) + e0
	 *//*
	public void eToH(CElement cl) throws RuntimeException{
		//Temporary
		double sm = 0.0;
		//Temporary reference variables
		Blade bld;
		Value val;
		//Defining ordered array of blades and values of cl
		Object[] arrbld = cl.blades.keySet().toArray();
		Object[] arrval = cl.blades.values().toArray();
		//For all cl blade-value mappings
		for(int x = 0; x < arrbld.length; x ++){
			if(x == p)
				break;
			bld = (Blade) arrbld[x];
			val = (Value) arrval[x];
			//Checking for grade 1 blade
			if(gradeTable[bld.blade] != 1)
				throw new RuntimeException("Only vectors can be mapped to" +
											" Generalized Homogeneous Coordinate.");
			sm += val.value * val.value;
		}
		cl.put(1 << (p - 1), 0.5 * sm - 0.5);
		cl.put(1 << p, 0.5 * sm + 0.5);
	}*/

	/**
	 *Maps an element from Homogeneous to Euclidean Coordinate.
	 *x = (X ^ E) * E
	 *//*
	public void hToE(CElement cl){
		cl.remove(1 << (p - 1));
		cl.remove(1 << p);
	}*/

	/**
	 *.
	 */
	public void eToProj(){
		Clifford newcl = e.gP(this).add(E);
		map = newcl.map;
	}

	/**
	 *.
	 */
	public void projToE(){
		normalize2();
		map.remove(new Blade(3 << (p - 1))); //cl = this.sub(E);
		Clifford newcl = eo.lC(this).gP(-1.0);
		map = newcl.map;
	}

	/**
	 *Euclidean Coordinates to traslation operator.
	 *//*
	public void eToTr(CElement cl){
		cl = gP(cl, he);
		cl.put(0, 1.0);
	}*/

}

