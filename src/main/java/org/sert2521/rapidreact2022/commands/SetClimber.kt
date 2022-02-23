package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.CLIMBER_MAINTAIN
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber

class SetClimber(private val staticTarget: Double, private val variableTarget: Double, private val angleTarget: Double,
                 private val staticTolerance: Double, private val variableTolerance: Double, private val angleTolerance: Double) : CommandBase() {
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
        return target - current >= -tolerance && tolerance <= target - current
    }

    override fun execute() {
        allInTolerance = true

        if(inTolerance(staticTarget, Climber.staticHeight, staticTolerance)) {
            Climber.setStaticSpeed(0.0)
            Climber.setLockStatic(true)
        } else {
            allInTolerance = false
            Climber.setStaticSpeed(staticPID.calculate(staticTarget - Climber.staticHeight))
            Climber.setLockStatic(false)
        }

        if(inTolerance(variableTarget, Climber.variableHeight, variableTolerance)) {
            Climber.setVariableSpeed(0.0)
            Climber.setLockVariable(true)
        } else {
            allInTolerance = false
            Climber.setVariableSpeed(variablePID.calculate(variableTarget - Climber.variableHeight))
            Climber.setLockVariable(false)
        }

        if(inTolerance(angleTarget, Climber.variableAngle, angleTolerance)) {
            Climber.setAngleSpeed(0.0)
        } else {
            allInTolerance = false
            Climber.setVariableSpeed(anglePID.calculate(angleTarget - Climber.variableAngle))
        }
    }

    override fun isFinished(): Boolean {
        return allInTolerance
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}