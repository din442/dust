import com.jigsawstdio.dust._
import org.scalatra._
import javax.servlet.ServletContext
import scalikejdbc.ConnectionPool

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    Class.forName("org.postgresql.Driver")
    ConnectionPool.singleton("jdbc:postgresql://127.0.0.1:5432/data_catalog", "jigsaw_app", "jigsaw")

    context.mount(new DustServlet, "/*")
  }
}
