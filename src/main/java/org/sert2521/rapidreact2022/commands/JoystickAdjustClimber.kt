package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.ACTUATE_ADJUST_SPEED
import org.sert2521.rapidreact2022.CLIMB_ADJUST_SPEED
import org.sert2521.rapidreact2022.OI
import org.sert2521.rapidreact2022.subsytems.Climber

class JoystickAdjustClimber : CommandBase() {
    init {
        addRequirements(Climber)
    }

    override fun execute() {
        Climber.elevateStatic(OI.getClimbStatic() * CLIMB_ADJUST_SPEED)
        Climber.elevateVariable(OI.getClimbVariable() * CLIMB_ADJUST_SPEED)
        Climber.actuateVariable(OI.getClimbVariableActuator() * ACTUATE_ADJUST_SPEED)
    }

    override fun isFinished(): Boolean {
        return OI.getClimbNext()
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}
