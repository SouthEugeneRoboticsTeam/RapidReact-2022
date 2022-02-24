package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class SetClimber(private val staticTarget: Double?, private val variableTarget: Double?, private val angleTarget: Double?,
                 private val staticTolerance: Double?, private val variableTolerance: Double?, private val angleTolerance: Double?) : CommandBase() {
    private val staticPID: PIDController
    private val variablePID: PIDController
    private val anglePID: PIDController
    private var allInTolerance = false

    init {
        addRequirements(Climber)

        val climberPIDArray = robotPreferences.climberPID
        val actuatorPIDArray = robotPreferences.actuatorPID

        staticPID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
        variablePID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
        anglePID = PIDController(actuatorPIDArray[0], actuatorPIDArray[1], actuatorPIDArray[2])
    }

    private fun inTolerance(target: Double, current: Double, tolerance: Double): Boolean {
        return target - current <= tolerance && -tolerance <= target - current
    }

    override fun execute() {
        allInTolerance = true

        if(staticTarget != null && staticTolerance != null) {
            if(inTolerance(staticTarget, Climber.staticHeight, staticTolerance)) {
                Climber.setLockStatic(LockStates.LOCKED)
            } else {
                allInTolerance = false
                Climber.setStaticSpeed(0.2)//staticPID.calculate(staticTarget - Climber.staticHeight))
                Climber.setLockStatic(LockStates.UNLOCKED)
            }
        }

        if(variableTarget != null && variableTolerance != null) {
            if(inTolerance(variableTarget, Climber.variableHeight, variableTolerance)) {
                Climber.setStaticSpeed(0.0)
                Climber.setLockVariable(LockStates.LOCKED)
            } else {
                allInTolerance = false
                Climber.setVariableSpeed(0.2)//variablePID.calculate(variableTarget - Climber.variableHeight))
                Climber.setLockVariable(LockStates.UNLOCKED)
            }
        }

        if(angleTarget != null && angleTolerance != null) {
            if(inTolerance(angleTarget, Climber.variableAngle, angleTolerance)) {
                //Just have PID outside if
                Climber.setAngleSpeed(0.0)
            } else {
                allInTolerance = false
                Climber.setVariableSpeed(0.2)//anglePID.calculate(angleTarget - Climber.variableAngle))
            }
        }
    }

    override fun isFinished(): Boolean {
        return allInTolerance
    }

    override fun end(interrupted: Boolean) {
        Climber.stopAndLock()
    }
}