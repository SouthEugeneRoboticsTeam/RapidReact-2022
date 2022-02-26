package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Climber

class ClimbTraversal : SequentialCommandGroup() {
    init {
        addCommands(
            InstantCommand( { OI.climbing = true} ),
            SetClimber(MID_HEIGHT, MIN_CLIMBER_HEIGHT, DEFAULT_ANGLE) { OI.getClimbNext() },
            SetClimber(HANG_HEIGHT, REACH_HIGH_HEIGHT, REACH_HIGH_ANGLE) { OI.getClimbNext() },
            SetClimber(HANG_HEIGHT, REACH_HIGH_HEIGHT, HIT_HIGH_ANGLE) { OI.getClimbNext() },
            SetClimber(LET_GO_MID_HEIGHT, HANG_HEIGHT, HIT_HIGH_ANGLE) { OI.getClimbNext() },
            SetClimber(LET_GO_MID_HEIGHT, HANG_HEIGHT, LET_GO_MID_ANGLE),
            InstantCommand( { Climber.lock() } )
        )
    }
}
