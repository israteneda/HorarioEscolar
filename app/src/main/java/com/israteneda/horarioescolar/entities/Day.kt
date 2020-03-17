package com.israteneda.horarioescolar.entities

data class Day(
    val idx: Int,
    val name: String
) {
    companion object {

        val LUNES = Day(0,"LUNES")
        val MARTES = Day(1,"MARTES")
        val MIERCOLES = Day(2,"MIÉRCOLES")
        val JUEVES = Day(3,"JUEVES")
        val VIERBES = Day(4,"VIERNES")

        val days:List<Day> = listOf(
            Day(0,"LUNES"),
            Day(1,"MARTES"),
            Day(2,"MIÉRCOLES"),
            Day(3,"JUEVES"),
            Day(4,"VIERNES")
        )

    }
}