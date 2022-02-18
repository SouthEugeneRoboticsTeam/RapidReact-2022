package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class ShootDoubleLeft : SequentialCommandGroup() {
    init {
        addCommands(
            InstantCommand( { Drivetrain.reset(DOUBLE_START_POSE_LEFT) } ),
        )
    }
}
