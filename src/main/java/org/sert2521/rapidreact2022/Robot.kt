package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.commands.SetClimber
import org.sert2521.rapidreact2022.subsytems.Climber

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()
    private val climb = SetClimber(0.5, Climber.variableAngle, Climber.variableAngle, 0.1, 0.1, 0.1)

    override fun robotPeriodic() {
        commandScheduler.run()
        SmartDashboardManager.update()
    }

    override fun teleopInit() {
        OI.onEnable()

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