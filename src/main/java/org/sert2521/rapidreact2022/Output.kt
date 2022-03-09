package org.sert2521.rapidreact2022

import badlog.lib.BadLog
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter
import java.io.File

object Output {
    private val log: BadLog?

    init {
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