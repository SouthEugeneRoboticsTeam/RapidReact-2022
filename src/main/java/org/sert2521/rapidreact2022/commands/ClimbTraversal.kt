package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class ClimbTraversal : SequentialCommandGroup() {
    init {
        addCommands(
            ClimbUp(),
            ClimbNext(),
            SetClimberPID(LET_GO_MID_HEIGHT, HANG_NEXT_HEIGHT, RESET_ANGLE),
            SetClimberPID(RESET_HEIGHT, HANG_NEXT_HEIGHT, RESET_ANGLE),
            SetClimberPID(RESET_HEIGHT, HANG_NEXT_HEIGHT, REHOOK_ANGLE),
            SetClimberLinear(LOCK_NEXT_HEIGHT, HANG_NEXT_HEIGHT, REHOOK_ANGLE, staticSpeed = LOW_SPEED, angleOn = false),
            WaitUntilCommand { OI.getClimbNext() },
            InstantCommand( { Climber.setLockVariable(LockStates.UNLOCKED) } ),
            WaitUntilCommand { Climber.isVariableLocked() == LockStates.UNLOCKED },
            InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
            SetClimberPID(HANG_HEIGHT, HANG_HEIGHT, TOP_ANGLE_HANG),
            ClimbNext(),
            SetClimberPID(END_HEIGHT, HIT_NEXT_HEIGHT, END_ANGLE),
            InstantCommand( { Climber.lock() } )
        )
    }
}
