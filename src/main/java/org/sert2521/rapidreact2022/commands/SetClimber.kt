package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.DEFAULT_ACTUATOR_ANGLE
import org.sert2521.rapidreact2022.DEFAULT_TOLERANCE
import org.sert2521.rapidreact2022.DEFAULT_TOLERANCE_ANGLE
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class SetClimber(private val staticTarget: Double?, private val variableTarget: Double?, private val angleTarget: Double,
                 private val staticTolerance: Double = DEFAULT_TOLERANCE, private val variableTolerance: Double = DEFAULT_TOLERANCE, private val angleTolerance: Double = DEFAULT_TOLERANCE_ANGLE) : CommandBase() {
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

        if(staticTarget != null) {
            if(inTolerance(staticTarget, Climber.staticHeight, staticTolerance)) {
                Climber.setLockStatic(LockStates.LOCKED)
            } else {
                allInTolerance = false
                Climber.setStaticSpeed(staticPID.calculate(Climber.staticHeight - staticTarget))
                Climber.setLockStatic(LockStates.UNLOCKED)
            }
        }

        if(variableTarget != null) {
            if(inTolerance(variableTarget, Climber.variableHeight, variableTolerance)) {
                Climber.setStaticSpeed(0.0)
                Climber.setLockVariable(LockStates.LOCKED)
            } else {
                allInTolerance = false
                Climber.setVariableSpeed(variablePID.calculate(Climber.variableHeight - variableTarget))
                Climber.setLockVariable(LockStates.UNLOCKED)
            }
        }

        if(!inTolerance(angleTarget, Climber.variableAngle, angleTolerance)) {
            allInTolerance = false
        }

        Climber.setAngleSpeed(anglePID.calculate(Climber.variableAngle - angleTarget))
    }

    override fun isFinished(): Boolean {
        return allInTolerance
    }

    override fun end(interrupted: Boolean) {
        Climber.stopAndLock()
    }
}