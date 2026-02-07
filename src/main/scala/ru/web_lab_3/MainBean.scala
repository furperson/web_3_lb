package ru.web_lab_3

import jakarta.inject.Named
import jakarta.faces.context.FacesContext

import scala.beans.BeanProperty
import java.io.Serializable
import java.util.{ArrayList, List => JList}
import scala.jdk.CollectionConverters._
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped

@Named("mainBean")
@ApplicationScoped
class MainBean extends Serializable {

  @BeanProperty var x: Double = 0.0
  @BeanProperty var y: Double = 0.0
  @BeanProperty var r: Double = 1.0
  @BeanProperty var results: JList[Result] = new ArrayList[Result]()

  @PostConstruct
  def init(): Unit = {
    loadResults()
  }

  def loadResults(): Unit = {
    val session = HibernateUtil.getSessionFactory.openSession()
    try {
      val query = session.createQuery("FROM Result ORDER BY id DESC", classOf[Result])
      results = query.getResultList
    } finally {
      session.close()
    }
  }

  def addFormResult(): Unit = {
    processResult(x, y, r)


  }

  def addGraphResult(): Unit = {
    val params = FacesContext.getCurrentInstance.getExternalContext.getRequestParameterMap
    try {
      val graphX = params.get("x").toDouble
      val graphY = params.get("y").toDouble
      val graphR = params.get("r").toDouble
      processResult(graphX, graphY, graphR)
    } catch {
      case e: NumberFormatException => e.printStackTrace()
    }
  }

  private def processResult(curX: Double, curY: Double, curR: Double): Unit = {
    val startTime = System.nanoTime()

    val isHit = AreaCheck.check(curX, curY, curR)

    val endTime = System.nanoTime()
    val durationMks = (endTime - startTime) / 1000.0
    val execTimeStr = f"$durationMks%.2f mks"

    val result = new Result(curX, curY, curR, isHit, execTimeStr)


    val session = HibernateUtil.getSessionFactory.openSession()
    val transaction = session.beginTransaction()
    try {
      session.persist(result)
      transaction.commit()
      results.add(0, result)
    } catch {
      case e: Exception =>
        if (transaction.isActive) transaction.rollback()
        e.printStackTrace()
    } finally {
      session.close()
    }
  }

  def clearResults(): Unit = {
    val session = HibernateUtil.getSessionFactory.openSession()
    val transaction = session.beginTransaction()
    try {
      session.createMutationQuery("DELETE FROM Result").executeUpdate()
      transaction.commit()
      results.clear()
    } finally {
      session.close()
    }
  }

  def getResultsJson: String = {
    results.asScala.map(r =>
      s"""{"x":${r.x}, "y":${r.y}, "r":${r.r}, "hit":${r.hit}}"""
    ).mkString("[", ",", "]")
  }
}