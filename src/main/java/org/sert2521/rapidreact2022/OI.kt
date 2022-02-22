package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import org.sert2521.rapidreact2022.commands.*
import org.sert2521.rapidreact2022.subsytems.Climber

object OI {
    private val intakeBalls = IntakeBalls()
    private val outtakeBalls = OuttakeBalls()
    private val shootBalls = ShootBalls()
    private val revShooter = RevShooter()

    private val climbTraversal = ClimbTraversal()
    private val climbMid = ClimbMid()

    private var slowMode = false

    private var indexerOverride = false

    init {
        controlPreferences.intake.whileHeld(intakeBalls, false)
        controlPreferences.outtake.whileHeld(outtakeBalls)
        controlPreferences.shoot.whileHeld(shootBalls, false)
        controlPreferences.rev.whileHeld(revShooter)

        controlPreferences.startClimbTraversal.toggleWhenPressed(climbTraversal)
        controlPreferences.startClimbMid.toggleWhenPressed(climbMid)

        controlPreferences.slowMode.whenPressed(InstantCommand( { slowMode = !slowMode } ))
        controlPreferences.overrideIndexer.whenPressed(InstantCommand( { indexerOverride = !indexerOverride } ))
    }

    fun onEnable() {
        slowMode = false

        indexerOverride = false
    }

    fun getIndexerOverride(): Boolean {
        return indexerOverride
    }

    fun getRunIndexer(): Boolean {
        return controlPreferences.runIndexer.get()
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

    fun getClimbStatic(): Double {
        return controlPreferences.secondaryController.x * 0.4
    }

    fun getClimbVariable(): Double {
        return controlPreferences.secondaryController.y * 0.4
    }

    fun getClimbActuate(): Double {
        return controlPreferences.secondaryController.z * 0.4
    }

    val xAxis
        get() = controlPreferences.joystickX()

    val yAxis
        get() = controlPreferences.joystickY()
}