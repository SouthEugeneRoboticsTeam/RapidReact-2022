package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class ClimbTraversal : SequentialCommandGroup() {
    init {
        addCommands(
            ClimbUp(),
            ClimbNext(),
            SetClimberPID(LET_GO_MID_HEIGHT, HIT_NEXT_HEIGHT, RESET_ANGLE),
            SetClimberPID(RESET_HEIGHT, HIT_NEXT_HEIGHT, RESET_ANGLE),
            SetClimberPID(RESET_HEIGHT, HIT_NEXT_HEIGHT, REHOOK_ANGLE),
            SetClimberLinear(HIT_NEXT_HEIGHT, HIT_MID_HEIGHT, REHOOK_ANGLE, staticSpeed = LOW_SPEED, variableSpeed = LOW_SPEED) { OI.getClimbNext() },
            InstantCommand( { Climber.setLockVariable(LockStates.UNLOCKED) } ),
            WaitUntilCommand { Climber.isVariableLocked() == LockStates.UNLOCKED },
            SetClimberPID(HANG_HEIGHT, HANG_HEIGHT, TOP_ANGLE_HANG),
            ClimbNext(),
            InstantCommand( { Climber.lock() } )
        )
    }
}
