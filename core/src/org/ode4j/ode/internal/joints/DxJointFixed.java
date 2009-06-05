/*************************************************************************
 *                                                                       *
 * Open Dynamics Engine, Copyright (C) 2001,2002 Russell L. Smith.       *
 * All rights reserved.  Email: russ@q12.org   Web: www.q12.org          *
 *                                                                       *
 * This library is free software; you can redistribute it and/or         *
 * modify it under the terms of EITHER:                                  *
 *   (1) The GNU Lesser General Public License as published by the Free  *
 *       Software Foundation; either version 2.1 of the License, or (at  *
 *       your option) any later version. The text of the GNU Lesser      *
 *       General Public License is included with this library in the     *
 *       file LICENSE.TXT.                                               *
 *   (2) The BSD-style license that is included with this library in     *
 *       the file LICENSE-BSD.TXT.                                       *
 *                                                                       *
 * This library is distributed in the hope that it will be useful,       *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the files    *
 * LICENSE.TXT and LICENSE-BSD.TXT for more details.                     *
 *                                                                       *
 *************************************************************************/
package org.ode4j.ode.internal.joints;

import org.ode4j.math.DQuaternion;
import org.ode4j.math.DVector3;
import org.ode4j.ode.DFixedJoint;
import org.ode4j.ode.internal.DxWorld;
import static org.ode4j.ode.OdeMath.*;


/**
 * 
 *
 * @deprecated Should not be used anymore, see forums.
 *
 */
public class DxJointFixed extends DxJoint implements DFixedJoint {

	//****************************************************************************
	// fixed joint

	public DQuaternion qrel;   // initial relative rotation body1 -> body2
	DVector3 offset;    // relative offset between the bodies
	double erp;          // error reduction parameter
	double cfm;          // constraint force mix-in
	//    void  set ( int num, double value );
	//    double get ( int num );

	//    dxJointFixed ( dxWorld w );
	//    abstract void getInfo1 ( Info1 info );
	//    abstract void getInfo2 ( Info2 info );
	//    abstract dJointType type();
	//    abstract size_t size();
	//
	//    public abstract void computeInitialRelativeRotation();


	DxJointFixed ( DxWorld w )
	{
		super(w);
		offset = new DVector3();
		//dSetZero ( offset.v, 4 );
		qrel = new DQuaternion();
		//dSetZero ( qrel.v, 4 );
		erp = world.global_erp;
		cfm = world.global_cfm;
	}


	public void
	getInfo1 ( Info1 info )
	{
		info.setM(6);
		info.setNub(6);
	}


	public void
	getInfo2 ( Info2 info )
	{
		int s = info.rowskip();

		// Three rows for orientation
		setFixedOrientation ( this, info, qrel, 3 );

		// Three rows for position.
		// set jacobian
		info._J[info.J1lp+0] = 1;
		info._J[info.J1lp+s+1] = 1;
		info._J[info.J1lp+2*s+2] = 1;

		info.erp = erp;
		info.setCfm(0, cfm);
		info.setCfm(1, cfm);
		info.setCfm(2, cfm);

		DVector3 ofs = new DVector3();
		dMULTIPLY0_331 ( ofs, node[0].body._posr.R, offset );
		if ( node[1].body != null )
		{
			dCROSSMAT ( info._J, info.J1ap, ofs, s, +1 , -1 );
			info._J[info.J2lp+0] = -1;
			info._J[info.J2lp+s+1] = -1;
			info._J[info.J2lp+2*s+2] = -1;
		}

		// set right hand side for the first three rows (linear)
		double k = info.fps * info.erp;
		if ( node[1].body != null)
		{
			for ( int j = 0; j < 3; j++ )
				info.setC(j, k * ( node[1].body._posr.pos.v[j] -
						node[0].body._posr.pos.v[j] + ofs.v[j] ));
		}
		else
		{
			for ( int j = 0; j < 3; j++ )
				info.setC(j, k * ( offset.v[j] - node[0].body._posr.pos.v[j] ));
		}
	}

	//void dJointSetFixed ( dJoint j )
	public void dJointSetFixed ()
	{
		//    dxJointFixed joint = (dxJointFixed) j;//( dxJointFixed* ) j;
		//    COM.dUASSERT ( joint, "bad joint argument" );
		//    checktype ( joint, dxJointFixed.class );

		// This code is taken from dJointSetSliderAxis(), we should really put the
		// common code in its own function.
		// compute the offset between the bodies
		if ( node[0].body != null)
		{
			if ( node[1].body != null)
			{
				//double[] ofs = new double[4];
				DVector3 ofs = new DVector3();
				for ( int i = 0; i < 4; i++ )
					ofs.v[i] = node[0].body._posr.pos.v[i] - 
					node[1].body._posr.pos.v[i];
				dMULTIPLY1_331 ( offset, node[0].body._posr.R, ofs );
			}
			else
			{
				//				offset.v[0] = node[0].body._posr.pos.v[0];
				//				offset.v[1] = node[0].body._posr.pos.v[1];
				//				offset.v[2] = node[0].body._posr.pos.v[2];
				offset.set( node[0].body._posr.pos );
			}
		}

		computeInitialRelativeRotation();
	}

	//void set ( int num, double value )
	void set (D_PARAM_NAMES num, double value)
	{
		switch ( num )
		{
		case dParamCFM:
			cfm = value;
			break;
		case dParamERP:
			erp = value;
			break;
		}
	}


	//double get ( int num )
	double get ( D_PARAM_NAMES num )
	{
		switch ( num )
		{
		case dParamCFM:
			return cfm;
		case dParamERP:
			return erp;
		default:
			return 0;
		}
	}


	//void dJointSetFixedParam ( dJoint j, int parameter, double value )
	public void dJointSetFixedParam ( D_PARAM_NAMES_N parameter, double value )
	{
		//    dxJointFixed joint = (dxJointFixed) j; //( dxJointFixed* ) j;
		//    COM.dUASSERT ( joint, "bad joint argument" );
		//    checktype ( joint, dxJointFixed.class );
		set ( parameter.toSUB(), value );
	}


	//double dJointGetFixedParam ( dJoint j, int parameter )
	private double dJointGetFixedParam ( D_PARAM_NAMES_N parameter )
	{
		//		dxJointFixed joint = (dxJointFixed) j; //( dxJointFixed* ) j;
		//		COM.dUASSERT ( joint, "bad joint argument" );
		//		checktype ( joint, dxJointFixed.class );
		return get ( parameter.toSUB() );
	}


	void
	computeInitialRelativeRotation()
	{
		if (node[0].body != null)
		{
			if (node[1].body != null)
			{
				dQMultiply1 (qrel, node[0].body._q, node[1].body._q );
			}
			else
			{
				// set qrel to the transpose of the first body q
				qrel.v[0] =  node[0].body._q.v[0];
				qrel.v[1] = -node[0].body._q.v[1];
				qrel.v[2] = -node[0].body._q.v[2];
				qrel.v[3] = -node[0].body._q.v[3];
			}
		}
	}


	// **********************************
	// API dFixedJoint
	// **********************************

	public void set()
	{ dJointSetFixed (); }

	public void setParam (D_PARAM_NAMES_N parameter, double value)
	{ dJointSetFixedParam (parameter, value); }

	public double getParam (D_PARAM_NAMES_N parameter)
	{ return dJointGetFixedParam (parameter); }


	@Override
	public void setFixed() {
		dJointSetFixed();
	}
}