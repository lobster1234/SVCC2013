package controllers

import models.Person
import models.PersonModel
import play.Logger
import play.api.libs.functional.syntax.functionalCanBuildApplicative
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.JsError
import play.api.libs.json.JsSuccess
import play.api.libs.json.Json
import play.api.libs.json.Reads.functorReads
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc.Cookie
import java.util.Date

object SimpleAPI extends Controller {

  implicit val personWrites = Json.writes[Person]
  implicit val personReads = Json.reads[Person]

  def getAll = Action {
    val all = PersonModel.getAll
    Ok(Json.toJson(all))
  }

  /**
   * We specify the body parser just in case the client does not set the 
   * Content-Type header
   */
  def add = Action(parse.json) { implicit request =>
    Logger.info(s"Body is $request.body") //request.body is going to be a JsValue as we passed in the JSON BodyParser
    request.body.validate(personReads) match {
      case x: JsError =>
        BadRequest
      case x: JsSuccess[Person] =>
        PersonModel.add(x.get)
        Created
    }
  }

  /**
   * Showing an example of setting headers and cookies with the response
   */
  def getByFirstName(first: String) = Action {
    val list = PersonModel.getByFirstName(first)
    if (list.isEmpty) NotFound else Ok(Json.toJson(PersonModel.getByFirstName(first))).withHeaders("foo"->"bar").withCookies(Cookie("date",new Date().toString))
  }

  def getByLastName(last: String) = Action {
    val list = PersonModel.getByLastName(last)
    if (list.isEmpty) NotFound else Ok(Json.toJson(PersonModel.getByLastName(last))).as(HTML)
  }

  def getbyZip(zip: Int) = Action {
    val list = PersonModel.getByZip(zip)
    if (list.isEmpty) NotFound else Ok(Json.toJson(PersonModel.getByZip(zip)))
  }
  
}

