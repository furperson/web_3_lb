package ru.web_lab_3
import jakarta.inject.Named
import jakarta.enterprise.context.RequestScoped
import java.util.Date
@Named("clockBean")
@RequestScoped
class ClockBean {
  def getNow: Date = new Date()
}