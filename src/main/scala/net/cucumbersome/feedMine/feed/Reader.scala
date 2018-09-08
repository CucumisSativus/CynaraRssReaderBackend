package net.cucumbersome.feedMine.feed

import java.time.LocalDateTime

import cats.MonadError
import com.rometools.rome.feed.synd.SyndEntry
import net.cucumbersome.feedMine.feed.Feed.{FeedUrl, LastUpdatedDate}

import scala.collection.JavaConverters._

object Reader {

  private[feed] def selectOnlyEntriesNewerThan(lastUpdateDate: LocalDateTime)(entries: Seq[ParsedEntry]): Seq[ParsedEntry] =
    entries.filter(entry => entry.entryDate.isAfter(lastUpdateDate))


  private[feed] def buildDomainEntry(syndEntry: SyndEntry): ParsedEntry = {
    val title = ParsedEntry.buildTitle(syndEntry.getTitle)
    val uri = ParsedEntry.buildUrl(syndEntry.getUri)
    val date = ParsedEntry.buildDate(syndEntry.getUpdatedDate)
    val content = ParsedEntry.buildContent(syndEntry.getContents.asScala.map(_.getValue).mkString(""))

    ParsedEntry(title, uri, date, content)
  }

  private[feed] def buildDomainEntries(entries: Seq[SyndEntry]): Seq[ParsedEntry] = entries.map(buildDomainEntry)

  sealed trait ReadLanguageErrors

  case class ReadingFailed(feedUrl: FeedUrl, exception: Exception) extends ReadLanguageErrors

  def getEntriesProgram[F[_]](reader: ReaderLanguage[F])(feed: Feed, now: () => LastUpdatedDate)(implicit F: MonadError[F, ReadLanguageErrors]): F[(Seq[ParsedEntry], Feed)] = {
    val getNewestAndUpdateFeed: Seq[ParsedEntry] => (Seq[ParsedEntry], Feed) = (entries: Seq[ParsedEntry]) => {
      val filteredEntries = feed.lastUpdatedDate.map(lastUpdatedDate =>
        selectOnlyEntriesNewerThan(lastUpdatedDate)(entries)
      ).getOrElse(entries)

      (filteredEntries, feed.copy(lastUpdatedDate = Some(now())))
    }
    F.map(reader.readFeed(feed))(buildDomainEntries _ andThen getNewestAndUpdateFeed)
  }

  private[feed] trait ReaderLanguage[F[_]] {
    def readFeed(feed: Feed): F[Seq[SyndEntry]]
  }
}

