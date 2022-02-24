package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.commands.IdleClimber
import org.sert2521.rapidreact2022.commands.SetClimber
import org.sert2521.rapidreact2022.subsytems.Climber

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()

    override fun robotPeriodic() {
        commandScheduler.run()
        SmartDashboardManager.update()
    }

    override fun teleopInit() {
        OI.onEnable()
        val moveUp = SetClimber(0.2, 0.2, null, 0.02, 0.02, null)
        IdleClimber().andThen(moveUp).schedule()
    }

    override fun teleopExit() {
    }

    override fun autonomousInit() {
        OI.onEnable()
        getAuto()?.schedule()
    }

    override fun autonomousExit() {
        getAuto()?.cancel()
    }
}