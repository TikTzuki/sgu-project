# Generate maven project
#mvn archetype:generate "-DgroupId=com.sqlsamples" "-DartifactId=SqlServerSample" "-DarchetypeArtifactId=maven-archetype-quickstart" "-Dversion=1.0.0"
if [ $1 ]
  then
  echo $1
  mvn package
mvn -q exec:java "-Dexec.mainClass=com.tourism.$1"
else
mvn package
mvn -q exec:java "-Dexec.mainClass=com.tourism.GUI.MainFrame"
fi
