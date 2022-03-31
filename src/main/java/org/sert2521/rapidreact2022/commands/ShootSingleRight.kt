package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class ShootSingleRight : SequentialCommandGroup() {
    init {
        addCommands(
            InstantCommand( { Drivetrain.reset(SHOOT_POSE) } ),
            ShootBalls(1),
            DrivePath(AUTO_EXIT_SPEED, AUTO_EXIT_ACCELERATION, true, SHOOT_POSE, END_POSE_SINGLE_RIGHT)
        )
    }
}
