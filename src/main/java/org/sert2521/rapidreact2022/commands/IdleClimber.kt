package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class IdleClimber : CommandBase() {
    private val anglePID: PIDController
    private var allInTolerance = false

    init {
        addRequirements(Climber)

        val actuatorPIDArray = robotPreferences.actuatorPID

        anglePID = PIDController(actuatorPIDArray[0], actuatorPIDArray[1], actuatorPIDArray[2])
    }

    override fun initialize() {
        Climber.unlock()
    }

    override fun execute() {
        allInTolerance = true

        if(Climber.isAtBottomStatic()) {
            Climber.setStaticSpeed(0.0)
            Climber.setLockStatic(LockStates.LOCKED)
        } else {
            allInTolerance = false
            Climber.setStaticSpeed(-0.2)
            Climber.setLockStatic(LockStates.UNLOCKED)
        }

        if(Climber.isAtBottomVariable()) {
            Climber.setVariableSpeed(0.0)
            Climber.setLockVariable(LockStates.LOCKED)
        } else {
            allInTolerance = false
            Climber.setVariableSpeed(-0.2)
            Climber.setLockVariable(LockStates.UNLOCKED)
        }

        /*if(inTolerance(angleTarget, Climber.variableAngle, angleTolerance)) {
            Climber.setAngleSpeed(0.0)
        } else {
            allInTolerance = false
            Climber.setVariableSpeed(anglePID.calculate(angleTarget - Climber.variableAngle))
        }*/
    }

    override fun isFinished(): Boolean {
        return allInTolerance
    }

    override fun end(interrupted: Boolean) {
        Climber.stopAndLock()
    }
}