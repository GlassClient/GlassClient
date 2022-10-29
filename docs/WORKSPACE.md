# GlassClient 

## Setting up a Workspace
GlassClient uses gradle, so make sure that it is installed properly. Instructions can be found on [Gradle's website](https://gradle.org/install/).
1. Clone the repository using `git clone https://github.com/GlassClient/GlassClient.git` (Make sure you have git or Github Desktop installed on your system).
2. CD into the local repository folder.
3. If you are using Intelij run the following command `gradlew --debug setupDevWorkspace idea genIntellijRuns build`
4. Open the folder as a Gradle project in your IDE. (Make sure that your IDE is using Java 8, if not then it will have issues)
5. Select the Forge run configuration.
### Troubleshooting Workspace Errors
If you get a "cannot find forgebin" error, download forge 1.8.9 universal from the forge site and place it in `./GlassClient-main/.gradle/minecraft`