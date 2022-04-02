package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

//Maybe add end state velocity
class SetClimber(private val staticTarget: Double, private val variableTarget: Double, private val angleTarget: Double?,
                 private val maxStatic: Double = 1.0, private val maxVariable: Double = 1.0,
                 private val isDone: () -> Boolean) : CommandBase() {
    constructor(staticTarget: Double, variableTarget: Double, angleTarget: Double?,
                maxStatic: Double = 1.0, maxVariable: Double = 1.0,
                staticTolerance: Double = DEFAULT_TOLERANCE, variableTolerance: Double = DEFAULT_TOLERANCE, angleTolerance: Double = DEFAULT_TOLERANCE_ANGLE) :
            this(staticTarget, variableTarget, angleTarget,
                maxStatic, maxVariable,
                { ((staticTarget - Climber.staticHeight in -staticTolerance..staticTolerance) || (Climber.isStaticLocked() == LockStates.LOCKED)) &&
                (variableTarget - Climber.variableHeight in -variableTolerance..variableTolerance || (Climber.isVariableLocked() == LockStates.LOCKED)) &&
                (angleTarget == null || angleTarget - Climber.variableAngleArm in -angleTolerance..angleTolerance) } )
    private val staticPID: PIDController
    private val variablePID: PIDController
    private val anglePID: PIDController

    init {
        addRequirements(Climber)

        val climberPIDArray = robotPreferences.climberPID
        val actuatorPIDArray = robotPreferences.actuatorPID

        staticPID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
        variablePID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
        anglePID = PIDController(actuatorPIDArray[0], actuatorPIDArray[1], actuatorPIDArray[2])
    }

    override fun initialize() {
        staticPID.reset()
        variablePID.reset()
        anglePID.reset()
    }

    override fun execute() {
        //Has trouble fully pulling up without dumb if
        Climber.setStaticSpeed(staticPID.calculate(Climber.staticHeight, staticTarget).coerceIn(-maxStatic, maxStatic) + if(staticTarget == HANG_HEIGHT) { PULL_SPEED } else { 0.0 })
        Climber.setVariableSpeed(variablePID.calculate(Climber.variableHeight, variableTarget).coerceIn(-maxVariable, maxVariable) + if(variableTarget == HANG_HEIGHT) { PULL_SPEED } else { 0.0 })
        if(angleTarget != null) {
            Climber.setAngleSpeed(anglePID.calculate(Climber.variableAngleArm, angleTarget))
        } else {
            Climber.setAngleSpeed(0.0)
        }
    }

    override fun isFinished(): Boolean {
        return isDone()
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}