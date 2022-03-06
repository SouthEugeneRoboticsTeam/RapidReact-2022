package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class ClimbNext : SequentialCommandGroup() {
    init {
        addCommands(
                InstantCommand( { Climber.setLockStatic(LockStates.LOCKED) } ),
                WaitUntilCommand { Climber.isStaticLocked() == LockStates.LOCKED },
                InstantCommand( { Climber.loadBearingArm = Arms.VARIABLE } ),
                SetClimber(HANG_HEIGHT, LET_GO_BAR_VARIABLE, null),
                SetClimber(HANG_HEIGHT, LET_GO_BAR_VARIABLE, PAST_NEXT_ANGLE),
                SetClimber(HANG_HEIGHT, ABOVE_NEXT, PAST_NEXT_ANGLE),
                ClimberHitBar(HANG_HEIGHT, ABOVE_NEXT) { OI.getClimbNext() },
                ClimberHitBar(HANG_HEIGHT, PULL_IN_NEXT) { OI.getClimbNext() },
                InstantCommand( { Climber.setLockVariable(LockStates.LOCKED) } ),
                WaitUntilCommand { Climber.isVariableLocked() == LockStates.LOCKED },
                InstantCommand( { Climber.setLockStatic(LockStates.UNLOCKED) } ),
                WaitUntilCommand { Climber.isStaticLocked() == LockStates.UNLOCKED },
                InstantCommand( { Climber.loadBearingArm = Arms.NEITHER } ),
                SetClimber(LET_GO_STATIC, PULL_IN_NEXT, null),
                SetClimber(GO_UNDER, PULL_IN_NEXT, UNDER_ANGLE)
        )
    }
}
