package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.trajectory.TrapezoidProfile
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates
import java.lang.System.currentTimeMillis

//Maybe add end state velocity
class SetClimber(staticTarget: Double, variableTarget: Double, private val angleTarget: Double?,
                 maxSpeed: Double = CLIMBER_MAX_SPEED, maxAcceleration: Double = CLIMBER_MAX_ACCELERATION,
                 private val isDone: () -> Boolean) : CommandBase() {
    constructor(staticTarget: Double, variableTarget: Double, angleTarget: Double?,
                maxSpeed: Double = CLIMBER_MAX_SPEED, maxAcceleration: Double = CLIMBER_MAX_ACCELERATION,
                staticTolerance: Double = DEFAULT_TOLERANCE, variableTolerance: Double = DEFAULT_TOLERANCE, angleTolerance: Double = DEFAULT_TOLERANCE_ANGLE) :
            this(staticTarget, variableTarget, angleTarget,
                maxSpeed, maxAcceleration,
                { ((staticTarget - Climber.staticHeight in -staticTolerance..staticTolerance) || (Climber.isStaticLocked() == LockStates.LOCKED)) &&
                (variableTarget - Climber.variableHeight in -variableTolerance..variableTolerance || (Climber.isVariableLocked() == LockStates.LOCKED)) &&
                (angleTarget == null || angleTarget - Climber.variableAngle in -angleTolerance..angleTolerance) } )
    private val staticPID: PIDController
    private val variablePID: PIDController
    private val anglePID: PIDController

    private val staticProfile: TrapezoidProfile
    private val variableProfile: TrapezoidProfile
    private val angleProfile: TrapezoidProfile?

    private var time = 0.0

    init {
        addRequirements(Climber)

        val climberPIDArray = robotPreferences.climberPID
        val actuatorPIDArray = robotPreferences.actuatorPID

        staticPID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
        variablePID = PIDController(climberPIDArray[0], climberPIDArray[1], climberPIDArray[2])
        anglePID = PIDController(actuatorPIDArray[0], actuatorPIDArray[1], actuatorPIDArray[2])

        val climberConstraints = TrapezoidProfile.Constraints(maxSpeed, maxAcceleration)
        val actuatorConstraints = TrapezoidProfile.Constraints(CLIMBER_ACTUATOR_MAX_SPEED, CLIMBER_ACTUATOR_MAX_ACCELERATION)
        staticProfile = TrapezoidProfile(climberConstraints, TrapezoidProfile.State(staticTarget, 0.0))
        variableProfile = TrapezoidProfile(climberConstraints, TrapezoidProfile.State(variableTarget, 0.0))
        angleProfile = if(angleTarget != null) {
            TrapezoidProfile(actuatorConstraints, TrapezoidProfile.State(angleTarget, 0.0))
        } else {
            null
        }
    }

    override fun initialize() {
        staticPID.reset()
        variablePID.reset()
        anglePID.reset()

        time = currentTimeMillis() / 1000.0
    }

    override fun execute() {
        Climber.setStaticSpeed(staticPID.calculate(Climber.staticHeight, staticProfile.calculate(time).position))
        Climber.setVariableSpeed(variablePID.calculate(Climber.variableHeight, variableProfile.calculate(time).position))
        if(angleTarget != null) {
            Climber.setAngleSpeed(anglePID.calculate(Climber.variableAngle, angleProfile!!.calculate(time).position))
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