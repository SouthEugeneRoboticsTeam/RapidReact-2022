package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.subsytems.Arms
import org.sert2521.rapidreact2022.subsytems.Climber

class Climb : SequentialCommandGroup() {
    init {
        //Make easier to undo climb
        addCommands(
            InstantCommand( { Climber.climbing = true } ),
            SetClimber(REACH_MID, ABOVE_HIGH, PAST_HIGH_ANGLE) { Input.getClimbNext() },
            InstantCommand( { Climber.loadBearingArm = Arms.STATIC } ),
            SetClimber(HANG_HEIGHT, ABOVE_HIGH, PAST_HIGH_ANGLE),
            ClimberHitBar(HANG_HEIGHT, ABOVE_HIGH, Directions.FORWARD) { Input.getClimbNext() },
            //Make go high not down
            InstantCommand( { Climber.committed = true } ),
            InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
            ClimberHitBar(HANG_HEIGHT, HIT_HIGH, Directions.FORWARD),
            InstantCommand( { Climber.loadBearingArm = Arms.VARIABLE } ),
            SetClimber(LET_GO_MID, PULL_IN_HIGH, null, maxStatic = CLIMBER_UNHOOK_SPEED),
            SetClimber(GO_UNDER_HIGH, PULL_IN_HIGH, PAST_CURRENT_ANGLE, staticTolerance = CLIMBER_LOOSE_TOLERANCE, angleTolerance = CLIMBER_LOOSE_TOLERANCE_ANGLE),
            SetClimber(ABOVE_CURRENT, PULL_IN_HIGH, PAST_CURRENT_ANGLE, angleTolerance = CLIMBER_LOOSE_TOLERANCE_ANGLE),
            ClimberHitBar(ABOVE_CURRENT, PULL_IN_HIGH, Directions.BACKWARD, minAngleMotor = MIN_ANGLE_HIT_CURRENT) { Input.getClimbNext() },
            InstantCommand( { Climber.loadBearingArm = Arms.STATIC } ),
            SetClimber(PULL_IN_HIGH, REACH_TRAVERSAL, null),
            SetClimber(HANG_HEIGHT, REACH_TRAVERSAL, REACH_TRAVERSAL_ANGLE),
            ClimberHitBar(HANG_HEIGHT, REACH_TRAVERSAL, Directions.BACKWARD) { Input.getClimbNext() },
            InstantCommand( { Climber.loadBearingArm = Arms.BOTH } ),
            ClimberHitBar(HANG_HEIGHT, HIT_TRAVERSAL, Directions.BACKWARD),
            InstantCommand( { Climber.loadBearingArm = Arms.VARIABLE } ),
            SetClimber(HANG_HEIGHT, PULL_IN_TRAVERSAL, null),
            SetClimber(LET_GO_HIGH, PULL_IN_TRAVERSAL, null, maxStatic = CLIMBER_UNHOOK_SPEED),
            SetClimber(GO_UNDER_TRAVERSAL, PULL_IN_TRAVERSAL, END_ANGLE, staticTolerance = CLIMBER_LOOSE_TOLERANCE, angleTolerance = CLIMBER_LOOSE_TOLERANCE_ANGLE),
            SetClimber(END, PULL_IN_TRAVERSAL, END_ANGLE),
            InstantCommand( { Climber.lock() } ),
            SetClimber(END, PULL_IN_TRAVERSAL, END_ANGLE) { false }.withTimeout(150.0)
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