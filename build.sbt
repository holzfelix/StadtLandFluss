name := "webtechgame"

version := "1.0-SNAPSHOT"

val appDependencies = Seq(
      // Add your project dependencies here,
	  "mysql" % "mysql-connector-mxj" % "5.0.12"
    )

play.Project.playJavaSettings
