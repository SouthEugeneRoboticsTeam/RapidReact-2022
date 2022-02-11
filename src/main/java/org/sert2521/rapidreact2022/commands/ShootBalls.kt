package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.INDEXER_SPEED
import org.sert2521.rapidreact2022.SHOOTER_SHOOT_RPM
import org.sert2521.rapidreact2022.SHOOTER_TOLERANCE
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter

class ShootBalls : CommandBase() {
    init {
        addRequirements(Intake)
        addRequirements(Shooter)
    }

    override fun initialize() {
        Shooter.setWheelSpeed(SHOOTER_SHOOT_RPM)
    }

    override fun execute() {
        if(SHOOTER_SHOOT_RPM + SHOOTER_TOLERANCE <= Shooter.wheelSpeed && Shooter.wheelSpeed <= SHOOTER_SHOOT_RPM + SHOOTER_TOLERANCE) {
            Intake.setIndexerSpeed(INDEXER_SPEED)
        }else{
            Intake.setIndexerSpeed(0.0)
        }
    }

    override fun end(interrupted: Boolean) {
        Intake.stop()
    }
}
