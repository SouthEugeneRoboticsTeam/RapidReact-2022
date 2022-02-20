package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Shooter

class RevShooter : CommandBase() {
    init {
        addRequirements(Shooter)
    }

    override fun initialize() {
        Shooter.setWheelSpeed(robotPreferences.shooterRPM)
    }

    override fun end(interrupted: Boolean) {
        Shooter.stop()
    }
}
