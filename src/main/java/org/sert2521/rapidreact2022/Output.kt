package org.sert2521.rapidreact2022

import badlog.lib.BadLog
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.WaitCommand
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.sert2521.rapidreact2022.commands.*
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter
import java.io.File
import java.util.*
import java.util.function.Supplier

object Output {
    private val autoChooser = SendableChooser<Command?>()
    private val shuffleboardOutputs = mutableMapOf<String, Supplier<Double>>()
    private val log: BadLog?

    init {
        autoChooser.setDefaultOption("Nothing", null)
        autoChooser.addOption("Drive Forward", DriveForward())
        autoChooser.addOption("Shoot Single Right", ShootSingleRight())
        autoChooser.addOption("Shoot Single Left", ShootSingleLeft())
        autoChooser.addOption("Shoot Double Right", ShootDoubleRight())
        autoChooser.addOption("Shoot Double Left", ShootDoubleLeft())
        SmartDashboard.putData(autoChooser)

        SmartDashboard.putNumber("Auto Delay", 0.0)

        val path = File(LOG_PATH)
        if (path.exists() && path.canWrite()) {
            log = BadLog.init(LOG_PATH + "${System.currentTimeMillis()}.bag")

            BadLog.createTopic("Shooter Speed", "rpm", { Shooter.wheelSpeed })

            BadLog.createTopic("Drivetrain Left Speed", "m/s", { Drivetrain.leftVelocity })
            BadLog.createTopic("Drivetrain Right Speed", "m/s", { Drivetrain.rightVelocity })

            BadLog.createTopic("Drivetrain Left Distance", "m", { Drivetrain.leftDistanceTraveled })
            BadLog.createTopic("Drivetrain Right Distance", "m", { Drivetrain.rightDistanceTraveled })

            BadLog.createTopic("Pose X", "m", { Drivetrain.pose.x })
            BadLog.createTopic("Pose Y", "m", { Drivetrain.pose.y })
            BadLog.createTopic("Pose Angle", "°", { Drivetrain.pose.rotation.degrees })

            BadLog.createTopic("Intake Full", BadLog.UNITLESS, { if(Intake.indexerFull) { 1.0 } else { 0.0 } })
        } else {
            log = null
        }

        log?.finishInitialization()
    }

    fun getAuto(): Command? {
        if(autoChooser.selected == null) {
            return null
        }

        return WaitCommand(SmartDashboard.getNumber("Auto Delay", 0.0)).andThen(autoChooser.selected)
    }

    fun update() {
        SmartDashboard.putNumber("Shooter Speed", Shooter.wheelSpeed)

        SmartDashboard.putNumber("Drivetrain Left Speed", Drivetrain.leftVelocity)
        SmartDashboard.putNumber("Drivetrain Right Speed", Drivetrain.rightVelocity)

        SmartDashboard.putNumber("Drivetrain Left Distance", Drivetrain.leftDistanceTraveled)
        SmartDashboard.putNumber("Drivetrain Right Distance", Drivetrain.rightDistanceTraveled)

        SmartDashboard.putNumber("Climber Static Height", Climber.staticHeight)
        SmartDashboard.putNumber("Climber Variable Height", Climber.variableHeight)
        SmartDashboard.putNumber("Climber Variable Angle", Climber.variableAngle)

        SmartDashboard.putBoolean("Climber Static Limit", Climber.isAtBottomStatic())
        SmartDashboard.putBoolean("Climber Variable Limit", Climber.isAtBottomVariable())

        SmartDashboard.putString("Climber Static Locked", Climber.isStaticLocked().toString())
        SmartDashboard.putString("Climber Variable Locked", Climber.isVariableLocked().toString())

        SmartDashboard.putBoolean("Intake Full", Intake.indexerFull)

        SmartDashboard.putNumber("Pose X", Drivetrain.pose.x)
        SmartDashboard.putNumber("Pose Y", Drivetrain.pose.y)

        SmartDashboard.putNumber("Gyro Angle ", Drivetrain.pose.rotation.degrees)

        log?.updateTopics()
        log?.log()
    }
}