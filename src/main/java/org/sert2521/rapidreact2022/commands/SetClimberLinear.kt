package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.DEFAULT_TOLERANCE
import org.sert2521.rapidreact2022.DEFAULT_TOLERANCE_ANGLE
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates
import kotlin.math.sign

class SetClimberLinear(private val staticTarget: Double, private val variableTarget: Double, private val angleTarget: Double,
                       private val staticSpeed: Double = 0.0, private val variableSpeed: Double = 0.0,
                       private val isDone: () -> Boolean) : CommandBase() {
    constructor(staticTarget: Double, variableTarget: Double, angleTarget: Double,
                staticSpeed: Double = 0.0, variableSpeed: Double = 0.0,
                staticTolerance: Double = DEFAULT_TOLERANCE, variableTolerance: Double = DEFAULT_TOLERANCE, angleTolerance: Double = DEFAULT_TOLERANCE_ANGLE) :
            this(staticTarget, variableTarget, angleTarget,
                    staticSpeed, variableSpeed,
                    { ((staticTarget - Climber.staticHeight in -staticTolerance..staticTolerance) || (Climber.isStaticLocked() == LockStates.LOCKED)) &&
                    (variableTarget - Climber.variableHeight in -variableTolerance..variableTolerance || (Climber.isVariableLocked() == LockStates.LOCKED)) &&
                    angleTarget - Climber.variableAngle in -angleTolerance..angleTolerance } )
    private val anglePID: PIDController

    init {
        addRequirements(Climber)

        val actuatorPIDArray = robotPreferences.actuatorPID

        anglePID = PIDController(actuatorPIDArray[0], actuatorPIDArray[1], actuatorPIDArray[2])
    }

    override fun initialize() {
        anglePID.reset()
    }

    override fun execute() {
        Climber.setStaticSpeed(staticSpeed * sign(staticTarget - Climber.staticHeight))
        Climber.setVariableSpeed(variableSpeed * sign(variableTarget - Climber.variableHeight))
        Climber.setAngleSpeed(anglePID.calculate(Climber.variableAngle, angleTarget))
    }

    override fun isFinished(): Boolean {
        return isDone()
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}