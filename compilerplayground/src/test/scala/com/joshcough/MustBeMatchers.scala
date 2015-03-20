package com.joshcough


import org.scalatest.matchers.MustMatchers

trait MustBeMatchers extends MustMatchers {

  implicit def anyToMustBe(a: Any) = new {
    def mustBe(b: Any) {
      a must be(b)
    }

    def must_be(b: Any) {
      a must be(b)
    }
  }
}
