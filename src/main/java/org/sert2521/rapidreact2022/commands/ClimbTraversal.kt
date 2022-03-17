package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

//Add overriding climb sequence with joystick
class ClimbTraversal : SequentialCommandGroup() {
    init {
        addCommands(
                ClimbUp(),
                ClimbNext(),
                SetClimber(GO_UNDER, PULL_IN_NEXT, PAST_CURRENT_ANGLE),
                SetClimber(ABOVE_CURRENT, PULL_IN_NEXT, PAST_CURRENT_ANGLE),
                ClimberHitBar(ABOVE_CURRENT, PULL_IN_NEXT) { OI.getClimbNext() },
                ClimberHitBar(HIT_CURRENT, PULL_IN_NEXT),
                InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
                SetClimber(HANG_HEIGHT, HANG_HEIGHT, null),
                InstantCommand( { Climber.loadBearingArm = Arms.STATIC } ),
                SetClimber(HANG_HEIGHT, LET_GO_BAR_VARIABLE, null),
                SetClimber(HANG_HEIGHT, LET_GO_BAR_VARIABLE, PAST_NEXT_ANGLE),
                ClimbNext(),
                SetClimber(END, PULL_IN_NEXT, END_ANGLE),
                InstantCommand( { Climber.lock() } )
        )
    }

    override fun isFinished(): Boolean {
        return OI.getClimbEnd() && !Climber.climbing
    }

    override fun end(interrupted: Boolean) {
        Climber.reset()
    }
}
