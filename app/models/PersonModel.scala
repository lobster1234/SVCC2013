package models

import scala.collection.mutable.ArrayBuffer

object PersonModel {

  /**
   * This would be your database, as Play! Applications should be all stateless
   */
  val persons = ArrayBuffer(Person("Manish", "Pandit", 94568), Person("John", "Doe", 95051), Person("My", "Neighbor", 94568))

  def add(p: Person) = persons += p

  def getAll = persons

  def getByFirstName(first: String) = persons.filter(_.first == first)

  def getByLastName(last: String) = persons.filter(_.last == last)

  def getByZip(zip: Int) = persons.filter(_.zip == zip)

}

case class Person(first: String, last: String, zip: Int)