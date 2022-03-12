package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber

class ClimbUp : SequentialCommandGroup() {
    init {
        addCommands(
                InstantCommand( { Climber.climbing = true } ),
                SetClimber(REACH_MID, HANG_HEIGHT, DEFAULT_ANGLE) { OI.getClimbNext() },
                ClimberHitBar(HIT_MID, HANG_HEIGHT, climberHitSpeed = HIT_MID_SPEED) { OI.getClimbNext() },
                SetClimber(HIT_MID, HANG_HEIGHT, DEFAULT_ANGLE) { OI.getClimbNext() },
                InstantCommand( { Climber.inAir = true } ),
                InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
                SetClimber(HANG_HEIGHT, HANG_HEIGHT, null)
        )
    }
}
