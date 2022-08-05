package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber

class ClimbMid : SequentialCommandGroup() {
    init {
        addCommands(
                InstantCommand( { Climber.climbing = true } ),
                SetClimber(REACH_MID, HANG_HEIGHT, PAST_HIGH_ANGLE) { Input.getClimbNext() },
                InstantCommand( { Climber.committed = true } ),
                InstantCommand( { Climber.loadBearingArm = Arms.STATIC } ),
                SetClimber(HANG_HEIGHT, HANG_HEIGHT, PAST_HIGH_ANGLE),
                InstantCommand( { Climber.lock() } ),
                SetClimber(HANG_HEIGHT, HANG_HEIGHT, PAST_HIGH_ANGLE) { false }.withTimeout(150.0)
        )
    }

    override fun isFinished(): Boolean {
        if(Input.getClimbLocked()) {
            Climber.lock()
            Climber.stop()
            return true
        }

        return false
    }
}
