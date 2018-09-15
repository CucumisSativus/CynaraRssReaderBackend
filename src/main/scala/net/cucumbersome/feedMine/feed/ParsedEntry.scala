package net.cucumbersome.feedMine.feed

import java.time.{Instant, LocalDateTime, ZoneOffset}
import java.util.Date

import com.softwaremill.tagging._
import net.cucumbersome.feedMine.feed.ParsedEntry._

case class ParsedEntry(
                        title: Title,
                        entryUrl: EntryUrl,
                        entryDate: EntryDate,
                        entryContent: EntryContent
                      )

object ParsedEntry {

  sealed trait TitleTag

  type Title = String Tagged TitleTag

  def buildTitle(str: String): Title = str.taggedWith[TitleTag]

  sealed trait UrlTag

  type EntryUrl = String Tagged UrlTag

  def buildUrl(str: String): EntryUrl = str.taggedWith[UrlTag]

  sealed trait EntryDateTag

  type EntryDate = LocalDateTime Tagged EntryDateTag

  def buildDate(date: LocalDateTime): EntryDate = date.taggedWith[EntryDateTag]

  def buildDate(date: Date): EntryDate = buildDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime), ZoneOffset.UTC))

  sealed trait EntryContentTag

  type EntryContent = String Tagged EntryContentTag

  def buildContent(content: String): EntryContent = content.taggedWith[EntryContentTag]
}