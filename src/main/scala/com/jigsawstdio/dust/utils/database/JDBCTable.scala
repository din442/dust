package com.jigsawstudio.dust.utils.database

import java.sql.ResultSet
import com.jigsawstdio.dust.utils.conversions.CaseReflections._

trait JDBCTable[T] {
  def apply(m: Map[String, Any])(implicit T: Manifest[T]) = caseFromMap[T](m)
}
