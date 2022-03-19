package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber

//Add AI confirm climber next
class Climb : SequentialCommandGroup() {
    init {
        addCommands(
                InstantCommand( { Climber.climbing = true } ),
                SetClimber(REACH_MID, ABOVE_HIGH, PAST_HIGH_ANGLE) { OI.getClimbNext() },
                InstantCommand( { Climber.inAir = true } ),
                InstantCommand( { Climber.loadBearingArm = Arms.STATIC } ),
                SetClimber(HANG_HEIGHT, ABOVE_HIGH, PAST_HIGH_ANGLE),
                SetClimber(HANG_HEIGHT, ABOVE_HIGH, PAST_HIGH_ANGLE),
                ClimberHitBar(HANG_HEIGHT, ABOVE_HIGH, Directions.FORWARD) { OI.getClimbNext() },
                InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
                ClimberHitBar(HANG_HEIGHT, HIT_HIGH, Directions.FORWARD),
                InstantCommand( { Climber.loadBearingArm = Arms.VARIABLE } ),
                SetClimber(HANG_HEIGHT, PULL_IN_HIGH, null),
                SetClimber(LET_GO_MID, PULL_IN_HIGH, null, maxAcceleration = CLIMBER_LET_GO_ACCELERATION),
                SetClimber(GO_UNDER, PULL_IN_HIGH, GO_UNDER_ANGLE),
                SetClimber(GO_UNDER, PULL_IN_HIGH, PAST_CURRENT_ANGLE),
                SetClimber(ABOVE_CURRENT, PULL_IN_HIGH, PAST_CURRENT_ANGLE),
                ClimberHitBar(ABOVE_CURRENT, PULL_IN_HIGH, Directions.BACKWARD) { OI.getClimbNext() },
                InstantCommand( { Climber.loadBearingArm = Arms.STATIC } ),
                SetClimber(HANG_HEIGHT, GO_UNDER_TRAVERSAL, null),
                SetClimber(HANG_HEIGHT, GO_UNDER_TRAVERSAL, REACH_TRAVERSAL_ANGLE),
                SetClimber(HANG_HEIGHT, REACH_TRAVERSAL, REACH_TRAVERSAL_ANGLE),
                ClimberHitBar(HANG_HEIGHT, REACH_TRAVERSAL, Directions.FORWARD) { OI.getClimbNext() },
                InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
                ClimberHitBar(HANG_HEIGHT, HIT_TRAVERSAL, Directions.FORWARD),
                InstantCommand( { Climber.loadBearingArm = Arms.VARIABLE } ),
                SetClimber(HANG_HEIGHT, PULL_IN_TRAVERSAL, null),
                SetClimber(LET_GO_HIGH, PULL_IN_TRAVERSAL, null, maxAcceleration = CLIMBER_LET_GO_ACCELERATION),
                SetClimber(LET_GO_HIGH, PULL_IN_TRAVERSAL, LET_GO_ANGLE),
                SetClimber(END, PULL_IN_TRAVERSAL, LET_GO_ANGLE),
                SetClimber(END, PULL_IN_TRAVERSAL, END_ANGLE),
                InstantCommand( { Climber.lock() } )
        )
    }

    override fun isFinished(): Boolean {
        if(OI.getClimbLocked()) {
            Climber.lock()
            Climber.stop()
            return true
        }

        if(OI.getClimbEnd() && !Climber.inAir) {
            Climber.reset()
            return true
        }

        return false
    }
}
