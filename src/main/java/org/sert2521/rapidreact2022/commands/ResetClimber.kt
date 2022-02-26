package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.CLIMBER_RESET
import org.sert2521.rapidreact2022.DEFAULT_ANGLE
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber

class ResetClimber : CommandBase() {
    private val anglePID: PIDController

    init {
        addRequirements(Climber)

        val actuatorPIDArray = robotPreferences.actuatorPID
        anglePID = PIDController(actuatorPIDArray[0], actuatorPIDArray[1], actuatorPIDArray[2])
    }

    override fun initialize() {
        Climber.unlock()
        Climber.setStaticSpeed(CLIMBER_RESET)
        Climber.setVariableSpeed(CLIMBER_RESET)

        anglePID.reset()
    }

    override fun execute() {
        Climber.setAngleSpeed(anglePID.calculate(Climber.variableAngle, DEFAULT_ANGLE))
    }

    override fun isFinished(): Boolean {
        return Climber.isAtBottomStatic() && Climber.isAtBottomVariable()
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}