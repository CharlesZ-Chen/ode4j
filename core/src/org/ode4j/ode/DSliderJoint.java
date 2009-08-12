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
package org.ode4j.ode;

import org.ode4j.math.DVector3;
import org.ode4j.math.DVector3C;
import org.ode4j.ode.DJoint.PARAM_N;

public interface DSliderJoint extends DJoint {

	/**
	 * @brief set the joint axis
	 * @ingroup joints
	 */
	void setAxis (double x, double y, double z);
	
	
	/**
	 * @brief set the joint axis
	 * @ingroup joints
	 */
	void setAxis (DVector3C a);

	
	/**
	 * @brief Get the slider axis
	 * @ingroup joints
	 */
	void getAxis (DVector3 result);

	
	/**
	 * @brief Get the slider linear position (i.e. the slider's extension).
	 * <p>
	 * When the axis is set, the current position of the attached bodies is
	 * examined and that position will be the zero position.
	 * <p>
	 * The position is the distance, with respect to the zero position,
	 * along the slider axis of body 1 with respect to
	 * body 2. (A NULL body is replaced by the world).
	 * @ingroup joints
	 */
	double getPosition();
	
	
	/**
	 * @brief Get the slider linear position's time derivative.
	 * @ingroup joints
	 */
	double getPositionRate();

	
	/**
	 * @brief Applies the given force in the slider's direction.
	 * <p>
	 * That is, it applies a force with specified magnitude, in the direction of
	 * slider's axis, to body1, and with the same magnitude but opposite
	 * direction to body2.  This function is just a wrapper for dBodyAddForce().
	 * @ingroup joints
	 */
	void addForce (double force);
	void setParamFMax(double d);
	void setParamLoStop(double d);
	void setParamHiStop(double d);
	void setParamVel(double d);
	void setParamBounce(double d);
	
	
	/**
	 * @ingroup joints
	 */
	void setAxisDelta(double x, double y, double z, 
			double dx, double dy, double dz);

	/**
	 * @brief set joint parameter
	 * @ingroup joints
	 */
	@Override
	void setParam (PARAM_N parameter, double value);

	
	/**
	 * @brief get joint parameter
	 * @ingroup joints
	 */
	@Override
	double getParam (PARAM_N parameter);

}
