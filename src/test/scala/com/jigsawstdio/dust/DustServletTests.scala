package com.jigsawstdio.dust

import org.scalatra.test.scalatest._

class DustServletTests extends ScalatraFunSuite {

  addServlet(classOf[DustServlet], "/*")

  test("GET / on DustServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
