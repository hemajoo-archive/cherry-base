# Cherry Base - DevOps Workflow

<hr>

**GitHub** is used as the **DevOps** and **CI** (continuous integration) platform.

Each time code is pushed or a pull request (PR) is opened against a *protected* branch, a **DevOps** workflow is automatically triggered and executes the following **Maven** goals:

|      Goal | Description                                                                   |
|----------:|:------------------------------------------------------------------------------|
|   `build` | Compile the project's code.                                                   |
|    `test` | Executes the project's unit tests.                                            |
| `package` | Generate the project's artifacts.                                             |
|   `sonar` | Execute a static analysis of the code (code quality).                         |
|  `deploy` | Deploy the artifacts in a **Maven** registry.                                 |
|     `jib` | Generate a Docker image of the solution and push it to a **Docker** registry. |

Rules have been set to ensure, if one of these steps does not complete successfully, the code will not be merged into the protected branch!

See: [Sonar - Quality Gate]()
