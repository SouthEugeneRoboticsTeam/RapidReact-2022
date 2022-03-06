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
                SetClimber(REACH_MID, REACH_MID, DEFAULT_ANGLE) { OI.getClimbNext() },
                SetClimber(HIT_MID, HIT_MID, HIT_MID_ANGLE) { OI.getClimbNext() },
                InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
                SetClimber(HANG_HEIGHT, HANG_HEIGHT, null)
        )
    }
}
