package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.Input
import org.sert2521.rapidreact2022.subsytems.Climber

class JoystickClimb : CommandBase() {
    init {
        addRequirements(Climber)
    }

    override fun initialize() {
        Climber.unlock()
    }

    override fun execute() {
        Climber.setStaticSpeed(Input.getClimbStatic())
        Climber.setVariableSpeed(Input.getClimbVariable())
        Climber.setAngleSpeed(Input.getClimbActuate())
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}