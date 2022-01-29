package org.sert2521.rapidreact2022

import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.trajectory.Trajectory
import edu.wpi.first.math.trajectory.TrajectoryConfig
import edu.wpi.first.math.trajectory.TrajectoryGenerator
import edu.wpi.first.wpilibj.CounterBase
import edu.wpi.first.wpilibj.SPI
import kotlin.math.PI

const val THROUGH_BORE_PULSES_PER_ROTATION = 2048.0

//6 inches is 0.1524 meters
const val WHEEL_CIRCUMFERENCE = 0.1524 * PI

const val TRACK_WIDTH = 0.5816473

const val CLIMBER_HEIGHT_PER_ROTATION = 0.0

const val MAX_CLIMBER_ANGLE = 0.0
const val MIN_CLIMBER_ANGLE = 0.0

const val MID_HEIGHT = 0.0
const val NEXT_BAR_HEIGHT = 0.0
const val FORWARD_ACTUATOR_ANGLE = 0.0
const val DEFAULT_ACTUATOR_ANGLE = 0.0
const val BACKWARD_ACTUATOR_ANGLE = 0.0
const val HANG_HEIGHT = 0.0
const val LET_GO_HEIGHT = 0.0
const val DEFAULT_HEIGHT = 0.0

const val CLIMB_ADJUST_SPEED = 0.1
const val ACTUATE_ADJUST_SPEED = 0.1

const val INTAKE_SPEED = 0.5
const val INDEXER_SPEED = 0.5

val GYRO_PORT = SPI.Port.kMXP

enum class AutoPaths(val shuffleBoardName: String, val trajectory: Trajectory?) {
    DRIVE_FORWARD("Forward", TrajectoryGenerator.generateTrajectory(
        Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
        listOf(),
        Pose2d(1.0, 0.0, Rotation2d.fromDegrees(0.0)),
        TrajectoryConfig(0.5, 0.5))),
    NOTHING("Nothing", null)
}

const val PRIMARY_CONTROLLER_ID = 0
const val SECONDARY_CONTROLLER_ID = 0

enum class PrimaryButtons(val id: Int) {
    INTAKE(1),
    OVERRIDE_INDEXER(1),//Fix id
    SHOOT(1) //Fix id
}

enum class SecondaryButtons(val id: Int) {
    CLIMB_NEXT(1), //Fix id
    STATIC_LOWER(1), //Fix id
    STATIC_RAISE(1), //Fix id
    VARIABLE_LOWER(1), //Fix id
    VARIABLE_RAISE(1), //Fix id
    VARIABLE_ANGLE_DOWN(1), //Fix id
    VARIABLE_ANGLE_UP(1), //Fix id
    START_CLIMB(1) //Fix id
}

enum class Talons(val id: Int, val reversed: Boolean) {
    FRONT_LEFT_DRIVE(1, false),
    BACK_LEFT_DRIVE(3, false),
    FRONT_RIGHT_DRIVE(2, true),
    BACK_RIGHT_DRIVE(4, true),
    INTAKE(5, false), //Fix id
    INDEXER(6, false), //Fix id
    STATIC_CLIMBER(7, false), //Fix id
    VARIABLE_CLIMBER(8, false), //Fix id
    VARIABLE_ACTUATOR(9, false) //Fix id
} 

enum class Sparks(val id: Int, val type: CANSparkMaxLowLevel.MotorType, val reversed: Boolean) {
    SHOOTER(10, CANSparkMaxLowLevel.MotorType.kBrushless, false) //Fix id
}

enum class OnOffs(val id: Int) {
    STATIC_CLIMBER_DOWN(13), //Fix id
    STATIC_CLIMBER_UP(12), //Fix id
    VARIABLE_CLIMBER_DOWN(11), //Fix id
    VARIABLE_CLIMBER_UP(10), //Fix id
    INDEXER(9) //Fix id
}

enum class Potentiometers(val id: Int, val maxAngle: Double, val zeroAngle: Double) {
    VARIABLE_CLIMBER_ANGLE(0, 0.0, 0.0) //Fix id
}

enum class Encoders(val idA: Int, val idB: Int, val reversed: Boolean, val encodingType: CounterBase.EncodingType, val encoderDistancePerPulse: Double, val maxPeriod: Double, val minRate: Double, val samples: Int) {
    LEFT_DRIVE(0, 1, true, CounterBase.EncodingType.k2X, WHEEL_CIRCUMFERENCE / THROUGH_BORE_PULSES_PER_ROTATION, 0.1, 10.0, 5),
    RIGHT_DRIVE(2, 3, false, CounterBase.EncodingType.k2X, WHEEL_CIRCUMFERENCE / THROUGH_BORE_PULSES_PER_ROTATION, 0.1, 10.0, 5),
    STATIC_CLIMBER(4, 5, false, CounterBase.EncodingType.k2X, CLIMBER_HEIGHT_PER_ROTATION, 0.1, 10.0, 5), //Fix id
    VARIABLE_CLIMBER(6, 7, false, CounterBase.EncodingType.k2X, CLIMBER_HEIGHT_PER_ROTATION, 0.1, 10.0, 5) //Fix id
}

//Need to do characterization
enum class SimpleFeedForwards(val s: Double, val v: Double, val a: Double) {
    DRIVE(0.01, 0.4, 0.2)
}

enum class PIDs(val p: Double, val i: Double, val d: Double) {
    CLIMBER(0.0, 0.0, 0.0),
    ACTUATOR(0.0, 0.0, 0.0),
    SHOOTER(0.0, 0.0, 0.0),
    DRIVE(3.0, 0.0, 0.0)
}