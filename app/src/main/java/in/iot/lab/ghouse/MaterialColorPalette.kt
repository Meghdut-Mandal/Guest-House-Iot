package `in`.iot.lab.ghouse

import android.graphics.Color
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class MaterialColorPalette(primary: Int) {
    companion object {
        const val RED_500 = -0xbbcca
        const val PINK_500 = -0x16e19d
        const val PURPLE_500 = -0x63d850
        const val DEEP_PURPLE_500 = -0x98c549
        const val INDIGO_500 = -0xc0ae4b
        const val BLUE_500 = -0xde690d
        const val LIGHT_BLUE_500 = -0xfc560c
        const val CYAN_500 = -0xff432c
        const val TEAL_500 = -0xff6978
        const val GREEN_500 = -0xb350b0
        const val LIGHT_GREEN_500 = -0x743cb6
        const val LIME_500 = -0x3223c7
        const val YELLOW_500 = -0x14c5
        const val AMBER_500 = -0x3ef9
        const val ORANGE_500 = -0x6800
        const val DEEP_ORANGE_500 = -0xa8de
        const val BROWN_500 = -0x86aab8
        const val GREY_500 = -0x616162
        const val BLUE_GREY_500 = -0x9f8275
        private val MATERIAL_PALETTES: MutableList<MaterialColorPalette>
        fun getRandomColor(key: String?): Int =
            MATERIAL_PALETTES[nextInt(MATERIAL_PALETTES.size)].getColor(key)

        /**
         * Lighten or darken a color
         *
         * @param color   color value
         * @param percent -1.0 to 1.0
         * @return new shaded color
         * @see .shadeColor
         */
        fun shadeColor(color: Int, percent: Double): Int {
            return shadeColor(
                String.format("#%06X", 0xFFFFFF and color),
                percent
            ) // ignores alpha channel
        }

        /**
         * Lighten or darken a color
         *
         * @param color   7 character string representing the color.
         * @param percent -1.0 to 1.0
         * @return new shaded color
         * @see .shadeColor
         */
        fun shadeColor(color: String, percent: Double): Int {
            // based off http://stackoverflow.com/a/13542669/1048340
            val f = color.substring(1).toLong(16)
            val t: Double = if (percent < 0) 0.0 else 255.toDouble()
            val p = if (percent < 0) percent * -1 else percent
            val R = f shr 16
            val G = f shr 8 and 0x00FF
            val B = f and 0x0000FF
            val red = (Math.round((t - R) * p) + R).toInt()
            val green = (Math.round((t - G) * p) + G).toInt()
            val blue = (Math.round((t - B) * p) + B).toInt()
            return Color.rgb(red, green, blue)
        }

        init {
            MATERIAL_PALETTES = arrayListOf()
            MATERIAL_PALETTES.add(MaterialColorPalette(RED_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(PINK_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(PURPLE_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(DEEP_PURPLE_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(INDIGO_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(BLUE_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(LIGHT_BLUE_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(CYAN_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(TEAL_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(GREEN_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(LIGHT_GREEN_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(LIME_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(YELLOW_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(AMBER_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(ORANGE_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(DEEP_ORANGE_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(BROWN_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(GREY_500))
            MATERIAL_PALETTES.add(MaterialColorPalette(BLUE_GREY_500))
        }
    }

    private val palette = HashMap<String?, Int>()
    fun getColor(key: String?): Int = palette[key]!!

    fun putColor(key: String?, color: Int) {
        palette[key] = color
    }

    /**
     * @param primary the 500 color
     */
    init {
        palette["50"] = shadeColor(primary, 0.9)
        palette["100"] = shadeColor(primary, 0.7)
        palette["200"] = shadeColor(primary, 0.5)
        palette["300"] = shadeColor(primary, 0.333)
        palette["400"] = shadeColor(primary, 0.166)
        palette["500"] = primary
        palette["600"] = shadeColor(primary, -0.125)
        palette["700"] = shadeColor(primary, -0.25)
        palette["800"] = shadeColor(primary, -0.375)
        palette["900"] = shadeColor(primary, -0.5)
        palette["A100"] = shadeColor(primary, 0.7)
        palette["A200"] = shadeColor(primary, 0.5)
        palette["A400"] = shadeColor(primary, 0.166)
        palette["A700"] = shadeColor(primary, -0.25)
    }
}