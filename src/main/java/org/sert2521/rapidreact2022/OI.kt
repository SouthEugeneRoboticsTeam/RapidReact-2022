package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton

object OI {
    private val primaryController = XboxController(PRIMARY_CONTROLLER_ID)
    private val button = JoystickButton(primaryController, 0)

    val yAxis
        get() = primaryController.leftY

    val xAxis
        get() = primaryController.leftX
}