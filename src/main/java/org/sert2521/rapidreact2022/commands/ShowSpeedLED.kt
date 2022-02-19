package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.LED_LENGTH
import org.sert2521.rapidreact2022.robotPreferences
import org.sert2521.rapidreact2022.subsytems.LEDStrip
import org.sert2521.rapidreact2022.subsytems.Shooter
import kotlin.math.abs

class ShowSpeedLED : CommandBase() {
    init {
        addRequirements(LEDStrip)
    }

    override fun execute() {
        for (i in 0 until LED_LENGTH) {
            val percentToSpeed = abs(robotPreferences.shooterRPM - Shooter.wheelSpeed) / robotPreferences.shooterRPM
            LEDStrip.setLEDRGB(i, (percentToSpeed * 255 / 3.5).toInt(), ((1 - percentToSpeed) * 255 / 3.5).toInt(), 0)
        }

        LEDStrip.update()
    }

    override fun end(interrupted: Boolean) {
        LEDStrip.reset()
    }
}
