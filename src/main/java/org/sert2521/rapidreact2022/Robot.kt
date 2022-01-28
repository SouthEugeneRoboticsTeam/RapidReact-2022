package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.commands.ClimbMid
import org.sert2521.rapidreact2022.commands.ClimbTransversal
import org.sert2521.rapidreact2022.commands.DrivePath

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()

    private val autoChooser = SendableChooser<Command>()
    private val climbChooser = SendableChooser<Command>()

    init {
        val autos = AutoPaths.values()

        autoChooser.setDefaultOption(autos[0].shuffleBoardName, DrivePath(autos[0].trajectory))
        for(i in 1..autos.size) {
            autoChooser.addOption(autos[i].shuffleBoardName, DrivePath(autos[i].trajectory))
        }

        SmartDashboard.putData(autoChooser)

        climbChooser.setDefaultOption("Mid", ClimbMid())
        climbChooser.addOption("Transversal", ClimbTransversal())
        SmartDashboard.putData(climbChooser)
    }

    fun getClimb(): Command {
        return climbChooser.selected!!
    }

    override fun robotPeriodic() {
        commandScheduler.run()
    }

    override fun autonomousInit() {
        autoChooser.selected.schedule()
    }

    override fun autonomousExit() {
        autoChooser.selected.cancel()
    }
}