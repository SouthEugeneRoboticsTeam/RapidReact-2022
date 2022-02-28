package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class ClimbHigh : SequentialCommandGroup() {
    init {
        addCommands(
            InstantCommand( { OI.climbing = true} ),
            SetClimberPID(MID_HEIGHT, MID_HEIGHT, DEFAULT_ANGLE) { OI.getClimbNext() },
            SetClimberLinear(HIT_MID_HEIGHT, HIT_MID_HEIGHT, HIT_MID_ANGLE, staticSpeed = LOW_SPEED, variableSpeed = LOW_SPEED) { OI.getClimbNext() },
            InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
            SetClimberPID(HANG_HEIGHT, HANG_HEIGHT, MID_ANGLE_HANG),
            InstantCommand( { Climber.setLockStatic(LockStates.LOCKED) } ),
            WaitUntilCommand { Climber.isStaticLocked() == LockStates.LOCKED },
            InstantCommand( { Climber.loadBearingArm = Arms.NEITHER } ),
            SetClimberPID(HANG_HEIGHT, UNHOOK_MID_HEIGHT, MID_ANGLE_HANG),
            SetClimberPID(HANG_HEIGHT, REACH_HIGH_HEIGHT, REACH_HIGH_ANGLE) { OI.getClimbNext() },
            SetClimberPID(HANG_HEIGHT, REACH_HIGH_HEIGHT, HIT_HIGH_ANGLE) { OI.getClimbNext() },
            SetClimberLinear(HANG_HEIGHT, HIT_HIGH_HEIGHT, HIT_HIGH_ANGLE, variableSpeed = LOW_SPEED),
            InstantCommand( { Climber.setLockVariable(LockStates.LOCKED) } ),
            WaitUntilCommand { Climber.isVariableLocked() == LockStates.LOCKED && OI.getClimbNext() },
            InstantCommand( { Climber.setLockStatic(LockStates.UNLOCKED) } ),
            WaitUntilCommand { Climber.isStaticLocked() == LockStates.UNLOCKED },
            SetClimberLinear(LET_GO_MID_HEIGHT, HIT_HIGH_HEIGHT, HIT_HIGH_ANGLE, staticSpeed = RELEASE_SPEED, angleOn = false),
            SetClimberPID(LET_GO_MID_HEIGHT, HIT_HIGH_HEIGHT, DEFAULT_ANGLE),
            InstantCommand( { Climber.lock() } )
        )
    }
}
