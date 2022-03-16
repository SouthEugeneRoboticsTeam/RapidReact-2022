package org.sert2521.rapidreact2022

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import org.sert2521.rapidreact2022.commands.*
import org.sert2521.rapidreact2022.subsytems.Climber
import java.util.function.Supplier
import kotlin.math.abs

object Input {
    private val intakeBalls = IntakeBalls()
    private val outtakeBalls = OuttakeBalls()
    private val shootBalls = ShootBalls()
    private val revShooter = RevShooter()

    private val climbTraversal = ClimbTraversal()

    private var slowMode = false

    private var indexerOverride = false

    private var climbNext = false

    private val autoChooser = SendableChooser<Supplier<Command>>()

    private var camera = 0

    init {
        controlPreferences.intake.whileHeld(intakeBalls, false)
        controlPreferences.outtake.whileHeld(outtakeBalls)
        controlPreferences.shoot.whileHeld(shootBalls, false)
        controlPreferences.rev.whileHeld(revShooter)

        controlPreferences.startClimbTraversal.whenPressed(climbTraversal, false)

        controlPreferences.slowMode.whenPressed(InstantCommand( { slowMode = !slowMode } ))
        controlPreferences.overrideIndexer.whenPressed(InstantCommand( { indexerOverride = !indexerOverride } ))

        controlPreferences.switchCameras.whenPressed(InstantCommand( { NetworkTableInstance.getDefault().getEntry(CAMERA_PATH).setString(CAMERAS[camera]); camera++; camera %= CAMERAS.size } ))

        autoChooser.setDefaultOption("Nothing", null)
        autoChooser.addOption("Drive Forward") { DriveForward() }
        autoChooser.addOption("Shoot Single Right") { ShootSingleRight() }
        autoChooser.addOption("Shoot Single Left") { ShootSingleLeft() }
        autoChooser.addOption("Shoot Double Right") { ShootDoubleRight() }
        autoChooser.addOption("Shoot Double Left") { ShootDoubleLeft() }
        SmartDashboard.putData(autoChooser)

        SmartDashboard.putNumber("Auto Delay", 0.0)
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
        return slowMode || Climber.climbing
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
        return controlPreferences.primaryController.rightY * abs(controlPreferences.primaryController.rightY)
    }

    fun getClimbVariable(): Double {
        return controlPreferences.primaryController.leftY * abs(controlPreferences.primaryController.leftY)
    }

    fun getClimbActuate(): Double {
        return controlPreferences.secondaryController.y
    }

    val xAxis
        get() = controlPreferences.joystickX()

    val yAxis
        get() = controlPreferences.joystickY()

    fun getAuto(): Command? {
        if(autoChooser.selected == null) {
            return null
        }

        return WaitCommand(SmartDashboard.getNumber("Robot/Auto Delay", 0.0)).andThen(autoChooser.selected.get())
    }
}