package com.atomist.rug.kind.dockerfile

import com.atomist.rug.kind.core.{FileMutableView, ProjectMutableView}
import com.atomist.source.{SimpleFileBasedArtifactSource, StringFileArtifact}
import org.scalatest.{FlatSpec, Matchers}

class DockerfileMutableViewTest extends FlatSpec with Matchers {

  import DockerfileMutableViewTest._

  it should "be able to get something interesting" in {
    import DockerfileMutableView._
    assert(mv.somethingUseful === useful)
  }

  it should "be able to get the contents of the file" in {
    assert(mv.contents === fileContents)
  }

  it should "be able to set the contents of the file" in {
    val newContent =
      """This is the new content
        |for the thing we'd like to test.
        |""".stripMargin
    mv.setContents(newContent)
    assert(mv.contents === newContent)
  }
}

object DockerfileMutableViewTest {

  import DockerfileType._

  private val fileContents =
    """FROM java:8
      |RUN [ "/bin/bash" ]
      |""".stripMargin
  val fileName: String = dockerfileName
  val fileArtifact = StringFileArtifact(fileName, fileContents)
  val artifactSource = SimpleFileBasedArtifactSource(fileArtifact)
  val pmv = new ProjectMutableView(artifactSource)
  val fmv = new FileMutableView(fileArtifact, pmv)
  val mv = new DockerfileMutableView(fileArtifact, pmv, new Dockerfile)

}
