package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*

class ClimbTransversal : SequentialCommandGroup() {
    init {
        addCommands(
            ClimbMid(),
            SetClimber(HANG_HEIGHT, NEXT_BAR_HEIGHT, FORWARD_ACTUATOR_ANGLE),
            JoystickAdjustClimber(),
            SetClimber(LET_GO_HEIGHT, HANG_HEIGHT, FORWARD_ACTUATOR_ANGLE),
            SetClimber(0.0, HANG_HEIGHT, DEFAULT_ACTUATOR_ANGLE),
            SetClimber(NEXT_BAR_HEIGHT, 0.0, BACKWARD_ACTUATOR_ANGLE),
            JoystickAdjustClimber(),
            SetClimber(HANG_HEIGHT, LET_GO_HEIGHT, BACKWARD_ACTUATOR_ANGLE),
            SetClimber(0.0, HANG_HEIGHT, 0.0),
        )
    }
}
