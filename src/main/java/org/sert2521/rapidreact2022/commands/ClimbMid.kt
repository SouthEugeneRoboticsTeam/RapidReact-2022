package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*

class ClimbMid : SequentialCommandGroup() {
    init {
        addCommands(
            SetClimber(MID_HEIGHT, DEFAULT_HEIGHT, DEFAULT_ACTUATOR_ANGLE),
            SetClimber(HANG_HEIGHT, DEFAULT_HEIGHT, DEFAULT_ACTUATOR_ANGLE)
        )
    }
}
