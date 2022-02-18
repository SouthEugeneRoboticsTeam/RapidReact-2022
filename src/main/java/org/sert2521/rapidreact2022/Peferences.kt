package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj2.command.Command

enum class RobotPreference {
    COMPETITION,
    PRACTICE
}

val ROBOT_TYPE = RobotPreference.PRACTICE

const val CLIMBER_PID_SHUFFLEBOARD = false
const val ACTUATOR_PID_SHUFFLEBOARD = false
const val SHOOTER_PIDF_SHUFFLEBOARD = false
const val DRIVE_PID_SHUFFLEBOARD = false
const val DRIVE_FEED_FORWARD_SHUFFLEBOARD = false
const val SHOOTER_SHOOT_RPM_SHUFFLEBOARD = false
const val SHOOTER_TOLERANCE_SHUFFLEBOARD = false
const val SHOOTER_IDLE_RPM_SHUFFLEBOARD = false

const val LOG_DRIVETRAIN = false

object CompetitionPreferences {
    val CLIMBER_PID = arrayOf(0.0, 0.0, 0.0)
    val ACTUATOR_PID = arrayOf(0.0, 0.0, 0.0)
    val SHOOTER_PIDF = arrayOf(0.00024, 0.0, 0.0, 0.000185)
    val DRIVE_PID = arrayOf(2.773, 0.0, 0.0)
    val DRIVE_FEED_FORWARD = arrayOf(0.72556, 2.437, 2.5888)

    const val SHOOTER_SHOOT_RPM = 5000.0
    const val SHOOTER_TOLERANCE = 60.0
    const val SHOOTER_IDLE_RPM = 0.0
}

object PracticePreferences {
    val CLIMBER_PID = arrayOf(0.0, 0.0, 0.0)
    val ACTUATOR_PID = arrayOf(0.0, 0.0, 0.0)
    val SHOOTER_PIDF = arrayOf(0.00024, 0.0, 0.0, 0.000185)
    val DRIVE_PID = arrayOf(2.773, 0.0, 0.0)
    val DRIVE_FEED_FORWARD = arrayOf(0.72556, 2.437, 2.5888)

    const val SHOOTER_SHOOT_RPM = 5000.0
    const val SHOOTER_TOLERANCE = 60.0
    const val SHOOTER_IDLE_RPM = 0.0
}

object Preferences {
    fun getClimberPID(): Array<Double> {
        var pid = if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.CLIMBER_PID
        }else{
            PracticePreferences.CLIMBER_PID
        }

        if(CLIMBER_PID_SHUFFLEBOARD) {
            pid = SmartDashboardManager.getClimberPID()
        }

        return pid
    }

    fun getActuatorPID(): Array<Double> {
        var pid = if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.ACTUATOR_PID
        }else{
            PracticePreferences.ACTUATOR_PID
        }

        if(ACTUATOR_PID_SHUFFLEBOARD) {
            pid = SmartDashboardManager.getActuatorPID()
        }

        return pid
    }

    fun getShooterPIDF(): Array<Double> {
        var pidf = if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.SHOOTER_PIDF
        }else{
            PracticePreferences.SHOOTER_PIDF
        }

        if(SHOOTER_PIDF_SHUFFLEBOARD) {
            pidf = SmartDashboardManager.getShooterPIDF()
        }

        return pidf
    }

    fun getDrivePID(): Array<Double> {
        var pid = if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.DRIVE_PID
        }else{
            PracticePreferences.DRIVE_PID
        }

        if(DRIVE_PID_SHUFFLEBOARD) {
            pid = SmartDashboardManager.getDrivePID()
        }

        return pid
    }

    fun getDriveFeedForward(): Array<Double> {
        var feedForward = if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.DRIVE_FEED_FORWARD
        }else{
            PracticePreferences.DRIVE_FEED_FORWARD
        }

        if(DRIVE_FEED_FORWARD_SHUFFLEBOARD) {
            feedForward = SmartDashboardManager.getDriveFeedForward()
        }

        return feedForward
    }

    fun getShooterRPM(): Double {
        return if (SHOOTER_SHOOT_RPM_SHUFFLEBOARD) {
            SmartDashboardManager.getShooterRPM()
        } else {
            if(ROBOT_TYPE == RobotPreference.COMPETITION) {
                CompetitionPreferences.SHOOTER_SHOOT_RPM
            }else{
                PracticePreferences.SHOOTER_SHOOT_RPM
            }
        }
    }

    fun getShooterRPMTolerance(): Double {
        return if (SHOOTER_TOLERANCE_SHUFFLEBOARD) {
            SmartDashboardManager.getShooterRPMTolerance()
        } else {
            if(ROBOT_TYPE == RobotPreference.COMPETITION) {
                CompetitionPreferences.SHOOTER_TOLERANCE
            }else{
                PracticePreferences.SHOOTER_TOLERANCE
            }
        }
    }

    fun getShooterIdleRPM(): Double {
        return if (SHOOTER_IDLE_RPM_SHUFFLEBOARD) {
            SmartDashboardManager.getShooterIdleRPM()
        } else {
            if(ROBOT_TYPE == RobotPreference.COMPETITION) {
                CompetitionPreferences.SHOOTER_IDLE_RPM
            }else{
                PracticePreferences.SHOOTER_IDLE_RPM
            }
        }
    }

    fun getAuto(): Command? {
        return SmartDashboardManager.getAuto()
    }
}
