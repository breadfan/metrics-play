package com.kenshoo.play.metrics

import play.api.inject.Module
import play.api.{Configuration, Environment}

class PlayModule extends Module {
  def bindings(environment: Environment, configuration: Configuration) = {
    if (configuration.get[Option[Boolean]]("metrics.enabled").getOrElse(true)) {
      Seq(
        bind[MetricsFilter].to[MetricsFilterImpl].eagerly,
        bind[Metrics].to[MetricsImpl].eagerly
      )
    } else {
      Seq(
        bind[MetricsFilter].to[DisabledMetricsFilter].eagerly,
        bind[Metrics].to[DisabledMetrics].eagerly
      )
    }
  }
}
