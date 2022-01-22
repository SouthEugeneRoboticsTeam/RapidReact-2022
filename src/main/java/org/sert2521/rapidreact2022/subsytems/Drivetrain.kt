package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.Motors
import org.sert2521.rapidreact2022.commands.JoystickDrive
import edu.wpi.first.wpilibj.SPI




object Drivetrain : SubsystemBase() {
    private val frontLeft = TalonSRX(Motors.FRONT_LEFT.id)
    private val backLeft = TalonSRX(Motors.BACK_LEFT.id)

    private val frontRight = TalonSRX(Motors.FRONT_RIGHT.id)
    private val backRight = TalonSRX(Motors.BACK_RIGHT.id)

    private val gyro = AHRS(SPI.Port.kMXP)

    private val odometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(0.0))

    init {
        //Right motors are built the other way on the drivetrain
        frontRight.inverted = true
        backRight.inverted = true

        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)

        defaultCommand = JoystickDrive()

        resetDistanecTraveled()
    }

    fun resetDistanecTraveled() {
        frontLeft.selectedSensorPosition = 0.0
        frontRight.selectedSensorPosition = 0.0
    }

    val leftDistanceTraveled
        get() = frontLeft.selectedSensorPosition * Motors.FRONT_LEFT.encoderDistancePerPulse

    val rightDistanceTraveled
        get() = frontLeft.selectedSensorPosition * Motors.FRONT_LEFT.encoderDistancePerPulse

    override fun periodic() {
        odometry.update(gyro.rotation2d, leftDistanceTraveled, rightDistanceTraveled)
    }

    val pose
        get() = odometry.poseMeters

    val leftVelocity
        get() = frontLeft.selectedSensorVelocity * Motors.FRONT_LEFT.encoderDistancePerPulse

    val rightVelocity
        get() = frontLeft.selectedSensorVelocity * Motors.FRONT_RIGHT.encoderDistancePerPulse

    val averageVelocity
        get() = (rightVelocity + leftVelocity) / 2.0

    private fun spinLeft(amount: Double) {
        frontLeft.set(ControlMode.PercentOutput, amount)
        backLeft.set(ControlMode.PercentOutput, amount)
    }

    private fun spinRight(amount: Double) {
        frontRight.set(ControlMode.PercentOutput, amount)
        backRight.set(ControlMode.PercentOutput, amount)
    }

    fun tankDrive(leftSpeed: Double, rightSpeed: Double) {
        spinLeft(leftSpeed)
        spinRight(rightSpeed)
    }

    fun arcadeDrive(speed: Double, turn: Double) {
        spinLeft(speed + turn)
        spinRight(speed - turn)
    }

    fun stop() {
        tankDrive(0.0, 0.0)
    }
}