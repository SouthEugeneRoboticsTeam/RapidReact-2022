package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.endPose
import org.sert2521.rapidreact2022.shootPose
import org.sert2521.rapidreact2022.startPose

class ShootSingle : SequentialCommandGroup() {
    init {
        addCommands(
            DrivePath(startPose, shootPose),
            ShootBalls().withTimeout(4.0),
            DrivePath(shootPose, endPose),
        )
    }
}
