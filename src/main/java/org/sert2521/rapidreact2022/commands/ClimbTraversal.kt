package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*

class ClimbTraversal : SequentialCommandGroup() {
    init {
        addCommands(
            SetClimber(MID_HEIGHT, DEFAULT_HEIGHT, DEFAULT_ACTUATOR_ANGLE),
            JoystickAdjustClimber(),
            SetClimber(HANG_HEIGHT, NEXT_UNHOOKED_BAR_HEIGHT, FORWARD_UNHOOKED_ACTUATOR_ANGLE),
            SetClimber(HANG_HEIGHT, NEXT_HOOKED_BAR_HEIGHT, FORWARD_HOOKED_ACTUATOR_ANGLE),
            JoystickAdjustClimber(),
            SetClimber(LET_GO_HEIGHT, HANG_HEIGHT, FORWARD_UNHOOKED_ACTUATOR_ANGLE),
            SetClimber(DEFAULT_HEIGHT, HANG_HEIGHT, DEFAULT_ACTUATOR_ANGLE),
            SetClimber(NEXT_UNHOOKED_BAR_HEIGHT, DEFAULT_HEIGHT, BACKWARD_UNHOOKED_ACTUATOR_ANGLE),
            SetClimber(NEXT_HOOKED_BAR_HEIGHT, DEFAULT_HEIGHT, BACKWARD_HOOKED_ACTUATOR_ANGLE),
            JoystickAdjustClimber(),
            SetClimber(HANG_HEIGHT, LET_GO_HEIGHT, BACKWARD_UNHOOKED_ACTUATOR_ANGLE),
            SetClimber(DEFAULT_HEIGHT, HANG_HEIGHT, DEFAULT_ACTUATOR_ANGLE)
        )
    }
}
