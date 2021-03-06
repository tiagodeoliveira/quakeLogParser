package io.tiagodeoliveira

/**
  * Holds the logic to extract the kill data from a log line.
  *
  * Created by tiagooliveira on 11/18/15.
  */
object KillExtractor {
  def unapplySeq(line: String): Option[List[String]] = {
    val pattern = """:\s([^:]+)\skilled\s(.*?)\sby\s(.*)""".r
    val result = pattern.findAllMatchIn(line).toList.head.subgroups
    Some(result)
  }
}
