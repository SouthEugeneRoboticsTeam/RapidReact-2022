package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import org.sert2521.rapidreact2022.commands.IntakeBalls
import org.sert2521.rapidreact2022.commands.FeedBalls

object OI {
    private val primaryController = XboxController(PRIMARY_CONTROLLER_ID)
    private val secondaryController = Joystick(SECONDARY_CONTROLLER_ID)

    private val intake = JoystickButton(primaryController, PrimaryButtons.INTAKE.id)
    private val overrideIndexer = JoystickButton(primaryController, PrimaryButtons.OVERRIDE_INDEXER.id)
    private val shoot = JoystickButton(primaryController, PrimaryButtons.SHOOT.id)

    private val startClimb = JoystickButton(secondaryController, SecondaryButtons.START_CLIMB.id)
    private val climbNext = JoystickButton(secondaryController, SecondaryButtons.CLIMB_NEXT.id)

    private val climbStaticDown = JoystickButton(secondaryController, SecondaryButtons.STATIC_LOWER.id)
    private val climbStaticUp = JoystickButton(secondaryController, SecondaryButtons.STATIC_RAISE.id)

    private val climbVariableDown = JoystickButton(secondaryController, SecondaryButtons.VARIABLE_LOWER.id)
    private val climbVariableUp = JoystickButton(secondaryController, SecondaryButtons.VARIABLE_RAISE.id)

    private val climbVariableActuatorDown = JoystickButton(secondaryController, SecondaryButtons.VARIABLE_ANGLE_DOWN.id)
    private val climbVariableActuatorUp = JoystickButton(secondaryController, SecondaryButtons.VARIABLE_ANGLE_UP.id)

    init {
        shoot.whenActive(IntakeBalls())
        intake.and(shoot.negate()).whenActive(FeedBalls())

        startClimb.toggleWhenPressed(Robot.getClimb())
    }

    fun getOverrideIndexer(): Boolean {
        return overrideIndexer.get()
    }

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
        get() = -primaryController.leftY

    val xAxis
        get() = primaryController.leftX
}