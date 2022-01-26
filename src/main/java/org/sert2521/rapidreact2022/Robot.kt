package org.sert2521.rapidreact2022

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.trajectory.TrajectoryConfig
import edu.wpi.first.math.trajectory.TrajectoryGenerator
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.sert2521.rapidreact2022.commands.DrivePath

object Robot : TimedRobot() {
    private val commandScheduler = CommandScheduler.getInstance()

    private var autoChooser = SendableChooser<Command?>()

    init {
        autoChooser.setDefaultOption("Do Nothing", null)

        val forward = TrajectoryGenerator.generateTrajectory(
            Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            listOf(),
            Pose2d(1.0, 0.0, Rotation2d.fromDegrees(0.0)),
            TrajectoryConfig(0.5, 0.5))
        autoChooser.addOption("Drive Forward", DrivePath(forward))

        val forwardAndRight = TrajectoryGenerator.generateTrajectory(
            Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            listOf(),
            Pose2d(1.0, 0.0, Rotation2d.fromDegrees(0.0)),
            TrajectoryConfig(0.5, 0.5))
        autoChooser.addOption("Drive Forward And Right", DrivePath(forwardAndRight))

        SmartDashboard.putData(autoChooser)
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