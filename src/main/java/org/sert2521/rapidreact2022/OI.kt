package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton

object OI {
    private val primaryController = XboxController(PRIMARY_CONTROLLER_ID)

    private val climbNext = JoystickButton(primaryController, Buttons.CLIMB_NEXT.id)

    private val climbStaticDown = JoystickButton(primaryController, Buttons.CLIMB_NEXT.id)
    private val climbStaticUp = JoystickButton(primaryController, Buttons.CLIMB_NEXT.id)

    private val climbVariableDown = JoystickButton(primaryController, Buttons.CLIMB_NEXT.id)
    private val climbVariableUp = JoystickButton(primaryController, Buttons.CLIMB_NEXT.id)

    private val climbVariableActuatorDown = JoystickButton(primaryController, Buttons.CLIMB_NEXT.id)
    private val climbVariableActuatorUp = JoystickButton(primaryController, Buttons.CLIMB_NEXT.id)

    fun getClimbNext(): Boolean {
        return climbNext.get()
    }

    fun getClimbStatic(): Double {
        return if(climbStaticDown.get()) {
            -1.0
        }else if(climbStaticUp.get()) {
            1.0
        }else{
            0.0
        }
    }

    fun getClimbVariable(): Double {
        return if(climbVariableDown.get()) {
            -1.0
        }else if(climbVariableUp.get()) {
            1.0
        }else{
            0.0
        }
    }

    fun getClimbVariableActuator(): Double {
        return if(climbVariableActuatorDown.get()) {
            -1.0
        }else if(climbVariableActuatorUp.get()) {
            1.0
        }else{
            0.0
        }
    }

    val yAxis
        get() = primaryController.leftY

    val xAxis
        get() = primaryController.leftX
}