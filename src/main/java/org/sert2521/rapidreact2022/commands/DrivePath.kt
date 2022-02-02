package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.RamseteController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.math.trajectory.Trajectory
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.RamseteCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.Preferences
import org.sert2521.rapidreact2022.TRACK_WIDTH
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class DrivePath(trajectory: Trajectory) : SequentialCommandGroup() {
    init {
        addRequirements(Drivetrain)
        Drivetrain.reset()

        val drivePIDArray = Preferences.getDrivePID()
        val driveFeedForwardArray = Preferences.getDriveFeedForward()
        addCommands(
                InstantCommand(Drivetrain::reset),
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
