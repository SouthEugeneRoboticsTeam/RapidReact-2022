package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.Joystick

object OI {
    private val primaryController = Joystick(PRIMARY_CONTROLLER_ID)

    val yAxis
        get() = -primaryController.y

    val xAxis
        get() = primaryController.x
}