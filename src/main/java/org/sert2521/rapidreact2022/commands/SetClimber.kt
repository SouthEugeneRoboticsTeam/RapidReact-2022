package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.PIDs
import org.sert2521.rapidreact2022.subsytems.Climber

class SetClimber(
    private val staticHeight: Double,
    private val variableHeight: Double,
    private val variableActuator: Double,
    private val staticToleranceDis: Double = 0.01,
    private val variableToleranceDis: Double = 0.01,
    private val variableActuatorToleranceDis: Double = 1.0,
    private val staticToleranceSpeed: Double = 0.01,
    private val variableToleranceSpeed: Double = 0.01,
    private val variableActuatorToleranceSpeed: Double = 1.0,
) : CommandBase() {
    private val staticPID = PIDController(PIDs.CLIMB.p, PIDs.CLIMB.i, PIDs.CLIMB.d)
    private val variablePID = PIDController(PIDs.CLIMB.p, PIDs.CLIMB.i, PIDs.CLIMB.d)
    private val variableActuatorPID = PIDController(PIDs.ACTUATE.p, PIDs.ACTUATE.i, PIDs.ACTUATE.d)

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
        variableActuatorPID.setTolerance(variableActuatorToleranceDis, variableActuatorToleranceSpeed)

        atStaticLimit = false
        atVariableLimit = false
        atVariableActuatorLimit = false
    }

    override fun execute() {
        atStaticLimit = Climber.elevateStatic(staticPID.calculate(staticHeight - Climber.staticHeight))
        atVariableLimit = Climber.elevateStatic(variablePID.calculate(variableHeight - Climber.staticHeight))
        atVariableActuatorLimit = Climber.elevateStatic(variableActuatorPID.calculate(variableActuator - Climber.staticHeight))
    }

    override fun isFinished(): Boolean {
        return (atStaticLimit or staticPID.atSetpoint()) && (atVariableLimit or variablePID.atSetpoint()) && (atVariableActuatorLimit or variableActuatorPID.atSetpoint())
    }

    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}
