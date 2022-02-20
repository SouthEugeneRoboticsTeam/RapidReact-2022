package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.INDEXER_SPEED
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter

class ShootBalls : CommandBase() {
    private val danceLED = DanceLED()

    init {
        addRequirements(Intake, Shooter)
    }

    override fun initialize() {
        danceLED.schedule()

        Intake.setIntakeSpeed(INDEXER_SPEED)
        Shooter.setWheelSpeed(robotPreferences.shooterRPM)
    }

    override fun execute() {
        if((robotPreferences.shooterRPM - robotPreferences.shooterTolerance <= Shooter.wheelSpeed && Shooter.wheelSpeed <= robotPreferences.shooterRPM + robotPreferences.shooterTolerance) || !Intake.indexerFull) {
            Intake.setIndexerSpeed(INDEXER_SPEED)
        }else{
            Intake.setIndexerSpeed(0.0)
        }
    }

    override fun end(interrupted: Boolean) {
        danceLED.cancel()

        Intake.stop()
        Shooter.stop()
    }
}
