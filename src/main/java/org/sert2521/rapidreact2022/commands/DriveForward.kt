package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class DriveForward : SequentialCommandGroup() {
    init {
        val start = Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0))
        val end = Pose2d(DRIVE_FORWARD_DISTANCE, 0.0, Rotation2d.fromDegrees(0.0))
        addCommands(
            InstantCommand( { Drivetrain.reset(start) } ),
            DrivePath(AUTO_EXIT_SPEED, AUTO_EXIT_ACCELERATION, false, start, end)
        )
    }
}
