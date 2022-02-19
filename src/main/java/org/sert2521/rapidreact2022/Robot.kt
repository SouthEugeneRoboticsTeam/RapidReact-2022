package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()

    override fun robotPeriodic() {
        commandScheduler.run()
        SmartDashboardManager.update()
    }

    override fun autonomousInit() {
        getAuto()?.schedule()
    }

    override fun autonomousExit() {
        getAuto()?.cancel()
    }
}