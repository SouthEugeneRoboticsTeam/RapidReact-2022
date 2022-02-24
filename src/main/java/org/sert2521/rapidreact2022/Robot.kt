package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.commands.IdleClimber
import org.sert2521.rapidreact2022.commands.JoystickClimb
import org.sert2521.rapidreact2022.commands.SetClimber
import org.sert2521.rapidreact2022.subsytems.Climber

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()
    private val idleClimber = IdleClimber()

    override fun robotPeriodic() {
        commandScheduler.run()
        SmartDashboardManager.update()
    }

    override fun teleopInit() {
        OI.onEnable()
        idleClimber.schedule()
    }

    override fun autonomousInit() {
        OI.onEnable()
        idleClimber.schedule()
        getAuto()?.schedule()
    }

    override fun autonomousExit() {
        getAuto()?.cancel()
    }
}