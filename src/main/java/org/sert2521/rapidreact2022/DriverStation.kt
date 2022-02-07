package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.rapidreact2022.commands.ClimbMid
import org.sert2521.rapidreact2022.commands.ClimbTransversal
import org.sert2521.rapidreact2022.commands.DrivePath

object DriverStation {
    private val autoChooser = SendableChooser<Command?>()
    val climbChooser = SendableChooser<Command>()

    init {
        val autos = AutoPaths.values()

        if (autos[0].trajectory != null) {
            autoChooser.setDefaultOption(autos[0].shuffleBoardName, DrivePath(autos[0].trajectory!!))
        } else {
            autoChooser.setDefaultOption(autos[0].shuffleBoardName, null)
        }
        for (i in 1 until autos.size) {
            if (autos[i].trajectory != null) {
                autoChooser.addOption(autos[i].shuffleBoardName, DrivePath(autos[i].trajectory!!))
            } else {
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

    fun getClimberPID(): Array<Double> {
        val pid = arrayOf(0.0, 0.0, 0.0)
        pid[0] = SmartDashboard.getNumber("Climber PID/Climber PID P", 0.0)
        pid[1] = SmartDashboard.getNumber("Climber PID/Climber PID I", 0.0)
        pid[2] = SmartDashboard.getNumber("Climber PID/Climber PID D", 0.0)

        return pid
    }

    fun getActuatorPID(): Array<Double> {
        val pid = arrayOf(0.0, 0.0, 0.0)
        pid[0] = SmartDashboard.getNumber("Actuator PID/Actuator PID P", 0.0)
        pid[1] = SmartDashboard.getNumber("Actuator PID/Actuator PID I", 0.0)
        pid[2] = SmartDashboard.getNumber("Actuator PID/Actuator PID D", 0.0)

        return pid
    }

    fun getShooterPIDF(): Array<Double> {
        val pidf = arrayOf(0.0, 0.0, 0.0, 0.0)
        pidf[0] = SmartDashboard.getNumber("Shooter PID/Shooter PID P", 0.0)
        pidf[1] = SmartDashboard.getNumber("Shooter PID/Shooter PID I", 0.0)
        pidf[2] = SmartDashboard.getNumber("Shooter PID/Shooter PID D", 0.0)
        pidf[3] = SmartDashboard.getNumber("Shooter PID/Shooter PID F", 0.0)

        return pidf
    }

    fun getDrivePID(): Array<Double> {
        val pid = arrayOf(0.0, 0.0, 0.0)
        pid[0] = SmartDashboard.getNumber("Drive PID/Drive PID P", 0.0)
        pid[1] = SmartDashboard.getNumber("Drive PID/Drive PID I", 0.0)
        pid[2] = SmartDashboard.getNumber("Drive PID/Drive PID D", 0.0)

        return pid
    }

    fun getDriveFeedForward(): Array<Double> {
        val feedForward = arrayOf(0.0, 0.0, 0.0)

        feedForward[0] = SmartDashboard.getNumber("Drive Feed Forward/Drive Feed Forward S", 0.0)
        feedForward[1] = SmartDashboard.getNumber("Drive Feed Forward/Drive Feed Forward V", 0.0)
        feedForward[2] = SmartDashboard.getNumber("Drive Feed Forward/Drive Feed Forward A", 0.0)

        return feedForward
    }

    fun getAuto(): Command? {
        return autoChooser.selected
    }

    fun getClimb(): Command? {
        return climbChooser.selected
    }

    fun update() {

    }
}