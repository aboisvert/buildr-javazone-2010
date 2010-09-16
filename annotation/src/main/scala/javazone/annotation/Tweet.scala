package javazone.annotation

class Tweet(
  val author: String,
  val text: String,
  val latitude: Double,
  val longitude: Double
) {
  override def toString() = "[%s] %s" format (author, text)
}
