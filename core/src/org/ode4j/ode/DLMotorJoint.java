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

public interface DLMotorJoint extends DJoint {

	/**
	 * @brief Set the number of axes that will be controlled by the LMotor.
	 * @param num can range from 0 (which effectively deactivates the joint) to 3.
	 * @ingroup joints
	 */
	void setNumAxes (int num);

	
	/**
	 * @brief Get nr of axes.
	 * @ingroup joints
	 */
	int getNumAxes();

	/**
	 * @brief Set the AMotor axes.
	 * @param anum selects the axis to change (0,1 or 2).
	 * @param rel Each axis can have one of three ``relative orientation'' modes
	 * <li> 0: The axis is anchored to the global frame.
	 * <li> 1: The axis is anchored to the first body.
	 * <li> 2: The axis is anchored to the second body.
	 * @remarks The axis vector is always specified in global coordinates
	 * regardless of the setting of rel.
	 * @ingroup joints
	 */
	void setAxis (int anum, int rel, double x, double y, double z);

	
	/**
	 * @brief Set the AMotor axes.
	 * @param anum selects the axis to change (0,1 or 2).
	 * @param rel Each axis can have one of three ``relative orientation'' modes
	 * <li> 0: The axis is anchored to the global frame.
	 * <li> 1: The axis is anchored to the first body.
	 * <li> 2: The axis is anchored to the second body.
	 * @remarks The axis vector is always specified in global coordinates
	 * regardless of the setting of rel.
	 * @ingroup joints
	 */
	void setAxis (int anum, int rel, DVector3C a);

	
	/**
	 * @brief Get axis.
	 * @ingroup joints
	 */
	void getAxis (int anum, DVector3 result);

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
	
	double getParamVel();
	double getParamVel2();
	double getParamVel3();
	double getParamFMax();
	double getParamFMax2();
	double getParamFMax3();
	void setParamVel(double d);
	void setParamVel2(double d);
	void setParamVel3(double d);
	void setParamFMax(double d);
	void setParamFMax2(double d);
	void setParamFMax3(double d);


}
