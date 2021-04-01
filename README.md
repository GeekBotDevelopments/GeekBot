# GeekBot

[discord-invite]: https://discord.gg/ADrTFRZ
[discord-shield]: https://discord.com/api/guilds/632708637122625538/widget.png

![Java CI with Gradle](https://github.com/LegendaryGeek/GeekBot/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=release)
![Build and Push Docker image](https://github.com/LegendaryGeek/GeekBot/workflows/Build%20and%20Push%20Docker%20image/badge.svg?branch=release)
[ ![discord-shield][] ][discord-invite]


## How to run locally

Build the project using gradle or run from the IDE. Make sure to have a folder called `Config` nearby containing Config.properties.

Optionally an env variable `main_config` can be set to point the app towards the `Config` folder location

## Docker

Run the following commands locally

Build: `docker build . -t geekbot:local --rm` will build the image

Run: `docker-compose up docker-compose.yml --build` will build then run the image as a compose

Stop: `docker-compose -f docker-compose.yml down && docker-compose -f docker-compose.yml rm f` will shutdown the compose