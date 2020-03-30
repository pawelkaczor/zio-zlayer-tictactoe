package com.example.tictactoe.parser

import com.example.tictactoe.domain.ConfirmCommand
import zio._

package object confirm {
  type ConfirmCommandParser = Has[ConfirmCommandParser.Service]

  object ConfirmCommandParser {
    trait Service {
      def parse(input: String): IO[Unit, ConfirmCommand]
    }
    object Service {
      val live: Layer[Nothing, ConfirmCommandParser] = ZLayer.succeed {
        new Service {
          override def parse(input: String): IO[Unit, ConfirmCommand] =
            input match {
              case "yes" => ZIO.succeed(ConfirmCommand.Yes)
              case "no"  => ZIO.succeed(ConfirmCommand.No)
              case _     => ZIO.fail(())
            }
        }
      }
    }

    // accessors
    def parse(input: String): ZIO[ConfirmCommandParser, Unit, ConfirmCommand] = ZIO.accessM(_.get.parse(input))
  }
}