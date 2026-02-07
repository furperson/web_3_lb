package ru.web_lab_3

object AreaCheck {
  def check(x: Double, y: Double, r: Double): Boolean = {
    if (x >= 0 && y >= 0) {
      x <= r && y <= r
    } else if (x <= 0 && y >= 0) {
      y <= x + (r / 2.0)
    } else if (x <= 0 && y <= 0) {
      (x * x + y * y) <= (r / 2.0) * (r / 2.0)
    } else {
      false
    }
  }
}