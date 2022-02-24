package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.DEFAULT_ACTUATOR_ANGLE
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class IdleClimber : CommandBase() {
    private val anglePID: PIDController

    init {
        addRequirements(Climber)

        val actuatorPIDArray = robotPreferences.actuatorPID

        anglePID = PIDController(actuatorPIDArray[0], actuatorPIDArray[1], actuatorPIDArray[2])
    }

    override fun initialize() {
        Climber.unlock()
    }

    //add 0.2 to constants
    override fun execute() {
        if(Climber.isAtBottomStatic()) {
            Climber.setStaticSpeed(0.0)
            Climber.setLockStatic(LockStates.LOCKED)
        } else {
            Climber.setStaticSpeed(-0.2)
            Climber.setLockStatic(LockStates.UNLOCKED)
        }

        if(Climber.isAtBottomVariable()) {
            Climber.setVariableSpeed(0.0)
            Climber.setLockVariable(LockStates.LOCKED)
        } else {
            Climber.setVariableSpeed(-0.2)
            Climber.setLockVariable(LockStates.UNLOCKED)
        }

        Climber.setAngleSpeed(anglePID.calculate(Climber.variableAngle - DEFAULT_ACTUATOR_ANGLE))
    }

    override fun end(interrupted: Boolean) {
        Climber.stopAndLock()
    }
}