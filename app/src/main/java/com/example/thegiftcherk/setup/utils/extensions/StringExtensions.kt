package com.example.thegiftcherk.setup.utils.extensions

import java.util.regex.Pattern

private val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
    "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$",
    Pattern.CASE_INSENSITIVE
)
private val VALID_PHONE_REGEX = Pattern.compile(
    "\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|\n" +
            "2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|\n" +
            "4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}\$",
    Pattern.CASE_INSENSITIVE
)

fun String.isEmail(): Boolean {
    val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(this)
    return matcher.find()
}

fun String.isPhoneNumber(): Boolean {
    val matcher = VALID_PHONE_REGEX.matcher(this)
    return matcher.find()
}

fun String.isValidDNI(): Boolean {
    var esValido = false
    var i = 0
    var caracterASCII: Int
    val letra: Char
    val miDNI: Int
    val resto: Int
    val asignacionLetra = charArrayOf(
        'T',
        'R',
        'W',
        'A',
        'G',
        'M',
        'Y',
        'F',
        'P',
        'D',
        'X',
        'B',
        'N',
        'J',
        'Z',
        'S',
        'Q',
        'V',
        'H',
        'L',
        'C',
        'K',
        'E'
    )

    if (this.length == 9 && Character.isLetter(this[8])) {

        do {
            caracterASCII = this.codePointAt(i)
            esValido = caracterASCII > 47 && caracterASCII < 58
            i++
        } while (i < this.length - 1 && esValido)
    }

    if (esValido) {
        letra = Character.toUpperCase(this[8])
        miDNI = Integer.parseInt(this.substring(0, 8))
        resto = miDNI % 23
        esValido = letra == asignacionLetra[resto]
    }

    return esValido
}

fun String.addTenths() : String {
    return if(this.toInt() <= 9) {
        String.format("0%s", this)
    } else {
        this
    }
}