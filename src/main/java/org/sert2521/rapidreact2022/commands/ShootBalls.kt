package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.filter.LinearFilter
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter
import java.lang.System.currentTimeMillis

//Maybe make it wait till PID is steady
class ShootBalls : CommandBase() {
    private val danceLED = DanceLED()
    private var shooting = false
    private var lastShot = 0L
    //Maybe make based on stability
    private val shooterFilter = LinearFilter.movingAverage(robotPreferences.shooterAveragePoints)
    private val shooterBackFilter = LinearFilter.movingAverage(robotPreferences.shooterAveragePoints)

    init {
        addRequirements(Intake, Shooter)
    }

    override fun initialize() {
        danceLED.schedule()

        Shooter.setWheelSpeed(robotPreferences.shooterRPM)
        Shooter.setWheelSpeedBack(robotPreferences.shooterBackRPM)

        shooting = false
        lastShot = 0L

        shooterFilter.reset()
    }

    private fun shouldShoot(): Boolean {
        return shooting && currentTimeMillis() - lastShot >= SHOOT_DELAY * 1000
    }

    override fun execute() {
        val speed = shooterFilter.calculate(Shooter.wheelSpeed)
        val backSpeed = shooterBackFilter.calculate(Shooter.wheelSpeedBack)

        if(robotPreferences.shooterEnterRPM <= speed && robotPreferences.shooterBackEnterRPM <= backSpeed) {
            shooting = true
        }

        if(robotPreferences.shooterExitRPM >= Shooter.wheelSpeed) {
            if(shouldShoot()) {
                lastShot = currentTimeMillis()
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

    override fun end(interrupted: Boolean) {
        danceLED.cancel()

        Intake.stop()
        Shooter.stop()
    }
}
