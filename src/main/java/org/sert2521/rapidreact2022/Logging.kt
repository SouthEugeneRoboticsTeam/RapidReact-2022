package org.sert2521.rapidreact2022

import badlog.lib.BadLog
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter
import java.lang.Exception
import java.lang.System.nanoTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logging {
    private var log: BadLog? = null
    private var startTime = 0L

    init {
        for(logPath in LOG_PATHS) {
            try {
                log = BadLog.init("$logPath${LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_PATTERN))}.bag")

                //Maybe log joystick
                initMetaInfoLogging()
                initRobotLogging()

                log?.finishInitialization()
                break
            } catch(e: Exception) {
                log = null
                println(e)
            }
        }
    }

    private fun initMetaInfoLogging() {
        startTime = nanoTime()
        BadLog.createTopic("Info/Time Nano", "ns", { (nanoTime() - startTime).toDouble() })
        BadLog.createTopic("Info/Time", "s", { (nanoTime() - startTime).toDouble() / 1.0e9 }, "xaxis")
        BadLog.createTopic("Info/Match Time", "s", { DriverStation.getMatchTime() })

        BadLog.createTopic("Info/Match Color", BadLog.UNITLESS, { when(DriverStation.getAlliance()) { DriverStation.Alliance.Blue -> 1.0; DriverStation.Alliance.Invalid -> 0.0; DriverStation.Alliance.Red -> -1.0; null -> 0.0 } })
        BadLog.createTopic("Info/Match Number", BadLog.UNITLESS, { DriverStation.getMatchNumber().toDouble() })

        BadLog.createTopic("Info/Mode", BadLog.UNITLESS, { if(Robot.isEnabled) { if(Robot.isAutonomous) { 2.0 } else { 1.0 } } else { 0.0 } })
    }

    private fun initRobotLogging() {
        BadLog.createTopic("Shooter/Shooter Speed", "rpm", { Shooter.wheelSpeed })

        BadLog.createTopic("Drivetrain/Left Speed", "m/s", { Drivetrain.leftVelocity })
        BadLog.createTopic("Drivetrain/Right Speed", "m/s", { Drivetrain.rightVelocity })

        BadLog.createTopic("Drivetrain/Left Distance", "m", { Drivetrain.leftDistanceTraveled })
        BadLog.createTopic("Drivetrain/Right Distance", "m", { Drivetrain.rightDistanceTraveled })

        BadLog.createTopic("Drivetrain/Pose X", "m", { Drivetrain.pose.x })
        BadLog.createTopic("Drivetrain/Pose Y", "m", { Drivetrain.pose.y })
        BadLog.createTopic("Drivetrain/Pose Angle", "deg", { Drivetrain.pose.rotation.degrees })

        BadLog.createTopic("Intake/Full", BadLog.UNITLESS, { if(Intake.indexerFull) { 1.0 } else { 0.0 } })
    }

    fun update() {
        SmartDashboard.putNumber("Robot/Shooter Speed", Shooter.wheelSpeed)

        SmartDashboard.putNumber("Robot/Drivetrain Left Speed", Drivetrain.leftVelocity)
        SmartDashboard.putNumber("Robot/Drivetrain Right Speed", Drivetrain.rightVelocity)

        SmartDashboard.putNumber("Robot/Drivetrain Left Distance", Drivetrain.leftDistanceTraveled)
        SmartDashboard.putNumber("Robot/Drivetrain Right Distance", Drivetrain.rightDistanceTraveled)

        SmartDashboard.putNumber("Robot/Climber Static Height", Climber.staticHeight)
        SmartDashboard.putNumber("Robot/Climber Variable Height", Climber.variableHeight)
        SmartDashboard.putNumber("Robot/Climber Variable Angle", Climber.variableAngle)

        SmartDashboard.putBoolean("Robot/Climber Static Limit", Climber.isAtBottomStatic())
        SmartDashboard.putBoolean("Robot/Climber Variable Limit", Climber.isAtBottomVariable())

        SmartDashboard.putString("Robot/Climber Static Locked", Climber.isStaticLocked().toString())
        SmartDashboard.putString("Robot/Climber Variable Locked", Climber.isVariableLocked().toString())

        SmartDashboard.putBoolean("Robot/Intake Full", Intake.indexerFull)

        SmartDashboard.putNumber("Robot/Pose X", Drivetrain.pose.x)
        SmartDashboard.putNumber("Robot/Pose Y", Drivetrain.pose.y)

        SmartDashboard.putNumber("Robot/Gyro Angle ", Drivetrain.pose.rotation.degrees)

        //fix not catching errors
        try {
            log?.updateTopics()
            log?.log()
        } catch(e: Exception) {
            log = null
            println(e)
        }
    }
}