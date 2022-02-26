package org.sert2521.rapidreact2022.subsytems

import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.revrobotics.CANSparkMax
import edu.wpi.first.wpilibj.AnalogPotentiometer
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.Servo
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.*
import org.sert2521.rapidreact2022.commands.StartClimber
import java.lang.System.currentTimeMillis

enum class LockStates {
    LOCKED,
    UNLOCKED,
    NEITHER
}

object Climber : SubsystemBase() {
    private val staticClimberMotor = CANSparkMax(Sparks.STATIC_CLIMBER.id, Sparks.STATIC_CLIMBER.type)
    private val variableClimberMotor = CANSparkMax(Sparks.VARIABLE_CLIMBER.id, Sparks.VARIABLE_CLIMBER.type)
    private val variableActuator = WPI_TalonSRX(Talons.VARIABLE_ACTUATOR.id)

    private var staticGoal = 0.0
    private var variableGoal = 0.0
    private var angleGoal = 0.0

    private val staticDownLimitSwitch = DigitalInput(OnOffs.STATIC_CLIMBER_DOWN.id)
    private val variableDownLimitSwitch = DigitalInput(OnOffs.VARIABLE_CLIMBER_DOWN.id)

    private val potentiometer = AnalogPotentiometer(Potentiometers.VARIABLE_CLIMBER_ANGLE.id, Potentiometers.VARIABLE_CLIMBER_ANGLE.maxAngle, Potentiometers.VARIABLE_CLIMBER_ANGLE.zeroAngle)

    private val servoStatic = try { Servo(PWMS.SERVO_STATIC.id) } catch (e: Exception) { null }
    private val servoVariable = try { Servo(PWMS.SERVO_VARIABLE.id) } catch (e: Exception) { null }

    private var staticLocked = LockStates.NEITHER
    private var staticLockedUpdate = 0L
    private var variableLocked = LockStates.NEITHER
    private var variableLockedUpdate = 0L

    private var forceLocked = false
    private val startTime = currentTimeMillis()

    private val startClimber = StartClimber()

    init {
        staticClimberMotor.inverted = Sparks.STATIC_CLIMBER.reversed
        variableClimberMotor.inverted = Sparks.VARIABLE_CLIMBER.reversed
        variableActuator.inverted = Talons.VARIABLE_ACTUATOR.reversed

        staticClimberMotor.idleMode = CANSparkMax.IdleMode.kBrake
        variableClimberMotor.idleMode = CANSparkMax.IdleMode.kBrake
        variableActuator.setNeutralMode(NeutralMode.Brake)

        staticClimberMotor.encoder.positionConversionFactor = SparkEncoders.STATIC_CLIMBER.conversionFactor
        variableClimberMotor.encoder.positionConversionFactor = SparkEncoders.VARIABLE_CLIMBER.conversionFactor

        staticClimberMotor.pidController.p = robotPreferences.climberPIDVel[0]
        staticClimberMotor.pidController.i = robotPreferences.climberPIDVel[1]
        staticClimberMotor.pidController.d = robotPreferences.climberPIDVel[2]

        variableClimberMotor.pidController.p = robotPreferences.climberPIDVel[0]
        variableClimberMotor.pidController.i = robotPreferences.climberPIDVel[1]
        variableClimberMotor.pidController.d = robotPreferences.climberPIDVel[2]
    }

    fun onEnable() {
        staticLocked = LockStates.NEITHER
        staticLockedUpdate = 0L
        variableLocked = LockStates.NEITHER
        variableLockedUpdate = 0L

        staticClimberMotor.encoder.position = Double.POSITIVE_INFINITY
        variableClimberMotor.encoder.position = Double.POSITIVE_INFINITY

        forceLocked = false
        startClimber.schedule(false)
    }

    val staticHeight
        get() = staticClimberMotor.encoder.position

    val variableHeight
        get() = variableClimberMotor.encoder.position

    val variableAngle
        get() = potentiometer.get()

    fun isAtBottomStatic(): Boolean {
        return !staticDownLimitSwitch.get()
    }

    fun isAtBottomVariable(): Boolean {
        return !variableDownLimitSwitch.get()
    }

    fun isStaticLocked(): LockStates {
        return if(currentTimeMillis() - staticLockedUpdate >= LOCK_TIME_STATIC * 1000) {
            staticLocked
        } else {
            LockStates.NEITHER
        }
    }

    fun isVariableLocked(): LockStates {
        return if(currentTimeMillis() - variableLockedUpdate >= LOCK_TIME_VARIABLE * 1000) {
            variableLocked
        } else {
            LockStates.NEITHER
        }
    }

    //Re-add
    fun forceLock() {
        /*forceLocked = true
        stop()
        lock()*/
    }

    override fun periodic() {
        if(currentTimeMillis() - startTime >= (MATCH_TIME - FAILSAFE_TIME) * 1000) {
            forceLock()
        }

        if(isAtBottomStatic()) {
            staticClimberMotor.encoder.position = MIN_CLIMBER_HEIGHT
        }

        if(isAtBottomVariable()) {
            variableClimberMotor.encoder.position = MIN_CLIMBER_HEIGHT
        }

        staticUpdate()
        variableUpdate()
        angleUpdate()
    }

    fun setLockStatic(lock: LockStates) {
        if(!forceLocked && lock != LockStates.NEITHER) {
            if(lock == LockStates.LOCKED) {
                servoStatic?.set(SERVO_LOCK_STATIC)
            } else {
                servoStatic?.set(SERVO_UNLOCK_STATIC)
            }

            if(staticLocked != lock) {
                staticLockedUpdate = currentTimeMillis()
            }
            staticLocked = lock
        }
    }

    fun setLockVariable(lock: LockStates) {
        if(!forceLocked && lock != LockStates.NEITHER) {
            if(lock == LockStates.LOCKED) {
                servoVariable?.set(SERVO_LOCK_VARIABLE)
            } else {
                servoVariable?.set(SERVO_UNLOCK_VARIABLE)
            }

            if(variableLocked != lock) {
                variableLockedUpdate = currentTimeMillis()
            }
            variableLocked = lock
        }
    }

    fun setStaticSpeed(amount: Double) {
        staticGoal = amount
        staticUpdate()
    }

    fun setVariableSpeed(amount: Double) {
        variableGoal = amount
        variableUpdate()
    }

    fun setAngleSpeed(amount: Double) {
        angleGoal = amount
        angleUpdate()
    }

    private fun staticUpdate() {
        var offset = 0.0
        //will release some pressure from birds if trying to unlock
        //if changing to unlocked
        if(isStaticLocked() == LockStates.NEITHER && staticLocked == LockStates.UNLOCKED) {
            offset -= UNSTICK_POWER
        }

        if(isStaticLocked() == LockStates.LOCKED) {
            staticClimberMotor.pidController.setReference(0.0, CANSparkMax.ControlType.kVelocity)
        } else {
            if(isStaticLocked() == LockStates.NEITHER || (staticGoal < 0.0 && isAtBottomStatic()) || (staticGoal > 0.0 && staticHeight >= MAX_CLIMBER_HEIGHT)) {
                staticClimberMotor.pidController.setReference(offset, CANSparkMax.ControlType.kVelocity)
            } else {
                staticClimberMotor.pidController.setReference(staticGoal + offset, CANSparkMax.ControlType.kVelocity)
            }
        }
    }

    private fun variableUpdate() {
        var unstick = 0.0
        if(isVariableLocked() == LockStates.NEITHER && variableLocked == LockStates.UNLOCKED) {
            unstick -= UNSTICK_POWER
        }

        if(isVariableLocked() == LockStates.LOCKED) {
            variableClimberMotor.pidController.setReference(0.0, CANSparkMax.ControlType.kVelocity)
        } else {
            if(isVariableLocked() == LockStates.NEITHER || (variableGoal < 0.0 && isAtBottomVariable()) || (variableGoal > 0.0 && variableHeight >= MAX_CLIMBER_HEIGHT)) {
                variableClimberMotor.pidController.setReference(unstick, CANSparkMax.ControlType.kVelocity)
            } else {
                variableClimberMotor.pidController.setReference(variableGoal + unstick, CANSparkMax.ControlType.kVelocity)
            }
        }
    }

    private fun angleUpdate() {
        if((angleGoal < 0.0 && variableAngle <= MIN_CLIMBER_ANGLE) || (angleGoal > 0.0 && variableAngle >= MAX_CLIMBER_ANGLE)) {
            variableActuator.set(0.0)
        } else {
            variableActuator.set(angleGoal)
        }
    }

    fun lock() {
        setLockStatic(LockStates.LOCKED)
        setLockVariable(LockStates.LOCKED)
    }

    fun unlock() {
        setLockStatic(LockStates.UNLOCKED)
        setLockVariable(LockStates.UNLOCKED)
    }

    fun stop() {
        setStaticSpeed(0.0)
        setVariableSpeed(0.0)
        setAngleSpeed(0.0)
    }
}