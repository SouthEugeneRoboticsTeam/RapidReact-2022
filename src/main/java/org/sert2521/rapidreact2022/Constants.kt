package org.sert2521.rapidreact2022

import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.CounterBase
import edu.wpi.first.wpilibj.SPI
import kotlin.math.PI

//Maybe add to other motors
const val DEFAULT_VOLTAGE = 12.0

const val THROUGH_BORE_CYCLES_PER_ROTATION = 2048
const val THROUGH_BORE_COUNTS_PER_ROTATION = 8192

//6 inches is 0.1524 meters
const val WHEEL_CIRCUMFERENCE = 0.1524 * PI

const val TRACK_WIDTH = 0.5816473
const val MAX_SPEED_ADDON = 1.0
const val MAX_SPEED = 2.0
const val MAX_TURN_SPEED = 1.2
const val MAX_SLOW_SPEED = 0.5
const val MAX_SLOW_TURN_SPEED = 0.8
const val SLEW_RATE = 3.0
const val DEADBAND = 0.1

const val CLIMBER_HEIGHT_PER_ROTATION = 0.004842
const val CLIMBER_ANGLE_OFFSET = 122.0
const val MAX_CLIMBER_ANGLE_VALUE = 24750
const val MIN_CLIMBER_ANGLE_VALUE = -27618
const val START_POS = 1.0
const val MAX_CLIMBER_HEIGHT = 0.66
const val MIN_CLIMBER_HEIGHT = 0.0

const val DEFAULT_ANGLE = 30.0
const val HANG_HEIGHT = 0.0
const val REACH_MID = 0.64
const val PAST_HIGH_ANGLE = 9.0
const val ABOVE_HIGH = 0.66
const val HIT_HIGH = 0.57
const val PULL_IN_HIGH = 0.46
const val LET_GO_MID = 0.6
const val GO_UNDER_ANGLE = 16.0
const val GO_UNDER_HIGH = 0.35
const val PAST_CURRENT_ANGLE = 42.0
const val ABOVE_CURRENT = 0.64
const val REACH_TRAVERSAL = 0.66
const val REACH_TRAVERSAL_ANGLE = 25.0
const val LET_GO_HIGH = 0.6
const val HIT_TRAVERSAL = 0.56
const val PULL_IN_TRAVERSAL = 0.46
const val LET_GO_ANGLE = 26.0
const val GO_UNDER_TRAVERSAL = 0.3
const val END = 0.1
const val END_ANGLE = 46.0

const val DEFAULT_TOLERANCE = 0.02
const val DEFAULT_TOLERANCE_ANGLE = 3.0

const val CLIMBER_MAINTAIN = -0.1
const val CLIMBER_HIT_SPEED = 0.35
const val FILTER_TAPS = 20
const val STOP_TOLERANCE = 0.5
const val CLIMBER_RESET_SPEED = -0.6

const val CLIMBER_LET_GO_ACCELERATION = 0.005
const val CLIMBER_LET_GO_SPEED = 0.05
const val CLIMBER_MAX_SPEED = 0.4
const val CLIMBER_MAX_ACCELERATION = 0.7
const val CLIMBER_ACTUATOR_MAX_SPEED = 5.0
const val CLIMBER_ACTUATOR_MAX_ACCELERATION = 5.0

const val SERVO_UNLOCK_STATIC = 0.1
const val SERVO_UNLOCK_VARIABLE = 0.75
const val SERVO_LOCK_STATIC = 0.75
const val SERVO_LOCK_VARIABLE = 0.0
const val LOCK_TIME_STATIC = 1.0
const val LOCK_TIME_VARIABLE = 1.5
const val UNSTICK_SPEED_DOWN = 0.0//-0.05
const val UNSTICK_SPEED_CLIMB = 0.0//-0.25

const val INTAKE_SPEED = 0.8
const val INDEXER_SPEED = 0.8

const val AUTO_SPEED = 1.2
const val AUTO_ACCELERATION = 1.2

const val AUTO_TURN_SPEED = 0.7
const val AUTO_TURN_ACCELERATION = 0.7

const val AUTO_EXIT_SPEED = 1.5
const val AUTO_EXIT_ACCELERATION = 1.5

const val END_SPEED = 0.4

const val SHOOT_TIME = 2.0
const val SHOOT_DELAY = 0.5

//fix
val LOG_PATHS = listOf("/media/sda1/", "/media/sdb1/", "/media/sdc1/")
const val FORMAT_PATTERN = "yyyy,MMM,dd,HH,mm,ss"

const val CAMERA_PATH = "/Drive Switched"
val CAMERAS = listOf("Drive 1", "Drive 2")

val SHOOT_POSE = Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0))

val DOUBLE_START_POSE_LEFT = Pose2d(-0.8, -0.45, Rotation2d.fromDegrees(190.0))
val PICKUP_POSE_LEFT = Pose2d(-2.55, -0.75, Rotation2d.fromDegrees(205.0))
val ENTRY_POSE_LEFT = Pose2d(-1.6, -0.95, Rotation2d.fromDegrees(80.0))
val END_POSE_SINGLE_LEFT = Pose2d(-2.0, -1.8, Rotation2d.fromDegrees(20.0))

val DOUBLE_START_POSE_RIGHT = Pose2d(-0.8, 0.45, Rotation2d.fromDegrees(-190.0))
val PICKUP_POSE_RIGHT = Pose2d(-2.55, 0.75, Rotation2d.fromDegrees(-205.0))
val ENTRY_POSE_RIGHT = Pose2d(-1.6, 0.95, Rotation2d.fromDegrees(-80.0))
val END_POSE_SINGLE_RIGHT = Pose2d(-2.0, 1.8, Rotation2d.fromDegrees(-20.0))

const val DRIVE_FORWARD_DISTANCE = 1.75

val GYRO_PORT = SPI.Port.kMXP

const val PRIMARY_CONTROLLER_ID = 0
const val SECONDARY_CONTROLLER_ID = 1

enum class Talons(val id: Int, val reversed: Boolean) {
    FRONT_LEFT_DRIVE(1, false),
    BACK_LEFT_DRIVE(3, false),
    FRONT_RIGHT_DRIVE(2, true),
    BACK_RIGHT_DRIVE(4, true),
    INTAKE(5, false),
    INDEXER(6, true),
    VARIABLE_ACTUATOR(7, true)
} 

enum class Sparks(val id: Int, val type: CANSparkMaxLowLevel.MotorType, val reversed: Boolean) {
    STATIC_CLIMBER(8, CANSparkMaxLowLevel.MotorType.kBrushless, true),
    VARIABLE_CLIMBER(9, CANSparkMaxLowLevel.MotorType.kBrushless, false),
    SHOOTER(10, CANSparkMaxLowLevel.MotorType.kBrushless, true),
    SHOOTER_BACK(11, CANSparkMaxLowLevel.MotorType.kBrushed, false)
}

enum class SparkEncodersHall(val conversionFactor: Double) {
    //maybe fix ratio it is 8 to 9
    SHOOTER(1.0),
    STATIC_CLIMBER(CLIMBER_HEIGHT_PER_ROTATION),
    VARIABLE_CLIMBER(CLIMBER_HEIGHT_PER_ROTATION)
}

enum class SparkEncodersQuadrature(val encoderPulsesPerRev: Int, val conversionFactor: Double) {
    SHOOTER_BACK(THROUGH_BORE_COUNTS_PER_ROTATION, 1.0)
}

enum class OnOffs(val id: Int) {
    STATIC_CLIMBER_DOWN(5),
    VARIABLE_CLIMBER_DOWN(6),
    INDEXER(4)
}

enum class Potentiometers(val id: Int, val maxAngle: Double, val zeroAngle: Double) {
    VARIABLE_CLIMBER_ANGLE(1, 300.0, 0.0)
}

enum class Encoders(val idA: Int, val idB: Int, val reversed: Boolean, val encodingType: CounterBase.EncodingType, val encoderDistancePerPulse: Double, val maxPeriod: Double, val minRate: Double, val samples: Int) {
    LEFT_DRIVE(0, 1, true, CounterBase.EncodingType.k2X, WHEEL_CIRCUMFERENCE / THROUGH_BORE_CYCLES_PER_ROTATION, 0.1, 0.01, 5),
    RIGHT_DRIVE(2, 3, false, CounterBase.EncodingType.k2X, WHEEL_CIRCUMFERENCE / THROUGH_BORE_CYCLES_PER_ROTATION, 0.1, 0.01, 5),
}

enum class TalonEncoders(val device: TalonSRXFeedbackDevice, val encoderDistanceFactor: Double) {
    ACTUATOR_MOTOR(TalonSRXFeedbackDevice.QuadEncoder, 1.0)
}

enum class PWMS(val id: Int) {
    LED(9),
    SERVO_STATIC(0),
    SERVO_VARIABLE(1)
}