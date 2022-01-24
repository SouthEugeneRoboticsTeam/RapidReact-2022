package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.subsytems.Climber

class ClimbMid : CommandBase() {
    private var isHooked = false

    init {
        addRequirements(Climber)
    }

    override fun initialize() {
        isHooked = false
    }

    override fun execute() {

    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end(interrupted: Boolean) {

    }
}
