package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.INDEXER_SPEED
import org.sert2521.rapidreact2022.INTAKE_SPEED
import org.sert2521.rapidreact2022.Input
import org.sert2521.rapidreact2022.subsytems.Intake

class IntakeBalls : CommandBase() {
    private val cycleLED = CycleLED()

    init {
        addRequirements(Intake)
    }

    override fun initialize() {
        cycleLED.schedule()

        Intake.setIntakeSpeed(INTAKE_SPEED)
    }

    override fun execute() {
        if((!Intake.indexerFull && !Input.getIndexerOverride()) || (Input.getRunIndexer() && Input.getIndexerOverride())) {
            Intake.setIndexerSpeed(INDEXER_SPEED)
        }else{
            Intake.setIndexerSpeed(0.0)
        }
    }

    override fun end(interrupted: Boolean) {
        cycleLED.cancel()

        Intake.stop()
    }
}
