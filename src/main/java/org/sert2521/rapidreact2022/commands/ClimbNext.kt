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
            InstantCommand( { Climber.loadBearingArm = Arms.NEITHER } ),
            SetClimberPID(HANG_HEIGHT, UNHOOK_HEIGHT, UNHOOK_ANGLE),
            SetClimberPID(HANG_HEIGHT, REACH_NEXT_HEIGHT, REACH_NEXT_ANGLE),
            SetClimberPID(HANG_HEIGHT, REACH_NEXT_HEIGHT, HIT_NEXT_FAR_ANGLE) { Input.getClimbNext() },
            SetClimberLinear(HANG_HEIGHT, HIT_NEXT_HEIGHT, HIT_NEXT_CLOSE_ANGLE, variableSpeed = LOW_SPEED),
            SetClimberLinear(HANG_HEIGHT, HANG_NEXT_HEIGHT, HANG_NEXT_ANGLE, variableSpeed = LOW_SPEED, angleOn = false),
            InstantCommand( { Climber.setLockVariable(LockStates.LOCKED) } ),
            WaitUntilCommand { Climber.isVariableLocked() == LockStates.LOCKED },
            InstantCommand( { Climber.setLockStatic(LockStates.UNLOCKED) } ),
            WaitUntilCommand { Climber.isStaticLocked() == LockStates.UNLOCKED },
            SetClimberLinear(LET_GO_MID_HEIGHT, HANG_NEXT_HEIGHT, HIT_NEXT_CLOSE_ANGLE, staticSpeed = RELEASE_SPEED, angleOn = false),
            SetClimberPID(GO_UNDER_HEIGHT, HANG_NEXT_HEIGHT, DEFAULT_ANGLE)
        )
    }
}
