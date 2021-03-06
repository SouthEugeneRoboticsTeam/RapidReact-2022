package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.subsytems.Climber
import edu.wpi.first.math.filter.MedianFilter
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.LockStates
import kotlin.math.abs

enum class Directions {
    FORWARD,
    BACKWARD,
}

class ClimberHitBar(private val staticTarget: Double, private val variableTarget: Double,
                    private val direction: Directions,
                    private val minAngleMotor: Double = Double.NEGATIVE_INFINITY,
                    private val climberHitSpeed: Double = CLIMBER_HIT_SPEED,
                    private val isDone: () -> Boolean) : CommandBase() {
    constructor(staticTarget: Double, variableTarget: Double, direction: Directions,
                minAngleMotor: Double = Double.NEGATIVE_INFINITY,
                staticTolerance: Double = DEFAULT_TOLERANCE, variableTolerance: Double = DEFAULT_TOLERANCE) :
            this(staticTarget, variableTarget, direction,
                    minAngleMotor,
                    isDone = { ((staticTarget - Climber.staticHeight in -staticTolerance..staticTolerance) || (Climber.isStaticLocked() == LockStates.LOCKED)) &&
                            (variableTarget - Climber.variableHeight in -variableTolerance..variableTolerance || (Climber.isVariableLocked() == LockStates.LOCKED)) } )
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
    }

    override fun execute() {
        //Has trouble fully pulling up without dumb if
        Climber.setStaticSpeed(staticPID.calculate(Climber.staticHeight, staticTarget) + if(staticTarget == HANG_HEIGHT) { PULL_SPEED } else { 0.0 })
        Climber.setVariableSpeed(variablePID.calculate(Climber.variableHeight, variableTarget) + if(variableTarget == HANG_HEIGHT) { PULL_SPEED } else { 0.0 })

        if(direction == Directions.BACKWARD) {
            if(Climber.variableAngleMotor >= minAngleMotor) {
                Climber.setAngleSpeed(-climberHitSpeed)
            } else {
                Climber.setAngleSpeed(0.0)
            }
        }

        if(direction == Directions.FORWARD) {
            Climber.setAngleSpeed(climberHitSpeed)
        }
    }

    override fun isFinished(): Boolean {
        return isDone()
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}