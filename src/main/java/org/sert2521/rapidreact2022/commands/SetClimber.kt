package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.PIDs
import org.sert2521.rapidreact2022.subsytems.Climber

class SetClimber(
    private val staticHeight: Double,
    private val variableHeight: Double,
    private val variableAngle: Double,
    private val staticToleranceDis: Double = 0.01,
    private val variableToleranceDis: Double = 0.01,
    private val variableAngleToleranceDis: Double = 1.0,
    private val staticToleranceSpeed: Double = 0.01,
    private val variableToleranceSpeed: Double = 0.01,
    private val variableAngleToleranceSpeed: Double = 1.0,
) : CommandBase() {
    private val staticPID = PIDController(PIDs.CLIMBER.p, PIDs.CLIMBER.i, PIDs.CLIMBER.d)
    private val variablePID = PIDController(PIDs.CLIMBER.p, PIDs.CLIMBER.i, PIDs.CLIMBER.d)
    private val variableActuatorPID = PIDController(PIDs.ACTUATOR.p, PIDs.ACTUATOR.i, PIDs.ACTUATOR.d)

    var atStaticLimit = false
    var atVariableLimit = false
    var atVariableActuatorLimit = false

    init {
        addRequirements(Climber)
    }

    override fun initialize() {
        staticPID.reset()
        staticPID.setTolerance(staticToleranceDis, staticToleranceSpeed)

        variablePID.reset()
        variablePID.setTolerance(variableToleranceDis, variableToleranceSpeed)

        variableActuatorPID.reset()
        variableActuatorPID.setTolerance(variableAngleToleranceDis, variableAngleToleranceSpeed)

        atStaticLimit = false
        atVariableLimit = false
        atVariableActuatorLimit = false
    }

    override fun execute() {
        atStaticLimit = Climber.elevateStatic(staticPID.calculate(staticHeight - Climber.staticHeight))
        atVariableLimit = Climber.elevateStatic(variablePID.calculate(variableHeight - Climber.variableHeight))
        atVariableActuatorLimit = Climber.elevateStatic(variableActuatorPID.calculate(variableAngle - Climber.variableAngle))
    }

    override fun isFinished(): Boolean {
        return (atStaticLimit or staticPID.atSetpoint()) && (atVariableLimit or variablePID.atSetpoint()) && (atVariableActuatorLimit or variableActuatorPID.atSetpoint())
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}
