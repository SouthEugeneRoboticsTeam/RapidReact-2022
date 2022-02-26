package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter

//Add waiting period
class ShootBalls : CommandBase() {
    private val danceLED = DanceLED()
    private var shooting = false

    init {
        addRequirements(Intake, Shooter)
    }

    override fun initialize() {
        danceLED.schedule()

        Intake.setIntakeSpeed(INTAKE_SPEED)
        Shooter.setWheelSpeed(robotPreferences.shooterRPM)

        shooting = false
    }

    override fun execute() {
        if (robotPreferences.shooterEnterRPM <= Shooter.wheelSpeed) {
            shooting = true
        }

        if (robotPreferences.shooterExitRPM >= Shooter.wheelSpeed) {
            shooting = false
        }

        if(shooting || !Intake.indexerFull) {
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
