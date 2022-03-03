package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.DEFAULT_TOLERANCE
import org.sert2521.rapidreact2022.DEFAULT_TOLERANCE_ANGLE
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class SetClimberPID(private val staticTarget: Double, private val variableTarget: Double, private val angleTarget: Double,
                    private val angleOn: Boolean = true,
                    private val isDone: () -> Boolean) : CommandBase() {
    constructor(staticTarget: Double, variableTarget: Double, angleTarget: Double,
                staticTolerance: Double = DEFAULT_TOLERANCE, variableTolerance: Double = DEFAULT_TOLERANCE, angleTolerance: Double = DEFAULT_TOLERANCE_ANGLE,
                angleOn: Boolean = true) :
            this(staticTarget, variableTarget, angleTarget,
                    angleOn,
                    { ((staticTarget - Climber.staticHeight in -staticTolerance..staticTolerance) || (Climber.isStaticLocked() == LockStates.LOCKED)) &&
                    (variableTarget - Climber.variableHeight in -variableTolerance..variableTolerance || (Climber.isVariableLocked() == LockStates.LOCKED)) &&
                    (angleTarget - Climber.variableAngle in -angleTolerance..angleTolerance || !angleOn) } )
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
        Climber.setStaticSpeed(staticPID.calculate(Climber.staticHeight, staticTarget))
        Climber.setVariableSpeed(variablePID.calculate(Climber.variableHeight, variableTarget))
        if(angleOn) {
            Climber.setAngleSpeed(anglePID.calculate(Climber.variableAngle, angleTarget))
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