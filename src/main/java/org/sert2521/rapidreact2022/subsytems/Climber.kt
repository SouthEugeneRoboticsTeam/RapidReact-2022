package org.sert2521.rapidreact2022.subsytems

import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.LimitSwitches
import org.sert2521.rapidreact2022.Motors
import org.sert2521.rapidreact2022.climbSpeed

//Add encoders
object Climber : SubsystemBase() {
    private val leftStaticClimbMotor = PWMSparkMax(Motors.LEFT_STATIC_CLIMBER.id)
    private val rightStaticClimbMotor = PWMSparkMax(Motors.RIGHT_STATIC_CLIMBER.id)

    private val leftVariableClimbMotor = PWMSparkMax(Motors.LEFT_VARIABLE_CLIMBER.id)
    private val rightVariableClimbMotor = PWMSparkMax(Motors.RIGHT_VARIABLE_CLIMBER.id)

    private val leftVariableActuator = PWMSparkMax(Motors.LEFT_VARIABLE_ACTUATOR.id)
    private val rightVariableActuator = PWMSparkMax(Motors.RIGHT_VARIABLE_ACTUATOR.id)

    private val leftStaticDownLimitSwitch = DigitalInput(LimitSwitches.LEFT_STATIC_CLIMBER_DOWN.id)
    private val leftStaticUpLimitSwitch = DigitalInput(LimitSwitches.LEFT_STATIC_CLIMBER_UP.id)

    private val rightStaticDownLimitSwitch = DigitalInput(LimitSwitches.RIGHT_STATIC_CLIMBER_DOWN.id)
    private val rightStaticUpLimitSwitch = DigitalInput(LimitSwitches.RIGHT_STATIC_CLIMBER_UP.id)

    private val leftVariableDownLimitSwitch = DigitalInput(LimitSwitches.LEFT_VARIABLE_CLIMBER_DOWN.id)
    private val leftVariableUpLimitSwitch = DigitalInput(LimitSwitches.LEFT_VARIABLE_CLIMBER_UP.id)

    private val rightVariableDownLimitSwitch = DigitalInput(LimitSwitches.RIGHT_VARIABLE_CLIMBER_DOWN.id)
    private val rightVariableUpLimitSwitch = DigitalInput(LimitSwitches.RIGHT_VARIABLE_CLIMBER_UP.id)

    init {
        leftStaticClimbMotor.inverted = Motors.LEFT_STATIC_CLIMBER.reversed
        rightStaticClimbMotor.inverted = Motors.RIGHT_STATIC_CLIMBER.reversed

        leftVariableClimbMotor.inverted = Motors.LEFT_VARIABLE_CLIMBER.reversed
        rightVariableClimbMotor.inverted = Motors.RIGHT_VARIABLE_CLIMBER.reversed

        leftVariableActuator.inverted = Motors.LEFT_VARIABLE_ACTUATOR.reversed
        rightVariableActuator.inverted = Motors.RIGHT_VARIABLE_ACTUATOR.reversed
    }

    fun raiseStatic() {
        if(leftStaticUpLimitSwitch.get()) {
            leftStaticClimbMotor.set(0.0)
        }else{
            leftStaticClimbMotor.set(climbSpeed)
        }

        if(rightStaticUpLimitSwitch.get()) {
            rightStaticClimbMotor.set(0.0)
        }else{
            rightStaticClimbMotor.set(climbSpeed)
        }
    }

    fun lowerStatic() {
        if(leftStaticDownLimitSwitch.get()) {
            leftStaticClimbMotor.set(0.0)
        }else{
            leftStaticClimbMotor.set(-climbSpeed)
        }

        if(rightStaticDownLimitSwitch.get()) {
            rightStaticClimbMotor.set(0.0)
        }else{
            rightStaticClimbMotor.set(-climbSpeed)
        }
    }

    fun raiseVariable() {
        if(leftVariableUpLimitSwitch.get()) {
            leftStaticClimbMotor.set(0.0)
        }else{
            leftStaticClimbMotor.set(climbSpeed)
        }

        if(rightVariableUpLimitSwitch.get()) {
            rightStaticClimbMotor.set(0.0)
        }else{
            rightStaticClimbMotor.set(climbSpeed)
        }
    }

    fun lowerVariable() {
        if(leftVariableDownLimitSwitch.get()) {
            leftVariableClimbMotor.set(0.0)
        }else{
            leftVariableClimbMotor.set(-climbSpeed)
        }

        if(rightVariableDownLimitSwitch.get()) {
            rightVariableClimbMotor.set(0.0)
        }else{
            rightVariableClimbMotor.set(-climbSpeed)
        }
    }
}