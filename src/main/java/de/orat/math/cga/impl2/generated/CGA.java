// 3D Projective Geometric Algebra
// Written by a generator written by enki.
//package de.dhbw.rahmlab.ganjatest.impl;
package de.orat.math.cga.impl2.generated;

import java.util.Arrays;

public class CGA {
	// just for debug and print output, the basis names
	public static final String[] _basis = new String[] { "1","e1","e2","e3","e4","e5","e12","e13","e14","e15","e23","e24","e25","e34","e35","e45","e123","e124","e125","e134","e135","e145","e234","e235","e245","e345","e1234","e1235","e1245","e1345","e2345","e12345" };
	public static final int _basisLength = 32;

        // WOKAROUND private--> public
	public double[] _mVec = new double[CGA._basisLength];

	// You must ensure that it's inner _mVec will never be changed.
	private static final CGA Empty = new CGA();

        
        //WOKRAOUND
        public CGA(double[] mVec){
            _mVec = mVec;
        }
        
	/// <summary>
	/// Ctor
	/// </summary>
	/// <param name="f"></param>
	/// <param name="idx"></param>
	public CGA(int idx, double value) {
		Arrays.fill(this._mVec, 0d);
		this._mVec[idx] = value;
	}

	/// <summary>
	/// Ctor
	/// </summary>
	public CGA() {
		Arrays.fill(this._mVec, 0d);
	}

	/// <summary>
	/// Ctor
	/// </summary>
	public CGA(final CGA other) {
		this._mVec = other._mVec;
	}

	public double get(int idx) {
		return this._mVec[idx];
	}

	public void set(int idx, double value) {
		this._mVec[idx] = value;
	}



	/// <summary>
	/// unary operator
	/// CGA.Reverse : res = ~a
	/// Reverse the order of the basis blades.
	/// </summary>
	public static CGA unop_Reverse (CGA a_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;

		res[0]=a[0];
		res[1]=a[1];
		res[2]=a[2];
		res[3]=a[3];
		res[4]=a[4];
		res[5]=a[5];
		res[6]=-a[6];
		res[7]=-a[7];
		res[8]=-a[8];
		res[9]=-a[9];
		res[10]=-a[10];
		res[11]=-a[11];
		res[12]=-a[12];
		res[13]=-a[13];
		res[14]=-a[14];
		res[15]=-a[15];
		res[16]=-a[16];
		res[17]=-a[17];
		res[18]=-a[18];
		res[19]=-a[19];
		res[20]=-a[20];
		res[21]=-a[21];
		res[22]=-a[22];
		res[23]=-a[23];
		res[24]=-a[24];
		res[25]=-a[25];
		res[26]=a[26];
		res[27]=a[27];
		res[28]=a[28];
		res[29]=a[29];
		res[30]=a[30];
		res[31]=a[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// unary operator
	/// CGA.Dual : res = !a
	/// Poincare duality operator.
	/// </summary>
	public static CGA unop_Dual (CGA a_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;

		res[0]=-a[31];
		res[1]=-a[30];
		res[2]=a[29];
		res[3]=-a[28];
		res[4]=a[27];
		res[5]=a[26];
		res[6]=a[25];
		res[7]=-a[24];
		res[8]=a[23];
		res[9]=a[22];
		res[10]=a[21];
		res[11]=-a[20];
		res[12]=-a[19];
		res[13]=a[18];
		res[14]=a[17];
		res[15]=-a[16];
		res[16]=a[15];
		res[17]=-a[14];
		res[18]=-a[13];
		res[19]=a[12];
		res[20]=a[11];
		res[21]=-a[10];
		res[22]=-a[9];
		res[23]=-a[8];
		res[24]=a[7];
		res[25]=-a[6];
		res[26]=-a[5];
		res[27]=-a[4];
		res[28]=a[3];
		res[29]=-a[2];
		res[30]=a[1];
		res[31]=a[0];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// unary operator
	/// CGA.Conjugate : res = a.Conjugate()
	/// Clifford Conjugation
	/// </summary>
	public  CGA Conjugate ()
	{
		double[] res = new double[CGA._basisLength];
		

		res[0]=this._mVec[0];
		res[1]=-this._mVec[1];
		res[2]=-this._mVec[2];
		res[3]=-this._mVec[3];
		res[4]=-this._mVec[4];
		res[5]=-this._mVec[5];
		res[6]=-this._mVec[6];
		res[7]=-this._mVec[7];
		res[8]=-this._mVec[8];
		res[9]=-this._mVec[9];
		res[10]=-this._mVec[10];
		res[11]=-this._mVec[11];
		res[12]=-this._mVec[12];
		res[13]=-this._mVec[13];
		res[14]=-this._mVec[14];
		res[15]=-this._mVec[15];
		res[16]=this._mVec[16];
		res[17]=this._mVec[17];
		res[18]=this._mVec[18];
		res[19]=this._mVec[19];
		res[20]=this._mVec[20];
		res[21]=this._mVec[21];
		res[22]=this._mVec[22];
		res[23]=this._mVec[23];
		res[24]=this._mVec[24];
		res[25]=this._mVec[25];
		res[26]=this._mVec[26];
		res[27]=this._mVec[27];
		res[28]=this._mVec[28];
		res[29]=this._mVec[29];
		res[30]=this._mVec[30];
		res[31]=-this._mVec[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// unary operator
	/// CGA.Involute : res = a.Involute()
	/// Main involution
	/// </summary>
	public  CGA Involute ()
	{
		double[] res = new double[CGA._basisLength];
		

		res[0]=this._mVec[0];
		res[1]=-this._mVec[1];
		res[2]=-this._mVec[2];
		res[3]=-this._mVec[3];
		res[4]=-this._mVec[4];
		res[5]=-this._mVec[5];
		res[6]=this._mVec[6];
		res[7]=this._mVec[7];
		res[8]=this._mVec[8];
		res[9]=this._mVec[9];
		res[10]=this._mVec[10];
		res[11]=this._mVec[11];
		res[12]=this._mVec[12];
		res[13]=this._mVec[13];
		res[14]=this._mVec[14];
		res[15]=this._mVec[15];
		res[16]=-this._mVec[16];
		res[17]=-this._mVec[17];
		res[18]=-this._mVec[18];
		res[19]=-this._mVec[19];
		res[20]=-this._mVec[20];
		res[21]=-this._mVec[21];
		res[22]=-this._mVec[22];
		res[23]=-this._mVec[23];
		res[24]=-this._mVec[24];
		res[25]=-this._mVec[25];
		res[26]=this._mVec[26];
		res[27]=this._mVec[27];
		res[28]=this._mVec[28];
		res[29]=this._mVec[29];
		res[30]=this._mVec[30];
		res[31]=-this._mVec[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.Mul : res = a * b
	/// The geometric product.
	/// </summary>
	public static CGA binop_Mul (CGA a_param, CGA b_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;
		double[] b = b_param._mVec;

		res[0]=b[0]*a[0]+b[1]*a[1]+b[2]*a[2]+b[3]*a[3]+b[4]*a[4]-b[5]*a[5]-b[6]*a[6]-b[7]*a[7]-b[8]*a[8]+b[9]*a[9]-b[10]*a[10]-b[11]*a[11]+b[12]*a[12]-b[13]*a[13]+b[14]*a[14]+b[15]*a[15]-b[16]*a[16]-b[17]*a[17]+b[18]*a[18]-b[19]*a[19]+b[20]*a[20]+b[21]*a[21]-b[22]*a[22]+b[23]*a[23]+b[24]*a[24]+b[25]*a[25]+b[26]*a[26]-b[27]*a[27]-b[28]*a[28]-b[29]*a[29]-b[30]*a[30]-b[31]*a[31];
		res[1]=b[1]*a[0]+b[0]*a[1]-b[6]*a[2]-b[7]*a[3]-b[8]*a[4]+b[9]*a[5]+b[2]*a[6]+b[3]*a[7]+b[4]*a[8]-b[5]*a[9]-b[16]*a[10]-b[17]*a[11]+b[18]*a[12]-b[19]*a[13]+b[20]*a[14]+b[21]*a[15]-b[10]*a[16]-b[11]*a[17]+b[12]*a[18]-b[13]*a[19]+b[14]*a[20]+b[15]*a[21]+b[26]*a[22]-b[27]*a[23]-b[28]*a[24]-b[29]*a[25]-b[22]*a[26]+b[23]*a[27]+b[24]*a[28]+b[25]*a[29]-b[31]*a[30]-b[30]*a[31];
		res[2]=b[2]*a[0]+b[6]*a[1]+b[0]*a[2]-b[10]*a[3]-b[11]*a[4]+b[12]*a[5]-b[1]*a[6]+b[16]*a[7]+b[17]*a[8]-b[18]*a[9]+b[3]*a[10]+b[4]*a[11]-b[5]*a[12]-b[22]*a[13]+b[23]*a[14]+b[24]*a[15]+b[7]*a[16]+b[8]*a[17]-b[9]*a[18]-b[26]*a[19]+b[27]*a[20]+b[28]*a[21]-b[13]*a[22]+b[14]*a[23]+b[15]*a[24]-b[30]*a[25]+b[19]*a[26]-b[20]*a[27]-b[21]*a[28]+b[31]*a[29]+b[25]*a[30]+b[29]*a[31];
		res[3]=b[3]*a[0]+b[7]*a[1]+b[10]*a[2]+b[0]*a[3]-b[13]*a[4]+b[14]*a[5]-b[16]*a[6]-b[1]*a[7]+b[19]*a[8]-b[20]*a[9]-b[2]*a[10]+b[22]*a[11]-b[23]*a[12]+b[4]*a[13]-b[5]*a[14]+b[25]*a[15]-b[6]*a[16]+b[26]*a[17]-b[27]*a[18]+b[8]*a[19]-b[9]*a[20]+b[29]*a[21]+b[11]*a[22]-b[12]*a[23]+b[30]*a[24]+b[15]*a[25]-b[17]*a[26]+b[18]*a[27]-b[31]*a[28]-b[21]*a[29]-b[24]*a[30]-b[28]*a[31];
		res[4]=b[4]*a[0]+b[8]*a[1]+b[11]*a[2]+b[13]*a[3]+b[0]*a[4]+b[15]*a[5]-b[17]*a[6]-b[19]*a[7]-b[1]*a[8]-b[21]*a[9]-b[22]*a[10]-b[2]*a[11]-b[24]*a[12]-b[3]*a[13]-b[25]*a[14]-b[5]*a[15]-b[26]*a[16]-b[6]*a[17]-b[28]*a[18]-b[7]*a[19]-b[29]*a[20]-b[9]*a[21]-b[10]*a[22]-b[30]*a[23]-b[12]*a[24]-b[14]*a[25]+b[16]*a[26]+b[31]*a[27]+b[18]*a[28]+b[20]*a[29]+b[23]*a[30]+b[27]*a[31];
		res[5]=b[5]*a[0]+b[9]*a[1]+b[12]*a[2]+b[14]*a[3]+b[15]*a[4]+b[0]*a[5]-b[18]*a[6]-b[20]*a[7]-b[21]*a[8]-b[1]*a[9]-b[23]*a[10]-b[24]*a[11]-b[2]*a[12]-b[25]*a[13]-b[3]*a[14]-b[4]*a[15]-b[27]*a[16]-b[28]*a[17]-b[6]*a[18]-b[29]*a[19]-b[7]*a[20]-b[8]*a[21]-b[30]*a[22]-b[10]*a[23]-b[11]*a[24]-b[13]*a[25]+b[31]*a[26]+b[16]*a[27]+b[17]*a[28]+b[19]*a[29]+b[22]*a[30]+b[26]*a[31];
		res[6]=b[6]*a[0]+b[2]*a[1]-b[1]*a[2]+b[16]*a[3]+b[17]*a[4]-b[18]*a[5]+b[0]*a[6]-b[10]*a[7]-b[11]*a[8]+b[12]*a[9]+b[7]*a[10]+b[8]*a[11]-b[9]*a[12]-b[26]*a[13]+b[27]*a[14]+b[28]*a[15]+b[3]*a[16]+b[4]*a[17]-b[5]*a[18]-b[22]*a[19]+b[23]*a[20]+b[24]*a[21]+b[19]*a[22]-b[20]*a[23]-b[21]*a[24]+b[31]*a[25]-b[13]*a[26]+b[14]*a[27]+b[15]*a[28]-b[30]*a[29]+b[29]*a[30]+b[25]*a[31];
		res[7]=b[7]*a[0]+b[3]*a[1]-b[16]*a[2]-b[1]*a[3]+b[19]*a[4]-b[20]*a[5]+b[10]*a[6]+b[0]*a[7]-b[13]*a[8]+b[14]*a[9]-b[6]*a[10]+b[26]*a[11]-b[27]*a[12]+b[8]*a[13]-b[9]*a[14]+b[29]*a[15]-b[2]*a[16]+b[22]*a[17]-b[23]*a[18]+b[4]*a[19]-b[5]*a[20]+b[25]*a[21]-b[17]*a[22]+b[18]*a[23]-b[31]*a[24]-b[21]*a[25]+b[11]*a[26]-b[12]*a[27]+b[30]*a[28]+b[15]*a[29]-b[28]*a[30]-b[24]*a[31];
		res[8]=b[8]*a[0]+b[4]*a[1]-b[17]*a[2]-b[19]*a[3]-b[1]*a[4]-b[21]*a[5]+b[11]*a[6]+b[13]*a[7]+b[0]*a[8]+b[15]*a[9]-b[26]*a[10]-b[6]*a[11]-b[28]*a[12]-b[7]*a[13]-b[29]*a[14]-b[9]*a[15]-b[22]*a[16]-b[2]*a[17]-b[24]*a[18]-b[3]*a[19]-b[25]*a[20]-b[5]*a[21]+b[16]*a[22]+b[31]*a[23]+b[18]*a[24]+b[20]*a[25]-b[10]*a[26]-b[30]*a[27]-b[12]*a[28]-b[14]*a[29]+b[27]*a[30]+b[23]*a[31];
		res[9]=b[9]*a[0]+b[5]*a[1]-b[18]*a[2]-b[20]*a[3]-b[21]*a[4]-b[1]*a[5]+b[12]*a[6]+b[14]*a[7]+b[15]*a[8]+b[0]*a[9]-b[27]*a[10]-b[28]*a[11]-b[6]*a[12]-b[29]*a[13]-b[7]*a[14]-b[8]*a[15]-b[23]*a[16]-b[24]*a[17]-b[2]*a[18]-b[25]*a[19]-b[3]*a[20]-b[4]*a[21]+b[31]*a[22]+b[16]*a[23]+b[17]*a[24]+b[19]*a[25]-b[30]*a[26]-b[10]*a[27]-b[11]*a[28]-b[13]*a[29]+b[26]*a[30]+b[22]*a[31];
		res[10]=b[10]*a[0]+b[16]*a[1]+b[3]*a[2]-b[2]*a[3]+b[22]*a[4]-b[23]*a[5]-b[7]*a[6]+b[6]*a[7]-b[26]*a[8]+b[27]*a[9]+b[0]*a[10]-b[13]*a[11]+b[14]*a[12]+b[11]*a[13]-b[12]*a[14]+b[30]*a[15]+b[1]*a[16]-b[19]*a[17]+b[20]*a[18]+b[17]*a[19]-b[18]*a[20]+b[31]*a[21]+b[4]*a[22]-b[5]*a[23]+b[25]*a[24]-b[24]*a[25]-b[8]*a[26]+b[9]*a[27]-b[29]*a[28]+b[28]*a[29]+b[15]*a[30]+b[21]*a[31];
		res[11]=b[11]*a[0]+b[17]*a[1]+b[4]*a[2]-b[22]*a[3]-b[2]*a[4]-b[24]*a[5]-b[8]*a[6]+b[26]*a[7]+b[6]*a[8]+b[28]*a[9]+b[13]*a[10]+b[0]*a[11]+b[15]*a[12]-b[10]*a[13]-b[30]*a[14]-b[12]*a[15]+b[19]*a[16]+b[1]*a[17]+b[21]*a[18]-b[16]*a[19]-b[31]*a[20]-b[18]*a[21]-b[3]*a[22]-b[25]*a[23]-b[5]*a[24]+b[23]*a[25]+b[7]*a[26]+b[29]*a[27]+b[9]*a[28]-b[27]*a[29]-b[14]*a[30]-b[20]*a[31];
		res[12]=b[12]*a[0]+b[18]*a[1]+b[5]*a[2]-b[23]*a[3]-b[24]*a[4]-b[2]*a[5]-b[9]*a[6]+b[27]*a[7]+b[28]*a[8]+b[6]*a[9]+b[14]*a[10]+b[15]*a[11]+b[0]*a[12]-b[30]*a[13]-b[10]*a[14]-b[11]*a[15]+b[20]*a[16]+b[21]*a[17]+b[1]*a[18]-b[31]*a[19]-b[16]*a[20]-b[17]*a[21]-b[25]*a[22]-b[3]*a[23]-b[4]*a[24]+b[22]*a[25]+b[29]*a[26]+b[7]*a[27]+b[8]*a[28]-b[26]*a[29]-b[13]*a[30]-b[19]*a[31];
		res[13]=b[13]*a[0]+b[19]*a[1]+b[22]*a[2]+b[4]*a[3]-b[3]*a[4]-b[25]*a[5]-b[26]*a[6]-b[8]*a[7]+b[7]*a[8]+b[29]*a[9]-b[11]*a[10]+b[10]*a[11]+b[30]*a[12]+b[0]*a[13]+b[15]*a[14]-b[14]*a[15]-b[17]*a[16]+b[16]*a[17]+b[31]*a[18]+b[1]*a[19]+b[21]*a[20]-b[20]*a[21]+b[2]*a[22]+b[24]*a[23]-b[23]*a[24]-b[5]*a[25]-b[6]*a[26]-b[28]*a[27]+b[27]*a[28]+b[9]*a[29]+b[12]*a[30]+b[18]*a[31];
		res[14]=b[14]*a[0]+b[20]*a[1]+b[23]*a[2]+b[5]*a[3]-b[25]*a[4]-b[3]*a[5]-b[27]*a[6]-b[9]*a[7]+b[29]*a[8]+b[7]*a[9]-b[12]*a[10]+b[30]*a[11]+b[10]*a[12]+b[15]*a[13]+b[0]*a[14]-b[13]*a[15]-b[18]*a[16]+b[31]*a[17]+b[16]*a[18]+b[21]*a[19]+b[1]*a[20]-b[19]*a[21]+b[24]*a[22]+b[2]*a[23]-b[22]*a[24]-b[4]*a[25]-b[28]*a[26]-b[6]*a[27]+b[26]*a[28]+b[8]*a[29]+b[11]*a[30]+b[17]*a[31];
		res[15]=b[15]*a[0]+b[21]*a[1]+b[24]*a[2]+b[25]*a[3]+b[5]*a[4]-b[4]*a[5]-b[28]*a[6]-b[29]*a[7]-b[9]*a[8]+b[8]*a[9]-b[30]*a[10]-b[12]*a[11]+b[11]*a[12]-b[14]*a[13]+b[13]*a[14]+b[0]*a[15]-b[31]*a[16]-b[18]*a[17]+b[17]*a[18]-b[20]*a[19]+b[19]*a[20]+b[1]*a[21]-b[23]*a[22]+b[22]*a[23]+b[2]*a[24]+b[3]*a[25]+b[27]*a[26]-b[26]*a[27]-b[6]*a[28]-b[7]*a[29]-b[10]*a[30]-b[16]*a[31];
		res[16]=b[16]*a[0]+b[10]*a[1]-b[7]*a[2]+b[6]*a[3]-b[26]*a[4]+b[27]*a[5]+b[3]*a[6]-b[2]*a[7]+b[22]*a[8]-b[23]*a[9]+b[1]*a[10]-b[19]*a[11]+b[20]*a[12]+b[17]*a[13]-b[18]*a[14]+b[31]*a[15]+b[0]*a[16]-b[13]*a[17]+b[14]*a[18]+b[11]*a[19]-b[12]*a[20]+b[30]*a[21]-b[8]*a[22]+b[9]*a[23]-b[29]*a[24]+b[28]*a[25]+b[4]*a[26]-b[5]*a[27]+b[25]*a[28]-b[24]*a[29]+b[21]*a[30]+b[15]*a[31];
		res[17]=b[17]*a[0]+b[11]*a[1]-b[8]*a[2]+b[26]*a[3]+b[6]*a[4]+b[28]*a[5]+b[4]*a[6]-b[22]*a[7]-b[2]*a[8]-b[24]*a[9]+b[19]*a[10]+b[1]*a[11]+b[21]*a[12]-b[16]*a[13]-b[31]*a[14]-b[18]*a[15]+b[13]*a[16]+b[0]*a[17]+b[15]*a[18]-b[10]*a[19]-b[30]*a[20]-b[12]*a[21]+b[7]*a[22]+b[29]*a[23]+b[9]*a[24]-b[27]*a[25]-b[3]*a[26]-b[25]*a[27]-b[5]*a[28]+b[23]*a[29]-b[20]*a[30]-b[14]*a[31];
		res[18]=b[18]*a[0]+b[12]*a[1]-b[9]*a[2]+b[27]*a[3]+b[28]*a[4]+b[6]*a[5]+b[5]*a[6]-b[23]*a[7]-b[24]*a[8]-b[2]*a[9]+b[20]*a[10]+b[21]*a[11]+b[1]*a[12]-b[31]*a[13]-b[16]*a[14]-b[17]*a[15]+b[14]*a[16]+b[15]*a[17]+b[0]*a[18]-b[30]*a[19]-b[10]*a[20]-b[11]*a[21]+b[29]*a[22]+b[7]*a[23]+b[8]*a[24]-b[26]*a[25]-b[25]*a[26]-b[3]*a[27]-b[4]*a[28]+b[22]*a[29]-b[19]*a[30]-b[13]*a[31];
		res[19]=b[19]*a[0]+b[13]*a[1]-b[26]*a[2]-b[8]*a[3]+b[7]*a[4]+b[29]*a[5]+b[22]*a[6]+b[4]*a[7]-b[3]*a[8]-b[25]*a[9]-b[17]*a[10]+b[16]*a[11]+b[31]*a[12]+b[1]*a[13]+b[21]*a[14]-b[20]*a[15]-b[11]*a[16]+b[10]*a[17]+b[30]*a[18]+b[0]*a[19]+b[15]*a[20]-b[14]*a[21]-b[6]*a[22]-b[28]*a[23]+b[27]*a[24]+b[9]*a[25]+b[2]*a[26]+b[24]*a[27]-b[23]*a[28]-b[5]*a[29]+b[18]*a[30]+b[12]*a[31];
		res[20]=b[20]*a[0]+b[14]*a[1]-b[27]*a[2]-b[9]*a[3]+b[29]*a[4]+b[7]*a[5]+b[23]*a[6]+b[5]*a[7]-b[25]*a[8]-b[3]*a[9]-b[18]*a[10]+b[31]*a[11]+b[16]*a[12]+b[21]*a[13]+b[1]*a[14]-b[19]*a[15]-b[12]*a[16]+b[30]*a[17]+b[10]*a[18]+b[15]*a[19]+b[0]*a[20]-b[13]*a[21]-b[28]*a[22]-b[6]*a[23]+b[26]*a[24]+b[8]*a[25]+b[24]*a[26]+b[2]*a[27]-b[22]*a[28]-b[4]*a[29]+b[17]*a[30]+b[11]*a[31];
		res[21]=b[21]*a[0]+b[15]*a[1]-b[28]*a[2]-b[29]*a[3]-b[9]*a[4]+b[8]*a[5]+b[24]*a[6]+b[25]*a[7]+b[5]*a[8]-b[4]*a[9]-b[31]*a[10]-b[18]*a[11]+b[17]*a[12]-b[20]*a[13]+b[19]*a[14]+b[1]*a[15]-b[30]*a[16]-b[12]*a[17]+b[11]*a[18]-b[14]*a[19]+b[13]*a[20]+b[0]*a[21]+b[27]*a[22]-b[26]*a[23]-b[6]*a[24]-b[7]*a[25]-b[23]*a[26]+b[22]*a[27]+b[2]*a[28]+b[3]*a[29]-b[16]*a[30]-b[10]*a[31];
		res[22]=b[22]*a[0]+b[26]*a[1]+b[13]*a[2]-b[11]*a[3]+b[10]*a[4]+b[30]*a[5]-b[19]*a[6]+b[17]*a[7]-b[16]*a[8]-b[31]*a[9]+b[4]*a[10]-b[3]*a[11]-b[25]*a[12]+b[2]*a[13]+b[24]*a[14]-b[23]*a[15]+b[8]*a[16]-b[7]*a[17]-b[29]*a[18]+b[6]*a[19]+b[28]*a[20]-b[27]*a[21]+b[0]*a[22]+b[15]*a[23]-b[14]*a[24]+b[12]*a[25]-b[1]*a[26]-b[21]*a[27]+b[20]*a[28]-b[18]*a[29]-b[5]*a[30]-b[9]*a[31];
		res[23]=b[23]*a[0]+b[27]*a[1]+b[14]*a[2]-b[12]*a[3]+b[30]*a[4]+b[10]*a[5]-b[20]*a[6]+b[18]*a[7]-b[31]*a[8]-b[16]*a[9]+b[5]*a[10]-b[25]*a[11]-b[3]*a[12]+b[24]*a[13]+b[2]*a[14]-b[22]*a[15]+b[9]*a[16]-b[29]*a[17]-b[7]*a[18]+b[28]*a[19]+b[6]*a[20]-b[26]*a[21]+b[15]*a[22]+b[0]*a[23]-b[13]*a[24]+b[11]*a[25]-b[21]*a[26]-b[1]*a[27]+b[19]*a[28]-b[17]*a[29]-b[4]*a[30]-b[8]*a[31];
		res[24]=b[24]*a[0]+b[28]*a[1]+b[15]*a[2]-b[30]*a[3]-b[12]*a[4]+b[11]*a[5]-b[21]*a[6]+b[31]*a[7]+b[18]*a[8]-b[17]*a[9]+b[25]*a[10]+b[5]*a[11]-b[4]*a[12]-b[23]*a[13]+b[22]*a[14]+b[2]*a[15]+b[29]*a[16]+b[9]*a[17]-b[8]*a[18]-b[27]*a[19]+b[26]*a[20]+b[6]*a[21]-b[14]*a[22]+b[13]*a[23]+b[0]*a[24]-b[10]*a[25]+b[20]*a[26]-b[19]*a[27]-b[1]*a[28]+b[16]*a[29]+b[3]*a[30]+b[7]*a[31];
		res[25]=b[25]*a[0]+b[29]*a[1]+b[30]*a[2]+b[15]*a[3]-b[14]*a[4]+b[13]*a[5]-b[31]*a[6]-b[21]*a[7]+b[20]*a[8]-b[19]*a[9]-b[24]*a[10]+b[23]*a[11]-b[22]*a[12]+b[5]*a[13]-b[4]*a[14]+b[3]*a[15]-b[28]*a[16]+b[27]*a[17]-b[26]*a[18]+b[9]*a[19]-b[8]*a[20]+b[7]*a[21]+b[12]*a[22]-b[11]*a[23]+b[10]*a[24]+b[0]*a[25]-b[18]*a[26]+b[17]*a[27]-b[16]*a[28]-b[1]*a[29]-b[2]*a[30]-b[6]*a[31];
		res[26]=b[26]*a[0]+b[22]*a[1]-b[19]*a[2]+b[17]*a[3]-b[16]*a[4]-b[31]*a[5]+b[13]*a[6]-b[11]*a[7]+b[10]*a[8]+b[30]*a[9]+b[8]*a[10]-b[7]*a[11]-b[29]*a[12]+b[6]*a[13]+b[28]*a[14]-b[27]*a[15]+b[4]*a[16]-b[3]*a[17]-b[25]*a[18]+b[2]*a[19]+b[24]*a[20]-b[23]*a[21]-b[1]*a[22]-b[21]*a[23]+b[20]*a[24]-b[18]*a[25]+b[0]*a[26]+b[15]*a[27]-b[14]*a[28]+b[12]*a[29]-b[9]*a[30]-b[5]*a[31];
		res[27]=b[27]*a[0]+b[23]*a[1]-b[20]*a[2]+b[18]*a[3]-b[31]*a[4]-b[16]*a[5]+b[14]*a[6]-b[12]*a[7]+b[30]*a[8]+b[10]*a[9]+b[9]*a[10]-b[29]*a[11]-b[7]*a[12]+b[28]*a[13]+b[6]*a[14]-b[26]*a[15]+b[5]*a[16]-b[25]*a[17]-b[3]*a[18]+b[24]*a[19]+b[2]*a[20]-b[22]*a[21]-b[21]*a[22]-b[1]*a[23]+b[19]*a[24]-b[17]*a[25]+b[15]*a[26]+b[0]*a[27]-b[13]*a[28]+b[11]*a[29]-b[8]*a[30]-b[4]*a[31];
		res[28]=b[28]*a[0]+b[24]*a[1]-b[21]*a[2]+b[31]*a[3]+b[18]*a[4]-b[17]*a[5]+b[15]*a[6]-b[30]*a[7]-b[12]*a[8]+b[11]*a[9]+b[29]*a[10]+b[9]*a[11]-b[8]*a[12]-b[27]*a[13]+b[26]*a[14]+b[6]*a[15]+b[25]*a[16]+b[5]*a[17]-b[4]*a[18]-b[23]*a[19]+b[22]*a[20]+b[2]*a[21]+b[20]*a[22]-b[19]*a[23]-b[1]*a[24]+b[16]*a[25]-b[14]*a[26]+b[13]*a[27]+b[0]*a[28]-b[10]*a[29]+b[7]*a[30]+b[3]*a[31];
		res[29]=b[29]*a[0]+b[25]*a[1]-b[31]*a[2]-b[21]*a[3]+b[20]*a[4]-b[19]*a[5]+b[30]*a[6]+b[15]*a[7]-b[14]*a[8]+b[13]*a[9]-b[28]*a[10]+b[27]*a[11]-b[26]*a[12]+b[9]*a[13]-b[8]*a[14]+b[7]*a[15]-b[24]*a[16]+b[23]*a[17]-b[22]*a[18]+b[5]*a[19]-b[4]*a[20]+b[3]*a[21]-b[18]*a[22]+b[17]*a[23]-b[16]*a[24]-b[1]*a[25]+b[12]*a[26]-b[11]*a[27]+b[10]*a[28]+b[0]*a[29]-b[6]*a[30]-b[2]*a[31];
		res[30]=b[30]*a[0]+b[31]*a[1]+b[25]*a[2]-b[24]*a[3]+b[23]*a[4]-b[22]*a[5]-b[29]*a[6]+b[28]*a[7]-b[27]*a[8]+b[26]*a[9]+b[15]*a[10]-b[14]*a[11]+b[13]*a[12]+b[12]*a[13]-b[11]*a[14]+b[10]*a[15]+b[21]*a[16]-b[20]*a[17]+b[19]*a[18]+b[18]*a[19]-b[17]*a[20]+b[16]*a[21]+b[5]*a[22]-b[4]*a[23]+b[3]*a[24]-b[2]*a[25]-b[9]*a[26]+b[8]*a[27]-b[7]*a[28]+b[6]*a[29]+b[0]*a[30]+b[1]*a[31];
		res[31]=b[31]*a[0]+b[30]*a[1]-b[29]*a[2]+b[28]*a[3]-b[27]*a[4]+b[26]*a[5]+b[25]*a[6]-b[24]*a[7]+b[23]*a[8]-b[22]*a[9]+b[21]*a[10]-b[20]*a[11]+b[19]*a[12]+b[18]*a[13]-b[17]*a[14]+b[16]*a[15]+b[15]*a[16]-b[14]*a[17]+b[13]*a[18]+b[12]*a[19]-b[11]*a[20]+b[10]*a[21]-b[9]*a[22]+b[8]*a[23]-b[7]*a[24]+b[6]*a[25]+b[5]*a[26]-b[4]*a[27]+b[3]*a[28]-b[2]*a[29]+b[1]*a[30]+b[0]*a[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.Wedge : res = a ^ b
	/// The outer product. (MEET)
	/// </summary>
	public static CGA binop_Wedge (CGA a_param, CGA b_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;
		double[] b = b_param._mVec;

		res[0]=b[0]*a[0];
		res[1]=b[1]*a[0]+b[0]*a[1];
		res[2]=b[2]*a[0]+b[0]*a[2];
		res[3]=b[3]*a[0]+b[0]*a[3];
		res[4]=b[4]*a[0]+b[0]*a[4];
		res[5]=b[5]*a[0]+b[0]*a[5];
		res[6]=b[6]*a[0]+b[2]*a[1]-b[1]*a[2]+b[0]*a[6];
		res[7]=b[7]*a[0]+b[3]*a[1]-b[1]*a[3]+b[0]*a[7];
		res[8]=b[8]*a[0]+b[4]*a[1]-b[1]*a[4]+b[0]*a[8];
		res[9]=b[9]*a[0]+b[5]*a[1]-b[1]*a[5]+b[0]*a[9];
		res[10]=b[10]*a[0]+b[3]*a[2]-b[2]*a[3]+b[0]*a[10];
		res[11]=b[11]*a[0]+b[4]*a[2]-b[2]*a[4]+b[0]*a[11];
		res[12]=b[12]*a[0]+b[5]*a[2]-b[2]*a[5]+b[0]*a[12];
		res[13]=b[13]*a[0]+b[4]*a[3]-b[3]*a[4]+b[0]*a[13];
		res[14]=b[14]*a[0]+b[5]*a[3]-b[3]*a[5]+b[0]*a[14];
		res[15]=b[15]*a[0]+b[5]*a[4]-b[4]*a[5]+b[0]*a[15];
		res[16]=b[16]*a[0]+b[10]*a[1]-b[7]*a[2]+b[6]*a[3]+b[3]*a[6]-b[2]*a[7]+b[1]*a[10]+b[0]*a[16];
		res[17]=b[17]*a[0]+b[11]*a[1]-b[8]*a[2]+b[6]*a[4]+b[4]*a[6]-b[2]*a[8]+b[1]*a[11]+b[0]*a[17];
		res[18]=b[18]*a[0]+b[12]*a[1]-b[9]*a[2]+b[6]*a[5]+b[5]*a[6]-b[2]*a[9]+b[1]*a[12]+b[0]*a[18];
		res[19]=b[19]*a[0]+b[13]*a[1]-b[8]*a[3]+b[7]*a[4]+b[4]*a[7]-b[3]*a[8]+b[1]*a[13]+b[0]*a[19];
		res[20]=b[20]*a[0]+b[14]*a[1]-b[9]*a[3]+b[7]*a[5]+b[5]*a[7]-b[3]*a[9]+b[1]*a[14]+b[0]*a[20];
		res[21]=b[21]*a[0]+b[15]*a[1]-b[9]*a[4]+b[8]*a[5]+b[5]*a[8]-b[4]*a[9]+b[1]*a[15]+b[0]*a[21];
		res[22]=b[22]*a[0]+b[13]*a[2]-b[11]*a[3]+b[10]*a[4]+b[4]*a[10]-b[3]*a[11]+b[2]*a[13]+b[0]*a[22];
		res[23]=b[23]*a[0]+b[14]*a[2]-b[12]*a[3]+b[10]*a[5]+b[5]*a[10]-b[3]*a[12]+b[2]*a[14]+b[0]*a[23];
		res[24]=b[24]*a[0]+b[15]*a[2]-b[12]*a[4]+b[11]*a[5]+b[5]*a[11]-b[4]*a[12]+b[2]*a[15]+b[0]*a[24];
		res[25]=b[25]*a[0]+b[15]*a[3]-b[14]*a[4]+b[13]*a[5]+b[5]*a[13]-b[4]*a[14]+b[3]*a[15]+b[0]*a[25];
		res[26]=b[26]*a[0]+b[22]*a[1]-b[19]*a[2]+b[17]*a[3]-b[16]*a[4]+b[13]*a[6]-b[11]*a[7]+b[10]*a[8]+b[8]*a[10]-b[7]*a[11]+b[6]*a[13]+b[4]*a[16]-b[3]*a[17]+b[2]*a[19]-b[1]*a[22]+b[0]*a[26];
		res[27]=b[27]*a[0]+b[23]*a[1]-b[20]*a[2]+b[18]*a[3]-b[16]*a[5]+b[14]*a[6]-b[12]*a[7]+b[10]*a[9]+b[9]*a[10]-b[7]*a[12]+b[6]*a[14]+b[5]*a[16]-b[3]*a[18]+b[2]*a[20]-b[1]*a[23]+b[0]*a[27];
		res[28]=b[28]*a[0]+b[24]*a[1]-b[21]*a[2]+b[18]*a[4]-b[17]*a[5]+b[15]*a[6]-b[12]*a[8]+b[11]*a[9]+b[9]*a[11]-b[8]*a[12]+b[6]*a[15]+b[5]*a[17]-b[4]*a[18]+b[2]*a[21]-b[1]*a[24]+b[0]*a[28];
		res[29]=b[29]*a[0]+b[25]*a[1]-b[21]*a[3]+b[20]*a[4]-b[19]*a[5]+b[15]*a[7]-b[14]*a[8]+b[13]*a[9]+b[9]*a[13]-b[8]*a[14]+b[7]*a[15]+b[5]*a[19]-b[4]*a[20]+b[3]*a[21]-b[1]*a[25]+b[0]*a[29];
		res[30]=b[30]*a[0]+b[25]*a[2]-b[24]*a[3]+b[23]*a[4]-b[22]*a[5]+b[15]*a[10]-b[14]*a[11]+b[13]*a[12]+b[12]*a[13]-b[11]*a[14]+b[10]*a[15]+b[5]*a[22]-b[4]*a[23]+b[3]*a[24]-b[2]*a[25]+b[0]*a[30];
		res[31]=b[31]*a[0]+b[30]*a[1]-b[29]*a[2]+b[28]*a[3]-b[27]*a[4]+b[26]*a[5]+b[25]*a[6]-b[24]*a[7]+b[23]*a[8]-b[22]*a[9]+b[21]*a[10]-b[20]*a[11]+b[19]*a[12]+b[18]*a[13]-b[17]*a[14]+b[16]*a[15]+b[15]*a[16]-b[14]*a[17]+b[13]*a[18]+b[12]*a[19]-b[11]*a[20]+b[10]*a[21]-b[9]*a[22]+b[8]*a[23]-b[7]*a[24]+b[6]*a[25]+b[5]*a[26]-b[4]*a[27]+b[3]*a[28]-b[2]*a[29]+b[1]*a[30]+b[0]*a[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.Vee : res = a & b
	/// The regressive product. (JOIN)
	/// </summary>
	public static CGA binop_Vee (CGA a_param, CGA b_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;
		double[] b = b_param._mVec;

		res[31]=1*(a[31]*b[31]);
		res[30]=1*(a[30]*b[31]+a[31]*b[30]);
		res[29]=-1*(a[29]*-1*b[31]+a[31]*b[29]*-1);
		res[28]=1*(a[28]*b[31]+a[31]*b[28]);
		res[27]=-1*(a[27]*-1*b[31]+a[31]*b[27]*-1);
		res[26]=1*(a[26]*b[31]+a[31]*b[26]);
		res[25]=1*(a[25]*b[31]+a[29]*-1*b[30]-a[30]*b[29]*-1+a[31]*b[25]);
		res[24]=-1*(a[24]*-1*b[31]+a[28]*b[30]-a[30]*b[28]+a[31]*b[24]*-1);
		res[23]=1*(a[23]*b[31]+a[27]*-1*b[30]-a[30]*b[27]*-1+a[31]*b[23]);
		res[22]=-1*(a[22]*-1*b[31]+a[26]*b[30]-a[30]*b[26]+a[31]*b[22]*-1);
		res[21]=1*(a[21]*b[31]+a[28]*b[29]*-1-a[29]*-1*b[28]+a[31]*b[21]);
		res[20]=-1*(a[20]*-1*b[31]+a[27]*-1*b[29]*-1-a[29]*-1*b[27]*-1+a[31]*b[20]*-1);
		res[19]=1*(a[19]*b[31]+a[26]*b[29]*-1-a[29]*-1*b[26]+a[31]*b[19]);
		res[18]=1*(a[18]*b[31]+a[27]*-1*b[28]-a[28]*b[27]*-1+a[31]*b[18]);
		res[17]=-1*(a[17]*-1*b[31]+a[26]*b[28]-a[28]*b[26]+a[31]*b[17]*-1);
		res[16]=1*(a[16]*b[31]+a[26]*b[27]*-1-a[27]*-1*b[26]+a[31]*b[16]);
		res[15]=1*(a[15]*b[31]+a[21]*b[30]-a[24]*-1*b[29]*-1+a[25]*b[28]+a[28]*b[25]-a[29]*-1*b[24]*-1+a[30]*b[21]+a[31]*b[15]);
		res[14]=-1*(a[14]*-1*b[31]+a[20]*-1*b[30]-a[23]*b[29]*-1+a[25]*b[27]*-1+a[27]*-1*b[25]-a[29]*-1*b[23]+a[30]*b[20]*-1+a[31]*b[14]*-1);
		res[13]=1*(a[13]*b[31]+a[19]*b[30]-a[22]*-1*b[29]*-1+a[25]*b[26]+a[26]*b[25]-a[29]*-1*b[22]*-1+a[30]*b[19]+a[31]*b[13]);
		res[12]=1*(a[12]*b[31]+a[18]*b[30]-a[23]*b[28]+a[24]*-1*b[27]*-1+a[27]*-1*b[24]*-1-a[28]*b[23]+a[30]*b[18]+a[31]*b[12]);
		res[11]=-1*(a[11]*-1*b[31]+a[17]*-1*b[30]-a[22]*-1*b[28]+a[24]*-1*b[26]+a[26]*b[24]*-1-a[28]*b[22]*-1+a[30]*b[17]*-1+a[31]*b[11]*-1);
		res[10]=1*(a[10]*b[31]+a[16]*b[30]-a[22]*-1*b[27]*-1+a[23]*b[26]+a[26]*b[23]-a[27]*-1*b[22]*-1+a[30]*b[16]+a[31]*b[10]);
		res[9]=-1*(a[9]*-1*b[31]+a[18]*b[29]*-1-a[20]*-1*b[28]+a[21]*b[27]*-1+a[27]*-1*b[21]-a[28]*b[20]*-1+a[29]*-1*b[18]+a[31]*b[9]*-1);
		res[8]=1*(a[8]*b[31]+a[17]*-1*b[29]*-1-a[19]*b[28]+a[21]*b[26]+a[26]*b[21]-a[28]*b[19]+a[29]*-1*b[17]*-1+a[31]*b[8]);
		res[7]=-1*(a[7]*-1*b[31]+a[16]*b[29]*-1-a[19]*b[27]*-1+a[20]*-1*b[26]+a[26]*b[20]*-1-a[27]*-1*b[19]+a[29]*-1*b[16]+a[31]*b[7]*-1);
		res[6]=1*(a[6]*b[31]+a[16]*b[28]-a[17]*-1*b[27]*-1+a[18]*b[26]+a[26]*b[18]-a[27]*-1*b[17]*-1+a[28]*b[16]+a[31]*b[6]);
		res[5]=1*(a[5]*b[31]+a[9]*-1*b[30]-a[12]*b[29]*-1+a[14]*-1*b[28]-a[15]*b[27]*-1+a[18]*b[25]-a[20]*-1*b[24]*-1+a[21]*b[23]+a[23]*b[21]-a[24]*-1*b[20]*-1+a[25]*b[18]+a[27]*-1*b[15]-a[28]*b[14]*-1+a[29]*-1*b[12]-a[30]*b[9]*-1+a[31]*b[5]);
		res[4]=-1*(a[4]*-1*b[31]+a[8]*b[30]-a[11]*-1*b[29]*-1+a[13]*b[28]-a[15]*b[26]+a[17]*-1*b[25]-a[19]*b[24]*-1+a[21]*b[22]*-1+a[22]*-1*b[21]-a[24]*-1*b[19]+a[25]*b[17]*-1+a[26]*b[15]-a[28]*b[13]+a[29]*-1*b[11]*-1-a[30]*b[8]+a[31]*b[4]*-1);
		res[3]=1*(a[3]*b[31]+a[7]*-1*b[30]-a[10]*b[29]*-1+a[13]*b[27]*-1-a[14]*-1*b[26]+a[16]*b[25]-a[19]*b[23]+a[20]*-1*b[22]*-1+a[22]*-1*b[20]*-1-a[23]*b[19]+a[25]*b[16]+a[26]*b[14]*-1-a[27]*-1*b[13]+a[29]*-1*b[10]-a[30]*b[7]*-1+a[31]*b[3]);
		res[2]=-1*(a[2]*-1*b[31]+a[6]*b[30]-a[10]*b[28]+a[11]*-1*b[27]*-1-a[12]*b[26]+a[16]*b[24]*-1-a[17]*-1*b[23]+a[18]*b[22]*-1+a[22]*-1*b[18]-a[23]*b[17]*-1+a[24]*-1*b[16]+a[26]*b[12]-a[27]*-1*b[11]*-1+a[28]*b[10]-a[30]*b[6]+a[31]*b[2]*-1);
		res[1]=1*(a[1]*b[31]+a[6]*b[29]*-1-a[7]*-1*b[28]+a[8]*b[27]*-1-a[9]*-1*b[26]+a[16]*b[21]-a[17]*-1*b[20]*-1+a[18]*b[19]+a[19]*b[18]-a[20]*-1*b[17]*-1+a[21]*b[16]+a[26]*b[9]*-1-a[27]*-1*b[8]+a[28]*b[7]*-1-a[29]*-1*b[6]+a[31]*b[1]);
		res[0]=1*(a[0]*b[31]+a[1]*b[30]-a[2]*-1*b[29]*-1+a[3]*b[28]-a[4]*-1*b[27]*-1+a[5]*b[26]+a[6]*b[25]-a[7]*-1*b[24]*-1+a[8]*b[23]-a[9]*-1*b[22]*-1+a[10]*b[21]-a[11]*-1*b[20]*-1+a[12]*b[19]+a[13]*b[18]-a[14]*-1*b[17]*-1+a[15]*b[16]+a[16]*b[15]-a[17]*-1*b[14]*-1+a[18]*b[13]+a[19]*b[12]-a[20]*-1*b[11]*-1+a[21]*b[10]-a[22]*-1*b[9]*-1+a[23]*b[8]-a[24]*-1*b[7]*-1+a[25]*b[6]+a[26]*b[5]-a[27]*-1*b[4]*-1+a[28]*b[3]-a[29]*-1*b[2]*-1+a[30]*b[1]+a[31]*b[0]);

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.Dot : res = a | b
	/// The inner product.
	/// </summary>
	public static CGA binop_Dot (CGA a_param, CGA b_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;
		double[] b = b_param._mVec;

		res[0]=b[0]*a[0]+b[1]*a[1]+b[2]*a[2]+b[3]*a[3]+b[4]*a[4]-b[5]*a[5]-b[6]*a[6]-b[7]*a[7]-b[8]*a[8]+b[9]*a[9]-b[10]*a[10]-b[11]*a[11]+b[12]*a[12]-b[13]*a[13]+b[14]*a[14]+b[15]*a[15]-b[16]*a[16]-b[17]*a[17]+b[18]*a[18]-b[19]*a[19]+b[20]*a[20]+b[21]*a[21]-b[22]*a[22]+b[23]*a[23]+b[24]*a[24]+b[25]*a[25]+b[26]*a[26]-b[27]*a[27]-b[28]*a[28]-b[29]*a[29]-b[30]*a[30]-b[31]*a[31];
		res[1]=b[1]*a[0]+b[0]*a[1]-b[6]*a[2]-b[7]*a[3]-b[8]*a[4]+b[9]*a[5]+b[2]*a[6]+b[3]*a[7]+b[4]*a[8]-b[5]*a[9]-b[16]*a[10]-b[17]*a[11]+b[18]*a[12]-b[19]*a[13]+b[20]*a[14]+b[21]*a[15]-b[10]*a[16]-b[11]*a[17]+b[12]*a[18]-b[13]*a[19]+b[14]*a[20]+b[15]*a[21]+b[26]*a[22]-b[27]*a[23]-b[28]*a[24]-b[29]*a[25]-b[22]*a[26]+b[23]*a[27]+b[24]*a[28]+b[25]*a[29]-b[31]*a[30]-b[30]*a[31];
		res[2]=b[2]*a[0]+b[6]*a[1]+b[0]*a[2]-b[10]*a[3]-b[11]*a[4]+b[12]*a[5]-b[1]*a[6]+b[16]*a[7]+b[17]*a[8]-b[18]*a[9]+b[3]*a[10]+b[4]*a[11]-b[5]*a[12]-b[22]*a[13]+b[23]*a[14]+b[24]*a[15]+b[7]*a[16]+b[8]*a[17]-b[9]*a[18]-b[26]*a[19]+b[27]*a[20]+b[28]*a[21]-b[13]*a[22]+b[14]*a[23]+b[15]*a[24]-b[30]*a[25]+b[19]*a[26]-b[20]*a[27]-b[21]*a[28]+b[31]*a[29]+b[25]*a[30]+b[29]*a[31];
		res[3]=b[3]*a[0]+b[7]*a[1]+b[10]*a[2]+b[0]*a[3]-b[13]*a[4]+b[14]*a[5]-b[16]*a[6]-b[1]*a[7]+b[19]*a[8]-b[20]*a[9]-b[2]*a[10]+b[22]*a[11]-b[23]*a[12]+b[4]*a[13]-b[5]*a[14]+b[25]*a[15]-b[6]*a[16]+b[26]*a[17]-b[27]*a[18]+b[8]*a[19]-b[9]*a[20]+b[29]*a[21]+b[11]*a[22]-b[12]*a[23]+b[30]*a[24]+b[15]*a[25]-b[17]*a[26]+b[18]*a[27]-b[31]*a[28]-b[21]*a[29]-b[24]*a[30]-b[28]*a[31];
		res[4]=b[4]*a[0]+b[8]*a[1]+b[11]*a[2]+b[13]*a[3]+b[0]*a[4]+b[15]*a[5]-b[17]*a[6]-b[19]*a[7]-b[1]*a[8]-b[21]*a[9]-b[22]*a[10]-b[2]*a[11]-b[24]*a[12]-b[3]*a[13]-b[25]*a[14]-b[5]*a[15]-b[26]*a[16]-b[6]*a[17]-b[28]*a[18]-b[7]*a[19]-b[29]*a[20]-b[9]*a[21]-b[10]*a[22]-b[30]*a[23]-b[12]*a[24]-b[14]*a[25]+b[16]*a[26]+b[31]*a[27]+b[18]*a[28]+b[20]*a[29]+b[23]*a[30]+b[27]*a[31];
		res[5]=b[5]*a[0]+b[9]*a[1]+b[12]*a[2]+b[14]*a[3]+b[15]*a[4]+b[0]*a[5]-b[18]*a[6]-b[20]*a[7]-b[21]*a[8]-b[1]*a[9]-b[23]*a[10]-b[24]*a[11]-b[2]*a[12]-b[25]*a[13]-b[3]*a[14]-b[4]*a[15]-b[27]*a[16]-b[28]*a[17]-b[6]*a[18]-b[29]*a[19]-b[7]*a[20]-b[8]*a[21]-b[30]*a[22]-b[10]*a[23]-b[11]*a[24]-b[13]*a[25]+b[31]*a[26]+b[16]*a[27]+b[17]*a[28]+b[19]*a[29]+b[22]*a[30]+b[26]*a[31];
		res[6]=b[6]*a[0]+b[16]*a[3]+b[17]*a[4]-b[18]*a[5]+b[0]*a[6]-b[26]*a[13]+b[27]*a[14]+b[28]*a[15]+b[3]*a[16]+b[4]*a[17]-b[5]*a[18]+b[31]*a[25]-b[13]*a[26]+b[14]*a[27]+b[15]*a[28]+b[25]*a[31];
		res[7]=b[7]*a[0]-b[16]*a[2]+b[19]*a[4]-b[20]*a[5]+b[0]*a[7]+b[26]*a[11]-b[27]*a[12]+b[29]*a[15]-b[2]*a[16]+b[4]*a[19]-b[5]*a[20]-b[31]*a[24]+b[11]*a[26]-b[12]*a[27]+b[15]*a[29]-b[24]*a[31];
		res[8]=b[8]*a[0]-b[17]*a[2]-b[19]*a[3]-b[21]*a[5]+b[0]*a[8]-b[26]*a[10]-b[28]*a[12]-b[29]*a[14]-b[2]*a[17]-b[3]*a[19]-b[5]*a[21]+b[31]*a[23]-b[10]*a[26]-b[12]*a[28]-b[14]*a[29]+b[23]*a[31];
		res[9]=b[9]*a[0]-b[18]*a[2]-b[20]*a[3]-b[21]*a[4]+b[0]*a[9]-b[27]*a[10]-b[28]*a[11]-b[29]*a[13]-b[2]*a[18]-b[3]*a[20]-b[4]*a[21]+b[31]*a[22]-b[10]*a[27]-b[11]*a[28]-b[13]*a[29]+b[22]*a[31];
		res[10]=b[10]*a[0]+b[16]*a[1]+b[22]*a[4]-b[23]*a[5]-b[26]*a[8]+b[27]*a[9]+b[0]*a[10]+b[30]*a[15]+b[1]*a[16]+b[31]*a[21]+b[4]*a[22]-b[5]*a[23]-b[8]*a[26]+b[9]*a[27]+b[15]*a[30]+b[21]*a[31];
		res[11]=b[11]*a[0]+b[17]*a[1]-b[22]*a[3]-b[24]*a[5]+b[26]*a[7]+b[28]*a[9]+b[0]*a[11]-b[30]*a[14]+b[1]*a[17]-b[31]*a[20]-b[3]*a[22]-b[5]*a[24]+b[7]*a[26]+b[9]*a[28]-b[14]*a[30]-b[20]*a[31];
		res[12]=b[12]*a[0]+b[18]*a[1]-b[23]*a[3]-b[24]*a[4]+b[27]*a[7]+b[28]*a[8]+b[0]*a[12]-b[30]*a[13]+b[1]*a[18]-b[31]*a[19]-b[3]*a[23]-b[4]*a[24]+b[7]*a[27]+b[8]*a[28]-b[13]*a[30]-b[19]*a[31];
		res[13]=b[13]*a[0]+b[19]*a[1]+b[22]*a[2]-b[25]*a[5]-b[26]*a[6]+b[29]*a[9]+b[30]*a[12]+b[0]*a[13]+b[31]*a[18]+b[1]*a[19]+b[2]*a[22]-b[5]*a[25]-b[6]*a[26]+b[9]*a[29]+b[12]*a[30]+b[18]*a[31];
		res[14]=b[14]*a[0]+b[20]*a[1]+b[23]*a[2]-b[25]*a[4]-b[27]*a[6]+b[29]*a[8]+b[30]*a[11]+b[0]*a[14]+b[31]*a[17]+b[1]*a[20]+b[2]*a[23]-b[4]*a[25]-b[6]*a[27]+b[8]*a[29]+b[11]*a[30]+b[17]*a[31];
		res[15]=b[15]*a[0]+b[21]*a[1]+b[24]*a[2]+b[25]*a[3]-b[28]*a[6]-b[29]*a[7]-b[30]*a[10]+b[0]*a[15]-b[31]*a[16]+b[1]*a[21]+b[2]*a[24]+b[3]*a[25]-b[6]*a[28]-b[7]*a[29]-b[10]*a[30]-b[16]*a[31];
		res[16]=b[16]*a[0]-b[26]*a[4]+b[27]*a[5]+b[31]*a[15]+b[0]*a[16]+b[4]*a[26]-b[5]*a[27]+b[15]*a[31];
		res[17]=b[17]*a[0]+b[26]*a[3]+b[28]*a[5]-b[31]*a[14]+b[0]*a[17]-b[3]*a[26]-b[5]*a[28]-b[14]*a[31];
		res[18]=b[18]*a[0]+b[27]*a[3]+b[28]*a[4]-b[31]*a[13]+b[0]*a[18]-b[3]*a[27]-b[4]*a[28]-b[13]*a[31];
		res[19]=b[19]*a[0]-b[26]*a[2]+b[29]*a[5]+b[31]*a[12]+b[0]*a[19]+b[2]*a[26]-b[5]*a[29]+b[12]*a[31];
		res[20]=b[20]*a[0]-b[27]*a[2]+b[29]*a[4]+b[31]*a[11]+b[0]*a[20]+b[2]*a[27]-b[4]*a[29]+b[11]*a[31];
		res[21]=b[21]*a[0]-b[28]*a[2]-b[29]*a[3]-b[31]*a[10]+b[0]*a[21]+b[2]*a[28]+b[3]*a[29]-b[10]*a[31];
		res[22]=b[22]*a[0]+b[26]*a[1]+b[30]*a[5]-b[31]*a[9]+b[0]*a[22]-b[1]*a[26]-b[5]*a[30]-b[9]*a[31];
		res[23]=b[23]*a[0]+b[27]*a[1]+b[30]*a[4]-b[31]*a[8]+b[0]*a[23]-b[1]*a[27]-b[4]*a[30]-b[8]*a[31];
		res[24]=b[24]*a[0]+b[28]*a[1]-b[30]*a[3]+b[31]*a[7]+b[0]*a[24]-b[1]*a[28]+b[3]*a[30]+b[7]*a[31];
		res[25]=b[25]*a[0]+b[29]*a[1]+b[30]*a[2]-b[31]*a[6]+b[0]*a[25]-b[1]*a[29]-b[2]*a[30]-b[6]*a[31];
		res[26]=b[26]*a[0]-b[31]*a[5]+b[0]*a[26]-b[5]*a[31];
		res[27]=b[27]*a[0]-b[31]*a[4]+b[0]*a[27]-b[4]*a[31];
		res[28]=b[28]*a[0]+b[31]*a[3]+b[0]*a[28]+b[3]*a[31];
		res[29]=b[29]*a[0]-b[31]*a[2]+b[0]*a[29]-b[2]*a[31];
		res[30]=b[30]*a[0]+b[31]*a[1]+b[0]*a[30]+b[1]*a[31];
		res[31]=b[31]*a[0]+b[0]*a[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.Add : res = a + b
	/// Multivector addition
	/// </summary>
	public static CGA binop_Add (CGA a_param, CGA b_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;
		double[] b = b_param._mVec;

		res[0] = a[0]+b[0];
		res[1] = a[1]+b[1];
		res[2] = a[2]+b[2];
		res[3] = a[3]+b[3];
		res[4] = a[4]+b[4];
		res[5] = a[5]+b[5];
		res[6] = a[6]+b[6];
		res[7] = a[7]+b[7];
		res[8] = a[8]+b[8];
		res[9] = a[9]+b[9];
		res[10] = a[10]+b[10];
		res[11] = a[11]+b[11];
		res[12] = a[12]+b[12];
		res[13] = a[13]+b[13];
		res[14] = a[14]+b[14];
		res[15] = a[15]+b[15];
		res[16] = a[16]+b[16];
		res[17] = a[17]+b[17];
		res[18] = a[18]+b[18];
		res[19] = a[19]+b[19];
		res[20] = a[20]+b[20];
		res[21] = a[21]+b[21];
		res[22] = a[22]+b[22];
		res[23] = a[23]+b[23];
		res[24] = a[24]+b[24];
		res[25] = a[25]+b[25];
		res[26] = a[26]+b[26];
		res[27] = a[27]+b[27];
		res[28] = a[28]+b[28];
		res[29] = a[29]+b[29];
		res[30] = a[30]+b[30];
		res[31] = a[31]+b[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.Sub : res = a - b
	/// Multivector subtraction
	/// </summary>
	public static CGA binop_Sub (CGA a_param, CGA b_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;
		double[] b = b_param._mVec;

		res[0] = a[0]-b[0];
		res[1] = a[1]-b[1];
		res[2] = a[2]-b[2];
		res[3] = a[3]-b[3];
		res[4] = a[4]-b[4];
		res[5] = a[5]-b[5];
		res[6] = a[6]-b[6];
		res[7] = a[7]-b[7];
		res[8] = a[8]-b[8];
		res[9] = a[9]-b[9];
		res[10] = a[10]-b[10];
		res[11] = a[11]-b[11];
		res[12] = a[12]-b[12];
		res[13] = a[13]-b[13];
		res[14] = a[14]-b[14];
		res[15] = a[15]-b[15];
		res[16] = a[16]-b[16];
		res[17] = a[17]-b[17];
		res[18] = a[18]-b[18];
		res[19] = a[19]-b[19];
		res[20] = a[20]-b[20];
		res[21] = a[21]-b[21];
		res[22] = a[22]-b[22];
		res[23] = a[23]-b[23];
		res[24] = a[24]-b[24];
		res[25] = a[25]-b[25];
		res[26] = a[26]-b[26];
		res[27] = a[27]-b[27];
		res[28] = a[28]-b[28];
		res[29] = a[29]-b[29];
		res[30] = a[30]-b[30];
		res[31] = a[31]-b[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.smul : res = a * b
	/// scalar/multivector multiplication
	/// </summary>
	public static CGA binop_smul (double a_param, CGA b_param)
	{
		double[] res = new double[CGA._basisLength];
		double a = a_param;
		double[] b = b_param._mVec;

		res[0] = a*b[0];
		res[1] = a*b[1];
		res[2] = a*b[2];
		res[3] = a*b[3];
		res[4] = a*b[4];
		res[5] = a*b[5];
		res[6] = a*b[6];
		res[7] = a*b[7];
		res[8] = a*b[8];
		res[9] = a*b[9];
		res[10] = a*b[10];
		res[11] = a*b[11];
		res[12] = a*b[12];
		res[13] = a*b[13];
		res[14] = a*b[14];
		res[15] = a*b[15];
		res[16] = a*b[16];
		res[17] = a*b[17];
		res[18] = a*b[18];
		res[19] = a*b[19];
		res[20] = a*b[20];
		res[21] = a*b[21];
		res[22] = a*b[22];
		res[23] = a*b[23];
		res[24] = a*b[24];
		res[25] = a*b[25];
		res[26] = a*b[26];
		res[27] = a*b[27];
		res[28] = a*b[28];
		res[29] = a*b[29];
		res[30] = a*b[30];
		res[31] = a*b[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.muls : res = a * b
	/// multivector/scalar multiplication
	/// </summary>
	public static CGA binop_muls (CGA a_param, double b_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;
		double b = b_param;

		res[0] = a[0]*b;
		res[1] = a[1]*b;
		res[2] = a[2]*b;
		res[3] = a[3]*b;
		res[4] = a[4]*b;
		res[5] = a[5]*b;
		res[6] = a[6]*b;
		res[7] = a[7]*b;
		res[8] = a[8]*b;
		res[9] = a[9]*b;
		res[10] = a[10]*b;
		res[11] = a[11]*b;
		res[12] = a[12]*b;
		res[13] = a[13]*b;
		res[14] = a[14]*b;
		res[15] = a[15]*b;
		res[16] = a[16]*b;
		res[17] = a[17]*b;
		res[18] = a[18]*b;
		res[19] = a[19]*b;
		res[20] = a[20]*b;
		res[21] = a[21]*b;
		res[22] = a[22]*b;
		res[23] = a[23]*b;
		res[24] = a[24]*b;
		res[25] = a[25]*b;
		res[26] = a[26]*b;
		res[27] = a[27]*b;
		res[28] = a[28]*b;
		res[29] = a[29]*b;
		res[30] = a[30]*b;
		res[31] = a[31]*b;

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.sadd : res = a + b
	/// scalar/multivector addition
	/// </summary>
	public static CGA binop_sadd (double a_param, CGA b_param)
	{
		double[] res = new double[CGA._basisLength];
		double a = a_param;
		double[] b = b_param._mVec;

		res[0] = a+b[0];
		res[1] = b[1];
		res[2] = b[2];
		res[3] = b[3];
		res[4] = b[4];
		res[5] = b[5];
		res[6] = b[6];
		res[7] = b[7];
		res[8] = b[8];
		res[9] = b[9];
		res[10] = b[10];
		res[11] = b[11];
		res[12] = b[12];
		res[13] = b[13];
		res[14] = b[14];
		res[15] = b[15];
		res[16] = b[16];
		res[17] = b[17];
		res[18] = b[18];
		res[19] = b[19];
		res[20] = b[20];
		res[21] = b[21];
		res[22] = b[22];
		res[23] = b[23];
		res[24] = b[24];
		res[25] = b[25];
		res[26] = b[26];
		res[27] = b[27];
		res[28] = b[28];
		res[29] = b[29];
		res[30] = b[30];
		res[31] = b[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.adds : res = a + b
	/// multivector/scalar addition
	/// </summary>
	public static CGA binop_adds (CGA a_param, double b_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;
		double b = b_param;

		res[0] = a[0]+b;
		res[1] = a[1];
		res[2] = a[2];
		res[3] = a[3];
		res[4] = a[4];
		res[5] = a[5];
		res[6] = a[6];
		res[7] = a[7];
		res[8] = a[8];
		res[9] = a[9];
		res[10] = a[10];
		res[11] = a[11];
		res[12] = a[12];
		res[13] = a[13];
		res[14] = a[14];
		res[15] = a[15];
		res[16] = a[16];
		res[17] = a[17];
		res[18] = a[18];
		res[19] = a[19];
		res[20] = a[20];
		res[21] = a[21];
		res[22] = a[22];
		res[23] = a[23];
		res[24] = a[24];
		res[25] = a[25];
		res[26] = a[26];
		res[27] = a[27];
		res[28] = a[28];
		res[29] = a[29];
		res[30] = a[30];
		res[31] = a[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.ssub : res = a - b
	/// scalar/multivector subtraction
	/// </summary>
	public static CGA binop_ssub (double a_param, CGA b_param)
	{
		double[] res = new double[CGA._basisLength];
		double a = a_param;
		double[] b = b_param._mVec;

		res[0] = a-b[0];
		res[1] = -b[1];
		res[2] = -b[2];
		res[3] = -b[3];
		res[4] = -b[4];
		res[5] = -b[5];
		res[6] = -b[6];
		res[7] = -b[7];
		res[8] = -b[8];
		res[9] = -b[9];
		res[10] = -b[10];
		res[11] = -b[11];
		res[12] = -b[12];
		res[13] = -b[13];
		res[14] = -b[14];
		res[15] = -b[15];
		res[16] = -b[16];
		res[17] = -b[17];
		res[18] = -b[18];
		res[19] = -b[19];
		res[20] = -b[20];
		res[21] = -b[21];
		res[22] = -b[22];
		res[23] = -b[23];
		res[24] = -b[24];
		res[25] = -b[25];
		res[26] = -b[26];
		res[27] = -b[27];
		res[28] = -b[28];
		res[29] = -b[29];
		res[30] = -b[30];
		res[31] = -b[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


	/// <summary>
	/// binary operator
	/// CGA.subs : res = a - b
	/// multivector/scalar subtraction
	/// </summary>
	public static CGA binop_subs (CGA a_param, double b_param)
	{
		double[] res = new double[CGA._basisLength];
		double[] a = a_param._mVec;
		double b = b_param;

		res[0] = a[0]-b;
		res[1] = a[1];
		res[2] = a[2];
		res[3] = a[3];
		res[4] = a[4];
		res[5] = a[5];
		res[6] = a[6];
		res[7] = a[7];
		res[8] = a[8];
		res[9] = a[9];
		res[10] = a[10];
		res[11] = a[11];
		res[12] = a[12];
		res[13] = a[13];
		res[14] = a[14];
		res[15] = a[15];
		res[16] = a[16];
		res[17] = a[17];
		res[18] = a[18];
		res[19] = a[19];
		res[20] = a[20];
		res[21] = a[21];
		res[22] = a[22];
		res[23] = a[23];
		res[24] = a[24];
		res[25] = a[25];
		res[26] = a[26];
		res[27] = a[27];
		res[28] = a[28];
		res[29] = a[29];
		res[30] = a[30];
		res[31] = a[31];

		CGA res_ret = new CGA(CGA.Empty);
		res_ret._mVec = res;
		return res_ret;
	}


        /// <summary>
	/// CGA.norm()
	/// Calculate the Euclidean norm. (strict positive).
	/// </summary>
	public double norm() {
		return Math.sqrt(Math.abs(binop_Mul(this, this.Conjugate())._mVec[0]));
	}

	/// <summary>
	/// CGA.inorm()
	/// Calculate the Ideal norm. (signed)
	/// </summary>
	public double inorm() {
		return unop_Dual(this).norm();
	}

	/// <summary>
	/// CGA.normalized()
	/// Returns a normalized (Euclidean) element.
	/// </summary>
	public CGA normalized() {
		return binop_muls(this, 1d / norm());
	}

	
	// The basis blades
	public static final CGA e1 = new CGA(1, 1d);
	public static final CGA e2 = new CGA(2, 1d);
	public static final CGA e3 = new CGA(3, 1d);
	public static final CGA e4 = new CGA(4, 1d);
	public static final CGA e5 = new CGA(5, 1d);
	public static final CGA e12 = new CGA(6, 1d);
	public static final CGA e13 = new CGA(7, 1d);
	public static final CGA e14 = new CGA(8, 1d);
	public static final CGA e15 = new CGA(9, 1d);
	public static final CGA e23 = new CGA(10, 1d);
	public static final CGA e24 = new CGA(11, 1d);
	public static final CGA e25 = new CGA(12, 1d);
	public static final CGA e34 = new CGA(13, 1d);
	public static final CGA e35 = new CGA(14, 1d);
	public static final CGA e45 = new CGA(15, 1d);
	public static final CGA e123 = new CGA(16, 1d);
	public static final CGA e124 = new CGA(17, 1d);
	public static final CGA e125 = new CGA(18, 1d);
	public static final CGA e134 = new CGA(19, 1d);
	public static final CGA e135 = new CGA(20, 1d);
	public static final CGA e145 = new CGA(21, 1d);
	public static final CGA e234 = new CGA(22, 1d);
	public static final CGA e235 = new CGA(23, 1d);
	public static final CGA e245 = new CGA(24, 1d);
	public static final CGA e345 = new CGA(25, 1d);
	public static final CGA e1234 = new CGA(26, 1d);
	public static final CGA e1235 = new CGA(27, 1d);
	public static final CGA e1245 = new CGA(28, 1d);
	public static final CGA e1345 = new CGA(29, 1d);
	public static final CGA e2345 = new CGA(30, 1d);
	public static final CGA e12345 = new CGA(31, 1d);

}
