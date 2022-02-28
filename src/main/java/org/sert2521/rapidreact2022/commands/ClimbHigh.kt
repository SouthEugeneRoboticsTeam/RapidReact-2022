package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.subsytems.Climber

class ClimbHigh : SequentialCommandGroup() {
    init {
        addCommands(
            ClimbUp(),
            ClimbNext(),
            InstantCommand( { Climber.lock() } )
        )
    }
}
