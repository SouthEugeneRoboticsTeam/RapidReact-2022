package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber

class ClimbUp : SequentialCommandGroup() {
    init {
        addCommands(
            InstantCommand( { Climber.climbing = true } ),
            InstantCommand( { OI.climbing = true } ),
            SetClimberPID(MID_HEIGHT, MID_HEIGHT, DEFAULT_ANGLE) { OI.getClimbNext() },
            SetClimberLinear(HIT_MID_HEIGHT, HIT_MID_HEIGHT, HIT_MID_ANGLE, staticSpeed = LOW_SPEED, variableSpeed = LOW_SPEED) { OI.getClimbNext() },
            InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
            SetClimberPID(HANG_HEIGHT, HANG_HEIGHT, TOP_ANGLE_HANG),
        )
    }
}
