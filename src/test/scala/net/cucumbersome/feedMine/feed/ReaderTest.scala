package net.cucumbersome.feedMine.feed

import java.net.URL
import java.time.{LocalDateTime, ZoneOffset}
import java.util.Date

import cats.implicits._
import com.rometools.rome.feed.synd.{SyndContent, SyndContentImpl, SyndEntry, SyndEntryImpl}
import net.cucumbersome.feedMine.feed.Reader._
import net.cucumbersome.feedMine.feed.ReaderTest._
import net.cucumbersome.feedMine.test.UnitTest

import scala.collection.JavaConverters._
import scala.collection.mutable

class ReaderTest extends UnitTest {

  "Reader" when {
    val oldEntry = ParsedEntry(
      title = ParsedEntry.buildTitle("title"),
      entryUrl = ParsedEntry.buildUrl("url"),
      entryDate = ParsedEntry.buildDate(LocalDateTime.of(2008, 12, 11, 0, 0)),
      entryContent = ParsedEntry.buildContent("content")
    )

    val middleEntry = oldEntry.copy(entryDate = ParsedEntry.buildDate(LocalDateTime.of(2011, 12, 4, 0, 0)))
    val newestEntry = oldEntry.copy(entryDate = ParsedEntry.buildDate(LocalDateTime.of(2018, 11, 22, 0, 0)))
    val entries = Seq(middleEntry, newestEntry, oldEntry)

    "selecting only not saved yet entries" should {
      "select only entries newer than" in {
        val obtained = Reader.selectOnlyEntriesNewerThan(LocalDateTime.of(2010, 5, 4, 0, 0))(entries)

        obtained should contain theSameElementsAs Seq(middleEntry, newestEntry)
      }
    }

    "using reader program" should {
      val now: Feed.LastUpdatedDate = Feed.buildLastUpdateDate(LocalDateTime.now())

      val initialFeed = Feed(
        Feed.buildUrl(new URL("http://example.com")),
        Feed.buildLastUpdateDate(LocalDateTime.of(2009, 3, 9, 0, 0)).some
      )

      "return failure in case reading fails" in {
        val program = getEntriesProgram(failingLanguage) _
        val result = program(initialFeed, () => now)
        assert(result.isLeft)
      }

      "return filtered entries and return updated feed" in {
        val program = getEntriesProgram(successfulLanguage(entries)) _
        val result = program(initialFeed, () => now)

        assert(result.isRight)
        val (obtainedEntries, updatedFeed) = result.right.get

        obtainedEntries should contain theSameElementsAs Seq(middleEntry, newestEntry)
        updatedFeed shouldBe initialFeed.copy(lastUpdatedDate = Some(now))

      }
    }
  }
}

object ReaderTest {
  type Wrapper[A] = Either[ReadLanguageErrors, A]

  val failingLanguage: ReaderLanguage[Wrapper] = (_: Feed) => Left(
    ReadingFailed(Feed.buildUrl(new URL("http://example.com")), new Exception("failed"))
  )

  def successfulLanguage(entries: Seq[ParsedEntry]): ReaderLanguage[Wrapper] = (_: Feed) => {
    val transformedEntries = entries.map(transform)
    Right(transformedEntries)
  }

  def transform(entry: ParsedEntry): SyndEntry = {
    val syndEntry = new SyndEntryImpl()
    syndEntry.setTitle(entry.title)
    syndEntry.setUri(entry.entryUrl)
    syndEntry.setUpdatedDate(Date.from(entry.entryDate.atZone(ZoneOffset.UTC).toInstant))
    val contents = new SyndContentImpl
    contents.setValue(entry.entryContent)
    syndEntry.setContents(mutable.Buffer(contents.asInstanceOf[SyndContent]).asJava)
    syndEntry
  }
}
