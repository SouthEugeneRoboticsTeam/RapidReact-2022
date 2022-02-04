package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.commands.ControlShooter

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()
    private val controlShooter = ControlShooter()

    init {
        initShuffleboard()
    }

    fun getClimb(): Command {
        return climbChooser.selected!!
    }

    override fun robotPeriodic() {
        commandScheduler.run()
    }

    override fun teleopInit() {
        controlShooter.schedule()
    }

    override fun teleopExit() {
        controlShooter.cancel()
    }

    override fun autonomousInit() {
        autoChooser.selected?.schedule()
    }

    override fun autonomousExit() {
        autoChooser.selected?.cancel()
    }
}