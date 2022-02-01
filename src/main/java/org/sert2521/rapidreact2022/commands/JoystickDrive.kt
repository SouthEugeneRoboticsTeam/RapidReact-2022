package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.OI
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import kotlin.math.abs

class JoystickDrive : CommandBase() {
    init {
        addRequirements(Drivetrain)
    }

    override fun initialize() {
        Drivetrain.reset()
    }

    //Squaring(while persevering sign) the input allows for finer control at low values and the ability to go max speed
    private fun joystickToWheelPercent(amount: Double): Double {
        return amount * abs(amount)
    }

    override fun execute() {
        Drivetrain.arcadeDrive(joystickToWheelPercent(OI.yAxis), joystickToWheelPercent(OI.xAxis))
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.stop()
    }
}