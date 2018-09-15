package net.cucumbersome.feedMine.feed

import java.net.URL
import java.time.LocalDateTime

import com.softwaremill.tagging._
import net.cucumbersome.feedMine.feed.Feed.{FeedUrl, LastUpdatedDate}

case class Feed(
                 feedUrl: FeedUrl,
                 lastUpdatedDate: Option[LastUpdatedDate]
               )

object Feed {

  sealed trait LastUpdatedDateTag

  type LastUpdatedDate = LocalDateTime @@ LastUpdatedDateTag

  def buildLastUpdateDate(date: LocalDateTime): LastUpdatedDate = date.taggedWith[LastUpdatedDateTag]

  sealed trait FeedUrlTag

  type FeedUrl = URL @@ FeedUrlTag

  def buildUrl(url: URL): FeedUrl = url.taggedWith[FeedUrlTag]
}


