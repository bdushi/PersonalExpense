package al.bruno.financaime.binding.utils

import al.bruno.financaime.util.Utilities.format
import androidx.databinding.InverseMethod

object Converter {
    @InverseMethod("doubleToString")
    fun stringToDouble(quantity: Double): String {
        return format(quantity, 1)
    }
    fun doubleToString(quantity: String): Double {
        return try {
            java.lang.Double.parseDouble(quantity)
        } catch (ex: NumberFormatException) {
            0.0
        }
    }
}