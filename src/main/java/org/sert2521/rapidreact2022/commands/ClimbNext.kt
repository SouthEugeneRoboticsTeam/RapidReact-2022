package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber

class ClimbNext : SequentialCommandGroup() {
    init {
        addCommands(
                SetClimber(HANG_HEIGHT, ABOVE_NEXT, PAST_NEXT_ANGLE),
                ClimberHitBar(HANG_HEIGHT, ABOVE_NEXT) { OI.getClimbNext() },
                InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
                ClimberHitBar(HANG_HEIGHT, HIT_NEXT),
                InstantCommand( { Climber.loadBearingArm = Arms.VARIABLE } ),
                SetClimber(LET_GO_STATIC, PULL_IN_NEXT, null)
        )
    }
}
