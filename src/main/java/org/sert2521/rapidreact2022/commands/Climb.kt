package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber

class Climb : SequentialCommandGroup() {
    init {
        addCommands(
                InstantCommand( { Climber.climbing = true } ),
                SetClimber(REACH_MID, HANG_HEIGHT, DEFAULT_ANGLE) { Input.getClimbNext() },
                InstantCommand( { Climber.loadBearingArm = Arms.STATIC } ),
                SetClimber(HANG_HEIGHT, HANG_HEIGHT, DEFAULT_ANGLE),
                InstantCommand( { Climber.lock() } )
        )
    }

    override fun isFinished(): Boolean {
        if(Input.getClimbLocked()) {
            Climber.lock()
            Climber.stop()
            return true
        }

        if(Input.getClimbEnd() && !Climber.committed) {
            Climber.reset()
            return true
        }

        return false
    }
}
