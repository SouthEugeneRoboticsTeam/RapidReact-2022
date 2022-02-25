package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.commands.ResetClimber

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()
    private val resetClimber = ResetClimber()

    override fun robotPeriodic() {
        commandScheduler.run()
        SmartDashboardManager.update()
    }

    override fun teleopInit() {
        OI.onEnable()
        resetClimber.schedule()
    }

    override fun autonomousInit() {
        OI.onEnable()
        resetClimber.schedule()
        getAuto()?.schedule()
    }

    override fun autonomousExit() {
        getAuto()?.cancel()
    }
}