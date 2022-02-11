package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.MAX_SPEED
import org.sert2521.rapidreact2022.OI
import org.sert2521.rapidreact2022.Preferences
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import kotlin.math.abs

class JoystickDrive : CommandBase() {
    private val feedForward: SimpleMotorFeedforward

    init {
        val driveFeedForwardArray = Preferences.getDriveFeedForward()
        feedForward = SimpleMotorFeedforward(driveFeedForwardArray[0], driveFeedForwardArray[1], driveFeedForwardArray[2])

        addRequirements(Drivetrain)
    }

    override fun initialize() {
        Drivetrain.reset()
    }

    //Squaring(while persevering sign) the input allows for finer control at low values and the ability to go max speed
    private fun joystickToWheelPercent(amount: Double): Double {
        return amount * abs(amount)
    }

    override fun execute() {
        val leftSpeed = (joystickToWheelPercent(OI.yAxis) - joystickToWheelPercent(OI.xAxis)) * MAX_SPEED
        val rightSpeed = (joystickToWheelPercent(OI.yAxis) - joystickToWheelPercent(OI.xAxis)) * MAX_SPEED
        Drivetrain.tankDriveVolts(feedForward.calculate(leftSpeed), feedForward.calculate(rightSpeed))
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.stop()
    }
}