package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.trajectory.TrajectoryConfig
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.END_POSE
import org.sert2521.rapidreact2022.SHOOT_POSE
import org.sert2521.rapidreact2022.SHOOT_TIME
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class ShootSingle : SequentialCommandGroup() {
    init {
        val trajectoryConfig = TrajectoryConfig(1.0, 0.7)
        trajectoryConfig.isReversed = true
        Drivetrain.reset(SHOOT_POSE)
        addCommands(
            ShootBalls().withTimeout(SHOOT_TIME),
            DrivePath(SHOOT_POSE, END_POSE, trajectoryConfig)
        )
    }
}
