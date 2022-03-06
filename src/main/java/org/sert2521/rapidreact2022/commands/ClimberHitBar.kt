package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.CLIMBER_HIT_SPEED
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber

class ClimberHitBar(private val staticTarget: Double, private val variableTarget: Double,
                    private val isDone: () -> Boolean) : CommandBase() {
    private val staticPID: PIDController
    private val variablePID: PIDController

    init {
        addRequirements(Climber)

        val climberPIDArray = robotPreferences.climberPID

        staticPID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
        variablePID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
    }

    override fun initialize() {
        staticPID.reset()
        variablePID.reset()

        Climber.setAngleSpeed(CLIMBER_HIT_SPEED)
    }

    override fun execute() {
        Climber.setStaticSpeed(staticPID.calculate(Climber.staticHeight, staticTarget))
        Climber.setVariableSpeed(variablePID.calculate(Climber.variableHeight, variableTarget))
    }

    override fun isFinished(): Boolean {
        return isDone()
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}