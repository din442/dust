package com.jigsawstdio.dust

import org.scalatra._

class DustServlet extends ScalatraServlet {

  get("/") {
    views.html.index()
  }

}
