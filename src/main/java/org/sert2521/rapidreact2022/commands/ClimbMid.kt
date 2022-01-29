package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.DEFAULT_ACTUATOR_ANGLE
import org.sert2521.rapidreact2022.DEFAULT_HEIGHT
import org.sert2521.rapidreact2022.HANG_HEIGHT
import org.sert2521.rapidreact2022.MID_HEIGHT

class ClimbMid : SequentialCommandGroup() {
    init {
        addCommands(
            SetClimber(MID_HEIGHT, DEFAULT_HEIGHT, DEFAULT_ACTUATOR_ANGLE),
            JoystickAdjustClimber(),
            SetClimber(HANG_HEIGHT, DEFAULT_HEIGHT, DEFAULT_ACTUATOR_ANGLE)
        )
    }
}
