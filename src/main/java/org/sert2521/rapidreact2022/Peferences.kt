package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.WaitCommand

enum class RobotPreference {
    COMPETITION,
    PRACTICE
}

val robotType = RobotPreference.PRACTICE

object CompetitionPreferences {
    val climberPID = arrayOf(0.0, 0.0, 0.0)
    val actuatorPID = arrayOf(0.0, 0.0, 0.0)
    val shooterPIDF = arrayOf(0.0003, 0.0, 0.0, 0.000185)
    val drivePID = arrayOf(0.0, 0.0, 0.0)
    val driveFeedForward = arrayOf(0.86657, 2.2779, 0.73969)
}

object PracticePreferences {
    val climberPID = arrayOf(0.0, 0.0, 0.0)
    val actuatorPID = arrayOf(0.0, 0.0, 0.0)
    val shooterPIDF = arrayOf(0.0003, 0.0, 0.0, 0.000185)
    val drivePID = arrayOf(0.0, 0.0, 0.0)
    val driveFeedForward = arrayOf(0.86657, 2.2779, 0.73969)
}

object Preferences {
    fun getClimberPID(shuffleboard: Boolean = false): Array<Double> {
        var pid = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.climberPID
        }else{
            PracticePreferences.climberPID
        }

        if(shuffleboard) {
            pid = DriverStation.getClimberPID()
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
            pid = DriverStation.getActuatorPID()
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
            pidf = DriverStation.getShooterPIDF()
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
            pid = DriverStation.getDrivePID()
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
            feedForward = DriverStation.getDriveFeedForward()
        }

        return feedForward
    }

    fun getAuto(): Command {
        return DriverStation.getAuto() ?: WaitCommand(0.0)
    }

    fun getClimb(): Command {
        return DriverStation.getClimb() ?: WaitCommand(0.0)
    }
}
