package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.commands.JoystickDrive

object Drivetrain : SubsystemBase() {
    private val frontLeftMotor = WPI_TalonSRX(Talons.FRONT_LEFT_DRIVE.id)
    private val backLeftMotor = WPI_TalonSRX(Talons.BACK_LEFT_DRIVE.id)
    private val leftMotors = MotorControllerGroup(frontLeftMotor, backLeftMotor)

    private val frontRightMotor = WPI_TalonSRX(Talons.FRONT_RIGHT_DRIVE.id)
    private val backRightMotor = WPI_TalonSRX(Talons.BACK_RIGHT_DRIVE.id)
    private val rightMotors = MotorControllerGroup(frontRightMotor, backRightMotor)

    private val drive = DifferentialDrive(leftMotors, rightMotors)

    private val leftEncoder = Encoder(Encoders.LEFT_DRIVE.idA, Encoders.LEFT_DRIVE.idB, Encoders.LEFT_DRIVE.reversed, Encoders.LEFT_DRIVE.encodingType)
    private val rightEncoder = Encoder(Encoders.RIGHT_DRIVE.idA, Encoders.RIGHT_DRIVE.idB, Encoders.RIGHT_DRIVE.reversed, Encoders.RIGHT_DRIVE.encodingType)

    private val gyro = AHRS(GYRO_PORT)

    //Make sure start rotation is correct
    private val odometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(0.0))

    init {
        frontLeftMotor.inverted = Talons.FRONT_LEFT_DRIVE.reversed
        backLeftMotor.inverted = Talons.BACK_LEFT_DRIVE.reversed

        frontRightMotor.inverted = Talons.FRONT_RIGHT_DRIVE.reversed
        backRightMotor.inverted = Talons.BACK_RIGHT_DRIVE.reversed

        leftEncoder.distancePerPulse = Encoders.LEFT_DRIVE.encoderDistancePerPulse
        rightEncoder.distancePerPulse = Encoders.RIGHT_DRIVE.encoderDistancePerPulse

        leftEncoder.setMaxPeriod(Encoders.LEFT_DRIVE.maxPeriod)
        rightEncoder.setMaxPeriod(Encoders.RIGHT_DRIVE.maxPeriod)

        leftEncoder.setMinRate(Encoders.LEFT_DRIVE.minRate)
        rightEncoder.setMinRate(Encoders.RIGHT_DRIVE.minRate)

        leftEncoder.samplesToAverage = Encoders.LEFT_DRIVE.samples
        rightEncoder.samplesToAverage = Encoders.RIGHT_DRIVE.samples

        reset()

        defaultCommand = JoystickDrive()
    }

    fun reset() {
        leftEncoder.reset()
        rightEncoder.reset()

        frontLeftMotor.selectedSensorPosition = 0.0
        frontRightMotor.selectedSensorPosition = 0.0
    }

    val leftDistanceTraveled
        get() = leftEncoder.distance

    val rightDistanceTraveled
        get() = rightEncoder.distance

    override fun periodic() {
        odometry.update(gyro.rotation2d, leftDistanceTraveled, rightDistanceTraveled)
    }

    val pose
        get() = odometry.poseMeters!!

    val leftVelocity
        get() = leftEncoder.rate

    val rightVelocity
        get() = leftEncoder.rate

    fun tankDriveVolts(leftVoltage: Double, rightVoltage: Double) {
        leftMotors.setVoltage(leftVoltage)
        rightMotors.setVoltage(rightVoltage)
        drive.feed()
    }

    fun tankDrive(leftSpeed: Double, rightSpeed: Double) {
        drive.tankDrive(leftSpeed, rightSpeed)
    }


    fun arcadeDrive(speed: Double, turn: Double) {
        drive.arcadeDrive(speed, turn)
    }

    fun stop() {
        tankDrive(0.0, 0.0)
    }
}