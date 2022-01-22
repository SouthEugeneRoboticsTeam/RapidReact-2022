package org.sert2521.rapidreact2022.commands

import edu.wpi.first.math.controller.RamseteController
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.math.trajectory.Trajectory
import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.TRACK_WIDTH
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import java.lang.System.currentTimeMillis

class DrivePath(private val trajectory: Trajectory) : CommandBase() {
    private val ramseteController = RamseteController()
    private val kinematics = DifferentialDriveKinematics(TRACK_WIDTH)

    private var startTime = 0L

    init {
        addRequirements(Drivetrain)
    }

    override fun initialize() {
        startTime = currentTimeMillis()
    }

    override fun execute() {
        val wheelSpeeds = kinematics.toWheelSpeeds(ramseteController.calculate(Drivetrain.pose, trajectory.sample((currentTimeMillis() - startTime) * 1000.0)))
        Drivetrain.driveWheelSpeeds(wheelSpeeds)
    }

    override fun isFinished(): Boolean {
        if((currentTimeMillis() - startTime) * 1000.0 > trajectory.totalTimeSeconds) {
            return true
        }

        return false
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.stop()
    }
}
