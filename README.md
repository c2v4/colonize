[![CircleCI](https://circleci.com/gh/c2v4/colonize.svg?style=shield)](https://circleci.com/gh/c2v4/colonize)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2e705c292f72432fb64e4de516777722)](https://app.codacy.com/app/c2v4/colonize?utm_source=github.com&utm_medium=referral&utm_content=c2v4/colonize&utm_campaign=badger)
# Colonize

A [LibGDX](http://libgdx.badlogicgames.com/) project generated with [gdx-setup](https://github.com/czyzby/gdx-setup).

Project template included simple launchers and an `ApplicationAdapter` extension that draws a simple GUI stage on the screen.

## Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies. Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands. Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `-t`: this flag will run in continuous mode - checking if sources were changed and rerunning tasks
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `desktop:jar`: builds application's runnable jar, which can be found at `desktop/build/libs`.
- `desktop:run`: starts the application.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `pack`: packs GUI assets from `raw/ui`. Saves the atlas file at `assets/ui`.
- `server:run`: runs the server application.
- `test`: runs unit tests (if any).
- `ktlintCheck`: checks code formatting
- `ktlintFormat`: formats source code

For continuous testing and ensuring good quality please use `gradlew -t --console=rich test ktlintCheck`

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
