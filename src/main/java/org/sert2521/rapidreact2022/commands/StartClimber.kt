package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Climber

class StartClimber : CommandBase() {
    private val anglePID: PIDController

    init {
        addRequirements(Climber)

        val actuatorPIDArray = robotPreferences.actuatorPID
        anglePID = PIDController(actuatorPIDArray[0], actuatorPIDArray[1], actuatorPIDArray[2])
    }

    override fun initialize() {
        Climber.unlock()
        Climber.setStaticSpeed(CLIMBER_RESET_SPEED)
        Climber.setVariableSpeed(CLIMBER_RESET_SPEED)

        anglePID.reset()
    }

    override fun execute() {
        Climber.setAngleSpeed(anglePID.calculate(Climber.variableAngleArm, DEFAULT_ANGLE))
    }

    override fun isFinished(): Boolean {
        return Climber.isAtBottomStatic() && Climber.isAtBottomVariable() && (Climber.variableAngleArm - DEFAULT_ANGLE) in -DEFAULT_TOLERANCE_ANGLE..DEFAULT_TOLERANCE_ANGLE
    }

    override fun end(interrupted: Boolean) {
        Climber.calibrateAngleMotor()
        SetClimber(MIN_CLIMBER_HEIGHT, MIN_CLIMBER_HEIGHT, DEFAULT_ANGLE) { false }.schedule()
        Climber.stop()
    }
}