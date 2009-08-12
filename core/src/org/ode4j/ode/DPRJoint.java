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

/**
 *  Prismatic and Rotoide.
 * 
 * The axisP must be perpendicular to axis2
 * <PRE>
 *                                        +-------------+
 *                                        |      x      |
 *                                        +------------\+
 * Prismatic articulation                   ..     ..
 *                       |                ..     ..
 *                      \/              ..      ..
 * +--------------+    --|        __..      ..  anchor2
 * |      x       | .....|.......(__)     ..
 * +--------------+    --|         ^     <
 *        |----------------------->|
 *            Offset               |--- Rotoide articulation
 * </PRE>
 */
public interface DPRJoint extends DJoint {

	/**
	 * @brief set anchor
	 * @ingroup joints
	 */
	void setAnchor (double x, double y, double z);

	
	/**
	 * @brief set anchor
	 * @ingroup joints
	 */
	void setAnchor (DVector3C a);

	
	/**
	 * @brief set the axis for the prismatic articulation
	 * @ingroup joints
	 */
	void setAxis1 (double x, double y, double z);
	
	
	/**
	 * @brief set the axis for the prismatic articulation
	 * @ingroup joints
	 */
	void setAxis1 (DVector3C a);

	
	/**
	 * @brief set the axis for the rotoide articulation
	 * @ingroup joints
	 */
	void setAxis2 (double x, double y, double z);

	
	/**
	 * @brief set the axis for the rotoide articulation
	 * @ingroup joints
	 */
	void setAxis2 (DVector3C a);


	/**
	 * @brief Get the joint anchor point, in world coordinates.
	 * Return the point on body 1. If the joint is perfectly satisfied, 
	 * this will be the same as the point on body 2.
	 * @ingroup joints
	 */
	void getAnchor (DVector3 result);

	
	/**
	 * @brief Get the prismatic axis
	 * @ingroup joints
	 */
	void getAxis1 (DVector3 result);

	
	/**
	 * @brief Get the Rotoide axis
	 * @ingroup joints
	 */
	void getAxis2 (DVector3 result);


	/**
	 * @brief Get the PR linear position (i.e. the prismatic's extension).
	 * <p>
	 * When the axis is set, the current position of the attached bodies is
	 * examined and that position will be the zero position.
	 * <p>
	 * The position is the "oriented" length between the
	 * position = (Prismatic axis) dot_product [(body1 + offset) - (body2 + anchor2)]
	 *
	 * @ingroup joints
	 */
	double getPosition();

	
	/**
	 * @brief Get the PR linear position's time derivative.
	 *
	 * @ingroup joints
	 */
	double getPositionRate();
	void setParamLoStop(double d);
	void setParamHiStop(double d);
	void setParamLoStop2(double d);
	void setParamHiStop2(double d);
	void setParamVel2(double d);
	void setParamFMax2(double d);


	/**
	 * @brief Get the PR angular position (i.e. the  twist between the 2 bodies)
	 *
	 * When the axis is set, the current position of the attached bodies is
	 * examined and that position will be the zero position.
	 * @ingroup joints
	 */
	double getAngle();

	
	/**
	 * @brief Get the PR angular position's time derivative
	 *
	 * @ingroup joints
	 */
	double getAngleRate();


	/**
	 * @brief set joint parameter
	 * @ingroup joints
	 *
	 * @note parameterX where X equal 2 refer to parameter for the rotoide articulation
	 */
	@Override
	void setParam (PARAM_N parameter, double value);


	/**
	 * @brief get joint parameter
	 * @ingroup joints
	 */
	@Override
	double getParam (PARAM_N parameter);

	
	/**
	 * @brief Applies the torque about the rotoide axis of the PR joint
	 * <p>
	 * That is, it applies a torque with specified magnitude in the direction 
	 * of the rotoide axis, to body 1, and with the same magnitude but in opposite
	 * direction to body 2. This function is just a wrapper for dBodyAddTorque()}
	 * @ingroup joints
	 */
	void addTorque(double torque);
}
