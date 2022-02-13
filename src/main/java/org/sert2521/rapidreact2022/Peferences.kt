package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj2.command.Command

const val LOG_DRIVETRAIN = false

enum class RobotPreference {
    COMPETITION,
    PRACTICE
}

val robotType = RobotPreference.PRACTICE

object CompetitionPreferences {
    val climberPID = arrayOf(0.0, 0.0, 0.0)
    val actuatorPID = arrayOf(0.0, 0.0, 0.0)
    val shooterPIDF = arrayOf(0.0003, 0.0, 0.0, 0.000185)
    val drivePID = arrayOf(2.773, 0.0, 0.0)
    val driveFeedForward = arrayOf(0.72556, 2.437, 2.5888)
}

object PracticePreferences {
    val climberPID = arrayOf(0.0, 0.0, 0.0)
    val actuatorPID = arrayOf(0.0, 0.0, 0.0)
    val shooterPIDF = arrayOf(0.0003, 0.0, 0.0, 0.000185)
    val drivePID = arrayOf(2.773, 0.0, 0.0)
    val driveFeedForward = arrayOf(0.72556, 2.437, 2.5888)
}

object Preferences {
    fun getClimberPID(shuffleboard: Boolean = false): Array<Double> {
        var pid = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.climberPID
        }else{
            PracticePreferences.climberPID
        }

        if(shuffleboard) {
            pid = SmartDashboardManager.getClimberPID()
        }

        return pid
    }

    fun getActuatorPID(shuffleboard: Boolean = false): Array<Double> {
        var pid = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.actuatorPID
        }else{
            PracticePreferences.actuatorPID
        }

        if(shuffleboard) {
            pid = SmartDashboardManager.getActuatorPID()
        }

        return pid
    }

    fun getShooterPIDF(shuffleboard: Boolean = false): Array<Double> {
        var pidf = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.shooterPIDF
        }else{
            PracticePreferences.shooterPIDF
        }

        if(shuffleboard) {
            pidf = SmartDashboardManager.getShooterPIDF()
        }

        return pidf
    }

    fun getDrivePID(shuffleboard: Boolean = false): Array<Double> {
        var pid = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.drivePID
        }else{
            PracticePreferences.drivePID
        }

        if(shuffleboard) {
            pid = SmartDashboardManager.getDrivePID()
        }

        return pid
    }

    fun getDriveFeedForward(shuffleboard: Boolean = false): Array<Double> {
        var feedForward = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.driveFeedForward
        }else{
            PracticePreferences.driveFeedForward
        }

        if(shuffleboard) {
            feedForward = SmartDashboardManager.getDriveFeedForward()
        }

        return feedForward
    }

    fun getAuto(): Command? {
        return SmartDashboardManager.getAuto()
    }
}
