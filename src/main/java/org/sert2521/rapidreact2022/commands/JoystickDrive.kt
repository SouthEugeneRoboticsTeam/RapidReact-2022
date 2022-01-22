package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.OI
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import kotlin.math.pow

class JoystickDrive : CommandBase() {
    init {
        addRequirements(Drivetrain)
    }

    override fun execute() {
        Drivetrain.arcadeDrive(OI.getYAxis().pow(2), OI.getXAxis().pow(2))
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.stop()
    }
}