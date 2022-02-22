package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.OI
import org.sert2521.rapidreact2022.subsytems.Climber

class JoystickClimb : CommandBase() {
    init {
        addRequirements(Climber)
    }

    override fun initialize() {
        Climber.setLock(false)
    }

    override fun execute() {
        Climber.elevateStatic(OI.getClimbStatic())
        Climber.elevateVariable(OI.getClimbVariable())
        Climber.actuateVariable(OI.getClimbActuate())
    }

    override fun end(interrupted: Boolean) {
        Climber.setLock(true)
        Climber.stop()
    }
}
