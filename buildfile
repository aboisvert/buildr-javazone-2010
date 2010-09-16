require 'buildr/scala'
require 'buildr/groovy'

VERSION_NUMBER = "1.0.0"

repositories.remote << "http://www.ibiblio.org/maven2/"

WORLDWIND_HOME = ENV['WORLDWIND_HOME'] || fail("Please set WORLDWIND_HOME")

WORLDWIND = Dir[File.join(WORLDWIND_HOME, '*.jar')]

HTTP_BUILDER = transitive('org.codehaus.groovy.modules.http-builder:http-builder:jar:0.5.0-RC2')

define "javazone" do
  project.version = VERSION_NUMBER
  project.group = "javazone"

  # Java
  define "globe" do
    manifest['Git-Version'] = `git log -1 --pretty=format:%H` 
      
    compile.with WORLDWIND
    test.exclude '*Failing*'
    
    unless $stdout.isatty
      test.exclude '*Globe*'
    end
    package(:jar)
  end

  # Scala
  define "annotation" do
    compile.with \
      project("globe"),
      project("globe").compile.dependencies
      
      package(:jar)
  end

  # Groovy
  define "feed" do
    compile.with \
        project("annotation"),
      project("annotation").compile.dependencies,
      HTTP_BUILDER
      
    task "run" do
      Java::Commands.java "javazone.feed.FakeFeed",
        :classpath => [ compile.dependencies, compile.target ]
    end
      
  end

end

Project.local_task 'run'
