package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.Joystick

object OI {
    private val primaryController = Joystick(PRIMARY_CONTROLLER_ID)

    fun getYAxis(): Double {
        return primaryController.y
    }

    fun getXAxis(): Double {
        return primaryController.x
    }
}