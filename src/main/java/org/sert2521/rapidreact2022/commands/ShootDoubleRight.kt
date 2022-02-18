package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class ShootDoubleRight : SequentialCommandGroup() {
    init {
        addCommands(
            InstantCommand( { Drivetrain.reset(DOUBLE_START_POSE_RIGHT) } ),
            DrivePath(AUTO_SPEED, AUTO_ACCELERATION, false, DOUBLE_START_POSE_RIGHT, PICKUP_POSE_RIGHT).deadlineWith(IntakeBalls()),
            DrivePath(AUTO_TURN_SPEED, AUTO_TURN_ACCELERATION, true, PICKUP_POSE_RIGHT, ENTRY_POSE_RIGHT).deadlineWith(IntakeBalls()),
            DrivePath(AUTO_SPEED, AUTO_ACCELERATION, false, ENTRY_POSE_RIGHT, SHOOT_POSE),
            ShootBalls().withTimeout(SHOOT_TIME * 2),
            DrivePath(AUTO_EXIT_SPEED, AUTO_EXIT_ACCELERATION, true, SHOOT_POSE, END_POSE_DOUBLE)
        )
    }
}