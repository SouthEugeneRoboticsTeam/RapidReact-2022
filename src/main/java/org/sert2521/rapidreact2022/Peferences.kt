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

    SmartDashboard.putNumber("Climber PID/Climber PID P", 0.0)
    SmartDashboard.putNumber("Climber PID/Climber PID I", 0.0)
    SmartDashboard.putNumber("Climber PID/Climber PID D", 0.0)

    SmartDashboard.putNumber("Actuator PID/Actuator PID P", 0.0)
    SmartDashboard.putNumber("Actuator PID/Actuator PID I", 0.0)
    SmartDashboard.putNumber("Actuator PID/Actuator PID D", 0.0)

    SmartDashboard.putNumber("Shooter PID/Shooter PID P", 0.0)
    SmartDashboard.putNumber("Shooter PID/Shooter PID I", 0.0)
    SmartDashboard.putNumber("Shooter PID/Shooter PID D", 0.0)
    SmartDashboard.putNumber("Shooter PID/Shooter PID F", 0.0)

    SmartDashboard.putNumber("Drive PID/Drive PID P", 0.0)
    SmartDashboard.putNumber("Drive PID/Drive PID I", 0.0)
    SmartDashboard.putNumber("Drive PID/Drive PID D", 0.0)

    SmartDashboard.putNumber("Drive Feed Forward/Drive Feed Forward S", 0.0)
    SmartDashboard.putNumber("Drive Feed Forward/Drive Feed Forward V", 0.0)
    SmartDashboard.putNumber("Drive Feed Forward/Drive Feed Forward A", 0.0)
}

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
        val pid = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.climberPID
        }else{
            PracticePreferences.climberPID
        }

        if(shuffleboard) {
            pid[0] = SmartDashboard.getNumber("Climber PID/Climber PID P", 0.0)
            pid[1] = SmartDashboard.getNumber("Climber PID/Climber PID I", 0.0)
            pid[2] = SmartDashboard.getNumber("Climber PID/Climber PID D", 0.0)
        }

        return pid
    }

    fun getActuatorPID(shuffleboard: Boolean = false): Array<Double> {
        val pid = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.actuatorPID
        }else{
            PracticePreferences.actuatorPID
        }

        if(shuffleboard) {
            pid[0] = SmartDashboard.getNumber("Actuator PID/Actuator PID P", 0.0)
            pid[1] = SmartDashboard.getNumber("Actuator PID/Actuator PID I", 0.0)
            pid[2] = SmartDashboard.getNumber("Actuator PID/Actuator PID D", 0.0)
        }

        return pid
    }

    fun getShooterPIDF(shuffleboard: Boolean = false): Array<Double> {
        val pidf = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.shooterPIDF
        }else{
            PracticePreferences.shooterPIDF
        }

        if(shuffleboard) {
            pidf[0] = SmartDashboard.getNumber("Shooter PID/Shooter PID P", 0.0)
            pidf[1] = SmartDashboard.getNumber("Shooter PID/Shooter PID I", 0.0)
            pidf[2] = SmartDashboard.getNumber("Shooter PID/Shooter PID D", 0.0)
            pidf[3] = SmartDashboard.getNumber("Shooter PID/Shooter PID F", 0.0)
        }

        return pidf
    }

    fun getDrivePID(shuffleboard: Boolean = false): Array<Double> {
        val pid = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.drivePID
        }else{
            PracticePreferences.drivePID
        }

        if(shuffleboard) {
            pid[0] = SmartDashboard.getNumber("Drive PID/Drive PID P", 0.0)
            pid[1] = SmartDashboard.getNumber("Drive PID/Drive PID I", 0.0)
            pid[2] = SmartDashboard.getNumber("Drive PID/Drive PID D", 0.0)
        }

        return pid
    }

    fun getDriveFeedForward(shuffleboard: Boolean = false): Array<Double> {
        val feedForward = if(robotType == RobotPreference.COMPETITION) {
            CompetitionPreferences.driveFeedForward
        }else{
            PracticePreferences.driveFeedForward
        }

        if(shuffleboard) {
            feedForward[0] = SmartDashboard.getNumber("Drive Feed Forward/Drive Feed Forward S", 0.0)
            feedForward[1] = SmartDashboard.getNumber("Drive Feed Forward/Drive Feed Forward V", 0.0)
            feedForward[2] = SmartDashboard.getNumber("Drive Feed Forward/Drive Feed Forward A", 0.0)
        }

        return feedForward
    }
}
