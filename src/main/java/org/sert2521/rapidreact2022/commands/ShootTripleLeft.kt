package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class ShootTripleLeft : SequentialCommandGroup() {
    init {
        addCommands(
            InstantCommand( { Drivetrain.reset(SHOOT_POSE) } ),
            ShootBalls(1, false),
            DrivePath(AUTO_TRIPLE_TURN_SPEED, AUTO_TRIPLE_TURN_ACCELERATION, true, SHOOT_POSE, TO_PICKUP_ONE),
            DrivePath(AUTO_TRIPLE_SPEED, AUTO_TRIPLE_ACCELERATION, false, DOUBLE_START_POSE_LEFT, PICKUP_POSE_ONE_TRIPLE).deadlineWith(IntakeBalls()),
            DrivePath(AUTO_TRIPLE_SPEED, AUTO_TRIPLE_ACCELERATION, true, PICKUP_POSE_ONE_TRIPLE, ENTRY_POSE_LEFT_ONE_TRIPLE).deadlineWith(IntakeBalls().withTimeout(0.5)),
            DrivePath(AUTO_TRIPLE_SPEED, AUTO_TRIPLE_ACCELERATION, false, ENTRY_POSE_LEFT_ONE_TRIPLE, PICKUP_POSE_TWO_TRIPLE).deadlineWith(IntakeBalls()),
            DrivePath(AUTO_TRIPLE_SPEED, AUTO_TRIPLE_TURN_ACCELERATION, true, PICKUP_POSE_TWO_TRIPLE, ENTRY_POSE_LEFT_TWO_TRIPLE).deadlineWith(IntakeBalls().withTimeout(0.5)),
            DrivePath(AUTO_TRIPLE_SPEED, AUTO_TRIPLE_ACCELERATION, false, ENTRY_POSE_LEFT_TWO_TRIPLE, END_POSE_TRIPLE, endSpeed = END_SPEED_TRIPLE).deadlineWith(RevShooter()),
            ShootBalls(2),
            InstantCommand( { Drivetrain.stop() } )
        )
    }
}
