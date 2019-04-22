# Your True Self

A [LibGDX](http://libgdx.badlogicgames.com/) project generated with [gdx-setup](https://github.com/czyzby/gdx-setup).

Project template includes simple launchers and a `Game` extension that sets the first screen.

## Documentation

Documentation is available on [the project's GitHub Pages](https://akervinen.github.io/your-true-self/index.html).

## The important bits

- `gradlew desktop:run`: runs the game
- `gradlew desktop:fatJar`: creates an all-in-one .jar file

## Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies. Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands. Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `android:lint`: performs Android project validation.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `desktop:fatJar`: builds application's runnable jar, which can be found at `desktop/build/libs`.
- `desktop:run`: starts the application.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `pack`: packs GUI assets from `raw/ui`. Saves the atlas file at `assets/ui`.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## Attributions

- Roboto-Regular.ttf: Copyright 2011 Google Inc. All Rights Reserved.
- Roboto-Medium.ttf: Copyright 2011 Google Inc. All Rights Reserved.
- Roboto-Bold.ttf: Copyright 2011 Google Inc. All Rights Reserved.
