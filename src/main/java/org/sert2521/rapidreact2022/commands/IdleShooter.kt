package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.Preferences
import org.sert2521.rapidreact2022.subsytems.Shooter

class IdleShooter : CommandBase() {
    init {
        addRequirements(Shooter)
    }

    override fun initialize() {
        Shooter.setPIDF(Preferences.getShooterPIDF())
        Shooter.setWheelSpeed(Preferences.getShooterIdleRPM())
    }

    override fun end(interrupted: Boolean) {
        Shooter.stop()
    }
}