package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj2.command.Command

enum class RobotPreference {
    COMPETITION,
    PRACTICE
}

val ROBOT_TYPE = RobotPreference.PRACTICE

object CompetitionPreferences {
    val CLIMBER_PID = arrayOf(0.0, 0.0, 0.0)
    val ACTUATOR_PID = arrayOf(0.0, 0.0, 0.0)
    val SHOOTER_PIDF = arrayOf(0.00035, 0.0, 0.0, 0.00018)
    val DRIVE_PID = arrayOf(2.773, 0.0, 0.0)
    val DRIVE_FEED_FORWARD = arrayOf(0.72556, 2.437, 2.5888)

    const val SHOOTER_SHOOT_RPM = 5150.0
    const val SHOOTER_TOLERANCE = 100.0
    const val SHOOTER_IDLE_RPM = 0.0
}

object PracticePreferences {
    val CLIMBER_PID = arrayOf(0.0, 0.0, 0.0)
    val ACTUATOR_PID = arrayOf(0.0, 0.0, 0.0)
    val SHOOTER_PIDF = arrayOf(0.00035, 0.0, 0.0, 0.00018)
    val DRIVE_PID = arrayOf(2.773, 0.0, 0.0)
    val DRIVE_FEED_FORWARD = arrayOf(0.72556, 2.437, 2.5888)

    const val SHOOTER_SHOOT_RPM = 5150.0
    const val SHOOTER_TOLERANCE = 100.0
    const val SHOOTER_IDLE_RPM = 0.0
}

object Preferences {
    fun getClimberPID(): Array<Double> {
        return if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.CLIMBER_PID
        }else{
            PracticePreferences.CLIMBER_PID
        }
    }

    fun getActuatorPID(): Array<Double> {
        return if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.ACTUATOR_PID
        }else{
            PracticePreferences.ACTUATOR_PID
        }
    }

    fun getShooterPIDF(): Array<Double> {
        return if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.SHOOTER_PIDF
        }else{
            PracticePreferences.SHOOTER_PIDF
        }
    }

    fun getDrivePID(): Array<Double> {
        return if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.DRIVE_PID
        }else{
            PracticePreferences.DRIVE_PID
        }
    }

    fun getDriveFeedForward(): Array<Double> {
        return if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.DRIVE_FEED_FORWARD
        }else{
            PracticePreferences.DRIVE_FEED_FORWARD
        }
    }

    fun getShooterRPM(): Double {
        return if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.SHOOTER_SHOOT_RPM
        }else{
            PracticePreferences.SHOOTER_SHOOT_RPM
        }
    }

    fun getShooterRPMTolerance(): Double {
        return if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.SHOOTER_TOLERANCE
        }else{
            PracticePreferences.SHOOTER_TOLERANCE
        }
    }

    fun getShooterIdleRPM(): Double {
        return if(ROBOT_TYPE == RobotPreference.COMPETITION) {
            CompetitionPreferences.SHOOTER_IDLE_RPM
        }else{
            PracticePreferences.SHOOTER_IDLE_RPM
        }
    }

    fun getAuto(): Command? {
        return SmartDashboardManager.getAuto()
    }
}
