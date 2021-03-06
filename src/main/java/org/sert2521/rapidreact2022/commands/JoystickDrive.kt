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
    private val slewRateLimiterDrive: SlewRateLimiter
    private val slewRateLimiterTurn: SlewRateLimiter

    init {
        addRequirements(Drivetrain)

        val driveFeedForwardArray = robotPreferences.driveFeedForward
        feedForward = SimpleMotorFeedforward(driveFeedForwardArray[0], driveFeedForwardArray[1], driveFeedForwardArray[2])
        val pidArray = robotPreferences.drivePID
        pid = PIDController(pidArray[0], pidArray[1], pidArray[2])

        slewRateLimiterDrive = SlewRateLimiter(SLEW_RATE)
        slewRateLimiterTurn = SlewRateLimiter(SLEW_RATE)
    }

    override fun initialize() {
        Drivetrain.reset()
        pid.reset()
        slewRateLimiterDrive.reset(0.0)
        slewRateLimiterTurn.reset(0.0)
    }

    private fun deadband(value: Double, range: Double): Double {
        if(value < range && value > -range) {
            return 0.0
        }

        return value
    }

    //Squaring(while persevering sign) the input allows for finer control at low values and the ability to go max speed
    private fun joystickToWheelPercent(amount: Double): Double {
        val deadbanded = deadband(amount, DEADBAND)
        return deadbanded * abs(deadbanded)
    }

    //Basically just used ramsete drive code, but with user input
    override fun execute() {
        val maxSpeed = if (!Input.getSlowMode()) {
            MAX_SPEED + MAX_SPEED_ADDON * Input.speedIncrease()
        } else {
            MAX_SLOW_SPEED
        }

        val maxTurnSpeed = if (!Input.getSlowMode()) {
            MAX_TURN_SPEED
        } else {
            MAX_SLOW_TURN_SPEED
        }

        val yIn = slewRateLimiterDrive.calculate(joystickToWheelPercent(Input.yAxis))
        val xIn = slewRateLimiterTurn.calculate(joystickToWheelPercent(Input.xAxis))
        val leftSpeed = yIn * maxSpeed + xIn * maxTurnSpeed
        val rightSpeed = yIn * maxSpeed - xIn * maxTurnSpeed
        val leftOutput = feedForward.calculate(leftSpeed) + pid.calculate(Drivetrain.leftVelocity, leftSpeed)
        val rightOutput = feedForward.calculate(rightSpeed) + pid.calculate(Drivetrain.rightVelocity, rightSpeed)

        Drivetrain.tankDriveVolts(leftOutput, rightOutput)
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.stop()
    }
}