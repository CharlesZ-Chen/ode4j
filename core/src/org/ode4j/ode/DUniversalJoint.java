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

public interface DUniversalJoint extends DJoint {

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
	 * @brief set axis
	 * @ingroup joints
	 */
	void setAxis1 (double x, double y, double z);
	
	
	/**
	 * @brief set axis
	 * @ingroup joints
	 */
	void setAxis1 (DVector3C a);
	
	
	/**
	 * @brief set axis
	 * @ingroup joints
	 */
	void setAxis2 (double x, double y, double z);
	
	
	/**
	 * @brief set axis
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
	 * @brief Get the joint anchor point, in world coordinates.
	 * Return the point on body 2.
	 * @remarks
	 * You can think of the ball and socket part of a universal joint as
	 * trying to keep the result of dJointGetBallAnchor() and
	 * dJointGetBallAnchor2() the same. If the joint is
	 * perfectly satisfied, this function will return the same value
	 * as dJointGetUniversalAnchor() to within roundoff errors.
	 * dJointGetUniversalAnchor2() can be used, along with
	 * dJointGetUniversalAnchor(), to see how far the joint has come apart.
	 * @ingroup joints
	 */
	void getAnchor2 (DVector3 result);

	
	/**
	 * @brief Get axis
	 * @ingroup joints
	 */
	void getAxis1 (DVector3 result);

	
	/**
	 * @brief Get axis
	 * @ingroup joints
	 */
	void getAxis2 (DVector3 result);


//	/**
//	 * @brief Get both angles at the same time.
//	 * @ingroup joints
//	 *
//	 * @param joint   The universal joint for which we want to calculate the angles
//	 * @param angle1  The angle between the body1 and the axis 1
//	 * @param angle2  The angle between the body2 and the axis 2
//	 *
//	 * @note This function combine getUniversalAngle1 and getUniversalAngle2 together
//	 *       and try to avoid redundant calculation
//	 */
	/**
	 * @brief Get angle between body the 1 and the axis 1.
	 * @ingroup joints
	 */
	double getAngle1();

	
	/**
	 * @brief Get angle between body the 2 and the axis 2.
	 * @ingroup joints
	 */
	double getAngle2();
	
	
	/**
	 * @brief Get time derivative of angle
	 * @ingroup joints
	 */
	double getAngle1Rate();

	
	/**
	 * @brief Get time derivative of angle
	 * @ingroup joints
	 */
	double getAngle2Rate();

	/**
	 * @brief Applies torque1 about the universal's axis 1, torque2 about the
	 * universal's axis 2.
	 * @remarks This function is just a wrapper for dBodyAddTorque().
	 * @ingroup joints
	 */
	void addTorques (double torque1, double torque2);
	
	
	/**
	 * @brief Set the Universal axis1 as if the 2 bodies were already at 
	 *        offset1 and offset2 appart with respect to axis1 and axis2.
	 * @ingroup joints
	 * <p>
	 * This function initialize the axis1 and the relative orientation of 
	 * each body as if body1 was rotated around the new axis1 by the offset1 
	 * value and as if body2 was rotated around the axis2 by offset2. <p>
	 * Ex: <br>
	 * <code>
	 * dJointSetHuniversalAxis1(jId, 1, 0, 0); <br>
	 * // If you request the position you will have: dJointGetUniversalAngle1(jId) == 0 <br>
	 * // If you request the position you will have: dJointGetUniversalAngle2(jId) == 0 <br>
	 * dJointSetHuniversalAxis1Offset(jId, 1, 0, 0, 0.2, 0.17); <br>
	 * // If you request the position you will have: dJointGetUniversalAngle1(jId) == 0.2 <br>
	 * // If you request the position you will have: dJointGetUniversalAngle2(jId) == 0.17 <br>
	 * </code>
	 *
	 * @param j The Hinge joint ID for which the axis will be set
	 * @param x The X component of the axis in world frame
	 * @param y The Y component of the axis in world frame
	 * @param z The Z component of the axis in world frame
	 * @param angle The angle for the offset of the relative orientation.
	 *              As if body1 was rotated by angle when the Axis was set (see below).
	 *              The rotation is around the new Hinge axis.
	 *
	 * @note Usually the function dJointSetHingeAxis set the current position of body1
	 *       and body2 as the zero angle position. This function set the current position
	 *       as the if the 2 bodies where \b offsets appart.
	 *
	 * @note Any previous offsets are erased.
	 *
	 * @warning Calling dJointSetUniversalAnchor, dJointSetUnivesalAxis1, 
	 *          dJointSetUniversalAxis2, dJointSetUniversalAxis2Offset 
	 *          will reset the "zero" angle position.
	 */
	void setAxis1Offset(double x, double y, double z, double offset1,
			double offset2);
	
	
	/**
	 * @brief Set the Universal axis2 as if the 2 bodies were already at 
	 *        offset1 and offset2 appart with respect to axis1 and axis2.
	 * @ingroup joints
	 * <p>
	 * This function initialize the axis2 and the relative orientation of 
	 * each body as if body1 was rotated around the axis1 by the offset1 
	 * value and as if body2 was rotated around the new axis2 by offset2. <p>
	 * Ex: <br>
	 * <code>
	 * dJointSetHuniversalAxis2(jId, 0, 1, 0); <br>
	 * // If you request the position you will have: dJointGetUniversalAngle1(jId) == 0 <br>
	 * // If you request the position you will have: dJointGetUniversalAngle2(jId) == 0 <br>
	 * dJointSetHuniversalAxis2Offset(jId, 0, 1, 0, 0.2, 0.17); <br>
	 * // If you request the position you will have: dJointGetUniversalAngle1(jId) == 0.2 <br>
	 * // If you request the position you will have: dJointGetUniversalAngle2(jId) == 0.17 <br>
	 * </code>

	 * @param j The Hinge joint ID for which the axis will be set
	 * @param x The X component of the axis in world frame
	 * @param y The Y component of the axis in world frame
	 * @param z The Z component of the axis in world frame
	 * @param angle The angle for the offset of the relative orientation.
	 *              As if body1 was rotated by angle when the Axis was set (see below).
	 *              The rotation is around the new Hinge axis.
	 *
	 * @note Usually the function dJointSetHingeAxis set the current position of body1
	 *       and body2 as the zero angle position. This function set the current position
	 *       as the if the 2 bodies where \b offsets appart.
	 *
	 * @note Any previous offsets are erased.
	 *
	 * @warning Calling dJointSetUniversalAnchor, dJointSetUnivesalAxis1, 
	 *          dJointSetUniversalAxis2, dJointSetUniversalAxis2Offset 
	 *          will reset the "zero" angle position.
	 */
	void setAxis2Offset(double x, double y, double z, double offset1,
			double offset2);
	
	
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
