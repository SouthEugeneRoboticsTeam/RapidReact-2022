package org.sert2521.rapidreact2022

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import org.sert2521.rapidreact2022.commands.*
import org.sert2521.rapidreact2022.subsytems.Climber

object Input {
    private val intakeBalls = IntakeBalls()
    private val outtakeBalls = OuttakeBalls()
    private val shootBalls = ShootBalls()
    private val revShooter = RevShooter()

    private val climb = Climb()
    private val climbMid = ClimbMid()

    private var slowMode = false

    private var indexerOverride = false

    private var climbNext = false
    private var climbLocked = false

    private val autoChooser = SendableChooser<Command>()

    private var camera = 0

    init {
        NetworkTableInstance.getDefault().getEntry(CAMERA_PATH).setString(CAMERAS[camera])

        controlPreferences.intake.whileHeld(intakeBalls, false)
        controlPreferences.outtake.whileHeld(outtakeBalls)
        controlPreferences.shoot.whileHeld(shootBalls, false)
        //controlPreferences.rev.whileHeld(revShooter)

        controlPreferences.climb.whenPressed( InstantCommand( { if (!isNormalClimb()) { climbMid.schedule(false) } else { climb.schedule(false) } } ))
        controlPreferences.lockOne.and(controlPreferences.lockTwo).whenActive(InstantCommand( { climbLocked = true } ))

        controlPreferences.slowMode.whenPressed(InstantCommand( { slowMode = !slowMode } ))
        //controlPreferences.overrideIndexer.whenPressed(InstantCommand( { indexerOverride = !indexerOverride } ))

        controlPreferences.switchCameras.whenPressed(InstantCommand( { NetworkTableInstance.getDefault().getEntry(CAMERA_PATH).setString(CAMERAS[camera]); camera++; camera %= CAMERAS.size } ))
    }

    fun onInit() {
        autoChooser.setDefaultOption("Nothing", null)
        autoChooser.addOption("Shoot No Move", ShootBalls().withTimeout(SHOOT_TIME))
        autoChooser.addOption("Drive Forward", DriveForward())
        autoChooser.addOption("Shoot Single Right", ShootSingleRight())
        autoChooser.addOption("Shoot Single Left", ShootSingleLeft())
        autoChooser.addOption("Shoot Double Right", ShootDoubleRight())
        autoChooser.addOption("Shoot Double Left", ShootDoubleLeft())
        autoChooser.addOption("Shoot Triple Left", ShootTripleLeft())
        SmartDashboard.putData(autoChooser)

        SmartDashboard.putNumber("Auto Delay", 0.0)
        SmartDashboard.putBoolean("Normal Speed", false)
        SmartDashboard.putBoolean("Normal Shoot", false)
        SmartDashboard.putBoolean("Normal Climb", false)
        SmartDashboard.putNumber("Shooter Power", 50.0)
    }

    fun onEnable() {
        slowMode = false
        indexerOverride = false
        climbLocked = false
    }

    fun getIndexerOverride(): Boolean {
        return indexerOverride
    }

    fun getRunIndexer(): Boolean {
        return controlPreferences.runIndexer.get()
    }

    fun getSlowMode(): Boolean {
        return slowMode || Climber.climbing// || !SmartDashboard.getBoolean("Normal Speed", false)
    }

    fun isNormalClimb() = SmartDashboard.getBoolean("Normal Climb", false)

    fun isNormalShoot() = SmartDashboard.getBoolean("Normal Shoot", false)

    fun forceShoot(): Boolean {
        return controlPreferences.forceShoot .get()
    }

    fun getShootPower(): Double {
        return ((SmartDashboard.getNumber("Shooter Power", 0.0).coerceIn(0.0, 100.0) / 100.0) * 0.5) + 0.85
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

    fun getClimbEnd(): Boolean {
        return controlPreferences.endClimb.get()
    }

    fun getClimbLocked(): Boolean {
        return climbLocked
    }

    val xAxis
        get() = controlPreferences.joystickX()

    val yAxis
        get() = controlPreferences.joystickY()

    val speedIncrease
        get() = controlPreferences.speedIncrease

    fun getSelectedAuto(): Command? {
        return autoChooser.selected
    }

    fun getAuto(): Command? {
        if(autoChooser.selected == null) {
            return null
        }

        return WaitCommand(SmartDashboard.getNumber("Auto Delay", 0.0)).andThen(InstantCommand( { autoChooser.selected.schedule() } ))
    }
}