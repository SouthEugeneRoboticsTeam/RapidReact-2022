package org.sert2521.rapidreact2022

const val TICKS_PER_ROTATION = 4096

const val MOTOR_SPINS_PER_WHEEL_SPIN = 10.71
const val WHEEL_CIRCUMFERENCE = 0.1524
const val WHEEL_DISTANCE_PER_PULSE = (WHEEL_CIRCUMFERENCE / MOTOR_SPINS_PER_WHEEL_SPIN) / TICKS_PER_ROTATION

const val PRIMARY_CONTROLLER_ID = 0

enum class Motors(val id: Int, val encoderDistancePerPulse: Double) {
    FRONT_LEFT(0, WHEEL_DISTANCE_PER_PULSE),
    BACK_LEFT(0, WHEEL_DISTANCE_PER_PULSE),
    FRONT_RIGHT(0, WHEEL_DISTANCE_PER_PULSE),
    BACK_RIGHT(0, WHEEL_DISTANCE_PER_PULSE)
}