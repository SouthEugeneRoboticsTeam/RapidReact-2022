package org.sert2521.rapidreact2022.subsytems

import edu.wpi.first.wpilibj.AddressableLED
import edu.wpi.first.wpilibj.AddressableLEDBuffer
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.rapidreact2022.LED_LENGTH
import org.sert2521.rapidreact2022.PWMS
import org.sert2521.rapidreact2022.commands.SpiralLED
import java.lang.System.currentTimeMillis

object LEDStrip : SubsystemBase() {
    private val addressableLED = AddressableLED(PWMS.LED.id)
    private var buffer = AddressableLEDBuffer(LED_LENGTH)

    init {
        addressableLED.setLength(LED_LENGTH)
        addressableLED.start()

        reset()

        defaultCommand = SpiralLED()
    }

    fun setLEDRGB(i: Int, r: Int, g: Int, b: Int) {
        buffer.setRGB(i, r, g, b)
    }

    fun setLEDHSV(i: Int, h: Int, s: Int, v: Int) {
        buffer.setHSV(i, h, s, v)
    }

    fun cycle(frequency: Double) {
        val newBuffer = AddressableLEDBuffer(LED_LENGTH)
        for (i in 0 until LED_LENGTH) {
            newBuffer.setLED(i, buffer.getLED(((i + ((currentTimeMillis() * frequency) / 1000)) % LED_LENGTH).toInt()))
        }

        addressableLED.setData(newBuffer)
    }
    fun update() {
        addressableLED.setData(buffer)
    }

    fun reset() {
        buffer = AddressableLEDBuffer(LED_LENGTH)
        update()
    }
}