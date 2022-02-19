package org.sert2521.rapidreact2022.subsytems

import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.SparkEncoders
import org.sert2521.rapidreact2022.Sparks
import org.sert2521.rapidreact2022.robotPreferences

object Shooter : SubsystemBase() {
    private val motor = CANSparkMax(Sparks.SHOOTER.id, Sparks.SHOOTER.type)

    init {
        motor.inverted = Sparks.SHOOTER.reversed

        motor.encoder.positionConversionFactor = SparkEncoders.SHOOTER.conversionFactor

        setPIDF(robotPreferences.shooterPIDF)
    }

    val wheelSpeed
        get() = motor.encoder.velocity

    fun setPIDF(pidfArray: Array<Double>) {
        motor.pidController.p = pidfArray[0]
        motor.pidController.i = pidfArray[1]
        motor.pidController.d = pidfArray[2]
        motor.pidController.ff = pidfArray[3]
    }

    fun setWheelSpeed(rpm: Double) {
        motor.pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity)
    }

    fun stop() {
        motor.stopMotor()
    }
}