package ru.web_lab_3

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

object HibernateUtil {
  private val sessionFactory: SessionFactory = try {
    new Configuration().configure().buildSessionFactory()
  } catch {
    case ex: Throwable =>
      System.err.println("SMTH wrong with BD: " + ex)
      throw new ExceptionInInitializerError(ex)
  }

  def getSessionFactory: SessionFactory = sessionFactory
}