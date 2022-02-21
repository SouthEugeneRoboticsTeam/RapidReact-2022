package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.commands.JoystickClimb

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()
    private val climbJoystick = JoystickClimb()

    override fun robotPeriodic() {
        commandScheduler.run()
        SmartDashboardManager.update()
    }

    override fun teleopInit() {
        OI.onEnable()
        climbJoystick.schedule()
    }

    override fun teleopExit() {
        climbJoystick.cancel()
    }

    override fun autonomousInit() {
        OI.onEnable()
        getAuto()?.schedule()
    }

    override fun autonomousExit() {
        getAuto()?.cancel()
    }
}