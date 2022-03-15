package org.sert2521.rapidreact2022.subsytems

import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*

object Shooter : SubsystemBase() {
    private val motor = CANSparkMax(Sparks.SHOOTER.id, Sparks.SHOOTER.type)
    private val motorBack = CANSparkMax(Sparks.SHOOTER_BACK.id, Sparks.SHOOTER_BACK.type)

    init {
        motor.inverted = Sparks.SHOOTER.reversed
        motorBack.inverted = Sparks.SHOOTER_BACK.reversed

        motor.encoder.positionConversionFactor = SparkEncoders.SHOOTER.conversionFactor
        motorBack.encoder.positionConversionFactor = SparkEncoders.SHOOTER_BACK.conversionFactor

        motor.pidController.p = robotPreferences.shooterPIDF[0]
        motor.pidController.i = robotPreferences.shooterPIDF[1]
        motor.pidController.d = robotPreferences.shooterPIDF[2]
        motor.pidController.ff = robotPreferences.shooterPIDF[3]

        motorBack.pidController.p = robotPreferences.shooterBackPIDF[0]
        motorBack.pidController.i = robotPreferences.shooterBackPIDF[1]
        motorBack.pidController.d = robotPreferences.shooterBackPIDF[2]
        motorBack.pidController.ff = robotPreferences.shooterBackPIDF[3]
    }

    val wheelSpeed
        get() = motor.encoder.velocity

    val wheelSpeedBack
        get() = motorBack.encoder.velocity

    fun setWheelSpeed(rpm: Double) {
        motor.pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity)
    }

    fun setWheelSpeedBack(rpm: Double) {
        motorBack.pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity)
    }

    fun stop() {
        motor.stopMotor()
        motorBack.stopMotor()
    }
}