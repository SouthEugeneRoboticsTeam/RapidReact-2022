package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class ShootSingle : SequentialCommandGroup() {
    init {
        Drivetrain.reset(SHOOT_POSE)
        addCommands(
            ShootBalls().withTimeout(SHOOT_TIME),
            DrivePath(AUTO_SPEED, AUTO_ACCELERATION, true, SHOOT_POSE, END_POSE)
        )
    }
}
