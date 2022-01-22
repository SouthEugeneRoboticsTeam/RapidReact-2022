package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.Motors
import org.sert2521.rapidreact2022.commands.JoystickDrive

object Drivetrain : SubsystemBase() {
    private val frontLeft = TalonSRX(Motors.FRONT_LEFT.id)
    private val backLeft = TalonSRX(Motors.BACK_LEFT.id)

    private val frontRight = TalonSRX(Motors.FRONT_RIGHT.id)
    private val backRight = TalonSRX(Motors.BACK_RIGHT.id)

    init {
        frontRight.inverted = true
        backRight.inverted = true

        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)

        defaultCommand = JoystickDrive()
    }

    private fun spinLeft(amount: Double) {
        frontLeft.set(ControlMode.PercentOutput, amount)
        backLeft.set(ControlMode.PercentOutput, amount)
    }

    private fun spinRight(amount: Double) {
        frontRight.set(ControlMode.PercentOutput, amount)
        backRight.set(ControlMode.PercentOutput, amount)
    }

    fun leftDistanceTraveled(): Double {
        return frontLeft.selectedSensorPosition * Motors.FRONT_LEFT.encoderDistancePerPulse
    }

    fun rightDistanceTraveled(): Double {
        return frontRight.selectedSensorPosition * Motors.FRONT_RIGHT.encoderDistancePerPulse
    }

    fun distanceTraveledAverage(): Double {
        return (leftDistanceTraveled() + rightDistanceTraveled()) / 2.0
    }

    fun leftVelocity(): Double {
        return frontLeft.selectedSensorVelocity * Motors.FRONT_LEFT.encoderDistancePerPulse
    }

    fun rightVelocity(): Double {
        return frontLeft.selectedSensorVelocity * Motors.FRONT_RIGHT.encoderDistancePerPulse
    }

    fun velocityAverage(): Double {
        return (rightVelocity() + leftVelocity()) / 2.0
    }

    fun tankDrive(leftSpeed: Double, rightSpeed: Double) {
        spinLeft(leftSpeed)
        spinRight(rightSpeed)
    }

    fun arcadeDrive(speed: Double, turn: Double) {
        spinLeft(speed + turn)
        spinRight(speed - turn)
    }

    fun stop() {
        tankDrive(0.0, 0.0)
    }
}