package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.Motors
import org.sert2521.rapidreact2022.OnOffs

object Intake : SubsystemBase() {
    private val intake = WPI_TalonSRX(Motors.INTAKE.id)
    private val indexer = WPI_TalonSRX(Motors.INDEXER.id)

    private val indexerBeamBreak = DigitalInput(OnOffs.INDEXER.id)

    init {
        intake.inverted = Motors.INTAKE.reversed
        indexer.inverted = Motors.INDEXER.reversed
    }

    fun setIntakeSpeed(speed: Double) {
        intake.set(speed)
    }

    fun isIndexerFull(): Boolean {
        return indexerBeamBreak.get()
    }

    fun setIndexerSpeed(speed: Double) {
        indexer.set(speed)
    }

    fun stop() {
        intake.stopMotor()
        indexer.stopMotor()
    }
}