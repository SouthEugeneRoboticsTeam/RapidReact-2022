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
        controlPreferences.intake.whileHeld(intakeBalls, false)
        controlPreferences.outtake.whileHeld(outtakeBalls)
        controlPreferences.shoot.whileHeld(shootBalls, false)
        controlPreferences.rev.whileHeld(revShooter)

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

    val xAxis
        get() = controlPreferences.joystickX()

    val yAxis
        get() = controlPreferences.joystickY()
}