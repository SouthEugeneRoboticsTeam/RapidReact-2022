package org.sert2521.rapidreact2022.subsytems

import com.revrobotics.CANSparkMax
import com.revrobotics.SparkMaxRelativeEncoder
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*

object Shooter : SubsystemBase() {
    private val motor = CANSparkMax(Sparks.SHOOTER.id, Sparks.SHOOTER.type)
    private val motorBack = CANSparkMax(Sparks.SHOOTER_BACK.id, Sparks.SHOOTER_BACK.type)
    private val motorBackEncoder = motorBack.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, SparkEncodersQuadrature.SHOOTER_BACK.encoderPulsesPerRev)
    private val motorBackPID = PIDController(robotPreferences.shooterBackPIDF[0], robotPreferences.shooterBackPIDF[1], robotPreferences.shooterBackPIDF[2])
    private var motorBackGoal = 0.0
    private var motorBackStopped = false

    init {
        motor.inverted = Sparks.SHOOTER.reversed
        motorBack.inverted = Sparks.SHOOTER_BACK.reversed

        motor.encoder.positionConversionFactor = SparkEncodersHall.SHOOTER.conversionFactor
        motorBackEncoder.positionConversionFactor = SparkEncodersQuadrature.SHOOTER_BACK.conversionFactor

        motor.pidController.p = robotPreferences.shooterPIDF[0]
        motor.pidController.i = robotPreferences.shooterPIDF[1]
        motor.pidController.d = robotPreferences.shooterPIDF[2]
        motor.pidController.ff = robotPreferences.shooterPIDF[3]
    }

    private fun getBackSpeed(): Double {
        return motorBackPID.calculate(wheelSpeedBack, motorBackGoal) + (motorBackGoal * robotPreferences.shooterBackPIDF[3])
    }

    override fun periodic() {
        if(!motorBackStopped) {
            motorBack.set(getBackSpeed())
        }
    }

    val wheelSpeed
        get() = motor.encoder.velocity

    val wheelSpeedBack
        get() = -motorBackEncoder.velocity

    fun setWheelSpeed(rpm: Double) {
        motor.pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity)
    }

    fun setWheelSpeedBack(rpm: Double) {
        motorBackGoal = rpm
        motorBackPID.reset()
        motorBackStopped = false
    }

    fun stop() {
        motor.stopMotor()
        motorBack.stopMotor()
        motorBackStopped = true
    }
}