package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.INDEXER_SPEED
import org.sert2521.rapidreact2022.Preferences
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter

class ShootBalls : CommandBase() {
    init {
        addRequirements(Intake, Shooter)
    }

    override fun initialize() {
        Intake.setIntakeSpeed(INDEXER_SPEED)
        Shooter.setWheelSpeed(Preferences.getShooterRPM())
    }

    override fun execute() {
        if((Preferences.getShooterRPM() - Preferences.getShooterRPMTolerance() <= Shooter.wheelSpeed && Shooter.wheelSpeed <= Preferences.getShooterRPM() + Preferences.getShooterRPMTolerance()) || !Intake.indexerFull) {
            Intake.setIndexerSpeed(INDEXER_SPEED)
        }else{
            Intake.setIndexerSpeed(0.0)
        }
    }

    override fun end(interrupted: Boolean) {
        Intake.stop()
        Shooter.stop()
    }
}
