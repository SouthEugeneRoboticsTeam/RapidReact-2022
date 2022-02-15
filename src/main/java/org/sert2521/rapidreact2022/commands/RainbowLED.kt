package org.sert2521.rapidreact2022.commands

import edu.wpi.first.wpilibj2.command.CommandBase
import org.sert2521.rapidreact2022.LED_LENGTH
import org.sert2521.rapidreact2022.subsytems.LEDStrip
import java.lang.System.currentTimeMillis

class RainbowLED : CommandBase() {
    init {
        addRequirements(LEDStrip)
    }

    override fun execute() {
        for (i in 0 until LED_LENGTH) {
            LEDStrip.setLEDHSV(i, ((((i + (currentTimeMillis() / 100)) % LED_LENGTH).toDouble() / LED_LENGTH) * 255).toInt(), 255, 128)
        }

        LEDStrip.update()
    }

    override fun end(interrupted: Boolean) {
        LEDStrip.reset()
    }
}
