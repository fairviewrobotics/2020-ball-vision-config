import java.awt.BorderLayout
import javax.swing.*

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableEntry
import edu.wpi.first.networktables.NetworkTableInstance
import java.awt.GridLayout
import java.lang.Integer.parseInt


/**
 * Application to receive and set HSV values for ball vision.
 */


class App {
    // GUI elements
    private val layout = GridLayout(5, 3)
    private val frame = JFrame("Ball Vision Config")
    private val recvButton = JButton("Receive HSV")
    private val confirmButton = JButton("Confirm HSV")
    private val values = JLabel("H: 0     S: 0     V: 0", SwingConstants.CENTER)

    private val h0field = JTextField("0.0")
    private val s0field = JTextField("0.0")
    private val v0field = JTextField("0.0")
    private val h1field = JTextField("0.0")
    private val s1field = JTextField("0.0")
    private val v1field = JTextField("0.0")

    // field labels
    private val h0label = JLabel("Hue Min:")
    private val s0label = JLabel("Hue Max:")
    private val v0label = JLabel("Sat Min:")
    private val h1label = JLabel("Sat Max:")
    private val s1label = JLabel("Val Min:")
    private val v1label = JLabel("Val Max:")


    // network tables shit
    private val inst = NetworkTableInstance.getDefault()
    private var table = inst.getTable("ball-vision")
    private var h0 = table.getEntry("hLow")
    private var s0 = table.getEntry("sLow")
    private var v0 = table.getEntry("vLow")
    private var h1 = table.getEntry("hHigh")
    private var s1 = table.getEntry("sHigh")
    private var v1 = table.getEntry("vHigh")

    private var currentH = table.getEntry("currentH")
    private var currentS = table.getEntry("currentS")
    private var currentV = table.getEntry("currentV")



    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            App()
        }
    }


    init {
        frame.layout = layout
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(1000, 300)

        // row 1
        frame.contentPane.add(recvButton)
        frame.contentPane.add(values)
        frame.contentPane.add(confirmButton)

        // row 2
        frame.contentPane.add(h0label)
        frame.contentPane.add(s0label)
        frame.contentPane.add(v0label)

        // row 3
        frame.contentPane.add(h0field)
        frame.contentPane.add(s0field)
        frame.contentPane.add(v0field)

        // row 4
        frame.contentPane.add(h1label)
        frame.contentPane.add(s1label)
        frame.contentPane.add(v1label)

        // row 5
        frame.contentPane.add(h1field)
        frame.contentPane.add(s1field)
        frame.contentPane.add(v1field)

        frame.isVisible = true

        // bind buttons
        recvButton.addActionListener { recvValues() }
        confirmButton.addActionListener { confirmValues() }

        table = inst.getTable("ball-vision")
    }


    private fun recvValues() {
        values.text = "H: ${currentH.getNumber(0)}     S: ${currentS.getNumber(0)}     V: ${currentV.getNumber(0)}"
    }


    private fun getLabelTextInt(src: JTextField): Int {
        return try {
            parseInt(src.text)
        } catch (e: java.lang.NumberFormatException) {
            0
        }
    }


    private fun confirmValues() {
        h0.setNumber(getLabelTextInt(h0field))
        s0.setNumber(getLabelTextInt(s0field))
        v0.setNumber(getLabelTextInt(v0field))

        h1.setNumber(getLabelTextInt(h1field))
        s1.setNumber(getLabelTextInt(s1field))
        v1.setNumber(getLabelTextInt(v1field))
    }
}