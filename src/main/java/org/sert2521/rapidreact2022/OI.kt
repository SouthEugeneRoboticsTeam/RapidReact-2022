package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj2.command.InstantCommand
import org.sert2521.rapidreact2022.commands.*

object OI {
    private val intakeBalls = IntakeBalls()
    private val outtakeBalls = OuttakeBalls()
    private val shootBalls = ShootBalls()
    private val revShooter = RevShooter()

    private val climbTraversal = ClimbTraversal()
    private val climbMid = ClimbMid()

    private var slowMode = false

    init {
        //fix holding two at same time probably causing problems
        controlPreferences.outtake.whileHeld(outtakeBalls)
        controlPreferences.intake.whileHeld(intakeBalls)
        controlPreferences.rev.whileHeld(revShooter)
        controlPreferences.shoot.whileHeld(shootBalls)

        controlPreferences.startClimbTraversal.toggleWhenPressed(climbTraversal)
        controlPreferences.startClimbMid.toggleWhenPressed(climbMid)

        controlPreferences.slowMode.toggleWhenPressed(InstantCommand( { slowMode = !slowMode } ))
    }

    fun onTeleop() {
        slowMode = false
    }

    fun getOverrideIndexer(): Boolean {
        return controlPreferences.overrideIndexer.get()
    }

    fun getSlowMode(): Boolean {
        return slowMode
    }

    fun isFast(): Boolean {
        return controlPreferences.fastMode.get()
    }

    fun getClimbNext(): Boolean {
        return controlPreferences.climbNext.get()
    }

    private fun deadband(value: Double, range: Double): Double {
        if(value < range && value > -range) {
            return 0.0
        }

        return value
    }

    val xAxis
        get() = deadband(controlPreferences.joystickX(), DEADBAND)

    val yAxis
        get() = deadband(controlPreferences.joystickY(), DEADBAND)
}