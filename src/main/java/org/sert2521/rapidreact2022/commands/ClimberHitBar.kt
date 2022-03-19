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
                    private val climberHitSpeed: Double = CLIMBER_HIT_SPEED,
                    private val taps: Int = FILTER_TAPS,
                    private val stopTolerance: Double = STOP_TOLERANCE,
                    private val isDone: () -> Boolean) : CommandBase() {
    constructor(staticTarget: Double, variableTarget: Double, direction: Directions,
                staticTolerance: Double = DEFAULT_TOLERANCE, variableTolerance: Double = DEFAULT_TOLERANCE) :
            this(staticTarget, variableTarget, direction,
                isDone = { ((staticTarget - Climber.staticHeight in -staticTolerance..staticTolerance) || (Climber.isStaticLocked() == LockStates.LOCKED)) &&
                (variableTarget - Climber.variableHeight in -variableTolerance..variableTolerance || (Climber.isVariableLocked() == LockStates.LOCKED)) } )
    private val staticPID: PIDController
    private val variablePID: PIDController
    private val filter = MedianFilter(taps)
    private var tapsBeforeStart = 0

    init {
        addRequirements(Climber)

        val climberPIDArray = robotPreferences.climberPID

        staticPID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
        variablePID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
    }

    override fun initialize() {
        staticPID.reset()
        variablePID.reset()
        filter.reset()
        tapsBeforeStart = 0
    }

    override fun execute() {
        Climber.setStaticSpeed(staticPID.calculate(Climber.staticHeight, staticTarget))
        Climber.setVariableSpeed(variablePID.calculate(Climber.variableHeight, variableTarget))

        if(direction == Directions.BACKWARD) {
            val angleOut = filter.calculate(Climber.variableAngle)
            tapsBeforeStart += 1

            if(tapsBeforeStart < taps || abs(angleOut - Climber.variableAngle) > stopTolerance) {
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