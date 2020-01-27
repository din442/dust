package com.jigsawstdio.dust

import com.jigsawstdio.dust.utils.conversions.CaseReflections
import com.jigsawstudio.dust.model.DataSet
import org.scalatra._
import scalikejdbc._

class DustServlet extends ScalatraServlet {

  get("/") {
    contentType="text/html"
    scala.io.Source.fromFile(
      servletContext.getResource("/html/index.html").getFile
    ).mkString
  }

  get("/dataset/list") {
    contentType="application/json"

    val ds = DB readOnly { implicit session =>
      DataSet.list(100)
    }
    CaseReflections.jsonMapper.writeValueAsString(ds)
  }

}
