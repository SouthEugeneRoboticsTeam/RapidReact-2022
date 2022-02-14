package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.MAX_SPEED
import org.sert2521.rapidreact2022.OI
import org.sert2521.rapidreact2022.Preferences
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import kotlin.math.abs

class JoystickDrive : CommandBase() {
    private val pid: PIDController
    private val feedForward: SimpleMotorFeedforward

    init {
        val driveFeedForwardArray = Preferences.getDriveFeedForward()
        feedForward = SimpleMotorFeedforward(driveFeedForwardArray[0], driveFeedForwardArray[1], driveFeedForwardArray[2])
        val pidArray = Preferences.getDrivePID()
        pid = PIDController(pidArray[0], pidArray[1], pidArray[2])

        addRequirements(Drivetrain)
    }

    override fun initialize() {
        Drivetrain.reset()
    }

    //Squaring(while persevering sign) the input allows for finer control at low values and the ability to go max speed
    private fun joystickToWheelPercent(amount: Double): Double {
        return amount * abs(amount)
    }

    //Basically just used ramsete drive code, but with user input
    override fun execute() {
        val leftSpeed = (joystickToWheelPercent(OI.yAxis) - joystickToWheelPercent(OI.xAxis)) * MAX_SPEED
        val rightSpeed = (joystickToWheelPercent(OI.yAxis) - joystickToWheelPercent(OI.xAxis)) * MAX_SPEED
        val leftOutput = feedForward.calculate(leftSpeed) + pid.calculate(Drivetrain.leftVelocity, leftSpeed)
        val rightOutput = feedForward.calculate(rightSpeed) + pid.calculate(Drivetrain.rightVelocity, rightSpeed)

        Drivetrain.tankDriveVolts(leftOutput, rightOutput)
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.stop()
    }
}