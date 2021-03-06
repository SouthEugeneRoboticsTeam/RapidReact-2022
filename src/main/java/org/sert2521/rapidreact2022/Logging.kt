package org.sert2521.rapidreact2022

import badlog.lib.BadLog
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.sert2521.rapidreact2022.subsytems.*
import java.lang.Exception
import java.lang.System.nanoTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.Path
import kotlin.io.path.exists

object Logging {
    private var log: BadLog? = null
    private var startTime = 0L
    private var path = ""

    private fun initMetaInfoLogging() {
        startTime = nanoTime()
        BadLog.createTopic("Info/Time Nano", "ns", { (nanoTime() - startTime).toDouble() }, "xaxis")
        BadLog.createTopic("Info/Match Time", "s", { DriverStation.getMatchTime() })

        BadLog.createTopic("Info/Match Color", BadLog.UNITLESS, { when(DriverStation.getAlliance()) { DriverStation.Alliance.Blue -> 1.0; DriverStation.Alliance.Invalid -> 0.0; DriverStation.Alliance.Red -> -1.0; null -> 0.0 } })
    }

    private fun boolToDouble(bool: Boolean): Double {
        return if(bool) {
            1.0
        } else {
            0.0
        }
    }

    private fun initJoystickLogging() {
        BadLog.createTopic("Input/X", BadLog.UNITLESS, controlPreferences.joystickX)
        BadLog.createTopic("Input/Y", BadLog.UNITLESS, controlPreferences.joystickY)
        BadLog.createTopic("Input/Speed Increase", BadLog.UNITLESS, controlPreferences.speedIncrease)

        BadLog.createTopic("Input/Intake", BadLog.UNITLESS, { boolToDouble(controlPreferences.intake.get()) })
        BadLog.createTopic("Input/Outtake", BadLog.UNITLESS, { boolToDouble(controlPreferences.outtake.get()) })
        BadLog.createTopic("Input/Override Indexer", BadLog.UNITLESS, { boolToDouble(controlPreferences.overrideIndexer.get()) })
        BadLog.createTopic("Input/Run Indexer", BadLog.UNITLESS, { boolToDouble(controlPreferences.runIndexer.get()) })

        BadLog.createTopic("Input/Climb Next", BadLog.UNITLESS, { boolToDouble(controlPreferences.climbNext.get()) })
        BadLog.createTopic("Input/End Climb", BadLog.UNITLESS, { boolToDouble(controlPreferences.endClimb.get()) })
        BadLog.createTopic("Input/Climb", BadLog.UNITLESS, { boolToDouble(controlPreferences.climb.get()) })
        BadLog.createTopic("Input/Lock One", BadLog.UNITLESS, { boolToDouble(controlPreferences.lockOne.get()) })
        BadLog.createTopic("Input/Lock Two", BadLog.UNITLESS, { boolToDouble(controlPreferences.lockTwo.get()) })

        BadLog.createTopic("Input/Rev", BadLog.UNITLESS, { boolToDouble(controlPreferences.rev.get()) })
        BadLog.createTopic("Input/Shoot", BadLog.UNITLESS, { boolToDouble(controlPreferences.shoot.get()) })
        BadLog.createTopic("Input/Force Shoot", BadLog.UNITLESS, { boolToDouble(controlPreferences.forceShoot.get()) })

        BadLog.createTopic("Input/Slow Mode", BadLog.UNITLESS, { boolToDouble(controlPreferences.slowMode.get()) })
    }

    private fun initRobotLogging() {
        BadLog.createTopic("Shooter/Shooter Speed", "rpm", { Shooter.wheelSpeedFront })
        BadLog.createTopic("Shooter/Shooter Back Speed", "rpm", { Shooter.wheelSpeedBack })

        BadLog.createTopic("Shooter/Shooter Speed Average", "rpm", { Shooter.getAverageFrontSpeed() })
        BadLog.createTopic("Shooter/Shooter Back Speed Average", "rpm", { Shooter.getAverageSpeedBack() })

        BadLog.createTopic("Drivetrain/Left Speed", "m/s", { Drivetrain.leftVelocity })
        BadLog.createTopic("Drivetrain/Right Speed", "m/s", { Drivetrain.rightVelocity })

        BadLog.createTopic("Drivetrain/Left Distance", "m", { Drivetrain.leftDistanceTraveled })
        BadLog.createTopic("Drivetrain/Right Distance", "m", { Drivetrain.rightDistanceTraveled })

        BadLog.createTopic("Drivetrain/Pose X", "m", { Drivetrain.pose.x })
        BadLog.createTopic("Drivetrain/Pose Y", "m", { Drivetrain.pose.y })
        BadLog.createTopic("Drivetrain/Pose Angle", "deg", { Drivetrain.pose.rotation.degrees })

        BadLog.createTopic("Intake/Full", BadLog.UNITLESS, { if(Intake.indexerFull) { 1.0 } else { 0.0 } })

        BadLog.createTopic("Climber/Static Height", "m", { Climber.staticHeight })
        BadLog.createTopic("Climber/Variable Height", "m", { Climber.variableHeight })
        BadLog.createTopic("Climber/Variable Angle Arm", BadLog.UNITLESS, { Climber.variableAngleArm })
        BadLog.createTopic("Climber/Variable Angle Motor", BadLog.UNITLESS, { Climber.variableAngleMotor })

        BadLog.createTopic("Climber/Static Limit", BadLog.UNITLESS, { boolToDouble(Climber.isAtBottomStatic()) })
        BadLog.createTopic("Climber/Variable Limit", BadLog.UNITLESS, { boolToDouble(Climber.isAtBottomVariable()) })

        BadLog.createTopic("Climber/Static Locked", BadLog.UNITLESS, { when(Climber.isStaticLocked()) { LockStates.LOCKED -> 1.0; LockStates.NEITHER -> 0.0; LockStates.UNLOCKED -> -1.0 } })
        BadLog.createTopic("Climber/Variable Locked", BadLog.UNITLESS, { when(Climber.isVariableLocked()) { LockStates.LOCKED -> 1.0; LockStates.NEITHER -> 0.0; LockStates.UNLOCKED -> -1.0 } })
    }

    fun onEnable() {
        for(logPath in LOG_PATHS) {
            try {
                path = if (DriverStation.isFMSAttached()) {
                    "${logPath}Match ${DriverStation.getMatchNumber()}, ${if(Robot.isEnabled) { if(Robot.isAutonomous) { "Mode Autonomous, " } else { "Mode Teleop, " } } else { "" }}Time ${LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_PATTERN))}.bag"
                } else {
                    "${logPath}Testing, ${if(Robot.isEnabled) { if(Robot.isAutonomous) { "Mode Autonomous, " } else { "Mode Teleop, " } } else { "" }}Time ${LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_PATTERN))}.bag"
                }

                log = BadLog.init(path)

                initMetaInfoLogging()
                initJoystickLogging()
                initRobotLogging()

                log?.finishInitialization()

                break
            } catch(e: Exception) {
                log = null
                println(e)
            }
        }
    }

    fun onDisable() {
        log = null
    }

    fun update() {
        SmartDashboard.putNumber("Robot/Shooter Speed", Shooter.wheelSpeedFront)
        SmartDashboard.putNumber("Robot/Shooter Back Speed", Shooter.wheelSpeedBack)

        SmartDashboard.putNumber("Robot/Shooter Speed Average", Shooter.getAverageFrontSpeed())
        SmartDashboard.putNumber("Robot/Shooter Back Speed Average", Shooter.getAverageSpeedBack())

        SmartDashboard.putNumber("Robot/Drivetrain Left Speed", Drivetrain.leftVelocity)
        SmartDashboard.putNumber("Robot/Drivetrain Right Speed", Drivetrain.rightVelocity)

        SmartDashboard.putNumber("Robot/Drivetrain Left Distance", Drivetrain.leftDistanceTraveled)
        SmartDashboard.putNumber("Robot/Drivetrain Right Distance", Drivetrain.rightDistanceTraveled)

        SmartDashboard.putNumber("Robot/Climber Static Height", Climber.staticHeight)
        SmartDashboard.putNumber("Robot/Climber Variable Height", Climber.variableHeight)
        SmartDashboard.putNumber("Robot/Climber Variable Angle Arm", Climber.variableAngleArm)
        SmartDashboard.putNumber("Robot/Climber Variable Angle Motor", Climber.variableAngleMotor)

        SmartDashboard.putBoolean("Robot/Climber Static Limit", Climber.isAtBottomStatic())
        SmartDashboard.putBoolean("Robot/Climber Variable Limit", Climber.isAtBottomVariable())

        SmartDashboard.putString("Robot/Climber Static Locked", Climber.isStaticLocked().toString())
        SmartDashboard.putString("Robot/Climber Variable Locked", Climber.isVariableLocked().toString())

        SmartDashboard.putBoolean("Robot/Intake Full", Intake.indexerFull)

        SmartDashboard.putNumber("Robot/Pose X", Drivetrain.pose.x)
        SmartDashboard.putNumber("Robot/Pose Y", Drivetrain.pose.y)

        SmartDashboard.putNumber("Robot/Gyro Angle ", Drivetrain.pose.rotation.degrees)

        //Fix
        if(Path(path).exists()) {
            log?.updateTopics()
            log?.log()
        } else {
            log = null
        }
    }
}