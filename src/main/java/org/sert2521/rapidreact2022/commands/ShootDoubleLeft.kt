package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class ShootDoubleLeft : SequentialCommandGroup() {
    init {
        addCommands(
            InstantCommand( { Drivetrain.reset(DOUBLE_START_POSE_LEFT) } ),
            DrivePath(AUTO_SPEED, AUTO_ACCELERATION, false, DOUBLE_START_POSE_LEFT, PICKUP_POSE_LEFT_DOUBLE).deadlineWith(IntakeBalls()),
            DrivePath(AUTO_TURN_SPEED, AUTO_TURN_ACCELERATION, true, PICKUP_POSE_LEFT_DOUBLE, ENTRY_POSE_LEFT).deadlineWith(IntakeBalls().withTimeout(0.5)),
            DrivePath(AUTO_SPEED, AUTO_ACCELERATION, false, ENTRY_POSE_LEFT, SHOOT_POSE, endSpeed = END_SPEED).deadlineWith(RevShooter()),
            ShootBalls().withTimeout(SHOOT_TIME),
            InstantCommand( { Drivetrain.stop() } )
        )
    }
}
