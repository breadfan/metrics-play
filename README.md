# metrics-play

This module provides some support for @codahale [Metrics](https://dropwizard.github.io/metrics/3.1.2/) library in a Play2 application (Scala)

[![Build Status](https://travis-ci.org/breadfan/metrics-play.svg)](https://travis-ci.org/breadfan/metrics-play)
[![codecov.io](http://codecov.io/github/breadfan/metrics-play/coverage.svg?branch=master)](http://codecov.io/github/AutoScout24/metrics-play?branch=master)
[![Download](https://api.bintray.com/packages/breadfan/maven/metrics-play/images/download.svg) ](https://bintray.com/breadfan/maven/metrics-play/_latestVersion)

Play Version: 2.6.0-M2 Metrics Version: 3.2.2, Scala Version: 2.12.2

## Features

1. Default Metrics Registry
2. Metrics Servlet
3. Filter to instrument http requests


## Usage

Add metrics-play dependency:

```scala
    val appDependencies = Seq(
    ...
    "com.kenshoo" %% "metrics-play" % "2.5.xxx"
    )
```

To enable the module:

add to application.conf the following line

     play.modules.enabled+="com.kenshoo.play.metrics.PlayModule"

### Default Registry

To add a custom metrics, you can use `defaultRegistry` which returns an instance of [MetricRegistry](http://metrics.dropwizard.io/3.1.0/manual/core/).

```scala
     import com.kenshoo.play.metrics.Metrics
     import com.codahale.metrics.Counter

     class SomeController @Inject() (metrics: Metrics) {
         val counter = metrics.defaultRegistry.counter("name")
         counter.inc()
     }
````

### Metrics Controller

An implementation of the [metrics-servlet](http://metrics.codahale.com/manual/servlets/) as a play2 controller.

It exports all registered metrics as a json document.

To enable the controller add a mapping to conf/routes file

     GET     /admin/metrics              com.kenshoo.play.metrics.MetricsController.metrics
     
#### Configuration

Override values from the (metrics-reference.conf)[src/main/resources/metrics-reference.conf] as needed in your `application.conf`:

```HOCON
metrics {
     name: "default"
     rateUnit: "SECONDS"
     durationUnit: "SECONDS"
     showSamples: false
     jvm: true # reporting jvm metrics
     logback: true # controls reporing logback metrics 
}
```

### Metrics Filter

An implementation of the Metrics' instrumenting filter for Play2. It records requests duration, number of active requests and counts each return code


```scala
    import com.kenshoo.play.metrics.MetricsFilter
    import play.api.mvc._

    class Filters @Inject() (metricsFilter: MetricsFilter) extends HttpFilters {
        val filters = Seq(metricsFilter)
    }
```

## Advanced usage

By default, metrics are prefixed with "com.kenshoo.play.metrics.MetricsFilter".

```
"com.kenshoo.play.metrics.MetricsFilter.200" : {
   "count" : 1584456,
   "m15_rate" : 1.6800220918042639,
   "m1_rate" : 1.9015104460758263,
   "m5_rate" : 1.8138545372237085,
   "mean_rate" : 3.20162010446889,
   "units" : "events/second"
},
```

You can change the prefix by extending `MetricsFilterImpl`.

```scala
package myapp

import javax.inject.Inject

import com.kenshoo.play.metrics.{MetricsImpl, MetricsFilter, Metrics, MetricsFilterImpl}
import play.api.http.Status
import play.api.inject.Module
import play.api.{Configuration, Environment}

class MyMetricsFilter @Inject() (metrics: Metrics) extends MetricsFilterImpl(metrics) {

  // configure metrics prefix
  override def labelPrefix: String = "foobar"

  // configure status codes to be monitored. other status codes are labeled as "other"
  override def knownStatuses = Seq(Status.OK)
}

class MyMetricsModule extends Module {
  def bindings(environment: Environment, configuration: Configuration) = {
    Seq(
      bind[MetricsFilter].to[MyMetricsFilter].eagerly,
      bind[Metrics].to[MetricsImpl].eagerly
    )
  }
}
```

and add a line like this to application.conf

```
play.modules.enabled+="myapp.MyMetricsModule"
```

instead of `com.kenshoo.play.metrics.PlayModule`

## Changes

* 2.6.0-M2    - Supporting Play 2.6, added Scala 2.12, abandoning Scala 2.11
* 2.5.13      - Supporting Play 2.5, abandoning Scala 2.10
* 2.4.0_0.4.0 - Re-implement as Play Module
* 2.4.0_0.3.0 - Upgrade to play 2.4, metrics 3.1.2
* 2.3.0_0.2.1 - Breaking Change! prefix jvm metric names to standardize with dropwizard
* 2.3.0_0.2.0 - Meter uncaught exceptions as 500 Internal Server Error
* 2.3.0_0.1.9 - Add extra http codes, support configurable metrics names for requests filter
* 2.3.0_0.1.8 - Support default registry in play java. Replace MetricsRegistry.default with MetricsRegistry.defaultRegistry (to support java where default is a reserved keyword)


## License
This code is released under the Apache Public License 2.0.
