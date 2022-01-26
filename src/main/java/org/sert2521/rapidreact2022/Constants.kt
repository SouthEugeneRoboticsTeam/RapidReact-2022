package org.sert2521.rapidreact2022

import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.CounterBase
import edu.wpi.first.wpilibj.SPI

const val TICKS_PER_ROTATION = 256

const val MOTOR_SPINS_PER_WHEEL_SPIN = 10.71
//6 inches is 0.1524 meters
const val WHEEL_CIRCUMFERENCE = 0.1524

const val TRACK_WIDTH = 0.0

const val PRIMARY_CONTROLLER_ID = 0

val GYRO_PORT = SPI.Port.kMXP

enum class Talons(val id: Int, val reversed: Boolean) {
    FRONT_LEFT_DRIVE(0, false),
    BACK_LEFT_DRIVE(0, false),
    FRONT_RIGHT_DRIVE(0, true),
    BACK_RIGHT_DRIVE(0, true)
}

enum class Sparks(val id: Int, val type: CANSparkMaxLowLevel.MotorType, val reversed: Boolean) {
    SHOOTER(0, CANSparkMaxLowLevel.MotorType.kBrushless, false)
}

enum class PIDControllers(val p: Double, val i: Double, val d: Double) {
    SHOOTER(0.1, 0.1, 0.1)
}

enum class Encoders(val idA: Int, val idB: Int, val reversed: Boolean, val encodingType: CounterBase.EncodingType, val encoderDistancePerPulse: Double, val maxPeriod: Double, val minRate: Double, val samples: Int) {
    RIGHT_DRIVE(0, 0, false, CounterBase.EncodingType.k2X, (WHEEL_CIRCUMFERENCE / MOTOR_SPINS_PER_WHEEL_SPIN) / TICKS_PER_ROTATION, 0.1, 10.0, 5),
    LEFT_DRIVE(0, 0, false, CounterBase.EncodingType.k2X, (WHEEL_CIRCUMFERENCE / MOTOR_SPINS_PER_WHEEL_SPIN) / TICKS_PER_ROTATION, 0.1, 10.0, 5)
}