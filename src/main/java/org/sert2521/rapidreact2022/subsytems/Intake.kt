package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.OnOffs
import org.sert2521.rapidreact2022.Talons

object Intake : SubsystemBase() {
    private val intake = WPI_TalonSRX(Talons.INTAKE.id)
    private val indexer = WPI_TalonSRX(Talons.INDEXER.id)

    private val indexerBeamBreak = DigitalInput(OnOffs.INDEXER.id)

    init {
        intake.inverted = Talons.INTAKE.reversed
        indexer.inverted = Talons.INDEXER.reversed
    }

    fun setIntakeSpeed(speed: Double) {
        intake.set(speed)
    }

    val indexerFull
        get() = indexerBeamBreak.get()

    fun setIndexerSpeed(speed: Double) {
        indexer.set(speed)
    }

    fun stop() {
        intake.stopMotor()
        indexer.stopMotor()
    }
}