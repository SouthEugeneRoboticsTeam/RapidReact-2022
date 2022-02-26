package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.OI
import org.sert2521.rapidreact2022.subsytems.Climber

class JoystickClimb : CommandBase() {
    init {
        addRequirements(Climber)
    }

    override fun initialize() {
        Climber.unlock()
    }

    override fun execute() {
        Climber.setStaticSpeed(OI.getClimbStatic())
        Climber.setVariableSpeed(OI.getClimbVariable())
        Climber.setAngleSpeed(OI.getClimbActuate())
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}