package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter
import java.lang.System.currentTimeMillis

//Remove ballsToShoot
class ShootBalls(private val ballsToShoot: Int? = null, private val stable: Boolean = false) : CommandBase() {
    private val danceLED = DanceLED()
    private var shooting = false
    private var lastShot = 0L
    private var ballsShot = 0

    init {
        addRequirements(Intake, Shooter)
    }

    override fun initialize() {
        danceLED.schedule()

        Shooter.setWheelSpeedFront(robotPreferences.shooterFrontRPM)
        Shooter.setWheelSpeedBack(robotPreferences.shooterBackRPM)

        shooting = false
        lastShot = 0L

        ballsShot = 0
    }

    private fun shouldShoot(): Boolean {
        return shooting && currentTimeMillis() - lastShot >= SHOOT_BETWEEN_DELAY * 1000
    }

    private fun inTolerance(): Boolean {
        return robotPreferences.shooterFrontRPM - Shooter.wheelSpeedFront in -robotPreferences.shooterRPMTolerance..robotPreferences.shooterRPMTolerance && robotPreferences.shooterBackRPM - Shooter.wheelSpeedBack in -robotPreferences.shooterBackRPMTolerance..robotPreferences.shooterBackRPMTolerance
    }

    override fun execute() {
        if(Input.forceShoot() || (inTolerance() && (stable || (Shooter.getAverageFrontSpeed() <= robotPreferences.shooterFrontStability && Shooter.getAverageSpeedBack() <= robotPreferences.shooterBackStability)))) {
            shooting = true
        }

        if(robotPreferences.shooterFrontRPM - robotPreferences.shooterFrontExitRPMDrop >= Shooter.wheelSpeedFront) {
            if(shouldShoot()) {
                lastShot = currentTimeMillis()
                ballsShot += 1
            }

            shooting = false
        }

        if(shouldShoot() || !Intake.indexerFull) {
            Intake.setIndexerSpeed(INDEXER_SPEED)
            Intake.setIntakeSpeed(INTAKE_SPEED)
        }else{
            Intake.setIndexerSpeed(0.0)
            Intake.setIntakeSpeed(0.0)
        }
    }

    override fun isFinished(): Boolean {
        if(ballsToShoot != null) {
            if(ballsShot >= ballsToShoot && currentTimeMillis() - lastShot >= SHOOT_DONE_DELAY * 1000) {
                return true
            }
        }

        return false
    }

    override fun end(interrupted: Boolean) {
        danceLED.cancel()

        Intake.stop()
        Shooter.stop()
    }
}
