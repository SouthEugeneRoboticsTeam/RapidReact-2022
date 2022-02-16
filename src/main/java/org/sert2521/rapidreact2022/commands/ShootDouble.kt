package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.trajectory.TrajectoryConfig
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Drivetrain

class ShootDouble : SequentialCommandGroup() {
    init {
        val trajectoryConfig = TrajectoryConfig(0.3, 0.5)
        trajectoryConfig.isReversed = true
        Drivetrain.reset(SHOOT_POSE)
        addCommands(
            DrivePath(DOUBLE_START_POSE, PICKUP_POSE, trajectoryConfig).deadlineWith(IntakeBalls()),
            DrivePath(PICKUP_POSE, SHOOT_POSE, trajectoryConfig).deadlineWith(IntakeBalls()),
            ShootBalls().withTimeout(SHOOT_TIME),
            DrivePath(SHOOT_POSE, END_POSE, trajectoryConfig)
        )
    }
}
