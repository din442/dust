package com.jigsawstudio.dust.model

import java.sql.{Connection, Timestamp}

import scalikejdbc._
import com.jigsawstdio.dust.utils.conversions.CaseReflections._

import com.jigsawstudio.dust.utils.database._


case class DataSetField(name: String, `type`: String, size: Option[Int], nullable: Option[Boolean])

case class DataSetSchema(fields: List[DataSetField])

object DataSetVersions {
  def dropSql =
    sql"""DROP TABLE IF EXISTS dataset_versions"""

  def createSql =
    sql"""
    CREATE TABLE dataset_versions (
    id integer NOT NULL,
    version integer NOT NULL,
    schema json,
    created timestamp NOT NULL,
    PRIMARY KEY(id, version));
      """


}

object DataSet extends JDBCTable[DataSet] {
  val schemaName = Some("data_catalog")
  val tableName = "datasets"

  def JList(limit: Int)(implicit connection: Connection): List[Map[String, Any]] = {
    val sql = s"select * from datasets limit ${limit}"
    JDBCQuery.queryResultsMap(sql)
  }
  def list(limit: Int)(implicit session: DBSession): List[Map[String, Any]] = {
    sql"""
          SELECT * FROM datasets limit ${limit};
      """
      .map(rs => rs.toMap())
      .toList()
      .apply()
  }
  def getByName(name: String)(implicit session: DBSession): Option[Map[String, Any]] = {
      sql"""
SELECT * FROM datasets WHERE name = ${name}
    """.map(rs => rs.toMap())
        .single
        .apply()
  }

  def dropSql =
    sql"""DROP TABLE IF EXISTS datasets"""

  def createSql =
    sql"""CREATE TABLE datasets (
         |    id SERIAL,
         |    name varchar(128) NOT NULL,
         |    type varchar(10),
         |    schema json NOT NULL,
         |    path text,
         |    ispartitioned boolean,
         |    owner varchar(128) NOT NULL,
         |    version integer,
         |    properties json,
         |    created timestamp NOT NULL,
         |    updated timestamp,
         |    PRIMARY KEY(name)
         |);""".stripMargin


}

case class DataSet(id: Option[Int],
                   name: String,
                   `type`: String,
                   schema: String,
                   path: String,
                   ispartitioned: Boolean,
                   owner: String,
                   version: Int,
                   properties: String,
                   created: Timestamp,
                   updated: Option[Timestamp]) extends DataSetType {

  require(dataSetTypes contains `type`, s"unrecognized type: ${`type`}")

  lazy val schemaMap = jsonMapper.readValue[DataSetSchema](schema)
  lazy val propertiesMap = jsonMapper.readValue[Map[String, Any]](properties)


  def insert(implicit con: Connection): Unit = {
    val cTime = JDBCConnection.currentTimestamp
    val sql =
      s"""
INSERT INTO datasets(name, type, schema, path, ispartitioned, owner, version, properties, created, updated)
VALUES('${name}', '${`type`}', '${schema}', '${path}', ${ispartitioned}, '${owner}', ${version}, '${properties}', '${cTime}', '${cTime}')
       """

    JDBCQuery.runQuery(sql)
  }
}




