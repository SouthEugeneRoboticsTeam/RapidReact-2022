package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()

    init {
        initShuffleboard()
    }

    fun getClimb(): Command {
        return climbChooser.selected!!
    }

    override fun robotPeriodic() {
        commandScheduler.run()
    }

    override fun autonomousInit() {
        autoChooser.selected?.schedule()
    }

    override fun autonomousExit() {
        autoChooser.selected?.cancel()
    }
}