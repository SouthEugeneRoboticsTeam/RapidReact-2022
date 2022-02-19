package org.sert2521.rapidreact2022

import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.CounterBase
import edu.wpi.first.wpilibj.SPI
import kotlin.math.PI

const val THROUGH_BORE_PULSES_PER_ROTATION = 2048.0

//6 inches is 0.1524 meters
const val WHEEL_CIRCUMFERENCE = 0.1524 * PI

const val TRACK_WIDTH = 0.5816473
const val MAX_SPEED = 2.0
const val MAX_SLOW_SPEED = 0.5
const val SLEW_RATE = 4.0
const val DEADBAND = 0.1

const val CLIMBER_HEIGHT_PER_ROTATION = 0.0

const val MAX_CLIMBER_ANGLE = 0.0
const val MIN_CLIMBER_ANGLE = 0.0

const val MID_HEIGHT = 0.0
const val NEXT_HOOKED_BAR_HEIGHT = 0.0
const val FORWARD_HOOKED_ACTUATOR_ANGLE = 0.0
const val NEXT_UNHOOKED_BAR_HEIGHT = 0.0
const val FORWARD_UNHOOKED_ACTUATOR_ANGLE = 0.0
const val DEFAULT_ACTUATOR_ANGLE = 0.0
const val BACKWARD_HOOKED_ACTUATOR_ANGLE = 0.0
const val BACKWARD_UNHOOKED_ACTUATOR_ANGLE = 0.0
const val HANG_HEIGHT = 0.0
const val LET_GO_HEIGHT = 0.0
const val DEFAULT_HEIGHT = 0.0

const val CLIMB_ADJUST_SPEED = 0.1
const val ACTUATE_ADJUST_SPEED = 0.1

const val INTAKE_SPEED = 0.4
const val INDEXER_SPEED = 0.7

const val LED_LENGTH = 56//58, but two are covered

const val AUTO_SPEED = 1.7
const val AUTO_ACCELERATION = 1.7

const val AUTO_TURN_SPEED = 1.0
const val AUTO_TURN_ACCELERATION = 1.0

const val AUTO_EXIT_SPEED = 3.0
const val AUTO_EXIT_ACCELERATION = 3.0

const val SHOOT_TIME = 1.5

val SHOOT_POSE = Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0))
val END_POSE_DOUBLE = Pose2d(-3.4, 0.0, Rotation2d.fromDegrees(0.0))

val DOUBLE_START_POSE_LEFT = Pose2d(-0.6, -0.9, Rotation2d.fromDegrees(180.0))
val PICKUP_POSE_LEFT = Pose2d(-2.6, -0.9, Rotation2d.fromDegrees(180.0))
val ENTRY_POSE_LEFT = Pose2d(-2.6, 0.0, Rotation2d.fromDegrees(0.0))
val END_POSE_SINGLE_LEFT = Pose2d(-2.5, -1.5, Rotation2d.fromDegrees(35.0))

val DOUBLE_START_POSE_RIGHT = Pose2d(-0.6, 0.9, Rotation2d.fromDegrees(180.0))
val PICKUP_POSE_RIGHT = Pose2d(-2.6, 0.9, Rotation2d.fromDegrees(180.0))
val ENTRY_POSE_RIGHT = Pose2d(-2.6, 0.0, Rotation2d.fromDegrees(0.0))
val END_POSE_SINGLE_RIGHT = Pose2d(-2.5, 1.5, Rotation2d.fromDegrees(-35.0))

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
    VARIABLE_ACTUATOR(7, false)
} 

enum class Sparks(val id: Int, val type: CANSparkMaxLowLevel.MotorType, val reversed: Boolean) {
    STATIC_CLIMBER(8, CANSparkMaxLowLevel.MotorType.kBrushless, false),
    VARIABLE_CLIMBER(9, CANSparkMaxLowLevel.MotorType.kBrushless, false),
    SHOOTER(10, CANSparkMaxLowLevel.MotorType.kBrushless, true)
}

enum class SparkEncoders(val conversionFactor: Double) {
    SHOOTER(1.0),
    STATIC_CLIMBER(CLIMBER_HEIGHT_PER_ROTATION),
    VARIABLE_CLIMBER(CLIMBER_HEIGHT_PER_ROTATION)
}

enum class OnOffs(val id: Int) {
    STATIC_CLIMBER_DOWN(13),
    VARIABLE_CLIMBER_DOWN(11),
    INDEXER(4)
}

enum class Potentiometers(val id: Int, val maxAngle: Double, val zeroAngle: Double) {
    VARIABLE_CLIMBER_ANGLE(0, 0.0, 0.0)
}

enum class Encoders(val idA: Int, val idB: Int, val reversed: Boolean, val encodingType: CounterBase.EncodingType, val encoderDistancePerPulse: Double, val maxPeriod: Double, val minRate: Double, val samples: Int) {
    LEFT_DRIVE(0, 1, true, CounterBase.EncodingType.k2X, WHEEL_CIRCUMFERENCE / THROUGH_BORE_PULSES_PER_ROTATION, 0.1, 10.0, 5),
    RIGHT_DRIVE(2, 3, false, CounterBase.EncodingType.k2X, WHEEL_CIRCUMFERENCE / THROUGH_BORE_PULSES_PER_ROTATION, 0.1, 10.0, 5),
}

enum class LedStrips(val id: Int) {
    MAIN(9)
}