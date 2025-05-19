# clock24

24-hour clock in Clojure using Quil / Processing

## Installation

Tested on Linux, Mac and Windows.

You need the Java JDK.  On Ubuntu 23.04 I did this:

```
$ sudo apt install openjdk-21-jdk
$ java -version
openjdk version "21.0.2" 2024-01-16
OpenJDK Runtime Environment (build 21.0.2+13-Ubuntu-122.04.1)
OpenJDK 64-Bit Server VM (build 21.0.2+13-Ubuntu-122.04.1, mixed mode, sharing)
```

Then download the "lein" script from `leiningen.org`, and run it; it
will self-install.

## Usage

Just run it with Leiningen:

```
$ lein run
```

If you build an uberjar with `lein uberjar` then you can run that
wherever, with:

```
$ java -jar clock24-0.1.0-standalone.jar
```

## Options

[no options yet]

