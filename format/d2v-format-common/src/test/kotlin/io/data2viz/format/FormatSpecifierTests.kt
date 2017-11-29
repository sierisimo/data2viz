@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import io.data2viz.test.shouldThrow
import kotlin.test.Test

class FormatSpecifierTests : TestBase() {

    @Test fun formatSpecifier_throws_an_error_for_invalid_formats () {
        shouldThrow<IllegalArgumentException> { specify("foo") }
        shouldThrow<IllegalArgumentException> { specify(".-2s") }
        shouldThrow<IllegalArgumentException> { specify(".f") }
    }

    /*"formatSpecifier(specifier) returns an instanceof formatSpecifier" {
        var s = Locale().formatSpecifier("")
        s instanceof Locale().formatSpecifier, true)
    }*/

    @Test fun formatSpecifier__has_the_expected_defaults () {
        val s = specify("")
        s.fill shouldBe " "
        s.align shouldBe ">"
        s.sign shouldBe "-"
        s.symbol shouldBe ""
        s.zero shouldBe false
        s.width shouldBe null
        s.groupSeparation shouldBe false
        s.precision shouldBe null
        s.type shouldBe ""
    }

    @Test fun formatSpecifier_specifier_uses_the_none_type_for_unknown_types () {
        specify("q").type shouldBe ""
        specify("S").type shouldBe ""
    }

    @Test fun formatSpecifier_n_is_an_alias_for_g() {
        val s = specify("n")
        s.groupSeparation shouldBe true
        s.type shouldBe "g"
    }

    @Test fun formatSpecifier_0_is_an_alias_for_0() {
        val s = specify("0")
        s.zero shouldBe true
        s.fill shouldBe "0"
        s.align shouldBe "="
    }

    @Test fun formatSpecifier_specifier_toString_reflects_current_field_values () {

        var s = specify("")
        s.fill = "_"
        s.toString() shouldBe "_>-"
        s.align = "^"
        s.toString() shouldBe "_^-"
        s.sign = "+"
        s.toString() shouldBe "_^+"
        s.symbol = "$"
        s.toString() shouldBe "_^+$"
        s.zero = true
        s.toString() shouldBe "_^+$0"
        s.width = 12
        s.toString() shouldBe "_^+$012"
        s.groupSeparation = true
        s.toString() shouldBe "_^+$012,"
        s = s.copy(precision = 2)
        s.toString() shouldBe "_^+$012,.2"
        s.type = "f"
        s.toString() shouldBe "_^+$012,.2f"
        Locale().formatter(s.toString())(42.0) shouldBe "+$0,000,042.00"
    }

    @Test fun formatSpecifier_specifier_toString_clamps_exponent_to_zero () {
        val s = specify("").copy(precision = -1)
        s.toString() shouldBe " >-.0"
    }

    @Test fun formatSpecifier_specifier_toString_clamps_width_to_one () {
        val s = specify("")
        s.width = -1
        s.toString() shouldBe " >-1"
    }


    @Test
    fun dsl(){
        formatter {
            fill = '_'
            toString() shouldBe "_>-"
            align = Align.CENTER
            toString() shouldBe "_^-"
            sign = Sign.PLUS
            toString() shouldBe "_^+"
            symbol = Symbol.CURRENCY
            toString() shouldBe "_^+$"
            zeroPadding = true
            toString() shouldBe "_^+$0"
            width = 12
            toString() shouldBe "_^+$012"
            groupSeparation = true
            toString() shouldBe "_^+$012,"
            precision = 2
            toString() shouldBe "_^+$012,.2"
            type = Type.FIXED_POINT
            toString() shouldBe "_^+$012,.2f"
        }
    }

    fun formatter(init: FormatDSL.() -> Unit){
        FormatDSL().init()
    }

}
