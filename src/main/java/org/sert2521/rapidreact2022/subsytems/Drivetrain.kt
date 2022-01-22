package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.Motors
import org.sert2521.rapidreact2022.commands.JoystickDrive
import edu.wpi.first.wpilibj.SPI
import org.sert2521.rapidreact2022.Encoders

//Add PID
object Drivetrain : SubsystemBase() {
    private val frontLeftMotor = TalonSRX(Motors.FRONT_LEFT_DRIVE.id)
    private val backLeftMotor = TalonSRX(Motors.BACK_LEFT_DRIVE.id)

    private val frontRightMotor = TalonSRX(Motors.FRONT_RIGHT_DRIVE.id)
    private val backRightMotor = TalonSRX(Motors.BACK_RIGHT_DRIVE.id)

    private val leftEncoder = Encoder(Encoders.LEFT_DRIVE.idA, Encoders.LEFT_DRIVE.idB, Encoders.LEFT_DRIVE.reversed, Encoders.LEFT_DRIVE.encodingType)
    private val rightEncoder = Encoder(Encoders.RIGHT_DRIVE.idA, Encoders.RIGHT_DRIVE.idB, Encoders.RIGHT_DRIVE.reversed, Encoders.RIGHT_DRIVE.encodingType)

    private val gyro = AHRS(SPI.Port.kMXP)

    //Make sure start rotation is correct
    private val odometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(0.0))

    init {
        frontLeftMotor.inverted = Motors.FRONT_LEFT_DRIVE.reversed
        backLeftMotor.inverted = Motors.BACK_LEFT_DRIVE.reversed

        frontRightMotor.inverted = Motors.FRONT_RIGHT_DRIVE.reversed
        backRightMotor.inverted = Motors.BACK_RIGHT_DRIVE.reversed

        leftEncoder.distancePerPulse = Encoders.LEFT_DRIVE.encoderDistancePerPulse
        rightEncoder.distancePerPulse = Encoders.RIGHT_DRIVE.encoderDistancePerPulse

        leftEncoder.setMaxPeriod(Encoders.LEFT_DRIVE.maxPeriod)
        rightEncoder.setMaxPeriod(Encoders.RIGHT_DRIVE.maxPeriod)

        leftEncoder.setMinRate(Encoders.LEFT_DRIVE.minRate)
        rightEncoder.setMinRate(Encoders.RIGHT_DRIVE.minRate)

        leftEncoder.samplesToAverage = Encoders.LEFT_DRIVE.samples
        rightEncoder.samplesToAverage = Encoders.RIGHT_DRIVE.samples

        resetDistanceTraveled()

        defaultCommand = JoystickDrive()
    }

    fun resetDistanceTraveled() {
        leftEncoder.reset()
        rightEncoder.reset()
    }

    val leftDistanceTraveled
        get() = leftEncoder.distance

    val rightDistanceTraveled
        get() = leftEncoder.distance

    override fun periodic() {
        odometry.update(gyro.rotation2d, leftDistanceTraveled, rightDistanceTraveled)
    }

    val pose
        get() = odometry.poseMeters!!

    val leftVelocity
        get() = leftEncoder.rate

    val rightVelocity
        get() = leftEncoder.rate

    val averageVelocity
        get() = (rightVelocity + leftVelocity) / 2.0

    private fun spinLeft(mode: ControlMode, amount: Double) {
        frontLeftMotor.set(mode, amount)
        backLeftMotor.set(mode, amount)
    }

    private fun spinRight(mode: ControlMode, amount: Double) {
        frontRightMotor.set(mode, amount)
        backRightMotor.set(mode, amount)
    }

    //Check to make sure works
    fun driveWheelSpeeds(wheelSpeeds: DifferentialDriveWheelSpeeds) {
        spinLeft(ControlMode.Velocity, wheelSpeeds.leftMetersPerSecond)
        spinRight(ControlMode.Velocity, wheelSpeeds.rightMetersPerSecond)
    }

    fun tankDrive(leftSpeed: Double, rightSpeed: Double) {
        spinLeft(ControlMode.PercentOutput, leftSpeed)
        spinRight(ControlMode.PercentOutput, rightSpeed)
    }

    fun arcadeDrive(speed: Double, turn: Double) {
        spinLeft(ControlMode.PercentOutput, speed + turn)
        spinRight(ControlMode.PercentOutput, speed - turn)
    }

    fun stop() {
        tankDrive(0.0, 0.0)
    }
}