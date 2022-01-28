package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.commands.JoystickDrive

//Weird encoder code due too encoders going to be different on the real robot
//PID will need to be fixed
object Drivetrain : SubsystemBase() {
    private val frontLeftMotor = TalonSRX(Motors.FRONT_LEFT_DRIVE.id)
    private val backLeftMotor = TalonSRX(Motors.BACK_LEFT_DRIVE.id)

    private val frontRightMotor = TalonSRX(Motors.FRONT_RIGHT_DRIVE.id)
    private val backRightMotor = TalonSRX(Motors.BACK_RIGHT_DRIVE.id)

    private val leftEncoder = Encoder(Encoders.LEFT_DRIVE.idA, Encoders.LEFT_DRIVE.idB, Encoders.LEFT_DRIVE.reversed, Encoders.LEFT_DRIVE.encodingType)
    private val rightEncoder = Encoder(Encoders.RIGHT_DRIVE.idA, Encoders.RIGHT_DRIVE.idB, Encoders.RIGHT_DRIVE.reversed, Encoders.RIGHT_DRIVE.encodingType)

    private val gyro = AHRS(GYRO_PORT)

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

        frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder)
        frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder)

        frontLeftMotor.config_kP(0, 0.1)
        frontLeftMotor.config_kI(0, 0.1)
        frontLeftMotor.config_kD(0, 0.1)
    }

    fun resetDistanceTraveled() {
        leftEncoder.reset()
        rightEncoder.reset()

        frontLeftMotor.selectedSensorPosition = 0.0
        frontRightMotor.selectedSensorPosition = 0.0
    }

    val leftDistanceTraveled
        get() = frontLeftMotor.selectedSensorPosition * (WHEEL_CIRCUMFERENCE / (MAG_PULSES_PER_ROTATION))//leftEncoder.distance

    val rightDistanceTraveled
        get() = frontRightMotor.selectedSensorPosition * (WHEEL_CIRCUMFERENCE / (MAG_PULSES_PER_ROTATION))//rightEncoder.distance

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

    //Make motors work with ControlMode.Velocity
    fun driveWheelSpeeds(wheelSpeeds: DifferentialDriveWheelSpeeds) {
        spinLeft(ControlMode.Velocity, wheelSpeeds.leftMetersPerSecond / (WHEEL_CIRCUMFERENCE / MAG_PULSES_PER_ROTATION))
        spinRight(ControlMode.Velocity, wheelSpeeds.rightMetersPerSecond / (WHEEL_CIRCUMFERENCE / MAG_PULSES_PER_ROTATION))
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