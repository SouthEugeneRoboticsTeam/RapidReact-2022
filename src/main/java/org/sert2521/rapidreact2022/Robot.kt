package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.subsytems.Climber

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

    override fun robotPeriodic() {
        commandScheduler.run()
        SmartDashboardManager.update()
    }

    override fun teleopInit() {
        OI.onEnable()
        Climber.onEnable()
    }

    override fun autonomousInit() {
        OI.onEnable()
        Climber.onEnable()
        getAuto()?.schedule()
    }

    override fun autonomousExit() {
        getAuto()?.cancel()
    }
}