package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.Drivetrain

//Add setup instructions
//Servos positions
//Cycle robot before changing servos
//Static all way back on start
//Variable all way back on start
//Start arms in proper pos
//Put ball in deep
//Things at competition
//Also reset gyro
object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()

    override fun robotInit() {
        Input.onInit()
    }

    override fun robotPeriodic() {
        commandScheduler.run()
        Logging.update()
    }

    override fun teleopInit() {
        Drivetrain.brakeMode()
        Input.onEnable()
        Climber.onEnable()
    }

    override fun teleopExit() {
        //Add before comp
        //Drivetrain.coastMode()
    }

    override fun autonomousInit() {
        Drivetrain.brakeMode()
        Input.onEnable()
        Climber.onEnable()
        Input.getAuto()?.schedule()
    }

    override fun autonomousExit() {
        Input.getAuto()?.cancel()
    }

    override fun testInit() {
        Drivetrain.coastMode()
    }
}