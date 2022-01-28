package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.HANG_HEIGHT
import org.sert2521.rapidreact2022.MID_HEIGHT

class ClimbMid : SequentialCommandGroup() {
    init {
        addCommands(
            SetClimber(MID_HEIGHT, 0.0, 0.0),
            JoystickAdjustClimber(),
            SetClimber(HANG_HEIGHT, 0.0, 0.0)
        )
    }
}
