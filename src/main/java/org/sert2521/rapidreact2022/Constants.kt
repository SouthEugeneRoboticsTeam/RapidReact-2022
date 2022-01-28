package org.sert2521.rapidreact2022

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
const val MOTOR_SPINS_PER_WHEEL_SPIN = 8.25

const val TRACK_WIDTH = 0.5816473

const val PRIMARY_CONTROLLER_ID = 0

val GYRO_PORT = SPI.Port.kMXP

enum class AutoPaths(val shuffleBoardName: String, val trajectory: Trajectory) {
    NOTHING("Nothing", TrajectoryGenerator.generateTrajectory(
            Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            listOf(),
            Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            TrajectoryConfig(0.5, 0.5))),
    DRIVE_FORWARD("Forward", TrajectoryGenerator.generateTrajectory(
            Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            listOf(),
            Pose2d(1.0, 0.0, Rotation2d.fromDegrees(0.0)),
            TrajectoryConfig(0.5, 0.5))),
}

enum class ClimbStatuses {
    FLOOR,
    MID,
    HIGH,
    HIGHEST
}

enum class Buttons(val id: Int) {
    INTAKE(0),
    SHOOT(0),
    CLIMB_NEXT(0)
}

enum class Motors(val id: Int, val reversed: Boolean) {
    FRONT_LEFT_DRIVE(0, false),
    BACK_LEFT_DRIVE(0, false),
    FRONT_RIGHT_DRIVE(0, true),
    BACK_RIGHT_DRIVE(0, true),
    INTAKE(0, false),
    INDEXER(0, false),
    SHOOTER(0, false),
    STATIC_CLIMBER(0, false),
    VARIABLE_CLIMBER(0, false),
    VARIABLE_ACTUATOR(0, false)
}

enum class LimitSwitches(val id: Int) {
}

enum class Potentiometers(val min: Double, val max: Double, val zero: Double) {
}

enum class Encoders(val idA: Int, val idB: Int, val reversed: Boolean, val encodingType: CounterBase.EncodingType, val encoderDistancePerPulse: Double, val maxPeriod: Double, val minRate: Double, val samples: Int) {
    RIGHT_DRIVE(0, 0, false, CounterBase.EncodingType.k2X, (WHEEL_CIRCUMFERENCE / MOTOR_SPINS_PER_WHEEL_SPIN) / THROUGH_BORE_PULSES_PER_ROTATION, 0.1, 10.0, 5),
    LEFT_DRIVE(0, 0, false, CounterBase.EncodingType.k2X, (WHEEL_CIRCUMFERENCE / MOTOR_SPINS_PER_WHEEL_SPIN) / THROUGH_BORE_PULSES_PER_ROTATION, 0.1, 10.0, 5)
}

enum class SimpleFeedForwards(val s: Double, val v: Double, val a: Double) {
}

enum class ArmFeedForwards(val s: Double, val cos: Double, val v: Double, val a: Double) {
}

enum class PIDs(val p: Double, val i: Double, val d: Double) {
}