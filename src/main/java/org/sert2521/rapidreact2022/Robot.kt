package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.commands.ControlShooter

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()
    private val controlShooter = ControlShooter()

    override fun robotPeriodic() {
        commandScheduler.run()
        SmartDashboardManager.update()
    }

    override fun teleopInit() {
        controlShooter.schedule()
    }

    override fun teleopExit() {
        controlShooter.cancel()
    }

    override fun autonomousInit() {
        Preferences.getAuto()?.schedule()
    }

    override fun autonomousExit() {
        Preferences.getAuto()?.cancel()
    }
}