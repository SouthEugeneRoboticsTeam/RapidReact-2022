package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.CLIMBER_RESET
import org.sert2521.rapidreact2022.DEFAULT_ACTUATOR_ANGLE
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class ResetClimber : CommandBase() {
    private val anglePID: PIDController

    init {
        addRequirements(Climber)
    }

    override fun initialize() {
        Climber.unlock()
    }

    override fun execute() {
        if(Climber.isAtBottomStatic()) {
            Climber.setStaticSpeed(0.0)
            Climber.setLockStatic(LockStates.LOCKED)
        } else {
            Climber.setStaticSpeed(CLIMBER_RESET)
            Climber.setLockStatic(LockStates.UNLOCKED)
        }

        if(Climber.isAtBottomVariable()) {
            Climber.setVariableSpeed(0.0)
            Climber.setLockVariable(LockStates.LOCKED)
        } else {
            Climber.setVariableSpeed(CLIMBER_RESET)
            Climber.setLockVariable(LockStates.UNLOCKED)
        }
    }

    override fun end(interrupted: Boolean) {
        Climber.stopAndLock()
    }
}