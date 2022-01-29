package org.sert2521.rapidreact2022.subsytems

import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.PIDs
import org.sert2521.rapidreact2022.Sparks

object Shooter : SubsystemBase() {
    private val motor = CANSparkMax(Sparks.SHOOTER.id, Sparks.SHOOTER.type)

    init {
        motor.inverted = Sparks.SHOOTER.reversed

        motor.pidController.p = PIDs.SHOOTER.p
        motor.pidController.i = PIDs.SHOOTER.i
        motor.pidController.d = PIDs.SHOOTER.d

        setWheelSpeed(0.1)
    }

    fun setWheelSpeed(speed: Double) {
        motor.set(speed)
    }

    fun stop() {
        motor.stopMotor()
    }
}