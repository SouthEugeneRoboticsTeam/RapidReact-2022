package org.sert2521.rapidreact2022

import org.sert2521.rapidreact2022.commands.*

object OI {
    private val intakeBalls = IntakeBalls()
    private val outtakeBalls = OuttakeBalls()
    private val shootBalls = ShootBalls()

    private val climbTraversal = ClimbTraversal()
    private val climbMid = ClimbMid()

    init {
        controlPreferences.intake.whileHeld(intakeBalls)
        controlPreferences.outtake.whileHeld(outtakeBalls)
        controlPreferences.shoot.whileHeld(shootBalls)

        controlPreferences.startClimbTraversal.toggleWhenPressed(climbTraversal)
        controlPreferences.startClimbMid.toggleWhenPressed(climbMid)
    }

    fun getOverrideIndexer(): Boolean {
        return controlPreferences.overrideIndexer.get()
    }

    fun getSlowMode(): Boolean {
        return controlPreferences.slowMode.get()
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