package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.INDEXER_SPEED
import org.sert2521.rapidreact2022.INTAKE_SPEED
import org.sert2521.rapidreact2022.OI
import org.sert2521.rapidreact2022.subsytems.Intake

class IntakeBalls : CommandBase() {
    init {
        addRequirements(Intake)
    }

    override fun execute() {
        Intake.setIntakeSpeed(INTAKE_SPEED)

        if(!Intake.isIndexerFull() || OI.getOverrideIndexer()) {
            Intake.setIndexerSpeed(INDEXER_SPEED)
        }
    }

    override fun end(interrupted: Boolean) {
        Intake.stop()
    }
}
