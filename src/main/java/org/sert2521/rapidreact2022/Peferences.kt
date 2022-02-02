package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.rapidreact2022.commands.ClimbMid
import org.sert2521.rapidreact2022.commands.ClimbTransversal
import org.sert2521.rapidreact2022.commands.DrivePath

enum class RobotPreference {
    COMPETITION,
    PRACTICE
}

val robotType = RobotPreference.PRACTICE

val autoChooser = SendableChooser<Command?>()
val climbChooser = SendableChooser<Command>()

fun initShuffleboard() {
    val autos = AutoPaths.values()

    if(autos[0].trajectory != null) {
        autoChooser.setDefaultOption(autos[0].shuffleBoardName, DrivePath(autos[0].trajectory!!))
    }else{
        autoChooser.setDefaultOption(autos[0].shuffleBoardName, null)
    }
    for(i in 1 until autos.size) {
        if(autos[i].trajectory != null) {
            autoChooser.addOption(autos[i].shuffleBoardName, DrivePath(autos[i].trajectory!!))
        }else{
            autoChooser.setDefaultOption(autos[i].shuffleBoardName, null)
        }
    }

    SmartDashboard.putData(autoChooser)

    climbChooser.setDefaultOption("Mid", ClimbMid())
    climbChooser.addOption("Transversal", ClimbTransversal())
    SmartDashboard.putData(climbChooser)

    SmartDashboard.putNumberArray("Climber PID", arrayOf(0.0, 0.0, 0.0))
    SmartDashboard.putNumberArray("Actuator PID", arrayOf(0.0, 0.0, 0.0))
    SmartDashboard.putNumberArray("Shooter PIDF", arrayOf(0.0, 0.0, 0.0, 0.0))
    SmartDashboard.putNumberArray("Drive PID", arrayOf(0.0, 0.0, 0.0))

    SmartDashboard.putNumberArray("Drive Feed Forward", arrayOf(0.0, 0.0, 0.0))
}

object CompetitionPreferences {
    val climberPID = arrayOf(0.0, 0.0, 0.0)
    val actuatorPID = arrayOf(0.0, 0.0, 0.0)
    val shooterPIDF = arrayOf(0.0, 0.0, 0.0, 0.00024)
    val drivePID = arrayOf(0.0, 0.0, 0.0)
    val driveFeedForward = arrayOf(0.86657, 2.2779, 0.73969)
}

object PracticePreferences {
    val climberPID = arrayOf(0.0, 0.0, 0.0)
    val actuatorPID = arrayOf(0.0, 0.0, 0.0)
    val shooterPIDF = arrayOf(0.0, 0.0, 0.0, 0.00024)
    val drivePID = arrayOf(0.0, 0.0, 0.0)
    val driveFeedForward = arrayOf(0.86657, 2.2779, 0.73969)
}

object Preferences {
    fun getClimberPID(shuffleboard: Boolean = false): Array<Double> {
        var outArray = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.climberPID
        }else{
            PracticePreferences.climberPID
        }

        if(shuffleboard) {
            outArray = SmartDashboard.getNumberArray("Climber PID", outArray)
        }

        return outArray
    }

    fun getActuatorPID(shuffleboard: Boolean = false): Array<Double> {
        var pid = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.actuatorPID
        }else{
            PracticePreferences.actuatorPID
        }

        if(shuffleboard) {
            pid = SmartDashboard.getNumberArray("Actuator PID", pid)
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
            pidf = SmartDashboard.getNumberArray("Shooter PIDF", pidf)
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
            pid = SmartDashboard.getNumberArray("Drive PID", pid)
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
            feedForward = SmartDashboard.getNumberArray("Drive Feed Forward", feedForward)
        }

        return feedForward
    }
}
