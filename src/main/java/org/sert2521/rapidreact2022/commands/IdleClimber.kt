package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.DEFAULT_ACTUATOR_ANGLE
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.Climber

/*class IdleClimber : CommandBase() {
    private val variableActuatorPID: PIDController

    init {
        addRequirements(Climber)

        val actuatorPIDArray = robotPreferences.actuatorPID
        variableActuatorPID = PIDController(actuatorPIDArray[0], actuatorPIDArray[1], actuatorPIDArray[2])
    }

    override fun initialize() {

    }

    override fun execute() {
        Climber.actuateVariable(variableActuatorPID.calculate(DEFAULT_ACTUATOR_ANGLE - Climber.variableAngle))
    }
    override fun end(interrupted: Boolean) {
        Climber.stop()
    }
}
*/