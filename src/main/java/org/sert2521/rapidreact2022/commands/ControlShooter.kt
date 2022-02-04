package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.Preferences
import org.sert2521.rapidreact2022.SHOOTER_RPM
import org.sert2521.rapidreact2022.subsytems.Shooter

class ControlShooter : CommandBase() {
    init {
        addRequirements(Shooter)
    }

    override fun initialize() {
        Shooter.setPIDF(Preferences.getShooterPIDF())
        Shooter.setWheelSpeed(SHOOTER_RPM)
    }

    override fun end(interrupted: Boolean) {
        Shooter.stop()
    }
}