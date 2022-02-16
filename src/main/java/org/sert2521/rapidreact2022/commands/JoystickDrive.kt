package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import kotlin.math.abs

class JoystickDrive : CommandBase() {
    private val pid: PIDController
    private val feedForward: SimpleMotorFeedforward
    private val slewRateLimiter: SlewRateLimiter

    init {
        addRequirements(Drivetrain)

        val driveFeedForwardArray = Preferences.getDriveFeedForward()
        feedForward = SimpleMotorFeedforward(driveFeedForwardArray[0], driveFeedForwardArray[1], driveFeedForwardArray[2])
        val pidArray = Preferences.getDrivePID()
        pid = PIDController(pidArray[0], pidArray[1], pidArray[2])

        slewRateLimiter = SlewRateLimiter(SLEW_RATE)
    }

    override fun initialize() {
        Drivetrain.reset()
        pid.reset()
        slewRateLimiter.reset(0.0)
    }

    //Squaring(while persevering sign) the input allows for finer control at low values and the ability to go max speed
    private fun joystickToWheelPercent(amount: Double): Double {
        return amount * abs(amount)
    }

    //Basically just used ramsete drive code, but with user input
    override fun execute() {
        val maxSpeed = if (!OI.getSlowMode()) {
            MAX_SPEED
        } else {
            MAX_SLOW_SPEED
        }

        val yIn = slewRateLimiter.calculate(joystickToWheelPercent(OI.yAxis))
        val xIn = joystickToWheelPercent(OI.xAxis)
        val leftSpeed = (yIn + xIn) * maxSpeed
        val rightSpeed = (yIn - xIn) * maxSpeed
        val leftOutput = feedForward.calculate(leftSpeed) + pid.calculate(Drivetrain.leftVelocity, leftSpeed)
        val rightOutput = feedForward.calculate(rightSpeed) + pid.calculate(Drivetrain.rightVelocity, rightSpeed)

        Drivetrain.tankDriveVolts(leftOutput, rightOutput)
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.stop()
    }
}