package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj2.command.InstantCommand
import org.sert2521.rapidreact2022.commands.*
import kotlin.math.abs

object OI {
    private val intakeBalls = IntakeBalls()
    private val outtakeBalls = OuttakeBalls()
    private val shootBalls = ShootBalls()
    private val revShooter = RevShooter()

    private val climbTraversal = ClimbTraversal()
    private val climbMid = ClimbMid()

    private var slowMode = false
    var climbing = false

    private var indexerOverride = false

    private var climbNext = false

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
        climbing = false

        indexerOverride = false
    }

    fun getIndexerOverride(): Boolean {
        return indexerOverride
    }

    fun getRunIndexer(): Boolean {
        return controlPreferences.runIndexer.get()
    }

    fun getSlowMode(): Boolean {
        return slowMode || climbing
    }

    fun isFast(): Boolean {
        return controlPreferences.fastMode.get()
    }

    fun getClimbNext(): Boolean {
        val currPress = controlPreferences.climbNext.get()
        return if (!climbNext) {
            climbNext = currPress
            currPress
        } else {
            climbNext = currPress
            false
        }
    }

    fun getClimbStatic(): Double {
        return controlPreferences.primaryController.rightY * -0.8 * abs(controlPreferences.primaryController.rightY)
    }

    fun getClimbVariable(): Double {
        return controlPreferences.primaryController.leftY * -0.8 * abs(controlPreferences.primaryController.leftY)
    }

    fun getClimbActuate(): Double {
        return controlPreferences.secondaryController.y * -0.8
    }

    val xAxis
        get() = controlPreferences.joystickX()

    val yAxis
        get() = controlPreferences.joystickY()
}