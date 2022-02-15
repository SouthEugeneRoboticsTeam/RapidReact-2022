package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.RamseteController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.math.trajectory.TrajectoryConfig
import edu.wpi.first.math.trajectory.TrajectoryGenerator.generateTrajectory
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.RamseteCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.Preferences
import org.sert2521.rapidreact2022.TRACK_WIDTH
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class DrivePath(start: Pose2d, end: Pose2d, trajectoryConfig: TrajectoryConfig) : SequentialCommandGroup() {
    init {
        addRequirements(Drivetrain)

        val trajectory = generateTrajectory(
            start,
            listOf(),
            end,
            trajectoryConfig
        )

        val drivePIDArray = Preferences.getDrivePID()
        val driveFeedForwardArray = Preferences.getDriveFeedForward()
        addCommands(
            RamseteCommand(
                trajectory,
                { Drivetrain.pose },
                RamseteController(),
                SimpleMotorFeedforward(driveFeedForwardArray[0], driveFeedForwardArray[1], driveFeedForwardArray[2]),
                DifferentialDriveKinematics(TRACK_WIDTH),
                { DifferentialDriveWheelSpeeds(Drivetrain.leftVelocity, Drivetrain.rightVelocity) },
                PIDController(drivePIDArray[0], drivePIDArray[1], drivePIDArray[2]),
                PIDController(drivePIDArray[0], drivePIDArray[1], drivePIDArray[2]),
                Drivetrain::tankDriveVolts,
                Drivetrain),
            InstantCommand(Drivetrain::stop))
    }
}