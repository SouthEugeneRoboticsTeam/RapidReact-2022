package org.sert2521.rapidreact2022

import edu.wpi.first.wpilibj.CounterBase
import edu.wpi.first.wpilibj.SPI

const val TICKS_PER_ROTATION = 256.0

const val MOTOR_SPINS_PER_WHEEL_SPIN = 10.71
//6 inches is 0.1524 meters
const val WHEEL_CIRCUMFERENCE = 0.1524

const val TRACK_WIDTH = 0.0

const val CLIMBER_HEIGHT_PER_ROTATION = 0.0

const val MID_STATIC_HEIGHT = 0.0

const val PRIMARY_CONTROLLER_ID = 0

val GYRO_PORT = SPI.Port.kMXP

enum class Motors(val id: Int, val reversed: Boolean) {
    FRONT_LEFT_DRIVE(0, false),
    BACK_LEFT_DRIVE(0, false),
    FRONT_RIGHT_DRIVE(0, true),
    BACK_RIGHT_DRIVE(0, true),

    STATIC_CLIMBER(0, false),
    VARIABLE_CLIMBER(0, false),
    VARIABLE_ACTUATOR(0, false)
}

enum class Encoders(val idA: Int, val idB: Int, val reversed: Boolean, val encodingType: CounterBase.EncodingType, val encoderDistancePerPulse: Double, val maxPeriod: Double, val minRate: Double, val samples: Int) {
    LEFT_DRIVE(0, 0, false, CounterBase.EncodingType.k2X, (WHEEL_CIRCUMFERENCE / MOTOR_SPINS_PER_WHEEL_SPIN) / TICKS_PER_ROTATION, 0.1, 10.0, 5),
    RIGHT_DRIVE(0, 0, false, CounterBase.EncodingType.k2X, (WHEEL_CIRCUMFERENCE / MOTOR_SPINS_PER_WHEEL_SPIN) / TICKS_PER_ROTATION, 0.1, 10.0, 5),
    STATIC_CLIMBER(0, 0, false, CounterBase.EncodingType.k2X, CLIMBER_HEIGHT_PER_ROTATION / TICKS_PER_ROTATION, 0.1, 10.0, 5),
    VARIABLE_CLIMBER(0, 0, false, CounterBase.EncodingType.k2X, CLIMBER_HEIGHT_PER_ROTATION / TICKS_PER_ROTATION, 0.1, 10.0, 5),
}

enum class LimitSwitches(val id: Int) {
    STATIC_CLIMBER_DOWN(0),
    STATIC_CLIMBER_UP(0),
    VARIABLE_CLIMBER_DOWN(0),
    VARIABLE_CLIMBER_UP(0)
}

enum class Potentiometers(val id: Int, val maxAngle: Double, val zeroAngle: Double) {
    VARIABLE_CLIMBER_ANGLE(0, 0.0, 0.0)
}