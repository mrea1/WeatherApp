package com.mrea.weatherapp

import org.junit.Assert.assertEquals

inline fun <reified T: Any?> T.assertEquals(expected: T) = assertEquals(expected, this)
fun String.assertEmpty() = assertEquals("", this)
fun Boolean.assertFalse() = org.junit.Assert.assertFalse(this)
fun Boolean.assertTrue() = org.junit.Assert.assertTrue(this)
