package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.LockStates

class ClimbMid : SequentialCommandGroup() {
    init {
        //Fix climbing mode getting stuck
        //SetClimber should check locks before end
        //Don't start at infinity
        addCommands(
            SetClimber(MID_HEIGHT, null, DEFAULT_ACTUATOR_ANGLE),
            WaitUntilCommand { OI.getClimbNext() && Climber.isStaticLocked()  == LockStates.LOCKED && Climber.isVariableLocked() == LockStates.LOCKED },
            InstantCommand( { Climber.climbing = true } ),
            SetClimber(HANG_HEIGHT, null, DEFAULT_ACTUATOR_ANGLE)
        )
    }
}
