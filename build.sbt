name := "sbt-sequence"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "org.apache.zookeeper" % "zookeeper" % "3.4.5-cdh5.13.1",
  "org.apache.curator" % "curator-framework" % "2.12.0",
  "org.apache.curator" % "curator-recipes" % "2.12.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.apache.curator" % "curator-test" % "2.12.0" % Test
)

resolvers ++= Seq(
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos"
)
