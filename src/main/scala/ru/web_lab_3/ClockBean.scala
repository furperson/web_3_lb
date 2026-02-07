package ru.web_lab_3

import jakarta.inject.Named
import jakarta.enterprise.context.RequestScoped
import java.util.Date
import java.time.ZoneId
import java.time.ZonedDateTime

@Named("clockBean")
@RequestScoped
class ClockBean {
  def getNow: Date = {
    val moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
    Date.from(moscowTime.toInstant())
  }
}