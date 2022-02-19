package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.rapidreact2022.commands.ShootDoubleLeft
import org.sert2521.rapidreact2022.commands.ShootDoubleRight
import org.sert2521.rapidreact2022.commands.ShootSingleLeft
import org.sert2521.rapidreact2022.commands.ShootSingleRight
import org.sert2521.rapidreact2022.subsytems.Climber
import org.sert2521.rapidreact2022.subsytems.Drivetrain
import org.sert2521.rapidreact2022.subsytems.Intake
import org.sert2521.rapidreact2022.subsytems.Shooter

object SmartDashboardManager {
    private val autoChooser = SendableChooser<Command?>()

    init {
        autoChooser.setDefaultOption("Nothing", null)
        autoChooser.addOption("Shoot Single Right", ShootSingleRight())
        autoChooser.addOption("Shoot Single Left", ShootSingleLeft())
        autoChooser.addOption("Shoot Double Right", ShootDoubleRight())
        autoChooser.addOption("Shoot Double Left", ShootDoubleLeft())
        SmartDashboard.putData(autoChooser)
    }

    fun getAuto(): Command? {
        return autoChooser.selected
    }

    fun update() {
        SmartDashboard.putNumber("Tuning/Shooter Speed", Shooter.wheelSpeed)

        SmartDashboard.putNumber("Tuning/Drivetrain Left Speed", Drivetrain.leftVelocity)
        SmartDashboard.putNumber("Tuning/Drivetrain Right Speed", Drivetrain.rightVelocity)

        SmartDashboard.putNumber("Tuning/Drivetrain Left Distance", Drivetrain.leftDistanceTraveled)
        SmartDashboard.putNumber("Tuning/Drivetrain Right Distance", Drivetrain.rightDistanceTraveled)

        SmartDashboard.putNumber("Tuning/Climber Static Height", Climber.staticHeight)
        SmartDashboard.putNumber("Tuning/Climber Variable Height", Climber.variableHeight)
        SmartDashboard.putNumber("Tuning/Climber Variable Angle", Climber.variableAngle)

        SmartDashboard.putBoolean("Tuning/Intake Full", Intake.indexerFull)

        SmartDashboard.putNumber("Tuning/Pose X", Drivetrain.pose.x)
        SmartDashboard.putNumber("Tuning/Pose Y", Drivetrain.pose.y)

        SmartDashboard.putNumber("Tuning/Gyro Angle ", Drivetrain.pose.rotation.degrees)
    }
}