package ru.web_lab_3

import jakarta.persistence._
import scala.beans.BeanProperty
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

@Entity
@Table(name = "results")
class Result(
              @BeanProperty var x: Double,
              @BeanProperty var y: Double,
              @BeanProperty var r: Double,
              @BeanProperty var hit: Boolean,
              @BeanProperty var executionTime: String
            ) extends Serializable {


  def this() = this(0.0, 0.0, 0.0, false, "")

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty var id: Long = _
  @BeanProperty var queryTime: String = new SimpleDateFormat("HH:mm:ss").format(new Date())
}