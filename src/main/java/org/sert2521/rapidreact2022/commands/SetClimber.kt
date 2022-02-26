package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.DEFAULT_TOLERANCE
import org.sert2521.rapidreact2022.DEFAULT_TOLERANCE_ANGLE
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber

class SetClimber(private val staticTarget: Double, private val variableTarget: Double, private val angleTarget: Double,
                 private val isDone: () -> Boolean) : CommandBase() {
    constructor(staticTarget: Double, variableTarget: Double, angleTarget: Double,
                staticTolerance: Double = DEFAULT_TOLERANCE, variableTolerance: Double = DEFAULT_TOLERANCE, angleTolerance: Double = DEFAULT_TOLERANCE_ANGLE) :
            this(staticTarget, variableTarget, angleTarget,
                    { staticTarget - Climber.staticHeight in -staticTolerance..staticTolerance &&
                    variableTarget - Climber.variableHeight in -variableTolerance..variableTolerance &&
                    angleTarget - Climber.variableAngle in -angleTolerance..angleTolerance } )

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

        Climber.unlock()
    }

    override fun execute() {
        Climber.setStaticSpeed(staticPID.calculate(Climber.staticHeight, staticTarget))
        Climber.setVariableSpeed(variablePID.calculate(Climber.variableHeight, variableTarget))
        Climber.setAngleSpeed(anglePID.calculate(Climber.variableAngle, angleTarget))
    }

    override fun isFinished(): Boolean {
        return isDone()
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}