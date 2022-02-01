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
import org.sert2521.rapidreact2022.PIDs
import org.sert2521.rapidreact2022.SimpleFeedForwards
import org.sert2521.rapidreact2022.TRACK_WIDTH
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class DrivePath(trajectory: Trajectory) : SequentialCommandGroup() {
    init {
        addRequirements(Drivetrain)
        Drivetrain.reset()
        addCommands(
                InstantCommand(Drivetrain::reset),
                RamseteCommand(
                    trajectory,
                    { Drivetrain.pose },
                    RamseteController(),
                    SimpleMotorFeedforward(SimpleFeedForwards.DRIVE.s, SimpleFeedForwards.DRIVE.v, SimpleFeedForwards.DRIVE.a),
                    DifferentialDriveKinematics(TRACK_WIDTH),
                    { DifferentialDriveWheelSpeeds(Drivetrain.leftVelocity, Drivetrain.rightVelocity) },
                    PIDController(PIDs.DRIVE.p, PIDs.DRIVE.i, PIDs.DRIVE.d),
                    PIDController(PIDs.DRIVE.p, PIDs.DRIVE.i, PIDs.DRIVE.d),
                    Drivetrain::tankDriveVolts,
                    Drivetrain),
                InstantCommand(Drivetrain::stop))
    }
}
